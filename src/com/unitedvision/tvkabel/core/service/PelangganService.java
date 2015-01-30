package com.unitedvision.tvkabel.core.service;

import java.util.Date;
import java.util.List;

import com.unitedvision.tvkabel.exception.ApplicationException;
import com.unitedvision.tvkabel.exception.EntityNotExistException;
import com.unitedvision.tvkabel.persistence.entity.Kelurahan;
import com.unitedvision.tvkabel.persistence.entity.Pegawai;
import com.unitedvision.tvkabel.persistence.entity.Pelanggan;
import com.unitedvision.tvkabel.persistence.entity.Perusahaan;
import com.unitedvision.tvkabel.persistence.entity.Pelanggan.Status;

public interface PelangganService extends Service<Pelanggan> {
	void remove(Pelanggan pelanggan) throws ApplicationException;
	void activate(Pelanggan pelanggan) throws ApplicationException;
	void passivate(Pelanggan pelanggan) throws ApplicationException;
	void banned(Pelanggan pelanggan) throws ApplicationException;
	void setMapLocation(Pelanggan pelanggan, float latitude, float longitude) throws ApplicationException;
	void recountTunggakan() throws ApplicationException;
	void recountTunggakan(int tanggal) throws ApplicationException;
	void recountTunggakan(Pelanggan pelanggan) throws EntityNotExistException;
	
	Pelanggan getOneByNama(Perusahaan perusahaan, String nama) throws ApplicationException;
	Pelanggan getOneByKode(Perusahaan perusahaan, String kode) throws ApplicationException;

	List<Pelanggan> get(Perusahaan perusahaan, int nomorBuku, int page) throws EntityNotExistException;
	List<Pelanggan> get(Perusahaan perusahaan, Status status, int nomorBuku, int page) throws EntityNotExistException;
	List<Pelanggan> get(Perusahaan perusahaan, Status status) throws EntityNotExistException;
	List<Pelanggan> get(Perusahaan perusahaan, Status status, int page) throws EntityNotExistException;
	List<Pelanggan> get(Perusahaan perusahaan, Status status, Kelurahan kelurahan, int lingkungan) throws EntityNotExistException;
	List<Pelanggan> get(Pegawai pegawai, Date tanggalBayarAwal, Date tanggalBayarAkhir) throws EntityNotExistException;
	List<Pelanggan> get(Perusahaan perusahaan, Date tanggalBayarAwal, Date tanggalBayarAkhir) throws EntityNotExistException;
	List<Pelanggan> get(Status status, int tanggal) throws EntityNotExistException;
	List<Pelanggan> getOrdered(Perusahaan perusahaan, Status status);

	List<Pelanggan> getByTunggakan(Perusahaan perusahaan, Status status, int tunggakan) throws EntityNotExistException;
	List<Pelanggan> getByKode(Perusahaan perusahaan, String kode, int page) throws EntityNotExistException;
	List<Pelanggan> getByKode(Perusahaan perusahaan, Status status, String kode, int page) throws EntityNotExistException;
	List<Pelanggan> getByNama(Perusahaan perusahaan, String nama, int page) throws EntityNotExistException;
	List<Pelanggan> getByNama(Perusahaan perusahaan, Status status, String nama, int page) throws EntityNotExistException;

	long count(Perusahaan perusahaan, Status status);
	long countByNama(Perusahaan perusahaan, String nama);
	long countByNama(Perusahaan perusahaan, Status status, String nama);
	long countByKode(Perusahaan perusahaan, String kode);
	long countByKode(Perusahaan perusahaan, Status status, String kode);
	long countByNomorBuku(Perusahaan perusahaan, int nomorBuku);
	long countByNomorBuku(Perusahaan perusahaan, Status status, int nomorBuku);
	long countByTunggakan(Perusahaan perusahaan, Status status, int tunggakan);
	long countByTunggakanGreaterThan(Perusahaan perusahaan, Status status, int tunggakan);
	long countByTunggakanLessThan(Perusahaan perusahaan, Status status, int tunggakan);
	
	Pelanggan cetakKartu(Pelanggan pelanggan);
	List<Pelanggan> cetakKartu(List<Pelanggan> listPelanggan);
	
	String resetKode(Perusahaan perusahaan, Kelurahan kelurahan, int lingkungan);
	String resetKode(Perusahaan perusahaan);
}
