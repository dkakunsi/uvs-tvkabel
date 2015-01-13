package com.unitedvision.tvkabel.web.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.unitedvision.tvkabel.core.domain.Alamat;
import com.unitedvision.tvkabel.core.domain.Kontak;
import com.unitedvision.tvkabel.core.domain.Perusahaan;
import com.unitedvision.tvkabel.exception.EmptyCodeException;
import com.unitedvision.tvkabel.exception.EmptyIdException;
import com.unitedvision.tvkabel.exception.UncompatibleTypeException;
import com.unitedvision.tvkabel.persistence.entity.AlamatValue;
import com.unitedvision.tvkabel.persistence.entity.KelurahanEntity;
import com.unitedvision.tvkabel.persistence.entity.KontakValue;
import com.unitedvision.tvkabel.persistence.entity.PerusahaanEntity;

public final class PerusahaanModel extends CodableModel implements Perusahaan {
	private String nama;
	private long iuran;
	private Status status;
	
	private int idKota;
	private String namaKota;
	private int idKecamatan;
	private String namaKecamatan;
	private int idKelurahan;
	private String namaKelurahan;
	private int lingkungan;
	private String detailAlamat;
	private float latitude;
	private float longitude;
	
	private String telepon;
	private String hp;
	private String email;
	
	public PerusahaanModel() {
		super();
	}
	
	public PerusahaanModel(int id) {
		super();
		this.id = id;
	}
	
	public PerusahaanModel(PerusahaanEntity perusahaanEntity) {
		super();
		this.id = perusahaanEntity.getId();
		this.kode = perusahaanEntity.getKode();
		this.nama = perusahaanEntity.getNama();
		this.idKota = perusahaanEntity.getKelurahan.getIdKota();
		this.namaKota = perusahaanEntity.getKelurahan().getNamaKota();
		this.idKecamatan = perusahaanEntity.getKelurahan().getIdKecamatan();
		this.namaKecamatan = perusahaanEntity.getKelurahan().getNamaKecamatan();
		thia.idKelurahan - perusahaanEntity.getKelurahan().getId();
		this.namaKelurahan = perusahaanEntity.getKelurahan().getNama();
		this.telepon = perusahaanEntity.getTelepon();
		this.hp = perusahaanEntity.getHp();
		this.email = perusahaanEntity.getEmail();
		this.lingkungan = perusahaanEntity.getLingkungan();
		this.detailAlamat = perusahaanEntity.getDetailAlamat();
		this.setLatitude(perusahaanEntity.getAlamat().getLatitude());
		this.setLongitude(perusahaanEntity.getAlamat().getLongitude());

		if (iuran == 0) {
			iuran = 1000L;
		} else {
			this.iuran = perusahaanEntity.getIuran();
		}
		
		if (perusahaanEntity.getStatus() == null) {
			status = Status.AKTIF;
		} else {
			this.status = perusahaanEntity.getStatus();
		}
	}
	
	public PerusahaanModel(int id, String kode, String nama, long iuran, Status status,
			String namaKota, String namaKecamatan, String namaKelurahan,  int lingkungan, String detailAlamat,
			String telepon, String hp, String email) {
		super();
		this.id = id;
		this.kode = kode;
		this.nama = nama;
		this.namaKota = namaKota;
		this.namaKecamatan = namaKecamatan;
		this.namaKelurahan = namaKelurahan;
		this.lingkungan = lingkungan;
		this.detailAlamat = detailAlamat;
		this.telepon = telepon;
		this.hp = hp;
		this.email = email;

		if (iuran == 0)
			iuran = 1000L;
		this.iuran = iuran;

		if (status == null)
			status = Status.AKTIF;
		this.status = status;
	}

	@Override
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String getKode() {
		return kode;
	}

	@Override
	public void setKode(String kode) {
		this.kode = kode;
	}

