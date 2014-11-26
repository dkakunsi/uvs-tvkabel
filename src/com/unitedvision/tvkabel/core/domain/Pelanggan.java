package com.unitedvision.tvkabel.core.domain;

import java.util.Date;
import java.util.List;

import com.unitedvision.tvkabel.exception.UncompatibleTypeException;
import com.unitedvision.tvkabel.persistence.entity.PelangganEntity;
import com.unitedvision.tvkabel.persistence.entity.PembayaranEntity;
import com.unitedvision.tvkabel.web.model.PelangganModel;

/**
 * Root of pelanggan domain
 * 
 * @author Deddy Christoper Kakunsi
 *
 */
public interface Pelanggan extends CodableDomain<PelangganEntity, PelangganModel>, Removable {
	/**
	 * Pelanggan status
	 * 
	 * @author Dessy Chsristoper Kakunsi
	 *
	 */
	public enum Status {
		/** AKTIF */
		AKTIF,
		/** PUTUS */
		BERHENTI,
		/** HUTANG */
		PUTUS,
		/** REMOVED from database (not deleted) */
		REMOVED;
	}
	
	/**
	 * Return perusahaan which pelanggan subscribes
	 * @return perusahaan
	 */
	Perusahaan getPerusahaan();
	
	/**
	 * Set perusahaan which pelanggan subscribes
	 * @param perusahaan
	 * @throws UncompatibleTypeException perusahaan passed is a wrong object
	 */
	void setPerusahaan(Perusahaan perusahaan) throws UncompatibleTypeException;
	
	/**
	 * Return customer's name.
	 * @return name
	 */
	String getNama();
	
	/**
	 * Return customer's address.
	 * @return address
	 */
	Alamat getAlamat();
	
	/**
	 * Set customer's address.
	 * @param alamat
	 * @throws UncompatibleTypeException wrong alamat object
	 */
	void setAlamat(Alamat alamat) throws UncompatibleTypeException;
	
	/**
	 * Return customer's contact.
	 * @return contact
	 */
	Kontak getKontak();
	
	/**
	 * Return customer's detail.
	 * @return detail
	 */
	DetailPelanggan getDetail();
	
	/**
	 * Return customer's status..
	 * @return status
	 */
	Status getStatus();
	
	/**
	 * Set customer's status.
	 * @param status
	 * @throws StatusChangeException {@code status} parameter is same as {@code status} element.
	 */
	void setStatus(Status status);
	
	/**
	 * Return customer's profession.
	 * @return profession
	 */
	String getProfesi();

	/**
	 * Count customer's default debt (in month). 
	 * Default debt counted by comparing {@link Tagihan} of {@code tanggalMulai} and NOW (month and year).
	 * @return tunggakan
	 */
	int countTunggakan();
	
	/**
	 * Count customer's debt (in month).<br />
	 * Counted by comparing customer's last payment and NOW (month and year).
	 * @param pembayaranTerakhir customer's last payment
	 * @return tunggakan
	 */
	int countTunggakan(Pembayaran pembayaranTerakhir);
	
	/**
	 * Count customer's debt (in month).<br />
	 * Counted by comparing NOW (month and year) with {@code tunggakan} parameter.
	 * @param tagihan customer's last paid bill.
	 * @return tunggakan
	 */
	int countTunggakan(Tagihan tagihan);
	
	/**
	 * Set customer's list of {@link Pembayaran} made by pelanggan.
	 * @param listPembayaran
	 */
	void setListPembayaran(List<PembayaranEntity> listPembayaran);

	/**
	 * Return customer's subscription start date.
	 * @return tanggalMulai
	 */
	Date getTanggalMulai();
	
	/**
	 * Return {@link Kecamatan} where pelanggan lives.
	 * @return kecamatan
	 */
	Kecamatan getKecamatan();
	
	/**
	 * Return {@link Kota} where pelanggan lives.
	 * @return kota
	 */
	Kota getKota();
	
	/**
	 * Return {@link Kelurahan}'s name where pelanggan lives.
	 * @return namaKelurahan
	 */
	String getNamaKelurahan();

	/**
	 * Return lingkungan where pelanggan lives.
	 * @return lingkungan
	 */
	int getLingkungan();
	
	/**
	 * Return customer's address detail.
	 * @return detailAlamat
	 */
	String getDetailAlamat();
	
	/**
	 * Return customer's telephone number.
	 * @return telephone number
	 */
	String getTelepon();
	
	/**
	 * Return customer's cellphone number
	 * @return cellphone number
	 */
	String getHp();
	
	/**
	 * Return customer's email address.
	 * @return email address
	 */
	String getEmail();
	
	/**
	 * Return customer's number of television.
	 * @return jumlahTv
	 */
	int getJumlahTv();
	
	/**
	 * Return customer's monthly bill.
	 * @return iuran
	 */
	long getIuran();
	
	/**
	 * Return customer's debt (in month).
	 * @return tunggakan
	 */
	int getTunggakan();
	
	/**
	 * Set customer's debt (int month).
	 * @param tunggakan
	 */
	void setTunggakan(int tunggakan);
}
