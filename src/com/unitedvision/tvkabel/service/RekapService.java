package com.unitedvision.tvkabel.service;

import java.time.Month;
import java.util.Date;
import java.util.List;

import com.unitedvision.tvkabel.entity.Kelurahan;
import com.unitedvision.tvkabel.entity.Pegawai;
import com.unitedvision.tvkabel.entity.Pelanggan;
import com.unitedvision.tvkabel.entity.Perusahaan;
import com.unitedvision.tvkabel.entity.Pelanggan.Status;
import com.unitedvision.tvkabel.exception.ApplicationException;

public interface RekapService {
	List<Pelanggan> rekapHarian(Pegawai pegawai, Date hari, Date hari2) throws ApplicationException;

	List<Pelanggan> rekapTahunan(Perusahaan perusahaan, int tahun) throws ApplicationException;

	List<Pelanggan> rekapTunggakan(Perusahaan perusahaan, Status status, Integer tunggakan) throws ApplicationException;
	List<Pelanggan> rekapTunggakan(Perusahaan perusahaan, Status status, Integer tunggakanAwal, Integer tunggakanAkhir) throws ApplicationException;
	
	List<Pelanggan> rekapAlamat(Perusahaan perusahaan, Status status, Kelurahan kelurahan, Integer lingkungan) throws ApplicationException;
	List<Pelanggan> rekapAlamat(Perusahaan perusahaan) throws ApplicationException;

	List<Pelanggan> rekapBulanan(Perusahaan perusahaan, Month bulan, int tahun) throws ApplicationException;
}
