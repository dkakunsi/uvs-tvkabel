package com.unitedvision.tvkabel.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.unitedvision.tvkabel.entity.Operator;
import com.unitedvision.tvkabel.entity.Perusahaan;
import com.unitedvision.tvkabel.entity.Pelanggan.Status;
import com.unitedvision.tvkabel.exception.ApplicationException;
import com.unitedvision.tvkabel.service.PerusahaanService;
import com.unitedvision.tvkabel.util.EntityRestMessage;
import com.unitedvision.tvkabel.util.ListEntityRestMessage;
import com.unitedvision.tvkabel.util.RestMessage;

@Controller
@RequestMapping ("/perusahaan")
public class PerusahaanController extends AbstractController {
	
	@Autowired
	private PerusahaanService perusahaanService;

	@RequestMapping(value = "/daftar", method = RequestMethod.POST)
	public @ResponseBody RestMessage registrasi(@RequestBody Perusahaan perusahaan, Map<String, Object> model) throws ApplicationException {
		final Operator op = perusahaanService.regist(perusahaan);
		model.put("operator", op);

		return RestMessage.success();
	}
	
	@RequestMapping(value = "/location/{id}/{latitude:.+}/{longitude:.+}", method = RequestMethod.PUT)
	public @ResponseBody RestMessage setLocation(@PathVariable Integer id, @PathVariable float latitude, @PathVariable float longitude) throws ApplicationException {
		perusahaanService.setMapLocation(getPerusahaan(), latitude, longitude);
	
		return RestMessage.success();
	}

	@RequestMapping (method = RequestMethod.POST)
	public @ResponseBody RestMessage save(@RequestBody Perusahaan perusahaan) throws ApplicationException {
		perusahaanService.save(perusahaan);
		
		return RestMessage.success();
	}
	
	@RequestMapping (method = RequestMethod.GET)
	public @ResponseBody ListEntityRestMessage<Perusahaan> getAll() throws ApplicationException {
		List<Perusahaan> list = perusahaanService.getAll();
		
		return ListEntityRestMessage.createListPerusahaan(list);
	}
	
	@RequestMapping (value = "/kode/{kode}", method = RequestMethod.GET)
	public @ResponseBody EntityRestMessage<Perusahaan> getByKode(@PathVariable String kode) throws ApplicationException {
		Perusahaan perusahaan = perusahaanService.getByKode(kode);
		
		return EntityRestMessage.create(perusahaan);
	}

	@RequestMapping (value = "/{id}", method = RequestMethod.GET)
	public @ResponseBody EntityRestMessage<Perusahaan> get(@PathVariable Integer id) throws ApplicationException {
		Perusahaan perusahaan = perusahaanService.getOne(id);
		
		return EntityRestMessage.create(perusahaan);
	}
	
	@RequestMapping(value = "/rekap/{id}", method = RequestMethod.GET)
	public @ResponseBody RestMessage getRekapPerusahaan(@PathVariable Integer id) throws ApplicationException {
		Perusahaan perusahaan = perusahaanService.getOne(id);
		
		return RestMessage.create(createRekapPerusahaan(perusahaan));
	}

	private Perusahaan.Rekap createRekapPerusahaan(Perusahaan perusahaan) throws ApplicationException {
		long estimasiPemasukanBulanan = perusahaanService.countEstimasiPemasukanBulanan(perusahaan);
		long estimasiTagihanBulanan = perusahaanService.countEstimasiTagihanBulanan(perusahaan);
		long pemasukanBulanBerjalan = perusahaanService.countPemasukanBulanBerjalan(perusahaan);
		long tagihanBulanBerjalan = perusahaanService.countTagihanBulanBerjalan(perusahaan);
		long totalAkumulasiTunggakan = perusahaanService.countTotalAkumulasiTunggakan(perusahaan);
		long jumlahPelangganAktif = pelangganService.count(perusahaan, Status.AKTIF);
		long jumlahPelangganBerhenti = pelangganService.count(perusahaan, Status.BERHENTI);
		long jumlahPelangganPutus = pelangganService.count(perusahaan, Status.PUTUS);
		long jumlahPelangganLunas = pelangganService.countByTunggakanLessThan(perusahaan, 1, Status.AKTIF);
		long jumlahPelangganMenunggakWajar = pelangganService.countByTunggakan(perusahaan, 1, Status.AKTIF);
		long jumlahPelangganMenunggakTakWajar = pelangganService.countByTunggakanGreaterThan(perusahaan, 1, Status.AKTIF);

		return Perusahaan.createRekap(estimasiPemasukanBulanan, estimasiTagihanBulanan, pemasukanBulanBerjalan, tagihanBulanBerjalan, totalAkumulasiTunggakan, jumlahPelangganAktif, jumlahPelangganBerhenti, jumlahPelangganPutus, jumlahPelangganLunas, jumlahPelangganMenunggakWajar, jumlahPelangganMenunggakTakWajar);
	}
}
