package com.unitedvision.tvkabel.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unitedvision.tvkabel.exception.EntityNotExistException;
import com.unitedvision.tvkabel.persistence.entity.Perusahaan;

public interface PerusahaanRepository extends JpaRepository<Perusahaan, Integer> {
	Perusahaan findByKode(String kode) throws EntityNotExistException;
	Perusahaan findFirstByOrderByIdDesc();
	
	List<Perusahaan> findByNamaContaining(String nama);
}
