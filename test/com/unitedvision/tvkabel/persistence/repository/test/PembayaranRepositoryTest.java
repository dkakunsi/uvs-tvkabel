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
import org.springframework.transaction.annotation.Transactional;

import com.unitedvision.tvkabel.persistence.SpringDataJpaConfig;
import com.unitedvision.tvkabel.persistence.entity.Pegawai;
import com.unitedvision.tvkabel.persistence.entity.Pelanggan;
import com.unitedvision.tvkabel.persistence.entity.Pembayaran;
import com.unitedvision.tvkabel.persistence.entity.Perusahaan;
import com.unitedvision.tvkabel.persistence.entity.Pembayaran.Tagihan;
import com.unitedvision.tvkabel.persistence.repository.PegawaiRepository;
import com.unitedvision.tvkabel.persistence.repository.PelangganRepository;
import com.unitedvision.tvkabel.persistence.repository.PembayaranRepository;
import com.unitedvision.tvkabel.persistence.repository.PerusahaanRepository;
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
		Perusahaan perusahaan = perusahaanRepository.getOne(17);
		Date tanggalAwal = DateUtil.getFirstDate();
		Date tanggalAkhir = DateUtil.getLastDate();
		
		long hasil = pembayaranRepository.countPemasukanBulanBerjalan(perusahaan, tanggalAwal, tanggalAkhir);

		assertNotEquals(0, hasil);
	}

	@Test
	public void testCountPemasukanBulanBerjalanWhenNoData() {
		Perusahaan perusahaan = perusahaanRepository.getOne(17);
		Date tanggalAwal = DateUtil.getDate("1/1/2020");
		Date tanggalAkhir = DateUtil.getDate("1/1/2025");
		
		long hasil = pembayaranRepository.countPemasukanBulanBerjalan(perusahaan, tanggalAwal, tanggalAkhir);

		assertEquals(0, hasil);
	}

	@Test
	public void testFindByPelangganAndTagihan_TahunAndTagihan_BulanBetweenDifferentYear() {
		Pelanggan pelanggan = pelangganRepository.findOne(55);
		int tahun1 = 2013;
		Month bulanAwal1 = Month.NOVEMBER;
		Month bulanAkhir1 = Month.DECEMBER;
		
		int tahun2 = 2014;
		Month bulanAwal2 = Month.JANUARY;
		Month bulanAkhir2 = Month.MARCH;

		List<Pembayaran> listPart1 = pembayaranRepository.findByPelangganAndTagihan_TahunAndTagihan_BulanBetween(pelanggan, tahun1, bulanAwal1, bulanAkhir1);
		assertEquals(2, listPart1.size());

		List<Pembayaran> listPart2 = pembayaranRepository.findByPelangganAndTagihan_TahunAndTagihan_BulanBetween(pelanggan, tahun2, bulanAwal2, bulanAkhir2);
		assertEquals(3, listPart2.size());
		
		@SuppressWarnings("unchecked")
		List<Pembayaran> listJoined = ListUtils.union(listPart1, listPart2);
		Iterator<Pembayaran> iterator = listJoined.iterator();
		assertEquals(new Tagihan(2013, Month.NOVEMBER), iterator.next().getTagihan());
		assertTrue(iterator.hasNext());
		assertEquals(new Tagihan(2013, Month.DECEMBER), iterator.next().getTagihan());
		assertTrue(iterator.hasNext());
		assertEquals(new Tagihan(2014, Month.JANUARY), iterator.next().getTagihan());
		assertTrue(iterator.hasNext());
		assertEquals(new Tagihan(2014, Month.FEBRUARY), iterator.next().getTagihan());
		assertTrue(iterator.hasNext());
		assertEquals(new Tagihan(2014, Month.MARCH), iterator.next().getTagihan());
		assertFalse(iterator.hasNext());
	}
	
	@Test
	public void testFindByPelangganAndTagihan_TahunAndTagihan_BulanBetweenSameYear() {
		Pelanggan pelanggan = pelangganRepository.findOne(55);
		int tahun = 2014;
		Month bulanAwal = Month.JULY;
		Month bulanAkhir = Month.NOVEMBER;
		
		List<Pembayaran> list = pembayaranRepository.findByPelangganAndTagihan_TahunAndTagihan_BulanBetween(pelanggan, tahun, bulanAwal, bulanAkhir);
		
		assertEquals(4, list.size());
	}
	
	@Test
	public void testFindByPelangganAndTagihan_Tahun() {
		Pelanggan pelanggan = pelangganRepository.findOne(35);
		int tahun = 2014;
		
		List<Pembayaran> list = pembayaranRepository.findByPelangganAndTagihan_Tahun(pelanggan, tahun);
		
		assertNotEquals(0, list.size());
	}
	
	@Test
	public void testFindByPegawai_PerusahaanAndTagihan() {
		Perusahaan perusahaan = perusahaanRepository.findOne(17);
		Tagihan tagihan = new Tagihan(2014, Month.DECEMBER);
		
		List<Pembayaran> list = pembayaranRepository.findByPegawai_PerusahaanAndTagihan(perusahaan, tagihan);
		
		assertNotEquals(0, list.size());
	}
	
	@Test
	public void testFindByPegawai_PerusahaanAndTanggalBayarBetween() {
		Perusahaan perusahaan = perusahaanRepository.findOne(17);
		Date tanggalAwal = DateUtil.getFirstDate();
		Date tanggalAkhir = DateUtil.getLastDate();
		
		List<Pembayaran> list = pembayaranRepository.findByPegawai_PerusahaanAndTanggalBayarBetween(perusahaan, tanggalAwal, tanggalAkhir);
		
		assertNotEquals(0, list.size());
	}
	
	@Test
	public void testCountByPegawai_PerusahaanAndTanggalBayarBetween() {
		Perusahaan perusahaan = perusahaanRepository.findOne(17);
		Date tanggalAwal = DateUtil.getFirstDate();
		Date tanggalAkhir = DateUtil.getLastDate();

		long hasil = pembayaranRepository.countByPegawai_PerusahaanAndTanggalBayarBetween(perusahaan, tanggalAwal, tanggalAkhir);
		
		assertNotEquals(0, hasil);
	}
	
	@Test
	public void testCountByPegawaiAndTanggalBayarBetween() {
		Pegawai pegawai = pegawaiRepository.findOne(14);
		Date tanggalAwal = DateUtil.getFirstDate();
		Date tanggalAkhir = DateUtil.getLastDate();

		long hasil = pembayaranRepository.countByPegawaiAndTanggalBayarBetween(pegawai, tanggalAwal, tanggalAkhir);
		
		assertNotEquals(0, hasil);
	}
	
	@Test
	public void testCountByPelangganAndTanggalBayarBetween() {
		Pelanggan pelanggan = pelangganRepository.findOne(82);
		Date tanggalAwal = DateUtil.getDate("1/1/2014");
		Date tanggalAkhir = DateUtil.getDate("1/1/2015");

		long hasil = pembayaranRepository.countByPelangganAndTanggalBayarBetween(pelanggan, tanggalAwal, tanggalAkhir);
		
		assertNotEquals(0, hasil);
	}
	
	@Test
	public void testFindFirstByPelangganOrderByIdDesc() {
		Pelanggan pelanggan = pelangganRepository.findOne(69);

		Pembayaran pembayaran = pembayaranRepository.findFirstByPelangganOrderByIdDesc(pelanggan);
		
		assertEquals(2014, pembayaran.getTahun());
		assertEquals(Month.DECEMBER, pembayaran.getBulan());
	}
}
