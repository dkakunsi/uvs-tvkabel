package com.unitedvision.tvkabel.persistence.entity;

import com.unitedvision.tvkabel.exception.EmptyIdException;
import com.unitedvision.tvkabel.web.model.Model;

/**
 * The base class of every entity.
 * 
 * @author Deddy Christoper Kakunsi
 *
 */
public abstract class Entity {
	/**
	 * Id
	 */
	protected int id;

	public int getId() {
		return id;
	}
	
	/**
	 * Set id.
	 * @param id must be positive
	 * @throws EmptyIdException id is 0 or negative
	 */
	public void setId(int id) throws EmptyIdException {
		if (id <= 0)
			throw new EmptyIdException("id must be positive");
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

	/**
	 * Convert to {@link Model} type.
	 * @return model
	 */
	public abstract Model toModel();

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
		Entity other = (Entity) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DomainModel [id=" + id + "]";
	}
}
