package com.unitedvision.tvkabel.persistence.repository.test;

import static org.junit.Assert.*;

import java.time.Month;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.ListUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.unitedvision.tvkabel.persistence.entity.PegawaiEntity;
import com.unitedvision.tvkabel.persistence.entity.PelangganEntity;
import com.unitedvision.tvkabel.persistence.entity.PembayaranEntity;
import com.unitedvision.tvkabel.persistence.entity.PembayaranEntity.TagihanValue;
import com.unitedvision.tvkabel.persistence.entity.PerusahaanEntity;
import com.unitedvision.tvkabel.persistence.repository.PegawaiRepository;
import com.unitedvision.tvkabel.persistence.repository.PelangganRepository;
import com.unitedvision.tvkabel.persistence.repository.PerusahaanRepository;

import org.springframework.transaction.annotation.Transactional;

import com.unitedvision.tvkabel.persistence.repository.PembayaranRepository;
import com.unitedvision.tvkabel.persistence.SpringDataJpaConfig;
import com.unitedvision.tvkabel.util.DateUtil;

@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration (classes = {SpringDataJpaConfig.class})
@Transactional
@TransactionConfiguration (defaultRollback = true)
public class PembayaranRepositoryTest {
	@Autowired
	private PembayaranRepository pembayaranRepository;
	@Autowired
	private PerusahaanRepository perusahaanRepository;
	@Autowired
	private PelangganRepository pelangganRepository;
	@Autowired
	private PegawaiRepository pegawaiRepository;
	
	@Test
	public void testCountPemasukanBulanBerjalan() {
		PerusahaanEntity perusahaanEntity = perusahaanRepository.getOne(17);
		Date tanggalAwal = DateUtil.getFirstDate();
		Date tanggalAkhir = DateUtil.getLastDate();
		
		long hasil = pembayaranRepository.countPemasukanBulanBerjalan(perusahaanEntity, tanggalAwal, tanggalAkhir);

		assertNotEquals(0, hasil);
	}

	@Test
	public void testFindByPelangganAndTagihan_TahunAndTagihan_BulanBetweenDifferentYear() {
		PelangganEntity pelangganEntity = pelangganRepository.findOne(55);
		int tahun1 = 2013;
		Month bulanAwal1 = Month.NOVEMBER;
		Month bulanAkhir1 = Month.DECEMBER;
		
		int tahun2 = 2014;
		Month bulanAwal2 = Month.JANUARY;
		Month bulanAkhir2 = Month.MARCH;

		List<PembayaranEntity> listPart1 = pembayaranRepository.findByPelangganAndTagihan_TahunAndTagihan_BulanBetween(pelangganEntity, tahun1, bulanAwal1, bulanAkhir1);
		assertEquals(2, listPart1.size());

		List<PembayaranEntity> listPart2 = pembayaranRepository.findByPelangganAndTagihan_TahunAndTagihan_BulanBetween(pelangganEntity, tahun2, bulanAwal2, bulanAkhir2);
		assertEquals(3, listPart2.size());
		
		@SuppressWarnings("unchecked")
		List<PembayaranEntity> listJoined = ListUtils.union(listPart1, listPart2);
		Iterator<PembayaranEntity> iterator = listJoined.iterator();
		assertEquals(new TagihanValue(2013, Month.NOVEMBER), iterator.next().getTagihan());
		assertTrue(iterator.hasNext());
		assertEquals(new TagihanValue(2013, Month.DECEMBER), iterator.next().getTagihan());
		assertTrue(iterator.hasNext());
		assertEquals(new TagihanValue(2014, Month.JANUARY), iterator.next().getTagihan());
		assertTrue(iterator.hasNext());
		assertEquals(new TagihanValue(2014, Month.FEBRUARY), iterator.next().getTagihan());
		assertTrue(iterator.hasNext());
		assertEquals(new TagihanValue(2014, Month.MARCH), iterator.next().getTagihan());
		assertFalse(iterator.hasNext());
	}
	
