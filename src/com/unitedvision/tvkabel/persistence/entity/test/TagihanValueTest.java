package com.unitedvision.tvkabel.persistence.entity.test;

import static org.junit.Assert.*;

import java.time.Month;

import org.junit.Test;

import com.unitedvision.tvkabel.persistence.entity.PembayaranEntity.TagihanValue;

public class TagihanValueTest {

	@Test
	public void testJoin() {
		TagihanValue tagihanValue = new TagihanValue(2014, Month.JANUARY);
		
		assertEquals("201401", tagihanValue.join());
	}

	@Test
	public void testIncrease() {
		TagihanValue tagihanValue = new TagihanValue(2014, Month.JANUARY);
		tagihanValue.increase();
		
		assertEquals(2014, tagihanValue.getTahun());
		assertEquals(Month.FEBRUARY, tagihanValue.getBulan());
	}

	@Test
	public void testIncreaseSpecial() {
		TagihanValue tagihanValue = new TagihanValue(2013, Month.DECEMBER);
		tagihanValue.increase();
		
		assertEquals(2014, tagihanValue.getTahun());
		assertEquals(Month.JANUARY, tagihanValue.getBulan());
	}

	@Test
	public void testGetValue() {
		TagihanValue tagihanValue = new TagihanValue(2014, Month.JANUARY);
		
		assertEquals(24169, tagihanValue.getValue());
	}

	@Test
	public void testCompareWithEquals() {
		TagihanValue tagihanValue = new TagihanValue(2013, Month.DECEMBER);
		TagihanValue comparer = new TagihanValue(2013, Month.DECEMBER);
		
		assertEquals(0, tagihanValue.compareWith(comparer));
	}

	@Test
	public void testCompareWithGreater() {
		TagihanValue tagihanValue = new TagihanValue(2014, Month.JANUARY);
		TagihanValue comparer = new TagihanValue(2013, Month.DECEMBER);
		
		assertEquals(true, (tagihanValue.compareWith(comparer) > 0));
	}

	@Test
	public void testCompareWithLesser() {
		TagihanValue tagihanValue = new TagihanValue(2014, Month.JANUARY);
		TagihanValue comparer = new TagihanValue(2014, Month.FEBRUARY);
		
		assertEquals(true, (tagihanValue.compareWith(comparer) < 0));
	}

	@Test
	public void testIsPaidGreaterThanLast() {
		TagihanValue tagihanValue = new TagihanValue(2014, Month.MARCH);
		TagihanValue last = new TagihanValue(2014, Month.FEBRUARY);
		
		assertEquals(false, tagihanValue.isPaid(last));
	}

	@Test
	public void testIsPaidLesserThanLast() {
		TagihanValue tagihanValue = new TagihanValue(2014, Month.JANUARY);
		TagihanValue last = new TagihanValue(2014, Month.FEBRUARY);
		
		assertEquals(true, tagihanValue.isPaid(last));
	}

	@Test
	public void testIsPaidEqualsToLast() {
		TagihanValue tagihanValue = new TagihanValue(2014, Month.FEBRUARY);
		TagihanValue last = new TagihanValue(2014, Month.FEBRUARY);
		
		assertEquals(true, tagihanValue.isPaid(last));
	}

	@Test
	public void testIsPrecidingGreaterThanNext() {
		TagihanValue tagihanValue = new TagihanValue(2014, Month.MARCH);
		TagihanValue next = new TagihanValue(2014, Month.FEBRUARY);
		
		assertEquals(false, tagihanValue.isPreceding(next));
	}

	@Test
	public void testIsPrecidingLesserThanNext() {
		TagihanValue tagihanValue = new TagihanValue(2014, Month.JANUARY);
		TagihanValue next = new TagihanValue(2014, Month.FEBRUARY);
		
		assertEquals(true, tagihanValue.isPreceding(next));
	}

	@Test
	public void testIsPrecidingLesserThanNextToMany() {
		TagihanValue tagihanValue = new TagihanValue(2013, Month.DECEMBER);
		TagihanValue next = new TagihanValue(2014, Month.FEBRUARY);
		
		assertEquals(false, tagihanValue.isPreceding(next));
	}

	@Test
	public void testIsPrecidingEqualsToNext() {
		TagihanValue tagihanValue = new TagihanValue(2014, Month.FEBRUARY);
		TagihanValue next = new TagihanValue(2014, Month.FEBRUARY);
		
		assertEquals(false, tagihanValue.isPreceding(next));
	}
}
