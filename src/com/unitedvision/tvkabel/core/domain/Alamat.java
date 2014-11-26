package com.unitedvision.tvkabel.core.domain;

import com.unitedvision.tvkabel.persistence.entity.AlamatValue;

/**
 * Representation of alamat(address) value.
 * 
 * @author Deddy CHristoper Kakunsi.
 *
 */
public interface Alamat extends Root<AlamatValue, Alamat> {
	/**
	 * Returns {@link Kelurahan} object.
	 * 
	 * @return {@link Kelurahan} kelurahan.
	 */
	Kelurahan getKelurahan();

	/**
	 * Returns lingkungan.
	 * @return {@link int} lingkungan.
	 */
	int getLingkungan();

	/**
	 * Returns detailAlamat.
	 * @return {@link String} detailAlamat.
	 */
	String getDetailAlamat();
	
	/**
	 * Returns {@link Kecamatan} object.
	 * 
	 * @return {@link Kecamatan} kecamatan.
	 */
	Kecamatan getKecamatan();

	/**
	 * Returns {@link Kota} object.
	 * 
	 * @return {@link Kota} kota.
	 */
	Kota getKota();

	/**
	 * Returns latitude position on map.
	 * @return latitude position
	 */
	float getLatitude();

	/**
	 * Returns longitude position on map.
	 * @return longitude position
	 */
	float getLongitude();
}
