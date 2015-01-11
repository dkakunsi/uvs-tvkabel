package com.unitedvision.tvkabel.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unitedvision.tvkabel.exception.EntityNotExistException;
import com.unitedvision.tvkabel.persistence.entity.Kecamatan;
import com.unitedvision.tvkabel.persistence.entity.Kota;

public interface KecamatanRepository extends JpaRepository<Kecamatan, Integer> {
	Kecamatan findByNama(String nama) throws EntityNotExistException;
	
	//This will be used by AJAX-style call later
	List<Kecamatan> findByKota(Kota kota);
}
