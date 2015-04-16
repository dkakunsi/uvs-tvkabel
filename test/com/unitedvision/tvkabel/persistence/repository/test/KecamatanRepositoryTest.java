package com.unitedvision.tvkabel.persistence.repository.test;

import static org.junit.Assert.*;

import java.util.List;

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
import com.unitedvision.tvkabel.exception.EntityNotExistException;
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

	@Test (expected = EntityNotExistException.class)
	public void testGetByKota_Empty() throws EntityNotExistException {
		Kota kota = kotaRepository.findOne(14);
		
		@SuppressWarnings("unused")
		List<Kecamatan> list = kecamatanRepository.findByKota(kota);
	}

	//@Test (expected = EntityNotExistException.class)
	@Test
	public void testGetNull() {
		Kecamatan kecamatan = kecamatanRepository.findOne(0);
		
		assertNotNull(kecamatan);
	}
}
