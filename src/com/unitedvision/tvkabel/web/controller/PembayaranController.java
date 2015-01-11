package com.unitedvision.tvkabel.web.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.unitedvision.tvkabel.core.service.PembayaranService;
import com.unitedvision.tvkabel.exception.ApplicationException;
import com.unitedvision.tvkabel.exception.EntityNotExistException;
import com.unitedvision.tvkabel.persistence.entity.Pegawai;
import com.unitedvision.tvkabel.persistence.entity.Pelanggan;
import com.unitedvision.tvkabel.persistence.entity.Pembayaran;
import com.unitedvision.tvkabel.persistence.entity.Pembayaran.Tagihan;
import com.unitedvision.tvkabel.util.DateUtil;
import com.unitedvision.tvkabel.util.PageSizeUtil;
import com.unitedvision.tvkabel.web.rest.ListPembayaranRestResult;
import com.unitedvision.tvkabel.web.rest.PembayaranRestResult;
import com.unitedvision.tvkabel.web.rest.RestResult;
import com.unitedvision.tvkabel.web.rest.SimpanPembayaranRestRequest;
import com.unitedvision.tvkabel.web.rest.TagihanRestResult;
import com.unitedvision.tvkabel.web.rest.UpdatePembayaranRestRequest;

@Controller
@RequestMapping("/api/pembayaran")
public class PembayaranController extends AbstractController {
	@Autowired
	private PembayaranService pembayaranService;

	@RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
	public @ResponseBody PembayaranRestResult getById(@PathVariable Integer id) {
		try {
			Pembayaran pembayaran = pembayaranService.getOne(id);
			
			return PembayaranRestResult.create("Berhasil!", pembayaran);
		} catch (EntityNotExistException e) {
			return PembayaranRestResult.create(e.getMessage());
		}
	}
	
	@RequestMapping(value = "/perusahaan/{perusahaan}/pegawai/kode/{kode}/awal/{tanggalAwal}/akhir/{tanggalAkhir}/page/{page}", method = RequestMethod.GET)
	public @ResponseBody ListPembayaranRestResult getByKodePegawai(@PathVariable Integer perusahaan, @PathVariable String kode, @PathVariable String tanggalAwal, @PathVariable String tanggalAkhir, @PathVariable Integer page) throws ApplicationException {
		try {
			final Pegawai pegawai = pegawaiService.getOneByKode(getPerusahaan(perusahaan), kode);
			final Date awal = getTanggalAwal(tanggalAwal);
			final Date akhir = getTanggalAkhir(tanggalAkhir);
			List<Pembayaran> list = getByPegawai(pegawai, awal, akhir, page);
			long total = pembayaranService.count(pegawai, awal, akhir);
			long count = PageSizeUtil.getCounter(page, list.size());
			
			return ListPembayaranRestResult.create("Berhasil!", list, page, total, count);
		} catch (EntityNotExistException e) {
			return ListPembayaranRestResult.create(String.format("Gagal! %s", e.getMessage()));
		}
	}

	@RequestMapping(value = "/perusahaan/{perusahaan}/pegawai/nama/{nama}/awal/{tanggalAwal}/akhir/{tanggalAkhir}/page/{page}", method = RequestMethod.GET)
	public @ResponseBody ListPembayaranRestResult getByNamaPegawai(@PathVariable Integer perusahaan, @PathVariable String nama, @PathVariable String tanggalAwal, @PathVariable String tanggalAkhir, @PathVariable Integer page) throws ApplicationException {
		try {
			final Pegawai pegawai = pegawaiService.getOneByNama(getPerusahaan(perusahaan), nama);
			final Date awal = getTanggalAwal(tanggalAwal);
			final Date akhir = getTanggalAkhir(tanggalAkhir);
			List<Pembayaran> list = getByPegawai(pegawai, awal, akhir, page);
			long total = pembayaranService.count(pegawai, awal, akhir);
			long count = PageSizeUtil.getCounter(page, list.size());
			
			return ListPembayaranRestResult.create("Berhasil!", list, page, total, count);
		} catch (EntityNotExistException e) {
			return ListPembayaranRestResult.create(String.format("Gagal! %s", e.getMessage()));
		}
	}
	
	@RequestMapping(value = "/perusahaan/{perusahaan}/pelanggan/kode/{kode}/awal/{tanggalAwal}/akhir/{tanggalAkhir}/page/{page}", method = RequestMethod.GET)
	public @ResponseBody ListPembayaranRestResult getByKodePelanggan(@PathVariable Integer perusahaan, @PathVariable String kode, @PathVariable String tanggalAwal, @PathVariable String tanggalAkhir, @PathVariable Integer page) {
		try {
			final Pelanggan pelanggan = pelangganService.getOneByKode(getPerusahaan(perusahaan), kode);
			final Date awal = getTanggalAwal(tanggalAwal);
			final Date akhir = getTanggalAkhir(tanggalAkhir);
			List<Pembayaran> list = getByPelanggan(pelanggan, awal, akhir, page);
			long total = pembayaranService.count(pelanggan, awal, akhir);
			long count = PageSizeUtil.getCounter(page, list.size());
			
			return ListPembayaranRestResult.create("Berhasil!", list, page, total, count);
		} catch (ApplicationException e) {
			return ListPembayaranRestResult.create(String.format("Gagal! %s", e.getMessage()));
		}
	}

