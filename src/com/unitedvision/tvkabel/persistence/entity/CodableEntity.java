package com.unitedvision.tvkabel.persistence.entity;

import com.unitedvision.tvkabel.exception.EmptyCodeException;

/**
 * Base class of entity which has code.
 * 
 * @author Deddy Chrsitoper Kakunsi
 *
 */
public abstract class CodableEntity extends Entity {
	/**
	 * Code.
	 */
	protected String kode;

	/**
	 * Return entity's code.
	 * @return entity's code
	 */
	public String getKode() {
		return kode;
	}

	/**
	 * Set entity's code. The {@code kode} passed in must have value and cannot be an empty string.
	 * @param kode cannot be null or an empty string
	 * @throws EmptyCodeException kode is null or an empty string
	 */
	public void setKode(String kode) throws EmptyCodeException {
		if ((kode == null) || (kode.equals("")))
			throw new EmptyCodeException("kode cannot be null or an empty String");
		this.kode = kode;
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
		CodableEntity other = (CodableEntity) obj;
		if (kode == null) {
			if (other.kode != null)
				return false;
		} else if (!kode.equals(other.kode))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CodableDomainModel [kode=" + kode + "]";
	}
}
