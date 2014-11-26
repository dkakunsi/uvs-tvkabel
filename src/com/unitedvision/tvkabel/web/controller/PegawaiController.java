package com.unitedvision.tvkabel.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.unitedvision.tvkabel.core.domain.Pegawai;
import com.unitedvision.tvkabel.exception.ApplicationException;
import com.unitedvision.tvkabel.exception.EntityNotExistException;
import com.unitedvision.tvkabel.util.PageSizeUtil;
import com.unitedvision.tvkabel.web.model.PegawaiModel;
import com.unitedvision.tvkabel.web.rest.ListPegawaiRestResult;
import com.unitedvision.tvkabel.web.rest.PegawaiRestResult;
import com.unitedvision.tvkabel.web.rest.RestResult;

@Controller
@RequestMapping("/api/pegawai")
public class PegawaiController extends AbstractController {
	@RequestMapping(value = "", method = RequestMethod.GET)
	public @ResponseBody ListPegawaiRestResult getAll() throws ApplicationException {
		List<? extends Pegawai> list = pegawaiService.get(getPerusahaan());
		
		return ListPegawaiRestResult.create("Berhasil!", PegawaiModel.createListModel(list));
	}	
	
	@RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
	public @ResponseBody PegawaiRestResult getById(@PathVariable final Integer id) {
		try {
			return PegawaiRestResult.create("Berhasil!", pegawaiService.getOne(id).toModel());
		} catch (EntityNotExistException e) {
			return PegawaiRestResult.create(e.getMessage());
		}
	}
	
	@RequestMapping(value = "/kode/{kode}", method = RequestMethod.GET)
	public @ResponseBody PegawaiRestResult getByKode(@PathVariable final String kode) throws ApplicationException {
		try {
			return PegawaiRestResult.create("Berhasil!", pegawaiService.getOneByKode(getPerusahaan(), kode).toModel());
		} catch (EntityNotExistException e) {
			return PegawaiRestResult.create(e.getMessage());
		}
	}
	
	@RequestMapping(value = "/kode/{kode}/page/{page}", method = RequestMethod.GET)
	public @ResponseBody ListPegawaiRestResult getByKode(@PathVariable String kode, @PathVariable Integer page) throws ApplicationException {
		List<? extends Pegawai> list = pegawaiService.getByKode(getPerusahaan(), kode, page);
		long total = pegawaiService.countByKode(getPerusahaan(), kode);
		long count = PageSizeUtil.getCounter(page, list.size());
		
		return ListPegawaiRestResult.create("Berhasil!", PegawaiModel.createListModel(list), page, total, count);
	}
	
	@RequestMapping(value = "/nama/{nama}", method = RequestMethod.GET)
	public @ResponseBody PegawaiRestResult getByNama(@PathVariable final String nama) throws ApplicationException {
		try {
			return PegawaiRestResult.create("Berhasil!", pegawaiService.getOneByNama(getPerusahaan(), nama).toModel());
		} catch (EntityNotExistException e) {
			return PegawaiRestResult.create(e.getMessage());
		}
	}
	
	@RequestMapping(value = "/nama/{nama}/page/{page}", method = RequestMethod.GET)
	public @ResponseBody ListPegawaiRestResult getByNama(@PathVariable String nama, @PathVariable Integer page) throws ApplicationException {
		List<? extends Pegawai> list = pegawaiService.getByNama(getPerusahaan(), nama, page);
		long total = pegawaiService.countByNama(getPerusahaan(), nama);
		long count = PageSizeUtil.getCounter(page, list.size());
		
		return ListPegawaiRestResult.create("Berhasil!", PegawaiModel.createListModel(list), page, total, count);
	}

	@RequestMapping(value = "/master", method = RequestMethod.POST)
	public @ResponseBody RestResult simpanPegawai(@RequestBody PegawaiModel pegawai) throws ApplicationException {
		pegawai.setPerusahaan(getPerusahaan());

		String message;
		try {
			pegawaiService.save(pegawai);

			message = "Berhasil!";
		} catch (ApplicationException e) {
			message = String.format("Gagal! %s", e.getMessage());
		}
		
		return RestResult.create(message);
	}

	@RequestMapping(value = "/removed/master", method = RequestMethod.POST)
	public @ResponseBody RestResult remove(@RequestBody Integer id, HttpServletResponse response) {
		try {
			Pegawai pegawai = pegawaiService.getOne(id);
			
			pegawaiService.remove(pegawai);
			
			return RestResult.create("Pegawai BERHASIL dihapus");
		} catch (ApplicationException e) {
			return RestResult.create(e.getMessage());
		}
	}
	
	@RequestMapping(value = "/active", method = RequestMethod.GET)
	public @ResponseBody PegawaiRestResult getActive() {
		return PegawaiRestResult.create("Berhasil!", getPegawai().toModel());
	}
}
