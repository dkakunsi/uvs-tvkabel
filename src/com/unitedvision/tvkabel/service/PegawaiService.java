package com.unitedvision.tvkabel.service;

import java.util.List;

import com.unitedvision.tvkabel.entity.Pegawai;
import com.unitedvision.tvkabel.entity.Perusahaan;
import com.unitedvision.tvkabel.exception.ApplicationException;

public interface PegawaiService extends Service<Pegawai> {
	Pegawai remove(Pegawai entity) throws ApplicationException;
	Pegawai remove(Integer id) throws ApplicationException;
	
	Pegawai add(Pegawai pegawai) throws ApplicationException;

	Pegawai getOne(Perusahaan perusahaan) throws ApplicationException;

	Pegawai getOneByNama(Perusahaan perusahaan, String nama) throws ApplicationException;
	Pegawai getOneByKode(Perusahaan perusahaan, String kode) throws ApplicationException;
	Pegawai getOneByUsername(String username) throws ApplicationException;

	List<Pegawai> get(Perusahaan perusahaan) throws ApplicationException;
	
	List<Pegawai> getByNama(Perusahaan perusahaan, String nama) throws ApplicationException;
	List<Pegawai> getByKode(Perusahaan perusahaan, String kode) throws ApplicationException;
	
	long countByNama(Perusahaan perusahaan, String nama);
	long countByKode(Perusahaan perusahaan, String kode);
}
