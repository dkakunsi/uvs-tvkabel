package com.unitedvision.tvkabel.web.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.unitedvision.tvkabel.core.domain.Alamat;
import com.unitedvision.tvkabel.core.domain.DetailPelanggan;
import com.unitedvision.tvkabel.core.domain.Kecamatan;
import com.unitedvision.tvkabel.core.domain.Kontak;
import com.unitedvision.tvkabel.core.domain.Kota;
import com.unitedvision.tvkabel.core.domain.Pelanggan;
import com.unitedvision.tvkabel.core.domain.Pembayaran;
import com.unitedvision.tvkabel.core.domain.Perusahaan;
import com.unitedvision.tvkabel.core.domain.Tagihan;
import com.unitedvision.tvkabel.exception.EmptyCodeException;
import com.unitedvision.tvkabel.exception.EmptyIdException;
import com.unitedvision.tvkabel.exception.StatusChangeException;
import com.unitedvision.tvkabel.exception.UncompatibleTypeException;
import com.unitedvision.tvkabel.persistence.entity.AlamatValue;
import com.unitedvision.tvkabel.persistence.entity.KelurahanEntity;
import com.unitedvision.tvkabel.persistence.entity.KontakValue;
import com.unitedvision.tvkabel.persistence.entity.PelangganEntity;
import com.unitedvision.tvkabel.persistence.entity.PembayaranEntity;
import com.unitedvision.tvkabel.util.DateUtil;

public class PelangganModel extends CodableModel implements Pelanggan {
	//Purposed Attribute
	private Perusahaan perusahaan;
	private List<PembayaranModel> listPembayaran;
	
	private String nama;
	private String profesi;

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
	
	private String tanggalMulaiStr;
	private int jumlahTv;
	private long iuran;
	private int tunggakan;
	private Status status;
	
	public PelangganModel() {
		super();
	}

	public PelangganModel(int id) {
		super();
		this.id = id;
	}
	
	public PelangganModel(int id, String kode, String nama, String profesi, Perusahaan perusahaan, 
			String namaKota, String namaKecamatan, String namaKelurahan, int lingkungan, String detailAlamat, 
			String telepon, String hp, String email, String tanggalMulai, int jumlahTv, long iuran, int tunggakan,
			Status status) {
		super();
		this.id = id;
		this.kode = kode;
		this.perusahaan = perusahaan;
		this.nama = nama;
		this.profesi = profesi;
		this.namaKota = namaKota;
		this.namaKecamatan = namaKecamatan;
		this.namaKelurahan = namaKelurahan;
		this.lingkungan = lingkungan;
		this.detailAlamat = detailAlamat;
		this.telepon = telepon;
		this.hp = hp;
		this.email = email;
		this.tanggalMulaiStr = tanggalMulai;
		this.jumlahTv = jumlahTv;
		this.iuran = iuran;
		this.tunggakan = tunggakan;
		this.status = status;
	}

