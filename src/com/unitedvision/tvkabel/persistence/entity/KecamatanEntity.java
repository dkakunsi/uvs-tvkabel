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
import com.unitedvision.tvkabel.core.domain.Kecamatan;
import com.unitedvision.tvkabel.exception.EmptyIdException;
import com.unitedvision.tvkabel.web.model.KecamatanModel;

/**
 * Mapping of kecamatan table in database.<br />
 * 
 * @author Deddy Christoper Kakunsi
 *
 */
@Entity
@Table(name = "kecamatan")
public final class KecamatanEntity extends com.unitedvision.tvkabel.persistence.entity.Entity 
	implements Kecamatan {
	/**
	 * Name.
	 */
	private String nama;
	
	/**
	 * {@link KotaEntity} where kecamatan placed.
	 */
	private KotaEntity kota;

	/**
	 * List of {@link KelurahanEntity} placed in kecamatan.
	 */
	private List<KelurahanEntity> listKelurahan;

	/**
	 * Create an empty instance.
	 */
	public KecamatanEntity() {
		super();
	}

	/**
	 * Create an entity which is placed in {@link KotaEntity} and have a name.<br />
	 * This mean you create a new entity, not a detached one.
	 * 
	 * @param kotaEntity {@link KotaEntity} which this kecamatan is placed.
	 * @param nama name
	 */
	public KecamatanEntity(KotaEntity kotaEntity, String nama) {
		super();
		this.nama = nama;
		this.kota = kotaEntity;
	}

	/**
	 * Create an entity which is placed in {@link KotaEntity}, have a name, and already have an id.<br />
	 * This mean you create a detached entity, not a new one.
	 * 
	 * @param id must be positive
	 * @param kotaEntity
	 * @param nama
	 * @throws EmptyIdException id is 0 or negative
	 */
	public KecamatanEntity(int id, KotaEntity kotaEntity, String nama) throws EmptyIdException {
		this(kotaEntity, nama);
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
	@JoinColumn(name = "id_kota", referencedColumnName = "id")
	public KotaEntity getKota() {
		return kota;
	}

	/**
	 * Set {@link KotaEntity}
	 * 
	 * @param kotaEntity
	 */
	public void setKota(KotaEntity kotaEntity) {
		this.kota = kotaEntity;
	}
	
	@Override
	@Column(name = "nama")
	public String getNama() {
		return nama;
	}

	/**
	 * Set name.
	 * 
	 * @param nama
	 */
	public void setNama(String nama) {
		this.nama = nama;
	}

	/**
	 * Returns the list of {@link KelurahanEntity} which is olaced in kecamatan.<br />
	 * 
	 * @return listKelurahan
	 */
	@JsonIgnore
	@OneToMany(targetEntity = KelurahanEntity.class, mappedBy = "kecamatan", fetch = FetchType.LAZY,
			cascade = {CascadeType.REFRESH, CascadeType.REMOVE})
	public List<KelurahanEntity> getListKelurahan() {
		return listKelurahan;
	}

	/**
	 * Set the list of {@link KelurahanEntity} which is placed in kecamatan.
	 * @param listKelurahan
	 */
	public void setListKelurahan(List<KelurahanEntity> listKelurahan) {
		this.listKelurahan = listKelurahan;
	}

	@Override
	@JsonIgnore
	@Transient
	public int getIdKota() {
		return getKota().getId();
	}

	@Override
	@JsonIgnore
	@Transient
	public String getNamaKota() {
		return getKota().getNama();
	}

	@Override
	public KecamatanEntity toEntity() {
		return this;
	}

	@Override
	public KecamatanModel toModel() {
		return new KecamatanModel(this);
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
		KecamatanEntity other = (KecamatanEntity) obj;
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
