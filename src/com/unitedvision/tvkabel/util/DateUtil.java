package com.unitedvision.tvkabel.util;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Utility class to working with java.util.Date and java.util.Calendar.
 * 
 * @author Deddy Christoper Kakunsi
 *
 */
public class DateUtil {
	public static final int EPOCH_YEAR = 1970;
	public static final int EPOCH_MONTH = 1;
	public static final int EPOCH_DAY = 1;
	
	public static final long DAY_IN_MILIS = 86400000L;

	/**
	 * Return date representation in millisecond.
	 * @param date
	 * @return date representation in millisecond.
	 */
	public static long toMilis(Date date) {
		int year = getYear(date);
		int month = getMonthInt(date);
		int day = getDay(date);
		
		return toMilis(year, month, day);
	}
	
	/**
	 * Return date representation in millisecond.
	 * @param year
	 * @param month
	 * @param day
	 * @return date representation in millisecond
	 */
	public static long toMilis(int year, int month, int day) {
		LocalDate epoch = LocalDate.of(EPOCH_YEAR, EPOCH_MONTH, EPOCH_DAY);
		LocalDate created = LocalDate.of(year, month, day);

		long p = ChronoUnit.DAYS.between(epoch, created);

		return p * DAY_IN_MILIS;
	}
	
	/**
	 * Create default calendar.<br />
	 * @return default calendar
	 */
	public static Calendar getCalendar() {
		return Calendar.getInstance();
	}

	/**
	 * Create calendar using date.<br />
	 * @param date
	 * @return calendar
	 */
	public static Calendar getCalendar(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		return cal;
	}

	/**
	 * Return month in {@code int} representation from {@code Calendar}.<br />
	 * @param calendar
	 * @return month in {@code int} representation
	 */
	public static int getMonthInt(Calendar calendar) {
		return calendar.get(Calendar.MONTH) + 1;
	}
	
	/**
	 * Return the last day of month and year.<br />
	 * @param month {@code Month}
	 * @param year {@code int}
	 * @return the last day
	 */
	public static int getLastDay(Month month, int year) {
		int lastDate = month.maxLength();

		if ((month.equals(Month.FEBRUARY)) && (year % 4 != 0))
			lastDate = 28;
		
		return lastDate;
	}

	/**
	 * Return today in date representation. Along with hour, minute, second, and other element.<br />
	 * @return today
	 */
	public static Date getNow() {
		return getCalendar().getTime();
	}

	/**
	 * Create date object.
	 * @param year {@code int}
	 * @param month {@code int}
	 * @param day {@code int}
	 * @return date object
	 */
	public static Date getDate(int year, int month, int day) {
		Calendar cal = getCalendar();
		cal.setTimeInMillis(toMilis(year, month, day));

		return cal.getTime();
	}

	/**
	 * Create date from String value.<br />
	 * Format of String is MM/dd/YYYY.
	 * @param dateStr String value with format MM/dd/YYYY
	 * @return date
	 */
	public static Date getDate(String dateStr) {
		String delim = dateStr.contains("-") ? "-" : "/";
		
		return getDate(dateStr, delim);
	}

	/**
	 * Create date from String value.<br />
	 * Format of String is MM/dd/YYYY.
	 * @param dateStr String value with format MM/dd/YYYY
	 * @param delim
	 * @return date
	 */
	public static Date getDate(String dateStr, String delim) {
		String elStr[] = dateStr.split(delim);
		Date date = getDate(Integer.parseInt(elStr[2]), getMonthInt(elStr[0]), Integer.parseInt(elStr[1]));

		return date;
	}

	/**
	 * Create String representation of date.
	 * @param date
	 * @return dateStr String representation of date in format MM/dd/YYYY
	 */
	public static String toString(Date date) {
		int[] arrOfDate = createArrayOfDate(date);
		
		return String.format("%d/%d/%d", arrOfDate[1], arrOfDate[0], arrOfDate[2]);
	}

	/**
	 * Create String representation of date with specified delimeter.
	 * @param date
	 * @param delim
	 * @return dateStr String representation of date in format MM [delim] dd [delim] YYYY
	 */
	public static String toString(Date date, String delim) {
		int[] arrOfDate = createArrayOfDate(date);
		
		return String.format("%d%s%d%s%d", arrOfDate[1], delim, arrOfDate[0], delim, arrOfDate[2]);
	}

	/**
	 * Create String representation of date with specified delimeter.
	 * @param date
	 * @param delim
	 * @return dateStr String representation of date in format YYYY [delim] mm [delim] DD
	 */
	public static String toDatabaseString(Date date, String delim) {
		int[] arrOfDate = createArrayOfDate(date);
		
		return String.format("%d%s%d%s%d", arrOfDate[2], delim, arrOfDate[1], delim, arrOfDate[0]);
	}

