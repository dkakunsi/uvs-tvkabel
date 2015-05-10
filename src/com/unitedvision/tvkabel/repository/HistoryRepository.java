package com.unitedvision.tvkabel.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unitedvision.tvkabel.entity.History;
import com.unitedvision.tvkabel.entity.Pelanggan;
import com.unitedvision.tvkabel.entity.Perusahaan;

/**
 * Kelas untuk mengakses database sejarah.
 * 
 * @author Deddy Christoper Kakunsi
 *
 */

public interface HistoryRepository extends JpaRepository<History, Integer> {

	List<History> findByPelanggan_PerusahaanAndTanggalBetweenOrderByTanggalAsc(Perusahaan perusahaan, Date awal, Date akhir);

	List<History> findByPelangganOrderByTanggalAsc(Pelanggan pelanggan);

	List<History> findByPelangganAndTanggalBetweenOrderByTanggalAsc(Pelanggan pelanggan, Date awal, Date akhir);

}
