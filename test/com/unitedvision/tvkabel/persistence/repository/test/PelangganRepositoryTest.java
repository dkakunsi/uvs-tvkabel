package com.unitedvision.tvkabel.persistence.repository.test;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.unitedvision.tvkabel.configuration.persistence.SpringDataJpaConfig;
import com.unitedvision.tvkabel.domain.Kelurahan;
import com.unitedvision.tvkabel.domain.Pelanggan;
import com.unitedvision.tvkabel.domain.Pelanggan.Status;
import com.unitedvision.tvkabel.domain.persistence.repository.KelurahanRepository;
import com.unitedvision.tvkabel.domain.persistence.repository.PegawaiRepository;
import com.unitedvision.tvkabel.domain.persistence.repository.PelangganRepository;
import com.unitedvision.tvkabel.domain.persistence.repository.PerusahaanRepository;
import com.unitedvision.tvkabel.domain.Perusahaan;
import com.unitedvision.tvkabel.exception.EntityNotExistException;
import com.unitedvision.tvkabel.util.DateUtil;

@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration (classes = {SpringDataJpaConfig.class})
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
	public void testCountEstimasi() {
		Perusahaan perusahaan = perusahaanRepository.getOne(17);
		long hasil = pelangganRepository.sumarizeEstimasiPemasukanBulanan(perusahaan, Status.AKTIF);
		
		assertNotEquals(0, hasil);
	}

	@Test
	public void testCountEstimasiWhenNoData() {
		Perusahaan perusahaan = perusahaanRepository.getOne(17);
		long hasil = pelangganRepository.sumarizeEstimasiPemasukanBulanan(perusahaan, Status.AKTIF);
		
		assertEquals(0, hasil);
	}

	@Test
	public void countAkumulasi() {
		Perusahaan perusahaan = perusahaanRepository.getOne(17);
		long hasil = pelangganRepository.summarizeTotalAkumulasiTunggakan(perusahaan, Pelanggan.Status.AKTIF);

		assertNotEquals(0, hasil);
	}

	@Test
	public void countAkumulasiWhenNoData() {
		Perusahaan perusahaan = perusahaanRepository.getOne(17);
		long hasil = pelangganRepository.summarizeTotalAkumulasiTunggakan(perusahaan, Pelanggan.Status.AKTIF);

		assertEquals(0, hasil);
	}
	
	@Test
	public void testGetByTanggalCont() {
		int tanggal = 12;
		
		List<Pelanggan> listPelanggan = pelangganRepository.findByTanggalMulai(Status.AKTIF, tanggal);
		
		assertNotEquals(0, listPelanggan.size());
	}
	
	@Test
	public void testFindByPembayaran() {
		Date tanggalBayar = DateUtil.getDate(2014, 11, 7);
		String tanggalBayarStr = DateUtil.toDatabaseString(tanggalBayar, "-");
		
		List<Pelanggan> list = pelangganRepository.findByPembayaran(14, tanggalBayarStr);

		assertEquals(113, list.size());
	}

	@Test
	public void testCountByPembayaran() {
		Date tanggalBayar = DateUtil.getDate(2014, 11, 7);
		String tanggalBayarStr = DateUtil.toDatabaseString(tanggalBayar, "-");
		
		long hasil = pelangganRepository.countByPembayaran(14, tanggalBayarStr);

		assertEquals(113, hasil);
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
	public void testCountByPerusahaanAndStatusAndKelurahanAndAlamat_Lingkungan() throws EntityNotExistException {
		Perusahaan perusahaan = perusahaanRepository.findOne(17); //17 is Global Vision
		Status status = Status.AKTIF;
		Kelurahan kelurahan = kelurahanRepository.findByNama("Winangun 1");
		int lingkungan = 1;
		
		long hasil = pelangganRepository.countByPerusahaanAndStatusAndKelurahanAndAlamat_Lingkungan(perusahaan, status, kelurahan, lingkungan);
		
		assertNotEquals(0, hasil);
	}

	@Test
	public void testFindByPerusahaanAndStatusAndDetail_Tunggakan() {
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
	public void testFindByPerusahaanAndStatusAndKodeContaining() {
		Perusahaan perusahaan = perusahaanRepository.findOne(17); //17 is Global Vision
		Status status = Status.AKTIF;
		String kode = "PLG000";
		PageRequest page = new PageRequest(0, 12);
		
		List<Pelanggan> list = pelangganRepository.findByPerusahaanAndStatusAndKodeContainingOrderByKodeAsc(perusahaan, status, kode, page);
		
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
}
