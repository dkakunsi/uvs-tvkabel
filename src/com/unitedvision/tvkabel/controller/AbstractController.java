package com.unitedvision.tvkabel.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.unitedvision.tvkabel.util.DateUtil;
import com.unitedvision.tvkabel.entity.Pegawai;
import com.unitedvision.tvkabel.entity.Pelanggan;
import com.unitedvision.tvkabel.entity.Perusahaan;
import com.unitedvision.tvkabel.entity.Pelanggan.Status;
import com.unitedvision.tvkabel.exception.ApplicationException;
import com.unitedvision.tvkabel.exception.UnauthenticatedAccessException;
import com.unitedvision.tvkabel.security.SpringAuthenticationBasedAuthorizationProvider;
import com.unitedvision.tvkabel.service.PegawaiService;
import com.unitedvision.tvkabel.service.PelangganService;

public abstract class AbstractController {
	
	@Autowired
	protected SpringAuthenticationBasedAuthorizationProvider authorizationProvider;
	@Autowired
	protected PelangganService pelangganService;
	@Autowired
	protected PegawaiService pegawaiService;
	
	protected Perusahaan getPerusahaan() throws UnauthenticatedAccessException {
		return  authorizationProvider.getPerusahaan();
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
		
	protected Pegawai createPegawai(String searchBy, String query) throws ApplicationException {
		Perusahaan perusahaan = getPerusahaan();

		if (searchBy.toLowerCase().contains("nama"))
			return pegawaiService.getOneByNama(perusahaan, query);
		return pegawaiService.getOneByKode(perusahaan, query);
	}
	
	protected Date toDate(String tanggal) {
		return DateUtil.getDate(tanggal, "-");
	}
}
