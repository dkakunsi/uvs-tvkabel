package com.unitedvision.tvkabel.persistence.repository.test;

import static org.junit.Assert.assertEquals;

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
import com.unitedvision.tvkabel.entity.Kecamatan;
import com.unitedvision.tvkabel.entity.Kelurahan;
import com.unitedvision.tvkabel.entity.Kota;
import com.unitedvision.tvkabel.repository.KecamatanRepository;
import com.unitedvision.tvkabel.repository.KelurahanRepository;
import com.unitedvision.tvkabel.repository.KotaRepository;

@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration (classes = {ApplicationConfig.class})
@Transactional
@TransactionConfiguration (defaultRollback = true)
public class KelurahanRepositoryTest {
	
	@Autowired
	private KelurahanRepository kelurahanRepository;
	@Autowired
	private KecamatanRepository kecamatanRepository;
	@Autowired
	private KotaRepository kotaRepository;
	
	private Kecamatan kecamatan;
	private Kota kota;
	private Kelurahan kelurahan;
	
	@Before
	public void setup() {
		kota = new Kota();
		kota.setNama("Manado");
		
		kotaRepository.save(kota);
		
		kecamatan = new Kecamatan();
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
		Kelurahan kelurahan = new Kelurahan();
		kelurahan.setKecamatan(kecamatan);
		kelurahan.setNama("Paniki Atas");
		
		kelurahanRepository.save(kelurahan);
	}

	@Test
	public void test_InsertWithSameKecamatanAndNama() {
		Kelurahan kelurahan2 = new Kelurahan();
		kelurahan2.setKecamatan(kecamatan);
		kelurahan2.setNama("Paniki Bawah");
		
		try {
			kelurahanRepository.save(kelurahan2);
		} catch(PersistenceException e) {
			assertEquals("Nama yang anda masukkan sudah digunakan.", e.getMessage());
		}
	}

	@Test
	public void test_InsertWithSameNamaButDifferentKecamatan() {
		Kecamatan kecamatan2 = new Kecamatan();
		kecamatan2.setKota(kota);
		kecamatan2.setNama("Wanea");
		
		kecamatanRepository.save(kecamatan2);

		Kelurahan kelurahan2 = new Kelurahan();
		kelurahan2.setKecamatan(kecamatan2);
		kelurahan2.setNama("Paniki Bawah");
		
		kelurahanRepository.save(kelurahan2);
	}
}
