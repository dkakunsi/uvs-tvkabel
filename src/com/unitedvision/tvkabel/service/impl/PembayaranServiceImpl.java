package com.unitedvision.tvkabel.service.impl;

import java.time.Month;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unitedvision.tvkabel.entity.Pegawai;
import com.unitedvision.tvkabel.entity.Pelanggan;
import com.unitedvision.tvkabel.entity.Pembayaran;
import com.unitedvision.tvkabel.entity.Perusahaan;
import com.unitedvision.tvkabel.entity.Pelanggan.Status;
import com.unitedvision.tvkabel.entity.Pembayaran.Tagihan;
import com.unitedvision.tvkabel.exception.ApplicationException;
import com.unitedvision.tvkabel.exception.DataDuplicationException;
import com.unitedvision.tvkabel.exception.EmptyIdException;
import com.unitedvision.tvkabel.exception.EntityNotExistException;
import com.unitedvision.tvkabel.exception.NotPayableCustomerException;
import com.unitedvision.tvkabel.exception.UnpaidBillException;
import com.unitedvision.tvkabel.repository.PembayaranRepository;
import com.unitedvision.tvkabel.service.PegawaiService;
import com.unitedvision.tvkabel.service.PelangganService;
import com.unitedvision.tvkabel.service.PembayaranService;
import com.unitedvision.tvkabel.util.DateUtil;

@Service
@Transactional(readOnly = true)
public class PembayaranServiceImpl implements PembayaranService {
	@Autowired
	private PelangganService pelangganService;
	@Autowired
	private PegawaiService pegawaiService;
	@Autowired
	private PembayaranRepository pembayaranRepository;
	
	@Override
	@Transactional(readOnly = false)
	public void pay(Pelanggan pelanggan) throws ApplicationException {
		Pegawai pegawai = pegawaiService.getOne(pelanggan.getPerusahaan());
		
		Date now = DateUtil.getSimpleNow();
		Tagihan last = Tagihan.create(now);
		Tagihan first = getPayableTagihan(pelanggan);
		
		int selisih = last.compareWith(first);
		
		if (selisih >= 0) {
			selisih++;
			pay(pelanggan, pegawai, 0, selisih);
		}
	}

	@Override
	@Transactional(readOnly = false)
	public Pembayaran pay(Pelanggan pelanggan, Pegawai pegawai, long jumlahPembayaran, int jumlahBulan) throws ApplicationException {
		Pembayaran last;
		
		if (jumlahBulan > 1) {
			last = payList(pelanggan, pegawai, jumlahPembayaran, jumlahBulan);
		} else {
			Tagihan tagihan = getPayableTagihan(pelanggan);
			Pembayaran pembayaran = new Pembayaran(0, "", DateUtil.getNow(), pelanggan, pegawai, jumlahPembayaran, tagihan);
			
			last = pay(pembayaran);
		}
		
		validateAfterPay(last);
		return last;
	};
	
	@Override
	@Transactional(readOnly = false)
	public Pembayaran payList(Pelanggan pelanggan, Pegawai pegawai, long jumlahPembayaran, int jumlahBulan) throws NotPayableCustomerException ,UnpaidBillException ,EntityNotExistException ,DataDuplicationException, EmptyIdException {
		List<Pembayaran> listPembayaran = createListPembayaran(pelanggan, pegawai, jumlahPembayaran, jumlahBulan);
		
		pembayaranRepository.save(listPembayaran);

		Pembayaran last = listPembayaran.get(jumlahBulan - 1);
		
		return last;
	};

