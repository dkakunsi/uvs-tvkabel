package com.unitedvision.tvkabel.core.service;

import java.time.Month;
import java.util.Date;
import java.util.List;

import com.unitedvision.tvkabel.core.domain.Kelurahan;
import com.unitedvision.tvkabel.core.domain.Pegawai;
import com.unitedvision.tvkabel.core.domain.Pelanggan;
import com.unitedvision.tvkabel.core.domain.Pelanggan.Status;
import com.unitedvision.tvkabel.core.domain.Pembayaran;
import com.unitedvision.tvkabel.core.domain.Perusahaan;

public interface RekapService {
	List<? extends Pelanggan> rekapHarian(Pegawai pegawai, Date hari);
	List<? extends Pelanggan> rekapHarian(Pegawai pegawai, Date hari, int lastNumber);

	List<? extends Pelanggan> rekapTahunan(Perusahaan perusahaan, int tahun);
	List<? extends Pelanggan> rekapTahunan(Perusahaan perusahaan, int tahun, int lastNumber);

	List<? extends Pelanggan> rekapTunggakan(Perusahaan perusahaan, Status status, Integer tunggakan);
	List<? extends Pelanggan> rekapTunggakan(Perusahaan perusahaan, Status status, Integer tunggakan, int lastNumber);
	
	List<? extends Pelanggan> rekapAlamat(Perusahaan perusahaan, Status status, Kelurahan kelurahan, Integer lingkungan);
	List<? extends Pelanggan> rekapAlamat(Perusahaan perusahaan, Status status, Kelurahan kelurahan, Integer lingkungan, Integer lastNumber);

	List<? extends Pembayaran> rekapTagihanBulanan(Perusahaan perusahaan, int tahun, Month bulan);
	List<? extends Pembayaran> rekapTagihanBulanan(Perusahaan perusahaan, int tahun, Month bulan, int lastNumber);
	List<? extends Pembayaran> rekapPembayaranBulanan(Perusahaan perusahaan, int tahun, Month bulan);
	List<? extends Pembayaran> rekapPembayaranBulanan(Perusahaan perusahaan, int tahun, Month bulan, int lastNumber);

	long countRekapTagihanBulanan(Perusahaan perusahaan, int tahun, Month bulan);
	long countRekapPembayaranBulanan(Perusahaan perusahaan, int tahun, Month bulan);
	long countRekapHarian(Pegawai pegawai, Date hari);
	long countRekapTahunan(Perusahaan perusahaan);
	long countRekapTunggakan(Perusahaan perusahaan, Status status, Integer tunggakan);
	long countRekapAlamat(Perusahaan perusahaan, Status status, Kelurahan kelurahan, Integer lingkungan);
}
