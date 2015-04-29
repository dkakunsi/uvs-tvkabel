package com.unitedvision.tvkabel.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.unitedvision.tvkabel.entity.Pelanggan.Status;
import com.unitedvision.tvkabel.util.DateUtil;

@Entity
@Table(name = "history")
public class History {
	private int id;
	private Pelanggan pelanggan;
	private Date tanggal;
	private Status status;
	private String keterangan;
	
	public History() {
		super();
	}

	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_pelanggan", referencedColumnName = "id")
	public Pelanggan getPelanggan() {
		return pelanggan;
	}

	public void setPelanggan(Pelanggan pelanggan) {
		this.pelanggan = pelanggan;
	}

	@JsonIgnore
	@Temporal(TemporalType.DATE)
	@Column(name = "tanggal")
	public Date getTanggal() {
		return tanggal;
	}
	
	@Transient
	public String getTanggalStr() {
		return DateUtil.toUserString(tanggal, "/");
	}

	public void setTanggal(Date tanggal) {
		this.tanggal = tanggal;
	}

	@Column(name = "status")
	public Status getStatus() {
		return status;
	}
	
	public void setStatus(Status status) {
		this.status = status;
	}

	@Column(name = "keterangan")
	public String getKeterangan() {
		return keterangan;
	}

	public void setKeterangan(String keterangan) {
		this.keterangan = keterangan;
	}

	@Override
	public String toString() {
		return "History [id=" + id + ", pelanggan=" + pelanggan + ", tanggal="
				+ tanggal + ", status=" + status + ", keterangan=" + keterangan
				+ "]";
	}
}
