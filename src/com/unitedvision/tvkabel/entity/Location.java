package com.unitedvision.tvkabel.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public final class Location {

	private float latitude;
	private float longitude;
	
	@Column(name = "latitude")
	public float getLatitude() {
		return latitude;
	}
	
	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}
	
	@Column(name = "longitude")
	public float getLongitude() {
		return longitude;
	}
	
	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}
}
