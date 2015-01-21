package com.unitedvision.tvkabel.util.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.unitedvision.tvkabel.util.PageSizeUtil;

public class PageSizeUtilTest {
	private final int lastNumber = 12;

	@Test
	public void testGetCounter() {
		assertEquals(27, PageSizeUtil.getCounter(1, 15));
	}

	@Test
	public void testGetCounterEqualsDataPage() {
		assertEquals(lastNumber, PageSizeUtil.getCounter(0, 12));
	}
}
