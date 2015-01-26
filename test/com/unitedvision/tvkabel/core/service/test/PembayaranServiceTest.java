package com.unitedvision.tvkabel.core.service.test;

import static org.junit.Assert.*;

import java.time.Month;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.unitedvision.tvkabel.core.service.PegawaiService;
import com.unitedvision.tvkabel.core.service.PelangganService;
import com.unitedvision.tvkabel.core.service.PembayaranService;
import com.unitedvision.tvkabel.core.service.PerusahaanService;
import com.unitedvision.tvkabel.exception.DataDuplicationException;
import com.unitedvision.tvkabel.exception.EmptyIdException;
import com.unitedvision.tvkabel.exception.EntityNotExistException;
import com.unitedvision.tvkabel.exception.NotPayableCustomerException;
import com.unitedvision.tvkabel.exception.UnpaidBillException;
import com.unitedvision.tvkabel.persistence.SpringDataJpaConfig;
import com.unitedvision.tvkabel.persistence.entity.Pegawai;
import com.unitedvision.tvkabel.persistence.entity.Pelanggan;
import com.unitedvision.tvkabel.persistence.entity.Pembayaran;
import com.unitedvision.tvkabel.persistence.entity.Perusahaan;
import com.unitedvision.tvkabel.persistence.entity.Pembayaran.Tagihan;
import com.unitedvision.tvkabel.util.DateUtil;

@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration (classes = {SpringDataJpaConfig.class})
@Transactional
@TransactionConfiguration (defaultRollback = true)
public class PembayaranServiceTest {
	@Autowired
	private PembayaranService pembayaranService;
	@Autowired
	private PerusahaanService perusahaanService;
	@Autowired
	private PelangganService pelangganService;
	@Autowired
	private PegawaiService pegawaiService;
	
	@Test
	public void testGet() throws EntityNotExistException {
		Perusahaan perusahaan = perusahaanService.getOne(17);
		Date tanggalMulai = DateUtil.getFirstDate();
		Date tanggalAkhir = DateUtil.getLastDate();
		
		List<Pembayaran> list = (List<Pembayaran>)pembayaranService.get(perusahaan, tanggalMulai, tanggalAkhir);
		
		for (Pembayaran pembayaran : list) {
			assertNotNull(pembayaran.getId());
			assertNotEquals(0, pembayaran.getId());
		}
	}
	
	@Test
	public void testDelete() throws EntityNotExistException, NotPayableCustomerException, UnpaidBillException, DataDuplicationException {
		Pelanggan pelanggan = pelangganService.getOne(35);

		Pembayaran pembayaranTerakhir = pembayaranService.getLast(pelanggan);
		assertEquals(2015, pembayaranTerakhir.getTahun());
		assertEquals(Month.JANUARY, pembayaranTerakhir.getBulan());
		
		assertEquals(0, pelanggan.getTunggakan());
		pembayaranService.delete(pembayaranTerakhir);

		Pelanggan pelangganDeleted = pelangganService.getOne(35);
		assertEquals(1, pelangganDeleted.getTunggakan());
	}
	
	@Test
	public void testGetPayableTagihan() throws EntityNotExistException {
		Pelanggan pelanggan = pelangganService.getOne(35);
		Tagihan tagihan = pembayaranService.getPayableTagihan(pelanggan);
		
		assertEquals(Month.FEBRUARY, tagihan.getBulan());
		assertEquals(2015, tagihan.getTahun());
	}
	
	@Test
	public void testGetLast() throws EntityNotExistException {
		Pelanggan pelanggan = pelangganService.getOne(35);

		Pembayaran pembayaran = pembayaranService.getLast(pelanggan);
		assertEquals(Month.JANUARY, pembayaran.getBulan());
		assertEquals(2015, pembayaran.getTahun());
	}
	
	@Test
	public void testPay() throws EntityNotExistException, NotPayableCustomerException, UnpaidBillException, DataDuplicationException, EmptyIdException {
		Date tanggalBayar = DateUtil.getSimpleNow();
		Pelanggan pelanggan = pelangganService.getOne(35);
		Pegawai pegawai = pegawaiService.getOne(15);
		long jumlahBayar = pelanggan.getIuran();
		Tagihan tagihan = new Tagihan(2015, Month.FEBRUARY);

		Pembayaran pembayaran = new Pembayaran(0, "", tanggalBayar, pelanggan, pegawai, jumlahBayar, tagihan);
		assertEquals(0, pelanggan.getTunggakan());
		
		pembayaranService.pay(pembayaran);
		Pelanggan pelangganUpdated = pelangganService.getOne(35);
		assertEquals(-1, pelangganUpdated.getTunggakan());
	}
	
	@Test
	public void testSinglePay() throws EntityNotExistException, NotPayableCustomerException, UnpaidBillException, DataDuplicationException, EmptyIdException {
		Pelanggan pelanggan = pelangganService.getOne(35);
		Pegawai pegawai = pegawaiService.getOne(15);
		long jumlahPembayaran = pelanggan.getIuran();
		int jumlahBulan = 1;

		pembayaranService.pay(pelanggan, pegawai, jumlahPembayaran, jumlahBulan);
		
		Pelanggan pelangganUpdated = pelangganService.getOne(35);
		
		assertEquals(-jumlahBulan, pelangganUpdated.getTunggakan());
	}
	
	@Test
	public void testMultiplePay() throws EntityNotExistException, NotPayableCustomerException, UnpaidBillException, DataDuplicationException, EmptyIdException {
		Pelanggan pelanggan = pelangganService.getOne(35);
		Pegawai pegawai = pegawaiService.getOne(15);
		long jumlahPembayaran = pelanggan.getIuran();
		int jumlahBulan = 3;

		pembayaranService.pay(pelanggan, pegawai, jumlahPembayaran, jumlahBulan);
		
		Pelanggan pelangganUpdated = pelangganService.getOne(35);
		
		assertEquals(-jumlahBulan, pelangganUpdated.getTunggakan());
	}
	
	@Test
	public void testCreateListPembayaran() throws EntityNotExistException, NotPayableCustomerException, UnpaidBillException, EmptyIdException, DataDuplicationException {
		Pelanggan pelanggan = pelangganService.getOne(35);
		Pegawai pegawai = pegawaiService.getOne(15);
		long jumlahPembayaran = pelanggan.getIuran();
		int jumlahBulan = 3;

		List<Pembayaran> listPembayaran = pembayaranService.createListPembayaran(pelanggan, pegawai, jumlahPembayaran, jumlahBulan);
		assertEquals(3, listPembayaran.size());

		Tagihan before = null;
		for (Pembayaran pembayaran : listPembayaran) {
			Tagihan tagihan = pembayaran.getTagihan();
			
			if (before != null) {
				assertNotEquals(before, tagihan);
			}
			
			before = tagihan;
		}
	}
}
