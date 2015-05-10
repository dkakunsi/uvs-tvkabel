package com.unitedvision.tvkabel.persistence.entity.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.unitedvision.tvkabel.entity.Alamat;
import com.unitedvision.tvkabel.entity.Kelurahan;
import com.unitedvision.tvkabel.entity.Pegawai;
import com.unitedvision.tvkabel.entity.Perusahaan;
import com.unitedvision.tvkabel.entity.Pegawai.Status;
import com.unitedvision.tvkabel.exception.EmptyCodeException;
import com.unitedvision.tvkabel.exception.EmptyIdException;
import com.unitedvision.tvkabel.exception.StatusChangeException;

public class PegawaiTest {

	@Test
	public void codeUpperCased() throws EmptyCodeException {
		Pegawai pegawai = new Pegawai();
		pegawai.setKode("xxx");
		
		assertEquals("YYY", pegawai.getKode());
	}
	
	@Test
	public void createWithIdWorks() throws EmptyIdException {
		Pegawai pegawaiDomain = new Pegawai();
		pegawaiDomain.setId(1);;

		assertEquals(1, pegawaiDomain.getId());
		assertNull(pegawaiDomain.getStatus());
	}

	@Test
	public void createNewWithKodeWorks() throws EmptyCodeException, EmptyIdException {
		Pegawai pegawaiDomain = new Pegawai(0, "1", null, "Pegawai", null, Status.AKTIF);

		assertEquals(Status.AKTIF, pegawaiDomain.getStatus());
		assertEquals(0, pegawaiDomain.getId());
		assertEquals("1", pegawaiDomain.getKode());
	}

	@Test
	public void createNewWorks() throws EmptyCodeException, EmptyIdException {
		Pegawai pegawaiDomain = new Pegawai(0, "PG01", null, "Pegawai", null, Status.AKTIF);

		assertEquals(Status.AKTIF, pegawaiDomain.getStatus());
		assertEquals(0, pegawaiDomain.getId());
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
	public void removeNewEntity() throws StatusChangeException, EmptyCodeException, EmptyIdException {
		Pegawai pegawaiDomain = new Pegawai(0, "-", null, "Pegawai", null, Status.AKTIF);
		
		pegawaiDomain.remove();
	}

	@Test
	public void generateKodeWorks() throws EmptyCodeException, EmptyIdException {
        Alamat alamat = new Alamat();
		Perusahaan perusahaanDomain = new Perusahaan(1, "COM1", "Perusahaan", "PT. Perusahaan", new Kelurahan(), alamat, null, Perusahaan.Status.AKTIF);
		Pegawai pegawaiDomain = new Pegawai(0, "-", perusahaanDomain, "Pegawai", null, Status.AKTIF);
		pegawaiDomain.generateKode(1L);
		
		assertEquals(Status.AKTIF, pegawaiDomain.getStatus());
		assertEquals(0, pegawaiDomain.getId());
		assertEquals("COM1PG2", pegawaiDomain.getKode());
	}
}
