package com.unitedvision.tvkabel.service;

import java.util.Date;
import java.util.List;

import com.unitedvision.tvkabel.entity.Operator;
import com.unitedvision.tvkabel.entity.Perusahaan;
import com.unitedvision.tvkabel.exception.ApplicationException;

public interface PerusahaanService extends Service<Perusahaan> {
	Perusahaan getByKode(String kode) throws ApplicationException;
	
	Operator regist(Perusahaan perusahaan) throws ApplicationException;
	
	void setMapLocation(Perusahaan perusahaan, float latitude, float longitude) throws ApplicationException;

	//This will be use for admin's page
	List<Perusahaan> getAll() throws ApplicationException;
	
	long countTagihanBulanBerjalan(Perusahaan perusahaan, Date tanggalAwal, Date tanggalAkhir) throws ApplicationException;
	long countTagihanBulanBerjalan(Perusahaan perusahaan) throws ApplicationException;
	long countEstimasiPemasukanBulanan(Perusahaan perusahaan) throws ApplicationException;
	long countEstimasiTagihanBulanan(Perusahaan perusahaan) throws ApplicationException;
	long countTotalAkumulasiTunggakan(Perusahaan perusahaan) throws ApplicationException;
	long countPemasukanBulanBerjalan(Perusahaan perusahaan) throws ApplicationException;
}
