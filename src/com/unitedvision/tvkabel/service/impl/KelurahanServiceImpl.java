package com.unitedvision.tvkabel.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unitedvision.tvkabel.entity.Kecamatan;
import com.unitedvision.tvkabel.entity.Kelurahan;
import com.unitedvision.tvkabel.exception.EntityNotExistException;
import com.unitedvision.tvkabel.repository.KelurahanRepository;
import com.unitedvision.tvkabel.service.KelurahanService;

@Service
@Transactional(readOnly = true)
public class KelurahanServiceImpl implements KelurahanService {
	@Autowired
	private KelurahanRepository kelurahanRepository;

	@Override
	@Transactional(readOnly = false)
	public Kelurahan save(Kelurahan kelurahan) {
		return kelurahanRepository.save(kelurahan);
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(Integer id) {
		kelurahanRepository.delete(id);
	}
	
	@Override
	@Transactional(readOnly = false)
	public void delete(Kelurahan kelurahan) {
		kelurahan = kelurahanRepository.findOne(kelurahan.getId());
		kelurahanRepository.delete(kelurahan);
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
	public List<Kelurahan> getAll() throws EntityNotExistException {
		return kelurahanRepository.findAll();
	}

	@Override
	public List<Kelurahan> getByKecamatan(Kecamatan kecamatan) throws EntityNotExistException {
		return kelurahanRepository.findByKecamatan(kecamatan);
	}
}
