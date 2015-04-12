package com.unitedvision.tvkabel.core.validator;

import java.time.Month;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.unitedvision.tvkabel.entity.Kecamatan;
import com.unitedvision.tvkabel.entity.Kelurahan;
import com.unitedvision.tvkabel.entity.Kota;
import com.unitedvision.tvkabel.entity.Pegawai;
import com.unitedvision.tvkabel.entity.Pelanggan;
import com.unitedvision.tvkabel.entity.Pembayaran;
import com.unitedvision.tvkabel.entity.Perusahaan;
import com.unitedvision.tvkabel.entity.Pembayaran.Tagihan;
import com.unitedvision.tvkabel.exception.DataDuplicationException;
import com.unitedvision.tvkabel.exception.EntityNotExistException;
import com.unitedvision.tvkabel.exception.UnpaidBillException;
import com.unitedvision.tvkabel.repository.KecamatanRepository;
import com.unitedvision.tvkabel.repository.KelurahanRepository;
import com.unitedvision.tvkabel.repository.KotaRepository;
import com.unitedvision.tvkabel.repository.PegawaiRepository;
import com.unitedvision.tvkabel.repository.PelangganRepository;
import com.unitedvision.tvkabel.repository.PembayaranRepository;
import com.unitedvision.tvkabel.repository.PerusahaanRepository;

@Component
public class Validator {
	@Autowired
	private KotaRepository kotaRepository;
	@Autowired
	private KecamatanRepository kecamatanRepository;
	@Autowired
	private KelurahanRepository kelurahanRepository;
	@Autowired
	private PerusahaanRepository perusahaanRepository;
	@Autowired
	private PelangganRepository pelangganRepository;
	@Autowired
	private PegawaiRepository pegawaiRepository;
	@Autowired
	private PembayaranRepository pembayaranRepository;

	public Kecamatan validate(Kecamatan kecamatan) {
		Kota kota = kecamatan.getKota();

		try {
			if (kota.isNew())
				kota = kotaRepository.findByNama(kota.getNama());
		} catch (EntityNotExistException e) {
			kota = kotaRepository.save(kota);
		}

		kecamatan.setKota(kota);

		return kecamatan;
	}
	
	public Kelurahan validate(Kelurahan kelurahan) {
		Kecamatan kecamatan = kelurahan.getKecamatan();
		
		try {
			if (kecamatan.isNew())
				kecamatan = kecamatanRepository.findByNama(kecamatan.getNama());
		} catch (EntityNotExistException e) {
			kecamatan = validate(kecamatan);
			kecamatan = kecamatanRepository.save(kecamatan);
		}

		kelurahan.setKecamatan(kecamatan);
		
		return kelurahan;
	}

	public Perusahaan validate(Perusahaan perusahaan) {
		Kelurahan kelurahan = validate(perusahaan.getKelurahan());
		perusahaan.setKelurahan(kelurahan);
		
		return perusahaan;
	}
	
	public Pelanggan validate(Pelanggan pelanggan) {
		Kelurahan kelurahan = validate(pelanggan.getKelurahan());
		pelanggan.setKelurahan(kelurahan);
		
		return pelanggan;
	}

	/**
	 * Validates {@link Pegawai} object.
	 * @param pegawai
	 * @return valid {@link Pegawai} object.
	 * @throws UncompatibleTypeException
	 */
	public Pegawai validate(Pegawai pegawai) {
		Perusahaan perusahaan = pegawai.getPerusahaan();
		perusahaan = validate(perusahaan);
		pegawai.setPerusahaan(perusahaan);
		
		return pegawai;
	}

	/**
	 * Validates {@link Pembayaran} object. If {@link Tagihan} is the same as {@code tanggalMulai} then it is the first payment and no need for further validation.
	 * @param pembayaran
	 * @return valid {@link Pembayaran} object.
	 * @throws UnpaidBillException 'pembayaran' is not valid due to Bill Payment error. See {@link Validator.isPreceding} method.
	 * @throws EntityNotExistException
	 * @throws DataDuplicationException 'pembayaran' is not valid due to Bill Payment error. See {@link Validator.isPaid} method.
	 */
	@Deprecated
	public Pembayaran validate(Pembayaran pembayaran) throws UnpaidBillException, EntityNotExistException, DataDuplicationException {
		if (!(compareWithTanggalMulai(pembayaran))) {
			Pembayaran last = pembayaranRepository.findFirstByPelangganOrderByIdDesc(pembayaran.getPelanggan());
			
			isPaid(pembayaran, last);
			isPreceding(pembayaran, last);
		}
		
		return pembayaran;
	}

	/**
	 * Check whether this is the first 'Pembayaran'/'Tagihan'.
	 * @param pembayaran
	 * @return true if {@link Tagihan} of 'pembayaran' equals to month and year of the related {@link Pelanggan}'s 'tanggalMulai'. Otherwise, false.
	 */
	private boolean compareWithTanggalMulai(Pembayaran pembayaran) {
		Date tanggalMulai = pembayaran.getPelanggan().getTanggalMulai();
		Tagihan tagihanAwal = Tagihan.create(tanggalMulai);
		Tagihan tagihan = pembayaran.getTagihan();
		
		return tagihan.equals(tagihanAwal);
	}

	/**
	 * Check whether {@link Tagihan} of 'pembayaran' has been paid or persisted in database before.
	 * @param pembayaran
	 * @param last
	 * @throws DataDuplicationException {@link Tagihan} of 'pembayaran' has been paid or persisted in database before.
	 */
	private void isPaid(Pembayaran pembayaran, Pembayaran last) throws DataDuplicationException {
		if (pembayaran.isPaid(last)) {
			throw new DataDuplicationException("Gagal menyimpan data pembayaran.<br />"
					+ "Anda sudah membayar tagihan bulan '" + pembayaran.getTagihan().toString());
		}
	}
	
	/**
	 * Check whether 'last' is the preceding {@link Pembayaran} of 'pembayaran'.
	 * @param pembayaran
	 * @param last
	 * @throws UnpaidBillException 'pembayaran' with preceding {@link Tagihan} was not paid. It must be paid in order to pay this 'pembayaran'.
	 */
	private void isPreceding(Pembayaran pembayaran, Pembayaran last) throws UnpaidBillException {
		if (!(last.isPreceding(pembayaran))) {
			Month lastMonth = pembayaran.getBulan().minus(1);
			int year = pembayaran.getTahun();
			
			if (lastMonth == Month.DECEMBER)
				year--;
			
			throw new UnpaidBillException("Gagal menyimpan data pembayaran.<br />"
					+ "Anda belum membayar tagihan bulan '" + lastMonth + "-" + year + "'");
		}
	}
}