	public PelangganModel(PelangganEntity pelangganEntity) {
		super();
		this.id = pelangganEntity.getId();
		this.perusahaan = pelangganEntity.getPerusahaan();
		this.kode = pelangganEntity.getKode();
		this.nama = pelangganEntity.getNama();
		this.profesi = pelangganEntity.getProfesi();
		this.idKota = pelangganEntity.getKota().getId();
		this.namaKota = pelangganEntity.getKota().getNama();
		this.idKecamatan = pelangganEntity.getKecamatan().getId();
		this.namaKecamatan = pelangganEntity.getKecamatan().getNama();
		this.idKelurahan = pelangganEntity.getKelurahan().getId();
		this.namaKelurahan = pelangganEntity.getKelurahan().getNama();
		this.lingkungan = pelangganEntity.getLingkungan();
		this.detailAlamat = pelangganEntity.getDetailAlamat();
		this.setLatitude(pelangganEntity.getAlamat().getLatitude());
		this.setLongitude(pelangganEntity.getAlamat().getLongitude());
		this.telepon = pelangganEntity.getTelepon();
		this.hp = pelangganEntity.getHp();
		this.email = pelangganEntity.getEmail();
		this.tanggalMulaiStr = DateUtil.toString(pelangganEntity.getTanggalMulai());
		this.jumlahTv = pelangganEntity.getJumlahTv();
		this.iuran = pelangganEntity.getIuran();
		this.tunggakan = pelangganEntity.getTunggakan();
		this.status = pelangganEntity.getStatus();
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
	public String getProfesi() {
		return profesi;
	}
	
	public void setProfesi(String profesi) {
		this.profesi = profesi;
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
	
	@Override
	@JsonIgnore
	public Date getTanggalMulai() {
		return DateUtil.getDate(tanggalMulaiStr);
	}

	public String getTanggalMulaiStr() {
		return tanggalMulaiStr;
	}

	public void setTanggalMulaiStr(String tanggalMulai) {
		this.tanggalMulaiStr = tanggalMulai;
	}

	@Override
	public int getJumlahTv() {
		return jumlahTv;
	}

	public void setJumlahTv(int jumlahTv) {
		this.jumlahTv = jumlahTv;
	}

	@Override
	public long getIuran() {
		return iuran;
	}

	public void setIuran(long iuran) {
		this.iuran = iuran;
	}

	@Override
	public int getTunggakan() {
		return tunggakan;
	}

	public void setTunggakan(int tunggakan) {
		this.tunggakan = tunggakan;
	}

	@Override
	public Status getStatus() {
		return status;
	}

	@Override
	public void setStatus(Status status) {
		this.status = status;
	}
	
	@Override
	@JsonIgnore
	public DetailPelanggan getDetail() {
		return new PelangganEntity.Detail(DateUtil.getDate(tanggalMulaiStr), jumlahTv, iuran, tunggakan);
	}

	@Override
	@JsonIgnore
	public Alamat getAlamat() {
		return new AlamatValue(getKelurahan(), lingkungan, detailAlamat);
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
	public Kontak getKontak() {
		return new KontakValue(telepon, hp, email);
	}

	@Override
	@JsonIgnore
	public Perusahaan getPerusahaan() {
		return perusahaan;
	}

	@Override
	public void setPerusahaan(Perusahaan perusahaan) throws UncompatibleTypeException {
		this.perusahaan = perusahaan.toEntity();
	}

	public int getIdPerusahaan() {
		return perusahaan.getId();
	}

	@JsonIgnore
	public List<PembayaranModel> getListPembayaran() {
		return listPembayaran;
	}

	@Override
	public void setListPembayaran(List<PembayaranEntity> listPembayaran) {
		this.listPembayaran = PembayaranModel.createListModel(listPembayaran);
	}

	//TODO delete this
	@Deprecated
	public PelangganEntity toCoreMinimum() throws EmptyIdException {
		return new PelangganEntity(id);
	}

	@Override
	public PelangganEntity toEntity() {
		try {
			return new PelangganEntity(id, perusahaan.toEntity(), kode, nama, profesi, getAlamat().toEntity(), getKontak().toEntity(), getDetail().toEntity(), status);
		} catch (EmptyIdException | EmptyCodeException e) {
			return toEntityWithKode();
		}
	}

	@Override
	protected PelangganEntity toEntityWithKode() {
		try {
			return new PelangganEntity(perusahaan.toEntity(), kode, nama, profesi, getAlamat().toEntity(), getKontak().toEntity(), getDetail().toEntity(), status);
		} catch (EmptyCodeException e) {
			return new PelangganEntity(perusahaan.toEntity(), nama, profesi, getAlamat().toEntity(), getKontak().toEntity(), getDetail().toEntity(), status);
		}
	}
	
	@Override
	public PelangganModel toModel() {
		return this;
	}

	@JsonIgnore
	public KelurahanModel getKelurahanModel() {
		return new KelurahanModel(0, namaKelurahan, namaKecamatan, namaKota);
	}
	
	@JsonIgnore
	public KelurahanEntity getKelurahan() {
		return getKelurahanModel().toEntity();
	}
	
	public static List<PelangganModel> createListModel(List<? extends Pelanggan> ls) {
		List<PelangganModel> list = new ArrayList<>();
		
		for (Pelanggan pelanggan : ls)
			list.add(pelanggan.toModel());
		
		return list;
	}

	public static List<PelangganModel> rekapPembayaran(List<PelangganEntity> s) { 
 		List<PelangganModel> list = new ArrayList<>(); 
 		 
		for (PelangganEntity entity : s) { 
 			PelangganModel PelangganModel = new PelangganModel(entity); 
 			PelangganModel.setListPembayaran(entity.getListPembayaran()); 
 			list.add(PelangganModel); 
 		} 
 
 		return list;
 	} 

	@Override
	@JsonIgnore
	public Kecamatan getKecamatan() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@JsonIgnore
	public Kota getKota() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int countTunggakan() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int countTunggakan(Pembayaran pembayaranTerakhir) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int countTunggakan(Tagihan tagihan) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void remove() throws StatusChangeException {
		// TODO Auto-generated method stub
		
	}
}
