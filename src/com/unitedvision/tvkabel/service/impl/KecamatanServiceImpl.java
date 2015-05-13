package com.unitedvision.tvkabel.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unitedvision.tvkabel.entity.Kecamatan;
import com.unitedvision.tvkabel.entity.Kota;
import com.unitedvision.tvkabel.exception.EntityNotExistException;
import com.unitedvision.tvkabel.repository.KecamatanRepository;
import com.unitedvision.tvkabel.service.KecamatanService;

@Service
@Transactional(readOnly = true)
public class KecamatanServiceImpl implements KecamatanService {
	@Autowired
	private KecamatanRepository kecamatanRepository;

	@Override
	@Transactional(readOnly = false)
	public Kecamatan save(Kecamatan kecamatan) {
		return kecamatanRepository.save(kecamatan);
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(Integer id) {
		kecamatanRepository.delete(id);
	}
	
	@Override
	@Transactional(readOnly = false)
	public void delete(Kecamatan kecamatan) {
		kecamatanRepository.delete(kecamatan);
	}

	@Override
	public Kecamatan getOne(int id) throws EntityNotExistException {
		return kecamatanRepository.findOne(id);
	}

	@Override
	public List<Kecamatan> getAll() throws EntityNotExistException {
		return kecamatanRepository.findAll();
	}

	@Override
	public List<Kecamatan> getByKota(Kota kota) throws EntityNotExistException {
		return kecamatanRepository.findByKota(kota);
	}
}
