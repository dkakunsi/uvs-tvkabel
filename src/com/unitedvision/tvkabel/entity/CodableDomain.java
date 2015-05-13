package com.unitedvision.tvkabel.entity;

import com.unitedvision.tvkabel.exception.EmptyCodeException;
import com.unitedvision.tvkabel.exception.EmptyIdException;

/**
 * Domain which has code.
 * 
 * @author Deddy Christoper Kakunsi
 *
 */
public abstract class CodableDomain extends Domain {
	/**
	 * Code.
	 */
	protected String kode;

	/**
	 * Create instance.
	 */
	protected CodableDomain() {
		super();
	}
	
	/**
	 * Create instance.
	 * @param id
	 * @param kode
	 * @throws EmptyIdException {@code id} is not positive.
	 * @throws EmptyCodeException {@code kode} is null or an empty string.
	 */
	protected CodableDomain(int id, String kode) throws EmptyIdException, EmptyCodeException {
		super(id);
		setKode(kode);
	}
	
	/**
	 * Return domain's code.
	 * @return domain's code
	 */
	public String getKode() {
		if (kode == null)
			kode = "DEFAULT";
		return kode.toUpperCase();
	}

	/**
	 * Set domain's code.
	 * @param kode
	 * @throws EmptyCodeException @{code kode} is null or an empty string.
	 */
	public void setKode(String kode) throws EmptyCodeException {
		if ((kode == null) || (kode.equals("")))
			throw new EmptyCodeException("kode cannot be null or an empty String");
		this.kode = kode.toUpperCase();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((kode == null) ? 0 : kode.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		CodableDomain other = (CodableDomain) obj;
		if (kode == null) {
			if (other.kode != null)
				return false;
		} else if (!kode.equals(other.kode))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CodableDomain [kode=" + kode + "]";
	}
}
