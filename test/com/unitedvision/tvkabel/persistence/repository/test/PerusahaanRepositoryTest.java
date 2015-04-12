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
import com.unitedvision.tvkabel.entity.Kelurahan;
import com.unitedvision.tvkabel.entity.Kontak;
import com.unitedvision.tvkabel.entity.Perusahaan;
import com.unitedvision.tvkabel.exception.EmptyCodeException;
import com.unitedvision.tvkabel.exception.EmptyIdException;
import com.unitedvision.tvkabel.repository.PerusahaanRepository;

@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration (classes = {ApplicationConfig.class})
@Transactional
@TransactionConfiguration (defaultRollback = true)
public class PerusahaanRepositoryTest {
	@Autowired
	private PerusahaanRepository perusahaanRepository;
	
	@Test
	public void testFindFirstByOrderByIdDesc() {
		Perusahaan perusahaan = perusahaanRepository.findFirstByOrderByIdDesc();
		
		assertNotNull(perusahaan);
		assertNotEquals(0, perusahaan.getId());
	}
	
	@Test
	public void testInsertWithExsistedEmail() throws EmptyIdException, EmptyCodeException {
		Perusahaan perusahaan = new Perusahaan();
		perusahaan.setKode("TEST");
		perusahaan.setNama("Test");
		perusahaan.setStatus(Perusahaan.Status.AKTIF);
		
		Kontak kontak = new Kontak();
		kontak.setEmail("jondiru2003@yahoo.com");
		perusahaan.setKontak(kontak);

		Kelurahan kelurahan = new Kelurahan();
		kelurahan.setId(21);
		perusahaan.setKelurahan(kelurahan);
		
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
