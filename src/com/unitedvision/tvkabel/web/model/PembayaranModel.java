package com.unitedvision.tvkabel.web.model;

import java.time.Month;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.unitedvision.tvkabel.core.domain.Pegawai;
import com.unitedvision.tvkabel.core.domain.Pelanggan;
import com.unitedvision.tvkabel.core.domain.Pembayaran;
import com.unitedvision.tvkabel.core.domain.Tagihan;
import com.unitedvision.tvkabel.exception.EmptyCodeException;
import com.unitedvision.tvkabel.exception.EmptyIdException;
import com.unitedvision.tvkabel.exception.UncompatibleTypeException;
import com.unitedvision.tvkabel.persistence.entity.PegawaiEntity;
import com.unitedvision.tvkabel.persistence.entity.PelangganEntity;
import com.unitedvision.tvkabel.persistence.entity.PembayaranEntity;
import com.unitedvision.tvkabel.util.DateUtil;

public class PembayaranModel extends CodableModel implements Pembayaran {
	private int idPelanggan;
	private String kodePelanggan;
	private String namaPelanggan;
	private String profesiPelanggan; //just for show purpose (not for saving)
	
	private int idPegawai;
	private String kodePegawai;
	private String namaPegawai;

	private int tahun;
	private String bulan;
	private String tanggalBayarStr;
	private long jumlahBayar;
	
	public PembayaranModel() {
		super();
	}
	
	public PembayaranModel(int id) {
		super();
		this.id = id;
	}
	
	public PembayaranModel(PegawaiEntity pegawaiEntity) {
		super();
		this.idPegawai = pegawaiEntity.getId();
		this.namaPegawai = pegawaiEntity.getNama();
		this.tanggalBayarStr = DateUtil.getSimpleNowInString();
	}
	
	public PembayaranModel(PembayaranEntity pembayaranEntity) {
		super();
		this.id = pembayaranEntity.getId();
		this.kode = pembayaranEntity.getKode();
		this.idPelanggan = pembayaranEntity.getIdPelanggan();
		this.kodePelanggan = pembayaranEntity.getPelanggan().getKode();
		this.namaPelanggan = pembayaranEntity.getNamaPelanggan();
		this.profesiPelanggan = pembayaranEntity.getProfesiPelanggan();
		this.idPegawai = pembayaranEntity.getIdPegawai();
		this.kodePegawai = pembayaranEntity.getPegawai().getKode();
		this.namaPegawai = pembayaranEntity.getNamaPegawai();
		this.tahun = pembayaranEntity.getTahun();
		this.bulan = pembayaranEntity.getBulan().toString();
		this.tanggalBayarStr = DateUtil.toString(pembayaranEntity.getTanggalBayar());
		this.jumlahBayar = pembayaranEntity.getJumlahBayar();
	}

	public PembayaranModel(int id, String kode, int idPelanggan, int idPegawai, int tahun,
			String bulan, String tanggalBayar, long jumlahPembayaran, String namaPelanggan, String namaPegawai, String profesiPelanggan) {
		super();
		this.id = id;
		this.kode = kode;
		this.idPelanggan = idPelanggan;
		this.idPegawai = idPegawai;
		this.tahun = tahun;
		this.bulan = bulan;
		this.tanggalBayarStr = tanggalBayar;
		this.jumlahBayar = jumlahPembayaran;
		this.namaPelanggan = namaPelanggan;
		this.namaPegawai = namaPegawai;
		this.profesiPelanggan = profesiPelanggan;
	}
	
	public PembayaranModel(Pelanggan pelanggan, Pegawai pegawai, long jumlahBayar, Date tanggalBayar, Tagihan tagihan) {
		super();
		this.idPelanggan = pelanggan.getId();
		this.idPegawai = pegawai.getId();
		this.tahun = tagihan.getTahun();
		this.bulan = tagihan.getBulan().name();
		this.jumlahBayar = jumlahBayar;		
		setTanggalBayar(tanggalBayar);
	}

	@Override
	public void setId(int id) throws EmptyIdException {
		this.id = id;
	}
	
	@Override
	public void setKode(String kode) throws EmptyCodeException {
		this.kode = kode;
	}
	
	@Override
	public int getIdPelanggan() {
		return idPelanggan;
	}

	public void setIdPelanggan(int idPelanggan) {
		this.idPelanggan = idPelanggan;
	}

	public String getKodePelanggan() {
		return kodePelanggan;
	}

	public void setKodePelanggan(String kodePelanggan) {
		this.kodePelanggan = kodePelanggan;
	}
	
	@Override
	public String getNamaPelanggan() {
		return namaPelanggan;
	}

	public void setNamaPelanggan(String namaPelanggan) {
		this.namaPelanggan = namaPelanggan;
	}
	
	public String getProfesiPelanggan() {
		return profesiPelanggan;
	}
	
	public void setProfesiPelanggan(String profesiPelanggan) {
		this.profesiPelanggan = profesiPelanggan;
	}

