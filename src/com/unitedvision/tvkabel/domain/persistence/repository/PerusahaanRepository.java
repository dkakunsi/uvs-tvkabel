package com.unitedvision.tvkabel.domain.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unitedvision.tvkabel.domain.entity.Perusahaan;
import com.unitedvision.tvkabel.exception.EntityNotExistException;

public interface PerusahaanRepository extends JpaRepository<Perusahaan, Integer> {
	Perusahaan findByKode(String kode) throws EntityNotExistException;
	Perusahaan findFirstByOrderByIdDesc();
	
	List<Perusahaan> findByNamaContaining(String nama);
}
