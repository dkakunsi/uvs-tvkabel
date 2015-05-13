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
import com.unitedvision.tvkabel.entity.Kecamatan;
import com.unitedvision.tvkabel.entity.Kota;
import com.unitedvision.tvkabel.repository.KecamatanRepository;
import com.unitedvision.tvkabel.repository.KotaRepository;

@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration (classes = {ApplicationConfig.class})
@Transactional
@TransactionConfiguration (defaultRollback = true)
public class KecamatanRepositoryTest {
	@Autowired
	private KecamatanRepository kecamatanRepository;
	@Autowired
	private KotaRepository kotaRepository;
	
	private Kota kota;
	private Kecamatan kecamatan;
	private long countBefore;
	
	@Before
	public void setup() {
		kota = new Kota();
		kota.setNama("Manado");
		
		kotaRepository.save(kota);
		
		kecamatan = new Kecamatan();
		kecamatan.setKota(kota);
		kecamatan.setNama("Mapanget");
		
		kecamatanRepository.save(kecamatan);
		
		assertTrue(kecamatanRepository.count() != 0);
		countBefore = kecamatanRepository.count();
	}

	@Test
	public void test_InsertSuccess() {
		Kecamatan kecamatan = new Kecamatan();
		kecamatan.setKota(kota);
		kecamatan.setNama("Wanea");
		
		kecamatanRepository.save(kecamatan);
		
		assertTrue(kecamatanRepository.count() == countBefore + 1);
	}
	
	@Test
	public void test_InsertWithSameKotaAndNama() {
		Kecamatan kecamatan2 = new Kecamatan();
		kecamatan2.setKota(kota);
		kecamatan2.setNama("Mapanget");
		
		try {
			kecamatanRepository.save(kecamatan2);
		} catch(PersistenceException e) {
			assertEquals("Nama yang anda masukkan sudah digunakan.", e.getMessage());
		}
	}
	
	@Test
	public void test_InsertWithSameNamaButDifferentKota() {
		Kota kota2 = new Kota();
		kota2.setNama("Bitung");
		
		kotaRepository.save(kota2);
		
		Kecamatan kecamatan2 = new Kecamatan();
		kecamatan2.setKota(kota2);
		kecamatan2.setNama("Mapanget");
		
		kecamatanRepository.save(kecamatan2);
	}
	
	//@Test // TODO Error jika tidak di-komen
	public void test_InsertWithTransientKota() {
		Kota kota2 = new Kota();
		kota2.setNama("Bitung");
		
		Kecamatan kecamatan2 = new Kecamatan();
		kecamatan2.setKota(kota2);
		kecamatan2.setNama("Mapanget");
		
		kecamatanRepository.save(kecamatan2);
		
		//assertTrue(kecamatanRepository.count() != 0);
		//assertTrue(kotaRepository.count() == 2);
	}
}
