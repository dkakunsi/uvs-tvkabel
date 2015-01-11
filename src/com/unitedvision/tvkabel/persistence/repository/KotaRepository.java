package com.unitedvision.tvkabel.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unitedvision.tvkabel.exception.EntityNotExistException;
import com.unitedvision.tvkabel.persistence.entity.Kota;

public interface KotaRepository extends JpaRepository<Kota, Integer> {
	Kota findByNama(String nama) throws EntityNotExistException;
}