	@Override
	public int getIdPegawai() {
		return idPegawai;
	}

	public void setIdPegawai(int idPegawai) {
		this.idPegawai = idPegawai;
	}

	public String getKodePegawai() {
		return kodePegawai;
	}

	public void setKodePegawai(String kodePegawai) {
		this.kodePegawai = kodePegawai;
	}

	@Override
	public String getNamaPegawai() {
		return namaPegawai;
	}

	public void setNamaPegawai(String namaPegawai) {
		this.namaPegawai = namaPegawai;
	}

	@Override
	public int getTahun() {
		return tahun;
	}

	public void setTahun(int tahun) {
		this.tahun = tahun;
	}

	@Override
	public Month getBulan() {
		return Month.valueOf(getBulanStr());
	}

	public String getBulanStr() {
		return bulan;
	}

	public void setBulanStr(String bulan) {
		this.bulan = bulan;
	}

	@Override
	public Date getTanggalBayar() {
		return DateUtil.getDate(getTanggalBayarStr());
	}

	public void setTanggalBayar(Date tanggalBayar) {
		setTanggalBayarStr(DateUtil.toString(tanggalBayar));
	}

	public String getTanggalBayarStr() {
		return tanggalBayarStr;
	}

	public void setTanggalBayarStr(String tanggalBayar) {
		this.tanggalBayarStr = tanggalBayar;
	}

	@Override
	public long getJumlahBayar() {
		return jumlahBayar;
	}

	public void setJumlahBayar(long jumlahPembayaran) {
		this.jumlahBayar = jumlahPembayaran;
	}
	
	public String getTagihanStr() {
		return getTagihan().toString();
	}
	
	@Override
	@JsonIgnore
	public Tagihan getTagihan() {
		return new PembayaranEntity.TagihanValue(tahun, Month.valueOf(bulan));
	}

	@JsonIgnore
	public PelangganModel getPelangganModel() {
		return new PelangganModel(idPelanggan);
	}

	@SuppressWarnings("deprecation")
	@Override
	@JsonIgnore
	public Pelanggan getPelanggan() {
		try {
			return getPelangganModel().toCoreMinimum(); //TODO delete toCoreMinimum(), change with toEntity()
		} catch (EmptyIdException e) {
			return new PelangganEntity();
		}
	}
	
	@Override
	public void setPelanggan(Pelanggan pelanggan) throws UncompatibleTypeException {
		setIdPelanggan(pelanggan.getId());
		setKodePelanggan(pelanggan.getKode());
		setNamaPelanggan(pelanggan.getNama());
	}

	@JsonIgnore
	public PegawaiModel getPegawaiModel() {
		return new PegawaiModel(idPegawai);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	@JsonIgnore
	public Pegawai getPegawai() {
		try {
			return getPegawaiModel().toCoreMinimum(); //TODO remove and replace with toEntity
		} catch (EmptyIdException e) {
			return new PegawaiEntity();
		}
	}

	@Override
	public void setPegawai(Pegawai pegawai) throws UncompatibleTypeException {
		setIdPegawai(pegawai.getId());
		setKodePegawai(pegawai.getKode());
		setNamaPegawai(pegawai.getNama());
	}

	@Override
	@JsonIgnore
	public PembayaranEntity toEntity() {
		try {
			return new PembayaranEntity(id, kode, DateUtil.getDate(tanggalBayarStr), getPelanggan().toEntity(), getPegawai().toEntity(), jumlahBayar, getTagihan().toEntity());
		} catch (EmptyIdException e) {
			return toEntityWithKode();
		}
	}
	
	@Override
	@JsonIgnore
	protected PembayaranEntity toEntityWithKode() {
		return new PembayaranEntity(kode, DateUtil.getDate(tanggalBayarStr), getPelanggan().toEntity(), getPegawai().toEntity(), jumlahBayar, getTagihan().toEntity());
	}

	@Override
	@JsonIgnore
	public PembayaranModel toModel() {
		return this;
	}

	//TODO delete this
	@Deprecated
	@JsonIgnore
	public PembayaranEntity toCoreMinimum() throws EmptyIdException {
		return new PembayaranEntity(id);
	}
	
	public static List<PembayaranModel> createListModel(List<PembayaranEntity> ls) {
		List<PembayaranModel> list = new ArrayList<>();
		
		for (PembayaranEntity core : ls)
			list.add(new PembayaranModel(core));
		
		return list;
	}

	public static List<PembayaranModel> createListModelPembayaran(List<? extends Pembayaran> ls) {
		List<PembayaranModel> list = new ArrayList<>();
		
		for (Pembayaran core : ls)
			list.add(new PembayaranModel(core.toEntity()));
		
		return list;
	}

	@Override
	@JsonIgnore
	public boolean isPaid(Pembayaran last) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	@JsonIgnore
	public boolean isPreceding(Pembayaran pembayaran) {
		// TODO Auto-generated method stub
		return false;
	}
}
