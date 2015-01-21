package com.unitedvision.tvkabel.core.service;

import java.util.List;

import com.unitedvision.tvkabel.exception.EntityNotExistException;
import com.unitedvision.tvkabel.persistence.entity.Kecamatan;
import com.unitedvision.tvkabel.persistence.entity.Kota;

public interface KecamatanService extends Service<Kecamatan> {
	List<Kecamatan> getAll() throws EntityNotExistException;
	List<Kecamatan> getByKota(Kota kota) throws EntityNotExistException;
}
