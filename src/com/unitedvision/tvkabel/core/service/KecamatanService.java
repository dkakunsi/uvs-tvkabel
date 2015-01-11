package com.unitedvision.tvkabel.core.service;

import java.util.List;

import com.unitedvision.tvkabel.persistence.domain.Kecamatan;
import com.unitedvision.tvkabel.persistence.domain.Kota;

public interface KecamatanService extends Service<Kecamatan> {
	List<Kecamatan> getAll();

	//This will be used by AJAX-style call later
	List<Kecamatan> getByKota(Kota kota);
}
