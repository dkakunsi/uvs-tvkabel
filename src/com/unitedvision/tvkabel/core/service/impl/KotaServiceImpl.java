package com.unitedvision.tvkabel.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unitedvision.tvkabel.core.service.KotaService;
import com.unitedvision.tvkabel.exception.EntityNotExistException;
import com.unitedvision.tvkabel.persistence.domain.Kota;
import com.unitedvision.tvkabel.persistence.repository.KotaRepository;

@Service
@Transactional(readOnly = true)
public class KotaServiceImpl implements KotaService {
	@Autowired
	private KotaRepository kotaRepository;
	
	@Override
	@Transactional(readOnly = false)
	public Kota save(Kota domain) {
		return kotaRepository.save(domain);
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(Kota domain) {
		domain = kotaRepository.findOne(domain.getId());
		kotaRepository.delete(domain);
	}
	
	@Override
	public Kota getOne(int id) throws EntityNotExistException {
		return kotaRepository.findOne(id);
	}
	
	@Override
	public List<Kota> getAll() {
		return kotaRepository.findAll();
	}
}
