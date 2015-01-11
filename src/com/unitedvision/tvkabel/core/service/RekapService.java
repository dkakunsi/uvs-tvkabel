package com.unitedvision.tvkabel.core.service;

import java.time.Month;
import java.util.Date;
import java.util.List;

import com.unitedvision.tvkabel.domain.Kelurahan;
import com.unitedvision.tvkabel.domain.Pegawai;
import com.unitedvision.tvkabel.domain.Pelanggan;
import com.unitedvision.tvkabel.domain.Pelanggan.Status;
import com.unitedvision.tvkabel.domain.Pembayaran;
import com.unitedvision.tvkabel.domain.Perusahaan;

public interface RekapService {
	List<Pelanggan> rekapHarian(Pegawai pegawai, Date hari);
	List<Pelanggan> rekapHarian(Pegawai pegawai, Date hari, int lastNumber);

	List<Pelanggan> rekapTahunan(Perusahaan perusahaan, int tahun);
	List<Pelanggan> rekapTahunan(Perusahaan perusahaan, int tahun, int lastNumber);

	List<Pelanggan> rekapTunggakan(Perusahaan perusahaan, Status status, Integer tunggakan);
	List<Pelanggan> rekapTunggakan(Perusahaan perusahaan, Status status, Integer tunggakan, int lastNumber);
	
	List<Pelanggan> rekapAlamat(Perusahaan perusahaan, Status status, Kelurahan kelurahan, Integer lingkungan);
	List<Pelanggan> rekapAlamat(Perusahaan perusahaan, Status status, Kelurahan kelurahan, Integer lingkungan, Integer lastNumber);

	List<Pembayaran> rekapTagihanBulanan(Perusahaan perusahaan, int tahun, Month bulan);
	List<Pembayaran> rekapTagihanBulanan(Perusahaan perusahaan, int tahun, Month bulan, int lastNumber);
	List<Pembayaran> rekapPembayaranBulanan(Perusahaan perusahaan, int tahun, Month bulan);
	List<Pembayaran> rekapPembayaranBulanan(Perusahaan perusahaan, int tahun, Month bulan, int lastNumber);

	long countRekapTagihanBulanan(Perusahaan perusahaan, int tahun, Month bulan);
	long countRekapPembayaranBulanan(Perusahaan perusahaan, int tahun, Month bulan);
	long countRekapHarian(Pegawai pegawai, Date hari);
	long countRekapTahunan(Perusahaan perusahaan);
	long countRekapTunggakan(Perusahaan perusahaan, Status status, Integer tunggakan);
	long countRekapAlamat(Perusahaan perusahaan, Status status, Kelurahan kelurahan, Integer lingkungan);
}
