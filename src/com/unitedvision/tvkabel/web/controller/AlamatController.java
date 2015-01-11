package com.unitedvision.tvkabel.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.unitedvision.tvkabel.core.service.KecamatanService;
import com.unitedvision.tvkabel.core.service.KelurahanService;
import com.unitedvision.tvkabel.core.service.KotaService;
import com.unitedvision.tvkabel.domain.Kecamatan;
import com.unitedvision.tvkabel.domain.Kelurahan;
import com.unitedvision.tvkabel.domain.Kota;
import com.unitedvision.tvkabel.exception.EntityNotExistException;
import com.unitedvision.tvkabel.web.rest.ListKecamatanRestResult;
import com.unitedvision.tvkabel.web.rest.ListKelurahanRestResult;
import com.unitedvision.tvkabel.web.rest.ListKotaRestResult;

@Controller
@RequestMapping("/api/alamat")
public class AlamatController extends AbstractController {
	@Autowired
	private KelurahanService kelurahanService;
	@Autowired
	private KecamatanService kecamatanService;
	@Autowired
	private KotaService kotaService;

	@RequestMapping(value = "/kelurahan", method = RequestMethod.GET)
	public @ResponseBody ListKelurahanRestResult getAllKelurahan() {
		List<Kelurahan> list = kelurahanService.getAll();
		
		return ListKelurahanRestResult.create("Berhasil!", list);
	}

	@RequestMapping(value = "/kelurahan/kecamatan/{kecamatanId}", method = RequestMethod.GET)
	public @ResponseBody ListKelurahanRestResult getKelurahanByKecamatan(@PathVariable Integer kecamatanId) {
		try {
			Kecamatan kecamatan = kecamatanService.getOne(kecamatanId);
			List<Kelurahan> list = kelurahanService.getByKecamatan(kecamatan);
			
			return ListKelurahanRestResult.create("Berhasil!", list);
		} catch (EntityNotExistException e) {
			return ListKelurahanRestResult.create(String.format("Gagal! %s", e.getMessage()));
		}
	}
	
	@RequestMapping(value = "/kecamatan", method = RequestMethod.GET)
	public @ResponseBody ListKecamatanRestResult getAllKecamatan() {
		List<Kecamatan> list = kecamatanService.getAll();
		
		return ListKecamatanRestResult.create("Berhasil!", list);
	}

	@RequestMapping(value = "/kecamatan/kota/{kotaId}", method = RequestMethod.GET)
	public @ResponseBody ListKecamatanRestResult getKecamatanByKota(@PathVariable Integer kotaId) {
		try {
			Kota kota = kotaService.getOne(kotaId);
			List<Kecamatan> list = kecamatanService.getByKota(kota);
			
			return ListKecamatanRestResult.create("Berhasil!", list);
		} catch (EntityNotExistException e) {
			return ListKecamatanRestResult.create(String.format("Gagal! %s", e.getMessage()));
		}
	}
	
	@RequestMapping(value = "/kota", method = RequestMethod.GET)
	public @ResponseBody ListKotaRestResult getAllKota() {
		List<Kota> list = kotaService.getAll();
		
		return ListKotaRestResult.create("Berhasil!", list);
	}
}
