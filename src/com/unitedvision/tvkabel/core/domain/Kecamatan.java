package com.unitedvision.tvkabel.core.domain;

import com.unitedvision.tvkabel.persistence.entity.KecamatanEntity;
import com.unitedvision.tvkabel.web.model.KecamatanModel;

/**
 * Root of kecamatan domain.
 * 
 * @author Deddy CHristoper Kakunsi
 *
 */
public interface Kecamatan extends Region<KecamatanEntity, KecamatanModel> {
	/**
	 * Return {@link Kota} where kecamatan placed.
	 * @return kota
	 */
	Kota getKota();
	
	/**
	 * Return id of {@link Kota} where kecamatan paced.
	 * @return idKota
	 */
	int getIdKota();
	
	/**
	 * Return name of {@link Kota} where kecamatan placed.
	 * @return namaKota
	 */
	String getNamaKota();
}
