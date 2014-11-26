package com.unitedvision.tvkabel.core.domain;

import java.time.Month;

import com.unitedvision.tvkabel.persistence.entity.PembayaranEntity.TagihanValue;
import com.unitedvision.tvkabel.web.model.TagihanModel;

/**
 * Root of tagihan.
 * 
 * @author Deddy Christoper Kakunsi
 *
 */
public interface Tagihan extends Root<TagihanValue, TagihanModel>{
	/**
	 * Return year.
	 * @return tahun
	 */
	int getTahun();
	
	/**
	 * Return month.
	 * @return bulan
	 */
	Month getBulan();

	/**
	 * Increase tagihan.
	 */
	void increase();

	/**
	 * Decrease tagihan.
	 */
	void decrease();

	/**
	 * Add tagihan with the given number.
	 * @param number
	 */
	void add(int number);

	/**
	 * Substract tagihan with the given number.
	 * @param number
	 */
	void substract(int number);
}
