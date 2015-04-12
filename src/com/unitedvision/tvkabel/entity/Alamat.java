package com.unitedvision.tvkabel.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Address value object.
 * 
 * @author Deddy Christoper Kakunsi
 *
 */
@Embeddable
public final class Alamat {
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
	 * Create instance.
	 */
	public Alamat() {
		super();
	}

	/**
	 * Create instance.
	 * @param lingkungan
	 * @param detailAlamat
	 * @param latitude
	 * @param longitude
	 */
	public Alamat(int lingkungan, String detailAlamat, float latitude, float longitude) {
		super();
		setLingkungan(lingkungan);
		setDetailAlamat(detailAlamat);
		setLatitude(latitude);
		setLongitude(longitude);
	}

	/**
	 * Return lingkungan.
	 * @return lingkungan
	 */
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

	/**
	 * Return detailAlamat.
	 * @return
	 */
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

	/**
	 * Return latitude position.
	 * @return latitude
	 */
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

	/**
	 * Return longitude position.
	 * @return longitude
	 */
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((detailAlamat == null) ? 0 : detailAlamat.hashCode());
		result = prime * result + Float.floatToIntBits(latitude);
		result = prime * result + lingkungan;
		result = prime * result + Float.floatToIntBits(longitude);
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
		Alamat other = (Alamat) obj;
		if (detailAlamat == null) {
			if (other.detailAlamat != null)
				return false;
		} else if (!detailAlamat.equals(other.detailAlamat))
			return false;
		if (Float.floatToIntBits(latitude) != Float
				.floatToIntBits(other.latitude))
			return false;
		if (lingkungan != other.lingkungan)
			return false;
		if (Float.floatToIntBits(longitude) != Float
				.floatToIntBits(other.longitude))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Alamat [lingkungan=" + lingkungan + ", detailAlamat="
				+ detailAlamat + ", latitude=" + latitude + ", longitude="
				+ longitude + "]";
	}

}
