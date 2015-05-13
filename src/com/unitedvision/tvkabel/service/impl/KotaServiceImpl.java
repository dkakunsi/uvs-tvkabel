package com.unitedvision.tvkabel.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unitedvision.tvkabel.entity.Kota;
import com.unitedvision.tvkabel.exception.EntityNotExistException;
import com.unitedvision.tvkabel.repository.KotaRepository;
import com.unitedvision.tvkabel.service.KotaService;

@Service
@Transactional(readOnly = true)
public class KotaServiceImpl implements KotaService {
	@Autowired
	private KotaRepository kotaRepository;
	
	@Override
	@Transactional(readOnly = false)
	public Kota save(Kota kota) {
		return kotaRepository.save(kota);
	}
	
	@Override
	@Transactional(readOnly = false)
	public void delete(Integer id) {
		kotaRepository.delete(id);
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(Kota kota) {
		kota = kotaRepository.findOne(kota.getId());
		kotaRepository.delete(kota);
	}
	
	@Override
	public Kota getOne(int id) throws EntityNotExistException {
		return kotaRepository.findOne(id);
	}
	
	@Override
	public List<Kota> getAll() throws EntityNotExistException {
		return kotaRepository.findAll();
	}
}
