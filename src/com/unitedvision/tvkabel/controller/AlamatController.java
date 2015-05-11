package com.unitedvision.tvkabel.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.unitedvision.tvkabel.entity.Kecamatan;
import com.unitedvision.tvkabel.entity.Kelurahan;
import com.unitedvision.tvkabel.entity.Kota;
import com.unitedvision.tvkabel.exception.ApplicationException;
import com.unitedvision.tvkabel.service.KecamatanService;
import com.unitedvision.tvkabel.service.KelurahanService;
import com.unitedvision.tvkabel.service.KotaService;
import com.unitedvision.tvkabel.util.ListEntityRestMessage;

@Controller
@RequestMapping("/alamat")
public class AlamatController extends AbstractController {
	
	@Autowired
	private KelurahanService kelurahanService;
	@Autowired
	private KecamatanService kecamatanService;
	@Autowired
	private KotaService kotaService;

	@RequestMapping(value = "/kelurahan", method = RequestMethod.GET)
	public @ResponseBody ListEntityRestMessage<Kelurahan> getAllKelurahan() throws ApplicationException {
		List<Kelurahan> list = kelurahanService.getAll();
		
		return ListEntityRestMessage.createListKelurahan(list);
	}

	@RequestMapping(value = "/kelurahan/kecamatan/{kecamatanId}", method = RequestMethod.GET)
	public @ResponseBody ListEntityRestMessage<Kelurahan> getKelurahanByKecamatan(@PathVariable Integer kecamatanId) throws ApplicationException {
		Kecamatan kecamatan = kecamatanService.getOne(kecamatanId);
		List<Kelurahan> list = kelurahanService.getByKecamatan(kecamatan);
		
		return ListEntityRestMessage.createListKelurahan(list);
	}
	
	@RequestMapping(value = "/kecamatan", method = RequestMethod.GET)
	public @ResponseBody ListEntityRestMessage<Kecamatan> getAllKecamatan() throws ApplicationException {
		List<Kecamatan> list = kecamatanService.getAll();

		return ListEntityRestMessage.createListKecamatan(list);
	}

	@RequestMapping(value = "/kecamatan/kota/{kotaId}", method = RequestMethod.GET)
	public @ResponseBody ListEntityRestMessage<Kecamatan> getKecamatanByKota(@PathVariable Integer kotaId) throws ApplicationException {
		Kota kota = kotaService.getOne(kotaId);
		List<Kecamatan> list = kecamatanService.getByKota(kota);
		
		return ListEntityRestMessage.createListKecamatan(list);
	}
	
	@RequestMapping(value = "/kota", method = RequestMethod.GET)
	public @ResponseBody ListEntityRestMessage<Kota> getAllKota() throws ApplicationException {
		List<Kota> list = kotaService.getAll();
		
		return ListEntityRestMessage.createListKota(list);
	}
}
