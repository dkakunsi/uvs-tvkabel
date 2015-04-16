package com.unitedvision.tvkabel.entity;

import com.unitedvision.tvkabel.exception.StatusChangeException;

/**
 * Interface to remove an object from it's container.
 * 
 * @author Deddy Christoper Kakunsi
 *
 */
public interface Removable {
	/**
	 * Remove an object from it's container
	 * @throws StatusChangeException customer has already been removed before
	 */
	void remove() throws StatusChangeException;
}
