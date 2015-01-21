package com.unitedvision.tvkabel.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unitedvision.tvkabel.exception.EntityNotExistException;
import com.unitedvision.tvkabel.persistence.entity.Perusahaan;

public interface PerusahaanRepository extends JpaRepository<Perusahaan, Integer> {
	Perusahaan findByKode(String kode) throws EntityNotExistException;
	Perusahaan findFirstByOrderByIdDesc();
}
