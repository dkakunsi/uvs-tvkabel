package com.unitedvision.tvkabel.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.unitedvision.tvkabel.entity.History;
import com.unitedvision.tvkabel.exception.ApplicationException;
import com.unitedvision.tvkabel.service.HistoryService;
import com.unitedvision.tvkabel.util.ListEntityRestMessage;

@Controller
@RequestMapping("/pelanggan/history")
public class HistoryController extends AbstractController {

	@Autowired
	private HistoryService historyService;
	
	@RequestMapping(value = "/perusahaan/{tanggalAwal}/{tanggalAkhir}", method = RequestMethod.GET)
	public @ResponseBody ListEntityRestMessage<History> get(@PathVariable String tanggalAwal, @PathVariable String tanggalAkhir) throws ApplicationException {
		final Date awal = toDate(tanggalAwal);
		final Date akhir = toDate(tanggalAkhir);

		List<History> list = historyService.get(getPerusahaan(), awal, akhir);
		
		return ListEntityRestMessage.createListHistory(list);
	}
	
	@RequestMapping(value = "/pelanggan/{idPelanggan}/{tanggalAwal}/{tanggalAkhir}", method = RequestMethod.GET)
	public @ResponseBody ListEntityRestMessage<History> get(@PathVariable Integer idPelanggan, @PathVariable String tanggalAwal, @PathVariable String tanggalAkhir) throws ApplicationException {
		final Date awal = toDate(tanggalAwal);
		final Date akhir = toDate(tanggalAkhir);

		List<History> list = historyService.get(idPelanggan, awal, akhir);
		
		return ListEntityRestMessage.createListHistory(list);
	}
	
	@RequestMapping(value = "/pelanggan/{idPelanggan}", method = RequestMethod.GET)
	public @ResponseBody ListEntityRestMessage<History> get(@PathVariable Integer idPelanggan) throws ApplicationException {
		List<History> list = historyService.get(idPelanggan);
		
		return ListEntityRestMessage.createListHistory(list);
	}
}
