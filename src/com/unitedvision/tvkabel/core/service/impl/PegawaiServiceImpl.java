package com.unitedvision.tvkabel.core.service.impl;

import java.util.List;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unitedvision.tvkabel.core.service.PegawaiService;
import com.unitedvision.tvkabel.core.validator.Validator;
import com.unitedvision.tvkabel.exception.DataDuplicationException;
import com.unitedvision.tvkabel.exception.EntityNotExistException;
import com.unitedvision.tvkabel.exception.StatusChangeException;
import com.unitedvision.tvkabel.persistence.entity.Pegawai;
import com.unitedvision.tvkabel.persistence.entity.Perusahaan;
import com.unitedvision.tvkabel.persistence.entity.Removable;
import com.unitedvision.tvkabel.persistence.entity.Pegawai.Status;
import com.unitedvision.tvkabel.persistence.repository.PegawaiRepository;
import com.unitedvision.tvkabel.util.PageSizeUtil;

@Service
@Transactional(readOnly = true)
public class PegawaiServiceImpl implements PegawaiService {
	@Autowired
	private PegawaiRepository pegawaiRepository;
	@Autowired
	private Validator validator;

	@Transactional(readOnly = false)
	public Pegawai save(Pegawai domain) throws DataDuplicationException {
		domain = validator.validate(domain);
		
		try {
			domain = pegawaiRepository.save(domain);
		} catch(PersistenceException e) {
			throw new DataDuplicationException(e.getMessage());
		}

		return  domain;
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(Pegawai domain) {
		domain = pegawaiRepository.findOne(domain.getId());
		pegawaiRepository.delete(domain);
	}

	@Override
	public void remove(Pegawai domain) throws EntityNotExistException, StatusChangeException {
		domain = pegawaiRepository.findOne(domain.getId());
		((Removable)domain).remove();

		pegawaiRepository.save(domain);
	}

	@Override
	public Pegawai getOne(int id) throws EntityNotExistException {
		return pegawaiRepository.findOne(id);
	}

	@Override
	public List<Pegawai> get(Perusahaan perusahaan) {
		return pegawaiRepository.findByPerusahaanAndStatus(perusahaan, Status.AKTIF);
	}

	@Override
	public List<Pegawai> getByNama(Perusahaan perusahaan, String nama, int page) {
		PageRequest pageRequest = new PageRequest(page, PageSizeUtil.DATA_NUMBER);

		return pegawaiRepository.findByPerusahaanAndStatusAndNamaContaining(perusahaan, Status.AKTIF, nama, pageRequest);
	}

	@Override
	public List<Pegawai> getByKode(Perusahaan perusahaan, String kode, int page) {
		PageRequest pageRequest = new PageRequest(page, PageSizeUtil.DATA_NUMBER);

		return pegawaiRepository.findByPerusahaanAndStatusAndKodeContaining(perusahaan, Status.AKTIF, kode, pageRequest);
	}

	@Override
	public Pegawai getOneByNama(Perusahaan perusahaan, String nama) throws EntityNotExistException {
		return pegawaiRepository.findByPerusahaanAndStatusAndNama(perusahaan, Status.AKTIF, nama);
	}

	@Override
	public Pegawai getOneByKode(Perusahaan perusahaan, String kode) throws EntityNotExistException {
		return pegawaiRepository.findByPerusahaanAndStatusAndKode(perusahaan, Status.AKTIF, kode);
	}

	@Override
	public Pegawai getOneByUsername(String username) throws EntityNotExistException {
		return pegawaiRepository.findByKredensi_UsernameAndStatus(username, Status.AKTIF);
	}

	@Override
	public long countByNama(Perusahaan perusahaan, String nama) {
		return pegawaiRepository.countByPerusahaanAndStatusAndNamaContaining(perusahaan, Status.AKTIF, nama);
	}

	@Override
	public long countByKode(Perusahaan perusahaan, String kode) {
		return pegawaiRepository.countByPerusahaanAndStatusAndKodeContaining(perusahaan, Status.AKTIF, kode);
	}
}
