package com.unitedvision.tvkabel.core.service;

import java.util.List;

import com.unitedvision.tvkabel.exception.EntityNotExistException;
import com.unitedvision.tvkabel.persistence.entity.Kecamatan;
import com.unitedvision.tvkabel.persistence.entity.Kelurahan;

public interface KelurahanService extends Service<Kelurahan> {
	List<Kelurahan> getByKecamatan(Kecamatan kecamatan);
	List<Kelurahan> getAll();
	
	Kelurahan getOneByNama(String nama) throws EntityNotExistException;
}
