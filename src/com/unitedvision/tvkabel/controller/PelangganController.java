package com.unitedvision.tvkabel.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.unitedvision.tvkabel.entity.Kelurahan;
import com.unitedvision.tvkabel.entity.Pelanggan;
import com.unitedvision.tvkabel.entity.Pelanggan.Status;
import com.unitedvision.tvkabel.exception.ApplicationException;
import com.unitedvision.tvkabel.service.KelurahanService;
import com.unitedvision.tvkabel.service.PerusahaanService;
import com.unitedvision.tvkabel.util.DateUtil;
import com.unitedvision.tvkabel.util.EntityRestMessage;
import com.unitedvision.tvkabel.util.ListEntityRestMessage;
import com.unitedvision.tvkabel.util.RestMessage;

@Controller
@RequestMapping("/pelanggan")
public class PelangganController extends AbstractController {
	
	@Autowired
	private KelurahanService kelurahanService;
	@Autowired
	private PerusahaanService perusahaanService;

	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody RestMessage add(@RequestBody Pelanggan pelanggan) throws ApplicationException {
		pelanggan.setPerusahaan(getPerusahaan());

		pelangganService.add(pelanggan);
		
		return RestMessage.success();
	}

	@RequestMapping(method = RequestMethod.PUT)
	public @ResponseBody RestMessage save(@RequestBody Pelanggan pelanggan) throws ApplicationException {
		pelanggan.setPerusahaan(getPerusahaan());

		pelangganService.save(pelanggan);
		
		return RestMessage.success();
	}
	
	@RequestMapping(value = "/tunggakan/{id}", method = RequestMethod.PUT)
	public @ResponseBody RestMessage updateTunggakan(@PathVariable Integer id, @RequestBody Integer tunggakan) throws ApplicationException {
		pelangganService.updateTunggakan(id, tunggakan);

		return RestMessage.success();
	}
	
	@RequestMapping(value = "/location/{id}/{latitude:.+}/{longitude:.+}", method = RequestMethod.PUT)
	public @ResponseBody RestMessage setLocation(@PathVariable Integer id, @PathVariable float latitude, @PathVariable float longitude) throws ApplicationException {
		pelangganService.setMapLocation(id, latitude, longitude);
		
		return RestMessage.success();
	}

	@RequestMapping(value = "/activation/{id}", method = RequestMethod.PUT)
	public @ResponseBody RestMessage activate(@PathVariable Integer id, @RequestParam String pesan) throws ApplicationException {
		pelangganService.activate(id, pesan);
		
		return RestMessage.success();
	}

	@RequestMapping(value = "/passivation/{id}", method = RequestMethod.PUT)
	public @ResponseBody RestMessage passivate(@PathVariable Integer id, @RequestParam String pesan) throws ApplicationException {
		pelangganService.passivate(id, pesan);
		
		return RestMessage.success();
	}

	@RequestMapping(value = "/ban/{id}", method = RequestMethod.PUT)
	public @ResponseBody RestMessage ban(@PathVariable Integer id, @RequestParam String pesan) throws ApplicationException {
		pelangganService.banned(id, pesan);

		return RestMessage.success();
	}

	@RequestMapping(value = "/free/{id}", method = RequestMethod.PUT)
	public @ResponseBody RestMessage free(@PathVariable Integer id, @RequestParam String pesan) throws ApplicationException {
		pelangganService.free(id, pesan);
		
		return RestMessage.success();
	}
	
	@RequestMapping(value = "/removed/{id}", method = RequestMethod.PUT)
	public @ResponseBody RestMessage remove(@PathVariable Integer id) throws ApplicationException {
		pelangganService.remove(id);
		
		return RestMessage.success();
	}
	
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
	
	// Rekap
	@RequestMapping(value = "/rekap/tunggakan/{tunggakanAwal}/{tunggakanAkhir}/{status}", method = RequestMethod.POST)
	public ModelAndView printTunggakan(@PathVariable("status") String s, @PathVariable Integer tunggakanAwal, @PathVariable Integer tunggakanAkhir,
			Map<String, Object> model) throws ApplicationException {
		
		Status status = createStatus(s);
		List<Pelanggan> list = pelangganService.getByTunggakan(getPerusahaan(), status, tunggakanAwal, tunggakanAkhir);
		
		model.put("listPelanggan", list);
		model.put("tunggakan", tunggakanAwal);
		
		return new ModelAndView("pdfTunggakan", model);
	}

