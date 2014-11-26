package com.unitedvision.tvkabel.persistence.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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
import com.unitedvision.tvkabel.core.domain.Alamat;
import com.unitedvision.tvkabel.core.domain.Perusahaan;
import com.unitedvision.tvkabel.exception.EmptyCodeException;
import com.unitedvision.tvkabel.exception.EmptyIdException;
import com.unitedvision.tvkabel.exception.UncompatibleTypeException;
import com.unitedvision.tvkabel.web.model.PerusahaanModel;

/**
 * Mapping of perusahaan table in database.
 * 
 * @author Deddy Christoper Kakunsi
 *
 */
@Entity
@Table(name = "perusahaan")
public final class PerusahaanEntity extends CodableEntity implements Perusahaan {
	/** Name. */
	private String nama;

	/** Address */
	private AlamatValue alamat;
	
	/** Contact */
	private KontakValue kontak;
	
	/** Iuran */
	private long iuran;
	
	/** Status */
	private Status status;

	/** {@link KelurahanEntity} */
	private KelurahanEntity kelurahan;

	/** List {@link PelangganEntity} subscribing in company*/
	private List<PelangganEntity> listPelanggan;
	
	/** List of {@link PegawaiEntity} working for company*/
	private List<PegawaiEntity> listPegawai;

	/**
	 * Create empty instance.
	 */
	public PerusahaanEntity() {
		super();
	}

	/**
	 * Create new instance which is not persisted in database and does not has id.
	 * @param nama
	 * @param alamatValue
	 * @param kontakValue
	 * @param iuran
	 * @param status
	 */
	public PerusahaanEntity(String nama, AlamatValue alamatValue, KontakValue kontakValue, long iuran, Status status) {
		super();
		setNama(nama);
		setKontak(kontakValue);
		setAlamat(alamatValue);
		setIuran(iuran);
		setStatus(status);
	}

	/**
	 * Create new instance which has code.
	 * @param kode
	 * @param nama
	 * @param alamatValue
	 * @param kontakValue
	 * @param iuran
	 * @param status
	 * @throws EmptyCodeException {@code kode} is null or an empty string
	 */
	public PerusahaanEntity(String kode, String nama, AlamatValue alamatValue, KontakValue kontakValue, long iuran, Status status) throws EmptyCodeException {
		this(nama, alamatValue, kontakValue, iuran, status);
		setKode(kode);
	}

