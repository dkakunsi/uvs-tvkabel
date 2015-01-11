package com.unitedvision.tvkabel.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.unitedvision.tvkabel.exception.ApplicationException;
import com.unitedvision.tvkabel.exception.EntityNotExistException;
import com.unitedvision.tvkabel.persistence.entity.Pelanggan;
import com.unitedvision.tvkabel.persistence.entity.Perusahaan;
import com.unitedvision.tvkabel.persistence.entity.Pelanggan.Status;
import com.unitedvision.tvkabel.util.PageSizeUtil;
import com.unitedvision.tvkabel.util.StatusUtil;
import com.unitedvision.tvkabel.web.rest.ListPelangganRestResult;
import com.unitedvision.tvkabel.web.rest.PelangganRestResult;
import com.unitedvision.tvkabel.web.rest.RestResult;

@Controller
@RequestMapping("/api/pelanggan")
public class PelangganController extends AbstractController {
	@RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
	public @ResponseBody PelangganRestResult get(@PathVariable Integer id) {
		try {
			Pelanggan pelanggan = pelangganService.getOne(id);
			
			return PelangganRestResult.create("Berhasil!", pelanggan);
		} catch (EntityNotExistException e) {
			return PelangganRestResult.create(String.format("Gagal! %s", e.getMessage()));
		}
	}
	
	@RequestMapping(value = "/perusahaan/{perusahaan}/status/{status}", method = RequestMethod.GET)
	public @ResponseBody ListPelangganRestResult getAll(@PathVariable Integer perusahaan, @PathVariable String status) throws ApplicationException {
		final Status _status = StatusUtil.getPelangganStatus(status);
		List<Pelanggan> list = pelangganService.get(getPerusahaan(perusahaan), _status);
		
		return ListPelangganRestResult.create("Berhasil!", list);
	}
	
	@RequestMapping(value = "/perusahaan/{perusahaan}/status/{status}/page/{page}", method = RequestMethod.GET)
	public @ResponseBody ListPelangganRestResult get(@PathVariable Integer perusahaan, @PathVariable String status, @PathVariable Integer page) throws ApplicationException {
		final Status _status = StatusUtil.getPelangganStatus(status);
		List<Pelanggan> list = pelangganService.get(getPerusahaan(perusahaan), _status, page);
		long total = pelangganService.count(getPerusahaan(perusahaan), _status);
		long count = PageSizeUtil.getCounter(page, list.size());
		
		return ListPelangganRestResult.create("Berhasil!", 
				list, page, total, count);
	}

	@RequestMapping(value = "/perusahaan/{idPerusahaan}/kode/{kode}", method = RequestMethod.GET)
	public @ResponseBody PelangganRestResult getByKode(@PathVariable Integer idPerusahaan, @PathVariable String kode) {
		try {
			final Perusahaan perusahaan = getPerusahaan(idPerusahaan);
			Pelanggan pelanggan = pelangganService.getOneByKode(perusahaan, kode);

			return PelangganRestResult.create("Berhasil!", pelanggan);
		} catch (ApplicationException e) {
			return PelangganRestResult.create(e.getMessage());
		}
	}
	
	@RequestMapping(value = "/perusahaan/{perusahaan}/kode/{kode}/page/{page}", method = RequestMethod.GET)
	public @ResponseBody ListPelangganRestResult getByKode(@PathVariable Integer perusahaan, @PathVariable String kode, @PathVariable Integer page) throws ApplicationException {
		List<Pelanggan> list = pelangganService.getByKode(getPerusahaan(perusahaan), kode, page);
		long total = pelangganService.countByKode(getPerusahaan(perusahaan), kode);
		long count = PageSizeUtil.getCounter(page, list.size());
		
		return ListPelangganRestResult.create("Berhasil!", list, page, total, count);
	}
	
	@RequestMapping(value = "/perusahaan/{perusahaan}/kode/{kode}/status/{status}/page/{page}", method = RequestMethod.GET)
	public @ResponseBody ListPelangganRestResult getByKode(@PathVariable Integer perusahaan, @PathVariable String status, @PathVariable String kode, @PathVariable Integer page) throws ApplicationException {
		Status _status = StatusUtil.getPelangganStatus(status);
		List<Pelanggan> list = pelangganService.getByKode(getPerusahaan(perusahaan), _status, kode, page);
		long total = pelangganService.countByKode(getPerusahaan(perusahaan), _status, kode);
		long count = PageSizeUtil.getCounter(page, list.size());
		
		return ListPelangganRestResult.create("Berhasil!", list, page, total, count);
	}
	
	@RequestMapping(value = "/perusahaan/{idPerusahaan}/nama/{nama:.+}", method = RequestMethod.GET)
	public @ResponseBody PelangganRestResult getByNama(@PathVariable Integer idPerusahaan, @PathVariable String nama) {
		try {
			final Perusahaan perusahaan = getPerusahaan(idPerusahaan);
			Pelanggan pelanggan = pelangganService.getOneByNama(perusahaan, nama);

			return PelangganRestResult.create("Berhasil!", pelanggan);
		} catch (ApplicationException e) {
			return PelangganRestResult.create(e.getMessage());
		}
	}

