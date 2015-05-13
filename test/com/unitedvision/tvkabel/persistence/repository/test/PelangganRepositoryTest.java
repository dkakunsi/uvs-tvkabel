package com.unitedvision.tvkabel.persistence.repository.test;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.unitedvision.tvkabel.configuration.ApplicationConfig;
import com.unitedvision.tvkabel.entity.Alamat;
import com.unitedvision.tvkabel.entity.Kecamatan;
import com.unitedvision.tvkabel.entity.Kelurahan;
import com.unitedvision.tvkabel.entity.Kontak;
import com.unitedvision.tvkabel.entity.Kota;
import com.unitedvision.tvkabel.entity.Pelanggan;
import com.unitedvision.tvkabel.entity.Pelanggan.Detail;
import com.unitedvision.tvkabel.entity.Perusahaan;
import com.unitedvision.tvkabel.entity.Pelanggan.Status;
import com.unitedvision.tvkabel.exception.ApplicationException;
import com.unitedvision.tvkabel.exception.EntityNotExistException;
import com.unitedvision.tvkabel.repository.KecamatanRepository;
import com.unitedvision.tvkabel.repository.KelurahanRepository;
import com.unitedvision.tvkabel.repository.KotaRepository;
import com.unitedvision.tvkabel.repository.PegawaiRepository;
import com.unitedvision.tvkabel.repository.PelangganRepository;
import com.unitedvision.tvkabel.repository.PerusahaanRepository;
import com.unitedvision.tvkabel.util.DateUtil;

@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration (classes = {ApplicationConfig.class})
@Transactional
@TransactionConfiguration (defaultRollback = true)
public class PelangganRepositoryTest {
	@Autowired
	private PelangganRepository pelangganRepository;
	@Autowired
	private PerusahaanRepository perusahaanRepository;
	@Autowired
	private PegawaiRepository pegawaiRepository;
	@Autowired
	private KelurahanRepository kelurahanRepository;
	@Autowired
	private KotaRepository kotaRepository;
	@Autowired
	private KecamatanRepository kecamatanRepository;
	
	private Perusahaan perusahaan;
	
	@Before
	public void setup() throws ApplicationException {
		Kota kota = new Kota();
		kota.setNama("Manado");
		
		kotaRepository.save(kota);
		
		Kecamatan kecamatan = new Kecamatan();
		kecamatan.setKota(kota);
		kecamatan.setNama("Mapanget");
		
		kecamatanRepository.save(kecamatan);
		
		Kelurahan kelurahan = new Kelurahan();
		kelurahan.setKecamatan(kecamatan);
		kelurahan.setNama("Paniki Bawah");
		
		kelurahanRepository.save(kelurahan);
		
		perusahaan = new Perusahaan();
		perusahaan.setNama("TVK. Global Vision");
		perusahaan.setNamaPT("PT. Aspetika Manasa SULUT");
		perusahaan.setStatus(Perusahaan.Status.AKTIF);
		
		Alamat alamat = new Alamat();
		alamat.setKelurahan(kelurahan);
		alamat.setLingkungan(1);
		perusahaan.setAlamat(alamat);
		
		Kontak kontak = new Kontak();
		kontak.setEmail("admin@globalvision.com");
		kontak.setHp("082323787878");
		perusahaan.setKontak(kontak);
		
		perusahaan.generateKode(0);
		
		perusahaanRepository.save(perusahaan);
		
		Pelanggan pelanggan = new Pelanggan();
		pelanggan.setKode("0101001");
		pelanggan.setNama("Pelanggan");
		pelanggan.setNomorBuku("1");
		pelanggan.setPerusahaan(perusahaan);
		pelanggan.setStatus(Status.AKTIF);
		
		Detail detail = new Detail();
		detail.setIuran(50000L);
		detail.setJumlahTv(1);
		detail.setTanggalMulai(DateUtil.getNow());
		detail.setTunggakan(0);
		pelanggan.setDetail(detail);
		
		pelanggan.setKontak(perusahaan.getKontak());
		pelanggan.setAlamat(perusahaan.getAlamat());
		
		pelangganRepository.save(pelanggan);
	}
	
	@Test
	public void test_Get() {
		Pelanggan pelanggan = pelangganRepository.getOne(perusahaan.getId());
		Perusahaan perusahaanLoaded = pelanggan.getPerusahaan();
		
		assertNotNull(perusahaanLoaded.getNamaPT());
		assertEquals(perusahaan, perusahaanLoaded);
	}
	
	@Test
	public void testCountEstimasi() {
		long hasil = pelangganRepository.sumarizeEstimasiPemasukanBulanan(perusahaan, Status.AKTIF);
		
		assertNotEquals(0, hasil);
	}

	@Test
	@Ignore
	public void testCountEstimasiWhenNoData() {
		long hasil = pelangganRepository.sumarizeEstimasiPemasukanBulanan(perusahaan, Status.AKTIF);
		
		assertEquals(0, hasil);
	}

	@Test
	@Ignore
	public void countAkumulasi() {
		long hasil = pelangganRepository.summarizeTotalAkumulasiTunggakan(perusahaan, Status.AKTIF);

		assertNotEquals(0, hasil);
	}

	@Test
	@Ignore
	public void countAkumulasiWhenNoData() {
		long hasil = pelangganRepository.summarizeTotalAkumulasiTunggakan(perusahaan, Status.AKTIF);

		assertEquals(0, hasil);
	}
	
	@Test
	public void testGetByTanggalCont() {
		String tanggal = "13";
		
		List<Pelanggan> listPelanggan = pelangganRepository.findByTanggalMulai(Status.AKTIF.ordinal(), tanggal);
		
		assertNotEquals(0, listPelanggan.size());
	}
	
