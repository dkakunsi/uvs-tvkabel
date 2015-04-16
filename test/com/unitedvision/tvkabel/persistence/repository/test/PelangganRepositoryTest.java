package com.unitedvision.tvkabel.persistence.repository.test;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.unitedvision.tvkabel.configuration.ApplicationConfig;
import com.unitedvision.tvkabel.entity.Kelurahan;
import com.unitedvision.tvkabel.entity.Pelanggan;
import com.unitedvision.tvkabel.entity.Perusahaan;
import com.unitedvision.tvkabel.entity.Pelanggan.Status;
import com.unitedvision.tvkabel.exception.EntityNotExistException;
import com.unitedvision.tvkabel.repository.KelurahanRepository;
import com.unitedvision.tvkabel.repository.PegawaiRepository;
import com.unitedvision.tvkabel.repository.PelangganRepository;
import com.unitedvision.tvkabel.repository.PerusahaanRepository;
import com.unitedvision.tvkabel.util.DateUtil;

@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration (classes = {ApplicationConfig.class})
@Transactional
@TransactionConfiguration (defaultRollback = true)
public class PelangganRepositoryTest {
	@Autowired
	private PelangganRepository pelangganRepository;
	@Autowired
	private PerusahaanRepository perusahaanRepository;
	@Autowired
	private PegawaiRepository pegawaiRepository;
	@Autowired
	private KelurahanRepository kelurahanRepository;
	
	@Test
	public void test_Get() {
		Pelanggan pelanggan = pelangganRepository.getOne(35);
		Perusahaan perusahaan = pelanggan.getPerusahaan();
		
		assertNotNull(perusahaan.getNamaPT());
		assertNotEquals("", perusahaan.getNamaPT());
	}
	
	@Test
	public void testCountEstimasi() {
		Perusahaan perusahaan = perusahaanRepository.getOne(17);
		long hasil = pelangganRepository.sumarizeEstimasiPemasukanBulanan(perusahaan, Status.AKTIF);
		
		assertNotEquals(0, hasil);
	}

	@Test
	@Ignore
	public void testCountEstimasiWhenNoData() {
		Perusahaan perusahaan = perusahaanRepository.getOne(17);
		long hasil = pelangganRepository.sumarizeEstimasiPemasukanBulanan(perusahaan, Status.AKTIF);
		
		assertEquals(0, hasil);
	}

	@Test
	public void countAkumulasi() {
		Perusahaan perusahaan = perusahaanRepository.getOne(17);
		long hasil = pelangganRepository.summarizeTotalAkumulasiTunggakan(perusahaan, Status.AKTIF);

		assertNotEquals(0, hasil);
	}

	@Test
	@Ignore
	public void countAkumulasiWhenNoData() {
		Perusahaan perusahaan = perusahaanRepository.getOne(17);
		long hasil = pelangganRepository.summarizeTotalAkumulasiTunggakan(perusahaan, Status.AKTIF);

		assertEquals(0, hasil);
	}
	
	@Test
	public void testGetByTanggalCont() {
		String tanggal = "1";
		
		List<Pelanggan> listPelanggan = pelangganRepository.findByTanggalMulai(Status.AKTIF.ordinal(), tanggal);
		
		assertNotEquals(0, listPelanggan.size());
	}
	
	@Test
	public void testFindByPembayaran() {
		Date tanggalBayarAwal = DateUtil.getDate(2014, 12, 1);
		String tanggalBayarAwalStr = DateUtil.toDatabaseString(tanggalBayarAwal, "-");
		Date tanggalBayarAkhir = DateUtil.getDate(2014, 12, 31);
		String tanggalBayarAkhirStr = DateUtil.toDatabaseString(tanggalBayarAkhir, "-");
		
		List<Pelanggan> list = pelangganRepository.findByPembayaran(14, tanggalBayarAwalStr, tanggalBayarAkhirStr);

		assertEquals(164, list.size());
	}

	@Test
	public void testFindByPerusahaanAndStatusAndKelurahanAndAlamat_Lingkungan() throws EntityNotExistException {
		Perusahaan perusahaan = perusahaanRepository.findOne(17); //17 is Global Vision
		Status status = Status.AKTIF;
		Kelurahan kelurahan = kelurahanRepository.findByNama("Winangun 1");
		int lingkungan = 1;
		
		List<Pelanggan> list = pelangganRepository.findByPerusahaanAndStatusAndKelurahanAndAlamat_LingkunganOrderByKodeAsc(perusahaan, status, kelurahan, lingkungan);
		
		assertNotEquals(0, list.size());
	}

	@Test
	public void testFindByPerusahaanAndStatusAndKelurahanAndAlamat_LingkunganWhenNoData() throws EntityNotExistException {
		Perusahaan perusahaan = perusahaanRepository.findOne(17); //17 is Global Vision
		Status status = Status.BERHENTI;
		Kelurahan kelurahan = kelurahanRepository.findByNama("Winangun 1");
		int lingkungan = 1;
		
		List<Pelanggan> list = pelangganRepository.findByPerusahaanAndStatusAndKelurahanAndAlamat_LingkunganOrderByKodeAsc(perusahaan, status, kelurahan, lingkungan);

		assertNotNull(list);
		assertEquals(0, list.size());
	}

