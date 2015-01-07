package com.unitedvision.tvkabel.persistence.repository;

import java.time.Month;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.unitedvision.tvkabel.persistence.entity.PegawaiEntity;
import com.unitedvision.tvkabel.persistence.entity.PelangganEntity;
import com.unitedvision.tvkabel.persistence.entity.PembayaranEntity;
import com.unitedvision.tvkabel.persistence.entity.PerusahaanEntity;
import com.unitedvision.tvkabel.persistence.entity.PembayaranEntity.TagihanValue;

public interface PembayaranRepository extends JpaRepository<PembayaranEntity, Integer> {
	String countPemasukanBulanBerjalan = "SELECT COALESCE(SUM(p.jumlahBayar), 0) FROM PembayaranEntity p WHERE p.pegawai.perusahaan = ?1 AND p.tanggalBayar BETWEEN ?2 AND ?3";

	PembayaranEntity findFirstByPelangganOrderByIdDesc(PelangganEntity pelangganEntity);

	List<PembayaranEntity> findByPegawaiAndTanggalBayarBetween(PegawaiEntity pegawaiEntity, Date tanggalAwal, Date tanggalAkhir, Pageable page);
	List<PembayaranEntity> findByPelangganAndTanggalBayarBetween(PelangganEntity pelangganEntity, Date tanggalAwal, Date tanggalAkhir, Pageable page);
	List<PembayaranEntity> findByPelangganAndTagihan_Tahun(PelangganEntity pelangganEntity, int tahun);
	List<PembayaranEntity> findByPelangganAndTagihan_TahunAndTagihan_BulanBetween(PelangganEntity pelangganEntity, int tahun, Month bulanAwal, Month bulanAkhir);
	List<PembayaranEntity> findByPegawai_PerusahaanAndTagihan(PerusahaanEntity perusahaanEntity, TagihanValue tagihanValue);
	List<PembayaranEntity> findByPegawai_PerusahaanAndTagihan(PerusahaanEntity perusahaanEntity, TagihanValue tagihanValue, Pageable page);
	List<PembayaranEntity> findByPegawai_PerusahaanAndTanggalBayarBetween(PerusahaanEntity perusahaanEntity, Date tanggalAwal, Date tanggalAkhir);
	List<PembayaranEntity> findByPegawai_PerusahaanAndTanggalBayarBetween(PerusahaanEntity perusahaanEntity, Date tanggalAwal, Date tanggalAkhir, Pageable page);

	long countByPegawai_PerusahaanAndTanggalBayarBetween(PerusahaanEntity perusahaanEntity, Date tanggalAwal, Date tanggalAkhir);
	long countByPegawai_PerusahaanAndTagihan(PerusahaanEntity perusahaanEntity, TagihanValue tagihan);
	long countByPegawaiAndTanggalBayarBetween(PegawaiEntity pegawaiEntity, Date tanggalAwal, Date tanggalAkhir);
	long countByPelangganAndTanggalBayarBetween(PelangganEntity pelangganEntity, Date tanggalAwal, Date tanggalAkhir);
	
	@Query (countPemasukanBulanBerjalan)
	long countPemasukanBulanBerjalan(PerusahaanEntity perusahaanEntity, Date tanggalAwal, Date tanggalAkhir);
}
