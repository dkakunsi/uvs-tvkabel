package com.unitedvision.tvkabel.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.unitedvision.tvkabel.util.DateUtil;

@Entity
@Table(name = "token")
public class Token {
	private String token;
	private Pegawai pegawai;
	private Date tanggal;
	private Date expire;
	private Status status;
	
	public Token() {
		super();
	}

	@Id
	@Column(name = "token")
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String generateToken() {
		int idPegawai = pegawai.getId();
		String tanggalCoded = DateUtil.codedString(tanggal);
		
		token = String.format("%d%s", idPegawai, tanggalCoded);
		
		return token;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_pegawai", referencedColumnName = "id")
	public Pegawai getPegawai() {
		return pegawai;
	}

	public void setPegawai(Pegawai pegawai) {
		this.pegawai = pegawai;
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

	@JsonIgnore
	@Temporal(TemporalType.DATE)
	@Column(name = "expire")
	public Date getExpire() {
		return expire;
	}
	
	@Transient
	public String getExpireStr() {
		return DateUtil.toUserString(expire, "/");
	}

	public void setExpire(Date expire) {
		this.expire = expire;
	}

	public Date generateExpireDate() {
		int day = DateUtil.getDay(tanggal);
		int month = DateUtil.getMonthInt(tanggal);
		int year = DateUtil.getYear(tanggal);
		
		expire = DateUtil.getDate(year, month, (day + 2));
		return expire;
	}

	@Column(name = "status")
	public Status getStatus() {
		return status;
	}
	
	public void setStatus(Status status) {
		this.status = status;
	}

	public enum Status {
		NON_AKTIF, AKTIF
	}
	
	@Transient
	@JsonIgnore
	public boolean isRenewable() {
		Date today = DateUtil.getNow();
		int day = DateUtil.getDay(today);
		int month = DateUtil.getMonthInt(today);
		int year = DateUtil.getYear(today);

		Date tomorrow = DateUtil.getDate(year, month, (day + 1));

		return DateUtil.equals(expire, tomorrow);
	}

	@Override
	public String toString() {
		return "Token [token=" + token + ", pegawai=" + pegawai + ", tanggal="
				+ tanggal + ", expire=" + expire + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((expire == null) ? 0 : expire.hashCode());
		result = prime * result + ((pegawai == null) ? 0 : pegawai.hashCode());
		result = prime * result + ((tanggal == null) ? 0 : tanggal.hashCode());
		result = prime * result + ((token == null) ? 0 : token.hashCode());
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
		Token other = (Token) obj;
		if (expire == null) {
			if (other.expire != null)
				return false;
		} else if (!expire.equals(other.expire))
			return false;
		if (pegawai == null) {
			if (other.pegawai != null)
				return false;
		} else if (!pegawai.equals(other.pegawai))
			return false;
		if (tanggal == null) {
			if (other.tanggal != null)
				return false;
		} else if (!tanggal.equals(other.tanggal))
			return false;
		if (token == null) {
			if (other.token != null)
				return false;
		} else if (!token.equals(other.token))
			return false;
		return true;
	}
}
