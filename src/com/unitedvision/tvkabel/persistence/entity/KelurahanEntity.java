package com.unitedvision.tvkabel.persistence.entity;

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
import com.unitedvision.tvkabel.core.domain.Kelurahan;
import com.unitedvision.tvkabel.exception.EmptyIdException;
import com.unitedvision.tvkabel.web.model.KelurahanModel;

/**
 * Mapping of kelurahan table in database.
 * 
 * @author Deddy Christoper Kakunsi
 *
 */
@Entity
@Table(name = "kelurahan")
public final class KelurahanEntity extends com.unitedvision.tvkabel.persistence.entity.Entity
	implements Kelurahan {
	/**
	 * Name.
	 */
	private String nama;
	
	/**
	 * {@link KecamatanEntity} where kelurahan placed.
	 */
	private KecamatanEntity kecamatan;

	/**
	 * List of {@link PerusahaanEntity} placed in kelurahan.
	 */
	private List<PerusahaanEntity> listPerusahaan;
	
	/**
	 * List of {@link PelangganEntity} placed in kelurahan.
	 */
	private List<PelangganEntity> listPelanggan;
	
	/**
	 * Create an empty entity.<br />
	 */
	public KelurahanEntity() {
		super();
	}
	
	/**
	 * Create an entity which is placed in {@link KecamatanEntity} and have a name.<br />
	 * This mean you create a new entity, not a detached one.
	 * 
	 * @param kecamatanEntity
	 * @param nama name
	 */
	public KelurahanEntity(KecamatanEntity kecamatanEntity, String nama) {
		super();
		this.nama = nama;
		this.kecamatan = kecamatanEntity;
	}

	/**
	 * Create an entity which is placed in {@link KecamatanEntity}, have a name, and already have an id.<br />
	 * This mean you create a detached entity.
	 * 
	 * @param id
	 * @param kota
	 * @param nama name
	 */
	public KelurahanEntity(int id, KecamatanEntity kecamatanEntity, String nama) throws EmptyIdException {
		this(kecamatanEntity, nama);
		if (id <= 0)
			throw new EmptyIdException("id must be positive");
		this.id = id;
	}

	@Override
	@Id @GeneratedValue
	public int getId() {
		return id;
	}

	/**
	 * Set id.
	 * 
	 * @param id must be positive
	 * @throws EmptyIdException id is 0 or negative
	 */
	public void setId(int id) throws EmptyIdException {
		if (id <= 0)
			throw new EmptyIdException("id must be positive");
		this.id = id;
	}

	@Override
	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_kecamatan", referencedColumnName = "id")
	public KecamatanEntity getKecamatan() {
		return kecamatan;
	}

	/**
	 * Set the {@link KecamatanEntity}.
	 * 
	 * @param kota
	 */
	public void setKecamatan(KecamatanEntity kecamatanEntity) {
		this.kecamatan = kecamatanEntity;
	}

	@Override
	@Column(name = "nama")
	public String getNama() {
		return nama;
	}

	/**
	 * Set name.
	 * 
	 * @param nama name
	 */
	public void setNama(String nama) {
		this.nama = nama;
	}

	/**
	 * Returns the list of {@link PerusahaanEntity} which is placed in kelurahan.<br />
	 * 
	 * @return listPerusahaan
	 */
	@JsonIgnore
	@OneToMany(targetEntity = PerusahaanEntity.class, mappedBy = "kelurahan", fetch = FetchType.LAZY,
			cascade = {CascadeType.REFRESH, CascadeType.REMOVE})
	public List<PerusahaanEntity> getListPerusahaan() {
		return listPerusahaan;
	}

	/**
	 * Set the list of {@link PerusahaanEntity} which is placed in kelurahan.
	 * 
	 * @param listPerusahaan 
	 */
	public void setListPerusahaan(List<PerusahaanEntity> listPerusahaan) {
		this.listPerusahaan = listPerusahaan;
	}

	/**
	 * Returns the list of {@link PelangganEntity} which is placed in kelurahan.
	 * 
	 * @return listPelanggan 
	 */
	@JsonIgnore
	@OneToMany(targetEntity = PelangganEntity.class, mappedBy = "kelurahan", fetch = FetchType.LAZY,
			cascade = {CascadeType.REFRESH, CascadeType.REMOVE})
	public List<PelangganEntity> getListPelanggan() {
		return listPelanggan;
	}

	/**
	 * Set the list of {@link PelangganEntity} which is placed in kelurahan.
	 * 
	 * @param listPelanggan 
	 */
	public void setListPelanggan(List<PelangganEntity> listPelanggan) {
		this.listPelanggan = listPelanggan;
	}

	@Override
	@JsonIgnore
	@Transient
	public int getIdKota() {
		return getKecamatan().getIdKota();
	}
	
	@Override
	@JsonIgnore
	@Transient
	public String getNamaKota() {
		return getKecamatan().getNamaKota();
	}
	
	@Override
	@JsonIgnore
	@Transient
	public int getIdKecamatan() {
		return getKecamatan().getId();
	}
	
	@Override
	@JsonIgnore
	@Transient
	public String getNamaKecamatan() {
		return getKecamatan().getNama();
	}

	@Override
	public KelurahanEntity toEntity() {
		return this;
	}

	@Override
	public KelurahanModel toModel() {
		return new KelurahanModel(this);
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
		KelurahanEntity other = (KelurahanEntity) obj;
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
