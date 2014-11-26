package com.unitedvision.tvkabel.core.service;

import java.time.Month;
import java.util.Date;
import java.util.List;

import com.unitedvision.tvkabel.core.domain.Pegawai;
import com.unitedvision.tvkabel.core.domain.Pelanggan;
import com.unitedvision.tvkabel.core.domain.Pembayaran;
import com.unitedvision.tvkabel.core.domain.Perusahaan;
import com.unitedvision.tvkabel.core.domain.Tagihan;
import com.unitedvision.tvkabel.exception.ApplicationException;
import com.unitedvision.tvkabel.exception.DataDuplicationException;
import com.unitedvision.tvkabel.exception.EntityNotExistException;
import com.unitedvision.tvkabel.exception.NotPayableCustomerException;
import com.unitedvision.tvkabel.exception.UncompatibleTypeException;
import com.unitedvision.tvkabel.exception.UnpaidBillException;
import com.unitedvision.tvkabel.persistence.entity.PembayaranEntity;

public interface PembayaranService extends Service<Pembayaran> {
	//This is for deprecation-marking only
	@Deprecated
	Pembayaran save(Pembayaran pembayaran) throws ApplicationException;
	
	Pembayaran pay(Pembayaran pembayaran) throws NotPayableCustomerException, UncompatibleTypeException, UnpaidBillException, EntityNotExistException, DataDuplicationException;
	Pembayaran updatePayment(Pembayaran pembayaran) throws UncompatibleTypeException;

	List<? extends Pembayaran> get(Perusahaan perusahaan, Tagihan tagihan);
	List<? extends Pembayaran> get(Perusahaan perusahaan, int tahun, Month bulan, int page);
	List<? extends Pembayaran> get(Perusahaan perusahaan, Date tanggalMulai, Date tanggalAkhir);
	List<? extends Pembayaran> get(Perusahaan perusahaan, Date tanggalMulai, Date tanggalAkhir, int page);
	List<? extends Pembayaran> get(Pegawai pegawai, Date tanggalMulai, Date tanggalAkhir, int page);
	List<? extends Pembayaran> get(Pelanggan pelanggan, Date tanggalMulai, Date tanggalAkhir, int page);
	List<? extends Pembayaran> get(Pelanggan pelanggan, int tahun);

	List<PembayaranEntity> get(Pelanggan pelanggan, Tagihan tagihanAwal, Tagihan tagihanAkhir);

	Pembayaran getLast(Pelanggan pelanggan) throws EntityNotExistException;

	long count(Perusahaan perusahaan, Date tanggalMulai, Date tanggalAkhir);
	long count(Pegawai pegawai, Date tanggalMulai, Date tanggalAkhir);
	long count(Pelanggan pelanggan, Date tanggalMulai, Date tanggalAkhir);
	long count(Perusahaan perusahaan,  Tagihan tagihan);
	
	Tagihan getPayableTagihan(Pelanggan pelanggan) throws EntityNotExistException;
}
