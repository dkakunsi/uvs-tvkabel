package com.unitedvision.tvkabel.core.service.impl;

import java.time.Month;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unitedvision.tvkabel.core.service.PelangganService;
import com.unitedvision.tvkabel.core.service.PembayaranService;
import com.unitedvision.tvkabel.core.service.RekapService;
import com.unitedvision.tvkabel.persistence.entity.Kelurahan;
import com.unitedvision.tvkabel.persistence.entity.Pegawai;
import com.unitedvision.tvkabel.persistence.entity.Pelanggan;
import com.unitedvision.tvkabel.persistence.entity.Pembayaran;
import com.unitedvision.tvkabel.persistence.entity.Perusahaan;
import com.unitedvision.tvkabel.persistence.entity.Pelanggan.Status;
import com.unitedvision.tvkabel.persistence.entity.Pembayaran.Tagihan;
import com.unitedvision.tvkabel.util.DateUtil;

@Service
public class RekapServiceImpl implements RekapService {
	@Autowired
	private PelangganService pelangganService;
	@Autowired
	private PembayaranService pembayaranService;

	@Override
	public List<Pelanggan> rekapHarian(Pegawai pegawai, Date hari) {
		List<Pelanggan> listPelanggan = pelangganService.get(pegawai, hari);

		return rekapHarian(listPelanggan, hari);
	}
	
	@Override
	public List<Pelanggan> rekapHarian(Pegawai pegawai, Date hari, int lastNumber) {
		List<Pelanggan> listPelanggan = pelangganService.get(pegawai, hari, lastNumber);

		return rekapHarian(listPelanggan, hari);
	}
	
	private List<Pelanggan> rekapHarian(List<Pelanggan> list, Date hari) {
		final int numberOfMonth = 5;
		Tagihan tagihanAkhir = Tagihan.create(hari);
		Tagihan tagihanAwal = Tagihan.create(hari);
		tagihanAwal.substract(numberOfMonth);
		
		for (Pelanggan pelanggan : list) {
			List<Pembayaran> listPembayaran = pembayaranService.get(pelanggan, tagihanAwal, tagihanAkhir);
			listPembayaran = verify(listPembayaran, numberOfMonth);
			pelanggan.setListPembayaran(listPembayaran);
		}
		
		return list;
	}
	
	private List<Pembayaran> verify(List<Pembayaran> list, int counter) {
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
	
	@Override
	public List<Pembayaran> rekapTagihanBulanan(Perusahaan perusahaan, int tahun, Month bulan, int lastNumber) {
		return (List<Pembayaran>)pembayaranService.get(perusahaan, tahun, bulan, lastNumber);
	}

	@Override
	public List<Pembayaran> rekapPembayaranBulanan(Perusahaan perusahaan, int tahun, Month bulan, int lastNumber) {
		final Date tanggalMulai = DateUtil.getDate(tahun, bulan, 1);
		final Date tanggalAkhir = DateUtil.getDate(tahun, bulan, DateUtil.getLastDay(bulan, tahun));

		return (List<Pembayaran>)pembayaranService.get(perusahaan, tanggalMulai, tanggalAkhir, lastNumber);
	}
	
	@Override
	public List<Pelanggan> rekapTahunan(Perusahaan perusahaan, int tahun, int lastNumber) {
		List<Pelanggan> listPelanggan = (List<Pelanggan>)pelangganService.get(perusahaan, Status.AKTIF, lastNumber);

		return rekapTahunan(listPelanggan, tahun);
	}

	private List<Pelanggan> rekapTahunan(List<Pelanggan> list, int tahun) {
 		for (Pelanggan pelanggan : list) {
 			List<Pembayaran> listPembayaran = (List<Pembayaran>)pembayaranService.get(pelanggan, tahun);
 			verifyListPembayaran(listPembayaran, tahun, pelanggan);
 			pelanggan.setListPembayaran(listPembayaran); 
 		} 
 		 
 		return list; 
	}
	
	private void verifyListPembayaran(List<Pembayaran> listPembayaran, int tahun, Pelanggan pelanggan) {
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
	private void verifyPembayaranFirst(List<Pembayaran> listPembayaran) {
		Pembayaran pembayaranEntity = listPembayaran.get(0);
		
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
	private void verifyPembayaranLast(List<Pembayaran> listPembayaran) {
		Pembayaran pembayaranEntity = listPembayaran.get(listPembayaran.size() - 1);

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
	private void verifyPembayaranEmpty(List<Pembayaran> listPembayaran) {
		for (int i = Month.JANUARY.getValue(); i <= Month.DECEMBER.getValue(); i++) {
			listPembayaran.add(createDefaultPembayaran(0, Month.of(i)));
		}
	}
	
	private Pembayaran createDefaultPembayaran(int tahun, Month bulan) {
		Tagihan tagihan = new Tagihan(tahun, bulan);

		return new Pembayaran("default", DateUtil.getNow(), new Pelanggan(), new Pegawai(), 0, tagihan);
	}

	@Override
	public List<Pelanggan> rekapTahunan(Perusahaan perusahaan, int tahun) {
		List<Pelanggan> listPelanggan = (List<Pelanggan>)pelangganService.get(perusahaan, Status.AKTIF);

		return rekapTahunan(listPelanggan, tahun);
	}

	@Override
	public List<Pembayaran> rekapTagihanBulanan(Perusahaan perusahaan, int tahun, Month bulan) {
		Tagihan tagihan = new Tagihan(tahun, bulan);

		return (List<Pembayaran>)pembayaranService.get(perusahaan, tagihan);
	}

	@Override
	public List<Pembayaran> rekapPembayaranBulanan(Perusahaan perusahaan, int tahun, Month bulan) {
		final Date tanggalAwal = DateUtil.getDate(tahun, bulan, 1);
		final Date tanggalAkhir = DateUtil.getDate(tahun, bulan, DateUtil.getLastDay(bulan, tahun));

		return (List<Pembayaran>)pembayaranService.get(perusahaan, tanggalAwal, tanggalAkhir);
	}

	@Override
	public List<Pelanggan> rekapTunggakan(Perusahaan perusahaan, Status status, Integer tunggakan) {
		return (List<Pelanggan>)pelangganService.getByTunggakan(perusahaan, status, tunggakan);
	}

	@Override
	public List<Pelanggan> rekapTunggakan(Perusahaan perusahaan, Status status, Integer tunggakan, int lastNumber) {
		return (List<Pelanggan>)pelangganService.getByTunggakan(perusahaan, status, tunggakan, lastNumber);
	}

	@Override
	public List<Pelanggan> rekapAlamat(Perusahaan perusahaan, Status status, Kelurahan kelurahan, Integer lingkungan) {
		return (List<Pelanggan>)pelangganService.get(perusahaan, status, kelurahan, lingkungan);
	}

	@Override
	public List<Pelanggan> rekapAlamat(Perusahaan perusahaan, Status status, Kelurahan kelurahan, Integer lingkungan, Integer lastNumber) {
		return (List<Pelanggan>)pelangganService.get(perusahaan, status, kelurahan, lingkungan, lastNumber);
	}

	@Override
	public List<Pelanggan> rekapAlamat(Perusahaan perusahaan) {
		return (List<Pelanggan>)pelangganService.getOrdered(perusahaan, Pelanggan.Status.AKTIF);
	}

	@Override
	public long countRekapTagihanBulanan(Perusahaan perusahaan, int tahun, Month bulan) {
		Tagihan tagihan = new Tagihan(tahun, bulan);
		
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
