package com.unitedvision.tvkabel.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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
import com.unitedvision.tvkabel.exception.EmptyIdException;

/**
 * Mapping of kelurahan table in database.
 * 
 * @author Deddy Christoper Kakunsi
 *
 */
@Entity
@Table(name = "kelurahan")
public final class Kelurahan extends Region {
	
	/**
	 * {@link Kecamatan} instance.
	 */
	private Kecamatan kecamatan;

	/**
	 * List of {@link Perusahaan} instances.
	 */
	private List<Perusahaan> listPerusahaan;
	
	/**
	 * List of {@link Pelanggan} instances..
	 */
	private List<Pelanggan> listPelanggan;
	
	/**
	 * Create instance.
	 */
	public Kelurahan() {
		super();
	}

	/**
	 * Create instance.
	 * 
	 * @param id
	 * @param kecamatan
	 * @param nama
	 * @throws EmptyIdException {@code id} is not positive.
	 */
	public Kelurahan(int id, Kecamatan kecamatan, String nama) throws EmptyIdException {
		super(id, nama);
		setKecamatan(kecamatan);
	}

	@Override
	@Id @GeneratedValue
	public int getId() {
		return id;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_kecamatan", referencedColumnName = "id")
	public Kecamatan getKecamatan() {
		return kecamatan;
	}

	/**
	 * Set {@link Kecamatan} instance.
	 * 
	 * @param kecamatan
	 */
	public void setKecamatan(Kecamatan kecamatan) {
		this.kecamatan = kecamatan;
	}

	@Override
	@Column(name = "nama")
	public String getNama() {
		return nama;
	}

	/**
	 * Returns the list of {@link Perusahaan} instances.
	 * 
	 * @return listPerusahaan
	 */
	@JsonIgnore
	@OneToMany(targetEntity = Perusahaan.class, mappedBy = "alamat.kelurahan", fetch = FetchType.LAZY,
			cascade = CascadeType.REFRESH)
	public List<Perusahaan> getListPerusahaan() {
		return listPerusahaan;
	}

	/**
	 * Set the list of {@link Perusahaan} instances..
	 * 
	 * @param listPerusahaan 
	 */
	public void setListPerusahaan(List<Perusahaan> listPerusahaan) {
		this.listPerusahaan = listPerusahaan;
	}
	
	public Perusahaan addPerusahaan(Perusahaan perusahaan) {
		getListPerusahaan().add(perusahaan);
		perusahaan.getAlamat().setKelurahan(this);
		
		return perusahaan;
	}
	
	public Perusahaan removePerusahaan(Perusahaan perusahaan) {
		getListPerusahaan().remove(perusahaan);
		perusahaan.getAlamat().setKelurahan(null);
		
		return perusahaan;
	}

	/**
	 * Returns the list of {@link Pelanggan} instances.
	 * 
	 * @return listPelanggan 
	 */
	@JsonIgnore
	@OneToMany(targetEntity = Pelanggan.class, mappedBy = "alamat.kelurahan", fetch = FetchType.LAZY,
			cascade = CascadeType.REFRESH)
	public List<Pelanggan> getListPelanggan() {
		return listPelanggan;
	}

	/**
	 * Set the list of {@link Pelanggan} instances.
	 * 
	 * @param listPelanggan 
	 */
	public void setListPelanggan(List<Pelanggan> listPelanggan) {
		this.listPelanggan = listPelanggan;
	}
	
	public Pelanggan addPelanggan(Pelanggan pelanggan) {
		getListPelanggan().add(pelanggan);
		pelanggan.getAlamat().setKelurahan(this);
		
		return pelanggan;
	}
	
	public Pelanggan removePelanggan(Pelanggan pelanggan) {
		getListPelanggan().remove(pelanggan);
		pelanggan.getAlamat().setKelurahan(null);
		
		return pelanggan;
	}
	
	@JsonIgnore
	@Transient
	public int getIdKota() {
		return kecamatan.getIdKota();
	}
	
	@JsonIgnore
	@Transient
	public String getNamaKota() {
		return kecamatan.getNamaKota();
	}
	
	@JsonIgnore
	@Transient
	public int getIdKecamatan() {
		return kecamatan.getId();
	}
	
	@JsonIgnore
	@Transient
	public String getNamaKecamatan() {
		return kecamatan.getNama();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((kecamatan == null) ? 0 : kecamatan.hashCode());
		result = prime * result + ((nama == null) ? 0 : nama.hashCode());
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
		Kelurahan other = (Kelurahan) obj;
		if (kecamatan == null) {
			if (other.kecamatan != null)
				return false;
		} else if (!kecamatan.equals(other.kecamatan))
			return false;
		if (nama == null) {
			if (other.nama != null)
				return false;
		} else if (!nama.equals(other.nama))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Kelurahan [nama=" + nama + ", kecamatan=" + kecamatan + "]";
	}
}
