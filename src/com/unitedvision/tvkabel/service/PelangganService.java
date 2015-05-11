package com.unitedvision.tvkabel.service;

import java.util.Date;
import java.util.List;

import com.unitedvision.tvkabel.entity.Kelurahan;
import com.unitedvision.tvkabel.entity.Pegawai;
import com.unitedvision.tvkabel.entity.Pelanggan;
import com.unitedvision.tvkabel.entity.Perusahaan;
import com.unitedvision.tvkabel.entity.Pelanggan.Status;
import com.unitedvision.tvkabel.exception.ApplicationException;

public interface PelangganService extends Service<Pelanggan> {
	Pelanggan add(Pelanggan pelanggan) throws ApplicationException;

	Pelanggan remove(Pelanggan pelanggan) throws ApplicationException;
	Pelanggan remove(Integer id) throws ApplicationException;

	Pelanggan activate(Pelanggan pelanggan, String keterangan) throws ApplicationException;
	Pelanggan activate(Integer id, String keterangan) throws ApplicationException;
	
	Pelanggan passivate(Pelanggan pelanggan, String keterangan) throws ApplicationException;
	Pelanggan passivate(Integer id, String keterangan) throws ApplicationException;
	
	Pelanggan banned(Pelanggan pelanggan, String keterangan) throws ApplicationException;
	Pelanggan banned(Integer id, String keterangan) throws ApplicationException;
	
	Pelanggan free(Pelanggan pelanggan, String keterangan) throws ApplicationException;
	Pelanggan free(Integer id, String keterangan) throws ApplicationException;
	
	Pelanggan setMapLocation(Pelanggan pelanggan, float latitude, float longitude) throws ApplicationException;
	Pelanggan setMapLocation(Integer id, float latitude, float longitude) throws ApplicationException;

	void recountTunggakan() throws ApplicationException;
	void recountTunggakan(String tanggal) throws ApplicationException;
	Pelanggan recountTunggakan(Pelanggan pelanggan) throws ApplicationException;
	
	Pelanggan updateLastPayment(Pelanggan pelanggan) throws ApplicationException;
	
	Pelanggan getOneByNama(Perusahaan perusahaan, String nama) throws ApplicationException;
	Pelanggan getOneByKode(Perusahaan perusahaan, String kode) throws ApplicationException;

	List<Pelanggan> get(Perusahaan perusahaan, Integer nomor) throws ApplicationException;
	List<Pelanggan> get(Perusahaan perusahaan, Integer nomor, Status status) throws ApplicationException;
	
	List<Pelanggan> get(Perusahaan perusahaan, Status status) throws ApplicationException;
	List<Pelanggan> get(Perusahaan perusahaan, Status status, Kelurahan kelurahan, int lingkungan) throws ApplicationException;

	List<Pelanggan> get(Pegawai pegawai, Date tanggalBayarAwal, Date tanggalBayarAkhir) throws ApplicationException;
	List<Pelanggan> get(Perusahaan perusahaan, Date tanggalBayarAwal, Date tanggalBayarAkhir) throws ApplicationException;
	List<Pelanggan> get(Status status, String tanggal) throws ApplicationException;
	
	List<Pelanggan> getOrdered(Perusahaan perusahaan, Status status) throws ApplicationException;

	List<Pelanggan> getByTunggakan(Perusahaan perusahaan, Status status, int tunggakan) throws ApplicationException;
	List<Pelanggan> getByTunggakan(Perusahaan perusahaan, Status status, int tunggakanAwal, int tunggakanAkhir) throws ApplicationException;

	List<Pelanggan> getByKode(Perusahaan perusahaan, String kode) throws ApplicationException;
	List<Pelanggan> getByKode(Perusahaan perusahaan, String kode, Status status) throws ApplicationException;
	
	List<Pelanggan> getByNama(Perusahaan perusahaan, String nama, Status status) throws ApplicationException;
	List<Pelanggan> getByNama(Perusahaan perusahaan, String nama) throws ApplicationException;

	long count(Perusahaan perusahaan, Status status) throws ApplicationException;

	long countByNama(Perusahaan perusahaan, String nama) throws ApplicationException;
	long countByNama(Perusahaan perusahaan, String nama, Status status) throws ApplicationException;
	
	long countByKode(Perusahaan perusahaan, String kode) throws ApplicationException;
	long countByKode(Perusahaan perusahaan, String kode, Status status) throws ApplicationException;
	
	long countByNomorBuku(Perusahaan perusahaan, Integer nomorBuku) throws ApplicationException;
	long countByNomorBuku(Perusahaan perusahaan, Integer nomorBuku, Status status) throws ApplicationException;
	
	long countByTunggakan(Perusahaan perusahaan, Integer tunggakan, Status status) throws ApplicationException;
	long countByTunggakanGreaterThan(Perusahaan perusahaan, Integer tunggakan, Status status) throws ApplicationException;
	long countByTunggakanLessThan(Perusahaan perusahaan, Integer tunggakan, Status status) throws ApplicationException;
	
	Pelanggan cetakKartu(Pelanggan pelanggan) throws ApplicationException;
	Pelanggan cetakKartu(Pelanggan pelanggan, Integer tahun) throws ApplicationException;
	
	List<Pelanggan> cetakKartu(List<Pelanggan> listPelanggan) throws ApplicationException;
	
	String resetKode(Perusahaan perusahaan) throws ApplicationException;
	String resetKode(Perusahaan perusahaan, Kelurahan kelurahan, Integer lingkungan) throws ApplicationException;

	void updateTunggakan(Integer id, Integer tunggakan) throws ApplicationException;
}
