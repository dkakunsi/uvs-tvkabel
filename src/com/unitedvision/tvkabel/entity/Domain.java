package com.unitedvision.tvkabel.entity;

import com.unitedvision.tvkabel.exception.EmptyIdException;

/**
 * The base class.
 * 
 * @author Deddy Christoper Kakunsi
 *
 */
public abstract class Domain {
	/**
	 * Id
	 */
	protected int id;

	/**
	 * Create instance.
	 */
	protected Domain() {
		super();
	}
	
	protected Domain(int id) throws EmptyIdException {
		super();
		setId(id);
	}
	
	/**
	 * Return domain's id.
	 * @return {@code id}.
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Set id.
	 * @param id must be positive
	 * @throws EmptyIdException id is negative
	 */
	public void setId(int id) throws EmptyIdException {
		if (id < 0)
			throw new EmptyIdException("id cannot be negative");
		this.id = id;
	}
	
	/**
	 * Whether this is a new object (not persisted in database) or it is a detach object.
	 * 
	 * @return true if it is new, otherwise false
	 */
	public boolean isNew() {
		return (id == 0);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Domain other = (Domain) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Domain [id=" + id + "]";
	}
}
