package com.unitedvision.tvkabel.entity;

import java.time.Month;
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
import com.unitedvision.tvkabel.exception.EmptyIdException;
import com.unitedvision.tvkabel.util.DateUtil;

/**
 * Mapping of pembayaran table in database.
 * 
 * @author Deddy Christoper Kakunsi
 *
 */
@Entity
@Table(name = "pembayaran")
public final class Pembayaran extends CodableDomain {
	/** Payment date */
	private Date tanggalBayar;

	/** Paying customer */
	private Pelanggan pelanggan;
	
	/** Handling employee */
	private Pegawai pegawai;
	
	/** Bill count */
	private long jumlahBayar;
	
	/** Bill */
	private Tagihan tagihan;

	/**
	 * Create empty instance.
	 */
	public Pembayaran() {
		super();
	}

	/**
	 * Create instance.
	 * @param id must be positive
	 * @param kode if null or empty String, will be generated.
	 * @param tanggalbayar
	 * @param pelanggan
	 * @param pegawai
	 * @param jumlahbayar
	 * @param Tagihan
	 * @throws EmptyIdException {@code id} is not positive.
	 */
	public Pembayaran(int id, String kode, Date tanggalBayar, Pelanggan pelanggan, Pegawai pegawai, long jumlahBayar, Tagihan tagihan) throws EmptyIdException {
		super();
		setId(id);
		setTanggalBayar(tanggalBayar);
		setPelanggan(pelanggan);
		setPegawai(pegawai);
		setJumlahBayar(jumlahBayar);
		setTagihan(tagihan);

		if (kode == null || kode.equals("") || kode.equals("new")) {
			generateKode();
		} else {
			this.kode = kode;
		}
	}

	@Override
	@Id @GeneratedValue
	public int getId() {
		return super.getId();
	}

	@Override
	@Column(name = "kode_referensi")
	public String getKode() {
		return super.getKode();
	}

	/**
	 * Return payment date.
	 * @return tanggalBayar
	 */
	@JsonIgnore
	@Temporal(TemporalType.DATE)
	@Column(name = "tanggal_bayar")
	public Date getTanggalBayar() {
		return tanggalBayar;
	}

	/**
	 * Set payment date
	 * @param tanggalBayar
	 */
	public void setTanggalBayar(Date tanggalBayar) {
		this.tanggalBayar = tanggalBayar;
	}
	
	/**
	 * Return payment date in string.
	 * @return tanggalBayar
	 */
	@Transient
	public String getTanggalBayarStr() {
		return DateUtil.toUserString(tanggalBayar, "/");
	}

	/**
	 * Set payment date in string
	 * @param tanggalBayarStr
	 */
	public void setTanggalBayarStr(String tanggalBayarStr) {
		this.tanggalBayar = DateUtil.getDate(tanggalBayarStr, "/");
	}

	/**
	 * Return {@link Pelanggan} instance.
	 * @return pelanggan
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_pelanggan", referencedColumnName = "id")
	public Pelanggan getPelanggan() {
		return pelanggan;
	}

	/**
	 * Set paying customer.
	 * @param pelanggan
	 */
	public void setPelanggan(Pelanggan pelanggan) {
		this.pelanggan = pelanggan;
		pelanggan.setPembayaranTerakhir(this);
	}

	/**
	 * Return {@link Pegawai} instance.
	 * @return pegawai
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_pegawai", referencedColumnName = "id")
	public Pegawai getPegawai() {
		return pegawai;
	}

	/**
	 * Set handling employee.
	 * @param pegawai
	 */
	public void setPegawai(Pegawai pegawai) {
		this.pegawai = pegawai;
	}

	/**
	 * Return total of bill.
	 * @return jumlahBayar
	 */
	@Column(name = "jumlah_bayar")
	public long getJumlahBayar() {
		return jumlahBayar;
	}

	/**
	 * Set total of bill.
	 * @param jumlahBayar
	 */
	public void setJumlahBayar(long jumlahBayar) {
		this.jumlahBayar = jumlahBayar;
	}