	@Test
	public void testFindByPerusahaanAndStatusAndDetail_Tunggakan() throws EntityNotExistException {
		Perusahaan perusahaan = perusahaanRepository.findOne(17); //17 is Global Vision
		Status status = Status.AKTIF;
		int tunggakan = 3;
		
		List<Pelanggan> list = pelangganRepository.findByPerusahaanAndStatusAndDetail_TunggakanOrderByKodeAsc(perusahaan, status, tunggakan);
		
		assertNotEquals(0, list.size());
	}
	
	@Test
	public void testCountByPerusahaanAndStatusAndDetail_Tunggakan() {
		Perusahaan perusahaan = perusahaanRepository.findOne(17); //17 is Global Vision
		Status status = Status.AKTIF;
		int tunggakan = 1;
		
		long hasil = pelangganRepository.countByPerusahaanAndStatusAndDetail_Tunggakan(perusahaan, status, tunggakan);
		
		assertNotEquals(138, hasil);
	}
	
	@Test
	public void testCountByPerusahaanAndStatusAndDetail_TunggakanMoreThan() {
		Perusahaan perusahaan = perusahaanRepository.findOne(17); //17 is Global Vision
		Status status = Status.AKTIF;
		int tunggakan = 1;
		
		long hasil = pelangganRepository.countByPerusahaanAndStatusAndDetail_TunggakanGreaterThan(perusahaan, status, tunggakan);
		
		assertNotEquals(655, hasil);
	}
	
	@Test
	public void testCountByPerusahaanAndStatusAndDetail_TunggakanLessThan() {
		Perusahaan perusahaan = perusahaanRepository.findOne(17); //17 is Global Vision
		Status status = Status.AKTIF;
		int tunggakan = 1;
		
		long hasil = pelangganRepository.countByPerusahaanAndStatusAndDetail_TunggakanLessThan(perusahaan, status, tunggakan);
		
		assertNotEquals(28, hasil);
	}
	
	@Test
	public void testFindByPerusahaanAndStatusAndKodeContaining() throws EntityNotExistException {
		Perusahaan perusahaan = perusahaanRepository.findOne(17); //17 is Global Vision
		Status status = Status.AKTIF;
		String kode = "WS01";
		
		List<Pelanggan> list = pelangganRepository.findByPerusahaanAndStatusAndKodeContainingOrderByKodeAsc(perusahaan, status, kode);
		
		assertNotEquals(0, list.size());
	}
	
	@Test
	@Ignore
	public void testFindByPembayaranPageable() {
		//Date tanggalBayar = DateUtil.getDate(2014, 11, 7);
		//String tanggalBayarStr = DateUtil.toDatabaseString(tanggalBayar, "-");
		//PageRequest page = new PageRequest(PageSizeUtil.getPageNumber(12), PageSizeUtil.DATA_NUMBER);
		
		
		//List<Pelanggan> list = pelangganRepository.findByPembayaran(14, tanggalBayarStr, page);

		//assertEquals(12, list.size());
	}
	
	@Test
	public void testFindByPerusahaanAndStatusOrderByAlamat() {
		List<Pelanggan> list = pelangganRepository.findByPerusahaanAndStatusOrderByAlamat(17, Status.AKTIF);

		int[] limit = {117, 2, 81, 84, 22, 48, 85, 46, 1, 5, 2, 8, 11, 7, 7, 3, 9, 6, 9};
		int i = 0;
		String kelurahan = "";
		int lingkungan = 0, counter = 0;
		boolean isNew = true;
		boolean next = false;
		for (Pelanggan en : list) {
			if (!kelurahan.equals(en.getNamaKelurahan()) || lingkungan != en.getLingkungan()) {
				kelurahan = en.getNamaKelurahan();
				lingkungan = en.getLingkungan();
				
				if (isNew == false)
					next = true;
			}

			if (next == true) {
				//assertEquals(limit[counter], i);
				i = 0;
				next = false;
				counter++;
			}

			i++;
			isNew = false;
			
			System.out.println(String.format("%s, %s, %d", en.getKode(), en.getNamaKelurahan(), en.getLingkungan()));
		}
		assertEquals(limit[counter], i);
	}
	
	@Test
	public void testGetByTanggalMulai_Like() {
		String tanggal = "1";
		tanggal = DateUtil.getDayString(tanggal);
		
		List<Pelanggan> listPelanggan = pelangganRepository.findByTanggalMulai(Status.AKTIF.ordinal(), tanggal);
		
		assertNotNull(listPelanggan);
		assertNotEquals(0, listPelanggan.size());
		assertEquals(1, listPelanggan.size());
	}
}
