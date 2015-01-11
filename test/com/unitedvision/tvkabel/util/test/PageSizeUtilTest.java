package com.unitedvision.tvkabel.util.test;

import static org.junit.Assert.*;

import java.time.Month;
import java.util.Date;

import org.junit.Test;

import com.unitedvision.tvkabel.persistence.entity.Pembayaran.Tagihan;
import com.unitedvision.tvkabel.util.DateUtil;
import com.unitedvision.tvkabel.util.PageSizeUtil;

public class PageSizeUtilTest {
	private final int lastNumber = 12;

	@Test
	public void testGetPageNumber() {
		assertEquals(1, PageSizeUtil.getPageNumber(lastNumber));
	}

	@Test
	public void testGetPageNumberNotKelipatan() {
		assertEquals(1, PageSizeUtil.getPageNumber(15));
	}

	@Test
	public void testVerifyNullLastNumberAndNullSubmit() {
		assertEquals(0, PageSizeUtil.verifyLastNumberAndSubmit(null, null));
	}

	@Test
	public void testVerifyLastNumberAndNextSubmit() {
		assertEquals(lastNumber, PageSizeUtil.verifyLastNumberAndSubmit(lastNumber, "1"));
	}

	@Test
	public void testVerifyLastNumberAndPreviousSubmit() {
		assertEquals(0, PageSizeUtil.verifyLastNumberAndSubmit(lastNumber, "0"));
	}

	@Test
	public void testVerifyLastNumberAndPageSizeMoreThanDataPage() {
		assertEquals(25, PageSizeUtil.verifyLastNumberAndListSize(lastNumber, 13));
	}

	@Test
	public void testVerifyLastNumberAndPageSizeEqualsToDataPage() {
		assertEquals(24, PageSizeUtil.verifyLastNumberAndListSize(lastNumber, 12));
	}

	@Test
	public void testVerifyLastNumberAndPageSizeLessThanDataPage() {
		assertEquals(lastNumber, PageSizeUtil.verifyLastNumberAndListSize(lastNumber, 10));
	}

	@Test
	public void testGetCounter() {
		assertEquals(27, PageSizeUtil.getCounter(1, 15));
	}

	@Test
	public void testGetCounterEqualsDataPage() {
		assertEquals(lastNumber, PageSizeUtil.getCounter(0, 12));
	}

	@Test
	public void testVerifyNullLastNumberWhileNext() {
		assertEquals(0, PageSizeUtil.verifyLastNumberWhileNext(null));
	}

	@Test
	public void testVerifyLastNumberWhileNext() {
		assertEquals(lastNumber, PageSizeUtil.verifyLastNumberWhileNext(lastNumber));
	}

	@Test
	public void testVerifyNullLastNumberWhilePrevious() {
		assertEquals(0, PageSizeUtil.verifyLastNumberWhilePrevious(null));
	}

	@Test
	public void testVerifyLastNumberWhilePrevious() {
		assertEquals(0, PageSizeUtil.verifyLastNumberWhilePrevious(lastNumber));
	}

	@Test
	public void testVerifyBigLastNumberWhilePrevious() {
		assertEquals(12, PageSizeUtil.verifyLastNumberWhilePrevious(36));
	}
	
	@Test
	public void testGeneral() {
		Date hari = DateUtil.getNow();
		
		Tagihan tagihanAkhir = Tagihan.create(hari);
		Tagihan tagihanAwal = Tagihan.create(hari);
		tagihanAwal.substract(5);
		
		assertEquals(Month.JANUARY, tagihanAkhir.getBulan());
		assertEquals(Month.SEPTEMBER, tagihanAwal.getBulan());
	}
}
