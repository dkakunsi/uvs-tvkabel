package com.unitedvision.tvkabel.domain.entity;

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
import com.unitedvision.tvkabel.exception.EmptyCodeException;
import com.unitedvision.tvkabel.exception.EmptyIdException;

/**
 * Perusahaan domain.
 * 
 * @author Deddy Christoper Kakunsi
 *
 */
@Entity
@Table(name = "perusahaan")
public final class Perusahaan extends CodableDomain {
	/** Name. */
	private String nama;

	/** Address */
	private Alamat alamat;
	
	/** Contact */
	private Kontak kontak;
	
	/** Iuran */
	private long iuran;
	
	/** Status */
	private Status status;

	/** {@link Kelurahan} instance */
	private Kelurahan kelurahan;

	/** List {@link Pelanggan} instances */
	private List<Pelanggan> listPelanggan;
	
	/** List of {@link Pegawai} instances */
	private List<Pegawai> listPegawai;

	/** DEFAULT KODE FORMAT */
	public static final String DEFAULT_KODE_FORMAT = "COM%d";

	/**
	 * Create instance.
	 */
	public Perusahaan() {
		super();
	}

	/**
	 * Create new instance which is not persisted in database and does not has id.
	 * @param nama
	 * @param alamat
	 * @param kontak
	 * @param iuran
	 * @param status
	 */
	public Perusahaan(String nama, Alamat alamat, Kontak kontak, long iuran, Status status) {
		super();
		setNama(nama);
		setKontak(kontak);
		setAlamat(alamat);
		setIuran(iuran);
		setStatus(status);
	}

	/**
	 * Create instance.
	 * @param kode
	 * @param nama
	 * @param alamat
	 * @param kontak
	 * @param iuran
	 * @param status
	 * @throws EmptyCodeException {@code kode} is null or an empty string
	 */
	public Perusahaan(String kode, String nama, Alamat alamat, Kontak kontak, long iuran, Status status) throws EmptyCodeException {
		this(nama, alamat, kontak, iuran, status);
		setKode(kode);
	}

