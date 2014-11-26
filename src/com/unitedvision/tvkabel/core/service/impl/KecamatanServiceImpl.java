package com.unitedvision.tvkabel.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unitedvision.tvkabel.core.domain.Kecamatan;
import com.unitedvision.tvkabel.core.domain.Kota;
import com.unitedvision.tvkabel.core.service.KecamatanService;
import com.unitedvision.tvkabel.core.validator.Validator;
import com.unitedvision.tvkabel.exception.EntityNotExistException;
import com.unitedvision.tvkabel.exception.UncompatibleTypeException;
import com.unitedvision.tvkabel.persistence.entity.KecamatanEntity;
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
	public Kecamatan save(Kecamatan domain) throws UncompatibleTypeException {
		domain = validator.validate(domain.toEntity());
		return kecamatanRepository.save(domain.toEntity());
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(Kecamatan domain) {
		domain = kecamatanRepository.findOne(domain.getId());
		kecamatanRepository.delete(domain.toEntity());
	}

	@Override
	public KecamatanEntity getOne(int id) throws EntityNotExistException {
		return kecamatanRepository.findOne(id);
	}

	@Override
	public List<KecamatanEntity> getAll() {
		return kecamatanRepository.findAll();
	}

	@Override
	public List<KecamatanEntity> getByKota(Kota kota) {
		return kecamatanRepository.findByKota(kota.toEntity());
	}
}
