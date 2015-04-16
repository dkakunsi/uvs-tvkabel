package com.unitedvision.tvkabel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unitedvision.tvkabel.entity.Perusahaan;
import com.unitedvision.tvkabel.exception.EntityNotExistException;

public interface PerusahaanRepository extends JpaRepository<Perusahaan, Integer> {
	Perusahaan findByKode(String kode) throws EntityNotExistException;
	Perusahaan findFirstByOrderByIdDesc();
}
