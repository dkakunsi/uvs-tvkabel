package com.unitedvision.tvkabel.service;

import java.util.List;

import com.unitedvision.tvkabel.entity.Alat;
import com.unitedvision.tvkabel.entity.Kelurahan;
import com.unitedvision.tvkabel.entity.Perusahaan;
import com.unitedvision.tvkabel.exception.ApplicationException;

public interface AlatService {

	Alat get(Integer id) throws ApplicationException;

	Alat add(Alat alat) throws ApplicationException;

	Alat save(Alat alat) throws ApplicationException;

	Alat remove(Integer id) throws ApplicationException;

	Alat remove(Alat alat) throws ApplicationException;

	Alat subtitute(Integer idLama, Integer idBaru) throws ApplicationException;

	List<Alat> getBySource(Integer idSource) throws ApplicationException;

	List<Alat> getBySource(Alat alat) throws ApplicationException;

	List<Alat> getByAlamat(Perusahaan perusahaan, Integer idKelurahan, Integer lingkungan) throws ApplicationException;

	List<Alat> getByAlamat(Perusahaan perusahaan, Kelurahan kelurahan, Integer lingkungan) throws ApplicationException;
}
