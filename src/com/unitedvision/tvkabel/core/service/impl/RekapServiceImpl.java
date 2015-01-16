package com.unitedvision.tvkabel.core.service.impl;

import java.time.Month;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unitedvision.tvkabel.core.domain.Kelurahan;
import com.unitedvision.tvkabel.core.domain.Pegawai;
import com.unitedvision.tvkabel.core.domain.Pelanggan;
import com.unitedvision.tvkabel.core.domain.Pelanggan.Status;
import com.unitedvision.tvkabel.core.domain.Pembayaran;
import com.unitedvision.tvkabel.core.domain.Perusahaan;
import com.unitedvision.tvkabel.core.domain.Tagihan;
import com.unitedvision.tvkabel.core.service.PelangganService;
import com.unitedvision.tvkabel.core.service.PembayaranService;
import com.unitedvision.tvkabel.core.service.RekapService;
import com.unitedvision.tvkabel.persistence.entity.PegawaiEntity;
import com.unitedvision.tvkabel.persistence.entity.PelangganEntity;
import com.unitedvision.tvkabel.persistence.entity.PembayaranEntity;
import com.unitedvision.tvkabel.persistence.entity.PembayaranEntity.TagihanValue;
import com.unitedvision.tvkabel.util.DateUtil;

@Service
public class RekapServiceImpl implements RekapService {
	@Autowired
	private PelangganService pelangganService;
	@Autowired
	private PembayaranService pembayaranService;

	@Override
	public List<? extends Pelanggan> rekapHarian(Pegawai pegawai, Date hari) {
		List<? extends Pelanggan> listPelanggan = pelangganService.get(pegawai, hari);

		return verify(listPelanggan, hari);
	}
	
	@Override
	public List<? extends Pelanggan> rekapHarian(Pegawai pegawai, Date hari, int lastNumber) {
		List<? extends Pelanggan> listPelanggan = pelangganService.get(pegawai, hari, lastNumber);

		return verify(listPelanggan, hari);
	}
	
	private List<? extends Pelanggan> verify(List<? extends Pelanggan> list, Date hari) {
		final int numberOfMonth = 5;
		Tagihan tagihanAkhir = TagihanValue.create(hari);
		Tagihan tagihanAwal = TagihanValue.create(hari);
		tagihanAwal.substract(numberOfMonth);
		
		for (Pelanggan p : list) {
			List<PembayaranEntity> listPembayaran = pembayaranService.get(p, tagihanAwal, tagihanAkhir);
			listPembayaran = verifyListPembayaran(listPembayaran, numberOfMonth);
			p.setListPembayaran(listPembayaran);
		}
		
		return list;
	}
	
