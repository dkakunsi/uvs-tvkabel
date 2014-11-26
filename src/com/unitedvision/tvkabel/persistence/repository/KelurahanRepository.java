package com.unitedvision.tvkabel.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unitedvision.tvkabel.exception.EntityNotExistException;
import com.unitedvision.tvkabel.persistence.entity.KecamatanEntity;
import com.unitedvision.tvkabel.persistence.entity.KelurahanEntity;

public interface KelurahanRepository extends JpaRepository<KelurahanEntity, Integer> {
	KelurahanEntity findByNama(String nama) throws EntityNotExistException;
	
	List<KelurahanEntity> findByKecamatan(KecamatanEntity kecamatanEntity);
}
