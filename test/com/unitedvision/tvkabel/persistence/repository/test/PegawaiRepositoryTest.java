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
import com.unitedvision.tvkabel.entity.Pegawai;
import com.unitedvision.tvkabel.entity.Perusahaan;
import com.unitedvision.tvkabel.entity.Pegawai.Kredensi;
import com.unitedvision.tvkabel.entity.Pegawai.Role;
import com.unitedvision.tvkabel.entity.Pegawai.Status;
import com.unitedvision.tvkabel.exception.EmptyCodeException;
import com.unitedvision.tvkabel.exception.EmptyIdException;
import com.unitedvision.tvkabel.exception.EntityNotExistException;
import com.unitedvision.tvkabel.repository.PegawaiRepository;
import com.unitedvision.tvkabel.repository.PerusahaanRepository;

@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration (classes = {ApplicationConfig.class})
@Transactional
@TransactionConfiguration (defaultRollback = true)
public class PegawaiRepositoryTest {
	@Autowired
	private PegawaiRepository pegawaiRepository;
	@Autowired
	private PerusahaanRepository perusahaanRepository;
	
	@Test
	public void testSaveWithUsernameException() throws EmptyIdException, EmptyCodeException {
		Perusahaan perusahaan = perusahaanRepository.findOne(17);
		
		Pegawai pegawai = new Pegawai();
		pegawai.setId(0);
		pegawai.setKode("TEST");
		pegawai.setKredensi(new Kredensi("jefry", "test", Role.OPERATOR));
		pegawai.setNama("Test");
		pegawai.setPerusahaan(perusahaan);
		pegawai.setStatus(Pegawai.Status.AKTIF);

		try {
			pegawaiRepository.save(pegawai);
		} catch (PersistenceException ex) {
			assertEquals("Username yang anda masukkan sudah digunakan.", ex.getMessage());
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
