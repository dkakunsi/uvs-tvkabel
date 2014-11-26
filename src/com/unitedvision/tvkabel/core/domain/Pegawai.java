package com.unitedvision.tvkabel.core.domain;

import com.unitedvision.tvkabel.core.domain.Kredensi.Role;
import com.unitedvision.tvkabel.persistence.entity.PegawaiEntity;
import com.unitedvision.tvkabel.web.model.PegawaiModel;

/**
 * Root of pegawai domain
 * 
 * @author Deddy Christoper Kakunsi
 *
 */
public interface Pegawai extends CodableDomain<PegawaiEntity, PegawaiModel>, Operator, Removable  {
	/**
	 * Employee's status.
	 * 
	 * @author Deddy Christoper Kakunsi
	 *
	 */
	public enum Status {
		/**
		 * AKTIF
		 */
		AKTIF,
		/**
		 * REMOVED
		 */
		REMOVED
	}
	
	/** DEFAULT KODE FORMAT */
	String DEFAULT_KODE_FORMAT = "%sPG%d";
	
	/**
	 * Return perusahaan where pegawai works
	 * @return perusahaan
	 */
	Perusahaan getPerusahaan();

	/**
	 * Return pegawai's name
	 * @return name
	 */
	String getNama();
	
	/**
	 * Whether pegawai is active or not. Not active means removed from database (not deleted).
	 * @return true if active, otherwise false
	 */
	Status getStatus();
	
	/**
	 * Set pegawai's activation state
	 * @param aktif
	 */
	void setStatus(Status status);
	
	/**
	 * Return employee {@link Kredensi}
	 * @return {@link Kredensi}
	 */
	Kredensi getKredensi();
	
	/**
	 * Return employee object in {@link Operator} type
	 * @return operator
	 */
	Operator toOperator();
	
	/**
	 * Generate code for employee
	 * @param count
	 * @return code
	 */
	String generateKode(long count);
	
	/**
	 * Return employee's role
	 * @return role
	 */
	Role getRole();
	
	/**
	 * Return employee's password used to login
	 * @return password
	 */
	String getPassword();
	
	/**
	 * Return employee's username used to login
	 * @return username
	 */
	String getUsername();
}
