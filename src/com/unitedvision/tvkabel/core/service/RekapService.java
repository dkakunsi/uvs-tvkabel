package com.unitedvision.tvkabel.core.service;

import java.time.Month;
import java.util.Date;
import java.util.List;

import com.unitedvision.tvkabel.persistence.entity.Kelurahan;
import com.unitedvision.tvkabel.persistence.entity.Pegawai;
import com.unitedvision.tvkabel.persistence.entity.Pelanggan;
import com.unitedvision.tvkabel.persistence.entity.Pembayaran;
import com.unitedvision.tvkabel.persistence.entity.Perusahaan;
import com.unitedvision.tvkabel.persistence.entity.Pelanggan.Status;

public interface RekapService {
	List<Pelanggan> rekapHarian(Pegawai pegawai, Date hari);

	List<Pelanggan> rekapTahunan(Perusahaan perusahaan, int tahun);

	List<Pelanggan> rekapTunggakan(Perusahaan perusahaan, Status status, Integer tunggakan);
	
	List<Pelanggan> rekapAlamat(Perusahaan perusahaan, Status status, Kelurahan kelurahan, Integer lingkungan);
	List<Pelanggan> rekapAlamat(Perusahaan perusahaan);

	List<Pembayaran> rekapTagihanBulanan(Perusahaan perusahaan, int tahun, Month bulan);
	List<Pembayaran> rekapPembayaranBulanan(Perusahaan perusahaan, int tahun, Month bulan);
}
