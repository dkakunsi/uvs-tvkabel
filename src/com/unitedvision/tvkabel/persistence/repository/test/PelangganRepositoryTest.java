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

import com.unitedvision.tvkabel.core.domain.Pelanggan;
import com.unitedvision.tvkabel.core.domain.Pelanggan.Status;
import com.unitedvision.tvkabel.exception.EntityNotExistException;
import com.unitedvision.tvkabel.persistence.SpringDataJpaConfig;
import com.unitedvision.tvkabel.persistence.entity.KelurahanEntity;
import com.unitedvision.tvkabel.persistence.entity.PelangganEntity;
import com.unitedvision.tvkabel.persistence.entity.PerusahaanEntity;
import com.unitedvision.tvkabel.persistence.repository.KelurahanRepository;
import com.unitedvision.tvkabel.persistence.repository.PegawaiRepository;
import com.unitedvision.tvkabel.persistence.repository.PelangganRepository;
import com.unitedvision.tvkabel.persistence.repository.PerusahaanRepository;
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
		PerusahaanEntity perusahaanEntity = perusahaanRepository.getOne(17);
		long hasil = pelangganRepository.sumarizeEstimasiPemasukanBulanan(perusahaanEntity, Status.AKTIF);
		
		assertNotEquals(0, hasil);
	}

	@Test
	public void testCountEstimasiWhenNoData() {
		PerusahaanEntity perusahaanEntity = perusahaanRepository.getOne(17);
		long hasil = pelangganRepository.sumarizeEstimasiPemasukanBulanan(perusahaanEntity, Status.AKTIF);
		
		assertEquals(0, hasil);
	}

	@Test
	public void countAkumulasi() {
		PerusahaanEntity perusahaanEntity = perusahaanRepository.getOne(17);
		long hasil = pelangganRepository.summarizeTotalAkumulasiTunggakan(perusahaanEntity, Pelanggan.Status.AKTIF);

		assertNotEquals(0, hasil);
	}

	@Test
	public void countAkumulasiWhenNoData() {
		PerusahaanEntity perusahaanEntity = perusahaanRepository.getOne(17);
		long hasil = pelangganRepository.summarizeTotalAkumulasiTunggakan(perusahaanEntity, Pelanggan.Status.AKTIF);

		assertEquals(0, hasil);
	}
	
	@Test
	public void testGetByTanggalCont() {
		int tanggal = 12;
		
		List<PelangganEntity> listPelanggan = pelangganRepository.findByTanggalMulai(Status.AKTIF, tanggal);
		
		assertNotEquals(0, listPelanggan.size());
	}
	
	@Test
	public void testFindByPembayaran() {
		Date tanggalBayar = DateUtil.getDate(2014, 11, 7);
		String tanggalBayarStr = DateUtil.toDatabaseString(tanggalBayar, "-");
		
		List<PelangganEntity> list = pelangganRepository.findByPembayaran(14, tanggalBayarStr);

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
		PerusahaanEntity perusahaanEntity = perusahaanRepository.findOne(17); //17 is Global Vision
		Status status = Status.AKTIF;
		KelurahanEntity kelurahanEntity = kelurahanRepository.findByNama("Winangun 1");
		int lingkungan = 1;
		
		List<PelangganEntity> list = pelangganRepository.findByPerusahaanAndStatusAndKelurahanAndAlamat_LingkunganOrderByKodeAsc(perusahaanEntity, status, kelurahanEntity, lingkungan);
		
		assertNotEquals(0, list.size());
	}

	@Test
	public void testCountByPerusahaanAndStatusAndKelurahanAndAlamat_Lingkungan() throws EntityNotExistException {
		PerusahaanEntity perusahaanEntity = perusahaanRepository.findOne(17); //17 is Global Vision
		Status status = Status.AKTIF;
		KelurahanEntity kelurahanEntity = kelurahanRepository.findByNama("Winangun 1");
		int lingkungan = 1;
		
		long hasil = pelangganRepository.countByPerusahaanAndStatusAndKelurahanAndAlamat_Lingkungan(perusahaanEntity, status, kelurahanEntity, lingkungan);
		
		assertNotEquals(0, hasil);
	}

	@Test
	public void testFindByPerusahaanAndStatusAndDetail_Tunggakan() {
		PerusahaanEntity perusahaanEntity = perusahaanRepository.findOne(17); //17 is Global Vision
		Status status = Status.AKTIF;
		int tunggakan = 3;
		
		List<PelangganEntity> list = pelangganRepository.findByPerusahaanAndStatusAndDetail_TunggakanOrderByKodeAsc(perusahaanEntity, status, tunggakan);
		
		assertNotEquals(0, list.size());
	}
	
	@Test
	public void testCountByPerusahaanAndStatusAndDetail_Tunggakan() {
		PerusahaanEntity perusahaanEntity = perusahaanRepository.findOne(17); //17 is Global Vision
		Status status = Status.AKTIF;
		int tunggakan = 1;
		
		long hasil = pelangganRepository.countByPerusahaanAndStatusAndDetail_Tunggakan(perusahaanEntity, status, tunggakan);
		
		assertNotEquals(138, hasil);
	}
	
	@Test
	public void testCountByPerusahaanAndStatusAndDetail_TunggakanMoreThan() {
		PerusahaanEntity perusahaanEntity = perusahaanRepository.findOne(17); //17 is Global Vision
		Status status = Status.AKTIF;
		int tunggakan = 1;
		
		long hasil = pelangganRepository.countByPerusahaanAndStatusAndDetail_TunggakanGreaterThan(perusahaanEntity, status, tunggakan);
		
		assertNotEquals(655, hasil);
	}
	
	@Test
	public void testCountByPerusahaanAndStatusAndDetail_TunggakanLessThan() {
		PerusahaanEntity perusahaanEntity = perusahaanRepository.findOne(17); //17 is Global Vision
		Status status = Status.AKTIF;
		int tunggakan = 1;
		
		long hasil = pelangganRepository.countByPerusahaanAndStatusAndDetail_TunggakanLessThan(perusahaanEntity, status, tunggakan);
		
		assertNotEquals(28, hasil);
	}
	
	@Test
	public void testFindByPerusahaanAndStatusAndKodeContaining() {
		PerusahaanEntity perusahaanEntity = perusahaanRepository.findOne(17); //17 is Global Vision
		Status status = Status.AKTIF;
		String kode = "PLG000";
		PageRequest page = new PageRequest(0, 12);
		
		List<PelangganEntity> list = pelangganRepository.findByPerusahaanAndStatusAndKodeContainingOrderByKodeAsc(perusahaanEntity, status, kode, page);
		
		assertNotEquals(0, list.size());
	}
	
	@Test
	@Ignore
	public void testFindByPembayaranPageable() {
		//Date tanggalBayar = DateUtil.getDate(2014, 11, 7);
		//String tanggalBayarStr = DateUtil.toDatabaseString(tanggalBayar, "-");
		//PageRequest page = new PageRequest(PageSizeUtil.getPageNumber(12), PageSizeUtil.DATA_NUMBER);
		
		
		//List<PelangganEntity> list = pelangganRepository.findByPembayaran(14, tanggalBayarStr, page);

		//assertEquals(12, list.size());
	}
}
