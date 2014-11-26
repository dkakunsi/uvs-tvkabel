package com.unitedvision.tvkabel.core.domain;

import com.unitedvision.tvkabel.persistence.entity.KontakValue;

/**
 * Representation of Kontak(Contact) value.
 * 
 * @author Deddy Christoper Kakunsi.
 *
 */
public interface Kontak extends Root<KontakValue, Kontak>{
	/**
	 * Return telephone number
	 * @return telephone number
	 */
	String getTelepon();
	
	/**
	 * Return cellphone number.
	 * @return cellphone number.
	 */
	String getHp();
	
	/**
	 * Return email address.
	 * @return email address.
	 */
	String getEmail();
}
