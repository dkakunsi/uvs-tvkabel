package com.unitedvision.tvkabel.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Contact
 * 
 * @author Deddy Christoper Kakunsi
 *
 */
@Embeddable
public class Kontak {
	/** Phone number */
	private String telepon;
	
	/** Cell phone number */
	private String hp;
	
	/** Email address */
	private String email;

	/**
	 * Create instance.
	 */
	public Kontak() {
		super();
	}

	/**
	 * Create instance.
	 * @param telepon
	 * @param ponsel
	 * @param email
	 */
	public Kontak(String telepon, String ponsel, String email) {
		super();
		setTelepon(telepon);
		setHp(ponsel);
		setEmail(email);
	}

	/**
	 * Return phone number.
	 * @return phone number
	 */
	@Column(name = "telepon")
	public String getTelepon() {
		return telepon;
	}

	/**
	 * Set phone number.
	 * @param telepon
	 */
	public void setTelepon(String telepon) {
		this.telepon = telepon;
	}

	/**
	 * Return cell phone number.
	 * @return cell phone number
	 */
	@Column(name = "hp")
	public String getHp() {
		return hp;
	}

	/**
	 * Set cell phone number.
	 * @param ponsel
	 */
	public void setHp(String ponsel) {
		this.hp = ponsel;
	}

	/**
	 * Return email address.
	 * @return email
	 */
	@Column(name = "email")
	public String getEmail() {
		return email;
	}

	/**
	 * Set email address.
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((hp == null) ? 0 : hp.hashCode());
		result = prime * result + ((telepon == null) ? 0 : telepon.hashCode());
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
		Kontak other = (Kontak) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (hp == null) {
			if (other.hp != null)
				return false;
		} else if (!hp.equals(other.hp))
			return false;
		if (telepon == null) {
			if (other.telepon != null)
				return false;
		} else if (!telepon.equals(other.telepon))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Kontak [telepon=" + telepon + ", ponsel=" + hp + ", email="
				+ email + "]";
	}
}