	@RequestMapping(value = "/perusahaan/{perusahaan}/nama/{nama:.+}/page/{page}", method = RequestMethod.GET)
	public @ResponseBody ListPelangganRestResult getByNama(@PathVariable Integer perusahaan, @PathVariable String nama, @PathVariable Integer page) throws ApplicationException {
		List<Pelanggan> list = pelangganService.getByNama(getPerusahaan(perusahaan), nama, page);
		long total = pelangganService.countByNama(getPerusahaan(perusahaan), nama);
		long count = PageSizeUtil.getCounter(page, list.size());
		
		return ListPelangganRestResult.create("Berhasil!", list, page, total, count);
	}

	@RequestMapping(value = "/perusahaan/{perusahaan}/nama/{nama:.+}/status/{status}/page/{page}", method = RequestMethod.GET)
	public @ResponseBody ListPelangganRestResult getByNama(@PathVariable Integer perusahaan, @PathVariable String status, @PathVariable String nama, @PathVariable Integer page) throws ApplicationException {
		Status _status = StatusUtil.getPelangganStatus(status);
		List<Pelanggan> list = pelangganService.getByNama(getPerusahaan(perusahaan), _status, nama, page);
		long total = pelangganService.countByNama(getPerusahaan(perusahaan), _status, nama);
		long count = PageSizeUtil.getCounter(page, list.size());
		
		return ListPelangganRestResult.create("Berhasil!", list, page, total, count);
	}
	
	@RequestMapping(value = "/{id}/location/{latitude:.+}/{longitude:.+}", method = RequestMethod.PUT)
	public @ResponseBody RestResult setMapLocation(@PathVariable Integer id, @PathVariable float latitude, @PathVariable float longitude) {
		try {
			Pelanggan pelanggan = pelangganService.getOne(id);
			
			pelangganService.setMapLocation(pelanggan, latitude, longitude);
			
			return RestResult.create("Berhasil!");
		} catch (ApplicationException e) {
			return RestResult.create(e.getMessage());
		}
	}

	@RequestMapping(value = "/activated/master", method = RequestMethod.PUT)
	public @ResponseBody RestResult activate(@RequestBody Integer id, HttpServletResponse response) {
		try {
			Pelanggan pelanggan = pelangganService.getOne(id);

			pelangganService.activate(pelanggan);

			return RestResult.create("Berhasil!");
		} catch (ApplicationException e) {
			return RestResult.create(e.getMessage());
		}
	}

	@RequestMapping(value = "/passivated/master", method = RequestMethod.PUT)
	public @ResponseBody RestResult passivate(@RequestBody Integer id, HttpServletResponse response) {
		try {
			Pelanggan pelanggan = pelangganService.getOne(id);

			pelangganService.passivate(pelanggan);
			
			return RestResult.create("Berhasil!");
		} catch (ApplicationException e) {
			return RestResult.create(e.getMessage());
		}
	}

	@RequestMapping(value = "/banned/master", method = RequestMethod.PUT)
	public @ResponseBody RestResult ban(@RequestBody Integer id, HttpServletResponse response) {
		try {
			Pelanggan pelanggan = pelangganService.getOne(id);

			pelangganService.banned(pelanggan);
			
			return RestResult.create("Berhasil!");
		} catch (ApplicationException e) {
			return RestResult.create(e.getMessage());
		}
	}
	
	@RequestMapping(value = "/removed/master", method = RequestMethod.PUT)
	public @ResponseBody RestResult remove(@RequestBody Integer id, HttpServletResponse response) {
		try {
			Pelanggan pelanggan = pelangganService.getOne(id);
			
			pelangganService.remove(pelanggan);
			
			return RestResult.create("Berhasil!");
		} catch (ApplicationException e) {
			return RestResult.create(e.getMessage());
		}
	}

	@RequestMapping(value = "/perusahaan/{perusahaan}/master", method = RequestMethod.POST)
	public @ResponseBody RestResult simpanPelanggan(@PathVariable Integer perusahaan, @RequestBody Pelanggan model) {
		String message;
		try {
			model.setPerusahaan(getPerusahaan(perusahaan));
			pelangganService.save(model);

			message = "Berhasil!";
		} catch (ApplicationException e) {
			message = String.format("Gagal! %s", e.getMessage());
		}
		
		return RestResult.create(message);
	}
	
	@RequestMapping(value = "/{id}/tunggakan/master", method = RequestMethod.PUT)
	public @ResponseBody RestResult updateTunggakan(@PathVariable Integer id, @RequestBody Integer tunggakan) {
		try {
			Pelanggan pelanggan = pelangganService.getOne(id);
			pelanggan.setTunggakan(tunggakan);
			
			pelangganService.save(pelanggan);

			return RestResult.create("Berhasil!");
		} catch (ApplicationException e) {
			return RestResult.create(String.format("Gagal! %s", e.getMessage()));
		}
	}
}
