package com.unitedvision.tvkabel.persistence.repository.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.unitedvision.tvkabel.exception.EntityNotExistException;
import com.unitedvision.tvkabel.persistence.SpringDataJpaConfig;
import com.unitedvision.tvkabel.persistence.entity.Pegawai;
import com.unitedvision.tvkabel.persistence.entity.Perusahaan;
import com.unitedvision.tvkabel.persistence.entity.Pegawai.Status;
import com.unitedvision.tvkabel.persistence.repository.PegawaiRepository;
import com.unitedvision.tvkabel.persistence.repository.PerusahaanRepository;

@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration (classes = {SpringDataJpaConfig.class})
@Transactional
@TransactionConfiguration (defaultRollback = true)
public class PegawaiRepositoryTest {
	@Autowired
	private PegawaiRepository pegawaiRepository;
	@Autowired
	private PerusahaanRepository perusahaanRepository;

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
