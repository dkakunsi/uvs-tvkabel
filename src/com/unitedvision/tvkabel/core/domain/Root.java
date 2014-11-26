package com.unitedvision.tvkabel.core.domain;

/**
 * Root of every type.
 * 
 * @author Deddy Christoper Kakunsi
 *
 * @param <T> Entity type. Use to send/retrieve data to/from database.
 * @param <U> Model type. Use to send/retrieve data to/from web pages.
 */
public interface Root<T, U> {
	/**
	 * Returns the {@link Entity} type of this object.
	 * @return {@link Entity} type.
	 */
	T toEntity();
	
	/**
	 * Returns th {@link Model} type of this object.
	 * @return {@link Model} type.
	 */
	U toModel();
}
