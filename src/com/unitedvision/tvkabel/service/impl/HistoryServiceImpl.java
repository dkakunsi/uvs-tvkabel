package com.unitedvision.tvkabel.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unitedvision.tvkabel.entity.History;
import com.unitedvision.tvkabel.entity.Pelanggan;
import com.unitedvision.tvkabel.entity.Perusahaan;
import com.unitedvision.tvkabel.exception.ApplicationException;
import com.unitedvision.tvkabel.repository.HistoryRepository;
import com.unitedvision.tvkabel.repository.PelangganRepository;
import com.unitedvision.tvkabel.service.HistoryService;

@Service
@Transactional(readOnly = true)
public class HistoryServiceImpl implements HistoryService {
	
	@Autowired
	private HistoryRepository historyRepository;
	@Autowired
	private PelangganRepository pelangganRepository;

	@Override
	public List<History> get(Perusahaan perusahaan, Date awal, Date akhir) throws ApplicationException {
		return historyRepository.findByPelanggan_PerusahaanAndTanggalBetweenOrderByTanggalAsc(perusahaan, awal, akhir);
	}

	@Override
	public List<History> get(Pelanggan pelanggan) throws ApplicationException {
		return historyRepository.findByPelangganOrderByTanggalAsc(pelanggan);
	}

	@Override
	public List<History> get(int idPelanggan) throws ApplicationException {
		final Pelanggan pelanggan = pelangganRepository.findOne(idPelanggan);

		return get(pelanggan);
	}
	
	@Override
	public List<History> get(Pelanggan pelanggan, Date awal, Date akhir) throws ApplicationException {
		return historyRepository.findByPelangganAndTanggalBetweenOrderByTanggalAsc(pelanggan, awal, akhir);
	}

	@Override
	public List<History> get(int idPelanggan, Date awal, Date akhir) throws ApplicationException {
		final Pelanggan pelanggan = pelangganRepository.findOne(idPelanggan);
		
		return get(pelanggan, awal, akhir);
	}

}
