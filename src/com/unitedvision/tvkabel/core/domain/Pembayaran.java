package com.unitedvision.tvkabel.core.domain;

import java.time.Month;
import java.util.Date;

import com.unitedvision.tvkabel.exception.UncompatibleTypeException;
import com.unitedvision.tvkabel.persistence.entity.PembayaranEntity;
import com.unitedvision.tvkabel.web.model.PembayaranModel;

/**
 * Root of pembayaran domain
 * 
 * @author Deddy Christoper Kakunsi
 *
 */
public interface Pembayaran extends CodableDomain<PembayaranEntity, PembayaranModel> {
	/**
	 * Return {@link Pelanggan} who is paying.
	 * @return pelanggan
	 */
	Pelanggan getPelanggan();
	
	/**
	 * Set {@link Pelanggan} who is paying.
	 * @param pelanggan
	 * @throws UncompatibleTypeException pelanggan is wrong object
	 */
	void setPelanggan(Pelanggan pelanggan) throws UncompatibleTypeException;
	
	/**
	 * Return {@link Pegawai} who handle payment.
	 * @return pegawai
	 */
	Pegawai getPegawai();
	
	/**
	 * Set {@link Pegawai} who handle payment.
	 * @param pegawai
	 * @throws UncompatibleTypeException pelanggan is wrong object
	 */
	void setPegawai(Pegawai pegawai) throws UncompatibleTypeException;
	
	/**
	 * Return payment date.
	 * @return tanggalBayar
	 */
	Date getTanggalBayar();
	
	/**
	 * Return bill count
	 * @return jumlahBayar
	 */
	long getJumlahBayar();

	/**
	 * Set jumlahBayar value.
	 * @param jumlahBayar
	 */
	void setJumlahBayar(long jumlahBayar);
	
	/**
	 * Return bill
	 * @return tagihan
	 */
	Tagihan getTagihan();
	
	/**
	 * Compare pembayaran with last.
	 * @param last
	 * @return true if equals, otherwise false
	 */
	boolean isPaid(Pembayaran last);
	
	/**
	 * Whether this object is preceding to pembayaran.
	 * @param pembayaran
	 * @return
	 */
	boolean isPreceding(Pembayaran pembayaran);

	/**
	 * Return month of bill
	 * @return bulan
	 */
	Month getBulan();
	
	/**
	 * Return year of bill
	 * @return tahun
	 */
	int getTahun();
	
	/**
	 * Return id of {@link Pegawai} who handle this payment.
	 * @return idPegawai
	 */
	int getIdPegawai();
	
	/**
	 * Return id of (@link Pelanggan} who is paying.
	 * @return idPelanggan
	 */
	int getIdPelanggan();
	
	/**
	 * Return name of {@link Pegawai} who handle this payment.
	 * @return namaPegawai
	 */
	String getNamaPegawai();
	
	/**
	 * Return name of {@link Pelanggan} who is paying.
	 * @return namaPelanggan
	 */
	String getNamaPelanggan();
}
