package com.unitedvision.tvkabel.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unitedvision.tvkabel.exception.EntityNotExistException;
import com.unitedvision.tvkabel.persistence.entity.KecamatanEntity;
import com.unitedvision.tvkabel.persistence.entity.KotaEntity;

public interface KecamatanRepository extends JpaRepository<KecamatanEntity, Integer> {
	KecamatanEntity findByNama(String nama) throws EntityNotExistException;
	
	//This will be used by AJAX-style call later
	List<KecamatanEntity> findByKota(KotaEntity kotaEntity);
}
