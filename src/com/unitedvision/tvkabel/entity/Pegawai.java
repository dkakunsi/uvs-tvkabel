package com.unitedvision.tvkabel.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
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
import com.unitedvision.tvkabel.exception.EmptyCodeException;
import com.unitedvision.tvkabel.exception.EmptyIdException;
import com.unitedvision.tvkabel.exception.StatusChangeException;

/**
 * Pegawai domain.
 * 
 * @author Deddy Christoper Kakunsi
 *
 */
@Entity
@Table(name = "pegawai")
public final class Pegawai extends CodableDomain implements Operator, Removable {
	/**
	 * {@link Perusahaan} instance
	 */
	private Perusahaan perusahaan;
	
	/** Name */
	private String nama;
	
	/** Credential */
	private Kredensi kredensi;
	
	/**
	 * Stated whether pegawai is removed or still active.  
	 */
	private Status status;

	/**
	 * List of {@link Pembayaran} handled by pegawai.
	 */
	private List<Pembayaran> listPembayaran;
	private List<Token> listToken;

	/** DEFAULT KODE FORMAT */
	public static final String DEFAULT_KODE_FORMAT = "%sPG%d";

	/**
	 * Create empty instance.
	 */
	public Pegawai() {
		super();
	}

	/**
	 * Create instance.
	 * @param id must be positive
	 * @param kode cannot be null or an empty String
	 * @param perusahaan
	 * @param nama
	 * @param kredensi
	 * @param status
	 * @throws EmptyCodeException {@code kode} is null or an empty String
	 * @throws EmptyIdException {@code id} is not positive.
	 */
	public Pegawai(int id, String kode, Perusahaan perusahaan, String nama, Kredensi kredensi, Status status) throws EmptyCodeException, EmptyIdException {
		super();
		setId(id);
		setKode(kode);
		setPerusahaan(perusahaan);
		setNama(nama);
		setKredensi(kredensi);
		setStatus(status);
	}

	@Override
	@Id @GeneratedValue
	public int getId() {
		return super.getId();
	}

	@Override
	@Column(name = "kode")
	public String getKode() {
		return super.getKode();
	}

	/**
	 * Return {@link Status} instance.
	 * @return status
	 */
	@Column(name = "status")
	public Status getStatus() {
		return status;
	}

	/**
	 * Set {@link Status} instance.
	 * @param status
	 */
	public void setStatus(Status status) {
		this.status = status;
	}

	/**
	 * Return {@link Perusahaan} instance.
	 * @return perusahaan
	 */
	// JavaScript need perusahaan data to use, so no ignored in JSON file.
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_perusahaan", referencedColumnName = "id")
	public Perusahaan getPerusahaan() {
		return perusahaan;
	}

	/**
	 * Set {@link Perusahaan} instance.
	 * @param perusahaan
	 */
	public void setPerusahaan(Perusahaan perusahaan) {
		this.perusahaan = perusahaan;
	}

	/**
	 * Return name.
	 * @return
	 */
	@Column(name = "nama")
	public String getNama() {
		return nama;
	}

	/**
	 * Set name.
	 * @param nama
	 */
	public void setNama(String nama) {
		this.nama = nama;
	}

	/**
	 * Return {@link Kredensi} instance.
	 * @return kredensi
	 */
	@Embedded
	public Kredensi getKredensi() {
		return kredensi;
	}

	/**
	 * Set {@link Kredensi} instance.
	 * @param kredensi
	 */
	public void setKredensi(Kredensi kredensi) {
		this.kredensi = kredensi;
	}

	/**
	 * Return list of {@link Pembayaran} handled by pegawai.
	 * @return listPembayaran
	 */
	@JsonIgnore
	@OneToMany(targetEntity = Pembayaran.class, mappedBy = "pegawai", fetch = FetchType.LAZY, 
			cascade = CascadeType.REFRESH)
	public List<Pembayaran> getListPembayaran() {
		return listPembayaran;
	}

	/**
	 * Set list of {@link Pembayaran} handled by employee.
	 * @param listPembayaran
	 */
	public void setListPembayaran(List<Pembayaran> listPembayaran) {
		this.listPembayaran = listPembayaran;
	}
	
	public Pembayaran addPembayaran(Pembayaran pembayaran) {
		getListPembayaran().add(pembayaran);
		pembayaran.setPegawai(this);
		
		return pembayaran;
	}
	
	public Pembayaran removePembayaran(Pembayaran pembayaran) {
		getListPembayaran().remove(pembayaran);
		pembayaran.setPegawai(null);
		
		return pembayaran;
	}
	
	@JsonIgnore
	@OneToMany(targetEntity = Token.class, mappedBy = "pegawai", fetch = FetchType.LAZY, 
			cascade = CascadeType.REFRESH)
	public List<Token> getListToken() {
		return listToken;
	}

	public void setListToken(List<Token> listToken) {
		this.listToken = listToken;
	}
	