	/**
	 * Return {@link Tagihan} instance.
	 * @return tagihan
	 */
	@Embedded
	public Tagihan getTagihan() {
		return tagihan;
	}

	/**
	 * Set bill.
	 * @param tagihan
	 */
	public void setTagihan(Tagihan tagihan) {
		this.tagihan = tagihan;
	}
	
	@JsonIgnore
	@Transient
	public String getTagihanStr() {
		return tagihan.toString();
	}
	
	@JsonIgnore
	@Transient
	public int getTahun() {
		return tagihan.getTahun();
	}

	@JsonIgnore
	@Transient
	public Month getBulan() {
		return tagihan.getBulan();
	}
	
	@JsonIgnore
	@Transient
	public int getIdPegawai() {
		return pegawai.getId();
	}
	
	@JsonIgnore
	@Transient
	public int getIdPelanggan() {
		return pelanggan.getId();
	}
	
	@JsonIgnore
	@Transient
	public boolean isPaid(Pembayaran last) {
		return tagihan.isPaid(last.getTagihan());
	}
	
	@JsonIgnore
	@Transient
	public boolean isPreceding(Pembayaran next) {
		return tagihan.isPreceding(next.getTagihan());
	}

	/**
	 * Generate payment code.
	 * @return kodeReferensi
	 */
	public String generateKode() {
		String idPelangganStr = toString(getIdPelanggan());
		String idPegawaiStr = toString(getIdPegawai());
		
		kode = String.format("%s%s%s", tagihan.join(), idPelangganStr, idPegawaiStr);
		
		return kode;
	}
	
	public Pembayaran copy() throws EmptyIdException {
		return new Pembayaran(id, kode, tanggalBayar, pelanggan, pegawai, jumlahBayar, tagihan);
	}
	
	/**
	 * Converts {@code id} to string representation with specified format.
	 * @param id
	 * @return idStr
	 */
	@Transient
	public String toString(final int id) {
		final String intStr = String.valueOf(id);
		final int intStrLength = intStr.length();
		String prefix = "";
		final int prefixLength = 5;
		if (intStrLength < prefixLength) {
			for (int i = 1; i <= (prefixLength - intStrLength); i++)
				prefix = String.format("0%s", prefix);
		}
		
		return String.format("%s%s", prefix, intStr);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (int) (jumlahBayar ^ (jumlahBayar >>> 32));
		result = prime * result + ((pegawai == null) ? 0 : pegawai.hashCode());
		result = prime * result
				+ ((pelanggan == null) ? 0 : pelanggan.hashCode());
		result = prime * result + ((tagihan == null) ? 0 : tagihan.hashCode());
		result = prime * result
				+ ((tanggalBayar == null) ? 0 : tanggalBayar.hashCode());
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
		Pembayaran other = (Pembayaran) obj;
		if (jumlahBayar != other.jumlahBayar)
			return false;
		if (pegawai == null) {
			if (other.pegawai != null)
				return false;
		} else if (!pegawai.equals(other.pegawai))
			return false;
		if (pelanggan == null) {
			if (other.pelanggan != null)
				return false;
		} else if (!pelanggan.equals(other.pelanggan))
			return false;
		if (tagihan == null) {
			if (other.tagihan != null)
				return false;
		} else if (!tagihan.equals(other.tagihan))
			return false;
		if (tanggalBayar == null) {
			if (other.tanggalBayar != null)
				return false;
		} else if (!tanggalBayar.equals(other.tanggalBayar))
			return false;
		return true;
	}

	@Override
	@JsonIgnore
	public String toString() {
		return "Pembayaran [tanggalBayar=" + tanggalBayar + ", pelanggan="
				+ pelanggan + ", pegawai=" + pegawai + ", jumlahbayar="
				+ jumlahBayar + ", tagihan=" + tagihan + "]";
	}

	/**
	 * Tagihan
	 * 
	 * @author Deddy Christoper Kakunsi
	 *
	 */
	@Embeddable
	public static final class Tagihan implements Comparable {
		/** Year */
		private int tahun;
		
		/** Month */
		private Month bulan;

		/**
		 * Create instance.
		 */
		public Tagihan() {
			super();
		}

