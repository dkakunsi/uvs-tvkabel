package com.unitedvision.tvkabel.core.service;

import java.util.List;

import com.unitedvision.tvkabel.persistence.domain.Kota;

public interface KotaService extends Service<Kota> {
	List<Kota> getAll();
}
