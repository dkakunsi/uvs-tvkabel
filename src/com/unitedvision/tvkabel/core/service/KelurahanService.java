package com.unitedvision.tvkabel.core.service;

import java.util.List;

import com.unitedvision.tvkabel.core.domain.Kecamatan;
import com.unitedvision.tvkabel.core.domain.Kelurahan;
import com.unitedvision.tvkabel.exception.EntityNotExistException;

public interface KelurahanService extends Service<Kelurahan> {
	//This will be used by AJAX-style call later
	List<? extends Kelurahan> getByKecamatan(Kecamatan kecamatan);
	List<? extends Kelurahan> getAll();
	
	Kelurahan getOneByNama(String nama) throws EntityNotExistException;
}
