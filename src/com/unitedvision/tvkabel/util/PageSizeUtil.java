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
	 * Returns the current page based on last number of data in page.
	 * @param lastNumber
	 * @return {@code lastNumber} divided by {@code DATA_NUMBER}.
	 */
	public static int getPageNumber(int lastNumber) {
		return lastNumber / DATA_NUMBER;
	}

	/**
	 * Verify the given last number of data when submit button is clicked.<br />
	 * Submit button means NEXT or PREVIOUS.
	 * @param lastNumber
	 * @param submit
	 * @return if PREVIOUS it will use previous-based verification, while next it will use next-based verification.
	 */
	public static int verifyLastNumberAndSubmit(Integer lastNumber, String submit) {
		boolean isPrevious = (submit != null) && (submit.equals("0"));
		
		return isPrevious == true ? 
				verifyLastNumberWhilePrevious(lastNumber) : verifyLastNumberWhileNext(lastNumber);
	}

	/**
	 * Next-based verification. This will verify the given last number when next button was clicked.
	 * @param lastNumber
	 * @return 0 if the given last number was null, otherwise verified last number
	 */
	public static int verifyLastNumberWhileNext(Integer lastNumber) {
		return lastNumber == null ? 0 : lastNumber;
	}

	/**
	 * Previous-based verification. This will verify the given last number when previous button was clicked.
	 * @param lastNumber
	 * @return 0 if the given last number was null, otherwise verified last number
	 */
	public static int verifyLastNumberWhilePrevious(Integer lastNumber) {
		if (lastNumber == null)
			return 0;

		lastNumber -= (DATA_NUMBER * 2);
		
		if (lastNumber < 0)
			lastNumber = 0;
		return lastNumber;
	}

	/**
	 * Verify last number and list size.
	 * @param lastNumber
	 * @param listSize
	 * @return verified last number
	 */
	public static int verifyLastNumberAndListSize(Integer lastNumber, int listSize) {
		if (listSize < DATA_NUMBER)
			listSize = 0;
		return lastNumber += listSize;
	}

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
