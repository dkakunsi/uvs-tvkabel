package com.unitedvision.tvkabel.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "alat")
public class Alat extends CodableDomain {
	private Perusahaan perusahaan;
	private String nama;
	private Tipe tipe;
	private String deskripsi;
	private Alat source;
	private Alamat alamat;
	private Status status;

	private List<Alat> listAlat;
	private List<Pelanggan> listPelanggan;
	
	public enum Tipe {
		BOOSTER
	}
	
	public enum Status {
		AKTIF,
		REMOVE
	}
	
	@Override
	@Id @GeneratedValue
	public int getId() {
		return super.getId();
	}
	
	@Override
	@Column(name = "kode")
	public String getKode() {
		return super.getKode();
	}

	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_perusahaan", referencedColumnName = "id")
	public Perusahaan getPerusahaan() {
		return perusahaan;
	}

	public void setPerusahaan(Perusahaan perusahaan) {
		this.perusahaan = perusahaan;
	}

	@Column(name = "nama")
	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	@Column(name = "tipe")
	public Tipe getTipe() {
		return tipe;
	}

	public void setTipe(Tipe tipe) {
		this.tipe = tipe;
	}

	@Column(name = "deskripsi")
	public String getDeskripsi() {
		return deskripsi;
	}

	public void setDeskripsi(String deskripsi) {
		this.deskripsi = deskripsi;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_source", referencedColumnName = "id")
	public Alat getSource() {
		return source;
	}

	public void setSource(Alat source) {
		this.source = source;
	}

	@JsonIgnore
	@Embedded
	public Alamat getAlamat() {
		return alamat;
	}

	public void setAlamat(Alamat alamat) {
		this.alamat = alamat;
	}

	@Column(name = "status")
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@JsonIgnore
	@OneToMany(targetEntity = Alat.class, mappedBy = "source", fetch = FetchType.LAZY,
		cascade = CascadeType.REFRESH)
	public List<Alat> getListAlat() {
		return listAlat;
	}

	public void setListAlat(List<Alat> listAlat) {
		this.listAlat = listAlat;
	}
	
	public Alat addAlat(Alat alat) {
		getListAlat().add(alat);
		alat.setSource(this);
		
		return alat;
	}
	
	public Alat removeAlat(Alat alat) {
		getListAlat().remove(alat);
		alat.setSource(null);
		
		return alat;
	}

	@JsonIgnore
	@OneToMany(targetEntity = Pelanggan.class, mappedBy = "source", fetch = FetchType.LAZY,
		cascade = CascadeType.REFRESH)
	public List<Pelanggan> getListPelanggan() {
		return listPelanggan;
	}

	public void setListPelanggan(List<Pelanggan> listPelanggan) {
		this.listPelanggan = listPelanggan;
	}
	
	public Pelanggan addPelanggan(Pelanggan pelanggan) {
		getListPelanggan().add(pelanggan);
		pelanggan.setSource(this);
		
		return pelanggan;
	}
	
	public Pelanggan removePelanggan(Pelanggan pelanggan) {
		getListPelanggan().remove(pelanggan);
		pelanggan.setSource(null);
		
		return pelanggan;
	}
	
	@Transient
	public Kelurahan getKelurahan() {
		return alamat.getKelurahan();
	}

	@Transient
	public int getLingkungan() {
		return alamat.getLingkungan();
	}

	@JsonIgnore
	@Transient
	public Location getLokasi() {
		return alamat.getLokasi();
	}

	@Transient
	public float getLatitude() {
		return getLokasi().getLatitude();
	}
	
	@Transient
	public float getLongitude() {
		return getLokasi().getLongitude();
	}
	
	@Override
	public String toString() {
		return "Alat [perusahaan=" + perusahaan + ", nama=" + nama + ", tipe="
				+ tipe + ", deskripsi=" + deskripsi + ", source=" + source + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((deskripsi == null) ? 0 : deskripsi.hashCode());
		result = prime * result
				+ ((listAlat == null) ? 0 : listAlat.hashCode());
		result = prime * result + ((nama == null) ? 0 : nama.hashCode());
		result = prime * result
				+ ((perusahaan == null) ? 0 : perusahaan.hashCode());
		result = prime * result + ((source == null) ? 0 : source.hashCode());
		result = prime * result + ((tipe == null) ? 0 : tipe.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Alat other = (Alat) obj;
		if (deskripsi == null) {
			if (other.deskripsi != null)
				return false;
		} else if (!deskripsi.equals(other.deskripsi))
			return false;
		if (listAlat == null) {
			if (other.listAlat != null)
				return false;
		} else if (!listAlat.equals(other.listAlat))
			return false;
		if (nama == null) {
			if (other.nama != null)
				return false;
		} else if (!nama.equals(other.nama))
			return false;
		if (perusahaan == null) {
			if (other.perusahaan != null)
				return false;
		} else if (!perusahaan.equals(other.perusahaan))
			return false;
		if (source == null) {
			if (other.source != null)
				return false;
		} else if (!source.equals(other.source))
			return false;
		if (tipe != other.tipe)
			return false;
		return true;
	}
}
