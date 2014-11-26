package com.unitedvision.tvkabel.persistence.entity.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.unitedvision.tvkabel.core.domain.Alamat;
import com.unitedvision.tvkabel.core.domain.Perusahaan.Status;
import com.unitedvision.tvkabel.exception.EmptyCodeException;
import com.unitedvision.tvkabel.exception.EmptyIdException;
import com.unitedvision.tvkabel.exception.UncompatibleTypeException;
import com.unitedvision.tvkabel.persistence.entity.AlamatValue;
import com.unitedvision.tvkabel.persistence.entity.KecamatanEntity;
import com.unitedvision.tvkabel.persistence.entity.KelurahanEntity;
import com.unitedvision.tvkabel.persistence.entity.KontakValue;
import com.unitedvision.tvkabel.persistence.entity.KotaEntity;
import com.unitedvision.tvkabel.persistence.entity.PerusahaanEntity;
import com.unitedvision.tvkabel.web.model.PerusahaanModel;

public class PerusahaanEntityTest {

	@Test
	public void createDefaultWorks() {
		PerusahaanEntity perusahaanEntity = new PerusahaanEntity();

		assertEquals(true, perusahaanEntity.isNew());
	}

	@Test
	public void setAlamatWorks() throws UncompatibleTypeException {
		PerusahaanEntity perusahaanEntity = new PerusahaanEntity();

		KelurahanEntity kelurahanEntity = new KelurahanEntity();
		Alamat alamat = new AlamatValue(kelurahanEntity, 1, "");
		perusahaanEntity.setAlamat(alamat);
		
		assertEquals(alamat, perusahaanEntity.getAlamat());
		assertEquals(kelurahanEntity, perusahaanEntity.getKelurahan());
	}

	@Test (expected = UncompatibleTypeException.class)
	public void setAlamatWithUncompatibleType() throws UncompatibleTypeException {
		PerusahaanEntity perusahaanEntity = new PerusahaanEntity();
		
		Alamat alamat = new AlamatValue(new KelurahanEntity(), 1, "");
		perusahaanEntity.setAlamat(alamat.toModel());
	}

	@Test
	public void setAlamatValueWorks() throws UncompatibleTypeException, EmptyIdException {
		PerusahaanEntity perusahaanEntity = new PerusahaanEntity();

		KotaEntity kotaEntity = new KotaEntity(1, "Kota");
		KecamatanEntity kecamatanEntity = new KecamatanEntity(1, kotaEntity, "Kecamatan");
		KelurahanEntity kelurahanEntity = new KelurahanEntity(1, kecamatanEntity, "Kelurahan");
		AlamatValue alamat = new AlamatValue(kelurahanEntity, 1, "");
		perusahaanEntity.setAlamat(alamat);
		
		assertEquals(alamat, perusahaanEntity.getAlamat());
		assertEquals(kelurahanEntity, perusahaanEntity.getKelurahan());
	}

	@Test
	public void toModelWorks() throws EmptyIdException, EmptyCodeException {
		KotaEntity kotaEntity = new KotaEntity(1, "Kota");
		KecamatanEntity kecamatanEntity = new KecamatanEntity(1, kotaEntity, "Kecamatan");
		KelurahanEntity kelurahanEntity = new KelurahanEntity(1, kecamatanEntity, "Kelurahan");
		AlamatValue alamat = new AlamatValue(kelurahanEntity, 1, "");
		KontakValue kontak = new KontakValue("1", "2", "3");
		PerusahaanEntity perusahaanEntity = new PerusahaanEntity(1, "1", "1", alamat, kontak, 1000L, Status.AKTIF);
		
		PerusahaanModel perusahaanModel =  perusahaanEntity.toModel();
		
		assertEquals(1, perusahaanModel.getId());
		assertEquals("1", perusahaanModel.getKode());
		assertEquals("1", perusahaanModel.getNama());
	}
	
	@Test
	public void generateKode() throws EmptyIdException, EmptyCodeException {
		KotaEntity kotaEntity = new KotaEntity(1, "Kota");
		KecamatanEntity kecamatanEntity = new KecamatanEntity(1, kotaEntity, "Kecamatan");
		KelurahanEntity kelurahanEntity = new KelurahanEntity(1, kecamatanEntity, "Kelurahan");
		AlamatValue alamat = new AlamatValue(kelurahanEntity, 1, "");
		KontakValue kontak = new KontakValue("1", "2", "3");
		PerusahaanEntity perusahaanEntity = new PerusahaanEntity(1, "1", "1", alamat, kontak, 1000L, Status.AKTIF);
		
		assertEquals(1, perusahaanEntity.getId());
		assertEquals("1", perusahaanEntity.getKode());
		assertEquals("1", perusahaanEntity.getNama());
		
		perusahaanEntity.generateKode(2);
		
		assertEquals("COM3", perusahaanEntity.getKode());
	}
}
