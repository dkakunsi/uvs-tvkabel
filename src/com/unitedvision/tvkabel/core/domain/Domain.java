package com.unitedvision.tvkabel.core.domain;

/**
 * Root of every domain.
 * 
 * @author Deddy CHristoper Kakunsi.
 *
 * @param <T> Entity type. Use to send/retrieve data to/from database.
 * @param <U> Model type. Use to send/retrieve data to/from web pages.
 */
public interface Domain<T, U> extends Root<T, U>{
	/**
	 * Return domain's id.
	 * @return id
	 */
	int getId();
	
	/**
	 * Whether it is a new domain.
	 * @return true if it is a new domain, otherwise false.
	 */
	boolean isNew();
}
