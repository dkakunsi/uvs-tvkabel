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

import com.unitedvision.tvkabel.core.domain.Kelurahan;
import com.unitedvision.tvkabel.core.domain.Pegawai;
import com.unitedvision.tvkabel.core.domain.Pelanggan;
import com.unitedvision.tvkabel.core.domain.Perusahaan;
import com.unitedvision.tvkabel.core.domain.Pelanggan.Status;
import com.unitedvision.tvkabel.core.service.KelurahanService;
import com.unitedvision.tvkabel.core.service.PembayaranService;
import com.unitedvision.tvkabel.core.service.RekapService;
import com.unitedvision.tvkabel.exception.ApplicationException;
import com.unitedvision.tvkabel.exception.UnauthenticatedAccessException;
import com.unitedvision.tvkabel.persistence.entity.PelangganEntity;
import com.unitedvision.tvkabel.persistence.entity.PembayaranEntity;
import com.unitedvision.tvkabel.util.DateUtil;
import com.unitedvision.tvkabel.web.model.PelangganModel;

@Controller
@RequestMapping("/api/print")
public class PrintController extends AbstractController {
	@Autowired
	private RekapService rekapService;
	@Autowired
	private KelurahanService kelurahanService;
	@Autowired
	private PembayaranService pembayaranService;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/rekap/hari", method = RequestMethod.POST)
	public ModelAndView printHari(@RequestParam String tanggal, @RequestParam String searchBy, @RequestParam String query,
			Map<String, Object> model) {
		try {
			List<PelangganEntity> list = null;
			Date hari = DateUtil.getDate(tanggal);
			Pegawai pegawai = createPegawai(searchBy, query);

			list = (List<PelangganEntity>)rekapService.rekapHarian(pegawai.toEntity(), hari);

			model.put("rekap", PelangganModel.rekapPembayaran(list));
			model.put("pegawai", pegawai.getNama());
			model.put("tanggal", tanggal);
			model.put("listBulan", DateUtil.getMonths(hari, 5));

			return new ModelAndView("pdfHari", model);
		} catch (ApplicationException e) {
			model.put("message", e.getMessage());

			return new ModelAndView("pdfException", model);
		}
	}
	
	@RequestMapping(value = "/rekap/bulan", method = RequestMethod.POST)
	public ModelAndView printBulan(@RequestParam String bulan, @RequestParam Integer tahun, @RequestParam String jenis,
			Map<String, Object> model) {
		try {
			List<PembayaranEntity> list = createListRekapBulanan(jenis, tahun, bulan);

			model.put("listPembayaran", list);
			model.put("jenis", jenis);
			model.put("bulan", bulan);
			model.put("tahun", tahun);

			return new ModelAndView("pdfBulan", model);
		} catch (ApplicationException e) {
			model.put("message", e.getMessage());

			return new ModelAndView("pdfException", model);
		}
	}
	
	@SuppressWarnings("unchecked")
	private List<PembayaranEntity> createListRekapBulanan(String jenis, int tahun, String bulan) throws UnauthenticatedAccessException {
		final Perusahaan perusahaan = getPerusahaan();
		final Month month = Month.valueOf(bulan.toUpperCase());

		if (jenis.equals("tagihan"))
			return (List<PembayaranEntity>)rekapService.rekapTagihanBulanan(perusahaan, tahun, month);
		return (List<PembayaranEntity>)rekapService.rekapPembayaranBulanan(perusahaan, tahun, month);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/rekap/tahun", method = RequestMethod.POST)
	public ModelAndView printTahun(@RequestParam Integer tahun, Map<String, Object> model) {
		try {
			final Perusahaan perusahaan = getPerusahaan();

			List<PelangganEntity> list = (List<PelangganEntity>)rekapService.rekapTahunan(perusahaan, tahun);
			model.put("rekap", PelangganModel.rekapPembayaran(list));
			model.put("tahun", tahun);

			return new ModelAndView("pdfTahun", model);
		} catch (ApplicationException e) {
			model.put("message", e.getMessage());

			return new ModelAndView("pdfException", model);
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/rekap/tunggakan", method = RequestMethod.POST)
	public ModelAndView printTunggakan(@RequestParam("status") String s, @RequestParam Integer tunggakan,
			Map<String, Object> model) {
		try {
			final Perusahaan perusahaan = getPerusahaan();
			Status status = createStatus(s);
			
			List<PelangganEntity> list = (List<PelangganEntity>)rekapService.rekapTunggakan(perusahaan, status, tunggakan);
			
			model.put("listPelanggan", list);
			model.put("tunggakan", tunggakan);

			return new ModelAndView("pdfTunggakan", model);
		} catch (ApplicationException e) {
			model.put("message", e.getMessage());

			return new ModelAndView("pdfException", model);
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/rekap/alamat", method = RequestMethod.POST)
	public ModelAndView printAlamat(@RequestParam("status") String s, @RequestParam("kelurahan") String k, @RequestParam Integer lingkungan,
			Map<String, Object> model) {
		try {
			final Perusahaan perusahaan = getPerusahaan();
			Status status = createStatus(s);
			List<PelangganEntity> list = null;
			Kelurahan kelurahan = kelurahanService.getOneByNama(k);
			
			list = (List<PelangganEntity>)rekapService.rekapAlamat(perusahaan, status, kelurahan, lingkungan);
			
			model.put("listPelanggan", list);
			model.put("kelurahan", k);
			model.put("lingkungan", lingkungan);

			return new ModelAndView("pdfAlamat", model);
		} catch (ApplicationException e) {
			model.put("message", e.getMessage());

			return new ModelAndView("pdfException", model);
		}
	}

	@RequestMapping(value = "/pelanggan/kartu", method = RequestMethod.POST)
	public ModelAndView printKartuPelanggan(@RequestParam Integer pembayaran, @RequestParam String searchBy, @RequestParam String query,
			Map<String, Object> model) {
		try {
			Pelanggan pelanggan = createPelanggan(searchBy, query);
			
			model.put("pembayaran", pembayaran);
			model.put("pelanggan", pelangganService.cetakKartu(pelanggan));

			return new ModelAndView("pdfKartu", model);
		} catch(ApplicationException e) {
			model.put("message", e.getMessage());

			return new ModelAndView("pdfException", model);
		}
	}
}
