package com.unitedvision.tvkabel.service;

import java.time.Month;
import java.util.Date;
import java.util.List;

import com.unitedvision.tvkabel.entity.Pegawai;
import com.unitedvision.tvkabel.entity.Pelanggan;
import com.unitedvision.tvkabel.entity.Pembayaran;
import com.unitedvision.tvkabel.entity.Pembayaran.Tagihan;
import com.unitedvision.tvkabel.entity.Perusahaan;
import com.unitedvision.tvkabel.exception.ApplicationException;
import com.unitedvision.tvkabel.exception.DataDuplicationException;
import com.unitedvision.tvkabel.exception.EmptyIdException;
import com.unitedvision.tvkabel.exception.EntityNotExistException;
import com.unitedvision.tvkabel.exception.NotPayableCustomerException;
import com.unitedvision.tvkabel.exception.UnpaidBillException;

public interface PembayaranService extends Service<Pembayaran> {
	/**
	 * Payment method for free customer.
	 * @param pelanggan
	 * @return
	 * @throws EntityNotExistException 
	 * @throws EmptyIdException 
	 * @throws DataDuplicationException 
	 * @throws UnpaidBillException 
	 * @throws NotPayableCustomerException 
	 * @throws ApplicationException 
	 */
	void pay(Pelanggan pelanggan) throws EntityNotExistException, NotPayableCustomerException, UnpaidBillException, DataDuplicationException, EmptyIdException, ApplicationException;
	
	/**
	 * Base method for paying bill.
	 * @param pembayaran
	 * @return
	 * @throws NotPayableCustomerException
	 * @throws UnpaidBillException
	 * @throws EntityNotExistException
	 * @throws DataDuplicationException
	 */
	Pembayaran pay(Pembayaran pembayaran) throws NotPayableCustomerException, UnpaidBillException, EntityNotExistException, DataDuplicationException;

	/**
	 * Payment method when customer pay single bill.
	 * @param pelanggan
	 * @param pegawai
	 * @param jumlahPembayaran
	 * @param jumlahBulan
	 * @return Pembayaran terakhir.
	 * @throws NotPayableCustomerException
	 * @throws UnpaidBillException
	 * @throws EntityNotExistException
	 * @throws DataDuplicationException
	 * @throws EmptyIdException
	 * @throws ApplicationException 
	 */
	Pembayaran pay(Pelanggan pelanggan, Pegawai pegawai, long jumlahPembayaran, int jumlahBulan) throws NotPayableCustomerException, UnpaidBillException, EntityNotExistException, DataDuplicationException, EmptyIdException, ApplicationException;
	
	/**
	 * Payment method when customer pay multiple bill.
	 * @param pelanggan
	 * @param pegawai
	 * @param jumlahPembayaran
	 * @param jumlahBulan
	 * @return Pembayaran terakhir.
	 * @throws NotPayableCustomerException
	 * @throws UnpaidBillException
	 * @throws EntityNotExistException
	 * @throws DataDuplicationException
	 * @throws EmptyIdException
	 */
	Pembayaran payList(Pelanggan pelanggan, Pegawai pegawai, long jumlahPembayaran, int jumlahBulan) throws NotPayableCustomerException, UnpaidBillException, EntityNotExistException, DataDuplicationException, EmptyIdException;

	/**
	 * Update the given payment.
	 * @param pembayaran
	 * @return
	 */
	Pembayaran updatePayment(Pembayaran pembayaran);

	Pembayaran updatePayment(Integer id, Long total) throws EntityNotExistException;
	
	/**
	 * Return the last payment of a customer.
	 * @param pelanggan
	 * @return
	 */
	Pembayaran getLast(Pelanggan pelanggan);

	/**
	 * Create list pembayaran, when customer pay multiple bill. 
	 * @param pelanggan
	 * @param pegawai
	 * @param jumlahPembayaran
	 * @param jumlahBulan
	 * @return
	 * @throws EntityNotExistException the given entity is not present in database.
	 * @throws EmptyIdException id passed to entity cannot be negative.
	 * @throws NotPayableCustomerException {@link Pelanggan.Status} is not {@code AKTIF}.
	 * @throws UnpaidBillException the given {@link Pembayaran} cannot be paid.
	 * @throws DataDuplicationException the given {@link Pembayaran} have been paid.
	 */
	List<Pembayaran> createListPembayaran(Pelanggan pelanggan, Pegawai pegawai, long jumlahPembayaran, int jumlahBulan) throws EntityNotExistException, EmptyIdException, NotPayableCustomerException, UnpaidBillException, DataDuplicationException;

	/**
	 * Return the payable bill of a customer. Technically last payment + 1.
	 * @param pelanggan
	 * @return
	 */
	Tagihan getPayableTagihan(Pelanggan pelanggan);

	List<Pembayaran> get(Pelanggan pelanggan, int tahun) throws EntityNotExistException;
	List<Pembayaran> get(Pegawai pegawai, Date tanggalMulai, Date tanggalAkhir) throws EntityNotExistException;
	List<Pembayaran> get(Pelanggan pelanggan, Date tanggalMulai, Date tanggalAkhir) throws EntityNotExistException;
	List<Pembayaran> get(Pelanggan pelanggan, Tagihan tagihanAwal, Tagihan tagihanAkhir) throws EntityNotExistException;

	long count(Pegawai pegawai, Date tanggalMulai, Date tanggalAkhir);
	long count(Pelanggan pelanggan, Date tanggalMulai, Date tanggalAkhir);

	// Rekap
	List<Pelanggan> rekapHarian(Pegawai pegawai, Date hariAwal, Date hariAkhir) throws ApplicationException;

	List<Pelanggan> rekapBulanan(Perusahaan perusahaan, Month bulan, int tahun) throws ApplicationException;

	List<Pelanggan> rekapTahunan(Perusahaan perusahaan, int tahun) throws ApplicationException;
}
