package com.unitedvision.tvkabel.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unitedvision.tvkabel.entity.Pegawai;
import com.unitedvision.tvkabel.entity.Perusahaan;
import com.unitedvision.tvkabel.entity.Pegawai.Status;
import com.unitedvision.tvkabel.exception.ApplicationException;
import com.unitedvision.tvkabel.exception.DataDuplicationException;
import com.unitedvision.tvkabel.exception.EntityNotExistException;
import com.unitedvision.tvkabel.exception.StatusChangeException;
import com.unitedvision.tvkabel.repository.PegawaiRepository;
import com.unitedvision.tvkabel.service.PegawaiService;

@Service
@Transactional(readOnly = true)
public class PegawaiServiceImpl implements PegawaiService {
	@Autowired
	private PegawaiRepository pegawaiRepository;

	@Override
	@Transactional(readOnly = false)
	public Pegawai add(Pegawai pegawai) throws ApplicationException {
		pegawai.setStatus(Status.AKTIF);
		
		return save(pegawai);
	}
	
	@Transactional(readOnly = false)
	public Pegawai save(Pegawai pegawai) throws DataDuplicationException {
		return pegawaiRepository.save(pegawai);
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(Pegawai pegawai) {
		pegawaiRepository.delete(pegawai);
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(Integer id) {
		pegawaiRepository.delete(id);
	}
	
	@Override
	@Transactional(readOnly = false)
	public Pegawai remove(Integer id) throws EntityNotExistException, StatusChangeException {
		return remove(pegawaiRepository.findOne(id));
	}

	@Override
	@Transactional(readOnly = false)
	public Pegawai remove(Pegawai pegawai) throws EntityNotExistException, StatusChangeException {
		pegawai.remove();
		return pegawaiRepository.save(pegawai);
	}

	@Override
	public Pegawai getOne(int id) throws EntityNotExistException {
		return pegawaiRepository.findOne(id);
	}

	@Override
	/**
	 * Digunakan untuk melakukan pembayaran otomatis.
	 * Untuk pelanggan GRATIS.
	 */
	public Pegawai getOne(Perusahaan perusahaan) throws EntityNotExistException {
		List<Pegawai> list = get(perusahaan);
		return list.get(0);
	}
	
	@Override
	public List<Pegawai> get(Perusahaan perusahaan) throws EntityNotExistException {
		return pegawaiRepository.findByPerusahaanAndStatus(perusahaan, Status.AKTIF);
	}

	@Override
	public List<Pegawai> getByNama(Perusahaan perusahaan, String nama) throws EntityNotExistException {
		return pegawaiRepository.findByPerusahaanAndStatusAndNamaContaining(perusahaan, Status.AKTIF, nama);
	}

	@Override
	public List<Pegawai> getByKode(Perusahaan perusahaan, String kode) throws EntityNotExistException {
		return pegawaiRepository.findByPerusahaanAndStatusAndKodeContaining(perusahaan, Status.AKTIF, kode);
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
		Pegawai pegawai = pegawaiRepository.findByKredensi_UsernameAndStatus(username, Status.AKTIF);
		if (pegawai.getStatus().equals(Status.REMOVED))
			throw new EntityNotExistException("Pegawai sudah tidak aktif");
		return pegawai;
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
