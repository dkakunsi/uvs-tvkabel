package com.unitedvision.tvkabel.service;

import java.util.List;

import com.unitedvision.tvkabel.entity.Kecamatan;
import com.unitedvision.tvkabel.entity.Kelurahan;
import com.unitedvision.tvkabel.exception.EntityNotExistException;

public interface KelurahanService extends Service<Kelurahan> {
	List<Kelurahan> getByKecamatan(Kecamatan kecamatan) throws EntityNotExistException;
	List<Kelurahan> getAll() throws EntityNotExistException;
	
	Kelurahan getOneByNama(String nama) throws EntityNotExistException;
}
