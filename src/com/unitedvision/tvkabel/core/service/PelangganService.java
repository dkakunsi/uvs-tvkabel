package com.unitedvision.tvkabel.core.service;

import java.util.Date;
import java.util.List;

import com.unitedvision.tvkabel.domain.entity.Kelurahan;
import com.unitedvision.tvkabel.domain.entity.Pegawai;
import com.unitedvision.tvkabel.domain.entity.Pelanggan;
import com.unitedvision.tvkabel.domain.entity.Perusahaan;
import com.unitedvision.tvkabel.domain.entity.Pelanggan.Status;
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

	List<Pelanggan> getByKode(Perusahaan perusahaan, String kode, int page);
	List<Pelanggan> getByNama(Perusahaan perusahaan, String nama, int page);
	List<Pelanggan> get(Perusahaan perusahaan, Status status);
	List<Pelanggan> get(Perusahaan perusahaan, Status status, int page);
	List<Pelanggan> get(Perusahaan perusahaan, Status status, Kelurahan kelurahan, int lingkungan);
	List<Pelanggan> get(Perusahaan perusahaan, Status status, Kelurahan kelurahan, int lingkungan, int page);
	List<Pelanggan> get(Pegawai pegawai, Date tanggalBayar);
	List<Pelanggan> get(Pegawai pegawai, Date tanggalBayar, int page);
	List<Pelanggan> get(Status status, int tanggal);

	List<Pelanggan> getByTunggakan(Perusahaan perusahaan, Status status, int tunggakan);
	List<Pelanggan> getByTunggakan(Perusahaan perusahaan, Status status, int tunggakan, int page);
	List<Pelanggan> getByNama(Perusahaan perusahaan, Status status, String nama, int page);
	List<Pelanggan> getByKode(Perusahaan perusahaan, Status status, String kode, int page);

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
	List<Pelanggan> cetakKartu(List<Pelanggan> listPelanggan);
	
	String resetKode(Perusahaan perusahaan, Kelurahan kelurahan, int lingkungan);
	String resetKode(Perusahaan perusahaan);
}
