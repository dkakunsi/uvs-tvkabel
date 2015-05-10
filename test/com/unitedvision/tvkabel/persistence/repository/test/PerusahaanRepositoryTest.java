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
import com.unitedvision.tvkabel.entity.Perusahaan;
import com.unitedvision.tvkabel.entity.Perusahaan.Status;
import com.unitedvision.tvkabel.exception.EmptyCodeException;
import com.unitedvision.tvkabel.exception.EmptyIdException;
import com.unitedvision.tvkabel.repository.KecamatanRepository;
import com.unitedvision.tvkabel.repository.KelurahanRepository;
import com.unitedvision.tvkabel.repository.KotaRepository;
import com.unitedvision.tvkabel.repository.PerusahaanRepository;

@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration (classes = {ApplicationConfig.class})
@Transactional
@TransactionConfiguration (defaultRollback = true)
public class PerusahaanRepositoryTest {
	@Autowired
	private PerusahaanRepository perusahaanRepository;
	@Autowired
	private KotaRepository kotaRepository;
	@Autowired
	private KecamatanRepository kecamatanRepository;
	@Autowired
	private KelurahanRepository kelurahanRepository;
	
	private Kelurahan kelurahan;
	
	@Before
	public void setup() {
		Kota kota = new Kota();
		kota.setNama("Manado");
		
		kotaRepository.save(kota);
		
		Kecamatan kecamatan = new Kecamatan();
		kecamatan.setKota(kota);
		kecamatan.setNama("Mapanget");
		
		kecamatanRepository.save(kecamatan);
		
		kelurahan = new Kelurahan();
		kelurahan.setKecamatan(kecamatan);
		kelurahan.setNama("Paniki Bawah");
		
		kelurahanRepository.save(kelurahan);
	}
	
	@Test
	public void test_InsertSuccess() {
		Perusahaan perusahaan = new Perusahaan();
		perusahaan.setNama("TVK. Global Vision");
		perusahaan.setNamaPT("PT. Aspetika Manasa SULUT");
		perusahaan.setStatus(Status.AKTIF);
		
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
		
		assertTrue(perusahaanRepository.count() != 0);
		assertEquals("COM1", perusahaan.getKode());
	}
	
	@Test
	public void testFindFirstByOrderByIdDesc() {
		Perusahaan perusahaan = perusahaanRepository.findFirstByOrderByIdDesc();
		
		assertNotNull(perusahaan);
		assertNotEquals(0, perusahaan.getId());
	}
	
	@Test
	public void testInsertWithExsistedEmail() throws EmptyIdException, EmptyCodeException {
		test_InsertSuccess();
		
		Perusahaan perusahaan = new Perusahaan();
		perusahaan.setKode("TEST");
		perusahaan.setNama("Test");
		perusahaan.setStatus(Perusahaan.Status.AKTIF);
		
		Kontak kontak = new Kontak();
		kontak.setEmail("admin@globalvision.com");
		perusahaan.setKontak(kontak);

		Kelurahan kelurahan = new Kelurahan();
		kelurahan.setId(21);
		Alamat alamat = new Alamat();
		alamat.setKelurahan(kelurahan);
		alamat.setLingkungan(1);
		perusahaan.setAlamat(alamat);
		
		try {
			perusahaanRepository.save(perusahaan);
		} catch (PersistenceException ex) {
			assertEquals("Email yang anda masukkan sudah digunakan.", ex.getMessage());
		}
	}
	
	@Test
	public void testGet() {
		Perusahaan perusahaan = perusahaanRepository.getOne(17);
		
		assertNotNull(perusahaan.getNamaPT());
		assertNotEquals("", perusahaan.getNamaPT());
	}
}
