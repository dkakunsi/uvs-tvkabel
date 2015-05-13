package com.unitedvision.tvkabel.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.unitedvision.tvkabel.exception.EmptyIdException;

/**
 * Kota domain.
 * 
 * @author Deddy Christoper Kakunsi
 *
 */
@Entity
@Table(name = "kota")
public final class Kota extends Region {

	/** List of {@link Kecamatan} */
	private List<Kecamatan> listKecamatan;

	/**
	 * Create instance.
	 */
	public Kota() {
		super();
	}
	
	/**
	 * Create instance.
	 * @param nama
	 */
	public Kota(String nama) {
		super();
		setNama(nama);
	}
	
	/**
	 * Create instance.
	 * @param id
	 * @param kode
	 * @param nama
	 * @throws EmptyIdException{@code id} is not positive.
	 */
	public Kota(int id, String nama) throws EmptyIdException {
		super(id, nama);
	}

	@Override
	@Id @GeneratedValue
	public int getId() {
		return id;
	}

	/**
	 * Return {@link Kota}'s name.
	 * @return nama
	 */
	@Column(name = "nama")
	public String getNama() {
		return nama;
	}

	/**
	 * Return list of {@link Kecamatan} instances. 
	 * @return listKecamatan
	 */
	@JsonIgnore
	@OneToMany(targetEntity = Kecamatan.class, mappedBy = "kota", fetch = FetchType.LAZY, 
			cascade = CascadeType.REFRESH)
	public List<Kecamatan> getListKecamatan() {
		return listKecamatan;
	}

	/**
	 * Set list of {@link Kecamatan} instances.
	 * @param listKecamatan
	 */
	public void setListKecamatan(List<Kecamatan> listKecamatan) {
		this.listKecamatan = listKecamatan;
	}
	
	public Kecamatan addKecamatan(Kecamatan kecamatan) {
		getListKecamatan().add(kecamatan);
		kecamatan.setKota(this);
		
		return kecamatan;
	}
	
	public Kecamatan removeKecamatan(Kecamatan kecamatan) {
		getListKecamatan().remove(kecamatan);
		kecamatan.setKota(null);
		
		return kecamatan;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
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
		Kota other = (Kota) obj;
		if (nama == null) {
			if (other.nama != null)
				return false;
		} else if (!nama.equals(other.nama))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Kota [id=" + id + ", nama=" + nama + "]";
	}
}