	private List<PembayaranEntity> verifyListPembayaran(List<PembayaranEntity> list, int counter) {
		if (list.size() == 0) {
			for (int i = 1; i <= counter; i++)
				list.add(createDefaultPembayaran(2014, Month.JANUARY));
		} else {
			int listSize = list.size();
			for (int i = listSize; i < counter; i++)
				list.add(createDefaultPembayaran(2014, Month.JANUARY));
		}
		
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PembayaranEntity> rekapTagihanBulanan(Perusahaan perusahaan, int tahun, Month bulan, int lastNumber) {
		return (List<PembayaranEntity>)pembayaranService.get(perusahaan, tahun, bulan, lastNumber);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PembayaranEntity> rekapPembayaranBulanan(Perusahaan perusahaan, int tahun, Month bulan, int lastNumber) {
		final Date tanggalMulai = DateUtil.getDate(tahun, bulan, 1);
		final Date tanggalAkhir = DateUtil.getDate(tahun, bulan, DateUtil.getLastDay(bulan, tahun));

		return (List<PembayaranEntity>)pembayaranService.get(perusahaan, tanggalMulai, tanggalAkhir, lastNumber);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PelangganEntity> rekapTahunan(Perusahaan perusahaan, int tahun, int lastNumber) {
		List<PelangganEntity> listPelanggan = (List<PelangganEntity>)pelangganService.get(perusahaan, Status.AKTIF, lastNumber);

		return rekapTahunan(listPelanggan, tahun);
	}

	@SuppressWarnings("unchecked")
	private List<PelangganEntity> rekapTahunan(List<PelangganEntity> list, int tahun) {
 		for (Pelanggan pelanggan : list) {
 			
 			List<PembayaranEntity> listPembayaran = (List<PembayaranEntity>)pembayaranService.get(pelanggan, tahun);
 			verifyListPembayaran(listPembayaran, tahun, pelanggan);
 			pelanggan.setListPembayaran(listPembayaran); 
 		} 
 		 
 		return list; 
	}
	
	private void verifyListPembayaran(List<PembayaranEntity> listPembayaran, int tahun, Pelanggan pelanggan) {
		if (listPembayaran.size() > 0) {
			verifyPembayaranFirst(listPembayaran);
			verifyPembayaranLast(listPembayaran);
		} else {
			verifyPembayaranEmpty(listPembayaran);
		}
	}

	/**
	 * Set the default {@link Pembayaran} value when it is not starts from JANUARY
	 * @param listPembayaran
	 */
	private void verifyPembayaranFirst(List<PembayaranEntity> listPembayaran) {
		PembayaranEntity pembayaranEntity = listPembayaran.get(0);
		
		if (pembayaranEntity.getBulan() != Month.JANUARY) {
			for (int i = Month.JANUARY.getValue(); i < pembayaranEntity.getBulan().getValue(); i++) {
				listPembayaran.add(0, createDefaultPembayaran(pembayaranEntity.getTahun(), pembayaranEntity.getBulan()));
			}
		}
	}
	
	/**
	 * Set the default {@link Pembayaran} value when it is not ends to DECEMBER
	 * @param listPembayaran
	 */
	private void verifyPembayaranLast(List<PembayaranEntity> listPembayaran) {
		PembayaranEntity pembayaranEntity = listPembayaran.get(listPembayaran.size() - 1);

		if (pembayaranEntity.getBulan() != Month.DECEMBER) {
			for (int i = pembayaranEntity.getBulan().getValue(); i < Month.DECEMBER.getValue(); i++) {
				listPembayaran.add(createDefaultPembayaran(pembayaranEntity.getTahun(), pembayaranEntity.getBulan()));
			}
		}
	}
	
	/**
	 * Set the default {@link Pembayaran} value when it is not found
	 * @param listPembayaran
	 */
	private void verifyPembayaranEmpty(List<PembayaranEntity> listPembayaran) {
		for (int i = Month.JANUARY.getValue(); i <= Month.DECEMBER.getValue(); i++) {
			listPembayaran.add(createDefaultPembayaran(0, Month.of(i)));
		}
	}
	
	private PembayaranEntity createDefaultPembayaran(int tahun, Month bulan) {
		Tagihan tagihan = new TagihanValue(tahun, bulan);

		return new PembayaranEntity("default", DateUtil.getNow(), new PelangganEntity(), new PegawaiEntity(), 0, tagihan.toEntity());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PelangganEntity> rekapTahunan(Perusahaan perusahaan, int tahun) {
		List<PelangganEntity> listPelanggan = (List<PelangganEntity>)pelangganService.get(perusahaan, Status.AKTIF);

		return rekapTahunan(listPelanggan, tahun);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PembayaranEntity> rekapTagihanBulanan(Perusahaan perusahaan, int tahun, Month bulan) {
		Tagihan tagihan = new TagihanValue(tahun, bulan);

		return (List<PembayaranEntity>)pembayaranService.get(perusahaan, tagihan);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PembayaranEntity> rekapPembayaranBulanan(Perusahaan perusahaan, int tahun, Month bulan) {
		final Date tanggalAwal = DateUtil.getDate(tahun, bulan, 1);
		final Date tanggalAkhir = DateUtil.getDate(tahun, bulan, DateUtil.getLastDay(bulan, tahun));

		return (List<PembayaranEntity>)pembayaranService.get(perusahaan, tanggalAwal, tanggalAkhir);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PelangganEntity> rekapTunggakan(Perusahaan perusahaan, Status status, Integer tunggakan) {
		return (List<PelangganEntity>)pelangganService.getByTunggakan(perusahaan, status, tunggakan);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PelangganEntity> rekapTunggakan(Perusahaan perusahaan, Status status, Integer tunggakan, int lastNumber) {
		return (List<PelangganEntity>)pelangganService.getByTunggakan(perusahaan, status, tunggakan, lastNumber);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PelangganEntity> rekapAlamat(Perusahaan perusahaan, Status status, Kelurahan kelurahan, Integer lingkungan) {
		return (List<PelangganEntity>)pelangganService.get(perusahaan, status, kelurahan, lingkungan);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PelangganEntity> rekapAlamat(Perusahaan perusahaan, Status status, Kelurahan kelurahan, Integer lingkungan, Integer lastNumber) {
		return (List<PelangganEntity>)pelangganService.get(perusahaan, status, kelurahan, lingkungan, lastNumber);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PelangganEntity> rekapAlamat(Perusahaan perusahaan) {
		return (List<PelangganEntity>)pelangganService.getOrdered(perusahaan, Pelanggan.Status.AKTIF);
	}

	@Override
	public long countRekapTagihanBulanan(Perusahaan perusahaan, int tahun, Month bulan) {
		Tagihan tagihan = new TagihanValue(tahun, bulan);
		
		return pembayaranService.count(perusahaan, tagihan);
	}

	@Override
	public long countRekapPembayaranBulanan(Perusahaan perusahaan, int tahun, Month bulan) {
		final Date tanggalMulai = DateUtil.getDate(tahun, bulan, 1);
		final Date tanggalAkhir = DateUtil.getDate(tahun, bulan, DateUtil.getLastDay(bulan, tahun));

		return pembayaranService.count(perusahaan, tanggalMulai, tanggalAkhir);
	}

	@Override
	public long countRekapHarian(Pegawai pegawai, Date hari) {
		return pelangganService.count(pegawai, hari);
	}
	
	@Override
	public long countRekapTahunan(Perusahaan perusahaan) {
		return pelangganService.count(perusahaan, Status.AKTIF);
	}

	@Override
	public long countRekapTunggakan(Perusahaan perusahaan, Status status, Integer tunggakan) {
		return pelangganService.countByTunggakan(perusahaan, status, tunggakan);
	}

	@Override
	public long countRekapAlamat(Perusahaan perusahaan, Status status, Kelurahan kelurahan, Integer lingkungan) {
		return pelangganService.count(perusahaan, status, kelurahan, lingkungan);
	}
}
