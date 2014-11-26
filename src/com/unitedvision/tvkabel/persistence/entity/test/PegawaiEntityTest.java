package com.unitedvision.tvkabel.persistence.entity.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.unitedvision.tvkabel.core.domain.Pegawai.Status;
import com.unitedvision.tvkabel.core.domain.Perusahaan;
import com.unitedvision.tvkabel.exception.EmptyCodeException;
import com.unitedvision.tvkabel.exception.EmptyIdException;
import com.unitedvision.tvkabel.exception.StatusChangeException;
import com.unitedvision.tvkabel.persistence.entity.AlamatValue;
import com.unitedvision.tvkabel.persistence.entity.KelurahanEntity;
import com.unitedvision.tvkabel.persistence.entity.PegawaiEntity;
import com.unitedvision.tvkabel.persistence.entity.PerusahaanEntity;

public class PegawaiEntityTest {

	@Test
	public void createWithIdWorks() throws EmptyIdException {
		@SuppressWarnings("deprecation")
		PegawaiEntity pegawaiEntity = new PegawaiEntity(1);

		assertEquals(1, pegawaiEntity.getId());
		assertNull(pegawaiEntity.getStatus());
	}

	@Test
	public void createNewWithKodeWorks() throws EmptyCodeException {
		PegawaiEntity pegawaiEntity = new PegawaiEntity("1", null, "Pegawai", null, Status.AKTIF);

		assertEquals(Status.AKTIF, pegawaiEntity.getStatus());
		assertEquals(0, pegawaiEntity.getId());
		assertEquals("1", pegawaiEntity.getKode());
	}

	@Test
	public void createNewWorks() {
		PegawaiEntity pegawaiEntity = new PegawaiEntity(null, "Pegawai", null, Status.AKTIF);

		assertEquals(Status.AKTIF, pegawaiEntity.getStatus());
		assertEquals(0, pegawaiEntity.getId());
		assertNull(pegawaiEntity.getKode());
	}

	@Test
	public void createWorks() throws EmptyCodeException, EmptyIdException {
		PegawaiEntity pegawaiEntity = new PegawaiEntity(1, "1", null, "Pegawai", null, Status.AKTIF);

		assertEquals(Status.AKTIF, pegawaiEntity.getStatus());
		assertEquals(1, pegawaiEntity.getId());
		assertEquals("1", pegawaiEntity.getKode());
	}

	@Test
	public void removeWorks() throws EmptyCodeException, EmptyIdException, StatusChangeException {
		PegawaiEntity pegawaiEntity = new PegawaiEntity(1, "1", null, "Pegawai", null, Status.AKTIF);
		
		pegawaiEntity.remove();
		
		assertEquals(Status.REMOVED, pegawaiEntity.getStatus());
		assertEquals(1, pegawaiEntity.getId());
		assertEquals("REM1", pegawaiEntity.getKode());
	}

	@Test(expected = StatusChangeException.class)
	public void removeNewEntity() throws StatusChangeException {
		PegawaiEntity pegawaiEntity = new PegawaiEntity(null, "Pegawai", null, Status.AKTIF);
		
		pegawaiEntity.remove();
	}

	@Test
	public void generateKodeWorks() throws EmptyCodeException, EmptyIdException {
		PerusahaanEntity perusahaanEntity = new PerusahaanEntity(1, "COM1", "Perusahaan", new AlamatValue(new KelurahanEntity(), 1, ""), null, 1000L, Perusahaan.Status.AKTIF);
		PegawaiEntity pegawaiEntity = new PegawaiEntity(perusahaanEntity, "Pegawai", null, Status.AKTIF);
		
		assertNull(pegawaiEntity.getKode());

		pegawaiEntity.generateKode(1L);
		
		assertEquals(Status.AKTIF, pegawaiEntity.getStatus());
		assertEquals(0, pegawaiEntity.getId());
		assertEquals("COM1PG2", pegawaiEntity.getKode());
	}
}
