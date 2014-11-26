package com.unitedvision.tvkabel.persistence.entity;

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
import com.unitedvision.tvkabel.core.domain.Pegawai;
import com.unitedvision.tvkabel.core.domain.Pelanggan;
import com.unitedvision.tvkabel.core.domain.Pembayaran;
import com.unitedvision.tvkabel.core.domain.Tagihan;
import com.unitedvision.tvkabel.exception.EmptyIdException;
import com.unitedvision.tvkabel.exception.UncompatibleTypeException;
import com.unitedvision.tvkabel.util.DateUtil;
import com.unitedvision.tvkabel.web.model.PembayaranModel;
import com.unitedvision.tvkabel.web.model.TagihanModel;

/**
 * Mapping of pembayaran table in database.
 * 
 * @author Deddy Christoper Kakunsi
 *
 */
@Entity
@Table(name = "pembayaran")
public final class PembayaranEntity extends CodableEntity implements Pembayaran {
	/** Payment date */
	private Date tanggalBayar;

	/** Paying customer */
	private PelangganEntity pelanggan;
	
	/** Handling employee */
	private PegawaiEntity pegawai;
	
	/** Bill count */
	private long jumlahBayar;
	
	/** Bill */
	private TagihanValue tagihan;

	/**
	 * Create empty instance.
	 */
	public PembayaranEntity() {
		super();
	}

	/**
	 * Create minimum instance containing only id.
	 * @param id must be positive
	 * @throws EmptyIdException id is 0 or negative
	 */
	@Deprecated
	public PembayaranEntity(int id) throws EmptyIdException {
		super();
		setId(id);
	}

	/**
	 * Create instance already persisting in database and already has id.
	 * @param tanggalBayar
	 * @param pelangganEntity
	 * @param pegawaiEntity
	 * @param jumlahBayar
	 * @param tagihanValue
	 */
	public PembayaranEntity(Date tanggalBayar, PelangganEntity pelangganEntity, PegawaiEntity pegawaiEntity, long jumlahBayar, TagihanValue tagihanValue) {
		super();
		this.tanggalBayar = tanggalBayar;
		this.pelanggan = pelangganEntity;
		this.pegawai = pegawaiEntity;
		this.jumlahBayar = jumlahBayar;
		this.tagihan = tagihanValue;
	}

	/**
	 * Create new instance without id. It is not persisted in database.
	 * @param kode if null or empty String, it will be generated.
	 * @param tanggalbayar
	 * @param pelangganEntity
	 * @param pegawaiEntity
	 * @param jumlahbayar
	 * @param tagihanValue
	 */
	public PembayaranEntity(String kode, Date tanggalbayar, PelangganEntity pelangganEntity, PegawaiEntity pegawaiEntity, long jumlahbayar, TagihanValue tagihanValue) {
		this(tanggalbayar, pelangganEntity, pegawaiEntity, jumlahbayar, tagihanValue);
		if (kode == null || kode.equals("")) {
			generateKode();
		} else {
			this.kode = kode;
		}
	}