	/**
	 * Create new instance which is already persisted in database and already has id.
	 * @param id must be positive
	 * @param kode
	 * @param nama
	 * @param alamatValue
	 * @param kontakValue
	 * @param iuran
	 * @param status
	 * @throws EmptyIdException id is 0 or negative
	 * @throws EmptyCodeException {@code kode} is null or an empty string
	 */
	public PerusahaanEntity(int id, String kode, String nama, AlamatValue alamatValue, KontakValue kontakValue, long iuran, Status status) throws EmptyIdException, EmptyCodeException {
		this(nama, alamatValue, kontakValue, iuran, status);
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
	public AlamatValue getAlamat() {
		return alamat;
	}

	/**
	 * Set company's address.
	 * @param alamatValue
	 */
	public void setAlamat(AlamatValue alamatValue) {
		this.alamat = alamatValue;
		if (alamat != null)
			setKelurahan(alamatValue.getKelurahan());
	}

	@Override
	public void setAlamat(Alamat alamat) throws UncompatibleTypeException {
		if (!(alamat instanceof AlamatValue))
			throw new UncompatibleTypeException();
		setAlamat(alamat.toEntity());
	}

	@Override
	@Embedded
	public KontakValue getKontak() {
		return kontak;
	}

	/**
	 * Set company's contact.
	 * @param kontakValue
	 */
	public void setKontak(KontakValue kontakValue) {
		this.kontak = kontakValue;
	}
	
	@Override
	@Column(name = "iuran")
	public long getIuran() {
		return iuran;
	}

	/**
	 * Set company's iuran
	 * @param iuran
	 */
	public void setIuran(long iuran) {
		this.iuran = iuran;
	}

	@Override
	@Column(name = "status")
	public Status getStatus() {
		return status;
	}

	/**
	 * Set company's status.
	 * @param status
	 */
	public void setStatus(Status status) {
		this.status = status;
	}
	
	/**
	 * Return {@link KelurahanEntity} where company placed.
	 * @return kelurahan
	 */
	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_kelurahan", referencedColumnName = "id")
	public KelurahanEntity getKelurahan() {
		return kelurahan;
	}

	/**
	 * Set {@link KelurahanEntity} where company placed.
	 * @param kelurahanEntity
	 */
	public void setKelurahan(KelurahanEntity kelurahanEntity) {
		this.kelurahan = kelurahanEntity;
		this.alamat.setKelurahan(kelurahanEntity);
	}

	/**
	 * Return list of {@link PelangganEntity} subscribing to company.
	 * @return listPelanggan
	 */
	@JsonIgnore
	@OneToMany(targetEntity = PelangganEntity.class, mappedBy = "perusahaan", fetch = FetchType.LAZY,
			cascade = {CascadeType.REFRESH, CascadeType.REMOVE})
	public List<PelangganEntity> getListPelanggan() {
		return listPelanggan;
	}

	/**
	 * Set list of {@link PelangganEntity} subscribing to company.
	 * @param listPelanggan
	 */
	public void setListPelanggan(List<PelangganEntity> listPelanggan) {
		this.listPelanggan = listPelanggan;
	}

	/**
	 * Return list of {@link PegawaiEntity} working for company.
	 * @return listPegawai
	 */
	@JsonIgnore
	@OneToMany(targetEntity = PegawaiEntity.class, mappedBy = "perusahaan", fetch = FetchType.LAZY,
			cascade = {CascadeType.REFRESH, CascadeType.REMOVE})
	public List<PegawaiEntity> getListPegawai() {
		return listPegawai;
	}

	/**
	 * Set list of {@link PegawaiEntity} working for company.
	 * @param listPegawai
	 */
	public void setListPegawai(List<PegawaiEntity> listPegawai) {
		this.listPegawai = listPegawai;
	}
	
	@Override
	@JsonIgnore
	@Transient
	public String getNamaKelurahan() {
		return getAlamat().getKelurahan().getNama();
	}

	@Override
	@JsonIgnore
	@Transient
	public int getLingkungan() {
		return getAlamat().getLingkungan();
	}

	@Override
	@JsonIgnore
	@Transient
	public String getDetailAlamat() {
		return getAlamat().getDetailAlamat();
	}

	@Override
	@JsonIgnore
	@Transient
	public String getTelepon() {
		return getKontak().getTelepon();
	}

	@Override
	@JsonIgnore
	@Transient
	public String getHp() {
		return getKontak().getHp();
	}

	@Override
	@JsonIgnore
	@Transient
	public String getEmail() {
		return getKontak().getEmail();
	}

	@Override
	@JsonIgnore
	@Transient
	public String generateKode(long jumlahMaksimum) {
		kode = String.format(DEFAULT_KODE_FORMAT, (jumlahMaksimum + 1 ));
		
		return kode;
	}

	@Override
	@JsonIgnore
	public PerusahaanEntity toEntity() {
		return this;
	}

	@Override
	@JsonIgnore
	public PerusahaanModel toModel() {
		return new PerusahaanModel(this);
	}

	@Override
	@JsonIgnore
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((alamat == null) ? 0 : alamat.hashCode());
		result = prime * result
				+ ((kelurahan == null) ? 0 : kelurahan.hashCode());
		result = prime * result + ((kontak == null) ? 0 : kontak.hashCode());
		result = prime * result + ((nama == null) ? 0 : nama.hashCode());
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
		PerusahaanEntity other = (PerusahaanEntity) obj;
		if (alamat == null) {
			if (other.alamat != null)
				return false;
		} else if (!alamat.equals(other.alamat))
			return false;
		if (kelurahan == null) {
			if (other.kelurahan != null)
				return false;
		} else if (!kelurahan.equals(other.kelurahan))
			return false;
		if (kontak == null) {
			if (other.kontak != null)
				return false;
		} else if (!kontak.equals(other.kontak))
			return false;
		if (nama == null) {
			if (other.nama != null)
				return false;
		} else if (!nama.equals(other.nama))
			return false;
		return true;
	}

	@Override
	@JsonIgnore
	public String toString() {
		return "Perusahaan [nama=" + nama + ", alamat=" + alamat + ", kontak="
				+ kontak + ", kelurahan=" + kelurahan + "]";
	}
}
