
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
 * Kecamatan domain.
 * 
 * @author Deddy Christoper Kakunsi
 *
 */
@Entity
@Table(name = "kecamatan")
public final class Kecamatan extends Region {
	
	/**
	 * {@link Kota} instance.
	 */
	private Kota kota;

	/**
	 * List of {@link Kelurahan} instances.
	 */
	private List<Kelurahan> listKelurahan;

	/**
	 * Create instance.
	 */
	public Kecamatan() {
		super();
	}

	/**
	 * Create instance.
	 * 
	 * @param id
	 * @param kota
	 * @param nama
	 * @throws EmptyIdException {@code id} is 0 or negative.
	 */
	public Kecamatan(int id, Kota kota, String nama) throws EmptyIdException {
		super(id, nama);
		setKota(kota);
	}

	@Override
	@Id @GeneratedValue
	public int getId() {
		return id;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_kota", referencedColumnName = "id")
	public Kota getKota() {
		return kota;
	}

	/**
	 * Set {@link Kota}
	 * 
	 * @param kota
	 */
	public void setKota(Kota kota) {
		this.kota = kota;
	}

	@Override
	@Column(name = "nama")
	public String getNama() {
		return nama;
	}

	/**
	 * Returns the list of {@link Kelurahan} instances.
	 * 
	 * @return listKelurahan
	 */
	@JsonIgnore
	@OneToMany(targetEntity = Kelurahan.class, mappedBy = "kecamatan", fetch = FetchType.LAZY,
			cascade = CascadeType.REFRESH)
	public List<Kelurahan> getListKelurahan() {
		return listKelurahan;
	}

	/**
	 * Set the list of {@link Kelurahan} instances.
	 * @param listKelurahan
	 */
	public void setListKelurahan(List<Kelurahan> listKelurahan) {
		this.listKelurahan = listKelurahan;
	}
	
	public Kelurahan addKelurahan(Kelurahan kelurahan) {
		getListKelurahan().add(kelurahan);
		kelurahan.setKecamatan(this);
		
		return kelurahan;
	}
	
	public Kelurahan removeKelurahan(Kelurahan kelurahan) {
		getListKelurahan().remove(kelurahan);
		kelurahan.setKecamatan(null);
		
		return kelurahan;
	}

	@JsonIgnore
	@Transient
	public int getIdKota() {
		return kota.getId();
	}
	
	@JsonIgnore
	@Transient
	public String getNamaKota() {
		return kota.getNama();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((kota == null) ? 0 : kota.hashCode());
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
		Kecamatan other = (Kecamatan) obj;
		if (kota == null) {
			if (other.kota != null)
				return false;
		} else if (!kota.equals(other.kota))
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
		return "Kecamatan [nama=" + nama + ", kota=" + kota + "]";
	}
}
