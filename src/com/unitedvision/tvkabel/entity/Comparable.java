package com.unitedvision.tvkabel.entity;

/**
 * Interface to compare object.
 * 
 * @author Deddy Christoper Kakunsi.
 *
 */
public interface Comparable {
	/**
	 * Compare this object with given parameter.
	 * @param comparable
	 * @return positive if this object greater than given parameter, zero if equals, otherwise negative.
	 */
	int compareWith(Comparable comparable);
	
	/**
	 * Return the comparable value of this object.
	 * @return comparable value of object.
	 */
	int getValue();
}
