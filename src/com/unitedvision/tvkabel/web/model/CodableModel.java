package com.unitedvision.tvkabel.web.model;

import com.unitedvision.tvkabel.exception.EmptyCodeException;
import com.unitedvision.tvkabel.persistence.entity.Entity;

/**
 * Root of codable model.
 * 
 * @author Deddy Christoper Kakunsi
 *
 */
public abstract class CodableModel extends Model {
	/** Kode */
	protected String kode;

	/**
	 * Return kode.
	 * @return kode
	 */
	public String getKode() {
		return kode;
	}

	/**
	 * Set kode.
	 * @param kode cannot be null or empty String
	 * @throws EmptyCodeException kode is null or empty String
	 */
	public void setKode(String kode) throws EmptyCodeException {
		if ((kode == null) || (kode.equals("")))
			throw new EmptyCodeException("kode cannot be null or empty String");
		this.kode = kode;
	}

	/**
	 * Whether model has kode or not.
	 * @return false if kode is null or empty String, otherwise true
	 */
	public boolean hasKode() {
		return !(kode == null || kode.equals(""));
	}
	
	/**
	 * Create entity containing code.
	 * @return
	 */
	protected abstract Entity toEntityWithKode();
}
