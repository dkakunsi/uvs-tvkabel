package com.unitedvision.tvkabel.web.model.test;

import static org.junit.Assert.*;

import java.time.Month;
import java.util.Date;

import org.junit.Test;

import com.unitedvision.tvkabel.exception.EmptyCodeException;
import com.unitedvision.tvkabel.exception.EmptyIdException;
import com.unitedvision.tvkabel.persistence.entity.PegawaiEntity;
import com.unitedvision.tvkabel.persistence.entity.PelangganEntity;
import com.unitedvision.tvkabel.persistence.entity.PelangganEntity.Detail;
import com.unitedvision.tvkabel.persistence.entity.PembayaranEntity;
import com.unitedvision.tvkabel.persistence.entity.PembayaranEntity.TagihanValue;
import com.unitedvision.tvkabel.util.DateUtil;
import com.unitedvision.tvkabel.web.model.PembayaranModel;

public class PembayaranModelTest {

	@Test
	public void testToEntity() {
		int id = 1;
		String kode = "PB1";
		int idPelanggan = 1;
		int idPegawai = 1;
		int tahun = 2014;
		String bulan = "JANUARY";
		String tanggalBayar = "1/30/2014";
		long jumlahPembayaran = 40000;
		String namaPelanggan = "Pelanggan";
		String namaPegawai = "Pegawai";
		String profesiPelanggan = "Profesi";
		
		PembayaranModel pembayaranModel = new PembayaranModel(id, kode, idPelanggan, idPegawai, tahun, bulan, tanggalBayar, jumlahPembayaran, namaPelanggan, namaPegawai, profesiPelanggan);
		PembayaranEntity pembayaranEntity = pembayaranModel.toEntity();

		assertEquals(id, pembayaranEntity.getId());
		assertEquals(kode, pembayaranEntity.getKode());
		assertEquals(idPelanggan, pembayaranEntity.getIdPelanggan());
		assertEquals(idPegawai, pembayaranEntity.getIdPegawai());
		assertEquals(tahun, pembayaranEntity.getTahun());
		assertEquals(bulan, pembayaranEntity.getBulan().name());
		assertEquals(tanggalBayar, DateUtil.toString(pembayaranEntity.getTanggalBayar()));
		assertEquals(jumlahPembayaran, pembayaranEntity.getJumlahBayar());
		//assertEquals(namaPelanggan, pembayaranEntity.getNamaPelanggan());
		//assertEquals(namaPegawai, pembayaranEntity.getNamaPegawai());
		//assertEquals(profesiPelanggan, pembayaranEntity.getProfesiPelanggan());
	}

	@Test
	public void testToEntityWithKode() {
		fail("Not yet implemented");
	}

	@Test
	public void testToCoreMinimum() throws EmptyIdException {
		int id = 1;
		
		PembayaranModel pembayaranModel = new PembayaranModel(id);
		@SuppressWarnings("deprecation")
		PembayaranEntity pembayaranEntity = pembayaranModel.toCoreMinimum();
		
		assertEquals(id, pembayaranEntity.getId());
	}

	@Test
	public void testNewUsingPembayaranEntity() throws EmptyIdException, EmptyCodeException {
		int id = 1;
		String kode = "PB1";
		Date tanggalbayar = DateUtil.getNow();
		
		int idPelanggan = 1;
		String namaPelanggan = "Pelanggan";
		String profesiPelanggan = "Pemain";
		Detail detail = new Detail(DateUtil.getDate("1/1/2014"), 1, 40000, 0);
		
		PelangganEntity pelangganEntity = new PelangganEntity(idPelanggan, null, "PL1", namaPelanggan, profesiPelanggan, null, null, detail, null);
		
		int idPegawai = 1;
		String namaPegawai = "Pegawai";
		
		PegawaiEntity pegawaiEntity = new PegawaiEntity(idPegawai, "PG1", null, namaPegawai, null, null);
		
		long jumlahBayar = 40000;
		TagihanValue tagihanValue = new TagihanValue(2014, Month.JANUARY);
		
		PembayaranEntity pembayaranEntity = new PembayaranEntity(id, kode, tanggalbayar, pelangganEntity, pegawaiEntity, jumlahBayar, tagihanValue);
		assertEquals(id, pembayaranEntity.getId());

		PembayaranModel pembayaranModel = new PembayaranModel(pembayaranEntity);
		assertEquals(id, pembayaranModel.getId());
	}

}
