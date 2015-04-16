package com.unitedvision.tvkabel.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.unitedvision.tvkabel.entity.Pegawai;
import com.unitedvision.tvkabel.entity.Pelanggan;
import com.unitedvision.tvkabel.entity.Pembayaran;
import com.unitedvision.tvkabel.entity.Pembayaran.Tagihan;
import com.unitedvision.tvkabel.exception.ApplicationException;
import com.unitedvision.tvkabel.service.PembayaranService;
import com.unitedvision.tvkabel.util.DateUtil;
import com.unitedvision.tvkabel.util.EntityRestMessage;
import com.unitedvision.tvkabel.util.ListEntityRestMessage;
import com.unitedvision.tvkabel.util.RestMessage;

@Controller
@RequestMapping("/pembayaran")
public class PembayaranController extends AbstractController {
	@Autowired
	private PembayaranService pembayaranService;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public @ResponseBody EntityRestMessage<Pembayaran> getById(@PathVariable Integer id) throws ApplicationException {
		Pembayaran pembayaran = pembayaranService.getOne(id);
		return EntityRestMessage.create(pembayaran);
	}
	
	@RequestMapping(value = "/pegawai/kode/{kode}/{tanggalAwal}/{tanggalAkhir}", method = RequestMethod.GET)
	public @ResponseBody ListEntityRestMessage<Pembayaran> getByKodePegawai(@PathVariable String kode, @PathVariable String tanggalAwal, @PathVariable String tanggalAkhir) throws ApplicationException {
		final Pegawai pegawai = pegawaiService.getOneByKode(getPerusahaan(), kode);
		final Date awal = getTanggalAwal(tanggalAwal);
		final Date akhir = getTanggalAkhir(tanggalAkhir);
		List<Pembayaran> list = getByPegawai(pegawai, awal, akhir);
		
		return ListEntityRestMessage.createListPembayaran(list);
	}

	@RequestMapping(value = "/pegawai/nama/{nama}/{tanggalAwal}/{tanggalAkhir}", method = RequestMethod.GET)
	public @ResponseBody ListEntityRestMessage<Pembayaran> getByNamaPegawai(@PathVariable String nama, @PathVariable String tanggalAwal, @PathVariable String tanggalAkhir) throws ApplicationException {
		final Pegawai pegawai = pegawaiService.getOneByNama(getPerusahaan(), nama);
		final Date awal = getTanggalAwal(tanggalAwal);
		final Date akhir = getTanggalAkhir(tanggalAkhir);
		List<Pembayaran> list = getByPegawai(pegawai, awal, akhir);
		
		return ListEntityRestMessage.createListPembayaran(list);
	}
	
	@RequestMapping(value = "/pelanggan/kode/{kode}/{tanggalAwal}/{tanggalAkhir}", method = RequestMethod.GET)
	public @ResponseBody ListEntityRestMessage<Pembayaran> getByKodePelanggan(@PathVariable String kode, @PathVariable String tanggalAwal, @PathVariable String tanggalAkhir) throws ApplicationException, ApplicationException {
		final Pelanggan pelanggan = pelangganService.getOneByKode(getPerusahaan(), kode);
		final Date awal = getTanggalAwal(tanggalAwal);
		final Date akhir = getTanggalAkhir(tanggalAkhir);
		List<Pembayaran> list = getByPelanggan(pelanggan, awal, akhir);
		
		return ListEntityRestMessage.createListPembayaran(list);
	}

	@RequestMapping(value = "/pelanggan/nama/{nama}/{tanggalAwal}/{tanggalAkhir}", method = RequestMethod.GET)
	public @ResponseBody ListEntityRestMessage<Pembayaran> getByNamaPelanggan(@PathVariable String nama, @PathVariable String tanggalAwal, @PathVariable String tanggalAkhir) throws ApplicationException, ApplicationException {
		final Pelanggan pelanggan = pelangganService.getOneByNama(getPerusahaan(), nama);
		final Date awal = getTanggalAwal(tanggalAwal);
		final Date akhir = getTanggalAkhir(tanggalAkhir);
		List<Pembayaran> list = getByPelanggan(pelanggan, awal, akhir);
		
		return ListEntityRestMessage.createListPembayaran(list);
	}
	
	@RequestMapping(value = "/pelanggan/payable/kode/{kode}", method = RequestMethod.GET)
	public @ResponseBody RestMessage getPayableTagihanByKode(@PathVariable String kode) throws ApplicationException, ApplicationException {
		Pelanggan pelanggan = pelangganService.getOneByKode(getPerusahaan(), kode);
		Tagihan tagihan = pembayaranService.getPayableTagihan(pelanggan);
		return RestMessage.create(tagihan);
	}
	
	@RequestMapping(value = "/pelanggan/payable/nama/{nama}", method = RequestMethod.GET)
	public @ResponseBody RestMessage getPayableTagihanByNama(@PathVariable String nama) throws ApplicationException, ApplicationException {
		Pelanggan pelanggan = pelangganService.getOneByNama(getPerusahaan(), nama);
		Tagihan tagihan = pembayaranService.getPayableTagihan(pelanggan);
		return RestMessage.create(tagihan);
	}
	
	@RequestMapping(value = "/pelanggan/payable/id/{id}", method = RequestMethod.GET)
	public @ResponseBody RestMessage getPayableTagihanById(@PathVariable Integer id) throws ApplicationException {
		Pelanggan pelanggan = pelangganService.getOne(id);
		Tagihan tagihan = pembayaranService.getPayableTagihan(pelanggan);
		return RestMessage.create(tagihan);
	}

	@RequestMapping(value = "/{idPelanggan}/{jumlah}/{total}", method = RequestMethod.POST)
	public @ResponseBody RestMessage pay(@PathVariable Integer idPelanggan, @PathVariable Integer jumlah, @PathVariable Long total) throws ApplicationException {
		Pelanggan pelanggan = pelangganService.getOne(idPelanggan);
		pembayaranService.pay(pelanggan, getPegawai(), total, jumlah);
		return RestMessage.success();
	}
	
	@RequestMapping(value = "/{id}/{total}", method = RequestMethod.PUT)
	public @ResponseBody RestMessage update(@PathVariable Integer id, @PathVariable Long total) throws ApplicationException {
		Pembayaran pembayaran = pembayaranService.getOne(id);
		pembayaran.setJumlahBayar(total);
		pembayaranService.updatePayment(pembayaran);
		return RestMessage.success();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public @ResponseBody RestMessage delete(@PathVariable Integer id) throws ApplicationException {
		Pembayaran pembayaran = pembayaranService.getOne(id);
		pembayaranService.delete(pembayaran);
		return RestMessage.success();
	}

	private List<Pembayaran> getByPegawai(Pegawai pegawai, Date awal, Date akhir) throws ApplicationException {
		return pembayaranService.get(pegawai, awal, akhir);
	}
	
	private List<Pembayaran> getByPelanggan(Pelanggan pelanggan, Date awal, Date akhir) throws ApplicationException {
		return pembayaranService.get(pelanggan, awal, akhir);
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
