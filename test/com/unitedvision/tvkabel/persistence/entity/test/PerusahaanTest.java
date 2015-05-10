package com.unitedvision.tvkabel.persistence.entity.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.unitedvision.tvkabel.entity.Alamat;
import com.unitedvision.tvkabel.entity.Kecamatan;
import com.unitedvision.tvkabel.entity.Kelurahan;
import com.unitedvision.tvkabel.entity.Kontak;
import com.unitedvision.tvkabel.entity.Kota;
import com.unitedvision.tvkabel.entity.Perusahaan;
import com.unitedvision.tvkabel.entity.Perusahaan.Status;
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
		//Alamat alamat = new Alamat(1, "", 0, 0);
        Alamat alamat = new Alamat();
		perusahaan.setAlamat(alamat);
		
		assertEquals(alamat, perusahaan.getAlamat());
	}

	@Test
	public void generateKode() throws EmptyIdException, EmptyCodeException {
		Kota kota = new Kota(1, "Kota");
		Kecamatan kecamatan = new Kecamatan(1, kota, "Kecamatan");
		Kelurahan kelurahan = new Kelurahan(1, kecamatan, "Kelurahan");
		//Alamat alamat = new Alamat(1, "", 0, 0);
        Alamat alamat = new Alamat();
        Kontak kontak = new Kontak("1", "2", "3");
		Perusahaan perusahaan = new Perusahaan(1, "1", "1", "PT. 1", kelurahan, alamat, kontak, Status.AKTIF);
		
		assertEquals(1, perusahaan.getId());
		assertEquals("1", perusahaan.getKode());
		assertEquals("1", perusahaan.getNama());
		
		perusahaan.generateKode(2);
		
		assertEquals("COM3", perusahaan.getKode());
	}
}
