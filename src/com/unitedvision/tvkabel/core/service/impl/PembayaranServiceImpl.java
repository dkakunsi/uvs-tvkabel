package com.unitedvision.tvkabel.core.service.impl;

import java.time.Month;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unitedvision.tvkabel.core.service.PembayaranService;
import com.unitedvision.tvkabel.core.validator.Validator;
import com.unitedvision.tvkabel.domain.Pegawai;
import com.unitedvision.tvkabel.domain.Pelanggan;
import com.unitedvision.tvkabel.domain.Pembayaran;
import com.unitedvision.tvkabel.domain.Pembayaran.Tagihan;
import com.unitedvision.tvkabel.domain.persistence.repository.PegawaiRepository;
import com.unitedvision.tvkabel.domain.persistence.repository.PelangganRepository;
import com.unitedvision.tvkabel.domain.persistence.repository.PembayaranRepository;
import com.unitedvision.tvkabel.domain.Perusahaan;
import com.unitedvision.tvkabel.exception.ApplicationException;
import com.unitedvision.tvkabel.exception.DataDuplicationException;
import com.unitedvision.tvkabel.exception.EntityNotExistException;
import com.unitedvision.tvkabel.exception.NotPayableCustomerException;
import com.unitedvision.tvkabel.exception.UnpaidBillException;
import com.unitedvision.tvkabel.util.DateUtil;
import com.unitedvision.tvkabel.util.PageSizeUtil;

@Service
@Transactional(readOnly = true)
public class PembayaranServiceImpl implements PembayaranService {
	@Autowired
	private PembayaranRepository pembayaranRepository;
	@Autowired
	private PelangganRepository pelangganRepository;
	@Autowired
	private PegawaiRepository pegawaiRepository;
	@Autowired
	private Validator validator;

	@Override
	public Pembayaran pay(Pembayaran domain) throws NotPayableCustomerException, UnpaidBillException, EntityNotExistException, DataDuplicationException {
		Pembayaran entity = domain;
		
		Pelanggan pelanggan = pelangganRepository.findOne(entity.getIdPelanggan());
		entity.setPelanggan(pelanggan);
		
		if (!(pelanggan.getStatus().equals(Pelanggan.Status.AKTIF)))
			throw new NotPayableCustomerException("Status BUKAN merupakan pelanggan AKTIF");

		Pegawai pegawai = pegawaiRepository.findOne(entity.getIdPegawai());
		entity.setPegawai(pegawai);

		validator.validate(entity);

		entity.generateKode();
		entity = pembayaranRepository.save(entity);
		updateTunggakan(entity.getPelanggan());
		
		return entity;
	}
	
	@Override
	public Pembayaran updatePayment(Pembayaran domain) {
		Pembayaran entity = domain;
		
		Pelanggan pelanggan = pelangganRepository.findOne(entity.getIdPelanggan());
		entity.setPelanggan(pelanggan);

		Pegawai pegawai = pegawaiRepository.findOne(entity.getIdPegawai());
		entity.setPegawai(pegawai);

		entity = pembayaranRepository.save(entity);
		
		return entity;
	}
	
	@Override
	@Transactional(readOnly = false)
	public Pembayaran save(Pembayaran domain) throws ApplicationException {
		return pay(domain);
	}
	
