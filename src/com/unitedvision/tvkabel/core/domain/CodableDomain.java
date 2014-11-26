package com.unitedvision.tvkabel.core.domain;

import com.unitedvision.tvkabel.exception.EmptyCodeException;

/**
 * Root of every domain which having kode(code).
 * @author Deddy CHristoper Kakunsi.
 *
 * @param <T> Entity type. Use to send/retrieve data to/from database.
 * @param <U> Model type. Use to send/retrieve data to/from web pages.
 */
public interface CodableDomain<T, U> extends Domain<T, U> {
	/**
	 * Returns domain's code.
	 * @return domain's code.
	 */
	String getKode();
	
	/**
	 * Set domain's code.
	 * @param kode code cannot be null or an empty String
	 * @throws EmptyCodeException kode is null or an empty String
	 */
	void setKode(String kode) throws EmptyCodeException;
}
