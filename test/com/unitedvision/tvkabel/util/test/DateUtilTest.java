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
	private final int NOW_MONTH = 2;
	private final Month _NOW_MONTH = Month.FEBRUARY;
	private final int NOW_YEAR = 2015;	
	private final String NOW_STR = "2/11/2015";
	private final String NOW_STR_DELIM = "2-11-2015";
	
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

		assertEquals(NOW_MONTH, DateUtil.getMonthInt(firstDate));
		assertEquals(1, DateUtil.getDay(firstDate));
		assertEquals(NOW_YEAR, DateUtil.getYear(firstDate));
	}

	@Test
	public void testGetLastDate() {
		Date lastDate = DateUtil.getLastDate();
		
		assertEquals(NOW_MONTH, DateUtil.getMonthInt(lastDate));
		assertEquals(31, DateUtil.getDay(lastDate));
		assertEquals(NOW_YEAR, DateUtil.getYear(lastDate));
	}

	@Test
	public void testGetLastDayCalendar() {
		Calendar cal = DateUtil.getCalendar();
		
		int lastDay = DateUtil.getLastDay(cal);

		assertEquals(31, lastDay);
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

		assertEquals(NOW_MONTH, month);
	}

	@Test
	public void testGetMonthCalendar() {
		Calendar cal = DateUtil.getCalendar();
		Month month = DateUtil.getMonth(cal);

		assertEquals(_NOW_MONTH, month);
	}

	@Test
	public void testGetMonthIntDate() {
		Date now = DateUtil.getNow();
		int month = DateUtil.getMonthInt(now);

		assertEquals(NOW_MONTH, month);
	}

	@Test
	public void testGetMonthDate() {
		Date now = DateUtil.getNow();
		Month month = DateUtil.getMonth(now);

		assertEquals(_NOW_MONTH, month);
	}

	@Test
	public void testGetYearCalendar() {
		Calendar cal = DateUtil.getCalendar();
		int year = DateUtil.getYear(cal);
		
		assertEquals(NOW_YEAR, year);
	}

	@Test
	public void testGetYearDate() {
		Date now = DateUtil.getNow();
		int year = DateUtil.getYear(now);
		
		assertEquals(NOW_YEAR, year);
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
		
		assertEquals(Month.SEPTEMBER, listMonth.get(0));
		assertEquals(Month.OCTOBER, listMonth.get(1));
		assertEquals(Month.NOVEMBER, listMonth.get(2));
		assertEquals(Month.DECEMBER, listMonth.get(3));
		assertEquals(Month.JANUARY, listMonth.get(4));
	}
	
	@Test
	public void testToStringWithDelim() {
		Date now = DateUtil.getNow();
		String nowStr = DateUtil.toString(now, "-");
		
		assertEquals(NOW_STR_DELIM, nowStr);
	}
	
	@Test
	public void testBeetween_Between() {
		Date date1 = DateUtil.getDate(2015, Month.JANUARY, 12);
		Date date2 = DateUtil.getDate(2015, Month.JANUARY, 11);
		Date date3 = DateUtil.getDate(2015, Month.JANUARY, 15);
		
		boolean between = DateUtil.between(date1, date2, date3);
		
		assertEquals(true, between);
	}
	
	@Test
	public void testBeetween_First() {
		Date date1 = DateUtil.getDate(2015, Month.JANUARY, 11);
		Date date2 = DateUtil.getDate(2015, Month.JANUARY, 11);
		Date date3 = DateUtil.getDate(2015, Month.JANUARY, 15);
		
		boolean between = DateUtil.between(date1, date2, date3);
		
		assertEquals(true, between);
	}
	
	@Test
	public void testBeetween_Last() {
		Date date1 = DateUtil.getDate(2015, Month.JANUARY, 15);
		Date date2 = DateUtil.getDate(2015, Month.JANUARY, 11);
		Date date3 = DateUtil.getDate(2015, Month.JANUARY, 15);
		
		boolean between = DateUtil.between(date1, date2, date3);
		
		assertEquals(true, between);
	}
	
	@Test
	public void testBeetween_NotBetween() {
		Date date1 = DateUtil.getDate(2015, Month.JANUARY, 10);
		Date date2 = DateUtil.getDate(2015, Month.JANUARY, 11);
		Date date3 = DateUtil.getDate(2015, Month.JANUARY, 15);
		
		boolean between = DateUtil.between(date1, date2, date3);
		
		assertEquals(false, between);
	}
}
