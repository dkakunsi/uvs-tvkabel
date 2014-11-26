package com.unitedvision.tvkabel.web.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.unitedvision.tvkabel.core.domain.Kelurahan;
import com.unitedvision.tvkabel.exception.EmptyIdException;
import com.unitedvision.tvkabel.persistence.entity.KecamatanEntity;
import com.unitedvision.tvkabel.persistence.entity.KelurahanEntity;

public final class KelurahanModel extends Model implements Kelurahan {
	private String nama;

	private int idKecamatan;
	private String namaKecamatan;
	private int idKota;
	private String namaKota;
	
	public KelurahanModel() {
		super();
	}

	public KelurahanModel(int idKelurahan, String namaKelurahan, String namaKecamatan, String namaKota) {
		super();
		this.id = idKelurahan;
		this.nama = namaKelurahan;
		this.namaKecamatan = namaKecamatan;
		this.namaKota = namaKota;
	}
	
	public KelurahanModel(KelurahanEntity kelurahanEntity) {
		this(kelurahanEntity.getId(), kelurahanEntity.getNama(), kelurahanEntity.getNamaKecamatan(), kelurahanEntity.getNamaKota());
	}

	@Override
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	@Override
	public int getIdKecamatan() {
		return idKecamatan;
	}

	public void setIdKecamatan(int idKecamatan) {
		this.idKecamatan = idKecamatan;
	}

	@Override
	public String getNamaKecamatan() {
		return namaKecamatan;
	}

	public void setNamaKecamatan(String namaKecamatan) {
		this.namaKecamatan = namaKecamatan;
	}

	@Override
	public int getIdKota() {
		return idKota;
	}

	public void setIdKota(int idKota) {
		this.idKota = idKota;
	}

	@Override
	public String getNamaKota() {
		return namaKota;
	}

	public void setNamaKota(String namaKota) {
		this.namaKota = namaKota;
	}

	@JsonIgnore
	public KecamatanModel getKecamatanModel() {
		return new KecamatanModel(0, namaKecamatan, namaKota);
	}
	
	@Override
	@JsonIgnore
	public KecamatanEntity getKecamatan() {
		return getKecamatanModel().toEntity();
	}
	
	@Override
	@JsonIgnore
	public KelurahanEntity toEntity() {
		try {
			return new KelurahanEntity(id, getKecamatan(), nama);
		} catch (EmptyIdException e) {
			return new KelurahanEntity(getKecamatan(), nama);
		}
	}

	@Override
	@JsonIgnore
	public KelurahanModel toModel() {
		return this;
	}
	
	public static List<KelurahanModel> createListModel(List<?> ls) {
		List<KelurahanModel> list = new ArrayList<>();
		
		for (Object o : ls)
			list.add(((Kelurahan)o).toModel());
		
		return list;
	}
}
