package com.unitedvision.tvkabel.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
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

/**
 * Sejarah pelanggan dan perubahan jumlah pelanggan.
 * 
 * @author Deddy Christoper Kakunsi
 *
 */

@Entity
@Table(name = "history")
public class History extends Domain {
	private int id;
	private Pelanggan pelanggan;
	private Date tanggal;
	private Status status;
	private String keterangan;
	
	private HistoryJumlah historyJumlah;
	
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
	
	@Embedded
	public HistoryJumlah getHistoryJumlah() {
		return historyJumlah;
	}

	public void setHistoryJumlah(HistoryJumlah historyJumlah) {
		this.historyJumlah = historyJumlah;
	}

	@Embeddable
	public static final class HistoryJumlah {
		private long jumlahAktif;
		private long jumlahPutus;
		private long jumlahBerhenti;
		private long jumlahGratis;

		@Column(name = "jumlah_aktif")
		public long getJumlahAktif() {
			return jumlahAktif;
		}

		public void setJumlahAktif(long jumlahAktif) {
			this.jumlahAktif = jumlahAktif;
		}

		@Column(name = "jumlah_putus")
		public long getJumlahPutus() {
			return jumlahPutus;
		}

		public void setJumlahPutus(long jumlahPutus) {
			this.jumlahPutus = jumlahPutus;
		}

		@Column(name = "jumlah_berhenti")
		public long getJumlahBerhenti() {
			return jumlahBerhenti;
		}

		public void setJumlahBerhenti(long jumlahBerhenti) {
			this.jumlahBerhenti = jumlahBerhenti;
		}

		@Column(name = "jumlah_gratis")
		public long getJumlahGratis() {
			return jumlahGratis;
		}

		public void setJumlahGratis(long jumlahGratis) {
			this.jumlahGratis = jumlahGratis;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result
					+ (int) (jumlahAktif ^ (jumlahAktif >>> 32));
			result = prime * result
					+ (int) (jumlahBerhenti ^ (jumlahBerhenti >>> 32));
			result = prime * result
					+ (int) (jumlahGratis ^ (jumlahGratis >>> 32));
			result = prime * result
					+ (int) (jumlahPutus ^ (jumlahPutus >>> 32));
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			HistoryJumlah other = (HistoryJumlah) obj;
			if (jumlahAktif != other.jumlahAktif)
				return false;
			if (jumlahBerhenti != other.jumlahBerhenti)
				return false;
			if (jumlahGratis != other.jumlahGratis)
				return false;
			if (jumlahPutus != other.jumlahPutus)
				return false;
			return true;
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((historyJumlah == null) ? 0 : historyJumlah.hashCode());
		result = prime * result + id;
		result = prime * result
				+ ((keterangan == null) ? 0 : keterangan.hashCode());
		result = prime * result
				+ ((pelanggan == null) ? 0 : pelanggan.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((tanggal == null) ? 0 : tanggal.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		History other = (History) obj;
		if (historyJumlah == null) {
			if (other.historyJumlah != null)
				return false;
		} else if (!historyJumlah.equals(other.historyJumlah))
			return false;
		if (id != other.id)
			return false;
		if (keterangan == null) {
			if (other.keterangan != null)
				return false;
		} else if (!keterangan.equals(other.keterangan))
			return false;
		if (pelanggan == null) {
			if (other.pelanggan != null)
				return false;
		} else if (!pelanggan.equals(other.pelanggan))
			return false;
		if (status != other.status)
			return false;
		if (tanggal == null) {
			if (other.tanggal != null)
				return false;
		} else if (!tanggal.equals(other.tanggal))
			return false;
		return true;
	}
}
