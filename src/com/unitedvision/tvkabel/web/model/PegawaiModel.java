package com.unitedvision.tvkabel.web.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.unitedvision.tvkabel.core.domain.Kredensi;
import com.unitedvision.tvkabel.core.domain.Kredensi.Role;
import com.unitedvision.tvkabel.core.domain.Operator;
import com.unitedvision.tvkabel.core.domain.Pegawai;
import com.unitedvision.tvkabel.core.domain.Perusahaan;
import com.unitedvision.tvkabel.exception.EmptyCodeException;
import com.unitedvision.tvkabel.exception.EmptyIdException;
import com.unitedvision.tvkabel.exception.StatusChangeException;
import com.unitedvision.tvkabel.persistence.entity.PegawaiEntity;
import com.unitedvision.tvkabel.persistence.entity.PegawaiEntity.KredensiValue;

public class PegawaiModel extends CodableModel implements Pegawai {
	private Perusahaan perusahaan;
	
	private String nama;
	private Role role;
	private String username;
	private String password;
	private Status status;
	
	public PegawaiModel() {
		super();
	}
	
	public PegawaiModel(int id) {
		super();
		this.id = id;
	}

	public PegawaiModel(Pegawai pegawai) {
		super();
		this.id = pegawai.getId();
		this.kode = pegawai.getKode();
		this.nama = pegawai.getNama();
		this.role = pegawai.getRole();
		this.username = pegawai.getUsername();
		this.password = pegawai.getPassword();
		this.perusahaan = pegawai.getPerusahaan();
		this.status = pegawai.getStatus();
	}

	public PegawaiModel(int id, int perusahaanId, String kode, String nama, Role role,
			String username, String password, Status status) {
		super();
		this.id = id;
		this.kode = kode;
		this.nama = nama;
		this.role = role;
		this.username = username;
		this.password = password;
		this.status = status;
	}

	@Override
	@JsonIgnore
	public Perusahaan getPerusahaan() {
		return perusahaan;
	}
	
	public void setPerusahaan(Perusahaan perusahaan) {
		this.perusahaan = perusahaan;
	}

	@Override
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String getKode() {
		return kode;
	}

	@Override
	public void setKode(String kode) {
		this.kode = kode;
	}

	@Override
	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	@Override
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Override
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public Status getStatus() {
		if (status == null)
			status = Status.AKTIF;
		return status;
	}

	@Override
	public void setStatus(Status status) {
		this.status = status;
	}
	
	@Override
	@JsonIgnore
	public Kredensi getKredensi() {
		return new KredensiValue(isOperator(), username, password, role);
	}

	@Override
	@JsonIgnore
	public PegawaiEntity toEntity() {
		try {
			return new PegawaiEntity(id, kode, perusahaan.toEntity(), nama, getKredensi().toEntity(), getStatus());
		} catch (EmptyCodeException | EmptyIdException e) {
			return toEntityWithKode();
		}
	}
	
	@JsonIgnore
	protected PegawaiEntity toEntityWithKode() {
		try {
			return new PegawaiEntity(kode, perusahaan.toEntity(), nama, getKredensi().toEntity(), getStatus());
		} catch (EmptyCodeException e) {
			return new PegawaiEntity(perusahaan.toEntity(), nama, getKredensi().toEntity(), getStatus());
		}
	}

	@Override
	@JsonIgnore
	public PegawaiModel toModel() {
		return this;
	}

	@Override
	@JsonIgnore
	public Operator toOperator() {
		return toEntity();
	}

	public boolean isOperator() {
		return (role == Role.OPERATOR || role == Role.OWNER);
	}

	//TODO delete this
	@Deprecated
	@JsonIgnore
	public PegawaiEntity toCoreMinimum() throws EmptyIdException {
		return new PegawaiEntity(id);
	}
	
	public static List<PegawaiModel> createListModel(List<? extends Pegawai> ls) {
		List<PegawaiModel> list = new ArrayList<>();
		
		for (Pegawai pegawai : ls)
			list.add(pegawai.toModel());

		return list;
	}

	@Override
	@JsonIgnore
	public String generateKode(long count) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@JsonIgnore
	public Pegawai toDomain() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void remove() throws StatusChangeException {
		// TODO Auto-generated method stub
		
	}
}
