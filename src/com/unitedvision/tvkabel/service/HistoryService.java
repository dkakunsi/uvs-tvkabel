package com.unitedvision.tvkabel.service;

import java.util.Date;
import java.util.List;

import com.unitedvision.tvkabel.entity.History;
import com.unitedvision.tvkabel.entity.Pelanggan;
import com.unitedvision.tvkabel.exception.ApplicationException;

/**
 * Layanan untuk entitas sejarah.
 * 
 * @author Deddy Christoper Kakunsi
 *
 */

public interface HistoryService {

	List<History> get(Date awal, Date akhir) throws ApplicationException;

	List<History> get(Pelanggan pelanggan) throws ApplicationException;

	List<History> get(Pelanggan pelanggan, Date awal, Date akhir) throws ApplicationException;
	List<History> get(int idPelanggan, Date awal, Date akhir) throws ApplicationException;
}
