package com.unitedvision.tvkabel.util;

import com.unitedvision.tvkabel.domain.Pegawai;
import com.unitedvision.tvkabel.domain.Pelanggan;
import com.unitedvision.tvkabel.domain.Perusahaan;

/**
 * Class for utilize status creation.
 * 
 * @author Deddy Christoper Kakunsi
 *
 */
public class StatusUtil {

	/**
	 * Returns customer's status from the given string.
	 * @param status can be both upper case and lower case.
	 * @return Customer's status.
	 */
	public static Pelanggan.Status getPelangganStatus(String status) {
		status = status.toUpperCase();
		
		return Pelanggan.Status.valueOf(status);
	}
	
	/**
	 * Returns employee's status from the given string.
	 * @param status can be both upper case and lower case.
	 * @return Employee's status.
	 */
	public static Pegawai.Status getPegawaiStatus(String status) {
		status = status.toUpperCase();

		return Pegawai.Status.valueOf(status);
	}

	/**
	 * Returns company's status from the given string.
	 * @param status can be both upper case and lower case.
	 * @return Company's status.
	 */
	public static Perusahaan.Status getPerusahaanStatus(String status) {
		status = status.toUpperCase();

		return Perusahaan.Status.valueOf(status);
	}
}
