package com.unitedvision.tvkabel.persistence.repository.test;

import static org.junit.Assert.*;

import javax.persistence.PersistenceException;

import org.junit.Before;
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
import com.unitedvision.tvkabel.entity.Pegawai;
import com.unitedvision.tvkabel.entity.Perusahaan;
import com.unitedvision.tvkabel.entity.Pegawai.Kredensi;
import com.unitedvision.tvkabel.entity.Pegawai.Role;
import com.unitedvision.tvkabel.entity.Pegawai.Status;
import com.unitedvision.tvkabel.exception.ApplicationException;
import com.unitedvision.tvkabel.exception.EntityNotExistException;
import com.unitedvision.tvkabel.repository.KecamatanRepository;
import com.unitedvision.tvkabel.repository.KelurahanRepository;
import com.unitedvision.tvkabel.repository.KotaRepository;
import com.unitedvision.tvkabel.repository.PegawaiRepository;
import com.unitedvision.tvkabel.repository.PerusahaanRepository;

@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration (classes = {ApplicationConfig.class})
@Transactional
@TransactionConfiguration (defaultRollback = true)
public class PegawaiRepositoryTest {
	@Autowired
	private PerusahaanRepository perusahaanRepository;
	@Autowired
	private KotaRepository kotaRepository;
	@Autowired
	private KecamatanRepository kecamatanRepository;
	@Autowired
	private KelurahanRepository kelurahanRepository;
	@Autowired
	private PegawaiRepository pegawaiRepository;

	private Perusahaan perusahaan;
	
	@Before
	public void setup() {
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
	}
	
	@Test
	public void test_InsertSuccess() throws ApplicationException {
		Pegawai pegawai = new Pegawai();
		pegawai.setId(0);
		pegawai.setKode("01001");
		pegawai.setKredensi(new Kredensi("admin", "admin", Role.OPERATOR));
		pegawai.setNama("ADMIN");
		pegawai.setPerusahaan(perusahaan);
		pegawai.setStatus(Pegawai.Status.AKTIF);

		pegawaiRepository.save(pegawai);
		
		assertTrue(pegawaiRepository.count() != 0);
	}
	
	@Test
	public void test_InsertWithSameUsername() throws ApplicationException {
		Pegawai pegawai = new Pegawai();
		pegawai.setId(0);
		pegawai.setKode("01001");
		pegawai.setKredensi(new Kredensi("admin", "admin", Role.OPERATOR));
		pegawai.setNama("ADMIN");
		pegawai.setPerusahaan(perusahaan);
		pegawai.setStatus(Pegawai.Status.AKTIF);

		pegawaiRepository.save(pegawai);

		Pegawai pegawai2 = new Pegawai();
		pegawai2.setId(0);
		pegawai2.setKode("01002");
		pegawai2.setKredensi(new Kredensi("admin", "admin", Role.OPERATOR));
		pegawai2.setNama("Jefry Wakkary");
		pegawai2.setPerusahaan(perusahaan);
		pegawai2.setStatus(Pegawai.Status.AKTIF);

		try {
			pegawaiRepository.save(pegawai2);
			
			throw new ApplicationException();
		} catch (PersistenceException ex) {
			assertEquals("Username yang anda masukkan sudah digunakan.", ex.getMessage());
		}
	}
	
	@Test
	public void test_InsertWithSameKode() throws ApplicationException {
		Pegawai pegawai = new Pegawai();
		pegawai.setId(0);
		pegawai.setKode("01001");
		pegawai.setKredensi(new Kredensi("admin", "admin", Role.OPERATOR));
		pegawai.setNama("ADMIN");
		pegawai.setPerusahaan(perusahaan);
		pegawai.setStatus(Pegawai.Status.AKTIF);

		pegawaiRepository.save(pegawai);
		
		Pegawai pegawai2 = new Pegawai();
		pegawai2.setId(0);
		pegawai2.setKode("01001");
		pegawai2.setKredensi(new Kredensi("jefry", "jefry", Role.OPERATOR));
		pegawai2.setNama("Jefry Wakkary");
		pegawai2.setPerusahaan(perusahaan);
		pegawai2.setStatus(Pegawai.Status.AKTIF);

		try {
			pegawaiRepository.save(pegawai2);
			
			throw new ApplicationException();
		} catch (PersistenceException ex) {
			assertEquals("Kode yang anda masukkan sudah digunakan.", ex.getMessage());
		}
	}
	
	@Test
	public void test_InsertWithSameNama() throws ApplicationException {
		Pegawai pegawai = new Pegawai();
		pegawai.setId(0);
		pegawai.setKode("01001");
		pegawai.setKredensi(new Kredensi("admin", "admin", Role.OPERATOR));
		pegawai.setNama("ADMIN");
		pegawai.setPerusahaan(perusahaan);
		pegawai.setStatus(Pegawai.Status.AKTIF);

		pegawaiRepository.save(pegawai);
		
		Pegawai pegawai2 = new Pegawai();
		pegawai2.setId(0);
		pegawai2.setKode("01001");
		pegawai2.setKredensi(new Kredensi("jefry", "jefry", Role.OPERATOR));
		pegawai2.setNama("ADMIN");
		pegawai2.setPerusahaan(perusahaan);
		pegawai2.setStatus(Pegawai.Status.AKTIF);

		try {
			pegawaiRepository.save(pegawai2);
			
			throw new ApplicationException();
		} catch (PersistenceException ex) {
			assertEquals("Kode yang anda masukkan sudah digunakan.", ex.getMessage());
		}
	}

	@Test
	public void testFindByUsernameAndStatusAndKredensi_Role() throws EntityNotExistException {
		String username = "john";
		Status status = Status.AKTIF;
		
		Pegawai pegawai = pegawaiRepository.findByKredensi_UsernameAndStatus(username, status);

		assertNotNull(pegawai);
		assertEquals(14, pegawai.getId());
	}

	@Test (expected = EntityNotExistException.class)
	public void testFindByUsernameAndStatusAndKredensi_RoleButRoleWasDenied() throws EntityNotExistException {
		String username = "xxxxxxxxxxx";
		Status status = Status.AKTIF;
		
		pegawaiRepository.findByKredensi_UsernameAndStatus(username, status);
	}
	
	@Test
	public void testCountByPerusahaanAndStatusAndKodeContaining() {
		Perusahaan perusahaan = perusahaanRepository.findOne(17);
		Status status = Status.AKTIF;
		String kode = "PG";
		
		long hasil = pegawaiRepository.countByPerusahaanAndStatusAndKodeContaining(perusahaan, status, kode);
		
		assertNotEquals(0, hasil);
	}

	@Test
	public void testCountByPerusahaanAndStatusAndNamaContaining() {
		Perusahaan perusahaan = perusahaanRepository.findOne(17);
		Status status = Status.AKTIF;
		String nama = "John";
		
		long hasil = pegawaiRepository.countByPerusahaanAndStatusAndNamaContaining(perusahaan, status, nama);
		
		assertNotEquals(0, hasil);
	}
}
