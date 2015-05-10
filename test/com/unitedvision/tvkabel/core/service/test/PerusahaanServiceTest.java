package com.unitedvision.tvkabel.core.service.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.unitedvision.tvkabel.configuration.ApplicationConfig;
import com.unitedvision.tvkabel.entity.Alamat;
import com.unitedvision.tvkabel.entity.Kelurahan;
import com.unitedvision.tvkabel.entity.Kontak;
import com.unitedvision.tvkabel.entity.Perusahaan;
import com.unitedvision.tvkabel.exception.ApplicationException;
import com.unitedvision.tvkabel.exception.EmptyCodeException;
import com.unitedvision.tvkabel.exception.EmptyIdException;
import com.unitedvision.tvkabel.exception.EntityNotExistException;
import com.unitedvision.tvkabel.service.KelurahanService;
import com.unitedvision.tvkabel.service.PerusahaanService;

@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration (classes = {ApplicationConfig.class})
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

		Kontak kontak = new Kontak();
		kontak.setEmail("jondiru2003@yahoo.com");
		perusahaan.setKontak(kontak);
		
		Alamat alamat = new Alamat();
		alamat.setKelurahan(kelurahan);
		alamat.setLingkungan(1);
		perusahaan.setAlamat(alamat);
		
		perusahaan.setStatus(Perusahaan.Status.AKTIF);
		
		try {
			perusahaanService.regist(perusahaan);
		} catch (ApplicationException ex) {
			assertEquals("Email yang anda masukkan sudah digunakan.", ex.getMessage());
		}
	}

}
