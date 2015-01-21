package com.unitedvision.tvkabel.core.service;

import java.util.List;

import com.unitedvision.tvkabel.exception.EntityNotExistException;
import com.unitedvision.tvkabel.persistence.entity.Kota;

public interface KotaService extends Service<Kota> {
	List<Kota> getAll() throws EntityNotExistException;
}
