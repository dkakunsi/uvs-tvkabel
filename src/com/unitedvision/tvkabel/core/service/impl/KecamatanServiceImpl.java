package com.unitedvision.tvkabel.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unitedvision.tvkabel.core.service.KecamatanService;
import com.unitedvision.tvkabel.core.validator.Validator;
import com.unitedvision.tvkabel.exception.EntityNotExistException;
import com.unitedvision.tvkabel.persistence.entity.Kecamatan;
import com.unitedvision.tvkabel.persistence.entity.Kota;
import com.unitedvision.tvkabel.persistence.repository.KecamatanRepository;

@Service
@Transactional(readOnly = true)
public class KecamatanServiceImpl implements KecamatanService {
	@Autowired
	private KecamatanRepository kecamatanRepository;
	@Autowired
	private Validator validator;

	@Override
	@Transactional(readOnly = false)
	public Kecamatan save(Kecamatan domain) {
		domain = validator.validate(domain);
		return kecamatanRepository.save(domain);
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(Kecamatan domain) {
		domain = kecamatanRepository.findOne(domain.getId());
		kecamatanRepository.delete(domain);
	}

	@Override
	public Kecamatan getOne(int id) throws EntityNotExistException {
		return kecamatanRepository.findOne(id);
	}

	@Override
	public List<Kecamatan> getAll() {
		return kecamatanRepository.findAll();
	}

	@Override
	public List<Kecamatan> getByKota(Kota kota) {
		return kecamatanRepository.findByKota(kota);
	}
}
