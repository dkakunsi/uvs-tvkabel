package com.unitedvision.tvkabel.repository;

import java.time.Month;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.unitedvision.tvkabel.entity.Pegawai;
import com.unitedvision.tvkabel.entity.Pelanggan;
import com.unitedvision.tvkabel.entity.Pembayaran;
import com.unitedvision.tvkabel.entity.Perusahaan;
import com.unitedvision.tvkabel.entity.Pembayaran.Tagihan;

public interface PembayaranRepository extends JpaRepository<Pembayaran, Integer> {
	String countPemasukanBulanBerjalan = "SELECT COALESCE(SUM(p.jumlahBayar), 0) FROM Pembayaran p WHERE p.pegawai.perusahaan = ?1 AND p.tanggalBayar BETWEEN ?2 AND ?3";

	Pembayaran findFirstByPelangganOrderByIdDesc(Pelanggan pelanggan);

	List<Pembayaran> findByPegawaiAndTanggalBayarBetween(Pegawai pegawai, Date tanggalAwal, Date tanggalAkhir);
	List<Pembayaran> findByPelangganAndTanggalBayarBetween(Pelanggan pelanggan, Date tanggalAwal, Date tanggalAkhir);
	List<Pembayaran> findByPelangganAndTagihan_Tahun(Pelanggan pelanggan, int tahun);
	List<Pembayaran> findByPelangganAndTagihan_TahunAndTagihan_BulanBetween(Pelanggan pelanggan, int tahun, Month bulanAwal, Month bulanAkhir);
	List<Pembayaran> findByPegawai_PerusahaanAndTagihan(Perusahaan perusahaan, Tagihan tagihan);
	List<Pembayaran> findByPegawai_PerusahaanAndTanggalBayarBetween(Perusahaan perusahaan, Date tanggalAwal, Date tanggalAkhir);

	long countByPegawai_PerusahaanAndTanggalBayarBetween(Perusahaan perusahaan, Date tanggalAwal, Date tanggalAkhir);
	long countByPegawaiAndTanggalBayarBetween(Pegawai pegawai, Date tanggalAwal, Date tanggalAkhir);
	long countByPelangganAndTanggalBayarBetween(Pelanggan pelanggan, Date tanggalAwal, Date tanggalAkhir);
	
	@Query (countPemasukanBulanBerjalan)
	long countPemasukanBulanBerjalan(Perusahaan perusahaan, Date tanggalAwal, Date tanggalAkhir);
}
