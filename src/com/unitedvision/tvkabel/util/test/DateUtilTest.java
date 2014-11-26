package com.unitedvision.tvkabel.util.test;

import static org.junit.Assert.*;

import java.time.Month;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.unitedvision.tvkabel.util.DateUtil;

public class DateUtilTest {
	private final int NOW_DATE = 11;
	private final int NOW_MONTH = 11;
	private final int NOW_YEAR = 2014;	
	private final String NOW_STR = "11/11/2014";
	
	@Test
	public void testGetDateString() {
		String dateStr = "10/20/2014";
		Date date = DateUtil.getDate(dateStr);
		
		assertEquals(20, DateUtil.getDay(date));
		assertEquals(2014, DateUtil.getYear(date));
		assertEquals(10, DateUtil.getMonthInt(date));
	}

	@Test
	public void testToStringDate() {
		Date now = DateUtil.getNow();
		
		String dateStr = DateUtil.toString(now);
		String[] dateStrArr = dateStr.split("/");

		assertEquals(Integer.toString(NOW_MONTH), dateStrArr[0]);
		assertEquals(Integer.toString(NOW_DATE), dateStrArr[1]);
		assertEquals(Integer.toString(NOW_YEAR), dateStrArr[2]);
		assertEquals(NOW_STR, dateStr);
	}

	@Test
	public void testCreateArrayOfDate() {
		Date date = DateUtil.getNow();
		
		int[] arrOfDate = DateUtil.createArrayOfDate(date);
		
		assertEquals(NOW_DATE, arrOfDate[0]);
		assertEquals(NOW_MONTH, arrOfDate[1]);
		assertEquals(NOW_YEAR, arrOfDate[2]);
	}

	@Test
	public void testGetFirstDate() {
		Date firstDate = DateUtil.getFirstDate();

		assertEquals(11, DateUtil.getMonthInt(firstDate));
		assertEquals(1, DateUtil.getDay(firstDate));
		assertEquals(2014, DateUtil.getYear(firstDate));
	}

	@Test
	public void testGetLastDate() {
		Date lastDate = DateUtil.getLastDate();
		
		assertEquals(11, DateUtil.getMonthInt(lastDate));
		assertEquals(30, DateUtil.getDay(lastDate));
		assertEquals(2014, DateUtil.getYear(lastDate));
	}

	@Test
	public void testGetLastDayCalendar() {
		Calendar cal = DateUtil.getCalendar();
		
		int lastDay = DateUtil.getLastDay(cal);

		assertEquals(30, lastDay);
	}

	@Test
	public void testGetLastDayMonthInt() {
		Month month = Month.OCTOBER;
		int year = 2014;
		
		int lastDay = DateUtil.getLastDay(month, year);
		
		assertEquals(31, lastDay);
	}

	@Test
	public void testGetLastDayIntInt() {
		int month = 10;
		int year = 2014;
		
		int lastDay = DateUtil.getLastDay(month, year);
		
		assertEquals(31, lastDay);
	}

	@Test
	public void testGetNow() {
		Date now = DateUtil.getNow();

		assertEquals(NOW_MONTH, DateUtil.getMonthInt(now));
		assertEquals(NOW_DATE, DateUtil.getDay(now));
		assertEquals(NOW_YEAR, DateUtil.getYear(now));
	}

	@Test
	public void testGetSimpleNow() {
		Date now = DateUtil.getSimpleNow();

		assertEquals(NOW_MONTH, DateUtil.getMonthInt(now));
		assertEquals(NOW_DATE, DateUtil.getDay(now));
		assertEquals(NOW_YEAR, DateUtil.getYear(now));
	}

	@Test
	public void testGetSimpleNowInString() {
		String simpleNow = DateUtil.getSimpleNowInString();
		
		assertEquals(NOW_STR, simpleNow);
	}

	@Test
	public void testGetSimpleDate() {
		Date now = DateUtil.getNow();
		Date simpleDate = DateUtil.getSimpleDate(now);

		assertEquals(NOW_MONTH, DateUtil.getMonthInt(simpleDate));
		assertEquals(NOW_DATE, DateUtil.getDay(simpleDate));
		assertEquals(NOW_YEAR, DateUtil.getYear(simpleDate));
	}

