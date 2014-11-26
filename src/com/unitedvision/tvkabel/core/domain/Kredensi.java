package com.unitedvision.tvkabel.core.domain;

import com.unitedvision.tvkabel.persistence.entity.PegawaiEntity.KredensiValue;

/**
 * Root of kredensi.
 * 
 * @author Deddy Christoper Kakunsi
 *
 */
public interface Kredensi extends Root<KredensiValue, Kredensi> {
	/**
	 * Credential Role for Authorization.
	 * 
	 * @author Deddy Christoper Kakunsi
	 *
	 */
	public enum Role {
		/** ADMIN */
		ADMIN,
		/** OWNER */
		OWNER,
		/** OPERATOR */
		OPERATOR, 
		/** NOTHING */
		NOTHING,
		/** GUEST */
		GUEST;
	}
	
	/**
	 * Return username.
	 * @return username
	 */
	String getUsername();
	
	/**
	 * Return password.
	 * @return password
	 */
	String getPassword();
	
	/**
	 * Return role.
	 * @return role
	 */
	Role getRole();
}
