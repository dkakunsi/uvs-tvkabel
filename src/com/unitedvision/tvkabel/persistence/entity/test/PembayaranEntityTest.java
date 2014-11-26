package com.unitedvision.tvkabel.persistence.entity.test;

import static org.junit.Assert.*;

import java.time.Month;

import org.junit.Test;

import com.unitedvision.tvkabel.exception.EmptyIdException;
import com.unitedvision.tvkabel.persistence.entity.PegawaiEntity;
import com.unitedvision.tvkabel.persistence.entity.PelangganEntity;
import com.unitedvision.tvkabel.persistence.entity.PembayaranEntity;
import com.unitedvision.tvkabel.persistence.entity.PembayaranEntity.TagihanValue;

public class PembayaranEntityTest {

	@Test
	public void generateKodeWorks() throws EmptyIdException {
		@SuppressWarnings("deprecation")
		PelangganEntity pelangganEntity = new PelangganEntity(101);
		@SuppressWarnings("deprecation")
		PegawaiEntity pegawaiEntity = new PegawaiEntity(201);
		
		PembayaranEntity pembayaranEntity = new PembayaranEntity();
		pembayaranEntity.setPegawai(pegawaiEntity);
		pembayaranEntity.setPelanggan(pelangganEntity);
		pembayaranEntity.setTagihan(new TagihanValue(2014, Month.JANUARY));
		
		pembayaranEntity.generateKode();
		
		assertEquals("2014010010100201", pembayaranEntity.getKode());
	}
	
	@Test
	public void toStringTest() {
		PembayaranEntity pembayaranEntity = new PembayaranEntity();
		
		assertEquals("00001", pembayaranEntity.toString(1));
		assertEquals("00021", pembayaranEntity.toString(21));
		assertEquals("00101", pembayaranEntity.toString(101));
		assertEquals("03001", pembayaranEntity.toString(3001));
		assertEquals("11111", pembayaranEntity.toString(11111));
	}
	
	@Test
	public void isPaidGreaterThanLast() {
		PembayaranEntity pembayaranEntity = new PembayaranEntity();
		pembayaranEntity.setTagihan(new TagihanValue(2014, Month.FEBRUARY));
		
		PembayaranEntity last = new PembayaranEntity();
		last.setTagihan(new TagihanValue(2014, Month.JANUARY));
		
		assertEquals(false, pembayaranEntity.isPaid(last));
	}
	
	@Test
	public void isPaidLesserThanLast() {
		PembayaranEntity pembayaranEntity = new PembayaranEntity();
		pembayaranEntity.setTagihan(new TagihanValue(2013, Month.DECEMBER));
		
		PembayaranEntity last = new PembayaranEntity();
		last.setTagihan(new TagihanValue(2014, Month.JANUARY));
		
		assertEquals(true, pembayaranEntity.isPaid(last));
	}
	
	@Test
	public void isPaidEqualsToLast() {
		PembayaranEntity pembayaranEntity = new PembayaranEntity();
		pembayaranEntity.setTagihan(new TagihanValue(2014, Month.JANUARY));
		
		PembayaranEntity last = new PembayaranEntity();
		last.setTagihan(new TagihanValue(2014, Month.JANUARY));
		
		assertEquals(true, pembayaranEntity.isPaid(last));
	}
	
	@Test
	public void isPrecedingGreaterThanNext() {
		PembayaranEntity pembayaranEntity = new PembayaranEntity();
		pembayaranEntity.setTagihan(new TagihanValue(2014, Month.JANUARY));
		
		PembayaranEntity next = new PembayaranEntity();
		next.setTagihan(new TagihanValue(2013, Month.DECEMBER));
		
		assertEquals(false, pembayaranEntity.isPreceding(next));
	}
	
	@Test
	public void isPrecedingLesserThanNext() {
		PembayaranEntity pembayaranEntity = new PembayaranEntity();
		pembayaranEntity.setTagihan(new TagihanValue(2013, Month.DECEMBER));
		
		PembayaranEntity next = new PembayaranEntity();
		next.setTagihan(new TagihanValue(2014, Month.JANUARY));
		
		assertEquals(true, pembayaranEntity.isPreceding(next));
	}
	
	@Test
	public void isPrecedingEqualsToNext() {
		PembayaranEntity pembayaranEntity = new PembayaranEntity();
		pembayaranEntity.setTagihan(new TagihanValue(2014, Month.JANUARY));
		
		PembayaranEntity next = new PembayaranEntity();
		next.setTagihan(new TagihanValue(2014, Month.JANUARY));
		
		assertEquals(false, pembayaranEntity.isPreceding(next));
	}
}
