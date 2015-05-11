package com.unitedvision.tvkabel.util;

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

public class EntityRestMessage<T extends Domain> extends RestMessage {
	private T model;
	
	protected EntityRestMessage(Exception ex) {
		super(ex);
	}
	
	protected EntityRestMessage(T model) {
		super("Berhasil", Type.ENTITY, model);
		this.model = model;
	}
	
	public T getModel() {
		return model;
	}
	
	public static <T extends Domain> EntityRestMessage<T> entityError(Exception cause) {
		return new EntityRestMessage<T>(cause);
	}
	
	public static EntityRestMessage<Kota> create(Kota kota) {
		return new EntityRestMessage<Kota>(kota);
	}
	
	public static EntityRestMessage<Kecamatan> create(Kecamatan kecamatan) {
		return new EntityRestMessage<Kecamatan>(kecamatan);
	}
	
	public static EntityRestMessage<Kelurahan> create(Kelurahan kelurahan) {
		return new EntityRestMessage<Kelurahan>(kelurahan);
	}
	
	public static EntityRestMessage<Perusahaan> create(Perusahaan perusahaan) {
		return new EntityRestMessage<Perusahaan>(perusahaan);
	}
	
	public static EntityRestMessage<Pegawai> create(Pegawai pegawai) {
		return new EntityRestMessage<Pegawai>(pegawai);
	}
	
	public static EntityRestMessage<Pelanggan> create(Pelanggan pelanggan) {
		return new EntityRestMessage<Pelanggan>(pelanggan);
	}
	
	public static EntityRestMessage<Pembayaran> create(Pembayaran pembayaran) {
		return new EntityRestMessage<Pembayaran>(pembayaran);
	}
	
	public static EntityRestMessage<Alat> create(Alat alat) {
		return new EntityRestMessage<Alat>(alat);
	}
	
	public static EntityRestMessage<History> create(History history) {
		return new EntityRestMessage<History>(history);
	}
}
