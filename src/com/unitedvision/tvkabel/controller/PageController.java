package com.unitedvision.tvkabel.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.unitedvision.tvkabel.entity.Perusahaan;
import com.unitedvision.tvkabel.exception.ApplicationException;
import com.unitedvision.tvkabel.service.KelurahanService;
import com.unitedvision.tvkabel.service.PerusahaanService;
import com.unitedvision.tvkabel.util.DateUtil;
import com.unitedvision.tvkabel.util.RestMessage;

@Controller
public class PageController extends AbstractController {
	@Autowired
	private KelurahanService kelurahanService;
	@Autowired
	protected PerusahaanService perusahaanService;
	
	@RequestMapping(value = "/admin/tunggakan/recount/{kode}", method = RequestMethod.GET)
	public @ResponseBody RestMessage recountTunggakan(@PathVariable String kode, Map<String, Object> model) throws ApplicationException {
		pelangganService.recountTunggakan();
		return RestMessage.success();
	}
	
	@RequestMapping(value = "/admin/tunggakan/recount/{kode}/{tanggal}", method = RequestMethod.GET)
	public @ResponseBody RestMessage recountTunggakanWithTanggal(@PathVariable String kode, @PathVariable String tanggal, Map<String, Object> model) throws ApplicationException {
		pelangganService.recountTunggakan(tanggal);
		return RestMessage.success();
	}

	@RequestMapping(value = "/admin/tunggakan/recount/now/{kode}", method = RequestMethod.GET)
	public @ResponseBody RestMessage recountTunggakanNow(@PathVariable String kode, Map<String, Object> model) throws ApplicationException {
		pelangganService.recountTunggakan(DateUtil.getSimpleNowInString());
		return RestMessage.success();
	}
	
	@RequestMapping(value = "/admin/reset/kode/{kode}/{idPerusahaan}", method = RequestMethod.GET)
	public @ResponseBody RestMessage resetAllKode(@PathVariable String kode, @PathVariable Integer idPerusahaan) throws ApplicationException {
		Perusahaan perusahaan = perusahaanService.getOne(idPerusahaan);
		pelangganService.resetKode(perusahaan);
		return RestMessage.success();
	}
}
