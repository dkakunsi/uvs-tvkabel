package com.unitedvision.tvkabel.domain.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unitedvision.tvkabel.domain.Kota;
import com.unitedvision.tvkabel.exception.EntityNotExistException;

public interface KotaRepository extends JpaRepository<Kota, Integer> {
	Kota findByNama(String nama) throws EntityNotExistException;
}