		/**
		 * Create instance
		 * @param tahun
		 * @param bulan
		 */
		public Tagihan(int tahun, Month bulan) {
			super();
			setBulan(bulan);
			setTahun(tahun);
		}

		/**
		 * Return year.
		 * @return tahun
		 */
		@Column(name = "tahun")
		public int getTahun() {
			return tahun;
		}

		/**
		 * Set year.
		 * @param tahun
		 */
		public void setTahun(int tahun) {
			this.tahun = tahun;
		}

		/**
		 * Return month.
		 * @return bulan
		 */
		@JsonIgnore
		@Column(name = "bulan")
		public Month getBulan() {
			return bulan;
		}

		/**
		 * Set month.
		 * @param bulan
		 */
		public void setBulan(Month bulan) {
			this.bulan = bulan;
		}

		@Transient
		public String getBulanStr() {
			return bulan.name();
		}
		
		public void setBulanStr(String bulanStr) {
			bulan = Month.valueOf(bulanStr);
		}

		/**
		 * Joins year and month with specified format.
		 * @return joined year and month with specified format
		 */
		public String join() {
			String bulanStr = String.valueOf(bulan.getValue());
			if (bulanStr.length() == 1)
				bulanStr = String.format("0%s", bulanStr);
			return String.format("%d%s", tahun, bulanStr);
		}

		/**
		 * Increase tagihan.
		 */
		public void increase() {
			int bulanInt = bulan.getValue();
			bulanInt++;

			if (bulanInt > 12) {
				bulanInt = 1;
				tahun++;
			}
			
			bulan = Month.of(bulanInt);
		}

		/**
		 * Decrease tagihan.
		 */
		public void decrease() {
			int bulanInt = bulan.getValue();
			bulanInt--;

			if (bulanInt < 1) {
				bulanInt = 12;
				tahun--;
			}
			
			bulan = Month.of(bulanInt);
		}

		/**
		 * Add tagihan with specified {@code number}.
		 * @param number
		 */
		public void add(int number) {
			for (int i = 1; i < number; i++)
				increase();
		}
		
		/**
		 * Substract tagihan with specified {@code number}.
		 * @param number
		 */
		public void substract(int number) {
			for (int i = 1; i < number; i++)
				decrease();
		}
		
		@JsonIgnore
		@Transient
		public int getValue() {
			return (tahun * 12) + bulan.getValue();
		}

		@JsonIgnore
		@Transient
		public int compareWith(Comparable comparer) {
			int selisih = this.getValue() - comparer.getValue();

			return selisih;
		}

		/**
		 * Whether this tagihan is already paid or not.
		 * @param last
		 * @return true if already paid, otherwise false
		 */
		@JsonIgnore
		@Transient
		public boolean isPaid(Tagihan last) {
			if (this.compareWith(last) <= 0)
				return true;
			return false;
		}

		/**
		 * Whether this is preceding for next. 
		 * @param next
		 * @return true if this is a direct preceding, otherwise false
		 */
		@JsonIgnore
		@Transient
		public boolean isPreceding(Tagihan next) {
			if (this.compareWith(next) == -1)
				return true;
			return false;
		}

		/**
		 * Create tagihan from given date.<br />
		 * It will take year and month from date as parameter to create new tagihan.
		 * @param date
		 * @return created tagihan from date
		 */
		@Transient
		public static Tagihan create(Date date) {
			int tahun = DateUtil.getYear(date);
			Month month = DateUtil.getMonth(date);
			
			return new Tagihan(tahun, month);
		}
		
		public static Tagihan copy(Tagihan tagihan) {
			return new Tagihan(tagihan.getTahun(), tagihan.getBulan());
		}

		@Override
		@JsonIgnore
		public String toString() {
			return String.format("%s - %d", bulan.name(), tahun);
		}

		@Override
		@JsonIgnore
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((bulan == null) ? 0 : bulan.hashCode());
			result = prime * result + tahun;
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
			Tagihan other = (Tagihan) obj;
			if (bulan != other.bulan)
				return false;
			if (tahun != other.tahun)
				return false;
			return true;
		}
	}
}
