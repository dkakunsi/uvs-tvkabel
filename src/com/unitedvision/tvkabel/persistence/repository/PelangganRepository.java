package com.unitedvision.tvkabel.persistence.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.unitedvision.tvkabel.exception.EntityNotExistException;
import com.unitedvision.tvkabel.persistence.entity.Kelurahan;
import com.unitedvision.tvkabel.persistence.entity.Pelanggan;
import com.unitedvision.tvkabel.persistence.entity.Perusahaan;
import com.unitedvision.tvkabel.persistence.entity.Pelanggan.Status;

public interface PelangganRepository extends JpaRepository<Pelanggan, Integer> {
	String findByPembayaran = "SELECT * FROM pelanggan WHERE id in (SELECT DISTINCT id_pelanggan FROM pembayaran WHERE (tanggal_bayar BETWEEN :tanggalBayarAwal AND :tanggalBayarAkhir) AND id_pegawai = :idPegawai) ORDER BY kode";
	String findByPembayaranWithoutPegawai = "SELECT * FROM pelanggan WHERE id in (SELECT DISTINCT id_pelanggan FROM pembayaran WHERE tanggal_bayar BETWEEN :tanggalBayarAwal AND :tanggalBayarAkhir) AND id_perusahaan = :idPerusahaan ORDER BY kode";
	String findByTanggalMulai = "SELECT * FROM pelanggan p WHERE p.status = :status AND p.tanggal_mulai like %:tanggal";
	String findByAlamatOrdered = "SELECT p FROM PelangganEntity p WHERE p.perusahaan = ?1 AND p.status = ?2 ORDER BY p.kelurahan, p.alamat.lingkungan";
	String findByAlamatOrderedNative = "SELECT * FROM pelanggan p WHERE p.id_perusahaan = :idPerusahaan AND p.status = :status ORDER BY p.id_kelurahan, p.lingkungan, p.kode";
	String summerizeEstimasiPemasukanBulanan = "SELECT COALESCE(SUM(p.detail.iuran), 0) FROM Pelanggan p WHERE p.perusahaan = ?1 AND p.status = ?2";
	String summerizeTotalEstimasiTunggakan = "SELECT COALESCE(SUM(p.detail.tunggakan * p.detail.iuran), 0) FROM Pelanggan p WHERE p.perusahaan = ?1 AND p.status = ?2";
	
	Pelanggan findByPerusahaanAndKode(Perusahaan perusahaan, String kode) throws EntityNotExistException;
	Pelanggan findByPerusahaanAndNama(Perusahaan perusahaan, String nama) throws EntityNotExistException;

	List<Pelanggan> findByPerusahaanAndNomorBukuContainingOrderByKodeAsc(Perusahaan perusahaan, int nomorBuku, Pageable page);
	List<Pelanggan> findByPerusahaanAndStatusAndNomorBukuContainingOrderByKodeAsc(Perusahaan perusahaan, Status status, int nomorBuku, Pageable page);

	List<Pelanggan> findByPerusahaanAndKodeContainingOrderByKodeAsc(Perusahaan perusahaan, String kode, Pageable page);
	List<Pelanggan> findByPerusahaanAndNamaContainingOrderByKodeAsc(Perusahaan perusahaan, String nama, Pageable page);
	List<Pelanggan> findByPerusahaanAndStatusOrderByKodeAsc(Perusahaan perusahaan, Status status);
	List<Pelanggan> findByPerusahaanAndStatusOrderByKodeAsc(Perusahaan perusahaan, Status status, Pageable page);
	List<Pelanggan> findByPerusahaanAndStatusAndNamaContainingOrderByKodeAsc(Perusahaan perusahaan, Status status, String nama, Pageable page);
	List<Pelanggan> findByPerusahaanAndStatusAndKodeContainingOrderByKodeAsc(Perusahaan perusahaan, Status status, String kode, Pageable page);

	List<Pelanggan> findByPerusahaanAndStatusAndKelurahanAndAlamat_LingkunganOrderByKodeAsc(Perusahaan perusahaan, Status status, Kelurahan kelurahan, int lingkungan);

	List<Pelanggan> findByPerusahaanAndStatusAndDetail_TunggakanOrderByKodeAsc(Perusahaan perusahaan, Status status, int tunggakan);
	List<Pelanggan> findByPerusahaanAndStatusAndDetail_TunggakanBetweenOrderByKodeAsc(Perusahaan perusahaan, Status status, int tunggakanAwal, int tunggakanAkhir);

	long countByPerusahaanAndStatus(Perusahaan perusahaan, Status status);

	long countByPerusahaanAndNamaContaining(Perusahaan perusahaan, String nama);
	long countByPerusahaanAndStatusAndNamaContaining(Perusahaan perusahaan, Status status, String nama);
	
	long countByPerusahaanAndKodeContaining(Perusahaan perusahaan, String kode);
	long countByPerusahaanAndStatusAndKodeContaining(Perusahaan perusahaan, Status status, String kode);

	long countByPerusahaanAndNomorBukuContaining(Perusahaan perusahaan, int nomorBuku);
	long countByPerusahaanAndStatusAndNomorBukuContaining(Perusahaan perusahaan, Status status, int nomorBuku);

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
