package com.unitedvision.tvkabel.persistence.entity.test;

import static org.junit.Assert.*;

import java.time.Month;

import org.junit.Test;

import com.unitedvision.tvkabel.entity.Pembayaran.Tagihan;

public class TagihanTest {

	@Test
	public void testJoin() {
		Tagihan tagihan = new Tagihan(2014, Month.JANUARY);
		
		assertEquals("201401", tagihan.join());
	}

	@Test
	public void testIncrease() {
		Tagihan tagihan = new Tagihan(2014, Month.JANUARY);
		tagihan.increase();
		
		assertEquals(2014, tagihan.getTahun());
		assertEquals(Month.FEBRUARY, tagihan.getBulan());
	}

	@Test
	public void testIncreaseSpecial() {
		Tagihan tagihan = new Tagihan(2013, Month.DECEMBER);
		tagihan.increase();
		
		assertEquals(2014, tagihan.getTahun());
		assertEquals(Month.JANUARY, tagihan.getBulan());
	}

	@Test
	public void testGetValue() {
		Tagihan tagihan = new Tagihan(2014, Month.JANUARY);
		
		assertEquals(24169, tagihan.getValue());
	}

	@Test
	public void testCompareWithEquals() {
		Tagihan tagihan = new Tagihan(2013, Month.DECEMBER);
		Tagihan comparer = new Tagihan(2013, Month.DECEMBER);
		
		assertEquals(0, tagihan.compareWith(comparer));
	}

	@Test
	public void testCompareWithGreater() {
		Tagihan tagihan = new Tagihan(2014, Month.JANUARY);
		Tagihan comparer = new Tagihan(2013, Month.DECEMBER);
		
		assertEquals(true, (tagihan.compareWith(comparer) > 0));
	}

	@Test
	public void testCompareWithLesser() {
		Tagihan tagihan = new Tagihan(2014, Month.JANUARY);
		Tagihan comparer = new Tagihan(2014, Month.FEBRUARY);
		
		assertEquals(true, (tagihan.compareWith(comparer) < 0));
	}

	@Test
	public void testIsPaidGreaterThanLast() {
		Tagihan tagihan = new Tagihan(2014, Month.MARCH);
		Tagihan last = new Tagihan(2014, Month.FEBRUARY);
		
		assertEquals(false, tagihan.isPaid(last));
	}

	@Test
	public void testIsPaidLesserThanLast() {
		Tagihan tagihan = new Tagihan(2014, Month.JANUARY);
		Tagihan last = new Tagihan(2014, Month.FEBRUARY);
		
		assertEquals(true, tagihan.isPaid(last));
	}

	@Test
	public void testIsPaidEqualsToLast() {
		Tagihan tagihan = new Tagihan(2014, Month.FEBRUARY);
		Tagihan last = new Tagihan(2014, Month.FEBRUARY);
		
		assertEquals(true, tagihan.isPaid(last));
	}

	@Test
	public void testIsPrecidingGreaterThanNext() {
		Tagihan tagihan = new Tagihan(2014, Month.MARCH);
		Tagihan next = new Tagihan(2014, Month.FEBRUARY);
		
		assertEquals(false, tagihan.isPreceding(next));
	}

	@Test
	public void testIsPrecidingLesserThanNext() {
		Tagihan tagihan = new Tagihan(2014, Month.JANUARY);
		Tagihan next = new Tagihan(2014, Month.FEBRUARY);
		
		assertEquals(true, tagihan.isPreceding(next));
	}

	@Test
	public void testIsPrecidingLesserThanNextToMany() {
		Tagihan tagihan = new Tagihan(2013, Month.DECEMBER);
		Tagihan next = new Tagihan(2014, Month.FEBRUARY);
		
		assertEquals(false, tagihan.isPreceding(next));
	}

	@Test
	public void testIsPrecidingEqualsToNext() {
		Tagihan tagihan = new Tagihan(2014, Month.FEBRUARY);
		Tagihan next = new Tagihan(2014, Month.FEBRUARY);
		
		assertEquals(false, tagihan.isPreceding(next));
	}
}