	/**
	 * Create String representation of date with specified delimeter.
	 * @param date
	 * @param delim
	 * @return dateStr String representation of date in format DD [delim] mm [delim] YYYY
	 */
	public static String toUserString(Date date, String delim) {
		int[] arrOfDate = createArrayOfDate(date);
		
		return String.format("%d%s%d%s%d", arrOfDate[0], delim, arrOfDate[1], delim, arrOfDate[2]);
	}

	/**
	 * Extract date element (year, month, and date) into array of {@code int}.
	 * @param date
	 * @return arrOfDate array of date [DD, mm, YYYY]
	 */
	public static int[] createArrayOfDate(Date date) {
		int[] arrOfDate = new int[3];

		arrOfDate[0] = getDay(date);
		arrOfDate[1] = getMonthInt(date);
		arrOfDate[2] = getYear(date);
		
		return arrOfDate;
	}

	/**
	 * Return the first date of this month.
	 * @return the first date of this month
	 */
	public static Date getFirstDate() {
		Calendar cal = getCalendar();
		cal.set(Calendar.DATE, 1);
		
		return cal.getTime();
	}

	/**
	 * Return the last date of this month.<br /><br />
	 * JANUARY, MARCH, MAY, JULY, AUGUST, OCTOBER, DECEMBER has 31 days.<br />
	 * APRIL, JUNE, SEPTEMBER, NOVEMBER has 30 days.<br />
	 * FEBRUARY has 28 days, if the {@code year % 4 == 0} it has 29 days.
	 * @return the last date of this month
	 */
	public static Date getLastDate() {
		Calendar cal = getCalendar();
		cal.set(Calendar.DATE, getLastDay(cal));

		return cal.getTime();
	}

	/**
	 * Return the last day of current month of the given calendar.
	 * @param cal
	 * @return the last day
	 */
	public static int getLastDay(Calendar cal) {
		Month month = getMonth(cal);
		int year = getYear(cal);

		return getLastDay(month, year);
	}

	/**
	 * Return the last day of month and year.
	 * @param month {@code int}
	 * @param year {@code int}
	 * @return the last day
	 */
	public static int getLastDay(int month, int year) {
		return getLastDay(Month.of(month), year);
	}

	/**
	 * Return today in date representation, but only containing year, month, and day.
	 * @return today
	 */
	public static Date getSimpleNow() {
		return getSimpleDate(getNow());
	}

	/**
	 * Return today in String representation.
	 * @return today in String representation using format MM/dd/YYYY
	 */
	public static String getSimpleNowInString() {
		return toString(getSimpleNow());
	}

	/**
	 * Return today in String representation with specified delim.
	 * @return today in String representation using format MM [delim] dd [delim] YYYY
	 */
	public static String getSimpleNowInString(String delim) {
		return toString(getSimpleNow(), delim);
	}

	/**
	 * Convert the full element date into simple element only containing year, month, and day.
	 * @param date 
	 * @return simpleDate simpler version of date
	 */
	public static Date getSimpleDate(Date date) {
		int[] arrOfDate = createArrayOfDate(date);
		
		return getDate(arrOfDate[2], arrOfDate[1], arrOfDate[0]);
	}

	/**
	 * Create date object.
	 * @param year {@code int}
	 * @param month {@code Month}
	 * @param date {@code int}
	 * @return
	 */
	public static Date getDate(int year, Month month, int date) {
		return getDate(year, month.getValue(), date);
	}
	
	/**
	 * Return month in {@code int} representation from {@code String}.
	 * @param month
	 * @return month in {@code int} representation
	 */
	public static int getMonthInt(String month) {
		return Integer.parseInt(month);
	}
	
	/**
	 * Return month in {@code Month} representation from {@code String}.
	 * @param month
	 * @return month in {@code Month} representation
	 */
	public static Month getMonth(String month) {
		return Month.of(getMonthInt(month));
	}

	/**
	 * Return month in {@code Month} representation from {@code int}.
	 * @param month
	 * @return month in {@code Month} representation
	 */
	public static Month getMonth(int month) {
		if (month < 1)
			month += 12;
		
		if (month > 12)
			month -= 12;
		
		return Month.of(month);
	}

	/**
	 * Return month in {@code Month} representation from {@code Calendar}.
	 * @param calendar
	 * @return month in {@code Month} representation
	 */
	public static Month getMonth(Calendar calendar) {
		return Month.of(getMonthInt(calendar));
	}
	
