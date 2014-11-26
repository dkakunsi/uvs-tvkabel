package com.unitedvision.tvkabel.core.domain;

import com.unitedvision.tvkabel.persistence.entity.KelurahanEntity;
import com.unitedvision.tvkabel.web.model.KelurahanModel;

/**
 * Root of kelurahan domain.
 * 
 * @author Deddy Chrsitoper Kakunsi
 *
 */
public interface Kelurahan extends Region<KelurahanEntity, KelurahanModel> {
	/**
	 * Return {@link Kecamatan} where kelurahan placed.
	 * @return kecamatan
	 */
	Kecamatan getKecamatan();
	
	/**
	 * Return id of {@link Kota} where kelurahan placed.
	 * @return idKota
	 */
	int getIdKota();

	/**
	 * Return name of {@link Kota} where kelurahan placed.
	 * @return namaKota
	 */
	String getNamaKota();

	/**
	 * Return id of {@link Kecamatan} where kelurahan placed.
	 * @return idKecamatan
	 */
	int getIdKecamatan();

	/**
	 * Return name of {@link Kecamatan} where kelurahan placed.
	 * @return namaKecamatan
	 */
	String getNamaKecamatan();
}
