package com.unitedvision.tvkabel.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.unitedvision.tvkabel.entity.Pegawai;
import com.unitedvision.tvkabel.exception.ApplicationException;
import com.unitedvision.tvkabel.util.EntityRestMessage;
import com.unitedvision.tvkabel.util.ListEntityRestMessage;
import com.unitedvision.tvkabel.util.RestMessage;

@Controller
@RequestMapping("/pegawai")
public class PegawaiController extends AbstractController {

	@RequestMapping(method = RequestMethod.PUT)
	public @ResponseBody RestMessage save(@RequestBody Pegawai pegawai) throws ApplicationException {
		pegawai.setPerusahaan(getPerusahaan());
		
		pegawaiService.save(pegawai);

		return RestMessage.success();
	}

	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody RestMessage add(@RequestBody Pegawai pegawai) throws ApplicationException {
		pegawai.setPerusahaan(getPerusahaan());
		
		pegawaiService.add(pegawai);

		return RestMessage.success();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public @ResponseBody RestMessage remove(@PathVariable Integer id) throws ApplicationException {
		pegawaiService.remove(id);
		
		return RestMessage.success();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public @ResponseBody EntityRestMessage<Pegawai> getById(@PathVariable final Integer id) throws ApplicationException {
		Pegawai pegawai = pegawaiService.getOne(id);
		
		return EntityRestMessage.create(pegawai);
	}

	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody ListEntityRestMessage<Pegawai> getByPerusahaan() throws ApplicationException {
		List<Pegawai> list = pegawaiService.get(getPerusahaan());

		return ListEntityRestMessage.createListPegawai(list);
	}
	
	@RequestMapping(value = "/kode/{kode}", method = RequestMethod.GET)
	public @ResponseBody EntityRestMessage<Pegawai> getByKode(@PathVariable final String kode) throws ApplicationException {
		Pegawai pegawai = pegawaiService.getOneByKode(getPerusahaan(), kode);
		
		return EntityRestMessage.create(pegawai);
	}
	
	@RequestMapping(value = "/list/kode/{kode}", method = RequestMethod.GET)
	public @ResponseBody ListEntityRestMessage<Pegawai> getListByKode(@PathVariable String kode) throws ApplicationException {
		List<Pegawai> list = pegawaiService.getByKode(getPerusahaan(), kode);

		return ListEntityRestMessage.createListPegawai(list);
	}
	
	@RequestMapping(value = "/nama/{nama}", method = RequestMethod.GET)
	public @ResponseBody EntityRestMessage<Pegawai> getByNama(@PathVariable final String nama) throws ApplicationException {
		Pegawai pegawai = pegawaiService.getOneByNama(getPerusahaan(), nama);
		
		return EntityRestMessage.create(pegawai);
	}
	
	@RequestMapping(value = "/list/nama/{nama}", method = RequestMethod.GET)
	public @ResponseBody ListEntityRestMessage<Pegawai> getListByNama(@PathVariable String nama) throws ApplicationException {
		List<Pegawai> list = pegawaiService.getByNama(getPerusahaan(), nama);
		
		return ListEntityRestMessage.createListPegawai(list);
	}
}
