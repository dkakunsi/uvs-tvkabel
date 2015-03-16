package com.unitedvision.tvkabel.core.service.impl;

import java.time.Month;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unitedvision.tvkabel.core.service.PelangganService;
import com.unitedvision.tvkabel.core.service.PembayaranService;
import com.unitedvision.tvkabel.core.service.RekapService;
import com.unitedvision.tvkabel.exception.EmptyIdException;
import com.unitedvision.tvkabel.exception.EntityNotExistException;
import com.unitedvision.tvkabel.persistence.entity.Kelurahan;
import com.unitedvision.tvkabel.persistence.entity.Pegawai;
import com.unitedvision.tvkabel.persistence.entity.Pelanggan;
import com.unitedvision.tvkabel.persistence.entity.Pembayaran;
import com.unitedvision.tvkabel.persistence.entity.Perusahaan;
import com.unitedvision.tvkabel.persistence.entity.Pelanggan.Status;
import com.unitedvision.tvkabel.util.DateUtil;

@Service
public class RekapServiceImpl implements RekapService {
	@Autowired
	private PelangganService pelangganService;
	@Autowired
	private PembayaranService pembayaranService;

	@Override
	public List<Pelanggan> rekapBulanan(Perusahaan perusahaan, Month bulan, int tahun) throws EntityNotExistException, EmptyIdException {
		Date tanggalAwal = DateUtil.getDate(tahun, bulan, 1);
		Date tanggalAkhir = DateUtil.getDate(tahun, bulan, DateUtil.getLastDay(bulan, tahun));

		List<Pelanggan> listPelanggan = pelangganService.get(perusahaan, tanggalAwal, tanggalAkhir);

		return rekapHarian(listPelanggan, tanggalAwal, tanggalAkhir);
	}
	
	@Override
	public List<Pelanggan> rekapHarian(Pegawai pegawai, Date hariAwal, Date hariAkhir) throws EntityNotExistException, EmptyIdException {
		List<Pelanggan> listPelanggan = pelangganService.get(pegawai, hariAwal, hariAkhir);

		return rekapHarian(listPelanggan, hariAwal, hariAkhir);
	}
	
	private List<Pelanggan> rekapHarian(List<Pelanggan> list, Date hariAwal, Date hariAkhir) throws EmptyIdException {
		for (Pelanggan pelanggan : list) {
			List<Pembayaran> listPembayaran;
			try {
				listPembayaran = pembayaranService.get(pelanggan, hariAwal, hariAkhir);
			} catch (EntityNotExistException e) {
				listPembayaran = new ArrayList<>();
			}
			pelanggan.setListPembayaran(listPembayaran);
		}
		
		return list;
	}

	@Override
	public List<Pelanggan> rekapTahunan(Perusahaan perusahaan, int tahun) throws EntityNotExistException {
		List<Pelanggan> listPelanggan = pelangganService.get(perusahaan, Status.AKTIF);

		return rekapTahunan(listPelanggan, tahun);
	}
	
	private List<Pelanggan> rekapTahunan(List<Pelanggan> list, int tahun) {
 		for (Pelanggan pelanggan : list) {
 			List<Pembayaran> listPembayaran;
			try {
				listPembayaran = pembayaranService.get(pelanggan, tahun);
	 			PembayaranServiceImpl.Verifier.verifyListPembayaran(listPembayaran, tahun, pelanggan);
			} catch (EntityNotExistException e) {
				listPembayaran = PembayaranServiceImpl.Verifier.createEmptyListPembayaran();
			}
 			pelanggan.setListPembayaran(listPembayaran); 
 		} 
 		 
 		return list; 
	}

	@Override
	public List<Pelanggan> rekapTunggakan(Perusahaan perusahaan, Status status, Integer tunggakan) throws EntityNotExistException {
		return pelangganService.getByTunggakan(perusahaan, status, tunggakan);
	}
	
	@Override
	public List<Pelanggan> rekapTunggakan(Perusahaan perusahaan, Status status, Integer tunggakanAwal, Integer tunggakanAkhir) throws EntityNotExistException {
		return pelangganService.getByTunggakan(perusahaan, status, tunggakanAwal, tunggakanAkhir);
	}

	@Override
	public List<Pelanggan> rekapAlamat(Perusahaan perusahaan, Status status, Kelurahan kelurahan, Integer lingkungan) throws EntityNotExistException {
		return pelangganService.get(perusahaan, status, kelurahan, lingkungan);
	}

	@Override
	public List<Pelanggan> rekapAlamat(Perusahaan perusahaan) {
		return pelangganService.getOrdered(perusahaan, Pelanggan.Status.AKTIF);
	}
}
