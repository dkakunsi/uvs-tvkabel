package com.unitedvision.tvkabel.core.service;

import java.util.List;

import com.unitedvision.tvkabel.core.domain.Kecamatan;
import com.unitedvision.tvkabel.core.domain.Kota;

public interface KecamatanService extends Service<Kecamatan> {
	List<? extends Kecamatan> getAll();

	//This will be used by AJAX-style call later
	List<? extends Kecamatan> getByKota(Kota kota);
}