	@Override
	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}
	
	@Override
	public long getIuran() {
		return iuran;
	}
	
	public void setIuran(long iuran) {
		this.iuran = iuran;
	}
	
	@Override
	public Status getStatus() {
		if (status == null)
			status = Status.AKTIF;
		return status;
	}
	
	public void setStatus(Status status) {
		this.status = status;
	}

	public int getIdKota() {
		return idKota;
	}

	public void setIdKota(int idKota) {
		this.idKota = idKota;
	}

	public String getNamaKota() {
		return namaKota;
	}

	public void setNamaKota(String namaKota) {
		this.namaKota = namaKota;
	}

	public int getIdKecamatan() {
		return idKecamatan;
	}

	public void setIdKecamatan(int idKecamatan) {
		this.idKecamatan = idKecamatan;
	}

	public String getNamaKecamatan() {
		return namaKecamatan;
	}

	public void setNamaKecamatan(String namaKecamatan) {
		this.namaKecamatan = namaKecamatan;
	}

	public int getIdKelurahan() {
		return idKelurahan;
	}

	public void setIdKelurahan(int idKelurahan) {
		this.idKelurahan = idKelurahan;
	}

	@Override
	public String getNamaKelurahan() {
		return namaKelurahan;
	}

	public void setNamaKelurahan(String namaKelurahan) {
		this.namaKelurahan = namaKelurahan;
	}

	@Override
	public int getLingkungan() {
		return lingkungan;
	}

	public void setLingkungan(int lingkungan) {
		this.lingkungan = lingkungan;
	}

	@Override
	@JsonIgnore
	public String getDetailAlamat() {
		return detailAlamat;
	}

	public void setDetailAlamat(String detailAlamat) {
		this.detailAlamat = detailAlamat;
	}

	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public float getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	@Override
	public String getTelepon() {
		return telepon;
	}

	public void setTelepon(String telepon) {
		this.telepon = telepon;
	}

	@Override
	public String getHp() {
		return hp;
	}

	public void setHp(String hp) {
		this.hp = hp;
	}

	@Override
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@JsonIgnore
	public KelurahanModel getKelurahanModel() {
		return new KelurahanModel(0, namaKelurahan, namaKecamatan, namaKota);
	}
	
	@JsonIgnore
	public KelurahanEntity getKelurahan() {
		return getKelurahanModel().toEntity();
	}

	@Override
	public void setAlamat(Alamat alamat) throws UncompatibleTypeException {
		setIdKota(alamat.getKelurahan().getIdKota());
		setNamaKota(alamat.getKelurahan().getNamaKota());
		setIdKecamatan(alamat.getKelurahan().getIdKecamatan());
		setNamaKecamatan(alamat.getKelurahan().getNamaKecamatan());
		setIdKelurahan(alamat.getKelurahan().getId());
		setNamaKelurahan(alamat.getKelurahan().getNama());
		setLingkungan(alamat.getLingkungan());
		setDetailAlamat(alamat.getDetailAlamat());
	}

	@Override
	@JsonIgnore
	public Alamat getAlamat() {
		return new AlamatValue(getKelurahan(), lingkungan, detailAlamat);
	}
	
	@Override
	@JsonIgnore
	public Kontak getKontak() {
		return new KontakValue(telepon, hp, email);
	}

	@Override
	@JsonIgnore
	public PerusahaanEntity toEntity() {
		try {
			return new PerusahaanEntity(id, kode, nama, getAlamat().toEntity(), getKontak().toEntity(), iuran, getStatus());
		} catch (EmptyIdException | EmptyCodeException e) {
			return toEntityWithKode();
		}
	}
	
	@Override
	@JsonIgnore
	protected PerusahaanEntity toEntityWithKode() {
		try {
			return new PerusahaanEntity(kode, nama, getAlamat().toEntity(), getKontak().toEntity(), iuran, getStatus());
		} catch (EmptyCodeException e) {
			return new PerusahaanEntity(nama, getAlamat().toEntity(), getKontak().toEntity(), iuran, getStatus());
		}
	}

	@Override
	@JsonIgnore
	public PerusahaanModel toModel() {
		return this;
	}
	
	public static List<PerusahaanModel> createListModel(List<? extends Perusahaan> ls) {
		List<PerusahaanModel> list = new ArrayList<>();
		
		for (Perusahaan perusahaan : ls)
			list.add(perusahaan.toModel());
		
		return list;
	}

	@Override
	public String generateKode(long count) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static Rekap createRekap(long estimasiPemasukanBulanan, long estimasiTagihanBulanan, long pemasukanBulanBerjalan, long tagihanBulanBerjalan, long totalAkumulasiTunggakan, long jumlahPelangganAktif, long jumlahPelangganBerhenti, long jumlahPelangganPutus, long jumlahPelangganLunas, long jumlahPelangganMenunggakWajar, long jumlahPelangganMenunggakTakWajar) {
		return new Rekap(estimasiPemasukanBulanan, pemasukanBulanBerjalan, estimasiTagihanBulanan, tagihanBulanBerjalan, totalAkumulasiTunggakan, jumlahPelangganAktif, jumlahPelangganBerhenti, jumlahPelangganPutus, jumlahPelangganLunas, jumlahPelangganMenunggakWajar, jumlahPelangganMenunggakTakWajar);
	}
	
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
