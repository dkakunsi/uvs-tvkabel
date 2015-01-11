package com.unitedvision.tvkabel.web.controller;

import org.springframework.beans.factory.annotation.Autowired;

import com.unitedvision.tvkabel.core.service.PegawaiService;
import com.unitedvision.tvkabel.core.service.PelangganService;
import com.unitedvision.tvkabel.core.service.PerusahaanService;
import com.unitedvision.tvkabel.exception.ApplicationException;
import com.unitedvision.tvkabel.exception.EntityNotExistException;
import com.unitedvision.tvkabel.exception.UnauthenticatedAccessException;
import com.unitedvision.tvkabel.persistence.domain.Pegawai;
import com.unitedvision.tvkabel.persistence.domain.Pelanggan;
import com.unitedvision.tvkabel.persistence.domain.Perusahaan;
import com.unitedvision.tvkabel.persistence.domain.Pelanggan.Status;
import com.unitedvision.tvkabel.security.SpringAuthenticationBasedAuthorizationProvider;

public abstract class AbstractController {
	@Autowired
	protected SpringAuthenticationBasedAuthorizationProvider authorizationProvider;
	@Autowired
	protected PelangganService pelangganService;
	@Autowired
	protected PegawaiService pegawaiService;
	@Autowired
	protected PerusahaanService perusahaanService;
	
	protected Perusahaan getPerusahaan() throws UnauthenticatedAccessException {
		return  authorizationProvider.getPerusahaan();
	}
	
	protected Perusahaan getPerusahaan(int idPerusahaan) throws EntityNotExistException {
		return perusahaanService.getOne(idPerusahaan);
	}
	
	protected Pegawai getPegawai() {
		return  authorizationProvider.getPegawai();
	}
	
	protected Status createStatus(String s) {
		if (s != null && !(s.equals("")) && !(s.equals("AKTIF")))
			return Status.valueOf(s.toUpperCase());
		return Status.AKTIF;
	}
	
	protected Pelanggan createPelanggan(String searchBy, String query) throws ApplicationException {
		Perusahaan perusahaan = getPerusahaan();
		
		if (searchBy.contains("nama"))
			return pelangganService.getOneByNama(perusahaan, query);
		return pelangganService.getOneByKode(perusahaan, query);
	}
	
	protected Pelanggan createPelanggan(String searchBy, String query, int idPerusahaan) throws ApplicationException {
		Perusahaan perusahaan = getPerusahaan(idPerusahaan);
		
		if (searchBy.contains("nama"))
			return pelangganService.getOneByNama(perusahaan, query);
		return pelangganService.getOneByKode(perusahaan, query);
	}
	
	protected Pegawai createPegawai(String searchBy, String query) throws ApplicationException {
		Perusahaan perusahaan = getPerusahaan();
		
		if (searchBy.toLowerCase().contains("nama"))
			return pegawaiService.getOneByNama(perusahaan, query);
		return pegawaiService.getOneByKode(perusahaan, query);
	}
	
	protected Pegawai createPegawai(String searchBy, String query, int idPerusahaan) throws ApplicationException {
		Perusahaan perusahaan = getPerusahaan(idPerusahaan);
		
		if (searchBy.toLowerCase().contains("nama"))
			return pegawaiService.getOneByNama(perusahaan, query);
		return pegawaiService.getOneByKode(perusahaan, query);
	}
}
