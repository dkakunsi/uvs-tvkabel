package com.unitedvision.tvkabel.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.unitedvision.tvkabel.core.domain.Alamat;
import com.unitedvision.tvkabel.core.domain.Kecamatan;
import com.unitedvision.tvkabel.core.domain.Kota;

/**
 * Address representation.
 * 
 * @author Deddy Christoper Kakunsi
 *
 */
@Embeddable
public final class AlamatValue implements Alamat {
	/**
	 * kelurahan.
	 */
	private KelurahanEntity kelurahan;
	
	/**
	 * lingkungan.
	 */
	private int lingkungan;

	/**
	 * Detail of address.
	 */
	private String detailAlamat;
	
	/**
	 * Latitude position on map
	 */
	private float latitude;

	/**
	 * Longitude position on map
	 */
	private float longitude;

	/**
	 * Create an empty Alamat. This will only used by Persistence Provider not by Developer.
	 */
	public AlamatValue() {
		super();
	}

	/**
	 * Create Alamat with specified {@link KelurahanEntity}, lingkungan, and detail alamat

	 * @param kelurahanEntity {@link KelurahanEntity} object
	 * @param lingkungan {@link int} lingkungan
	 * @param detailAlamat {@link String} detail of Alamat
	 */
	public AlamatValue(KelurahanEntity kelurahanEntity, int lingkungan, String detailAlamat) {
		super();
		this.kelurahan = kelurahanEntity;
		this.lingkungan = lingkungan;
		this.detailAlamat = detailAlamat;
	}

	@Override
	@Column(name = "lingkungan")
	public int getLingkungan() {
		return lingkungan;
	}

	/**
	 * Set lingkungan.
	 * @param lingkungan
	 */
	public void setLingkungan(int lingkungan) {
		this.lingkungan = lingkungan;
	}

	@Override
	@Column(name = "detail_alamat")
	public String getDetailAlamat() {
		return detailAlamat;
	}

	/**
	 * Set detailAlamat.
	 * @param detailAlamat
	 */
	public void setDetailAlamat(String detailAlamat) {
		this.detailAlamat = detailAlamat;
	}

	@Override
	@Column(name = "latitude")
	public float getLatitude() {
		return latitude;
	}
	
	/**
	 * Set latitude value
	 * @param latitude
	 */
	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}
	
	@Override
	@Column(name = "longitude")
	public float getLongitude() {
		return longitude;
	}

	/**
	 * Set longitude value.
	 * @param longitude
	 */
	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}
	
	@Override
	@JsonIgnore
	@Transient
	public KelurahanEntity getKelurahan() {
		return kelurahan;
	}

	/**
	 * Set kelurahan.  It is a transient object, which means it will not be persisted to database.
	 * If you want to persist it to database, use {@link KelurahanEntity} object in alamat container classes instead,
	 * such as {@link PerusahaanEntity} or {@link PelangganEntity}.
	 * 
	 * @param {@link KelurahanEntity} kelurahan
	 */
	public void setKelurahan(KelurahanEntity kelurahanEntity) {
		this.kelurahan = kelurahanEntity;
	}

	@Override
	@JsonIgnore
	@Transient
	public Kecamatan getKecamatan() {
		return getKelurahan().getKecamatan();
	}

	@Override
	@JsonIgnore
	@Transient
	public Kota getKota() {
		return getKelurahan().getKecamatan().getKota();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((detailAlamat == null) ? 0 : detailAlamat.hashCode());
		result = prime * result
				+ ((kelurahan == null) ? 0 : kelurahan.hashCode());
		result = prime * result + lingkungan;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AlamatValue other = (AlamatValue) obj;
		if (detailAlamat == null) {
			if (other.detailAlamat != null)
				return false;
		} else if (!detailAlamat.equals(other.detailAlamat))
			return false;
		if (kelurahan == null) {
			if (other.kelurahan != null)
				return false;
		} else if (!kelurahan.equals(other.kelurahan))
			return false;
		if (lingkungan != other.lingkungan)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Alamat [kelurahan=" + kelurahan + ", lingkungan=" + lingkungan
				+ ", detailAlamat=" + detailAlamat + "]";
	}

	@Override
	public AlamatValue toEntity() {
		return this;
	}

	@Override
	public Alamat toModel() {
		// TODO Auto-generated method stub
		return null;
	}
}
