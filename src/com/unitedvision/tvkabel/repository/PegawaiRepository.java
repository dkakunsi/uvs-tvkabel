package com.unitedvision.tvkabel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unitedvision.tvkabel.entity.Pegawai;
import com.unitedvision.tvkabel.entity.Perusahaan;
import com.unitedvision.tvkabel.entity.Pegawai.Status;
import com.unitedvision.tvkabel.exception.EntityNotExistException;

public interface PegawaiRepository extends JpaRepository<Pegawai, Integer> {
	Pegawai findByPerusahaanAndStatusAndKode(Perusahaan perusahaan, Status status, String kode) throws EntityNotExistException;
	Pegawai findByPerusahaanAndStatusAndNama(Perusahaan perusahaan, Status status, String nama) throws EntityNotExistException;
	Pegawai findByKredensi_UsernameAndStatus(String username, Status status) throws EntityNotExistException;

	List<Pegawai> findByPerusahaanAndStatus(Perusahaan perusahaan, Status status);
	List<Pegawai> findByPerusahaanAndStatusAndKodeContaining(Perusahaan perusahaan, Status status, String kode);
	List<Pegawai> findByPerusahaanAndStatusAndNamaContaining(Perusahaan perusahaan, Status status, String nama);
	
	long countByPerusahaan(Perusahaan perusahaan);
	long countByPerusahaanAndStatusAndKodeContaining(Perusahaan perusahaan, Status status, String kode);
	long countByPerusahaanAndStatusAndNamaContaining(Perusahaan perusahaan, Status status, String nama);
}