	@Test
	public void testFindByPelangganAndTagihan_TahunAndTagihan_BulanBetweenSameYear() {
		PelangganEntity pelangganEntity = pelangganRepository.findOne(55);
		int tahun = 2014;
		Month bulanAwal = Month.JULY;
		Month bulanAkhir = Month.NOVEMBER;
		
		List<PembayaranEntity> list = pembayaranRepository.findByPelangganAndTagihan_TahunAndTagihan_BulanBetween(pelangganEntity, tahun, bulanAwal, bulanAkhir);
		
		assertEquals(4, list.size());
	}
	
	@Test
	public void testFindByPelangganAndTagihan_Tahun() {
		PelangganEntity pelangganEntity = pelangganRepository.findOne(35);
		int tahun = 2014;
		
		List<PembayaranEntity> list = pembayaranRepository.findByPelangganAndTagihan_Tahun(pelangganEntity, tahun);
		
		assertNotEquals(0, list.size());
	}
	
	@Test
	public void testFindByPegawai_PerusahaanAndTagihan() {
		PerusahaanEntity perusahaanEntity = perusahaanRepository.findOne(17);
		TagihanValue tagihanValue = new TagihanValue(2014, Month.DECEMBER);
		
		List<PembayaranEntity> list = pembayaranRepository.findByPegawai_PerusahaanAndTagihan(perusahaanEntity, tagihanValue);
		
		assertNotEquals(0, list.size());
	}
	
	@Test
	public void testFindByPegawai_PerusahaanAndTanggalBayarBetween() {
		PerusahaanEntity perusahaanEntity = perusahaanRepository.findOne(17);
		Date tanggalAwal = DateUtil.getFirstDate();
		Date tanggalAkhir = DateUtil.getLastDate();
		
		List<PembayaranEntity> list = pembayaranRepository.findByPegawai_PerusahaanAndTanggalBayarBetween(perusahaanEntity, tanggalAwal, tanggalAkhir);
		
		assertNotEquals(0, list.size());
	}
	
	@Test
	public void testCountByPegawai_PerusahaanAndTanggalBayarBetween() {
		PerusahaanEntity perusahaanEntity = perusahaanRepository.findOne(17);
		Date tanggalAwal = DateUtil.getFirstDate();
		Date tanggalAkhir = DateUtil.getLastDate();

		long hasil = pembayaranRepository.countByPegawai_PerusahaanAndTanggalBayarBetween(perusahaanEntity, tanggalAwal, tanggalAkhir);
		
		assertNotEquals(0, hasil);
	}
	
	@Test
	public void testCountByPegawaiAndTanggalBayarBetween() {
		PegawaiEntity pegawaiEntity = pegawaiRepository.findOne(14);
		Date tanggalAwal = DateUtil.getFirstDate();
		Date tanggalAkhir = DateUtil.getLastDate();

		long hasil = pembayaranRepository.countByPegawaiAndTanggalBayarBetween(pegawaiEntity, tanggalAwal, tanggalAkhir);
		
		assertNotEquals(0, hasil);
	}
	
	@Test
	public void testCountByPelangganAndTanggalBayarBetween() {
		PelangganEntity pelangganEntity = pelangganRepository.findOne(82);
		Date tanggalAwal = DateUtil.getFirstDate();
		Date tanggalAkhir = DateUtil.getLastDate();

		long hasil = pembayaranRepository.countByPelangganAndTanggalBayarBetween(pelangganEntity, tanggalAwal, tanggalAkhir);
		
		assertNotEquals(0, hasil);
	}
	
	@Test
	public void testCountByPegawai_PerusahaanAndTagihan() {
		PerusahaanEntity perusahaanEntity = perusahaanRepository.findOne(17);
		TagihanValue tagihanValue = new TagihanValue(2014, Month.NOVEMBER);
		
		long hasil = pembayaranRepository.countByPegawai_PerusahaanAndTagihan(perusahaanEntity, tagihanValue);
		
		assertNotEquals(0, hasil);
	}
	
	@Test
	public void testFindFirstByPelangganOrderByIdDesc() {
		PelangganEntity pelangganEntity = pelangganRepository.findOne(69);

		PembayaranEntity pembayaranEntity = pembayaranRepository.findFirstByPelangganOrderByIdDesc(pelangganEntity);
		
		assertEquals(2014, pembayaranEntity.getTahun());
		assertEquals(Month.DECEMBER, pembayaranEntity.getBulan());
	}
}
