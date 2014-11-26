package com.unitedvision.tvkabel.persistence.entity;

import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.unitedvision.tvkabel.core.domain.Alamat;
import com.unitedvision.tvkabel.core.domain.Comparable;
import com.unitedvision.tvkabel.core.domain.DetailPelanggan;
import com.unitedvision.tvkabel.core.domain.Kecamatan;
import com.unitedvision.tvkabel.core.domain.Kota;
import com.unitedvision.tvkabel.core.domain.Pelanggan;
import com.unitedvision.tvkabel.core.domain.Pembayaran;
import com.unitedvision.tvkabel.core.domain.Perusahaan;
import com.unitedvision.tvkabel.core.domain.Tagihan;
import com.unitedvision.tvkabel.exception.EmptyCodeException;
import com.unitedvision.tvkabel.exception.EmptyIdException;
import com.unitedvision.tvkabel.exception.StatusChangeException;
import com.unitedvision.tvkabel.exception.UncompatibleTypeException;
import com.unitedvision.tvkabel.persistence.entity.PembayaranEntity.TagihanValue;
import com.unitedvision.tvkabel.util.DateUtil;
import com.unitedvision.tvkabel.web.model.PelangganModel;

/**
 * Mapping of pelanggan table in database.
 * 
 * @author Deddy Christoper Kakunsi
 *
 */
@Entity
@Table(name = "pelanggan")
public final class PelangganEntity extends CodableEntity implements Pelanggan {
	/** {@link PerusahaanEntity} where customer subscribes */
	private PerusahaanEntity perusahaan;

	/** Name */
	private String nama;
	
	/** Profession */
	private String profesi;
	
	/** Address */
	private AlamatValue alamat;
	
	/** Contact */
	private KontakValue kontak;
	
	/** Detail */
	private Detail detail;
	
	/** Status */
	private Status status;

	/** {@link KelurahanEntity} where customer lives. */
	private KelurahanEntity kelurahan;

	/** List of P={@link PembayaranEntity} made by customer. */
	private List<PembayaranEntity> listPembayaran;

	/**
	 * Create empty instance.
	 */
	public PelangganEntity() {
		super();
	}

	/** 
	 * Create minimum instance containing only id.
	 * @param id must be positive
	 * @throws EmptyIdException {@code id} is 0 or negative
	 */
	@Deprecated
	public PelangganEntity(int id) throws EmptyIdException {
		super();
		setId(id);
	}

	/**
	 * Create a detached instance already persisting in database and has id.
	 * @param perusahaan
	 * @param nama
	 * @param profesi
	 * @param alamatValue
	 * @param kontakValue
	 * @param detail
	 * @param status
	 */
	public PelangganEntity(PerusahaanEntity perusahaan, String nama, String profesi, AlamatValue alamatValue, KontakValue kontakValue, Detail detail, Status status) {
		super();
		this.perusahaan = perusahaan;
		this.nama = nama;
		this.profesi = profesi;
		this.kontak = kontakValue;
		this.detail = detail;
		this.status = status;
		setAlamat(alamatValue);
	}

	/**
	 * Create a detached instance already persisting in database and has id.
	 * @param id must be positive
	 * @param perusahaan
	 * @param kode cannot be null or an empty string
	 * @param nama
	 * @param profesi
	 * @param alamatValue
	 * @param kontakValue
	 * @param detail
	 * @param status
	 * @throws EmptyIdException id is 0 or negative
	 * @throws EmptyCodeException {@code kode} is nill or an empty string
	 */
	public PelangganEntity(PerusahaanEntity perusahaan, String kode, String nama, String profesi, AlamatValue alamatValue, KontakValue kontakValue, Detail detail, Status status) throws EmptyCodeException {
		this(perusahaan, nama, profesi, alamatValue, kontakValue, detail, status);
		setKode(kode);
	}

