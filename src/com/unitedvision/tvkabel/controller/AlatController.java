package com.unitedvision.tvkabel.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.unitedvision.tvkabel.entity.Alat;
import com.unitedvision.tvkabel.exception.ApplicationException;
import com.unitedvision.tvkabel.service.AlatService;
import com.unitedvision.tvkabel.util.EntityRestMessage;
import com.unitedvision.tvkabel.util.ListEntityRestMessage;
import com.unitedvision.tvkabel.util.RestMessage;

@Controller
@RequestMapping("/alat")
public class AlatController extends AbstractController {
	
	@Autowired
	private AlatService alatService;
	
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody RestMessage add(@RequestBody Alat alat) throws ApplicationException {
		alatService.add(alat);
		
		return RestMessage.success();
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	public @ResponseBody RestMessage save(@RequestBody Alat alat) throws ApplicationException {
		alatService.save(alat);
		
		return RestMessage.success();
	}
	
	@RequestMapping(value ="/subtitution/{idLama}/{idBaru}", method = RequestMethod.PUT)
	public @ResponseBody RestMessage substitution(@PathVariable Integer idLama, @PathVariable Integer idBaru) throws ApplicationException {
		alatService.subtitute(idLama, idBaru);
		
		return RestMessage.success();
	}
		
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public @ResponseBody RestMessage remove(@PathVariable Integer id) throws ApplicationException {
		alatService.remove(id);
		
		return RestMessage.success();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public @ResponseBody EntityRestMessage<Alat> get(@PathVariable Integer id) throws ApplicationException {
		Alat alat = alatService.get(id);
		
		return EntityRestMessage.create(alat);
	}
	
	@RequestMapping(value = "/source/{idSource}", method = RequestMethod.GET)
	public @ResponseBody ListEntityRestMessage<Alat> getBySource(@PathVariable Integer idSource) throws ApplicationException {
		List<Alat> list = alatService.getBySource(idSource);
		
		return ListEntityRestMessage.createListAlat(list);
	}
	
	@RequestMapping(value = "/alamat/{idKelurahan}/{lingkungan}", method = RequestMethod.GET)
	public @ResponseBody ListEntityRestMessage<Alat> getByAlamat(@PathVariable Integer idKelurahan, @PathVariable Integer lingkungan) throws ApplicationException {
		List<Alat> list = alatService.getByAlamat(getPerusahaan(), idKelurahan, lingkungan);
		
		return ListEntityRestMessage.createListAlat(list);
	}
}
