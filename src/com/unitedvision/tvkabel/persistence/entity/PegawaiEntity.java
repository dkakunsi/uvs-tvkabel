package com.unitedvision.tvkabel.persistence.entity;

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
import com.unitedvision.tvkabel.core.domain.Kredensi;
import com.unitedvision.tvkabel.core.domain.Kredensi.Role;
import com.unitedvision.tvkabel.core.domain.Operator;
import com.unitedvision.tvkabel.core.domain.Pegawai;
import com.unitedvision.tvkabel.exception.EmptyCodeException;
import com.unitedvision.tvkabel.exception.EmptyIdException;
import com.unitedvision.tvkabel.exception.StatusChangeException;
import com.unitedvision.tvkabel.web.model.PegawaiModel;

/**
 * Mapping of pegawai table in database.
 * 
 * @author Deddy Chrsitoper Kakunsi
 *
 */
@Entity
@Table(name = "pegawai")
public final class PegawaiEntity extends CodableEntity implements Pegawai {
	/**
	 * {@link Perusahaan} where pegawai works
	 */
	private PerusahaanEntity perusahaan;
	
	/** Name */
	private String nama;
	
	/** Credential */
	private KredensiValue kredensi;
	
	/**
	 * Stated whether pegawai is removed or still active.  
	 */
	private Status status;

	/**
	 * List of {@link PembayaranEntity} handled by pegawai.
	 */
	private List<PembayaranEntity> listPembayaran;

	/**
	 * Create empty instance.
	 */
	public PegawaiEntity() {
		super();
	}

	/**
	 * Create minimum instance containing only id.
	 * @param id must be positive
	 * @throws EmptyIdException id is 0 or zero
	 */
	@Deprecated
	public PegawaiEntity(int id) throws EmptyIdException {
		super();
		setId(id);
	}

	/**
	 * Create new instance not persisted in database, do not has code and id.
	 * @param perusahaan
	 * @param nama
	 * @param kredensi
	 * @param status
	 */
	public PegawaiEntity(PerusahaanEntity perusahaan, String nama, KredensiValue kredensi, Status status) {
		super();
		this.perusahaan = perusahaan;
		this.nama = nama;
		this.kredensi = kredensi;
		this.status = status;
	}

	/**
	 * Create new instace with code but not persisted in database, and do not has id.
	 * @param kode cannot be null or empty String
	 * @param perusahaan
	 * @param nama
	 * @param kredensi
	 * @param status
	 * @throws EmptyCodeException kode is null or an empty String
	 */
	public PegawaiEntity(String kode, PerusahaanEntity perusahaan, String nama, KredensiValue kredensi, Status status) throws EmptyCodeException {
		this(perusahaan, nama, kredensi, status);
		setKode(kode);
	}

	/**
	 * Create instance already persisting in database and already has id and code.
	 * @param id must be positive
	 * @param kode cannot be null or an empty String
	 * @param perusahaan
	 * @param nama
	 * @param kredensi
	 * @param status
	 * @throws EmptyCodeException kode is null or an empty String
	 * @throws EmptyIdException id is 0 or negative
	 */
	public PegawaiEntity(int id, String kode, PerusahaanEntity perusahaan, String nama, KredensiValue kredensi, Status status) throws EmptyCodeException, EmptyIdException {
		this(perusahaan, nama, kredensi, status);
		setId(id);
		setKode(kode);
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

	@Override
	@Column(name = "status")
	public Status getStatus() {
		return status;
	}

	@Override
	public void setStatus(Status status) {
		this.status = status;
	}

	@Override
	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_perusahaan", referencedColumnName = "id")
	public PerusahaanEntity getPerusahaan() {
		return perusahaan;
	}

	/**
	 * Set {@link PerusahaanEntity} where employee works.
	 * @param perusahaan
	 */
	public void setPerusahaan(PerusahaanEntity perusahaan) {
		this.perusahaan = perusahaan;
	}

	@Override
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

	@Override
	@Embedded
	public KredensiValue getKredensi() {
		return kredensi;
	}

	/**
	 * Set employee's credential.
	 * @param kredensi
	 */
	public void setKredensi(KredensiValue kredensi) {
		this.kredensi = kredensi;
	}

	/**
	 * Return list of {@link PembayaranEntity} handled by pegawai.
	 * @return listPembayaran
	 */
	@JsonIgnore
	@OneToMany(targetEntity = PembayaranEntity.class, mappedBy = "pegawai", fetch = FetchType.LAZY, 
			cascade = {CascadeType.REFRESH, CascadeType.REMOVE})
	public List<PembayaranEntity> getListPembayaran() {
		return listPembayaran;
	}

	/**
	 * Set list of {@link PembayaranEntity} handled by employee.
	 * @param listPembayaran
	 */
	public void setListPembayaran(List<PembayaranEntity> listPembayaran) {
		this.listPembayaran = listPembayaran;
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

	@Override
	@JsonIgnore
	public PegawaiEntity toEntity() {
		return this;
	}

	@Override
	@JsonIgnore
	public PegawaiModel toModel() {
		return new PegawaiModel(this);
	}
	
	@Override
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
		
		setStatus(Status.REMOVED);
		try {
			setKode(String.format("REM%d", getId()));
		} catch (EmptyCodeException e) {
			e.printStackTrace();
		}
	}

	@Override
	@Transient
	@JsonIgnore
	public String generateKode(long count) {
		kode = String.format(DEFAULT_KODE_FORMAT, perusahaan.getKode(), (count + 1));

		return kode;
	}

	@Override
	@JsonIgnore
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
	@JsonIgnore
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		PegawaiEntity other = (PegawaiEntity) obj;
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
	@JsonIgnore
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
	public static final class KredensiValue implements Kredensi {
		/** 
		 * Whether operator or not.<br />
		 */
		private boolean operator;
		
		/** Username used to login */
		private String username;
		
		/** Password used to login */
		private String password;
		
		/** Role used for authorization */
		private Role role;

		/**
		 * Create empty instance.
		 */
		public KredensiValue() {
			super();
		}

		/**
		 * Create instance.
		 * @param isOperator
		 * @param username
		 * @param password
		 * @param role
		 */
		public KredensiValue(boolean isOperator, String username, String password, Role role) {
			super();
			this.operator = isOperator;
			this.username = username;
			this.password = password;
			this.role = role;
		}

		/**
		 * Whether operator or not.<br />
		 * Operator if {@link Role} is ADMIN or OPERATOR.
		 * @return operator
		 */
		@Column(name = "is_operator")
		public boolean isOperator() {
			return operator;
		}

		/**
		 * Set operator or not.
		 * @param operator
		 */
		public void setOperator(boolean operator) {
			this.operator = operator;
		}

		@Override
		@Column(name = "username")
		public String getUsername() {
			return username;
		}

		/**
		 * Set username user to login
		 * @param username
		 */
		public void setUsername(String username) {
			this.username = username;
		}

		@Override
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

		@Override
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

		@Override
		@JsonIgnore
		public KredensiValue toEntity() {
			return this;
		}

		@Override
		@JsonIgnore
		public Kredensi toModel() {
			// TODO Auto-generated method stub
			return null;
		}
	}
}
