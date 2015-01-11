package com.unitedvision.tvkabel.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unitedvision.tvkabel.core.service.KelurahanService;
import com.unitedvision.tvkabel.core.validator.Validator;
import com.unitedvision.tvkabel.domain.Kecamatan;
import com.unitedvision.tvkabel.domain.Kelurahan;
import com.unitedvision.tvkabel.domain.persistence.repository.KelurahanRepository;
import com.unitedvision.tvkabel.exception.EntityNotExistException;
import com.unitedvision.tvkabel.exception.UncompatibleTypeException;

@Service
@Transactional(readOnly = true)
public class KelurahanServiceImpl implements KelurahanService {
	@Autowired
	private KelurahanRepository kelurahanRepository;
	@Autowired
	private Validator validator;

	@Override
	@Transactional(readOnly = false)
	public Kelurahan save(Kelurahan domain) throws UncompatibleTypeException {
		domain = validator.validate(domain);
		return kelurahanRepository.save(domain);
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(Kelurahan domain) {
		domain = kelurahanRepository.findOne(domain.getId());
		kelurahanRepository.delete(domain);
	}

	@Override
	public Kelurahan getOne(int id) throws EntityNotExistException {
		return kelurahanRepository.findOne(id);
	}

	@Override
	public Kelurahan getOneByNama(String nama) throws EntityNotExistException {
		return kelurahanRepository.findByNama(nama);
	}

	@Override
	public List<Kelurahan> getAll() {
		return kelurahanRepository.findAll();
	}

	@Override
	public List<Kelurahan> getByKecamatan(Kecamatan kecamatan) {
		return kelurahanRepository.findByKecamatan(kecamatan);
	}
}
