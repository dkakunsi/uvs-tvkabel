package com.unitedvision.tvkabel.web.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.unitedvision.tvkabel.core.domain.Kecamatan;
import com.unitedvision.tvkabel.exception.EmptyIdException;
import com.unitedvision.tvkabel.persistence.entity.KecamatanEntity;
import com.unitedvision.tvkabel.persistence.entity.KotaEntity;

public final class KecamatanModel extends Model implements Kecamatan {
	private String nama;

	private int idKota;
	private String namaKota;

	public KecamatanModel() {
		super();
	}

	public KecamatanModel(KecamatanEntity kecamatanEntity) {
		this(kecamatanEntity.getId(), kecamatanEntity.getNama(), kecamatanEntity.getNamaKota());
	}

	public KecamatanModel(int id, String nama, String namaKota) {
		super();
		this.nama = nama;
		this.namaKota = namaKota;
		this.id = id;
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
	public KotaModel getKotaModel() {
		return new KotaModel(namaKota);
	}
	
	@Override
	@JsonIgnore
	public KotaEntity getKota() {
		return getKotaModel().toEntity();
	}
	
	@Override
	@JsonIgnore
	public KecamatanEntity toEntity() {
		try {
			return new KecamatanEntity(id, getKota(), nama);
		} catch (EmptyIdException e) {
			return new KecamatanEntity(getKota(), nama);
		}
	}

	@Override
	@JsonIgnore
	public KecamatanModel toModel() {
		return this;
	}
	
	public static List<KecamatanModel> createListModel(List<?> ls) {
		final List<KecamatanModel> list = new ArrayList<>();
		
		for (Object o : ls)
			list.add(((Kecamatan)o).toModel());

		return list;
	}
}
