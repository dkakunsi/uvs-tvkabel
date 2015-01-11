package com.unitedvision.tvkabel.domain.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unitedvision.tvkabel.domain.Kecamatan;
import com.unitedvision.tvkabel.domain.Kelurahan;
import com.unitedvision.tvkabel.exception.EntityNotExistException;

public interface KelurahanRepository extends JpaRepository<Kelurahan, Integer> {
	Kelurahan findByNama(String nama) throws EntityNotExistException;
	
	List<Kelurahan> findByKecamatan(Kecamatan kecamatan);
}
