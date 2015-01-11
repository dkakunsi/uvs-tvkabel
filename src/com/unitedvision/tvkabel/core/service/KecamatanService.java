package com.unitedvision.tvkabel.core.service;

import java.util.List;

import com.unitedvision.tvkabel.persistence.entity.Kecamatan;
import com.unitedvision.tvkabel.persistence.entity.Kota;

public interface KecamatanService extends Service<Kecamatan> {
	List<Kecamatan> getAll();

	//This will be used by AJAX-style call later
	List<Kecamatan> getByKota(Kota kota);
}
