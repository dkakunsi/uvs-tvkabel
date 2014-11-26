package com.unitedvision.tvkabel.core.service;

import java.util.List;

import com.unitedvision.tvkabel.core.domain.Kota;

public interface KotaService extends Service<Kota> {
	List<? extends Kota> getAll();
}
