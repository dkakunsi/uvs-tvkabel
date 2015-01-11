package com.unitedvision.tvkabel.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unitedvision.tvkabel.exception.EntityNotExistException;
import com.unitedvision.tvkabel.persistence.domain.Kecamatan;
import com.unitedvision.tvkabel.persistence.domain.Kelurahan;

public interface KelurahanRepository extends JpaRepository<Kelurahan, Integer> {
	Kelurahan findByNama(String nama) throws EntityNotExistException;
	
	List<Kelurahan> findByKecamatan(Kecamatan kecamatan);
}
