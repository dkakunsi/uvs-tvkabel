package com.unitedvision.tvkabel.web.controller;

import java.time.Month;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.unitedvision.tvkabel.core.service.KelurahanService;
import com.unitedvision.tvkabel.core.service.PembayaranService;
import com.unitedvision.tvkabel.core.service.RekapService;
import com.unitedvision.tvkabel.exception.ApplicationException;
import com.unitedvision.tvkabel.persistence.entity.Kelurahan;
import com.unitedvision.tvkabel.persistence.entity.Pegawai;
import com.unitedvision.tvkabel.persistence.entity.Pelanggan;
import com.unitedvision.tvkabel.persistence.entity.Perusahaan;
import com.unitedvision.tvkabel.persistence.entity.Pelanggan.Status;
import com.unitedvision.tvkabel.util.DateUtil;

@Controller
@RequestMapping("/api/print")
public class PrintController extends AbstractController {
	@Autowired
	private RekapService rekapService;
	@Autowired
	private KelurahanService kelurahanService;
	@Autowired
	private PembayaranService pembayaranService;
	
	@RequestMapping(value = "/rekap/hari", method = RequestMethod.POST)
	public ModelAndView printHari(@RequestParam Integer idPerusahaan, @RequestParam String tanggalAwal, @RequestParam String tanggalAkhir, @RequestParam String searchBy, @RequestParam String query,
			Map<String, Object> model) {
		try {
			Date hariAwal = DateUtil.getDate(tanggalAwal);
			Date hariAkhir = DateUtil.getDate(tanggalAkhir);
			Pegawai pegawai = createPegawai(searchBy, query, idPerusahaan);

			List<Pelanggan> list = rekapService.rekapHarian(pegawai, hariAwal, hariAkhir);

			model.put("rekap", list);
			model.put("pegawai", pegawai.getNama());
			model.put("tanggalAwal", tanggalAwal);
			model.put("tanggalAkhir", tanggalAkhir);

			return new ModelAndView("pdfHari", model);
		} catch (ApplicationException e) {
			model.put("message", e.getMessage());

			return new ModelAndView("pdfException", model);
		}
	}
	
	@RequestMapping(value = "/rekap/bulan", method = RequestMethod.POST)
	public ModelAndView printBulan(@RequestParam Integer idPerusahaan, @RequestParam String bulan, @RequestParam Integer tahun, Map<String, Object> model) {
		try {
			Perusahaan perusahaan = perusahaanService.getOne(idPerusahaan);
			Month month = Month.valueOf(bulan.toUpperCase());

			List<Pelanggan> list = rekapService.rekapBulanan(perusahaan, month, tahun);

			model.put("rekap", list);
			model.put("bulan", bulan);
			model.put("tahun", tahun);

			return new ModelAndView("pdfBulan", model);
		} catch (ApplicationException e) {
			model.put("message", e.getMessage());

			return new ModelAndView("pdfException", model);
		}
	}
	
	@RequestMapping(value = "/rekap/tahun", method = RequestMethod.POST)
	public ModelAndView printTahun(@RequestParam Integer idPerusahaan, @RequestParam Integer tahun, Map<String, Object> model) {
		try {
			final Perusahaan perusahaan = getPerusahaan(idPerusahaan);

			List<Pelanggan> list = rekapService.rekapTahunan(perusahaan, tahun);

			model.put("rekap", list);
			model.put("tahun", tahun);

			return new ModelAndView("pdfTahun", model);
		} catch (ApplicationException e) {
			model.put("message", e.getMessage());

			return new ModelAndView("pdfException", model);
		}
	}

	@RequestMapping(value = "/rekap/tunggakan", method = RequestMethod.POST)
	public ModelAndView printTunggakan(@RequestParam Integer idPerusahaan, @RequestParam("status") String s, @RequestParam Integer tunggakan,
			Map<String, Object> model) {
		try {
			final Perusahaan perusahaan = getPerusahaan(idPerusahaan);
			Status status = createStatus(s);
			
			List<Pelanggan> list = (List<Pelanggan>)rekapService.rekapTunggakan(perusahaan, status, tunggakan);
			
			model.put("listPelanggan", list);
			model.put("tunggakan", tunggakan);

			return new ModelAndView("pdfTunggakan", model);
		} catch (ApplicationException e) {
			model.put("message", e.getMessage());

			return new ModelAndView("pdfException", model);
		}
	}

