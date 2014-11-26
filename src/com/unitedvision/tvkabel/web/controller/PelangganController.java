package com.unitedvision.tvkabel.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.unitedvision.tvkabel.core.domain.Pelanggan;
import com.unitedvision.tvkabel.core.domain.Perusahaan;
import com.unitedvision.tvkabel.core.domain.Pelanggan.Status;
import com.unitedvision.tvkabel.exception.ApplicationException;
import com.unitedvision.tvkabel.exception.EntityNotExistException;
import com.unitedvision.tvkabel.util.PageSizeUtil;
import com.unitedvision.tvkabel.util.StatusUtil;
import com.unitedvision.tvkabel.web.model.PelangganModel;
import com.unitedvision.tvkabel.web.rest.ListPelangganRestResult;
import com.unitedvision.tvkabel.web.rest.PelangganRestResult;
import com.unitedvision.tvkabel.web.rest.RestResult;

@Controller
@RequestMapping("/api/pelanggan")
public class PelangganController extends AbstractController {
	@RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
	public @ResponseBody PelangganRestResult get(@PathVariable Integer id) {
		try {
			PelangganModel pelangganModel = pelangganService.getOne(id).toModel();
			
			return PelangganRestResult.create("Berhasil!", pelangganModel);
		} catch (EntityNotExistException e) {
			return PelangganRestResult.create(String.format("Gagal! %s", e.getMessage()));
		}
	}
	
	@RequestMapping(value = "/status/{status}", method = RequestMethod.GET)
	public @ResponseBody ListPelangganRestResult getAll(@PathVariable String status) throws ApplicationException {
		final Status _status = StatusUtil.getPelangganStatus(status);
		List<? extends Pelanggan> list = pelangganService.get(getPerusahaan(), _status);
		
		return ListPelangganRestResult.create("Berhasil!", PelangganModel.createListModel(list));
	}
	
	@RequestMapping(value = "/status/{status}/page/{page}", method = RequestMethod.GET)
	public @ResponseBody ListPelangganRestResult get(@PathVariable String status, @PathVariable Integer page) throws ApplicationException {
		final Status _status = StatusUtil.getPelangganStatus(status);
		List<? extends Pelanggan> list = pelangganService.get(getPerusahaan(), _status, page);
		long total = pelangganService.count(getPerusahaan(), _status);
		long count = PageSizeUtil.getCounter(page, list.size());
		
		return ListPelangganRestResult.create("Berhasil!", 
				PelangganModel.createListModel(list), page, total, count);
	}

	@RequestMapping(value = "/kode/{kode}", method = RequestMethod.GET)
	public @ResponseBody PelangganRestResult getByKode(@PathVariable String kode) {
		try {
			final Perusahaan perusahaan = getPerusahaan();
			PelangganModel pelangganModel = pelangganService.getOneByKode(perusahaan, kode).toModel();

			return PelangganRestResult.create("Berhasil!", pelangganModel);
		} catch (ApplicationException e) {
			return PelangganRestResult.create(e.getMessage());
		}
	}
	
	@RequestMapping(value = "/kode/{kode}/page/{page}", method = RequestMethod.GET)
	public @ResponseBody ListPelangganRestResult getByKode(@PathVariable String kode, @PathVariable Integer page) throws ApplicationException {
		List<? extends Pelanggan> list = pelangganService.getByKode(getPerusahaan(), kode, page);
		long total = pelangganService.countByKode(getPerusahaan(), kode);
		long count = PageSizeUtil.getCounter(page, list.size());
		
		return ListPelangganRestResult.create("Berhasil!", PelangganModel.createListModel(list), page, total, count);
	}
	
	@RequestMapping(value = "/kode/{kode}/status/{status}/page/{page}", method = RequestMethod.GET)
	public @ResponseBody ListPelangganRestResult getByKode(@PathVariable String status, @PathVariable String kode, @PathVariable Integer page) throws ApplicationException {
		Status _status = StatusUtil.getPelangganStatus(status);
		List<? extends Pelanggan> list = pelangganService.getByKode(getPerusahaan(), _status, kode, page);
		long total = pelangganService.countByKode(getPerusahaan(), _status, kode);
		long count = PageSizeUtil.getCounter(page, list.size());
		
		return ListPelangganRestResult.create("Berhasil!", PelangganModel.createListModel(list), page, total, count);
	}
	
	@RequestMapping(value = "/nama/{nama:.+}", method = RequestMethod.GET)
	public @ResponseBody PelangganRestResult getByNama(@PathVariable String nama) {
		try {
			final Perusahaan perusahaan = getPerusahaan();
			PelangganModel pelangganModel = pelangganService.getOneByNama(perusahaan, nama).toModel();

			return PelangganRestResult.create("Berhasil!", pelangganModel);
		} catch (ApplicationException e) {
			return PelangganRestResult.create(e.getMessage());
		}
	}

	@RequestMapping(value = "/nama/{nama:.+}/page/{page}", method = RequestMethod.GET)
	public @ResponseBody ListPelangganRestResult getByNama(@PathVariable String nama, @PathVariable Integer page) throws ApplicationException {
		List<? extends Pelanggan> list = pelangganService.getByNama(getPerusahaan(), nama, page);
		long total = pelangganService.countByNama(getPerusahaan(), nama);
		long count = PageSizeUtil.getCounter(page, list.size());
		
		return ListPelangganRestResult.create("Berhasil!", PelangganModel.createListModel(list), page, total, count);
	}

	@RequestMapping(value = "/nama/{nama:.+}/status/{status}/page/{page}", method = RequestMethod.GET)
	public @ResponseBody ListPelangganRestResult getByNama(@PathVariable String status, @PathVariable String nama, @PathVariable Integer page) throws ApplicationException {
		Status _status = StatusUtil.getPelangganStatus(status);
		List<? extends Pelanggan> list = pelangganService.getByNama(getPerusahaan(), _status, nama, page);
		long total = pelangganService.countByNama(getPerusahaan(), _status, nama);
		long count = PageSizeUtil.getCounter(page, list.size());
		
		return ListPelangganRestResult.create("Berhasil!", PelangganModel.createListModel(list), page, total, count);
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

	@RequestMapping(value = "/master", method = RequestMethod.POST)
	public @ResponseBody RestResult simpanPelanggan(@RequestBody PelangganModel model) {
		String message;
		try {
			model.setPerusahaan(getPerusahaan());
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