	@RequestMapping(value = "/perusahaan/{perusahaan}/pelanggan/nama/{nama}/awal/{tanggalAwal}/akhir/{tanggalAkhir}/page/{page}", method = RequestMethod.GET)
	public @ResponseBody ListPembayaranRestResult getByNamaPelanggan(@PathVariable Integer perusahaan, @PathVariable String nama, @PathVariable String tanggalAwal, @PathVariable String tanggalAkhir, @PathVariable Integer page) {
		try {
			final Pelanggan pelanggan = pelangganService.getOneByNama(getPerusahaan(perusahaan), nama);
			final Date awal = getTanggalAwal(tanggalAwal);
			final Date akhir = getTanggalAkhir(tanggalAkhir);
			List<Pembayaran> list = getByPelanggan(pelanggan, awal, akhir, page);
			long total = pembayaranService.count(pelanggan, awal, akhir);
			long count = PageSizeUtil.getCounter(page, list.size());
			
			return ListPembayaranRestResult.create("Berhasil!", list, page, total, count);
		} catch (ApplicationException e) {
			return ListPembayaranRestResult.create(String.format("Gagal! %s", e.getMessage()));
		}
	}
	
	@RequestMapping(value = "/perusahaan/{perusahaan}/pelanggan/kode/{kode}/payable", method = RequestMethod.GET)
	public @ResponseBody TagihanRestResult getPayableTagihanByKode(@PathVariable Integer perusahaan, @PathVariable String kode) {
		try {
			Pelanggan pelanggan = pelangganService.getOneByKode(getPerusahaan(perusahaan), kode);
			Tagihan tagihan = pembayaranService.getPayableTagihan(pelanggan);
			
			return TagihanRestResult.create("Berhasil!", tagihan);
		} catch (ApplicationException e) {
			return TagihanRestResult.create(String.format("Gagal! %s", e.getMessage()));
		}
	}
	
	@RequestMapping(value = "/perusahaan/{perusahaan}/pelanggan/nama/{nama}/payable", method = RequestMethod.GET)
	public @ResponseBody TagihanRestResult getPayableTagihanByNama(@PathVariable Integer perusahaan, @PathVariable String nama) {
		try {
			Pelanggan pelanggan = pelangganService.getOneByNama(getPerusahaan(perusahaan), nama);
			Tagihan tagihan = pembayaranService.getPayableTagihan(pelanggan);
			
			return TagihanRestResult.create("Berhasil!", tagihan);
		} catch (ApplicationException e) {
			return TagihanRestResult.create(String.format("Gagal! %s", e.getMessage()));
		}
	}
	
	@RequestMapping(value = "/pelanggan/id/{id}/payable", method = RequestMethod.GET)
	public @ResponseBody TagihanRestResult getPayableTagihanById(@PathVariable Integer id) {
		try {
			Pelanggan pelanggan = pelangganService.getOne(id);
			Tagihan tagihan = pembayaranService.getPayableTagihan(pelanggan);
			
			return TagihanRestResult.create("Berhasil!", tagihan);
		} catch (ApplicationException e) {
			return TagihanRestResult.create(String.format("Gagal! %s", e.getMessage()));
		}
	}

	@RequestMapping(value = "/master", method = RequestMethod.POST)
	public @ResponseBody RestResult pay(@RequestBody SimpanPembayaranRestRequest restRequest) {
		try {
			Date tanggalBayar = DateUtil.getNow();
			Pelanggan pelanggan = pelangganService.getOne(restRequest.getIdPelanggan());
			Pegawai pegawai = pegawaiService.getOne(restRequest.getIdPegawai());
			Tagihan tagihan = pembayaranService.getPayableTagihan(pelanggan);
			
			Pembayaran pembayaran = new Pembayaran(pelanggan, pegawai, restRequest.getJumlahPembayaran(), tanggalBayar, tagihan);
			pembayaranService.pay(pembayaran);

			return RestResult.create("Berhasil!");
		} catch (ApplicationException e) {
			return RestResult.create(String.format("Gagal! %s", e.getMessage()));
		}
	}
	
	@RequestMapping(value = "/master", method = RequestMethod.PUT)
	public @ResponseBody RestResult update(@RequestBody UpdatePembayaranRestRequest restRequest) {
		try {
			Pembayaran pembayaran = pembayaranService.getOne(restRequest.getId());
			pembayaran.setPegawai(pegawaiService.getOne(restRequest.getIdPegawai()));
			pembayaran.setJumlahBayar(restRequest.getJumlahPembayaran());

			pembayaranService.updatePayment(pembayaran);

			return RestResult.create("Berhasil!");
		} catch (ApplicationException e) {
			return RestResult.create(String.format("Gagal! %s", e.getMessage()));
		}
	}
	
	@RequestMapping(value = "/master", method = RequestMethod.DELETE)
	public @ResponseBody RestResult delete(@RequestBody Integer id) {
		try {
			Pembayaran pembayaran = pembayaranService.getOne(id);
			
			pembayaranService.delete(pembayaran);

			return RestResult.create("Pembayaran BERHASIL dihapus");
		} catch (ApplicationException e) {
			return RestResult.create(e.getMessage());
		}
	}

	private List<Pembayaran> getByPegawai(Pegawai pegawai, Date awal, Date akhir, int page) {
		return pembayaranService.get(pegawai, awal, akhir, page);
	}
	
	private List<Pembayaran> getByPelanggan(Pelanggan pelanggan, Date awal, Date akhir, int page) {
		return pembayaranService.get(pelanggan, awal, akhir, page);
	}

	private Date getTanggalAwal(final String str) {
		if (str.equals(""))
			return DateUtil.getFirstDate();
		return DateUtil.getDate(str);
	}
	
	private Date getTanggalAkhir(final String str) {
		if (str.equals(""))
			return DateUtil.getLastDate();
		return DateUtil.getDate(str);
	}
}
