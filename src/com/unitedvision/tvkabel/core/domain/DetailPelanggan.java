package com.unitedvision.tvkabel.core.domain;

import java.util.Date;

import com.unitedvision.tvkabel.persistence.entity.PelangganEntity.Detail;

/**
 * Root of pelanggan's detail
 * 
 * @author Deddy Christoper Kakunsi
 *
 */
public interface DetailPelanggan extends Root<Detail, DetailPelanggan> {
	/**
	 * Return subscription start date.
	 * @return tanggalMulai
	 */
	Date getTanggalMulai();
	
	/**
	 * Set subscription start date.
	 * @param tanggalMulai
	 */
	void setTanggalMulai(Date tanggalMulai);
	
	/**
	 * Return number of television.
	 * @return jumlahTv
	 */
	int getJumlahTv();
	
	/**
	 * Return monthly bill.
	 * @return iuran
	 */
	long getIuran();
	
	/**
	 * Return debt.
	 * @return tunggakan
	 */
	int getTunggakan();

	/**
	 * Set debt.
	 * @param tunggakan
	 */
	void setTunggakan(int tunggakan);
}
