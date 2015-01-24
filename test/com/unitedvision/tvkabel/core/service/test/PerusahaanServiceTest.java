package com.unitedvision.tvkabel.core.service.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.unitedvision.tvkabel.core.service.KelurahanService;
import com.unitedvision.tvkabel.core.service.PerusahaanService;
import com.unitedvision.tvkabel.exception.ApplicationException;
import com.unitedvision.tvkabel.exception.EmptyCodeException;
import com.unitedvision.tvkabel.exception.EmptyIdException;
import com.unitedvision.tvkabel.exception.EntityNotExistException;
import com.unitedvision.tvkabel.persistence.SpringDataJpaConfig;
import com.unitedvision.tvkabel.persistence.entity.Kelurahan;
import com.unitedvision.tvkabel.persistence.entity.Perusahaan;

@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration (classes = {SpringDataJpaConfig.class})
@Transactional
@TransactionConfiguration (defaultRollback = true)
public class PerusahaanServiceTest {
	
	@Autowired
	private PerusahaanService perusahaanService;
	@Autowired
	private KelurahanService kelurahanService;
	
	@Test
	public void testSaveWithExsistedEmail() throws EntityNotExistException, EmptyIdException, EmptyCodeException {
		Kelurahan kelurahan = kelurahanService.getOne(21);

		Perusahaan perusahaan = new Perusahaan();
		perusahaan.setNama("Test");
		perusahaan.setEmail("jondiru2003@yahoo.com");
		perusahaan.setKelurahan(kelurahan);
		perusahaan.setStatus(Perusahaan.Status.AKTIF);
		
		try {
			perusahaanService.regist(perusahaan);
		} catch (ApplicationException ex) {
			assertEquals("Email yang anda masukkan sudah digunakan.", ex.getMessage());
		}
	}

}
