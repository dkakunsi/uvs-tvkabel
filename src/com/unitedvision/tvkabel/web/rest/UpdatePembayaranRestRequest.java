package com.unitedvision.tvkabel.web.rest;

public class UpdatePembayaranRestRequest extends RestRequest {
	private int id;
	private int idPegawai;
	private long jumlahPembayaran;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdPegawai() {
		return idPegawai;
	}

	public void setIdPegawai(int idPegawai) {
		this.idPegawai = idPegawai;
	}

	public long getJumlahPembayaran() {
		return jumlahPembayaran;
	}

	public void setJumlahPembayaran(long jumlahPembayaran) {
		this.jumlahPembayaran = jumlahPembayaran;
	}
	
	
}
