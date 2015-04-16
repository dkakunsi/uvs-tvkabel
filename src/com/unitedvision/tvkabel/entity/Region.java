package com.unitedvision.tvkabel.entity;

import com.unitedvision.tvkabel.exception.EmptyIdException;

/**
 * Root of regional value.
 * 
 * @author Deddy CHristoper Kakunsi.
 *
 * @param <T> Entity type. Use to send/retrieve data to/from database.
 * @param <U> Model type. Use to send/retrieve data to/from web pages.
 */
public abstract class Region extends Domain {
	protected String nama;
	
	/**
	 * Create instance.
	 */
	protected Region() {
		super();
	}
	
	/**
	 * Create instance.
	 * @param id
	 * @param kode
	 * @param nama
	 * @throws EmptyIdException {@code id} is not positive.
	 */
	protected Region(int id, String nama) throws EmptyIdException {
		super(id);
		setNama(nama);
	}
	
	/**
	 * Return region's name.
	 * @return region's name.
	 */
	public String getNama() {
		return nama;
	}
	
	/**
	 * Set nama.
	 * @param nama
	 */
	public void setNama(String nama) {
		this.nama = nama;
	}
}