	/**
	 * Create instance already persisting in database and already has id.
	 * @param id must be positive
	 * @param kode if null or empty String, i will be generated
	 * @param tanggalbayar
	 * @param pelangganEntity
	 * @param pegawaiEntity
	 * @param jumlahbayar
	 * @param tagihanValue
	 * @throws EmptyIdException id is 0 or negative
	 */
	public PembayaranEntity(int id, String kode, Date tanggalbayar, PelangganEntity pelangganEntity, PegawaiEntity pegawaiEntity, long jumlahbayar, TagihanValue tagihanValue) throws EmptyIdException {
		this(kode, tanggalbayar, pelangganEntity, pegawaiEntity, jumlahbayar, tagihanValue);
		setId(id);
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

	@Override
	@Temporal(TemporalType.DATE)
	@Column(name = "tanggal_bayar")
	public Date getTanggalBayar() {
		return tanggalBayar;
	}

	/**
	 * Set payment date
	 * @param tanggalbayar
	 */
	public void setTanggalBayar(Date tanggalbayar) {
		this.tanggalBayar = tanggalbayar;
	}

	@Override
	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_pelanggan", referencedColumnName = "id")
	public PelangganEntity getPelanggan() {
		return pelanggan;
	}

	/**
	 * Set paying customer.
	 * @param pelangganEntity
	 */
	public void setPelanggan(PelangganEntity pelangganEntity) {
		this.pelanggan = pelangganEntity;
	}

	@Override
	public void setPelanggan(Pelanggan pelanggan) throws UncompatibleTypeException {
		if (!(pelanggan instanceof PelangganEntity))
			throw new UncompatibleTypeException();
		setPelanggan(pelanggan.toEntity());
	}

	@Override
	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_pegawai", referencedColumnName = "id")
	public PegawaiEntity getPegawai() {
		return pegawai;
	}

	/**
	 * Set handling employee.
	 * @param pegawaiEntity
	 */
	public void setPegawai(PegawaiEntity pegawaiEntity) {
		this.pegawai = pegawaiEntity;
	}

	@Override
	public void setPegawai(Pegawai pegawai) throws UncompatibleTypeException {
		if (!(pegawai instanceof PegawaiEntity))
			throw new UncompatibleTypeException();
		setPegawai(pegawai.toEntity());
	}

	@Override
	@Column(name = "jumlah_bayar")
	public long getJumlahBayar() {
		return jumlahBayar;
	}

	/**
	 * Set bill count.
	 * @param jumlahbayar
	 */
	public void setJumlahBayar(long jumlahbayar) {
		this.jumlahBayar = jumlahbayar;
	}

	@Override
	@Embedded
	public TagihanValue getTagihan() {
		return tagihan;
	}

	/**
	 * Set bill.
	 * @param tagihanValue
	 */
	public void setTagihan(TagihanValue tagihanValue) {
		this.tagihan = tagihanValue;
	}

	@Override
	@JsonIgnore
	@Transient
	public int getTahun() {
		return tagihan.getTahun();
	}

	@Override
	@JsonIgnore
	@Transient
	public Month getBulan() {
		return tagihan.getBulan();
	}
	
	@Override
	@JsonIgnore
	@Transient
	public int getIdPegawai() {
		return pegawai.getId();
	}
	
	@Override
	@JsonIgnore
	@Transient
	public int getIdPelanggan() {
		return pelanggan.getId();
	}

	/**
	 * Return paying customer's profession.
	 * @return
	 */
	@JsonIgnore
	@Transient
	public String getProfesiPelanggan() {
		return pelanggan.getProfesi();
	}
	
	@Override
	@JsonIgnore
	@Transient
	public String getNamaPelanggan() {
		return pelanggan.getNama();
	}
	
	@Override
	@JsonIgnore
	@Transient
	public String getNamaPegawai() {
		return pegawai.getNama();
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
	
	@Override
	@JsonIgnore
	@Transient
	public boolean isPaid(Pembayaran last) {
		return tagihan.isPaid(last.getTagihan().toEntity());
	}
	
	@Override
	@JsonIgnore
	@Transient
	public boolean isPreceding(Pembayaran next) {
		return tagihan.isPreceding(next.getTagihan().toEntity());
	}

	@Override
	@JsonIgnore
	public PembayaranEntity toEntity() {
		return this;
	}

	@Override
	@JsonIgnore
	public PembayaranModel toModel() {
		return new PembayaranModel(this);
	}
	
	/**
	 * Converts id to String representation with specified format.
	 * @param id
	 * @return String representation of id with specified format.
	 */
	@JsonIgnore
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
	@JsonIgnore
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
	@JsonIgnore
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		PembayaranEntity other = (PembayaranEntity) obj;
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
	public static final class TagihanValue implements Tagihan, com.unitedvision.tvkabel.core.domain.Comparable {
		/** Year */
		private int tahun;
		
		/** Month */
		private Month bulan;

		/**
		 * Create empty instance.
		 */
		public TagihanValue() {
			super();
		}

		/**
		 * Create instance
		 * @param tahun
		 * @param bulan
		 */
		public TagihanValue(int tahun, Month bulan) {
			super();
			this.tahun = tahun;
			this.bulan = bulan;
		}

		@Override
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

		@Override
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

		/**
		 * Joins year and month with specified format.
		 * @return joined year and month with specified format
		 */
		@JsonIgnore
		public String join() {
			String bulanStr = String.valueOf(bulan.getValue());
			if (bulanStr.length() == 1)
				bulanStr = String.format("0%s", bulanStr);
			return String.format("%d%s", tahun, bulanStr);
		}
		
		@Override
		public void increase() {
			int bulanInt = bulan.getValue();
			bulanInt++;

			if (bulanInt > 12) {
				bulanInt = 1;
				tahun++;
			}
			
			bulan = Month.of(bulanInt);
		}

		@Override
		public void decrease() {
			int bulanInt = bulan.getValue();
			bulanInt--;

			if (bulanInt < 1) {
				bulanInt = 12;
				tahun--;
			}
			
			bulan = Month.of(bulanInt);
		}

		@Override
		public void add(int number) {
			for (int i = 1; i < number; i++)
				increase();
		}
		
		@Override
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
		public int compareWith(com.unitedvision.tvkabel.core.domain.Comparable comparer) {
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
		public boolean isPaid(TagihanValue last) {
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
		public boolean isPreceding(TagihanValue next) {
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
		public static TagihanValue create(Date date) {
			int tahun = DateUtil.getYear(date);
			Month month = DateUtil.getMonth(date);
			
			return new TagihanValue(tahun, month);
		}
		
		@Override
		@JsonIgnore
		public TagihanValue toEntity() {
			return this;
		}

		@Override
		@JsonIgnore
		public TagihanModel toModel() {
			return new TagihanModel(this);
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
			TagihanValue other = (TagihanValue) obj;
			if (bulan != other.bulan)
				return false;
			if (tahun != other.tahun)
				return false;
			return true;
		}
	}
}
