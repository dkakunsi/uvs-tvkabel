package com.unitedvision.tvkabel.service;

import java.util.List;

import com.unitedvision.tvkabel.entity.Kota;
import com.unitedvision.tvkabel.exception.EntityNotExistException;

public interface KotaService extends Service<Kota> {
	List<Kota> getAll() throws EntityNotExistException;
}
