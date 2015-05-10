package com.unitedvision.tvkabel.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.unitedvision.tvkabel.entity.Pelanggan;
import com.unitedvision.tvkabel.entity.Pelanggan.Status;
import com.unitedvision.tvkabel.exception.ApplicationException;
import com.unitedvision.tvkabel.util.EntityRestMessage;
import com.unitedvision.tvkabel.util.ListEntityRestMessage;
import com.unitedvision.tvkabel.util.RestMessage;

@Controller
@RequestMapping("/pelanggan")
public class PelangganController extends AbstractController {
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public @ResponseBody EntityRestMessage<Pelanggan> get(@PathVariable Integer id) throws ApplicationException {
		Pelanggan pelanggan = pelangganService.getOne(id);
		return EntityRestMessage.create(pelanggan);
	}
	
	@RequestMapping(value = "/status/{status}", method = RequestMethod.GET)
	public @ResponseBody ListEntityRestMessage<Pelanggan> getByPerusahaanAndStatus(@PathVariable String status) throws ApplicationException {
		final Status _status = Status.get(status);
		List<Pelanggan> list = pelangganService.get(getPerusahaan(), _status);
		return ListEntityRestMessage.createListPelanggan(list);
	}

	@RequestMapping(value = "/kode/{kode}", method = RequestMethod.GET)
	public @ResponseBody EntityRestMessage<Pelanggan> getByKode(@PathVariable String kode) throws ApplicationException {
		Pelanggan pelanggan = pelangganService.getOneByKode(getPerusahaan(), kode);
		return EntityRestMessage.create(pelanggan);
	}
	
	@RequestMapping(value = "/list/kode/{kode}", method = RequestMethod.GET)
	public @ResponseBody ListEntityRestMessage<Pelanggan> getListByKode(@PathVariable String kode) throws ApplicationException {
		List<Pelanggan> list = pelangganService.getByKode(getPerusahaan(), kode);
		return ListEntityRestMessage.createListPelanggan(list);
	}

	@RequestMapping(value = "/list/kode/{kode}/status/{status}", method = RequestMethod.GET)
	public @ResponseBody ListEntityRestMessage<Pelanggan> getByKode(@PathVariable String kode, @PathVariable String status) throws ApplicationException {
		final Status _status = Status.get(status);
		List<Pelanggan> list = pelangganService.getByKode(getPerusahaan(), kode, _status);
		return ListEntityRestMessage.createListPelanggan(list);
	}
	
	@RequestMapping(value = "/nama/{nama:.+}", method = RequestMethod.GET)
	public @ResponseBody EntityRestMessage<Pelanggan> getByNama(@PathVariable String nama) throws ApplicationException {
		Pelanggan pelanggan = pelangganService.getOneByNama(getPerusahaan(), nama);
		return EntityRestMessage.create(pelanggan);
	}

	@RequestMapping(value = "/list/nama/{nama:.+}", method = RequestMethod.GET)
	public @ResponseBody ListEntityRestMessage<Pelanggan> getListByNama(@PathVariable String nama) throws ApplicationException {
		List<Pelanggan> list = pelangganService.getByNama(getPerusahaan(), nama);
		return ListEntityRestMessage.createListPelanggan(list);
	}

	@RequestMapping(value = "/list/nama/{nama:.+}/status/{status}", method = RequestMethod.GET)
	public @ResponseBody ListEntityRestMessage<Pelanggan> getByNama(@PathVariable String nama, @PathVariable String status) throws ApplicationException {
		final Status _status = Status.get(status);
		List<Pelanggan> list = pelangganService.getByNama(getPerusahaan(), nama, _status);
		return ListEntityRestMessage.createListPelanggan(list);
	}

	@RequestMapping(value = "/list/nomor/{nomor}", method = RequestMethod.GET)
	public @ResponseBody ListEntityRestMessage<Pelanggan> getByNomor(@PathVariable Integer nomor) throws ApplicationException {
		List<Pelanggan> list = pelangganService.get(getPerusahaan(), nomor);
		return ListEntityRestMessage.createListPelanggan(list);
	}

	@RequestMapping(value = "/list/nomor/{nomor}/status/{status}", method = RequestMethod.GET)
	public @ResponseBody ListEntityRestMessage<Pelanggan> getByNomor(@PathVariable String status, @PathVariable Integer nomor) throws ApplicationException {
		final Status _status = Status.get(status);
		List<Pelanggan> list = pelangganService.get(getPerusahaan(), nomor, _status);
		return ListEntityRestMessage.createListPelanggan(list);
	}
	
	@RequestMapping(value = "/location/{id}/{latitude:.+}/{longitude:.+}", method = RequestMethod.PUT)
	public @ResponseBody RestMessage setLocation(@PathVariable Integer id, @PathVariable float latitude, @PathVariable float longitude) throws ApplicationException {
		Pelanggan pelanggan = pelangganService.getOne(id);
		pelangganService.setMapLocation(pelanggan, latitude, longitude);
		return RestMessage.success();
	}

	@RequestMapping(value = "/activation/{id}/pesan/{pesan}", method = RequestMethod.PUT)
	public @ResponseBody RestMessage activate(@PathVariable Integer id, @PathVariable String pesan) throws ApplicationException {
		Pelanggan pelanggan = pelangganService.getOne(id);
		pelangganService.activate(pelanggan, pesan);
		return RestMessage.success();
	}

	@RequestMapping(value = "/passivation/{id}/pesan/{pesan}", method = RequestMethod.PUT)
	public @ResponseBody RestMessage passivate(@PathVariable Integer id, @PathVariable String pesan) throws ApplicationException {
		Pelanggan pelanggan = pelangganService.getOne(id);
		pelangganService.passivate(pelanggan, pesan);
		return RestMessage.success();
	}

	@RequestMapping(value = "/ban/{id}/pesan/{pesan}", method = RequestMethod.PUT)
	public @ResponseBody RestMessage ban(@PathVariable Integer id, @PathVariable String pesan) throws ApplicationException {
		Pelanggan pelanggan = pelangganService.getOne(id);
		pelangganService.banned(pelanggan, pesan);
		return RestMessage.success();
	}

	@RequestMapping(value = "/free/{id}/pesan/{pesan}", method = RequestMethod.PUT)
	public @ResponseBody RestMessage free(@PathVariable Integer id, @PathVariable String pesan) throws ApplicationException {
		Pelanggan pelanggan = pelangganService.getOne(id);
		pelangganService.free(pelanggan, pesan);
		return RestMessage.success();
	}
	
	@RequestMapping(value = "/removed/{id}", method = RequestMethod.PUT)
	public @ResponseBody RestMessage remove(@PathVariable Integer id) throws ApplicationException {
		Pelanggan pelanggan = pelangganService.getOne(id);
		pelangganService.remove(pelanggan);
		return RestMessage.success();
	}

	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody RestMessage save(@RequestBody Pelanggan pelanggan) throws ApplicationException {
		pelanggan.setPerusahaan(getPerusahaan());
		pelangganService.save(pelanggan);
		return RestMessage.success();
	}
	
	@RequestMapping(value = "/tunggakan/{id}", method = RequestMethod.PUT)
	public @ResponseBody RestMessage updateTunggakan(@PathVariable Integer id, @RequestBody Integer tunggakan) throws ApplicationException {
		Pelanggan pelanggan = pelangganService.getOne(id);
		pelanggan.setTunggakan(tunggakan);
		pelangganService.save(pelanggan);
		return RestMessage.success();
	}
}
