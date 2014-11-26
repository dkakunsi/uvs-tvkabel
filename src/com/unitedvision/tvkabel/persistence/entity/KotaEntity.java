package com.unitedvision.tvkabel.persistence.entity;

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
import com.unitedvision.tvkabel.core.domain.Kota;
import com.unitedvision.tvkabel.exception.EmptyIdException;
import com.unitedvision.tvkabel.web.model.KotaModel;

/**
 * Mapping of kota table in database.
 * 
 * @author Deddy CHristoper Kakunsi
 *
 */
@Entity
@Table(name = "kota")
public final class KotaEntity extends com.unitedvision.tvkabel.persistence.entity.Entity implements Kota {
	/** name */
	private String nama;
	
	/** list of {@link KecamatanEntity} which is placed in kota*/
	private List<KecamatanEntity> listKecamatan;

	/**
	 * Create an empty instance.
	 */
	public KotaEntity() {
		super();
	}

	/**
	 * Create minimum instance that only contain id.
	 * @param id must be positive
	 * @throws EmptyIdException id passed in is 0 or negative
	 */
	public KotaEntity(int id) throws EmptyIdException {
		super();
		if (id <= 0)
			throw new EmptyIdException("id must be positive");
		this.id = id;
	}

	/**
	 * Create instance with name.
	 * @param nama
	 */
	public KotaEntity(String nama) {
		super();
		this.nama = nama;
	}

	/**
	 * Create instance with id and name.
	 * @param id must be positive
	 * @param nama name
	 * @throws EmptyIdException id passed in is 0 or negative
	 */
	public KotaEntity(int id, String nama) throws EmptyIdException {
		this(nama);
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
	 * @param id must be positive
	 * @throws EmptyIdException id passed in is 0 or negative
	 */
	public void setId(int id) throws EmptyIdException {
		if (id <= 0)
			throw new EmptyIdException("id must be positive");
		this.id = id;
	}

	@Override
	@Column(name = "nama")
	public String getNama() {
		return nama;
	}

	/**
	 * Set name.
	 * @param nama name
	 */
	public void setNama(String nama) {
		this.nama = nama;
	}

	/**
	 * Return list of {@link KecamatanEntity} which is placed in kecamatan. 
	 * This is only for database mapping purpose, developer must use {@link KecamatanService} to retrieve listKecamatan from database.
	 * @return listKecamatan
	 */
	@JsonIgnore
	@OneToMany(targetEntity = KecamatanEntity.class, mappedBy = "kota", fetch = FetchType.LAZY, 
			cascade = {CascadeType.REFRESH, CascadeType.REMOVE})
	public List<KecamatanEntity> getListKecamatan() {
		return listKecamatan;
	}

	/**
	 * Set list of {@link KecamatanEntity} which is placed in kecamatan.
	 * This is only for database mapping purpose, developer must use {@link KecamatanService} to save listKecamatan to database.
	 * @param listKecamatan
	 */
	public void setListKecamatan(List<KecamatanEntity> listKecamatan) {
		this.listKecamatan = listKecamatan;
	}
	
	@Override
	public KotaEntity toEntity() {
		return this;
	}

	@Override
	public KotaModel toModel() {
		return new KotaModel(this);
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
		KotaEntity other = (KotaEntity) obj;
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
