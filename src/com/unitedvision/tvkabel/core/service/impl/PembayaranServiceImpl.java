package com.unitedvision.tvkabel.core.service.impl;

import java.time.Month;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unitedvision.tvkabel.core.domain.Pegawai;
import com.unitedvision.tvkabel.core.domain.Pelanggan;
import com.unitedvision.tvkabel.core.domain.Pembayaran;
import com.unitedvision.tvkabel.core.domain.Perusahaan;
import com.unitedvision.tvkabel.core.domain.Tagihan;
import com.unitedvision.tvkabel.core.service.PembayaranService;
import com.unitedvision.tvkabel.core.validator.Validator;
import com.unitedvision.tvkabel.exception.ApplicationException;
import com.unitedvision.tvkabel.exception.DataDuplicationException;
import com.unitedvision.tvkabel.exception.EntityNotExistException;
import com.unitedvision.tvkabel.exception.NotPayableCustomerException;
import com.unitedvision.tvkabel.exception.UncompatibleTypeException;
import com.unitedvision.tvkabel.exception.UnpaidBillException;
import com.unitedvision.tvkabel.persistence.entity.PembayaranEntity;
import com.unitedvision.tvkabel.persistence.entity.PembayaranEntity.TagihanValue;
import com.unitedvision.tvkabel.persistence.repository.PegawaiRepository;
import com.unitedvision.tvkabel.persistence.repository.PelangganRepository;
import com.unitedvision.tvkabel.persistence.repository.PembayaranRepository;
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
	public Pembayaran pay(Pembayaran domain) throws NotPayableCustomerException, UncompatibleTypeException, UnpaidBillException, EntityNotExistException, DataDuplicationException {
		PembayaranEntity entity = domain.toEntity();
		
		Pelanggan pelanggan = pelangganRepository.findOne(entity.getIdPelanggan());
		entity.setPelanggan(pelanggan);
		
		if (!(pelanggan.getStatus().equals(Pelanggan.Status.AKTIF)))
			throw new NotPayableCustomerException("Status BUKAN merupakan pelanggan AKTIF");

		Pegawai pegawai = pegawaiRepository.findOne(entity.getIdPegawai());
		entity.setPegawai(pegawai);

		validator.validate(entity);

		entity.generateKode();
		entity = pembayaranRepository.save(entity);
		updateTunggakan(entity);
		
		return entity;
	}
	
	@Override
	public Pembayaran updatePayment(Pembayaran domain) throws UncompatibleTypeException {
		PembayaranEntity entity = domain.toEntity();
		
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
		PembayaranEntity entity = domain.toEntity();
		
		Pelanggan pelanggan = pelangganRepository.findOne(entity.getIdPelanggan());
		entity.setPelanggan(pelanggan);

		Pegawai pegawai = pegawaiRepository.findOne(entity.getIdPegawai());
		entity.setPegawai(pegawai);

		if (entity.isNew())
			validator.validate(entity);

		entity = pembayaranRepository.save(entity);
		updateTunggakan(entity);
		
		return entity;
	}
	
	private void updateTunggakan(Pembayaran domain) {
		Pelanggan pelanggan = domain.getPelanggan();
		pelanggan.countTunggakan(domain);

		pelangganRepository.save(pelanggan.toEntity());
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(Pembayaran domain) {
		domain = pembayaranRepository.findOne(domain.getId());
		pembayaranRepository.delete(domain.toEntity());
	}

	@Override
	public PembayaranEntity getOne(int id) throws EntityNotExistException {
		return pembayaranRepository.findOne(id);
	}

	@Override
	public List<PembayaranEntity> get(Perusahaan perusahaan, Date tanggalMulai, Date tanggalAkhir) {
		return pembayaranRepository.findByPegawai_PerusahaanAndTanggalBayarBetween(perusahaan.toEntity(), tanggalMulai, tanggalAkhir);
	}

	@Override
	public List<PembayaranEntity> get(Perusahaan perusahaan, Date tanggalMulai, Date tanggalAkhir, int page) {
		PageRequest pageRequest = new PageRequest(page, PageSizeUtil.DATA_NUMBER);

		return pembayaranRepository.findByPegawai_PerusahaanAndTanggalBayarBetween(perusahaan.toEntity(), tanggalMulai, tanggalAkhir, pageRequest);
	}

	@Override
	public List<PembayaranEntity> get(Pegawai pegawai, Date tanggalMulai, Date tanggalAkhir, int page) {
		PageRequest pageRequest = new PageRequest(page, PageSizeUtil.DATA_NUMBER);

		return pembayaranRepository.findByPegawaiAndTanggalBayarBetween(pegawai.toEntity(), tanggalMulai, tanggalAkhir, pageRequest);
	}

	@Override
	public List<PembayaranEntity> get(Pelanggan pelanggan, Date tanggalMulai, Date tanggalAkhir, int page) {
		PageRequest pageRequest = new PageRequest(page, PageSizeUtil.DATA_NUMBER);

		return pembayaranRepository.findByPelangganAndTanggalBayarBetween(pelanggan.toEntity(), tanggalMulai, tanggalAkhir, pageRequest);
	}
	
	@Override
	public List<PembayaranEntity> get(Perusahaan perusahaan, Tagihan tagihan) {
		return pembayaranRepository.findByPegawai_PerusahaanAndTagihan(perusahaan.toEntity(), tagihan.toEntity());
	}

	@Override
	public List<PembayaranEntity> get(Perusahaan perusahaan, int tahun, Month bulan, int page) {
		PageRequest pageRequest = new PageRequest(page, PageSizeUtil.DATA_NUMBER);
		Tagihan tagihan = new TagihanValue(tahun, bulan);

		return pembayaranRepository.findByPegawai_PerusahaanAndTagihan(perusahaan.toEntity(), tagihan.toEntity(), pageRequest);
	}

	@Override
	public List<PembayaranEntity> get(Pelanggan pelanggan, int tahun) {
		return pembayaranRepository.findByPelangganAndTagihan_Tahun(pelanggan.toEntity(), tahun);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PembayaranEntity> get(Pelanggan pelanggan, Tagihan tagihanAwal, Tagihan tagihanAkhir) {
		int tahunAwal = tagihanAwal.getTahun();
		int tahunAkhir = tagihanAkhir.getTahun();
		Month bulanAwal = tagihanAwal.getBulan();
		Month bulanAkhir = tagihanAkhir.getBulan();
		
		List<PembayaranEntity> merged;
		if (tahunAwal == tahunAkhir) {
			merged = pembayaranRepository.findByPelangganAndTagihan_TahunAndTagihan_BulanBetween(pelanggan.toEntity(), tahunAwal, bulanAwal, bulanAkhir);
		} else {
			List<PembayaranEntity> part1 = pembayaranRepository.findByPelangganAndTagihan_TahunAndTagihan_BulanBetween(pelanggan.toEntity(), tahunAwal, bulanAwal, Month.DECEMBER);
			List<PembayaranEntity> part2 = pembayaranRepository.findByPelangganAndTagihan_TahunAndTagihan_BulanBetween(pelanggan.toEntity(), tahunAkhir, Month.JANUARY, bulanAkhir);
			
			merged = ListUtils.union(part1, part2);
		}
		
		return merged;
	}

	@Override
	public PembayaranEntity getLast(Pelanggan pelanggan) throws EntityNotExistException {
		return pembayaranRepository.findFirstByPelangganOrderByIdDesc(pelanggan.toEntity());
	}

	@Override
	public long count(Perusahaan perusahaan, Date tanggalMulai, Date tanggalAkhir) {
		return pembayaranRepository.countByPegawai_PerusahaanAndTanggalBayarBetween(perusahaan.toEntity(), tanggalMulai, tanggalAkhir);
	}

	@Override
	public long count(Pegawai pegawai, Date tanggalMulai, Date tanggalAkhir) {
		return pembayaranRepository.countByPegawaiAndTanggalBayarBetween(pegawai.toEntity(), tanggalMulai, tanggalAkhir);
	}

	@Override
	public long count(Pelanggan pelanggan, Date tanggalMulai, Date tanggalAkhir) {
		return pembayaranRepository.countByPelangganAndTanggalBayarBetween(pelanggan.toEntity(), tanggalMulai, tanggalAkhir);
	}
	
	@Override
	public long count(Perusahaan perusahaan, Tagihan tagihan) {
		return pembayaranRepository.countByPegawai_PerusahaanAndTagihan(perusahaan.toEntity(), tagihan.toEntity());
	}

	@Override
	public Tagihan getPayableTagihan(Pelanggan pelanggan) throws EntityNotExistException {
		PembayaranEntity pembayaranEntity = getLast(pelanggan);

		Tagihan tagihan;
		if (pembayaranEntity != null) {
			tagihan = pembayaranEntity.getTagihan();
			tagihan.increase();
		} else {
			Date tanggalDaftar = pelanggan.getTanggalMulai();
			int tahun = DateUtil.getYear(tanggalDaftar);
			Month month = DateUtil.getMonth(tanggalDaftar);
			
			tagihan = new TagihanValue(tahun, month);
		}

		return tagihan;
	}
}