	private void updateTunggakan(Pelanggan pelanggan) {
		Pembayaran pembayaranTerakhir;
		try {
			pembayaranTerakhir = getLast(pelanggan);
		} catch (EntityNotExistException e) {
			pembayaranTerakhir = null;
		}
		
		pelanggan.countTunggakan(pembayaranTerakhir);
		pelangganRepository.save(pelanggan);
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(Pembayaran domain) {
		domain = pembayaranRepository.findOne(domain.getId());
		pembayaranRepository.delete(domain);
		updateTunggakan(domain.getPelanggan());
	}

	@Override
	public Pembayaran getOne(int id) throws EntityNotExistException {
		return pembayaranRepository.findOne(id);
	}

	@Override
	public List<Pembayaran> get(Perusahaan perusahaan, Date tanggalMulai, Date tanggalAkhir) {
		return pembayaranRepository.findByPegawai_PerusahaanAndTanggalBayarBetween(perusahaan, tanggalMulai, tanggalAkhir);
	}

	@Override
	public List<Pembayaran> get(Perusahaan perusahaan, Date tanggalMulai, Date tanggalAkhir, int page) {
		PageRequest pageRequest = new PageRequest(page, PageSizeUtil.DATA_NUMBER);

		return pembayaranRepository.findByPegawai_PerusahaanAndTanggalBayarBetween(perusahaan, tanggalMulai, tanggalAkhir, pageRequest);
	}

	@Override
	public List<Pembayaran> get(Pegawai pegawai, Date tanggalMulai, Date tanggalAkhir, int page) {
		PageRequest pageRequest = new PageRequest(page, PageSizeUtil.DATA_NUMBER);

		return pembayaranRepository.findByPegawaiAndTanggalBayarBetween(pegawai, tanggalMulai, tanggalAkhir, pageRequest);
	}

	@Override
	public List<Pembayaran> get(Pelanggan pelanggan, Date tanggalMulai, Date tanggalAkhir, int page) {
		PageRequest pageRequest = new PageRequest(page, PageSizeUtil.DATA_NUMBER);

		return pembayaranRepository.findByPelangganAndTanggalBayarBetween(pelanggan, tanggalMulai, tanggalAkhir, pageRequest);
	}
	
	@Override
	public List<Pembayaran> get(Perusahaan perusahaan, Tagihan tagihan) {
		return pembayaranRepository.findByPegawai_PerusahaanAndTagihan(perusahaan, tagihan);
	}

	@Override
	public List<Pembayaran> get(Perusahaan perusahaan, int tahun, Month bulan, int page) {
		PageRequest pageRequest = new PageRequest(page, PageSizeUtil.DATA_NUMBER);
		Tagihan tagihan = new Tagihan(tahun, bulan);

		return pembayaranRepository.findByPegawai_PerusahaanAndTagihan(perusahaan, tagihan, pageRequest);
	}

	@Override
	public List<Pembayaran> get(Pelanggan pelanggan, int tahun) {
		return pembayaranRepository.findByPelangganAndTagihan_Tahun(pelanggan, tahun);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Pembayaran> get(Pelanggan pelanggan, Tagihan tagihanAwal, Tagihan tagihanAkhir) {
		int tahunAwal = tagihanAwal.getTahun();
		int tahunAkhir = tagihanAkhir.getTahun();
		Month bulanAwal = tagihanAwal.getBulan();
		Month bulanAkhir = tagihanAkhir.getBulan();
		
		List<Pembayaran> merged;
		if (tahunAwal == tahunAkhir) {
			merged = pembayaranRepository.findByPelangganAndTagihan_TahunAndTagihan_BulanBetween(pelanggan, tahunAwal, bulanAwal, bulanAkhir);
		} else {
			List<Pembayaran> part1 = pembayaranRepository.findByPelangganAndTagihan_TahunAndTagihan_BulanBetween(pelanggan, tahunAwal, bulanAwal, Month.DECEMBER);
			List<Pembayaran> part2 = pembayaranRepository.findByPelangganAndTagihan_TahunAndTagihan_BulanBetween(pelanggan, tahunAkhir, Month.JANUARY, bulanAkhir);
			
			merged = ListUtils.union(part1, part2);
		}
		
		return merged;
	}

	@Override
	public long count(Perusahaan perusahaan, Date tanggalMulai, Date tanggalAkhir) {
		return pembayaranRepository.countByPegawai_PerusahaanAndTanggalBayarBetween(perusahaan, tanggalMulai, tanggalAkhir);
	}

	@Override
	public long count(Pegawai pegawai, Date tanggalMulai, Date tanggalAkhir) {
		return pembayaranRepository.countByPegawaiAndTanggalBayarBetween(pegawai, tanggalMulai, tanggalAkhir);
	}

	@Override
	public long count(Pelanggan pelanggan, Date tanggalMulai, Date tanggalAkhir) {
		return pembayaranRepository.countByPelangganAndTanggalBayarBetween(pelanggan, tanggalMulai, tanggalAkhir);
	}
	
	@Override
	public long count(Perusahaan perusahaan, Tagihan tagihan) {
		return pembayaranRepository.countByPegawai_PerusahaanAndTagihan(perusahaan, tagihan);
	}

	@Override
	public Pembayaran getLast(Pelanggan pelanggan) throws EntityNotExistException {
		return pembayaranRepository.findFirstByPelangganOrderByIdDesc(pelanggan);
	}

	@Override
	public Tagihan getPayableTagihan(Pelanggan pelanggan) throws EntityNotExistException {
		Pembayaran pembayaranEntity = getLast(pelanggan);

		Tagihan tagihan;
		if (pembayaranEntity != null) {
			tagihan = pembayaranEntity.getTagihan();
			tagihan.increase();
		} else {
			Date tanggalDaftar = pelanggan.getTanggalMulai();
			int tahun = DateUtil.getYear(tanggalDaftar);
			Month month = DateUtil.getMonth(tanggalDaftar);
			
			tagihan = new Tagihan(tahun, month);
		}

		return tagihan;
	}
}