	@RequestMapping(value = "/rekap/alamat/{idKelurahan}/{lingkungan}/{status}", method = RequestMethod.POST)
	public ModelAndView printAlamat(@PathVariable("status") String s, @PathVariable Integer idKelurahan, @PathVariable Integer lingkungan,
			Map<String, Object> model) throws ApplicationException {
		
		Status status = createStatus(s);
		Kelurahan kelurahan = kelurahanService.getOne(idKelurahan);
		
		List<Pelanggan> list = pelangganService.get(getPerusahaan(), status, kelurahan, lingkungan);
		
		model.put("listPelanggan", list);
		model.put("kelurahan", kelurahan.getNama());
		model.put("lingkungan", lingkungan);
		
		return new ModelAndView("pdfAlamat", model);
	}

	@RequestMapping(value = "/rekap/alamat/batch", method = RequestMethod.POST)
	public ModelAndView printAlamat(Map<String, Object> model) throws ApplicationException {
		List<Pelanggan> list = pelangganService.getOrdered(getPerusahaan(), Pelanggan.Status.AKTIF);
	
		model.put("listPelanggan", list);
		
		return new ModelAndView("pdfAlamat", model);
	}

	@RequestMapping(value = "/pelanggan/kartu/{tahun}", method = RequestMethod.POST)
	public ModelAndView printKartuPelanggan(@PathVariable Integer tahun,
			@RequestParam boolean pembayaran, @RequestParam String searchBy, @RequestParam String query, 
			Map<String, Object> model) throws ApplicationException {
		
		Pelanggan pelanggan = createPelanggan(searchBy, query);

		return printKartuPelanggan(pembayaran, pelanggan, tahun, model);
	}

	@RequestMapping(value = "/pelanggan/kartu/{idPelanggan}/{tahun}/{pembayaran}", method = RequestMethod.POST)
	public ModelAndView printKartuPelanggan(
			@PathVariable boolean pembayaran, @PathVariable Integer idPelanggan, @PathVariable Integer tahun,
			Map<String, Object> model) throws ApplicationException {
		
		Pelanggan pelanggan = pelangganService.getOne(idPelanggan);

		return printKartuPelanggan(pembayaran, pelanggan, tahun, model);
	}

	private ModelAndView printKartuPelanggan(boolean pembayaran, Pelanggan pelanggan, Integer tahun,
			Map<String, Object> model) throws ApplicationException {
		
		Pelanggan cetakKartu = pelangganService.cetakKartu(pelanggan, tahun);
		
		model.put("pembayaran", pembayaran);
		model.put("pelanggan", cetakKartu);
		model.put("tahun", tahun);
		
		return new ModelAndView("pdfKartu", model);
	}

	@RequestMapping(value = "/pelanggan/kartu/empty", method = RequestMethod.POST)
	public ModelAndView printKartuPelangganKosong(Map<String, Object> model) throws ApplicationException {
		
		model.put("perusahaan", getPerusahaan());
		
		return new ModelAndView("pdfKartuDefault", model);
	}

	@RequestMapping(value = "/pelanggan/kartu/aktif/{pembayaran}", method = RequestMethod.POST)
	public ModelAndView printKartuPelangganAktif(@PathVariable boolean pembayaran, Map<String, Object> model) throws ApplicationException {
		
		List<Pelanggan> listPelanggan = pelangganService.get(getPerusahaan(), Status.AKTIF);
		List<Pelanggan> cetakKartu = pelangganService.cetakKartu(listPelanggan);
		
		model.put("pembayaran", pembayaran);
		model.put("pelanggan", cetakKartu);
		model.put("tahun", DateUtil.getYearNow());
		
		return new ModelAndView("pdfKartu", model);
	}

}
