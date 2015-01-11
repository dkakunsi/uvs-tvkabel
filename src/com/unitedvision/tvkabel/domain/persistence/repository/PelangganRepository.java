package com.unitedvision.tvkabel.domain.persistence.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.unitedvision.tvkabel.domain.Kelurahan;
import com.unitedvision.tvkabel.domain.Pelanggan;
import com.unitedvision.tvkabel.domain.Pelanggan.Status;
import com.unitedvision.tvkabel.domain.Perusahaan;
import com.unitedvision.tvkabel.exception.EntityNotExistException;

public interface PelangganRepository extends JpaRepository<Pelanggan, Integer> {
	String findByPembayaran = "SELECT * FROM pelanggan WHERE id in (SELECT DISTINCT id_pelanggan FROM pembayaran WHERE tanggal_bayar = :tanggalBayar AND id_pegawai = :idPegawai)";
	String findByTanggalMulai = "SELECT * FROM pelanggan p WHERE p.status = :status AND p.tanggal_mulai like %:tanggal";
	String countByPembayaran = "SELECT count(*) FROM pelanggan WHERE id in (SELECT DISTINCT id_pelanggan FROM pembayaran WHERE tanggal_bayar = :tanggalBayar AND id_pegawai = :idPegawai)";
	String summerizeEstimasiPemasukanBulanan = "SELECT COALESCE(SUM(p.detail.iuran), 0) FROM Pelanggan p WHERE p.perusahaan = ?1 AND p.status = ?2";
	String summerizeTotalEstimasiTunggakan = "SELECT COALESCE(SUM(p.detail.tunggakan * p.detail.iuran), 0) FROM Pelanggan p WHERE p.perusahaan = ?1 AND p.status = ?2";
	
	Pelanggan findByPerusahaanAndKode(Perusahaan perusahaan, String kode) throws EntityNotExistException;
	Pelanggan findByPerusahaanAndNama(Perusahaan perusahaan, String nama) throws EntityNotExistException;

	List<Pelanggan> findByPerusahaanAndKodeContainingOrderByKodeAsc(Perusahaan perusahaan, String kode, Pageable page);
	List<Pelanggan> findByPerusahaanAndNamaContainingOrderByKodeAsc(Perusahaan perusahaan, String nama, Pageable page);
	List<Pelanggan> findByPerusahaanAndStatusOrderByKodeAsc(Perusahaan perusahaan, Status status);
	List<Pelanggan> findByPerusahaanAndStatusOrderByKodeAsc(Perusahaan perusahaan, Status status, Pageable page);
	List<Pelanggan> findByPerusahaanAndStatusAndNamaContainingOrderByKodeAsc(Perusahaan perusahaan, Status status, String nama, Pageable page);
	List<Pelanggan> findByPerusahaanAndStatusAndKodeContainingOrderByKodeAsc(Perusahaan perusahaan, Status status, String kode, Pageable page);

	List<Pelanggan> findByPerusahaanAndStatusAndKelurahanAndAlamat_LingkunganOrderByKodeAsc(Perusahaan perusahaan, Status status, Kelurahan kelurahan, int lingkungan);
	List<Pelanggan> findByPerusahaanAndStatusAndKelurahanAndAlamat_LingkunganOrderByKodeAsc(Perusahaan perusahaan, Status status, Kelurahan kelurahan, int lingkungan, Pageable page);

	List<Pelanggan> findByPerusahaanAndStatusAndDetail_TunggakanOrderByKodeAsc(Perusahaan perusahaan, Status status, int tunggakan);
	List<Pelanggan> findByPerusahaanAndStatusAndDetail_TunggakanOrderByKodeAsc(Perusahaan perusahaan, Status status, int tunggakan, Pageable page);

	long countByPerusahaanAndStatusAndNamaContaining(Perusahaan perusahaan, Status status, String nama);
	long countByPerusahaanAndStatusAndKodeContaining(Perusahaan perusahaan, Status status, String kode);
	long countByPerusahaanAndStatus(Perusahaan perusahaan, Status status);
	long countByPerusahaanAndKodeContaining(Perusahaan perusahaan, String kode);
	long countByPerusahaanAndNamaContaining(Perusahaan perusahaan, String nama);
	
	long countByPerusahaanAndStatusAndKelurahanAndAlamat_Lingkungan(Perusahaan perusahaan, Status status, Kelurahan kelurahan, int lingkungan);
	long countByPerusahaanAndStatusAndDetail_Tunggakan(Perusahaan perusahaan, Status status, int tunggakan);
	long countByPerusahaanAndStatusAndDetail_TunggakanGreaterThan(Perusahaan perusahaan, Status status, int tunggakan);
	long countByPerusahaanAndStatusAndDetail_TunggakanLessThan(Perusahaan perusahaan, Status status, int tunggakan);
	
	@Query (value = findByTanggalMulai, nativeQuery = true)
	List<Pelanggan> findByTanggalMulai(@Param("status") Status status, @Param("tanggal") int tanggal);
	@Query (value = findByPembayaran, nativeQuery = true)
	List<Pelanggan> findByPembayaran(@Param("idPegawai") int idPegawai, @Param("tanggalBayar") String tanggalBayar);

	@Query (value = countByPembayaran, nativeQuery = true)
	long countByPembayaran(@Param("idPegawai") int idPegawai, @Param("tanggalBayar") String tanggalBayar);

	@Query (summerizeEstimasiPemasukanBulanan)
	long sumarizeEstimasiPemasukanBulanan(Perusahaan perusahaan, Status status);
	@Query (summerizeTotalEstimasiTunggakan)
	long summarizeTotalAkumulasiTunggakan(Perusahaan perusahaan, Status status);
}
