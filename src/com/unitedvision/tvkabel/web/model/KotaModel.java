package com.unitedvision.tvkabel.web.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.unitedvision.tvkabel.core.domain.Kota;
import com.unitedvision.tvkabel.exception.EmptyIdException;
import com.unitedvision.tvkabel.persistence.entity.KotaEntity;

public class KotaModel extends Model implements Kota {
	private String nama;
	
	public KotaModel() {
		super();
	}
	
	public KotaModel(String nama) {
		super();
		this.nama = nama;
	}

	public KotaModel(int id, String nama) {
		this(nama);
		this.id = id;
	}

	public KotaModel(KotaEntity kotaEntity) {
		super();
		this.id = kotaEntity.getId();
		this.nama = kotaEntity.getNama();
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
	@JsonIgnore
	public KotaEntity toEntity() {
		try {
			return new KotaEntity(id, nama);
		} catch (EmptyIdException e) {
			return new KotaEntity(nama);
		}
	}

	@Override
	@JsonIgnore
	public KotaModel toModel() {
		return this;
	}
	
	public static List<KotaModel> createListModel(List<?> ls) {
		final List<KotaModel> list = new ArrayList<>();
		
		for (Object o : ls)
			list.add(((Kota)o).toModel());

		return list;
	}
}
