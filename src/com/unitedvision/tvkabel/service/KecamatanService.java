package com.unitedvision.tvkabel.service;

import java.util.List;

import com.unitedvision.tvkabel.entity.Kecamatan;
import com.unitedvision.tvkabel.entity.Kota;
import com.unitedvision.tvkabel.exception.EntityNotExistException;

public interface KecamatanService extends Service<Kecamatan> {
	List<Kecamatan> getAll() throws EntityNotExistException;
	List<Kecamatan> getByKota(Kota kota) throws EntityNotExistException;
}
