package com.unitedvision.tvkabel.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unitedvision.tvkabel.exception.EntityNotExistException;
import com.unitedvision.tvkabel.persistence.entity.KotaEntity;

public interface KotaRepository extends JpaRepository<KotaEntity, Integer> {
	KotaEntity findByNama(String nama) throws EntityNotExistException;
}