	/**
	 * Return month in {@code int} representation from {@code Date}.
	 * @param date
	 * @return month in {@code int} representation
	 */
	public static int getMonthInt(Date date) {
		return getMonthInt(getCalendar(date));
	}
	
	/**
	 * Return month of now in {@code int} representation from {@code Date}.
	 * @return month in {@code int} representation
	 */
	public static int getMonthIntNow() {
		return getMonthInt(getCalendar(getNow()));
	}
	
	/**
	 * Return month in {@code Month} representation from {@code Date}.
	 * @param date
	 * @return month in {@code Month} representation
	 */
	public static Month getMonth(Date date) {
		return Month.of(getMonthInt(date));
	}
	
	/**
	 * Return month of now in {@code Month} representation from {@code Date}.
	 * @return month in {@code Month} representation
	 */
	public static Month getMonthNow() {
		return Month.of(getMonthIntNow());
	}
	
	/**
	 * Return year in {@code int} representation from {@code Calendar}.
	 * @param calendar
	 * @return year in {@code int} representation
	 */
	public static int getYear(Calendar calendar) {
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * Return year in {@code int} representation from {@code Date}.
	 * @param date
	 * @return year in {@code int} representation
	 */
	public static int getYear(Date date) {
		return getCalendar(date).get(Calendar.YEAR);
	}

	/**
	 * Return year of now in {@code int} representation from {@code Date}.
	 * @return year in {@code int} representation
	 */
	public static int getYearNow() {
		return getCalendar(getNow()).get(Calendar.YEAR);
	}

	/**
	 * Return day in {@code int} representation from {@code Calendar}.
	 * @param calendar
	 * @return day in {@code int} representation
	 */
	public static int getDay(Calendar calendar) {
		return calendar.get(Calendar.DATE);
	}

	/**
	 * Return day in {@code int} representation from {@code Date}.
	 * @param day
	 * @return day in {@code int} representation
	 */
	public static int getDay(Date date) {
		return getCalendar(date).get(Calendar.DATE);
	}
	
	/**
	 * Convert {@code day} into String representation
	 * @param day
	 * @return
	 */
	public static String getDayString(int day) {
		String strInt = String.valueOf(day);
		return getDayString(strInt);
	}
	
	/**
	 * Convert {@code day} into String representation
	 * @param str
	 * @return
	 */
	public static String getDayString(String str) {
		if (str.length() == 1)
			return String.format("-0%s", str);
		return String.format("-%s", str);
	}
	
	/**
	 * Return day in {@code String} representation.
	 * @param date
	 * @return
	 */
	public static String getDayString(Date date) {
		int day = getDay(date);
		
		return getDayString(day);
	}
	
	/**
	 * Return day of now in {@code int} representation from {@code Date}.
	 * @return day in {@code int} representation
	 */
	public static int getDayNow() {
		return getCalendar(getNow()).get(Calendar.DATE);
	}
	
	/**
	 * Return list of months, based on given month and number of trace back.
	 * @param date
	 * @param traceBack
	 */
	public static List<Month> getMonths(Date date, int traceBack) {
		int bulan = getMonthInt(date);
		int start = bulan - traceBack;

		List<Month> months = new ArrayList<>();
		for (int i = (start + 1); i <= bulan; i++)
			months.add(getMonth(i));
		
		return months;
	}
	
	/**
	 * Check whether two dates equals or not. Comparison between year, month, and day only.
	 * @param date1
	 * @param date2
	 * @return true if year, month, and day are equals. Otherwise, false.
	 */
	public static boolean equals(Date date1, Date date2) {
		if (getYear(date1) != getYear(date2))
			return false;
		if (getMonthInt(date1) != getMonthInt(date2))
			return false;
		if (getDay(date1) != getDay(date2))
			return false;
		return true;
	}

	/**
	 * Check whether {@code date1} is beetween {@code date2} and {@code date3}.
	 * @param date1 main date.
	 * @param date2 camparer.
	 * @param date3 comporer.
	 * @return
	 */
	public static boolean between(Date date1, Date date2, Date date3) {
		long date1Milis = toMilis(date1);
		long date2Milis = toMilis(date2);
		long date3Milis = toMilis(date3);
		
		if (date1Milis >= date2Milis && date1Milis <= date3Milis)
			return true;
		return false;
	}
	
	public static String codedDate(Date date) {
		return String.format("%d%d%d", getYear(date), getMonthInt(date), getDay(date));
	}
	
	public static String codedTime(Date date) {
		return String.format("%d%d%d", getYear(date), getMonthInt(date), getDay(date));
	}
	
	public static String codedString(Date date) {
		return String.format("%s%s", codedDate(date), codedTime(date));
	}
}
