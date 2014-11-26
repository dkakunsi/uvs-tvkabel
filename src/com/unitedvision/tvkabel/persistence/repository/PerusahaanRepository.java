package com.unitedvision.tvkabel.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unitedvision.tvkabel.exception.EntityNotExistException;
import com.unitedvision.tvkabel.persistence.entity.PerusahaanEntity;

public interface PerusahaanRepository extends JpaRepository<PerusahaanEntity, Integer> {
	PerusahaanEntity findByKode(String kode) throws EntityNotExistException;
	PerusahaanEntity findFirstByOrderByIdDesc();
	
	List<PerusahaanEntity> findByNamaContaining(String nama);
}