	public List<Pembayaran> createListPembayaran(Pelanggan pelanggan, Pegawai pegawai, long jumlahPembayaran, int jumlahBulan) throws EntityNotExistException, EmptyIdException, NotPayableCustomerException, UnpaidBillException, DataDuplicationException {
		Tagihan tagihan = getPayableTagihan(pelanggan);

		List<Pembayaran> listPembayaran = new ArrayList<>();
		
		//Add the first payment
		listPembayaran.add(new Pembayaran(0, "", DateUtil.getNow(), pelanggan, pegawai, jumlahPembayaran, tagihan));

		//Add the second and so on
		for (int i = 2; i <= jumlahBulan; i++) {
			
			//increase tagihan as the second payment
			tagihan = Tagihan.copy(tagihan);
			tagihan.increase();
			
			Pembayaran pembayaran = new Pembayaran(0, "", DateUtil.getNow(), pelanggan, pegawai, jumlahPembayaran, tagihan);
			pembayaran = validateBeforePay(pembayaran);

			listPembayaran.add(pembayaran);
		}
		
		return listPembayaran;
	}
	
	@Override
	@Transactional(readOnly = false)
	public Pembayaran pay(Pembayaran pembayaran) throws NotPayableCustomerException, UnpaidBillException, EntityNotExistException, DataDuplicationException {
		pembayaran = validateBeforePay(pembayaran);
		pembayaran = pembayaranRepository.save(pembayaran);
		
		return pembayaran;
	}
	
	/**
	 * Validasi {@link Pembayaran} sebelum disimpan.
	 * Cek status {@link Pelanggan} dan generate {@code kodePembayaran}.
	 * @param pembayaran
	 * @return
	 * @throws NotPayableCustomerException
	 */
	private Pembayaran validateBeforePay(Pembayaran pembayaran) throws NotPayableCustomerException {
		Status status = pembayaran.getPelanggan().getStatus();
		
		if (!(status.equals(Pelanggan.Status.AKTIF)) && !(status.equals(Pelanggan.Status.GRATIS)))
			throw new NotPayableCustomerException(String.format("Gagal! Status Pelanggan adalah %s", status.toString()));

		pembayaran.generateKode();
		
		return pembayaran;
	}
	
	private void validateAfterPay(Pembayaran pembayaran) throws ApplicationException {
		Pelanggan pelanggan = pembayaran.getPelanggan();

		pelangganService.updateLastPayment(pelanggan);
		pelangganService.recountTunggakan(pelanggan);
	}
	
	@Override
	@Transactional(readOnly = false)
	public Pembayaran updatePayment(Pembayaran pembayaran) {
		return pembayaranRepository.save(pembayaran);
	}

	@Override
	@Transactional(readOnly = false)
	public Pembayaran updatePayment(Integer id, Long total) throws EntityNotExistException {
		Pembayaran pembayaran = getOne(id);
		pembayaran.setJumlahBayar(total);

		return updatePayment(pembayaran);
	}
	
