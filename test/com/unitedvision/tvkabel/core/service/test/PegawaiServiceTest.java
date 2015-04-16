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
import com.unitedvision.tvkabel.entity.Pegawai;
import com.unitedvision.tvkabel.entity.Perusahaan;
import com.unitedvision.tvkabel.entity.Pegawai.Kredensi;
import com.unitedvision.tvkabel.entity.Pegawai.Role;
import com.unitedvision.tvkabel.exception.ApplicationException;
import com.unitedvision.tvkabel.exception.EmptyCodeException;
import com.unitedvision.tvkabel.exception.EmptyIdException;
import com.unitedvision.tvkabel.exception.EntityNotExistException;
import com.unitedvision.tvkabel.service.PegawaiService;
import com.unitedvision.tvkabel.service.PerusahaanService;

@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration (classes = {ApplicationConfig.class})
@Transactional
@TransactionConfiguration (defaultRollback = true)
public class PegawaiServiceTest {
	
	@Autowired
	private PegawaiService pegawaiService;
	@Autowired
	private PerusahaanService perusahaanService;
	
	@Test
	public void testSaveWithExsistedUsername() throws EntityNotExistException, EmptyIdException, EmptyCodeException {
		Perusahaan perusahaan = perusahaanService.getOne(17);
		
		Pegawai pegawai = new Pegawai();
		pegawai.setId(0);
		pegawai.setKode("TEST");
		pegawai.setKredensi(new Kredensi("jefry", "test", Role.OPERATOR));
		pegawai.setNama("Test");
		pegawai.setPerusahaan(perusahaan);
		pegawai.setStatus(Pegawai.Status.AKTIF);
		
		try {
			pegawaiService.save(pegawai);
		} catch (ApplicationException e) {
			assertEquals("Username yang anda masukkan sudah digunakan.", e.getMessage());
		}
	}

}
