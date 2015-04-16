package com.unitedvision.tvkabel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unitedvision.tvkabel.entity.Kecamatan;
import com.unitedvision.tvkabel.entity.Kota;
import com.unitedvision.tvkabel.exception.EntityNotExistException;

public interface KecamatanRepository extends JpaRepository<Kecamatan, Integer> {
	Kecamatan findByNama(String nama) throws EntityNotExistException;
	List<Kecamatan> findByKota(Kota kota) throws EntityNotExistException;
}
