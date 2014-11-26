package com.unitedvision.tvkabel.persistence.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.unitedvision.tvkabel.core.domain.Pegawai.Status;
import com.unitedvision.tvkabel.exception.EntityNotExistException;
import com.unitedvision.tvkabel.persistence.entity.PegawaiEntity;
import com.unitedvision.tvkabel.persistence.entity.PerusahaanEntity;

public interface PegawaiRepository extends JpaRepository<PegawaiEntity, Integer> {
	PegawaiEntity findByPerusahaanAndStatusAndKode(PerusahaanEntity perusahaanEntity, Status status, String kode) throws EntityNotExistException;
	PegawaiEntity findByPerusahaanAndStatusAndNama(PerusahaanEntity perusahaanEntity, Status status, String nama) throws EntityNotExistException;
	PegawaiEntity findByKredensi_UsernameAndStatus(String username, Status status) throws EntityNotExistException;

	List<PegawaiEntity> findByPerusahaanAndStatus(PerusahaanEntity perusahaanEntity, Status status);
	List<PegawaiEntity> findByPerusahaanAndStatus(PerusahaanEntity perusahaanEntity, Status status, Pageable page);
	List<PegawaiEntity> findByPerusahaanAndStatusAndKodeContaining(PerusahaanEntity perusahaanEntity, Status status, String kode, Pageable page);
	List<PegawaiEntity> findByPerusahaanAndStatusAndNamaContaining(PerusahaanEntity perusahaanEntity, Status status, String nama, Pageable page);
	
	long countByPerusahaan(PerusahaanEntity perusahaanEntity);
	long countByPerusahaanAndStatus(PerusahaanEntity perusahaanEntity, Status status);
	long countByPerusahaanAndStatusAndKodeContaining(PerusahaanEntity perusahaanEntity, Status status, String kode);
	long countByPerusahaanAndStatusAndNamaContaining(PerusahaanEntity perusahaanEntity, Status status, String nama);
}
