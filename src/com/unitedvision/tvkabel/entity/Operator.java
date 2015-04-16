package com.unitedvision.tvkabel.entity;

import com.unitedvision.tvkabel.entity.Pegawai.Role;

/**
 * Operator definition.
 * 
 * @author Deddy Christoper Kakunsi
 *
 */
public interface Operator {
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
	 * Return role
	 * @return role
	 */
	Role getRole();
	
	/**
	 * Return {@link Perusahaan} where operator works.
	 * @return perusahaan
	 */
	Perusahaan getPerusahaan();

	/**
	 * Convert to {@link DOmain} type.
	 * @return domain
	 */
	Pegawai toDomain();
}
