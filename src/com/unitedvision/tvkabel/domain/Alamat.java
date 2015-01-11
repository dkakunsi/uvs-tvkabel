package com.unitedvision.tvkabel.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

/**
 * Address value object.
 * 
 * @author Deddy Christoper Kakunsi
 *
 */
@Embeddable
public final class Alamat {
	/**
	 * kelurahan.
	 */
	private Kelurahan kelurahan;
	
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
	 * @param kelurahan
	 * @param lingkungan
	 * @param detailAlamat
	 */
	public Alamat(Kelurahan kelurahan, int lingkungan, String detailAlamat) {
		super();
		setKelurahan(kelurahan);
		setLingkungan(lingkungan);
		setDetailAlamat(detailAlamat);
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

	/**
	 * Return {@link Kelurahan} instance.
	 * @return kelurahan
	 */
	@Transient
	public Kelurahan getKelurahan() {
		return kelurahan;
	}

	/**
	 * Set kelurahan.  It is a transient object, which means it will not be persisted to database.
	 * If you want to persist it to database, use {@link Kelurahan} object in alamat container classes instead,
	 * such as {@link Perusahaan} or {@link Pelanggan}.
	 * 
	 * @param {@link Kelurahan} kelurahan
	 */
	public void setKelurahan(Kelurahan kelurahan) {
		this.kelurahan = kelurahan;
	}

	/**
	 * If kelurahan is null, create a new one.
	 */
	private void setKelurahan() {
		if (kelurahan == null)
			kelurahan = new Kelurahan();
	}

	/**
	 * Return {@link Kecamatan} instance.
	 * @return kecamatan
	 */
	@Transient
	public Kecamatan getKecamatan() {
		return getKelurahan().getKecamatan();
	}

	/**
	 * Return {@link Kota} instance.
	 * @return kota
	 */
	@Transient
	public Kota getKota() {
		return getKelurahan().getKecamatan().getKota();
	}

	@Transient
	public int getIdKota() {
		setKelurahan();
		return kelurahan.getIdKota();
	}

	@Transient
	public String getNamaKota() {
		setKelurahan();
		return kelurahan.getNamaKota();
	}

	@Transient
	public int getIdKecamatan() {
		setKelurahan();
		return kelurahan.getIdKecamatan();
	}

	@Transient
	public String getNamaKelurahan() {
		setKelurahan();
		return kelurahan.getNama();
	}

	@Transient
	public int getIdKelurahan() {
		setKelurahan();
		return kelurahan.getId();
	}

	@Transient
	public String getNamaKecamatan() {
		setKelurahan();
		return kelurahan.getNamaKecamatan();
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
		Alamat other = (Alamat) obj;
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
}
