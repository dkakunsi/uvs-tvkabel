package com.unitedvision.tvkabel.controller;

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

import com.unitedvision.tvkabel.entity.Kelurahan;
import com.unitedvision.tvkabel.entity.Pegawai;
import com.unitedvision.tvkabel.entity.Pelanggan;
import com.unitedvision.tvkabel.entity.Perusahaan;
import com.unitedvision.tvkabel.entity.Pelanggan.Status;
import com.unitedvision.tvkabel.exception.ApplicationException;
import com.unitedvision.tvkabel.service.KelurahanService;
import com.unitedvision.tvkabel.service.PembayaranService;
import com.unitedvision.tvkabel.service.PerusahaanService;
import com.unitedvision.tvkabel.service.RekapService;
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
	@Autowired
	protected PerusahaanService perusahaanService;

	//model.put("message", e.getMessage());
	//return new ModelAndView("pdfException", model);

	@RequestMapping(value = "/rekap/hari", method = RequestMethod.POST)
	public ModelAndView printHari(@RequestParam String tanggalAwal, @RequestParam String tanggalAkhir, @RequestParam String searchBy, @RequestParam String query,
			Map<String, Object> model) throws ApplicationException { //Intercept exception, hasilkan pdfException
		Date hariAwal = DateUtil.getDate(tanggalAwal);
		Date hariAkhir = DateUtil.getDate(tanggalAkhir);
		Pegawai pegawai = createPegawai(searchBy, query);
		List<Pelanggan> list = rekapService.rekapHarian(pegawai, hariAwal, hariAkhir);

		model.put("rekap", list);
		model.put("pegawai", pegawai.getNama());
		model.put("tanggalAwal", tanggalAwal);
		model.put("tanggalAkhir", tanggalAkhir);
		return new ModelAndView("pdfHari", model);
	}
	
	@RequestMapping(value = "/rekap/bulan", method = RequestMethod.POST)
	public ModelAndView printBulan(@RequestParam Integer idPerusahaan, @RequestParam String bulan, @RequestParam Integer tahun,
			Map<String, Object> model) throws ApplicationException {
		Perusahaan perusahaan = perusahaanService.getOne(idPerusahaan);
		Month month = Month.valueOf(bulan.toUpperCase());
		List<Pelanggan> list = rekapService.rekapBulanan(perusahaan, month, tahun);

		model.put("rekap", list);
		model.put("bulan", bulan);
		model.put("tahun", tahun);
		return new ModelAndView("pdfBulan", model);
	}
	
	@RequestMapping(value = "/rekap/tahun", method = RequestMethod.POST)
	public ModelAndView printTahun(@RequestParam Integer tahun, Map<String, Object> model) throws ApplicationException {
		List<Pelanggan> list = rekapService.rekapTahunan(getPerusahaan(), tahun);

		model.put("rekap", list);
		model.put("tahun", tahun);
		return new ModelAndView("pdfTahun", model);
	}

	@RequestMapping(value = "/rekap/tunggakan", method = RequestMethod.POST)
	public ModelAndView printTunggakan(@RequestParam("status") String s, @RequestParam Integer tunggakanAwal, @RequestParam Integer tunggakanAkhir,
			Map<String, Object> model) throws ApplicationException {
		Status status = createStatus(s);
		List<Pelanggan> list = (List<Pelanggan>)rekapService.rekapTunggakan(getPerusahaan(), status, tunggakanAwal, tunggakanAkhir);
		
		model.put("listPelanggan", list);
		model.put("tunggakan", tunggakanAwal);
		return new ModelAndView("pdfTunggakan", model);
	}

	@RequestMapping(value = "/rekap/alamat", method = RequestMethod.POST)
	public ModelAndView printAlamat(@RequestParam("status") String s, @RequestParam("kelurahan") String k, @RequestParam Integer lingkungan,
			Map<String, Object> model) throws ApplicationException {
		Status status = createStatus(s);
		List<Pelanggan> list = null;
		Kelurahan kelurahan = kelurahanService.getOneByNama(k);
		list = (List<Pelanggan>)rekapService.rekapAlamat(getPerusahaan(), status, kelurahan, lingkungan);
		
		model.put("listPelanggan", list);
		model.put("kelurahan", k);
		model.put("lingkungan", lingkungan);
		return new ModelAndView("pdfAlamat", model);
	}

	@RequestMapping(value = "/rekap/alamat/batch", method = RequestMethod.POST)
	public ModelAndView printAlamat(Map<String, Object> model) throws ApplicationException {
		List<Pelanggan> list = (List<Pelanggan>)rekapService.rekapAlamat(getPerusahaan());
		model.put("listPelanggan", list);
		return new ModelAndView("pdfAlamat", model);
	}

	@RequestMapping(value = "/pelanggan/kartu", method = RequestMethod.POST)
	public ModelAndView printKartuPelanggan(@RequestParam boolean pembayaran, @RequestParam String searchBy, @RequestParam String query, @RequestParam int tahun,
			Map<String, Object> model) throws ApplicationException {
		Pelanggan pelanggan = createPelanggan(searchBy, query);
		
		model.put("pembayaran", pembayaran);
		model.put("pelanggan", pelangganService.cetakKartu(pelanggan, tahun));
		model.put("tahun", tahun);
		return new ModelAndView("pdfKartu", model);
	}

	@RequestMapping(value = "/pelanggan/kartu/empty", method = RequestMethod.POST)
	public ModelAndView printKartuPelangganKosong(@RequestParam Integer idPerusahaan, Map<String, Object> model) throws ApplicationException {
		model.put("perusahaan", perusahaanService.getOne(idPerusahaan));
		return new ModelAndView("pdfKartuDefault", model);
	}

	@RequestMapping(value = "/pelanggan/kartu/aktif", method = RequestMethod.POST)
	public ModelAndView printKartuPelangganAktif(@RequestParam boolean pembayaran,
			Map<String, Object> model) throws ApplicationException {
		List<Pelanggan> listPelanggan = (List<Pelanggan>) pelangganService.get(getPerusahaan(), Status.AKTIF);
		
		model.put("pembayaran", pembayaran);
		model.put("pelanggan", pelangganService.cetakKartu(listPelanggan));
		model.put("tahun", DateUtil.getYearNow());
		return new ModelAndView("pdfKartu", model);
	}
}