	@RequestMapping(value = "/rekap/alamat", method = RequestMethod.POST)
	public ModelAndView printAlamat(@RequestParam Integer idPerusahaan, @RequestParam("status") String s, @RequestParam("kelurahan") String k, @RequestParam Integer lingkungan,
			Map<String, Object> model) {
		try {
			final Perusahaan perusahaan = getPerusahaan(idPerusahaan);
			Status status = createStatus(s);
			List<Pelanggan> list = null;
			Kelurahan kelurahan = kelurahanService.getOneByNama(k);
			
			list = (List<Pelanggan>)rekapService.rekapAlamat(perusahaan, status, kelurahan, lingkungan);
			
			model.put("listPelanggan", list);
			model.put("kelurahan", k);
			model.put("lingkungan", lingkungan);

			return new ModelAndView("pdfAlamat", model);
		} catch (ApplicationException e) {
			model.put("message", e.getMessage());

			return new ModelAndView("pdfException", model);
		}
	}

	@RequestMapping(value = "/rekap/alamat/batch", method = RequestMethod.POST)
	public ModelAndView printAlamat(@RequestParam Integer idPerusahaan, Map<String, Object> model) {
		try {
			final Perusahaan perusahaan = getPerusahaan(idPerusahaan);
			List<Pelanggan> list = (List<Pelanggan>)rekapService.rekapAlamat(perusahaan);
			
			model.put("listPelanggan", list);

			return new ModelAndView("pdfAlamat", model);
		} catch (ApplicationException e) {
			model.put("message", e.getMessage());

			return new ModelAndView("pdfException", model);
		}
	}

	@RequestMapping(value = "/pelanggan/kartu", method = RequestMethod.POST)
	public ModelAndView printKartuPelanggan(@RequestParam Integer idPerusahaan, @RequestParam boolean pembayaran, @RequestParam String searchBy, @RequestParam String query, @RequestParam int tahun,
			Map<String, Object> model) {
		try {
			Pelanggan pelanggan = createPelanggan(searchBy, query, idPerusahaan);
			
			model.put("pembayaran", pembayaran);
			model.put("pelanggan", pelangganService.cetakKartu(pelanggan, tahun));
			model.put("tahun", tahun);

			return new ModelAndView("pdfKartu", model);
		} catch(ApplicationException e) {
			model.put("message", e.getMessage());

			return new ModelAndView("pdfException", model);
		}
	}

	@RequestMapping(value = "/pelanggan/kartu/empty", method = RequestMethod.POST)
	public ModelAndView printKartuPelangganKosong(@RequestParam Integer idPerusahaan, Map<String, Object> model) {
		try {
			model.put("perusahaan", perusahaanService.getOne(idPerusahaan));

			return new ModelAndView("pdfKartuDefault", model);
		} catch(ApplicationException e) {
			model.put("message", e.getMessage());

			return new ModelAndView("pdfException", model);
		}
	}

	@RequestMapping(value = "/pelanggan/kartu/aktif", method = RequestMethod.POST)
	public ModelAndView printKartuPelangganAktif(@RequestParam Integer idPerusahaan, @RequestParam boolean pembayaran,
			Map<String, Object> model) {
		try {
			List<Pelanggan> listPelanggan = (List<Pelanggan>) pelangganService.get(getPerusahaan(idPerusahaan), Status.AKTIF);
			
			model.put("pembayaran", pembayaran);
			model.put("pelanggan", pelangganService.cetakKartu(listPelanggan));
			model.put("tahun", DateUtil.getYearNow());

			return new ModelAndView("pdfKartu", model);
		} catch(ApplicationException e) {
			model.put("message", e.getMessage());

			return new ModelAndView("pdfException", model);
		}
	}
}