	@Test
	@Ignore
	public void testFindByPembayaran() {
		Date tanggalBayarAwal = DateUtil.getDate(2014, 12, 1);
		String tanggalBayarAwalStr = DateUtil.toDatabaseString(tanggalBayarAwal, "-");
		Date tanggalBayarAkhir = DateUtil.getDate(2015, 12, 31);
		String tanggalBayarAkhirStr = DateUtil.toDatabaseString(tanggalBayarAkhir, "-");
		
		List<Pelanggan> list = pelangganRepository.findByPembayaran(14, tanggalBayarAwalStr, tanggalBayarAkhirStr);

		assertEquals(164, list.size());
	}

	@Test
	public void testFindByPerusahaanAndStatusAndKelurahanAndAlamat_Lingkungan() throws EntityNotExistException {
		Status status = Status.AKTIF;
		Kelurahan kelurahan = kelurahanRepository.findByNama("Paniki Bawah");
		int lingkungan = 1;
		
		List<Pelanggan> list = pelangganRepository.findByPerusahaanAndStatusAndAlamat_KelurahanAndAlamat_LingkunganOrderByKodeAsc(perusahaan, status, kelurahan, lingkungan);
		
		assertNotEquals(0, list.size());
	}

	@Test
	public void testFindByPerusahaanAndStatusAndKelurahanAndAlamat_LingkunganWhenNoData() throws EntityNotExistException {
		Status status = Status.BERHENTI;
		Kelurahan kelurahan = kelurahanRepository.findByNama("Paniki Bawah");
		int lingkungan = 1;
		
		List<Pelanggan> list = pelangganRepository.findByPerusahaanAndStatusAndAlamat_KelurahanAndAlamat_LingkunganOrderByKodeAsc(perusahaan, status, kelurahan, lingkungan);

		assertNotNull(list);
		assertEquals(0, list.size());
	}

	@Test
	public void testFindByPerusahaanAndStatusAndDetail_Tunggakan() throws EntityNotExistException {
		Status status = Status.AKTIF;
		int tunggakan = 0;
		
		List<Pelanggan> list = pelangganRepository.findByPerusahaanAndStatusAndDetail_TunggakanOrderByKodeAsc(perusahaan, status, tunggakan);
		
		assertNotEquals(0, list.size());
	}
	
	@Test
	public void testCountByPerusahaanAndStatusAndDetail_Tunggakan() {
		Status status = Status.AKTIF;
		int tunggakan = 1;
		
		long hasil = pelangganRepository.countByPerusahaanAndStatusAndDetail_Tunggakan(perusahaan, status, tunggakan);
		
		assertNotEquals(138, hasil);
	}
	
	@Test
	public void testCountByPerusahaanAndStatusAndDetail_TunggakanMoreThan() {
		Status status = Status.AKTIF;
		int tunggakan = 1;
		
		long hasil = pelangganRepository.countByPerusahaanAndStatusAndDetail_TunggakanGreaterThan(perusahaan, status, tunggakan);
		
		assertNotEquals(655, hasil);
	}
	
	@Test
	public void testCountByPerusahaanAndStatusAndDetail_TunggakanLessThan() {
		Status status = Status.AKTIF;
		int tunggakan = 1;
		
		long hasil = pelangganRepository.countByPerusahaanAndStatusAndDetail_TunggakanLessThan(perusahaan, status, tunggakan);
		
		assertNotEquals(28, hasil);
	}
	
	@Test
	public void testFindByPerusahaanAndStatusAndKodeContaining() throws EntityNotExistException {
		Status status = Status.AKTIF;
		String kode = "01";
		
		List<Pelanggan> list = pelangganRepository.findByPerusahaanAndStatusAndKodeContainingOrderByKodeAsc(perusahaan, status, kode);
		
		assertNotEquals(0, list.size());
	}

	@Test
	@Ignore
	public void testFindByPerusahaanAndStatusOrderByAlamat() {
		List<Pelanggan> list = pelangganRepository.findByPerusahaanAndStatusOrderByAlamat(17, Status.AKTIF);

		int[] limit = {117, 2, 81, 84, 22, 48, 85, 46, 1, 5, 2, 8, 11, 7, 7, 3, 9, 6, 9};
		int i = 0;
		String kelurahan = "";
		int lingkungan = 0, counter = 0;
		boolean isNew = true;
		boolean next = false;
		for (Pelanggan en : list) {
			if (!kelurahan.equals(en.getNamaKelurahan()) || lingkungan != en.getLingkungan()) {
				kelurahan = en.getNamaKelurahan();
				lingkungan = en.getLingkungan();
				
				if (isNew == false)
					next = true;
			}

			if (next == true) {
				//assertEquals(limit[counter], i);
				i = 0;
				next = false;
				counter++;
			}

			i++;
			isNew = false;
			
			System.out.println(String.format("%s, %s, %d", en.getKode(), en.getNamaKelurahan(), en.getLingkungan()));
		}
		assertEquals(limit[counter], i);
	}
	
	@Test
	public void testGetByTanggalMulai_Like() {
		String tanggal = "13";
		tanggal = DateUtil.getDayString(tanggal);
		
		List<Pelanggan> listPelanggan = pelangganRepository.findByTanggalMulai(Status.AKTIF.ordinal(), tanggal);
		
		assertNotNull(listPelanggan);
		assertNotEquals(0, listPelanggan.size());
		assertEquals(1, listPelanggan.size());
	}
}
