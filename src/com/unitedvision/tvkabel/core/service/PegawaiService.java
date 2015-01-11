package com.unitedvision.tvkabel.core.service;

import java.util.List;

import com.unitedvision.tvkabel.domain.Pegawai;
import com.unitedvision.tvkabel.domain.Perusahaan;
import com.unitedvision.tvkabel.exception.EntityNotExistException;
import com.unitedvision.tvkabel.exception.StatusChangeException;

public interface PegawaiService extends Service<Pegawai> {
	void remove(Pegawai entity) throws EntityNotExistException, StatusChangeException;
	
	Pegawai getOneByNama(Perusahaan perusahaan, String nama) throws EntityNotExistException;
	Pegawai getOneByKode(Perusahaan perusahaan, String kode) throws EntityNotExistException;
	Pegawai getOneByUsername(String username) throws EntityNotExistException;
	
	List<Pegawai> get(Perusahaan perusahaan);
	List<Pegawai> get(Perusahaan perusahaan, int page);
	
	List<Pegawai> getByNama(Perusahaan perusahaan, String nama, int page);
	List<Pegawai> getByKode(Perusahaan perusahaan, String kode, int page);
	
	long count(Perusahaan perusahaan);
	long countByNama(Perusahaan perusahaan, String nama);
	long countByKode(Perusahaan perusahaan, String kode);
}
