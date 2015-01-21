package com.unitedvision.tvkabel.core.service;

import java.util.Date;
import java.util.List;

import com.unitedvision.tvkabel.exception.DataDuplicationException;
import com.unitedvision.tvkabel.exception.EntityNotExistException;
import com.unitedvision.tvkabel.exception.NotPayableCustomerException;
import com.unitedvision.tvkabel.exception.UnpaidBillException;
import com.unitedvision.tvkabel.persistence.entity.Pegawai;
import com.unitedvision.tvkabel.persistence.entity.Pelanggan;
import com.unitedvision.tvkabel.persistence.entity.Pembayaran;
import com.unitedvision.tvkabel.persistence.entity.Perusahaan;
import com.unitedvision.tvkabel.persistence.entity.Pembayaran.Tagihan;

public interface PembayaranService extends Service<Pembayaran> {
	Pembayaran pay(Pembayaran pembayaran) throws NotPayableCustomerException, UnpaidBillException, EntityNotExistException, DataDuplicationException;
	Pembayaran updatePayment(Pembayaran pembayaran);
	Pembayaran getLast(Pelanggan pelanggan) throws EntityNotExistException;
	
	Tagihan getPayableTagihan(Pelanggan pelanggan) throws EntityNotExistException;

	List<Pembayaran> get(Perusahaan perusahaan, Tagihan tagihan) throws EntityNotExistException;
	List<Pembayaran> get(Perusahaan perusahaan, Date tanggalMulai, Date tanggalAkhir) throws EntityNotExistException;
	List<Pembayaran> get(Pelanggan pelanggan, int tahun) throws EntityNotExistException;

	List<Pembayaran> get(Pegawai pegawai, Date tanggalMulai, Date tanggalAkhir, int page) throws EntityNotExistException;
	List<Pembayaran> get(Pelanggan pelanggan, Date tanggalMulai, Date tanggalAkhir, int page) throws EntityNotExistException;

	List<Pembayaran> get(Pelanggan pelanggan, Tagihan tagihanAwal, Tagihan tagihanAkhir) throws EntityNotExistException;

	long count(Pegawai pegawai, Date tanggalMulai, Date tanggalAkhir);
	long count(Pelanggan pelanggan, Date tanggalMulai, Date tanggalAkhir);
}
