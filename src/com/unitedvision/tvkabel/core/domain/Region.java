package com.unitedvision.tvkabel.core.domain;

/**
 * Root of regional value.
 * 
 * @author Deddy CHristoper Kakunsi.
 *
 * @param <T> Entity type. Use to send/retrieve data to/from database.
 * @param <U> Model type. Use to send/retrieve data to/from web pages.
 */
public interface Region<T, U> extends Domain<T, U> {
	/**
	 * Return region's name.
	 * @return region's name.
	 */
	String getNama();
}
