package com.unitedvision.tvkabel.web.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.unitedvision.tvkabel.core.domain.Operator;
import com.unitedvision.tvkabel.core.domain.Pelanggan.Status;
import com.unitedvision.tvkabel.core.domain.Perusahaan;
import com.unitedvision.tvkabel.core.service.PerusahaanService;
import com.unitedvision.tvkabel.exception.ApplicationException;
import com.unitedvision.tvkabel.exception.EntityNotExistException;
import com.unitedvision.tvkabel.exception.UnauthenticatedAccessException;
import com.unitedvision.tvkabel.web.model.PerusahaanModel;
import com.unitedvision.tvkabel.web.rest.ListPerusahaanRestResult;
import com.unitedvision.tvkabel.web.rest.PerusahaanRestResult;
import com.unitedvision.tvkabel.web.rest.RekapPerusahaanRestResult;
import com.unitedvision.tvkabel.web.rest.RestResult;

@Controller
@RequestMapping ("/api/perusahaan")
public class PerusahaanController extends AbstractController {
	@Autowired
	private PerusahaanService perusahaanService;

	@RequestMapping(value = "/registrasi", method = RequestMethod.POST)
	public @ResponseBody RestResult registrasi(@RequestBody PerusahaanModel perusahaanModel, Map<String, Object> model) {
		try {
			final Operator op = perusahaanService.regist(perusahaanModel);
			model.put("operator", op);

			return RestResult.create("Berhasil!");
		} catch (ApplicationException e) {
			return RestResult.create(String.format("Gagal! %s", e.getMessage()));
		}
	}
	
	@RequestMapping(value = "/id/{id}/location/{latitude:.+}/{longitude:.+}", method = RequestMethod.PUT)
	public @ResponseBody RestResult setMapLocation(@PathVariable Integer id, @PathVariable float latitude, @PathVariable float longitude) throws ApplicationException {
		Perusahaan perusahaan = getPerusahaan(id);
		
		try {
			perusahaanService.setMapLocation(perusahaan, latitude, longitude);
			
			return RestResult.create("Berhasil!");
		} catch (ApplicationException e) {
			return RestResult.create(e.getMessage());
		}
	}

	@RequestMapping (value = "/master", method = RequestMethod.POST)
	public @ResponseBody PerusahaanRestResult simpanPerusahaan(@RequestBody PerusahaanModel perusahaan) {
		try {
			perusahaanService.save(perusahaan);

			return PerusahaanRestResult.create("Berhasil!");
		} catch (ApplicationException e) {
			return PerusahaanRestResult.create(String.format("Gagal! %s", e.getMessage()));
		}
	}
	
	@RequestMapping (value = "", method = RequestMethod.GET)
	public @ResponseBody ListPerusahaanRestResult getAll() {
		List<? extends Perusahaan> list = perusahaanService.getAll();
		
		return ListPerusahaanRestResult.create("Berhasil!", PerusahaanModel.createListModel(list));
	}
	
	@RequestMapping (value = "/kode/{kode}", method = RequestMethod.GET)
	public @ResponseBody PerusahaanRestResult getByKode(@PathVariable String kode) throws UnauthenticatedAccessException {
		try {
			Perusahaan perusahaan = perusahaanService.getByKode(kode);

			return PerusahaanRestResult.create("Berhasil!", perusahaan.toModel());
		} catch (EntityNotExistException e) {
			return PerusahaanRestResult.create(String.format("Gagal! %s", e.getMessage()));
		}
	}

	@RequestMapping (value = "/id/{id}", method = RequestMethod.GET)
	public @ResponseBody PerusahaanRestResult get(@PathVariable Integer id) throws UnauthenticatedAccessException {
		try {
			Perusahaan perusahaan = perusahaanService.getOne(id);

			return PerusahaanRestResult.create("Berhasil!", perusahaan.toModel());
		} catch (EntityNotExistException e) {
			return PerusahaanRestResult.create(String.format("Gagal! %s", e.getMessage()));
		}
	}
	
	@RequestMapping(value = "/{id}/rekap", method = RequestMethod.GET)
	public @ResponseBody RekapPerusahaanRestResult getRekapPerusahaan(@PathVariable Integer id) {
		try {
			Perusahaan perusahaan = perusahaanService.getOne(id);

			return RekapPerusahaanRestResult.create("Berhasil!", createRekapPerusahaan(perusahaan));
		} catch (EntityNotExistException e) {
			return RekapPerusahaanRestResult.create(String.format("Gagal! %s", e.getMessage()));
		}
	}
	private PerusahaanModel.Rekap createRekapPerusahaan(Perusahaan perusahaan) {
		long estimasiPemasukanBulanan = perusahaanService.countEstimasiPemasukanBulanan(perusahaan);
		long estimasiTagihanBulanan = perusahaanService.countEstimasiTagihanBulanan(perusahaan);
		long pemasukanBulanBerjalan = perusahaanService.countPemasukanBulanBerjalan(perusahaan);
		long tagihanBulanBerjalan = perusahaanService.countTagihanBulanBerjalan(perusahaan);
		long totalAkumulasiTunggakan = perusahaanService.countTotalAkumulasiTunggakan(perusahaan);
		long jumlahPelangganAktif = pelangganService.count(perusahaan, Status.AKTIF);
		long jumlahPelangganBerhenti = pelangganService.count(perusahaan, Status.BERHENTI);
		long jumlahPelangganPutus = pelangganService.count(perusahaan, Status.PUTUS);
		long jumlahPelangganLunas = pelangganService.countByTunggakanLessThan(perusahaan, Status.AKTIF, 1);
		long jumlahPelangganMenunggakWajar = pelangganService.countByTunggakan(perusahaan, Status.AKTIF, 1);
		long jumlahPelangganMenunggakTakWajar = pelangganService.countByTunggakanGreaterThan(perusahaan, Status.AKTIF, 1);

		return PerusahaanModel.createRekap(estimasiPemasukanBulanan, estimasiTagihanBulanan, pemasukanBulanBerjalan, tagihanBulanBerjalan, totalAkumulasiTunggakan, jumlahPelangganAktif, jumlahPelangganBerhenti, jumlahPelangganPutus, jumlahPelangganLunas, jumlahPelangganMenunggakWajar, jumlahPelangganMenunggakTakWajar);
	}
}
