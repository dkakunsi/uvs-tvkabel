package com.unitedvision.tvkabel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unitedvision.tvkabel.entity.Alat;
import com.unitedvision.tvkabel.entity.Kelurahan;
import com.unitedvision.tvkabel.entity.Alat.Tipe;
import com.unitedvision.tvkabel.entity.Perusahaan;
import com.unitedvision.tvkabel.exception.EntityNotExistException;

public interface AlatRepository extends JpaRepository<Alat, Integer> {

	List<Alat> findByPerusahaan(Perusahaan perusahaan) throws EntityNotExistException;
	
	List<Alat> findByPerusahaanAndKodeContaining(Perusahaan perusahaan, String kode) throws EntityNotExistException;
	
	List<Alat> findByPerusahaanAndTipe(Perusahaan perusahaan, Tipe tipe) throws EntityNotExistException;
	
	List<Alat> findByPerusahaanAndAlamat_KelurahanAndAlamat_Lingkungan(Perusahaan perusahaan, Kelurahan kelurahan, int lingkungan) throws EntityNotExistException;
	
	List<Alat> findBySource(Alat alat) throws EntityNotExistException;
	
}
