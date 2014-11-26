package com.unitedvision.tvkabel.web.model;

import java.time.Month;

import com.unitedvision.tvkabel.core.domain.Tagihan;
import com.unitedvision.tvkabel.persistence.entity.PembayaranEntity.TagihanValue;

/**
 * Mapping of tagihan to web page.
 * 
 * @author Deddy Christoper Kakunsi
 *
 */
public class TagihanModel implements Tagihan {
	/** Year */
	private int tahun;
	
	/** Month's name */
	private String bulan;

	/**
	 * Create empty instance.
	 */
	public TagihanModel() {
		super();
	}

	/**
	 * Create instance.
	 * @param tagihanValue
	 */
	public TagihanModel(TagihanValue tagihanValue) {
		super();
		this.tahun = tagihanValue.getTahun();
		this.bulan = tagihanValue.getBulan().name();
	}
	
	@Override
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
	public Month getBulan() {
		return Month.valueOf(getBulanStr());
	}

	/**
	 * Set month.
	 * @param bulan
	 */
	public void setBulan(Month bulan) {
		this.bulan = bulan.name();
	}

	/**
	 * Return month's name.
	 * @return 
	 */
	public String getBulanStr() {
		return bulan;
	}

	/**
	 * Set month's name.
	 * @param bulan
	 */
	public void setBulanStr(String bulan) {
		this.bulan = bulan;
	}

	@Override
	public TagihanValue toEntity() {
		return new TagihanValue(tahun, Month.valueOf(bulan));
	}

	@Override
	public TagihanModel toModel() {
		return this;
	}

	@Override
	public void increase() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void decrease() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void add(int number) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void substract(int number) {
		// TODO Auto-generated method stub
		
	}
}
