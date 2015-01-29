package com.unitedvision.tvkabel.core.service;

import java.time.Month;
import java.util.Date;
import java.util.List;

import com.unitedvision.tvkabel.exception.EmptyIdException;
import com.unitedvision.tvkabel.exception.EntityNotExistException;
import com.unitedvision.tvkabel.persistence.entity.Kelurahan;
import com.unitedvision.tvkabel.persistence.entity.Pegawai;
import com.unitedvision.tvkabel.persistence.entity.Pelanggan;
import com.unitedvision.tvkabel.persistence.entity.Perusahaan;
import com.unitedvision.tvkabel.persistence.entity.Pelanggan.Status;

public interface RekapService {
	List<Pelanggan> rekapHarian(Pegawai pegawai, Date hari, Date hari2) throws EntityNotExistException, EmptyIdException;

	List<Pelanggan> rekapTahunan(Perusahaan perusahaan, int tahun) throws EntityNotExistException, EmptyIdException;

	List<Pelanggan> rekapTunggakan(Perusahaan perusahaan, Status status, Integer tunggakan) throws EntityNotExistException;
	
	List<Pelanggan> rekapAlamat(Perusahaan perusahaan, Status status, Kelurahan kelurahan, Integer lingkungan) throws EntityNotExistException;
	List<Pelanggan> rekapAlamat(Perusahaan perusahaan);

	List<Pelanggan> rekapBulanan(Perusahaan perusahaan, Month bulan, int tahun) throws EntityNotExistException, EmptyIdException;
}
