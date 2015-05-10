package com.unitedvision.tvkabel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.unitedvision.tvkabel.entity.Alat;
import com.unitedvision.tvkabel.entity.Kelurahan;
import com.unitedvision.tvkabel.entity.Pelanggan;
import com.unitedvision.tvkabel.entity.Perusahaan;
import com.unitedvision.tvkabel.entity.Pelanggan.Status;
import com.unitedvision.tvkabel.exception.EntityNotExistException;

public interface PelangganRepository extends JpaRepository<Pelanggan, Integer> {
	
	// JPAQL
	String findByPembayaran = "SELECT * FROM pelanggan WHERE id in (SELECT DISTINCT id_pelanggan FROM pembayaran WHERE (tanggal_bayar BETWEEN :tanggalBayarAwal AND :tanggalBayarAkhir) AND id_pegawai = :idPegawai) ORDER BY kode";
	String findByPembayaranWithoutPegawai = "SELECT * FROM pelanggan WHERE id in (SELECT DISTINCT id_pelanggan FROM pembayaran WHERE tanggal_bayar BETWEEN :tanggalBayarAwal AND :tanggalBayarAkhir) AND id_perusahaan = :idPerusahaan ORDER BY kode";
	String findByTanggalMulai = "SELECT * FROM pelanggan p WHERE p.status = :status AND p.tanggal_mulai like %:tanggal";
	String findByAlamatOrdered = "SELECT p FROM PelangganEntity p WHERE p.perusahaan = ?1 AND p.status = ?2 ORDER BY p.alamat.kelurahan, p.alamat.lingkungan";
	
	// Native Query
	String findByAlamatOrderedNative = "SELECT * FROM pelanggan p WHERE p.id_perusahaan = :idPerusahaan AND p.status = :status ORDER BY p.id_kelurahan, p.lingkungan, p.kode";
	String summerizeEstimasiPemasukanBulanan = "SELECT COALESCE(SUM(p.detail.iuran), 0) FROM Pelanggan p WHERE p.perusahaan = ?1 AND p.status = ?2";
	String summerizeTotalEstimasiTunggakan = "SELECT COALESCE(SUM(p.detail.tunggakan * p.detail.iuran), 0) FROM Pelanggan p WHERE p.perusahaan = ?1 AND p.status = ?2";
	
	Pelanggan findByPerusahaanAndKode(Perusahaan perusahaan, String kode) throws EntityNotExistException;
	Pelanggan findByPerusahaanAndNama(Perusahaan perusahaan, String nama) throws EntityNotExistException;

	List<Pelanggan> findByPerusahaanAndNomorBukuContainingOrderByKodeAsc(Perusahaan perusahaan, int nomorBuku) throws EntityNotExistException;
	List<Pelanggan> findByPerusahaanAndStatusAndNomorBukuContainingOrderByKodeAsc(Perusahaan perusahaan, Status status, int nomorBuku) throws EntityNotExistException;

	List<Pelanggan> findByPerusahaanAndKodeContainingOrderByKodeAsc(Perusahaan perusahaan, String kode) throws EntityNotExistException;
	List<Pelanggan> findByPerusahaanAndNamaContainingOrderByKodeAsc(Perusahaan perusahaan, String nama) throws EntityNotExistException;
	List<Pelanggan> findByPerusahaanAndStatusOrderByKodeAsc(Perusahaan perusahaan, Status status) throws EntityNotExistException;
	List<Pelanggan> findByPerusahaanAndStatusAndNamaContainingOrderByKodeAsc(Perusahaan perusahaan, Status status, String nama) throws EntityNotExistException;
	List<Pelanggan> findByPerusahaanAndStatusAndKodeContainingOrderByKodeAsc(Perusahaan perusahaan, Status status, String kode) throws EntityNotExistException;

	List<Pelanggan> findByPerusahaanAndStatusAndAlamat_KelurahanAndAlamat_LingkunganOrderByKodeAsc(Perusahaan perusahaan, Status status, Kelurahan kelurahan, int lingkungan) throws EntityNotExistException;

	List<Pelanggan> findByPerusahaanAndStatusAndDetail_TunggakanOrderByKodeAsc(Perusahaan perusahaan, Status status, int tunggakan) throws EntityNotExistException;
	List<Pelanggan> findByPerusahaanAndStatusAndDetail_TunggakanBetweenOrderByKodeAsc(Perusahaan perusahaan, Status status, int tunggakanAwal, int tunggakanAkhir) throws EntityNotExistException;
	
	List<Pelanggan> findBySource(Alat alat) throws EntityNotExistException;

	long countByPerusahaanAndStatus(Perusahaan perusahaan, Status status) throws EntityNotExistException;

	long countByPerusahaanAndNamaContaining(Perusahaan perusahaan, String nama) throws EntityNotExistException;
	long countByPerusahaanAndStatusAndNamaContaining(Perusahaan perusahaan, Status status, String nama) throws EntityNotExistException;
	
	long countByPerusahaanAndKodeContaining(Perusahaan perusahaan, String kode) throws EntityNotExistException;
	long countByPerusahaanAndStatusAndKodeContaining(Perusahaan perusahaan, Status status, String kode) throws EntityNotExistException;

	long countByPerusahaanAndNomorBukuContaining(Perusahaan perusahaan, int nomorBuku) throws EntityNotExistException;
	long countByPerusahaanAndStatusAndNomorBukuContaining(Perusahaan perusahaan, Status status, int nomorBuku) throws EntityNotExistException;

	long countByPerusahaanAndStatusAndDetail_Tunggakan(Perusahaan perusahaan, Status status, int tunggakan);
	long countByPerusahaanAndStatusAndDetail_TunggakanGreaterThan(Perusahaan perusahaan, Status status, int tunggakan);
	long countByPerusahaanAndStatusAndDetail_TunggakanLessThan(Perusahaan perusahaan, Status status, int tunggakan);
	
	@Query(value = findByAlamatOrderedNative, nativeQuery = true)
	List<Pelanggan> findByPerusahaanAndStatusOrderByAlamat(@Param("idPerusahaan") int idPerusahaan, @Param("status") Status status);
	@Query (value = findByTanggalMulai, nativeQuery = true)
	List<Pelanggan> findByTanggalMulai(@Param("status") int status, @Param("tanggal") String tanggal);
	@Query (value = findByPembayaran, nativeQuery = true)
	List<Pelanggan> findByPembayaran(@Param("idPegawai") int idPegawai, @Param("tanggalBayarAwal") String tanggalBayarAwal, @Param("tanggalBayarAkhir") String tanggalBayarAkhir);
	@Query (value = findByPembayaranWithoutPegawai, nativeQuery = true)
	List<Pelanggan> findByPembayaran_RekapBulanan(@Param("idPerusahaan") int idPerusahaan, @Param("tanggalBayarAwal") String tanggalBayarAwal, @Param("tanggalBayarAkhir") String tanggalBayarAkhir);

	@Query (summerizeEstimasiPemasukanBulanan)
	long sumarizeEstimasiPemasukanBulanan(Perusahaan perusahaan, Status status);
	@Query (summerizeTotalEstimasiTunggakan)
	long summarizeTotalAkumulasiTunggakan(Perusahaan perusahaan, Status status);
}
