package com.unitedvision.tvkabel.web.model;

import com.unitedvision.tvkabel.exception.EmptyIdException;
import com.unitedvision.tvkabel.persistence.entity.Entity;

/**
 * Root of model.
 * 
 * @author Deddy Christoper Kakunsi
 *
 */
public abstract class Model {
	/** Id */
	protected int id;

	/**
	 * Whether this is a new Model.<br />
	 * New Model means do not has id.
	 * @return true if has id, otherwise false
	 */
	public boolean isNew() {
		return (id == 0);
	}

	/**
	 * Return id.
	 * @return id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Set id.
	 * @param id must be positive.
	 * @throws EmptyIdException id is 0 or negative
	 */
	public void setId(int id) throws EmptyIdException {
		if (id <= 0)
			throw new EmptyIdException("id must be positive");
		this.id = id;
	}

	/**
	 * Convert this model to entity.
	 * @return
	 */
	public abstract Entity toEntity();
}