	@Test
	public void testGetDateIntIntInt() {
		int year = 2014;
		int month = 10;
		int date = 20;
		
		Date createdDate = DateUtil.getDate(year, month, date);

		assertEquals(2014, DateUtil.getYear(createdDate));
		assertEquals(20, DateUtil.getDay(createdDate));
		assertEquals(10, DateUtil.getMonthInt(createdDate));
	}

	@Test
	public void testGetDateIntMonthInt() {
		int year = 2014;
		Month month = Month.OCTOBER;
		int date = 20;
		
		Date createdDate = DateUtil.getDate(year, month, date);

		assertEquals(10, DateUtil.getMonthInt(createdDate));
		assertEquals(20, DateUtil.getDay(createdDate));
		assertEquals(2014, DateUtil.getYear(createdDate));
	}

	@Test
	public void testGetCalendar() {
		Calendar cal = DateUtil.getCalendar();
		
		assertNotNull(cal);
	}

	@Test
	public void testGetCalendarDate() {
		Date now = DateUtil.getNow();
		
		Calendar cal = DateUtil.getCalendar(now);
		
		assertNotNull(cal);
	}

	@Test
	public void testGetMonthIntString() {
		int month = DateUtil.getMonthInt("10");
		
		assertEquals(10, month);
	}

	@Test
	public void testGetMonthString() {
		Month month = DateUtil.getMonth("10");
		
		assertEquals(Month.OCTOBER, month);
	}

	@Test
	public void testGetMonthInt() {
		Month month = DateUtil.getMonth(10);

		assertEquals(Month.OCTOBER, month);
	}

	@Test
	public void testGetMonthIntCalendar() {
		Calendar cal = DateUtil.getCalendar();
		int month = DateUtil.getMonthInt(cal);

		assertEquals(11, month);
	}

	@Test
	public void testGetMonthCalendar() {
		Calendar cal = DateUtil.getCalendar();
		Month month = DateUtil.getMonth(cal);

		assertEquals(Month.NOVEMBER, month);
	}

	@Test
	public void testGetMonthIntDate() {
		Date now = DateUtil.getNow();
		int month = DateUtil.getMonthInt(now);

		assertEquals(11, month);
	}

	@Test
	public void testGetMonthDate() {
		Date now = DateUtil.getNow();
		Month month = DateUtil.getMonth(now);

		assertEquals(Month.NOVEMBER, month);
	}

	@Test
	public void testGetYearCalendar() {
		Calendar cal = DateUtil.getCalendar();
		int year = DateUtil.getYear(cal);
		
		assertEquals(2014, year);
	}

	@Test
	public void testGetYearDate() {
		Date now = DateUtil.getNow();
		int year = DateUtil.getYear(now);
		
		assertEquals(2014, year);
	}

	@Test
	public void testGetDayCalendar() {
		Calendar cal = DateUtil.getCalendar();
		int day = DateUtil.getDay(cal);
		
		assertEquals(NOW_DATE, day);
	}

	@Test
	public void testGetDayDate() {
		Date now = DateUtil.getNow();
		int day = DateUtil.getDay(now);
		
		assertEquals(NOW_DATE, day);
	}
	
	@Test
	public void testGetMonths() {
		Date now = DateUtil.getNow();
		
		List<Month> listMonth = DateUtil.getMonths(now, 5);
		
		assertEquals(Month.JULY, listMonth.get(0));
		assertEquals(Month.AUGUST, listMonth.get(1));
		assertEquals(Month.SEPTEMBER, listMonth.get(2));
		assertEquals(Month.OCTOBER, listMonth.get(3));
		assertEquals(Month.NOVEMBER, listMonth.get(4));
	}
	
	@Test
	public void testToStringWithDelim() {
		Date now = DateUtil.getNow();
		String nowStr = DateUtil.toString(now, "-");
		
		assertEquals("11-11-2014", nowStr);
	}
}
