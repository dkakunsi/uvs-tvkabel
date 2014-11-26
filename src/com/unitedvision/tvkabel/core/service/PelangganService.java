package com.unitedvision.tvkabel.core.service;

import java.util.Date;
import java.util.List;

import com.unitedvision.tvkabel.core.domain.Kelurahan;
import com.unitedvision.tvkabel.core.domain.Pegawai;
import com.unitedvision.tvkabel.core.domain.Pelanggan;
import com.unitedvision.tvkabel.core.domain.Pelanggan.Status;
import com.unitedvision.tvkabel.core.domain.Perusahaan;
import com.unitedvision.tvkabel.exception.ApplicationException;

public interface PelangganService extends Service<Pelanggan> {
	void remove(Pelanggan pelanggan) throws ApplicationException;
	void activate(Pelanggan pelanggan) throws ApplicationException;
	void passivate(Pelanggan pelanggan) throws ApplicationException;
	void banned(Pelanggan pelanggan) throws ApplicationException;
	void setMapLocation(Pelanggan pelanggan, float latitude, float longitude) throws ApplicationException;
	void recountTunggakan() throws ApplicationException;
	void recountTunggakan(int tanggal) throws ApplicationException;
	
	Pelanggan getOneByNama(Perusahaan perusahaan, String nama) throws ApplicationException;
	Pelanggan getOneByKode(Perusahaan perusahaan, String kode) throws ApplicationException;

	List<? extends Pelanggan> getByKode(Perusahaan perusahaan, String kode, int page);
	List<? extends Pelanggan> getByNama(Perusahaan perusahaan, String nama, int page);
	List<? extends Pelanggan> get(Perusahaan perusahaan, Status status);
	List<? extends Pelanggan> get(Perusahaan perusahaan, Status status, int page);
	List<? extends Pelanggan> get(Perusahaan perusahaan, Status status, Kelurahan kelurahan, int lingkungan);
	List<? extends Pelanggan> get(Perusahaan perusahaan, Status status, Kelurahan kelurahan, int lingkungan, int page);
	List<? extends Pelanggan> get(Pegawai pegawai, Date tanggalBayar);
	List<? extends Pelanggan> get(Pegawai pegawai, Date tanggalBayar, int page);
	List<? extends Pelanggan> get(Status status, int tanggal);

	List<? extends Pelanggan> getByTunggakan(Perusahaan perusahaan, Status status, int tunggakan);
	List<? extends Pelanggan> getByTunggakan(Perusahaan perusahaan, Status status, int tunggakan, int page);
	List<? extends Pelanggan> getByNama(Perusahaan perusahaan, Status status, String nama, int page);
	List<? extends Pelanggan> getByKode(Perusahaan perusahaan, Status status, String kode, int page);

	long count(Perusahaan perusahaan, Status status);
	long count(Perusahaan perusahaan, Status status, Kelurahan kelurahan, int lingkungan);
	long count(Pegawai pegawai, Date tanggalBayar);
	long countByNama(Perusahaan perusahaan, String nama);
	long countByKode(Perusahaan perusahaan, String kode);
	long countByNama(Perusahaan perusahaan, Status status, String nama);
	long countByKode(Perusahaan perusahaan, Status status, String kode);
	long countByTunggakan(Perusahaan perusahaan, Status status, int tunggakan);
	long countByTunggakanGreaterThan(Perusahaan perusahaan, Status status, int tunggakan);
	long countByTunggakanLessThan(Perusahaan perusahaan, Status status, int tunggakan);
	
	Pelanggan cetakKartu(Pelanggan pelanggan);
}
