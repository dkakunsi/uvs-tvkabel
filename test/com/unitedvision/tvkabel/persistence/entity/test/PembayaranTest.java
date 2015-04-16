package com.unitedvision.tvkabel.persistence.entity.test;

import static org.junit.Assert.*;

import java.time.Month;

import org.junit.Test;

import com.unitedvision.tvkabel.entity.Pegawai;
import com.unitedvision.tvkabel.entity.Pelanggan;
import com.unitedvision.tvkabel.entity.Pembayaran;
import com.unitedvision.tvkabel.entity.Pembayaran.Tagihan;
import com.unitedvision.tvkabel.exception.EmptyIdException;

public class PembayaranTest {

	@Test
	public void generateKodeWorks() throws EmptyIdException {
		Pelanggan pelanggan = new Pelanggan();
		pelanggan.setId(101);
		Pegawai pegawai = new Pegawai();
		pegawai.setId(201);
		
		Pembayaran pembayaran = new Pembayaran();
		pembayaran.setPegawai(pegawai);
		pembayaran.setPelanggan(pelanggan);
		pembayaran.setTagihan(new Tagihan(2014, Month.JANUARY));
		
		pembayaran.generateKode();
		
		assertEquals("2014010010100201", pembayaran.getKode());
	}
	
	@Test
	public void toStringTest() {
		Pembayaran pembayaran = new Pembayaran();
		
		assertEquals("00001", pembayaran.toString(1));
		assertEquals("00021", pembayaran.toString(21));
		assertEquals("00101", pembayaran.toString(101));
		assertEquals("03001", pembayaran.toString(3001));
		assertEquals("11111", pembayaran.toString(11111));
	}
	
	@Test
	public void isPaidGreaterThanLast() {
		Pembayaran pembayaran = new Pembayaran();
		pembayaran.setTagihan(new Tagihan(2014, Month.FEBRUARY));
		
		Pembayaran last = new Pembayaran();
		last.setTagihan(new Tagihan(2014, Month.JANUARY));
		
		assertEquals(false, pembayaran.isPaid(last));
	}
	
	@Test
	public void isPaidLesserThanLast() {
		Pembayaran pembayaran = new Pembayaran();
		pembayaran.setTagihan(new Tagihan(2013, Month.DECEMBER));
		
		Pembayaran last = new Pembayaran();
		last.setTagihan(new Tagihan(2014, Month.JANUARY));
		
		assertEquals(true, pembayaran.isPaid(last));
	}
	
	@Test
	public void isPaidEqualsToLast() {
		Pembayaran pembayaran = new Pembayaran();
		pembayaran.setTagihan(new Tagihan(2014, Month.JANUARY));
		
		Pembayaran last = new Pembayaran();
		last.setTagihan(new Tagihan(2014, Month.JANUARY));
		
		assertEquals(true, pembayaran.isPaid(last));
	}
	
	@Test
	public void isPrecedingGreaterThanNext() {
		Pembayaran pembayaran = new Pembayaran();
		pembayaran.setTagihan(new Tagihan(2014, Month.JANUARY));
		
		Pembayaran next = new Pembayaran();
		next.setTagihan(new Tagihan(2013, Month.DECEMBER));
		
		assertEquals(false, pembayaran.isPreceding(next));
	}
	
	@Test
	public void isPrecedingLesserThanNext() {
		Pembayaran pembayaran = new Pembayaran();
		pembayaran.setTagihan(new Tagihan(2013, Month.DECEMBER));
		
		Pembayaran next = new Pembayaran();
		next.setTagihan(new Tagihan(2014, Month.JANUARY));
		
		assertEquals(true, pembayaran.isPreceding(next));
	}
	
	@Test
	public void isPrecedingEqualsToNext() {
		Pembayaran pembayaran = new Pembayaran();
		pembayaran.setTagihan(new Tagihan(2014, Month.JANUARY));
		
		Pembayaran next = new Pembayaran();
		next.setTagihan(new Tagihan(2014, Month.JANUARY));
		
		assertEquals(false, pembayaran.isPreceding(next));
	}
}