	@Override
	@Transactional(readOnly = false)
	@Deprecated
	public Pembayaran save(Pembayaran pembayaran) throws ApplicationException {
		return pay(pembayaran);
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(Integer id) throws ApplicationException {
		Pembayaran pembayaran = pembayaranRepository.findOne(id);
		delete(pembayaran);
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(Pembayaran pembayaran) throws ApplicationException {
		Pelanggan pelanggan = pembayaran.getPelanggan();
		pelanggan.setPembayaranTerakhir(null);
		
		pembayaranRepository.delete(pembayaran);

		pelangganService.updateLastPayment(pelanggan);
		pelangganService.recountTunggakan(pelanggan);
	}

	@Override
	public Pembayaran getOne(int id) throws EntityNotExistException {
		return pembayaranRepository.findOne(id);
	}

	@Override
	public List<Pembayaran> get(Pegawai pegawai, Date tanggalMulai, Date tanggalAkhir) throws EntityNotExistException {
		return pembayaranRepository.findByPegawaiAndTanggalBayarBetween(pegawai, tanggalMulai, tanggalAkhir);
	}
	
	@Override
	public java.util.List<Pembayaran> get(Pelanggan pelanggan, Date tanggalMulai, Date tanggalAkhir) throws EntityNotExistException {
		return pembayaranRepository.findByPelangganAndTanggalBayarBetween(pelanggan, tanggalMulai, tanggalAkhir);
	}

	@Override
	public List<Pembayaran> get(Pelanggan pelanggan, int tahun) throws EntityNotExistException {
		return pembayaranRepository.findByPelangganAndTagihan_Tahun(pelanggan, tahun);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Pembayaran> get(Pelanggan pelanggan, Tagihan tagihanAwal, Tagihan tagihanAkhir) throws EntityNotExistException {
		int tahunAwal = tagihanAwal.getTahun();
		int tahunAkhir = tagihanAkhir.getTahun();
		Month bulanAwal = tagihanAwal.getBulan();
		Month bulanAkhir = tagihanAkhir.getBulan();
		
		List<Pembayaran> merged;
		
		if (tahunAwal == tahunAkhir) {
			merged = pembayaranRepository.findByPelangganAndTagihan_TahunAndTagihan_BulanBetween(pelanggan, tahunAwal, bulanAwal, bulanAkhir);
		} else {
			List<Pembayaran> part1 = pembayaranRepository.findByPelangganAndTagihan_TahunAndTagihan_BulanBetween(pelanggan, tahunAwal, bulanAwal, Month.DECEMBER);
			List<Pembayaran> part2 = pembayaranRepository.findByPelangganAndTagihan_TahunAndTagihan_BulanBetween(pelanggan, tahunAkhir, Month.JANUARY, bulanAkhir);
			
			merged = ListUtils.union(part1, part2);
		}
		
		return merged;
	}

	@Override
	public long count(Pegawai pegawai, Date tanggalMulai, Date tanggalAkhir) {
		return pembayaranRepository.countByPegawaiAndTanggalBayarBetween(pegawai, tanggalMulai, tanggalAkhir);
	}

	@Override
	public long count(Pelanggan pelanggan, Date tanggalMulai, Date tanggalAkhir) {
		return pembayaranRepository.countByPelangganAndTanggalBayarBetween(pelanggan, tanggalMulai, tanggalAkhir);
	}

	@Override
	public Pembayaran getLast(Pelanggan pelanggan) {
		try {
			Pembayaran pembayaran = pembayaranRepository.findFirstByPelangganOrderByIdDesc(pelanggan);
			
			if (pembayaran == null)
				return null;
			return pembayaran.copy();
			
		} catch (EmptyIdException e) { 
			return null;
		}
	}

	@Override
	public Tagihan getPayableTagihan(Pelanggan pelanggan) {
		Pembayaran pembayaran = getLast(pelanggan);

		Tagihan tagihan;
		
		if (pembayaran != null) {
			Tagihan lastTagihan = pembayaran.getTagihan();
			
			tagihan = Tagihan.copy(lastTagihan);
			tagihan.increase();
		} else {
			Date tanggalDaftar = pelanggan.getTanggalMulai();
			int tahun = DateUtil.getYear(tanggalDaftar);
			Month month = DateUtil.getMonth(tanggalDaftar);
			
			tagihan = new Tagihan(tahun, month);
		}

		return tagihan;
	}
	
	/**
	 * Class to verify {@code Pembayaran}.
	 * @author Deddy Christoper Kakunsi
	 *
	 */
	public static class Verifier {
		
		/**
		 * Set the default {@link Pembayaran} value when it is not found.
		 * @param listPembayaran
		 * @throws EmptyIdException 
		 */
		public static List<Pembayaran> createEmptyListPembayaran() {
			
			List<Pembayaran> listPembayaran = new ArrayList<>();
			
			for (int i = Month.JANUARY.getValue(); i <= Month.DECEMBER.getValue(); i++) {
				listPembayaran.add(createDefaultPembayaran(0, Month.of(i)));
			}
			
			return listPembayaran;
		}
		
		public static List<Pembayaran> verifyListPembayaran(List<Pembayaran> listPembayaran, int tahun, Pelanggan pelanggan) {
			verifyPembayaranFirst(listPembayaran);
			verifyPembayaranLast(listPembayaran);
			
			return listPembayaran;
		}

		/**
		 * Set the default {@link Pembayaran} value when it is not starts from JANUARY
		 * @param listPembayaran
		 * @throws EmptyIdException 
		 */
		private static void verifyPembayaranFirst(List<Pembayaran> listPembayaran) {
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
		 * @throws EmptyIdException 
		 */
		private static void verifyPembayaranLast(List<Pembayaran> listPembayaran) {
			Pembayaran pembayaranEntity = listPembayaran.get(listPembayaran.size() - 1);

			if (pembayaranEntity.getBulan() != Month.DECEMBER) {
				
				for (int i = pembayaranEntity.getBulan().getValue(); i < Month.DECEMBER.getValue(); i++) {
					listPembayaran.add(createDefaultPembayaran(pembayaranEntity.getTahun(), pembayaranEntity.getBulan()));
				}
				
			}
		}
		
		/**
		 * Create default {@code Pembayaran}, using empty detail.
		 * @param tahun
		 * @param bulan
		 * @return
		 */
		private static Pembayaran createDefaultPembayaran(int tahun, Month bulan) {
			Tagihan tagihan = new Tagihan(tahun, bulan);

			try {
				return new Pembayaran(0, "default", DateUtil.getNow(), new Pelanggan(), new Pegawai(), 0, tagihan);
			} catch (EmptyIdException e) {
				return null; //Never called
			}
		}
	}
	
	//Rekap
	@Override
	public List<Pelanggan> rekapBulanan(Perusahaan perusahaan, Month bulan, int tahun) throws ApplicationException {
		Date tanggalAwal = DateUtil.getDate(tahun, bulan, 1);
		Date tanggalAkhir = DateUtil.getDate(tahun, bulan, DateUtil.getLastDay(bulan, tahun));

		List<Pelanggan> listPelanggan = pelangganService.get(perusahaan, tanggalAwal, tanggalAkhir);
		return rekapHarian(listPelanggan, tanggalAwal, tanggalAkhir);
	}
	
	@Override
	public List<Pelanggan> rekapHarian(Pegawai pegawai, Date hariAwal, Date hariAkhir) throws ApplicationException {
		List<Pelanggan> listPelanggan = pelangganService.get(pegawai, hariAwal, hariAkhir);
		return rekapHarian(listPelanggan, hariAwal, hariAkhir);
	}
	
	private List<Pelanggan> rekapHarian(List<Pelanggan> list, Date hariAwal, Date hariAkhir) throws EmptyIdException {
		for (Pelanggan pelanggan : list) {
			
			List<Pembayaran> listPembayaran;
			
			try {
				listPembayaran = get(pelanggan, hariAwal, hariAkhir);
			} catch (EntityNotExistException e) {
				listPembayaran = new ArrayList<>();
			}
			
			pelanggan.setListPembayaran(listPembayaran);
			
		}
		
		return list;
	}

	@Override
	public List<Pelanggan> rekapTahunan(Perusahaan perusahaan, int tahun) throws ApplicationException {
		List<Pelanggan> listPelanggan = pelangganService.get(perusahaan, Status.AKTIF);

		return rekapTahunan(listPelanggan, tahun);
	}
	
	private List<Pelanggan> rekapTahunan(List<Pelanggan> list, int tahun) {
 		for (Pelanggan pelanggan : list) {
 			
 			List<Pembayaran> listPembayaran;
 			
			try {
				
				listPembayaran = get(pelanggan, tahun);
	 			PembayaranServiceImpl.Verifier.verifyListPembayaran(listPembayaran, tahun, pelanggan);
	 			
			} catch (EntityNotExistException e) {
				listPembayaran = PembayaranServiceImpl.Verifier.createEmptyListPembayaran();
			}
			
 			pelanggan.setListPembayaran(listPembayaran); 
 			
 		} 
 		 
 		return list; 
	}
}
