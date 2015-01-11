package com.unitedvision.tvkabel.domain.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.unitedvision.tvkabel.domain.Alamat;
import com.unitedvision.tvkabel.domain.Kecamatan;
import com.unitedvision.tvkabel.domain.Kelurahan;
import com.unitedvision.tvkabel.domain.Kontak;
import com.unitedvision.tvkabel.domain.Kota;
import com.unitedvision.tvkabel.domain.Perusahaan;
import com.unitedvision.tvkabel.domain.Perusahaan.Status;
import com.unitedvision.tvkabel.exception.EmptyCodeException;
import com.unitedvision.tvkabel.exception.EmptyIdException;

public class PerusahaanTest {

	@Test
	public void createDefaultWorks() {
		Perusahaan perusahaan = new Perusahaan();

		assertEquals(true, perusahaan.isNew());
	}

	@Test
	public void setAlamatWorks() {
		Perusahaan perusahaan = new Perusahaan();

		Kelurahan kelurahan = new Kelurahan();
		Alamat alamat = new Alamat(kelurahan, 1, "");
		perusahaan.setAlamat(alamat);
		
		assertEquals(alamat, perusahaan.getAlamat());
		assertEquals(kelurahan, perusahaan.getKelurahan());
	}

	@Test
	public void generateKode() throws EmptyIdException, EmptyCodeException {
		Kota kota = new Kota(1, "Kota");
		Kecamatan kecamatan = new Kecamatan(1, kota, "Kecamatan");
		Kelurahan kelurahan = new Kelurahan(1, kecamatan, "Kelurahan");
		Alamat alamat = new Alamat(kelurahan, 1, "");
		Kontak kontak = new Kontak("1", "2", "3");
		Perusahaan perusahaan = new Perusahaan(1, "1", "1", alamat, kontak, 1000L, Status.AKTIF);
		
		assertEquals(1, perusahaan.getId());
		assertEquals("1", perusahaan.getKode());
		assertEquals("1", perusahaan.getNama());
		
		perusahaan.generateKode(2);
		
		assertEquals("COM3", perusahaan.getKode());
	}
}