	/**
	 * Create instance.
	 * @param id must be positive
	 * @param kode
	 * @param nama
	 * @param alamat
	 * @param kontak
	 * @param iuran
	 * @param status
	 * @throws EmptyIdException id is 0 or negative
	 * @throws EmptyCodeException {@code kode} is null or an empty string
	 */
	public Perusahaan(int id, String kode, String nama, Alamat alamat, Kontak kontak, long iuran, Status status) throws EmptyIdException, EmptyCodeException {
		this(nama, alamat, kontak, iuran, status);
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

	/**
	 * Return name.
	 * @return name
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
	 * Return {@link ALamat} instance.
	 * @return alamat
	 */
	@JsonIgnore
	@Embedded
	public Alamat getAlamat() {
		return alamat;
	}

	/**
	 * Set {@link Alamat} instance.
	 * @param alamat
	 */
	public void setAlamat(Alamat alamat) {
		this.alamat = alamat;
		if (alamat != null)
			setKelurahan(alamat.getKelurahan());
	}
	
	/**
	 * If alamat is null, create new one.
	 */
	private void setAlamat() {
		if (alamat == null)
			alamat = new Alamat();
	}

	/**
	 * Return {@link Kontak} instance.
	 * @return
	 */
	@JsonIgnore
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
	 * if kontak is null, create a new one.
	 */
	private void setKontak() {
		if (kontak == null)
			kontak = new Kontak();
	}

	/**
	 * Return iuran.
	 * @return iuran
	 */
	@Column(name = "iuran")
	public long getIuran() {
		return iuran;
	}

	/**
	 * Set iuran
	 * @param iuran
	 */
	public void setIuran(long iuran) {
		this.iuran = iuran;
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
	 * Set company{@link Status} instance.
	 * @param status
	 */
	public void setStatus(Status status) {
		this.status = status;
	}
	
	/**
	 * Return {@link Kelurahan} instance.
	 * @return kelurahan
	 */
	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_kelurahan", referencedColumnName = "id")
	public Kelurahan getKelurahan() {
		return kelurahan;
	}

	/**
	 * Set {@link Kelurahan} instance.
	 * @param kelurahan
	 */
	public void setKelurahan(Kelurahan kelurahan) {
		this.kelurahan = kelurahan;
		this.alamat.setKelurahan(kelurahan);
	}

	/**
	 * if keluraha is null, create a new one.
	 */
	private void setKelurahan() {
		if (kelurahan == null)
			kelurahan = new Kelurahan();
	}
	
	/**
	 * Return list of {@link Pelanggan} instances.
	 * @return listPelanggan
	 */
	@JsonIgnore
	@OneToMany(targetEntity = Pelanggan.class, mappedBy = "perusahaan", fetch = FetchType.LAZY,
			cascade = {CascadeType.REFRESH, CascadeType.REMOVE})
	public List<Pelanggan> getListPelanggan() {
		return listPelanggan;
	}

	/**
	 * Set list of {@link Pelanggan} instances.
	 * @param listPelanggan
	 */
	public void setListPelanggan(List<Pelanggan> listPelanggan) {
		this.listPelanggan = listPelanggan;
	}

	/**
	 * Return list of {@link Pegawai} instances.
	 * @return listPegawai
	 */
	@JsonIgnore
	@OneToMany(targetEntity = Pegawai.class, mappedBy = "perusahaan", fetch = FetchType.LAZY,
			cascade = {CascadeType.REFRESH, CascadeType.REMOVE})
	public List<Pegawai> getListPegawai() {
		return listPegawai;
	}

	/**
	 * Set list of {@link Pegawai} instances.
	 * @param listPegawai
	 */
	public void setListPegawai(List<Pegawai> listPegawai) {
		this.listPegawai = listPegawai;
	}

	@Transient
	public String getNamaKelurahan() {
		return kelurahan.getNama();
	}
	
	public void setNamaKelurahan(String namaKelurahan) {
		setKelurahan();
		kelurahan.setNama(namaKelurahan);
	}
	
	@Transient
	public int getIdKelurahan() {
		return kelurahan.getId();
	}

	public void setIdKelurahan(int idKelurahan) throws EmptyIdException {
		setKelurahan();
		kelurahan.setId(idKelurahan);
	}
	
	@Transient
	public String getNamaKecamatan() {
		return kelurahan.getNamaKecamatan();
	}
	
	public void setNamaKecamatan(String namaKecamatan) {
		setKelurahan();
		kelurahan.setNamaKecamatan(namaKecamatan);
	}

	@Transient
	public int getIdKecamatan() {
		return kelurahan.getIdKecamatan();
	}
	
	public void setIdKecamatan(int idKecamatan) throws EmptyIdException {
		setKelurahan();
		kelurahan.setIdKecamatan(idKecamatan);
	}
	
	@Transient
	public String getNamaKota() {
		return kelurahan.getNamaKota();
	}
	
	public void setNamaKota(String namaKota) {
		setKelurahan();
		kelurahan.setNamaKota(namaKota);
	}

	@Transient
	public int getIdKota() {
		return kelurahan.getIdKota();
	}
	
	public void setIdKota(int idKota) throws EmptyIdException {
		setKelurahan();
		kelurahan.setIdKota(idKota);
	}

	@Transient
	public int getLingkungan() {
		return alamat.getLingkungan();
	}
	
	public void setLingkungan(int lingkungan) {
		setAlamat();
		alamat.setLingkungan(lingkungan);
	}

	@Transient
	public String getDetailAlamat() {
		return alamat.getDetailAlamat();
	}
	
	public void setDetailAlamat(String detailAlamat) {
		setAlamat();
		alamat.setDetailAlamat(detailAlamat);
	}
	
	@Transient
	public float getLatitude() {
		return alamat.getLatitude();
	}
	
	public void setLatitude(float latitude) {
		setAlamat();
		alamat.setLatitude(latitude);
	}

	@Transient
	public float getLongitude() {
		return alamat.getLongitude();
	}
	
	public void setLongitude(float longitude) {
		setAlamat();
		alamat.setLongitude(longitude);
	}

	@Transient
	public String getTelepon() {
		return kontak.getTelepon();
	}
	
	public void setTelepon(String telepon) {
		setKontak();
		kontak.setTelepon(telepon);
	}

	@Transient
	public String getHp() {
		return kontak.getHp();
	}
	
	public void setHp(String hp) {
		setKontak();
		kontak.setHp(hp);
	}

	@Transient
	public String getEmail() {
		return kontak.getEmail();
	}
	
	public void setEmail(String email) {
		setKontak();
		kontak.setEmail(email);
	}

	@JsonIgnore
	@Transient
	public String generateKode(long jumlahMaksimum) {
		kode = String.format(DEFAULT_KODE_FORMAT, (jumlahMaksimum + 1 ));
		
		return kode;
	}

	public static Rekap createRekap(long estimasiPemasukanBulanan, long estimasiTagihanBulanan, long pemasukanBulanBerjalan, long tagihanBulanBerjalan, long totalAkumulasiTunggakan, long jumlahPelangganAktif, long jumlahPelangganBerhenti, long jumlahPelangganPutus, long jumlahPelangganLunas, long jumlahPelangganMenunggakWajar, long jumlahPelangganMenunggakTakWajar) {
		return new Rekap(estimasiPemasukanBulanan, pemasukanBulanBerjalan, estimasiTagihanBulanan, tagihanBulanBerjalan, totalAkumulasiTunggakan, jumlahPelangganAktif, jumlahPelangganBerhenti, jumlahPelangganPutus, jumlahPelangganLunas, jumlahPelangganMenunggakWajar, jumlahPelangganMenunggakTakWajar);
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
		Perusahaan other = (Perusahaan) obj;
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
	
	/**
	 * Perusahaan status.
	 * @author Deddy Christoper Kakunsi
	 *
	 */
	public enum Status {
		/** AKTIF */
		AKTIF, 
		/** BANNED */
		BANNED
	}
	
	/**
	 * Class for Perusahaan rekap.
	 * 
	 * @author Deddy Christoper Kakunsi
	 *
	 */
	public static class Rekap {
		private long estimasiPemasukanBulanan;
		private long pemasukanBulanBerjalan;
		private long estimasiTagihanBulanan;
		private long tagihanBulanBerjalan;
		private long totalAkumulasiTunggakan;
		
		private long jumlahPelangganAktif;
		private long jumlahPelangganBerhenti;
		private long jumlahPelangganPutus;
		private long jumlahPelangganLunas;
		private long jumlahPelangganMenunggakWajar;
		private long jumlahPelangganMenunggakTakWajar;

		public Rekap(long estimasiPemasukanBulanan,
				long pemasukanBulanBerjalan, long estimasiTagihanBulanan,
				long tagihanBulanBerjalan, long totalAkumulasiTunggakan,
				long jumlahPelangganAktif, long jumlahPelangganBerhenti,
				long jumlahPelangganPutus, long jumlahPelangganLunas,
				long jumlahPelangganMenunggakWajar,
				long jumlahPelangganMenunggakTakWajar) {
			super();
			this.estimasiPemasukanBulanan = estimasiPemasukanBulanan;
			this.pemasukanBulanBerjalan = pemasukanBulanBerjalan;
			this.estimasiTagihanBulanan = estimasiTagihanBulanan;
			this.tagihanBulanBerjalan = tagihanBulanBerjalan;
			this.totalAkumulasiTunggakan = totalAkumulasiTunggakan;
			this.jumlahPelangganAktif = jumlahPelangganAktif;
			this.jumlahPelangganBerhenti = jumlahPelangganBerhenti;
			this.jumlahPelangganPutus = jumlahPelangganPutus;
			this.jumlahPelangganLunas = jumlahPelangganLunas;
			this.jumlahPelangganMenunggakWajar = jumlahPelangganMenunggakWajar;
			this.jumlahPelangganMenunggakTakWajar = jumlahPelangganMenunggakTakWajar;
		}

		public long getEstimasiPemasukanBulanan() {
			return estimasiPemasukanBulanan;
		}
		
		public void setEstimasiPemasukanBulanan(long estimasiPemasukanBulanan) {
			this.estimasiPemasukanBulanan = estimasiPemasukanBulanan;
		}

		public long getPemasukanBulanBerjalan() {
			return pemasukanBulanBerjalan;
		}

		public void setPemasukanBulanBerjalan(long pemasukanBulanBerjalan) {
			this.pemasukanBulanBerjalan = pemasukanBulanBerjalan;
		}

		public long getEstimasiTagihanBulanan() {
			return estimasiTagihanBulanan;
		}

		public void setEstimasiTagihanBulanan(long estimasiTagihanBulanan) {
			this.estimasiTagihanBulanan = estimasiTagihanBulanan;
		}

		public long getTagihanBulanBerjalan() {
			return tagihanBulanBerjalan;
		}

		public void setTagihanBulanBerjalan(long tagihanBulanBerjalan) {
			this.tagihanBulanBerjalan = tagihanBulanBerjalan;
		}

		public long getTotalAkumulasiTunggakan() {
			return totalAkumulasiTunggakan;
		}

		public void setTotalAkumulasiTunggakan(long totalAkumulasiTunggakan) {
			this.totalAkumulasiTunggakan = totalAkumulasiTunggakan;
		}

		public long getJumlahPelangganAktif() {
			return jumlahPelangganAktif;
		}

		public void setJumlahPelangganAktif(long jumlahPelangganAktif) {
			this.jumlahPelangganAktif = jumlahPelangganAktif;
		}

		public long getJumlahPelangganBerhenti() {
			return jumlahPelangganBerhenti;
		}

		public void setJumlahPelangganBerhenti(long jumlahPelangganBerhenti) {
			this.jumlahPelangganBerhenti = jumlahPelangganBerhenti;
		}

		public long getJumlahPelangganPutus() {
			return jumlahPelangganPutus;
		}

		public void setJumlahPelangganPutus(long jumlahPelangganPutus) {
			this.jumlahPelangganPutus = jumlahPelangganPutus;
		}

		public long getJumlahPelangganLunas() {
			return jumlahPelangganLunas;
		}

		public void setJumlahPelangganLunas(long jumlahPelangganLunas) {
			this.jumlahPelangganLunas = jumlahPelangganLunas;
		}

		public long getJumlahPelangganMenunggakWajar() {
			return jumlahPelangganMenunggakWajar;
		}

		public void setJumlahPelangganMenunggakWajar(
				long jumlahPelangganMenunggakWajar) {
			this.jumlahPelangganMenunggakWajar = jumlahPelangganMenunggakWajar;
		}

		public long getJumlahPelangganMenunggakTakWajar() {
			return jumlahPelangganMenunggakTakWajar;
		}

		public void setJumlahPelangganMenunggakTakWajar(
				long jumlahPelangganMenunggakTakWajar) {
			this.jumlahPelangganMenunggakTakWajar = jumlahPelangganMenunggakTakWajar;
		}
	}
}
