package com.unitedvision.tvkabel.domain.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.unitedvision.tvkabel.domain.entity.Alamat;
import com.unitedvision.tvkabel.domain.entity.Kelurahan;
import com.unitedvision.tvkabel.domain.entity.Pegawai;
import com.unitedvision.tvkabel.domain.entity.Perusahaan;
import com.unitedvision.tvkabel.domain.entity.Pegawai.Status;
import com.unitedvision.tvkabel.exception.EmptyCodeException;
import com.unitedvision.tvkabel.exception.EmptyIdException;
import com.unitedvision.tvkabel.exception.StatusChangeException;

public class PegawaiTest {

	@Test
	public void createWithIdWorks() throws EmptyIdException {
		@SuppressWarnings("deprecation")
		Pegawai pegawaiDomain = new Pegawai(1);

		assertEquals(1, pegawaiDomain.getId());
		assertNull(pegawaiDomain.getStatus());
	}

	@Test
	public void createNewWithKodeWorks() throws EmptyCodeException {
		Pegawai pegawaiDomain = new Pegawai("1", null, "Pegawai", null, Status.AKTIF);

		assertEquals(Status.AKTIF, pegawaiDomain.getStatus());
		assertEquals(0, pegawaiDomain.getId());
		assertEquals("1", pegawaiDomain.getKode());
	}

	@Test
	public void createNewWorks() {
		Pegawai pegawaiDomain = new Pegawai(null, "Pegawai", null, Status.AKTIF);

		assertEquals(Status.AKTIF, pegawaiDomain.getStatus());
		assertEquals(0, pegawaiDomain.getId());
		assertNull(pegawaiDomain.getKode());
	}

	@Test
	public void createWorks() throws EmptyCodeException, EmptyIdException {
		Pegawai pegawaiDomain = new Pegawai(1, "1", null, "Pegawai", null, Status.AKTIF);

		assertEquals(Status.AKTIF, pegawaiDomain.getStatus());
		assertEquals(1, pegawaiDomain.getId());
		assertEquals("1", pegawaiDomain.getKode());
	}

	@Test
	public void removeWorks() throws EmptyCodeException, EmptyIdException, StatusChangeException {
		Pegawai pegawaiDomain = new Pegawai(1, "1", null, "Pegawai", null, Status.AKTIF);
		
		pegawaiDomain.remove();
		
		assertEquals(Status.REMOVED, pegawaiDomain.getStatus());
		assertEquals(1, pegawaiDomain.getId());
		assertEquals("REM1", pegawaiDomain.getKode());
	}

	@Test(expected = StatusChangeException.class)
	public void removeNewEntity() throws StatusChangeException {
		Pegawai pegawaiDomain = new Pegawai(null, "Pegawai", null, Status.AKTIF);
		
		pegawaiDomain.remove();
	}

	@Test
	public void generateKodeWorks() throws EmptyCodeException, EmptyIdException {
		Perusahaan perusahaanDomain = new Perusahaan(1, "COM1", "Perusahaan", new Alamat(new Kelurahan(), 1, ""), null, 1000L, Perusahaan.Status.AKTIF);
		Pegawai pegawaiDomain = new Pegawai(perusahaanDomain, "Pegawai", null, Status.AKTIF);
		
		assertNull(pegawaiDomain.getKode());

		pegawaiDomain.generateKode(1L);
		
		assertEquals(Status.AKTIF, pegawaiDomain.getStatus());
		assertEquals(0, pegawaiDomain.getId());
		assertEquals("COM1PG2", pegawaiDomain.getKode());
	}
}
