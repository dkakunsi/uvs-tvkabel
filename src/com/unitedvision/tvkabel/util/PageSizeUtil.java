package com.unitedvision.tvkabel.util;

/**
 * Utility class for working with SPringDataJpa {@code Pageable} class.
 * @author Deddy Christoper Kakunsi
 *
 */
public class PageSizeUtil {
	/**
	 * NUMBER OF DATA IN EACH PAGE
	 */
	public static final int DATA_NUMBER = 12;

	/**
	 * Returns counter.
	 * @param lastNumber
	 * @param listSize
	 * @return counter
	 */
	public static int getCounter(Integer page, int listSize) {
		return (page + 1) * DATA_NUMBER - (DATA_NUMBER - listSize);
	}
}