	/**
	 * Create a detached instance already persisting in database and has id.
	 * @param id must be positive
	 * @param perusahaan
	 * @param kode cannot be null or an empty string
	 * @param nama
	 * @param profesi
	 * @param alamatValue
	 * @param kontakValue
	 * @param detail
	 * @param status
	 * @throws EmptyIdException id is 0 or negative
	 * @throws EmptyCodeException {@code kode} is nill or an empty string
	 */
	public PelangganEntity(int id, PerusahaanEntity perusahaan, String kode, String nama, String profesi, AlamatValue alamatValue, KontakValue kontakValue, Detail detail, Status status) throws EmptyIdException, EmptyCodeException {
		this(perusahaan, nama, profesi, alamatValue, kontakValue, detail, status);
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
	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_perusahaan", referencedColumnName = "id")
	public PerusahaanEntity getPerusahaan() {
		return perusahaan;
	}

	/**
	 * Set {@link PerusahaanEntity} which customer subscribes.
	 * @param perusahaan
	 */
	public void setPerusahaan(PerusahaanEntity perusahaan) {
		this.perusahaan = perusahaan;
	}

	@Override
	public void setPerusahaan(Perusahaan perusahaan) throws UncompatibleTypeException {
		if (!(perusahaan instanceof Perusahaan))
			throw new UncompatibleTypeException();
		setPerusahaan(perusahaan.toEntity());
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
	@Column(name = "profesi")
	public String getProfesi() {
		return profesi;
	}

	/**
	 * Set profession.
	 * @param profesi
	 */
	public void setProfesi(String profesi) {
		this.profesi = profesi;
	}
	
	@Override
	@Embedded
	public AlamatValue getAlamat() {
		return alamat;
	}

	/**
	 * Set {@link ALamatValue} where customer lives.
	 * @param alamatValue
	 */
	public void setAlamat(AlamatValue alamatValue) {
		this.alamat = alamatValue;
		if (alamatValue != null)
			setKelurahan(alamat.getKelurahan());
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
	 * Set customer's contact.
	 * @param kontakValue
	 */
	public void setKontak(KontakValue kontakValue) {
		this.kontak = kontakValue;
	}

	@Override
	@Embedded
	public Detail getDetail() {
		return detail;
	}

	/**
	 * Set detail.
	 * @param detail
	 */
	public void setDetail(Detail detail) {
		this.detail = detail;
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

	/**
	 * Return {@link KelurahanEntity} where customer lives.
	 * @return
	 */
	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_kelurahan", referencedColumnName = "id")
	public KelurahanEntity getKelurahan() {
		return kelurahan;
	}

	/**
	 * Set {@link KelurahanEntity} where customer lives.
	 * @param kelurahanEntity
	 */
	public void setKelurahan(KelurahanEntity kelurahanEntity) {
		this.kelurahan = kelurahanEntity;
		alamat.setKelurahan(kelurahanEntity);
	}

	/**
	 * Return list of {@link PebayaranEntity} made by customer.
	 * @return listPembayaran
	 */
	@JsonIgnore
	@OneToMany(targetEntity = PembayaranEntity.class, mappedBy = "pelanggan", fetch = FetchType.LAZY,
			cascade = {CascadeType.REMOVE})
	public List<PembayaranEntity> getListPembayaran() {
		return listPembayaran;
	}

	@Override
	public void setListPembayaran(List<PembayaranEntity> listPembayaran) {
		this.listPembayaran = listPembayaran;
	}

	@Override
	@JsonIgnore
	@Transient
	public Kecamatan getKecamatan() {
		return getAlamat().getKecamatan();
	}

	@Override
	@JsonIgnore
	@Transient
	public Kota getKota() {
		return getAlamat().getKota();
	}

	@Override
	@JsonIgnore
	@Transient
	public String getNamaKelurahan() {
		return getKelurahan().getNama();
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
	public Date getTanggalMulai() {
		return getDetail().getTanggalMulai();
	}

	@Override
	@JsonIgnore
	@Transient
	public int getJumlahTv() {
		return getDetail().getJumlahTv();
	}

	@Override
	@JsonIgnore
	@Transient
	public long getIuran() {
		return getDetail().getIuran();
	}

	@Override
	@JsonIgnore
	@Transient
	public int getTunggakan() {
		return getDetail().getTunggakan();
	}
	
	@Override
	public void setTunggakan(int tunggakan) {
		getDetail().setTunggakan(tunggakan);
	}

	@Override
	@JsonIgnore
	public int countTunggakan(Pembayaran pembayaranTerakhir) {
		if (pembayaranTerakhir == null)
			return countTunggakan();
		return countTunggakan(pembayaranTerakhir.getTagihan());
	}

	@Override
	@JsonIgnore
	public int countTunggakan() {
		Tagihan def = TagihanValue.create(detail.getTanggalMulai());

		return countTunggakan(def);
	}
	
	@Override
	@JsonIgnore
	public int countTunggakan(Tagihan tagihan) {
		Tagihan now = TagihanValue.create(DateUtil.getNow());
		detail.setTunggakan(((Comparable)now).compareWith(tagihan.toEntity()));
		
		return detail.getTunggakan();
	}

	@Override
	@JsonIgnore
	public PelangganEntity toEntity() {
		return this;
	}

	@Override
	@JsonIgnore
	public PelangganModel toModel() {
		return new PelangganModel(this);
	}

	@Override
	public void remove() throws StatusChangeException {
		if (isNew())
			throw new StatusChangeException("Pelanggan is new");
			
		setStatus(Status.REMOVED);
		try {
			setKode(String.format("REM%d", getId()));
		} catch (EmptyCodeException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	@JsonIgnore
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((alamat == null) ? 0 : alamat.hashCode());
		result = prime * result + ((detail == null) ? 0 : detail.hashCode());
		result = prime * result
				+ ((kelurahan == null) ? 0 : kelurahan.hashCode());
		result = prime * result + ((kontak == null) ? 0 : kontak.hashCode());
		result = prime * result + ((nama == null) ? 0 : nama.hashCode());
		result = prime * result
				+ ((perusahaan == null) ? 0 : perusahaan.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
		PelangganEntity other = (PelangganEntity) obj;
		if (alamat == null) {
			if (other.alamat != null)
				return false;
		} else if (!alamat.equals(other.alamat))
			return false;
		if (detail == null) {
			if (other.detail != null)
				return false;
		} else if (!detail.equals(other.detail))
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
		if (perusahaan == null) {
			if (other.perusahaan != null)
				return false;
		} else if (!perusahaan.equals(other.perusahaan))
			return false;
		if (status != other.status)
			return false;
		return true;
	}

	@Override
	@JsonIgnore
	public String toString() {
		return "Pelanggan [perusahaan=" + perusahaan + ", nama=" + nama
				+ ", alamat=" + alamat + ", kontak=" + kontak + ", detail="
				+ detail + ", status=" + status + ", kelurahan=" + kelurahan
				+ "]";
	}

	/**
	 * Customer's detail.
	 * 
	 * @author Deddy Christoper Kakunsi
	 *
	 */
	@Embeddable
	public static final class Detail implements DetailPelanggan {
		/** Subscription start date */
		private Date tanggalMulai;
		
		/** Number of television */
		private int jumlahTv;
		
		/** Monthly bill */
		private long iuran;
		
		/** Debt */
		private int tunggakan;
		
		/**
		 * Create empty instance.
		 */
		public Detail() {
			super();
		}

		/**
		 * Create instance.
		 * @param tanggalMulai
		 * @param jumlahTv
		 * @param iuranBulanan
		 * @param tunggakan
		 */
		public Detail(Date tanggalMulai, int jumlahTv, long iuranBulanan, int tunggakan) {
			super();
			this.tanggalMulai = tanggalMulai;
			this.jumlahTv = jumlahTv;
			this.iuran = iuranBulanan;
			this.tunggakan = tunggakan;
		}

		@Override
		@Temporal(TemporalType.DATE)
		@Column(name = "tanggal_mulai")
		public Date getTanggalMulai() {
			return tanggalMulai;
		}

		@Override
		public void setTanggalMulai(Date tanggalMulai) {
			this.tanggalMulai = tanggalMulai;
		}

		@Override
		@Column(name = "jumlah_tv")
		public int getJumlahTv() {
			return jumlahTv;
		}

		/**
		 * Set number of television
		 * @param jumlahTv
		 */
		public void setJumlahTv(int jumlahTv) {
			this.jumlahTv = jumlahTv;
		}

		@Override
		@Column(name = "iuran")
		public long getIuran() {
			return iuran;
		}

		/**
		 * Set monthly bill.
		 * @param iuran
		 */
		public void setIuran(long iuran) {
			this.iuran = iuran;
		}

		@Override
		@Column(name = "tunggakan")
		public int getTunggakan() {
			return tunggakan;
		}

		@Override
		public void setTunggakan(int tunggakan) {
			this.tunggakan = tunggakan;
		}

		@Override
		@JsonIgnore
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + (int) (iuran ^ (iuran >>> 32));
			result = prime * result + jumlahTv;
			result = prime * result
					+ ((tanggalMulai == null) ? 0 : tanggalMulai.hashCode());
			result = prime * result + tunggakan;
			return result;
		}

		@Override
		@JsonIgnore
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Detail other = (Detail) obj;
			if (iuran != other.iuran)
				return false;
			if (jumlahTv != other.jumlahTv)
				return false;
			if (tanggalMulai == null) {
				if (other.tanggalMulai != null)
					return false;
			} else if (!tanggalMulai.equals(other.tanggalMulai))
				return false;
			if (tunggakan != other.tunggakan)
				return false;
			return true;
		}

		@Override
		@JsonIgnore
		public String toString() {
			return "Detail [tanggalMulai=" + tanggalMulai + ", jumlahTv="
					+ jumlahTv + ", iuran=" + iuran + ", tunggakan="
					+ tunggakan + "]";
		}

		@Override
		@JsonIgnore
		public Detail toEntity() {
			return this;
		}

		@Override
		@JsonIgnore
		public DetailPelanggan toModel() {
			// TODO Auto-generated method stub
			return null;
		}
	}
}
