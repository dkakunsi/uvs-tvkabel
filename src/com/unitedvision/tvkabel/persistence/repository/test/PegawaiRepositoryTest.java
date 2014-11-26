package com.unitedvision.tvkabel.persistence.repository.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.unitedvision.tvkabel.core.domain.Pegawai.Status;
import com.unitedvision.tvkabel.exception.EntityNotExistException;
import com.unitedvision.tvkabel.persistence.SpringDataJpaConfig;
import com.unitedvision.tvkabel.persistence.entity.PegawaiEntity;
import com.unitedvision.tvkabel.persistence.entity.PerusahaanEntity;
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
		
		PegawaiEntity pegawaiEntity = pegawaiRepository.findByKredensi_UsernameAndStatus(username, status);

		assertNotNull(pegawaiEntity);
		assertEquals(14, pegawaiEntity.getId());
	}

	@Test (expected = EntityNotExistException.class)
	public void testFindByUsernameAndStatusAndKredensi_RoleButRoleWasDenied() throws EntityNotExistException {
		String username = "jefry";
		Status status = Status.AKTIF;
		
		pegawaiRepository.findByKredensi_UsernameAndStatus(username, status);
	}
	
	@Test
	public void testCountByPerusahaanAndStatus() {
		PerusahaanEntity perusahaanEntity = perusahaanRepository.findOne(17);
		Status status = Status.AKTIF;
		
		long hasil = pegawaiRepository.countByPerusahaanAndStatus(perusahaanEntity, status);
		
		assertNotEquals(0, hasil);
	}
	
	@Test
	public void testCountByPerusahaanAndStatusAndKodeContaining() {
		PerusahaanEntity perusahaanEntity = perusahaanRepository.findOne(17);
		Status status = Status.AKTIF;
		String kode = "PG";
		
		long hasil = pegawaiRepository.countByPerusahaanAndStatusAndKodeContaining(perusahaanEntity, status, kode);
		
		assertNotEquals(0, hasil);
	}

	@Test
	public void testCountByPerusahaanAndStatusAndNamaContaining() {
		PerusahaanEntity perusahaanEntity = perusahaanRepository.findOne(17);
		Status status = Status.AKTIF;
		String nama = "John";
		
		long hasil = pegawaiRepository.countByPerusahaanAndStatusAndNamaContaining(perusahaanEntity, status, nama);
		
		assertNotEquals(0, hasil);
	}
}
