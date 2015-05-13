package com.unitedvision.tvkabel.entity;

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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.unitedvision.tvkabel.entity.Pembayaran.Tagihan;
import com.unitedvision.tvkabel.exception.EmptyCodeException;
import com.unitedvision.tvkabel.exception.EmptyIdException;
import com.unitedvision.tvkabel.exception.StatusChangeException;
import com.unitedvision.tvkabel.util.DateUtil;

/**
 * Pelanggan domain.
 * 
 * @author Deddy Christoper Kakunsi
 *
 */
@Entity
@Table(name = "pelanggan")
public final class Pelanggan extends CodableDomain implements Removable {
	
	/** Registration number */
	private String nomorBuku;
	
	/** {@link Perusahaan} where customer subscribes */
	private Perusahaan perusahaan;

	/** Name */
	private String nama;
	
	/** Profession */
	private String profesi;
	
	/** Address */
	private Alamat alamat;
	
	/** Contact */
	private Kontak kontak;
	
	/** Detail */
	private Detail detail;
	
	/** Status */
	private Status status;
	
	private Alat source;

	/** List of {@link Pembayaran} made by customer. */
	private List<Pembayaran> listPembayaran;
	
	private List<History> listHistory;

	/**
	 * Pembayaran terakhir, set object ini setelah melakukan proses pembayaran.
	 */
	private Pembayaran pembayaranTerakhir;

	/**
	 * Create empty instance.
	 */
	public Pelanggan() {
		super();
	}

