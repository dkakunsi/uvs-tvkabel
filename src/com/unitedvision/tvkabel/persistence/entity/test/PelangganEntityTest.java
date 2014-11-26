package com.unitedvision.tvkabel.persistence.entity.test;

import static org.junit.Assert.*;

import java.time.Month;

import org.junit.Test;

import com.unitedvision.tvkabel.core.domain.Pelanggan.Status;
import com.unitedvision.tvkabel.exception.EmptyCodeException;
import com.unitedvision.tvkabel.exception.EmptyIdException;
import com.unitedvision.tvkabel.exception.StatusChangeException;
import com.unitedvision.tvkabel.persistence.entity.AlamatValue;
import com.unitedvision.tvkabel.persistence.entity.KecamatanEntity;
import com.unitedvision.tvkabel.persistence.entity.KelurahanEntity;
import com.unitedvision.tvkabel.persistence.entity.KontakValue;
import com.unitedvision.tvkabel.persistence.entity.KotaEntity;
import com.unitedvision.tvkabel.persistence.entity.PelangganEntity;
import com.unitedvision.tvkabel.persistence.entity.PelangganEntity.Detail;
import com.unitedvision.tvkabel.persistence.entity.PembayaranEntity;
import com.unitedvision.tvkabel.persistence.entity.PembayaranEntity.TagihanValue;
import com.unitedvision.tvkabel.util.DateUtil;

public class PelangganEntityTest {

	@Test
	public void createWithId() throws EmptyIdException {
		@SuppressWarnings("deprecation")
		PelangganEntity pelangganEntity = new PelangganEntity(1);
		
		assertEquals(1, pelangganEntity.getId());
	}

	@Test
	public void removeWorks() throws EmptyIdException, EmptyCodeException, StatusChangeException {
		KotaEntity kotaEntity = new KotaEntity(1, "Kota");
		KecamatanEntity kecamatanEntity = new KecamatanEntity(1, kotaEntity, "Kecamatan");
		KelurahanEntity kelurahanEntity = new KelurahanEntity(1, kecamatanEntity, "Kelurahan");
		AlamatValue alamatValue = new AlamatValue(kelurahanEntity, 1, "");
		KontakValue kontakValue = new KontakValue("1", "2", "3");
		Detail detail = new Detail(DateUtil.getDate("08/20/2012"), 1, 40000L, 0);
		PelangganEntity pelangganEntity = new PelangganEntity(1, null, "1", "Pelanggan", "Drummer", alamatValue, kontakValue, detail, Status.AKTIF);
		
		assertEquals(Status.AKTIF, pelangganEntity.getStatus());
		assertEquals(1, pelangganEntity.getId());
		assertEquals("1", pelangganEntity.getKode());
		
		pelangganEntity.remove();

		assertEquals(Status.REMOVED, pelangganEntity.getStatus());
		assertEquals(1, pelangganEntity.getId());
		assertEquals("REM1", pelangganEntity.getKode());
	}

	@Test(expected = StatusChangeException.class)
	public void removeNewPelanggan() throws StatusChangeException {
		PelangganEntity pelangganEntity = new PelangganEntity();
		
		pelangganEntity.remove();
	}
	
	@Test
	public void countTunggakanDefault() throws EmptyIdException, EmptyCodeException {
		KotaEntity kotaEntity = new KotaEntity(1, "Kota");
		KecamatanEntity kecamatanEntity = new KecamatanEntity(1, kotaEntity, "Kecamatan");
		KelurahanEntity kelurahanEntity = new KelurahanEntity(1, kecamatanEntity, "Kelurahan");
		AlamatValue alamatValue = new AlamatValue(kelurahanEntity, 1, "");
		KontakValue kontakValue = new KontakValue("1", "2", "3");
		Detail detail = new Detail(DateUtil.getDate("01/01/2014"), 1, 40000L, 0);
		PelangganEntity pelangganEntity = new PelangganEntity(1, null, "1", "Pelanggan", "Drummer", alamatValue, kontakValue, detail, Status.AKTIF);
		
		assertEquals(Status.AKTIF, pelangganEntity.getStatus());
		assertEquals(1, pelangganEntity.getId());
		assertEquals("1", pelangganEntity.getKode());
		
		pelangganEntity.countTunggakan();
		
		assertEquals(10, pelangganEntity.getTunggakan());
	}
	
	@Test
	public void countTunggakanWithPembayaran() throws EmptyIdException, EmptyCodeException {
		KotaEntity kotaEntity = new KotaEntity(1, "Kota");
		KecamatanEntity kecamatanEntity = new KecamatanEntity(1, kotaEntity, "Kecamatan");
		KelurahanEntity kelurahanEntity = new KelurahanEntity(1, kecamatanEntity, "Kelurahan");
		AlamatValue alamatValue = new AlamatValue(kelurahanEntity, 1, "");
		KontakValue kontakValue = new KontakValue("1", "2", "3");
		Detail detail = new Detail(DateUtil.getDate("01/01/2014"), 1, 40000L, 0);
		PelangganEntity pelangganEntity = new PelangganEntity(1, null, "1", "Pelanggan", "Drummer", alamatValue, kontakValue, detail, Status.AKTIF);
		
		assertEquals(Status.AKTIF, pelangganEntity.getStatus());
		assertEquals(1, pelangganEntity.getId());
		assertEquals("1", pelangganEntity.getKode());
		
		PembayaranEntity pembayaranTerakhir = new PembayaranEntity();
		pembayaranTerakhir.setTagihan(new TagihanValue(2014, Month.AUGUST));
		
		pelangganEntity.countTunggakan(pembayaranTerakhir);
		
		assertEquals(2, pelangganEntity.getTunggakan());
	}
	
	@Test
	public void countTunggakanWithTagihan() throws EmptyIdException, EmptyCodeException {
		KotaEntity kotaEntity = new KotaEntity(1, "Kota");
		KecamatanEntity kecamatanEntity = new KecamatanEntity(1, kotaEntity, "Kecamatan");
		KelurahanEntity kelurahanEntity = new KelurahanEntity(1, kecamatanEntity, "Kelurahan");
		AlamatValue alamatValue = new AlamatValue(kelurahanEntity, 1, "");
		KontakValue kontakValue = new KontakValue("1", "2", "3");
		Detail detail = new Detail(DateUtil.getDate("01/01/2014"), 1, 40000L, 0);
		PelangganEntity pelangganEntity = new PelangganEntity(1, null, "1", "Pelanggan", "Drummer", alamatValue, kontakValue, detail, Status.AKTIF);
		
		assertEquals(Status.AKTIF, pelangganEntity.getStatus());
		assertEquals(1, pelangganEntity.getId());
		assertEquals("1", pelangganEntity.getKode());
		
		TagihanValue tagihanValue = new TagihanValue(2014, Month.AUGUST);
		
		pelangganEntity.countTunggakan(tagihanValue);
		
		assertEquals(2, pelangganEntity.getTunggakan());
	}
}
