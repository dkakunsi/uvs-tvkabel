package com.unitedvision.tvkabel.util;

import java.util.List;

import com.unitedvision.tvkabel.entity.Alat;
import com.unitedvision.tvkabel.entity.Domain;
import com.unitedvision.tvkabel.entity.History;
import com.unitedvision.tvkabel.entity.Kecamatan;
import com.unitedvision.tvkabel.entity.Kelurahan;
import com.unitedvision.tvkabel.entity.Kota;
import com.unitedvision.tvkabel.entity.Pegawai;
import com.unitedvision.tvkabel.entity.Pelanggan;
import com.unitedvision.tvkabel.entity.Pembayaran;
import com.unitedvision.tvkabel.entity.Perusahaan;

public class ListEntityRestMessage<T extends Domain> extends RestMessage {
	private List<T> list;
	
	protected ListEntityRestMessage(Exception ex) {
		super(ex);
	}

	protected ListEntityRestMessage(List<T> list) {
		super("Berhasil", Type.LIST);
		this.list = list;
	}
	
	public List<T> getList() {
		return list;
	}
	
	public static <T extends Domain> ListEntityRestMessage<T> listEntityError(Exception cause) {
		return new ListEntityRestMessage<T>(cause);
	}
	
	public static ListEntityRestMessage<Kota> createListKota(List<Kota> kota) {
		return new ListEntityRestMessage<Kota>(kota);
	}
	
	public static ListEntityRestMessage<Kecamatan> createListKecamatan(List<Kecamatan> kecamatan) {
		return new ListEntityRestMessage<Kecamatan>(kecamatan);
	}
	
	public static ListEntityRestMessage<Kelurahan> createListKelurahan(List<Kelurahan> kelurahan) {
		return new ListEntityRestMessage<Kelurahan>(kelurahan);
	}
	
	public static ListEntityRestMessage<Perusahaan> createListPerusahaan(List<Perusahaan> perusahaan) {
		return new ListEntityRestMessage<Perusahaan>(perusahaan);
	}
	
	public static ListEntityRestMessage<Pegawai> createListPegawai(List<Pegawai> pegawai) {
		return new ListEntityRestMessage<Pegawai>(pegawai);
	}
	
	public static ListEntityRestMessage<Pelanggan> createListPelanggan(List<Pelanggan> pelanggan) {
		return new ListEntityRestMessage<Pelanggan>(pelanggan);
	}
	
	public static ListEntityRestMessage<Pembayaran> createListPembayaran(List<Pembayaran> pembayaran) {
		return new ListEntityRestMessage<Pembayaran>(pembayaran);
	}

	public static ListEntityRestMessage<History> createListHistory(List<History> history) {
		return new ListEntityRestMessage<History>(history);
	}

	public static ListEntityRestMessage<Alat> createListAlat(List<Alat> alat) {
		return new ListEntityRestMessage<Alat>(alat);
	}
}