	public Token addToken(Token token) {
		getListToken().add(token);
		token.setPegawai(this);
		
		return token;
	}
	
	public Token removeToken(Token token) {
		getListToken().remove(token);
		token.setPegawai(null);
		
		return token;
	}

	@Override
	@JsonIgnore
	@Transient
	public String getUsername() {
		return kredensi.getUsername();
	}
	
	@Override
	@JsonIgnore
	@Transient
	public String getPassword() {
		return kredensi.getPassword();
	}

	@Override
	@JsonIgnore
	@Transient
	public Role getRole() {
		return kredensi.getRole();
	}
	
	@JsonIgnore
	public Operator toOperator() {
		return this;
	}
	
	@Override
	@JsonIgnore
	public Pegawai toDomain() {
		return this;
	}
	
	@Override
	@Transient
	public void remove() throws StatusChangeException {
		if (isNew())
			throw new StatusChangeException("Pegawai do not have id");
		
		if (status.equals(Status.REMOVED))
			throw new StatusChangeException("Pelanggan was already removed");

		setStatus(Status.REMOVED);
		try {
			setKode(String.format("REM%d", getId()));
		} catch (EmptyCodeException e) {
			e.printStackTrace();
		}
	}

	public static Pegawai createGuest() throws EmptyCodeException, EmptyIdException {
		Kredensi kredensi = new Kredensi("guest", "guest", Role.GUEST);

		return new Pegawai(0, "GUEST", null, "GUEST", kredensi, Status.AKTIF);
	}
	
	/**
	 * Generate {@link Pegawai} kode for current instance.
	 * @param count
	 * @return kode
	 */
	@Transient
	public String generateKode(long count) {
		kode = String.format(DEFAULT_KODE_FORMAT, perusahaan.getKode(), (count + 1));

		return kode;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((kredensi == null) ? 0 : kredensi.hashCode());
		result = prime * result + ((nama == null) ? 0 : nama.hashCode());
		result = prime * result
				+ ((perusahaan == null) ? 0 : perusahaan.hashCode());
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
		Pegawai other = (Pegawai) obj;
		if (kredensi == null) {
			if (other.kredensi != null)
				return false;
		} else if (!kredensi.equals(other.kredensi))
			return false;
		if (nama == null) {
			if (other.nama != null)
				return false;
		} else if (!nama.equals(other.nama))
			return false;
		if (perusahaan == null) {
			if (other.perusahaan != null)
				return false;
		} else if (!perusahaan.equals(other.perusahaan))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Pegawai [perusahaan=" + perusahaan + ", nama=" + nama
				+ ", kredensi=" + kredensi + "]";
	}

	/**
	 * Pegawai's credential.
	 * 
	 * @author Deddy Christoper Kakunsi
	 *
	 */
	@Embeddable
	public static final class Kredensi {
		
		/** 
		 * Username used to login 
		 */
		private String username;
		
		/** 
		 * Password used to login
		 */
		private String password;
		
		/** 
		 * Role used for authorization 
		 */
		private Role role;

		/**
		 * Create instance.
		 */
		public Kredensi() {
			super();
		}

		/**
		 * Create instance.
		 * @param isOperator
		 * @param username
		 * @param password
		 * @param role
		 */
		public Kredensi(String username, String password, Role role) {
			super();
			setUsername(username);
			setPassword(password);
			setRole(role);
		}

		/**
		 * Return username.
		 * @return username
		 */
		@Column(name = "username")
		public String getUsername() {
			return username;
		}

		/**
		 * Set username.
		 * @param username
		 */
		public void setUsername(String username) {
			this.username = username;
		}

		/**
		 * Set password.
		 * @return password
		 */
		@Column(name = "password")
		public String getPassword() {
			return password;
		}

		/**
		 * Set password used to login.
		 * @param password
		 */
		public void setPassword(String password) {
			this.password = password;
		}

		/**
		 * Return {@link Role} instance.
		 * @return role
		 */
		@Column(name = "role")
		public Role getRole() {
			return role;
		}

		/**
		 * Set role.
		 * @param role
		 */
		public void setRole(Role role) {
			this.role = role;
		}
	}

	/**
	 * Employee's status.
	 * 
	 * @author Deddy Christoper Kakunsi
	 *
	 */
	public enum Status {
		/**
		 * AKTIF
		 */
		AKTIF,
		/**
		 * REMOVED
		 */
		REMOVED;
		
		/**
		 * Returns {@link Status} from the given string.
		 * @param status
		 * @return {@link Status}.
		 */
		public static Status get(String status) {
			status = status.toUpperCase();

			return Pegawai.Status.valueOf(status);
		}
	}
	
	/**
	 * Credential Role for Authorization.
	 * 
	 * @author Deddy Christoper Kakunsi
	 *
	 */
	public enum Role {
		/** ADMIN */
		ADMIN,
		/** OWNER */
		OWNER,
		/** OPERATOR */
		OPERATOR, 
		/** NOTHING */
		NOTHING,
		/** GUEST */
		GUEST;
	}

}
