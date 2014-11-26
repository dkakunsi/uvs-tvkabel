package com.unitedvision.tvkabel.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.unitedvision.tvkabel.core.domain.Kontak;

/**
 * Contact
 * 
 * @author Deddy Christoper Kakunsi
 *
 */
@Embeddable
public class KontakValue implements Kontak {
	/** Telephone number */
	private String telepon;
	
	/** Cellphone number */
	private String hp;
	
	/** Email address */
	private String email;

	/**
	 * Create empty instance.
	 */
	public KontakValue() {
		super();
	}

	/**
	 * Create instance.
	 * @param telepon
	 * @param ponsel
	 * @param email
	 */
	public KontakValue(String telepon, String ponsel, String email) {
		super();
		this.telepon = telepon;
		this.hp = ponsel;
		this.email = email;
	}

	@Override
	@Column(name = "telepon")
	public String getTelepon() {
		return telepon;
	}

	/**
	 * Set telephone number.
	 * @param telepon
	 */
	public void setTelepon(String telepon) {
		this.telepon = telepon;
	}

	@Override
	@Column(name = "hp")
	public String getHp() {
		return hp;
	}

	/**
	 * Set cellphone number.
	 * @param ponsel
	 */
	public void setHp(String ponsel) {
		this.hp = ponsel;
	}

	@Override
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
	public KontakValue toEntity() {
		return this;
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
		KontakValue other = (KontakValue) obj;
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

	@Override
	public Kontak toModel() {
		// TODO Auto-generated method stub
		return null;
	}
}
