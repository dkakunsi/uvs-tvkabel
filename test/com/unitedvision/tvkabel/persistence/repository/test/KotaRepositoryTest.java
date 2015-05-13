package com.unitedvision.tvkabel.persistence.repository.test;

import static org.junit.Assert.*;

import javax.persistence.PersistenceException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.unitedvision.tvkabel.configuration.ApplicationConfig;
import com.unitedvision.tvkabel.entity.Kota;
import com.unitedvision.tvkabel.repository.KotaRepository;

@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration (classes = {ApplicationConfig.class})
@Transactional
@TransactionConfiguration (defaultRollback = true)
public class KotaRepositoryTest {
	
	@Autowired
	private KotaRepository kotaRepository;
	
	@Test
	public void test_InsertSuccess() {
		Kota kota = new Kota();
		kota.setNama("Manado");
		
		kotaRepository.save(kota);
		
		assertTrue(kotaRepository.count() != 0);
	}
	
	@Test
	public void test_InsertWithSameName() {
		Kota kota = new Kota();
		kota.setNama("Manado");
		
		kotaRepository.save(kota);
		
		Kota kota2 = new Kota();
		kota.setNama("Manado");
		
		try {
			kotaRepository.save(kota2);
		} catch(PersistenceException e) {
			assertEquals("Nama yang anda masukkan sudah digunakan.", e.getMessage());
		}
	}

}
