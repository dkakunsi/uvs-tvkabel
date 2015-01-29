package com.unitedvision.tvkabel.core.service.impl;

import java.time.Month;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unitedvision.tvkabel.core.service.PelangganService;
import com.unitedvision.tvkabel.core.service.PembayaranService;
import com.unitedvision.tvkabel.core.validator.Validator;
import com.unitedvision.tvkabel.exception.ApplicationException;
import com.unitedvision.tvkabel.exception.DataDuplicationException;
import com.unitedvision.tvkabel.exception.EmptyIdException;
import com.unitedvision.tvkabel.exception.EntityNotExistException;
import com.unitedvision.tvkabel.exception.NotPayableCustomerException;
import com.unitedvision.tvkabel.exception.UnpaidBillException;
import com.unitedvision.tvkabel.persistence.entity.Pegawai;
import com.unitedvision.tvkabel.persistence.entity.Pelanggan;
import com.unitedvision.tvkabel.persistence.entity.Pembayaran;
import com.unitedvision.tvkabel.persistence.entity.Pembayaran.Tagihan;
import com.unitedvision.tvkabel.persistence.repository.PegawaiRepository;
import com.unitedvision.tvkabel.persistence.repository.PelangganRepository;
import com.unitedvision.tvkabel.persistence.repository.PembayaranRepository;
import com.unitedvision.tvkabel.util.DateUtil;
import com.unitedvision.tvkabel.util.PageSizeUtil;

@Service
@Transactional(readOnly = true)
public class PembayaranServiceImpl implements PembayaranService {
	@Autowired
	private PelangganService pelangganService;
	@Autowired
	private PembayaranRepository pembayaranRepository;
	@Autowired
	private PelangganRepository pelangganRepository;
	@Autowired
	private PegawaiRepository pegawaiRepository;
	@Autowired
	private Validator validator;

	@Override
	public void pay(Pelanggan pelanggan, Pegawai pegawai, long jumlahPembayaran, int jumlahBulan) throws NotPayableCustomerException ,UnpaidBillException ,EntityNotExistException ,DataDuplicationException, EmptyIdException {
		if (jumlahBulan > 1) {
			payList(pelanggan, pegawai, jumlahPembayaran, jumlahBulan);
		} else {
			Tagihan tagihan = getPayableTagihan(pelanggan);
			Pembayaran pembayaran = new Pembayaran(0, "", DateUtil.getNow(), pelanggan, pegawai, jumlahPembayaran, tagihan);
			
			pay(pembayaran);
		}
	};
	
	@Override
	public void payList(Pelanggan pelanggan, Pegawai pegawai, long jumlahPembayaran, int jumlahBulan) throws NotPayableCustomerException ,UnpaidBillException ,EntityNotExistException ,DataDuplicationException, EmptyIdException {
		List<Pembayaran> listPembayaran = createListPembayaran(pelanggan, pegawai, jumlahPembayaran, jumlahBulan);
		for (Pembayaran pembayaran : listPembayaran) {
			pay(pembayaran);
		}
	};

	/**
	 * Create list of {@link Pembayaran}.
	 * @param pelanggan
	 * @param pegawai
	 * @param jumlahPembayaran
	 * @param jumlahBulan
	 * @throws EntityNotExistException the given entity is not present in database.
	 * @throws EmptyIdException id passed to entity cannot be negative.
	 * @throws NotPayableCustomerException {@link Pelanggan.Status} is not {@code AKTIF}.
	 * @throws UnpaidBillException the given {@link Pembayaran} cannot be paid.
	 * @throws DataDuplicationException the given {@link Pembayaran} have been paid.
	 */
	public List<Pembayaran> createListPembayaran(Pelanggan pelanggan, Pegawai pegawai, long jumlahPembayaran, int jumlahBulan) throws EntityNotExistException, EmptyIdException, NotPayableCustomerException, UnpaidBillException, DataDuplicationException {
		Tagihan tagihan = getPayableTagihan(pelanggan);

		List<Pembayaran> listPembayaran = new ArrayList<>();
		//Add the first payment
		listPembayaran.add(new Pembayaran(0, "", DateUtil.getNow(), pelanggan, pegawai, jumlahPembayaran, tagihan));

		//Add the second and so on
		for (int i = 2; i <= jumlahBulan; i++) {
			//increase tagihan as the second payment
			tagihan = Tagihan.copy(tagihan);
			tagihan.increase();
			listPembayaran.add(new Pembayaran(0, "", DateUtil.getNow(), pelanggan, pegawai, jumlahPembayaran, tagihan));
		}
		
		return listPembayaran;
	}
	
