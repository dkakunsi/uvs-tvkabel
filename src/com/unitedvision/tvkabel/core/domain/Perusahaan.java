package com.unitedvision.tvkabel.core.domain;

import com.unitedvision.tvkabel.exception.UncompatibleTypeException;
import com.unitedvision.tvkabel.persistence.entity.PerusahaanEntity;
import com.unitedvision.tvkabel.web.model.PerusahaanModel;

/**
 * Root of perusahaan domain (both entity and model).
 * 
 * @author Deddy Christoper Kakunsi
 *
 */
public interface Perusahaan extends CodableDomain<PerusahaanEntity, PerusahaanModel> {

	/**
	 * Perusahaan status.
	 * @author Deddy Christoper Kakunsi
	 *
	 */
	public enum Status {
		/** AKTIF */
		AKTIF, 
		/** BANNED */
		BANNED
	}

	/** DEFAULT KODE FORMAT */
	String DEFAULT_KODE_FORMAT = "COM%d";

	/**
	 * Return perusahaan's name
	 * @return perusahaan's name
	 */
	String getNama();
	
	/**
	 * Return perusahaan's address
	 * @return perusahaan's address
	 */
	Alamat getAlamat();
	
	/**
	 * Set perusahaan's alamat
	 * @param alamat
	 * @throws UncompatibleTypeException the given alamat is not compatible with the domain type.
	 */
	void setAlamat(Alamat alamat) throws UncompatibleTypeException;

	/**
	 * Return perusahaan's contact
	 * @return [erusahaan's contact
	 */
	Kontak getKontak();
	
	/**
	 * Return perusahaan's iuran
	 * @return perusahaan's iuran
	 */
	long getIuran();
	
	/**
	 * Return perusahaan's status
	 * @return perusahaan's status
	 */
	Status getStatus();
	
	/**
	 * Generate perusahaan's code
	 * @param count
	 * @return perusahaan's code
	 */
	String generateKode(long count);

	/**
	 * Return {@link Kelurahan}'s name where perusahaan belongs.
	 * @return {@link Kelurahan}
	 */
	String getNamaKelurahan();
	
	/**
	 * Return lingkungan where perusahaan belongs
	 * @return lingkungan
	 */
	int getLingkungan();
	
	/**
	 * Return perusahaan's detail address
	 * @return detail address
	 */
	String getDetailAlamat();
	
	/**
	 * Return perusahaan's telephone number
	 * @return telephone number
	 */
	String getTelepon();

	/**
	 * Return perusahaan's cellphone number
	 * @return cellphone number
	 */
	String getHp();

	/**
	 * Return perusahaan's email address
	 * @return email address
	 */
	String getEmail();
}
