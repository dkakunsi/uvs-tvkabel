package com.unitedvision.tvkabel.persistence.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.unitedvision.tvkabel.exception.EntityNotExistException;
import com.unitedvision.tvkabel.persistence.entity.Pegawai;
import com.unitedvision.tvkabel.persistence.entity.Perusahaan;
import com.unitedvision.tvkabel.persistence.entity.Pegawai.Status;

public interface PegawaiRepository extends JpaRepository<Pegawai, Integer> {
	Pegawai findByPerusahaanAndStatusAndKode(Perusahaan perusahaan, Status status, String kode) throws EntityNotExistException;
	Pegawai findByPerusahaanAndStatusAndNama(Perusahaan perusahaan, Status status, String nama) throws EntityNotExistException;
	Pegawai findByKredensi_UsernameAndStatus(String username, Status status) throws EntityNotExistException;

	List<Pegawai> findByPerusahaanAndStatus(Perusahaan perusahaan, Status status);
	List<Pegawai> findByPerusahaanAndStatusAndKodeContaining(Perusahaan perusahaan, Status status, String kode, Pageable page);
	List<Pegawai> findByPerusahaanAndStatusAndNamaContaining(Perusahaan perusahaan, Status status, String nama, Pageable page);
	
	long countByPerusahaan(Perusahaan perusahaan);
	long countByPerusahaanAndStatusAndKodeContaining(Perusahaan perusahaan, Status status, String kode);
	long countByPerusahaanAndStatusAndNamaContaining(Perusahaan perusahaan, Status status, String nama);
}