	@Override
	public Pembayaran pay(Pembayaran pembayaran) throws NotPayableCustomerException, UnpaidBillException, EntityNotExistException, DataDuplicationException {
		if (!(pembayaran.getPelanggan().getStatus().equals(Pelanggan.Status.AKTIF)))
			throw new NotPayableCustomerException("Status BUKAN merupakan pelanggan AKTIF");

		//validator.validate(pembayaran);

		pembayaran.generateKode();
		pembayaran = pembayaranRepository.save(pembayaran);
		pelangganService.recountTunggakan(pembayaran.getPelanggan());
		
		return pembayaran;
	}
	
	@Override
	public Pembayaran updatePayment(Pembayaran pembayaran) {
		return pembayaranRepository.save(pembayaran);
	}
	
	@Override
	@Transactional(readOnly = false)
	public Pembayaran save(Pembayaran domain) throws ApplicationException {
		return pay(domain);
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(Pembayaran pembayaran) throws EntityNotExistException {
		pembayaran = pembayaranRepository.findOne(pembayaran.getId());
		pembayaranRepository.delete(pembayaran);
		pelangganService.recountTunggakan(pembayaran.getPelanggan());
	}

	@Override
	public Pembayaran getOne(int id) throws EntityNotExistException {
		return pembayaranRepository.findOne(id);
	}

	@Override
	public List<Pembayaran> get(Pegawai pegawai, Date tanggalMulai, Date tanggalAkhir, int page) throws EntityNotExistException {
		PageRequest pageRequest = new PageRequest(page, PageSizeUtil.DATA_NUMBER);

		return pembayaranRepository.findByPegawaiAndTanggalBayarBetween(pegawai, tanggalMulai, tanggalAkhir, pageRequest);
	}

	@Override
	public List<Pembayaran> get(Pelanggan pelanggan, Date tanggalMulai, Date tanggalAkhir, int page) throws EntityNotExistException {
		PageRequest pageRequest = new PageRequest(page, PageSizeUtil.DATA_NUMBER);

		return pembayaranRepository.findByPelangganAndTanggalBayarBetween(pelanggan, tanggalMulai, tanggalAkhir, pageRequest);
	}
	
	@Override
	public java.util.List<Pembayaran> get(Pelanggan pelanggan, Date tanggalMulai, Date tanggalAkhir) throws EntityNotExistException {
		return pembayaranRepository.findByPelangganAndTanggalBayarBetween(pelanggan, tanggalMulai, tanggalAkhir);
	}

	@Override
	public List<Pembayaran> get(Pelanggan pelanggan, int tahun) throws EntityNotExistException {
		return pembayaranRepository.findByPelangganAndTagihan_Tahun(pelanggan, tahun);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Pembayaran> get(Pelanggan pelanggan, Tagihan tagihanAwal, Tagihan tagihanAkhir) throws EntityNotExistException {
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
	public long count(Pegawai pegawai, Date tanggalMulai, Date tanggalAkhir) {
		return pembayaranRepository.countByPegawaiAndTanggalBayarBetween(pegawai, tanggalMulai, tanggalAkhir);
	}

	@Override
	public long count(Pelanggan pelanggan, Date tanggalMulai, Date tanggalAkhir) {
		return pembayaranRepository.countByPelangganAndTanggalBayarBetween(pelanggan, tanggalMulai, tanggalAkhir);
	}

	@Override
	public Pembayaran getLast(Pelanggan pelanggan) throws EntityNotExistException {
		return pembayaranRepository.findFirstByPelangganOrderByIdDesc(pelanggan);
	}

	@Override
	public Tagihan getPayableTagihan(Pelanggan pelanggan) throws EntityNotExistException {
		Pembayaran pembayaran = getLast(pelanggan);

		Tagihan tagihan;
		if (pembayaran != null) {
			Tagihan lastTagihan = pembayaran.getTagihan();
			
			tagihan = Tagihan.copy(lastTagihan);
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