	/**
	 * Create instance.
	 * @param id must be positive
	 * @param perusahaan
	 * @param kode cannot be null or an empty string
	 * @param nama
	 * @param profesi
	 * @param kelurahan
	 * @param alamat
	 * @param kontak
	 * @param detail
	 * @param status
	 * @throws EmptyIdException {@code id} is negative.
	 * @throws EmptyCodeException {@code kode} is null or an empty string
	 */
	public Pelanggan(int id, String nomorBuku, Perusahaan perusahaan, String kode, String nama, String profesi, Kelurahan kelurahan, Alamat alamat, Kontak kontak, Detail detail, Status status) throws EmptyIdException, EmptyCodeException {
		super();
		setId(id);
		setNomorBuku(nomorBuku);
		setPerusahaan(perusahaan);
		setKode(kode);
		setNama(nama);
		setProfesi(profesi);
		setAlamat(alamat);
		setKontak(kontak);
		setDetail(detail);
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
	 * Return registration number.
	 * @return
	 */
	@Column(name = "nomor_buku")
	public String getNomorBuku() {
		return nomorBuku;
	}

	/**
	 * Set registration number.
	 * @param nomorBuku
	 */
	public void setNomorBuku(String nomorBuku) {
		this.nomorBuku = nomorBuku;
	}

	/**
	 * Return {@link Perusahaan} instance.
	 * @return perusahaan
	 */
	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_perusahaan", referencedColumnName = "id")
	public Perusahaan getPerusahaan() {
		return perusahaan;
	}

	/**
	 * Set {@link Perusahaan} which customer subscribes.
	 * @param perusahaan
	 */
	public void setPerusahaan(Perusahaan perusahaan) {
		this.perusahaan = perusahaan;
	}

	/**
	 * Return name.
	 * @return nama
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
	 * Return profession.
	 * @return profesi
	 */
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

	/**
	 * Return {@link Alamat} instance.
	 * @return alamat
	 */
	@Embedded
	public Alamat getAlamat() {
		return alamat;
	}

	/**
	 * Set {@link Alamat} where customer lives.
	 * @param alamat
	 */
	public void setAlamat(Alamat alamat) {
		this.alamat = alamat;
	}
	
	/**
	 * Return {@link Kontak} instance.
	 * @return kontak
	 */
	@Embedded
	public Kontak getKontak() {
		return kontak;
	}

	/**
	 * Set {@link Kontak} instance.
	 * @param kontak
	 */
	public void setKontak(Kontak kontak) {
		this.kontak = kontak;
	}
	
	/**
	 * Return {@link Detail} instance.
	 * @return detail
	 */
	@Embedded
	public Detail getDetail() {
		return detail;
	}

	/**
	 * Set {@link Detail} instance.
	 * @param detail
	 */
	public void setDetail(Detail detail) {
		this.detail = detail;
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
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_source", referencedColumnName = "id")
	public Alat getSource() {
		return source;
	}

	public void setSource(Alat source) {
		this.source = source;
	}

	/**
	 * Return list of {@link Pembayaran} made by customer.
	 * @return listPembayaran
	 */
	@JsonIgnore
	@OneToMany(targetEntity = Pembayaran.class, mappedBy = "pelanggan", fetch = FetchType.LAZY,
			cascade = CascadeType.REFRESH)
	public List<Pembayaran> getListPembayaran() {
		return listPembayaran;
	}

	/**
	 * Set list of {@link Pembayaran} instances.
	 * @param listPembayaran
	 */
	public void setListPembayaran(List<Pembayaran> listPembayaran) {
		this.listPembayaran = listPembayaran;
	}
	
	public Pembayaran addPembayaran(Pembayaran pembayaran) {
		getListPembayaran().add(pembayaran);
		pembayaran.setPelanggan(this);
		
		return pembayaran;
	}
	
	public Pembayaran removePembayaran(Pembayaran pembayaran) {
		getListPembayaran().remove(pembayaran);
		pembayaran.setPelanggan(null);
		
		return pembayaran;
	}

	/**
	 * Return daftar history pelanggan.
	 * History seputar perubahan status.
	 * @return
	 */
	@JsonIgnore
	@OneToMany(targetEntity = History.class, mappedBy = "pelanggan", fetch = FetchType.LAZY,
			cascade = CascadeType.REFRESH)
	public List<History> getListHistory() {
		return listHistory;
	}

	public void setListHistory(List<History> listHistory) {
		this.listHistory = listHistory;
	}
	
	public History addHistory(History history) {
		getListHistory().add(history);
		history.setPelanggan(this);
		
		return history;
	}
	
	public History removeHistory(History history) {
		getListHistory().remove(history);
		history.setPelanggan(null);
		
		return history;
	}

	/**
	 * Return {@code pembayaranTerakhir}.
	 * @return
	 */
	@JsonIgnore
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "pembayaran_terakhir", referencedColumnName = "id")
	public Pembayaran getPembayaranTerakhir() {
		return pembayaranTerakhir;
	}

	public void setPembayaranTerakhir(Pembayaran pembayaranTerakhir) {
		this.pembayaranTerakhir = pembayaranTerakhir;
	}
	
	@JsonIgnore
	@Transient
	public int getIdPembayaranTerakhir() {
		if (pembayaranTerakhir != null)
			return pembayaranTerakhir.getId();
		return 0;
	}

	/**
	 * Return {@link Kelurahan} where customer lives.
	 * @return
	 */
	@JsonIgnore
	@Transient
	public Kelurahan getKelurahan() {
		return alamat.getKelurahan();
	}

	@JsonIgnore
	@Transient
	public String getNamaKelurahan() {
		return getKelurahan().getNama();
	}
	
	@JsonIgnore
	@Transient
	public int getIdKelurahan() {
		return getKelurahan().getId();
	}
	
	@JsonIgnore
	@Transient
	public String getNamaKecamatan() {
		return getKelurahan().getNamaKecamatan();
	}
	
	@JsonIgnore
	@Transient
	public int getIdKecamatan() {
		return getKelurahan().getIdKecamatan();
	}
	
	@JsonIgnore
	@Transient
	public String getNamaKota() {
		return getKelurahan().getNamaKota();
	}
	
	@JsonIgnore
	@Transient
	public int getIdKota() {
		return getKelurahan().getIdKota();
	}
	
	@JsonIgnore
	@Transient
	public int getLingkungan() {
		return alamat.getLingkungan();
	}
	
	@JsonIgnore
	@Transient
	public String getDetailAlamat() {
		return alamat.getDetailAlamat();
	}
	
	@JsonIgnore
	@Transient
	public Location getLokasi() {
		return alamat.getLokasi();
	}
	
	@JsonIgnore
	@Transient
	public float getLatitude() {
		return getLokasi().getLatitude();
	}
	
	@JsonIgnore
	@Transient
	public float getLongitude() {
		return getLokasi().getLongitude();
	}
	
	@JsonIgnore
	@Transient
	public String getTelepon() {
		return kontak.getTelepon();
	}
	
	@JsonIgnore
	@Transient
	public String getHp() {
		return kontak.getHp();
	}
	
	@JsonIgnore
	@Transient
	public String getEmail() {
		return kontak.getEmail();
	}
	
	@JsonIgnore
	@Transient
	public Date getTanggalMulai() {
		return detail.getTanggalMulai();
	}
	
	@Transient
	public String getTanggalMulaiStr() {
		return detail.getTanggalMulaiStr();
	}

	@JsonIgnore
	@Transient
	public int getTunggakan() {
		return detail.getTunggakan();
	}

	public void setTunggakan(Integer tunggakan) {
		detail.setTunggakan(tunggakan);
	}

	@JsonIgnore
	@Transient
	public long getIuran() {
		return detail.getIuran();
	}

	@JsonIgnore
	@Transient
	public int getJumlahTv() {
		return detail.getJumlahTv();
	}

	public int countTunggakan() {
		if (pembayaranTerakhir == null)
			return countDefaultTunggakan();
		return countTunggakan(pembayaranTerakhir.getTagihan());
	}

	public int countDefaultTunggakan() {
		Tagihan def = Tagihan.create(detail.getTanggalMulai());

		return countTunggakan(def);
	}
	
	public int countTunggakan(Tagihan tagihan) {
		Tagihan now = Tagihan.create(DateUtil.getNow());
		detail.setTunggakan(((Comparable)now).compareWith(tagihan));
		
		return detail.getTunggakan();
	}

	public void activate() throws StatusChangeException {
		if (isNew())
			throw new StatusChangeException("Pelanggan is new");
		
		if (status.equals(Status.AKTIF))
			throw new StatusChangeException("Tidak mengaktivasi pelanggan. Karena pelanggan merupakan pelanggan aktif");

		setStatus(Status.AKTIF);
		getDetail().setTanggalMulai(DateUtil.getNow());
		getDetail().setTunggakan(0);
	}
	
	public void passivate() throws StatusChangeException {
		if (isNew())
			throw new StatusChangeException("Pelanggan is new");
		
		if (status.equals(Status.BERHENTI))
			throw new StatusChangeException("Tidak memutuskan pelanggan. Karena pelanggan merupakan pelanggan berhenti");

		setStatus(Status.BERHENTI);
	}

	public void ban() throws StatusChangeException {
		if (isNew())
			throw new StatusChangeException("Pelanggan is new");
		
		if (status.equals(Status.PUTUS))
			throw new StatusChangeException("Tidak mem-banned pelanggan. Karena pelanggan merupakan pelanggan putus");

		setStatus(Status.PUTUS);

		countTunggakan();
	}
	
	@Override
	public void remove() throws StatusChangeException {
		if (isNew())
			throw new StatusChangeException("Pelanggan is new");
		
		if (status.equals(Status.REMOVED))
			throw new StatusChangeException("Pelanggan was already removed");
			
		setStatus(Status.REMOVED);

		try {
			setKode(String.format("REM%d", getId()));
		} catch (EmptyCodeException e) {
			e.printStackTrace();
		}
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
		Pelanggan other = (Pelanggan) obj;
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
				+ detail + ", status=" + status + "]";
	}

	/**
	 * Customer's detail.
	 * 
	 * @author Deddy Christoper Kakunsi
	 *
	 */
	@Embeddable
	public static final class Detail {
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
			setTanggalMulai(tanggalMulai);
			setJumlahTv(jumlahTv);
			setIuran(iuranBulanan);
			setTunggakan(tunggakan);
		}

		/**
		 * Return start date.
		 * @return tanggalMulai
		 */
		@JsonIgnore
		@Temporal(TemporalType.DATE)
		@Column(name = "tanggal_mulai")
		public Date getTanggalMulai() {
			return tanggalMulai;
		}

		/**
		 * Set start date.
		 * @param tanggalMulai
		 */
		public void setTanggalMulai(Date tanggalMulai) {
			this.tanggalMulai = tanggalMulai;
		}
		
		/**
		 * Return start date in string format.
		 * @return
		 */
		@Transient
		public String getTanggalMulaiStr() {
			return DateUtil.toUserString(tanggalMulai, "/");
		}

		/**
		 * Set tanggal mulai with string format.
		 * @param tanggalMulaiStr
		 */
		public void setTanggalMulaiStr(String tanggalMulaiStr) {
			this.tanggalMulai = DateUtil.getDate(tanggalMulaiStr, "/");
		}

		/**
		 * Return number of television.
		 * @return jumlahTv
		 */
		@Column(name = "jumlah_tv")
		public int getJumlahTv() {
			return jumlahTv;
		}

		/**
		 * Set number of television.
		 * @param jumlahTv
		 */
		public void setJumlahTv(int jumlahTv) {
			this.jumlahTv = jumlahTv;
		}

		/**
		 * Return monthly bill.
		 * @return iuran
		 */
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

		/**
		 * Set debt.
		 * @return tunggakan
		 */
		@Column(name = "tunggakan")
		public int getTunggakan() {
			return tunggakan;
		}

		/**
		 * Set debt.
		 * @param tunggakan
		 */
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
	}

	/**
	 * Pelanggan status
	 * 
	 * @author Deddy Christoper Kakunsi
	 *
	 */
	public enum Status {
		/** AKTIF */
		AKTIF,
		/** PUTUS */
		BERHENTI,
		/** HUTANG */
		PUTUS,
		/** REMOVED from database (not deleted) */
		REMOVED,
		/** FREE OF CHARGE */
		GRATIS;
		
		/**
		 * Returns {@link Status} from the given string.
		 * @param status
		 * @return {@link Status}.
		 */
		public static Status get(String status) {
			status = status.toUpperCase();
			
			return Pelanggan.Status.valueOf(status);
		}
	}

}
