package com.unitedvision.tvkabel.core.validator;

import java.time.Month;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.unitedvision.tvkabel.domain.Alamat;
import com.unitedvision.tvkabel.domain.Kecamatan;
import com.unitedvision.tvkabel.domain.Kelurahan;
import com.unitedvision.tvkabel.domain.Kota;
import com.unitedvision.tvkabel.domain.Pegawai;
import com.unitedvision.tvkabel.domain.Pelanggan;
import com.unitedvision.tvkabel.domain.Pembayaran;
import com.unitedvision.tvkabel.domain.Pembayaran.Tagihan;
import com.unitedvision.tvkabel.domain.persistence.repository.KecamatanRepository;
import com.unitedvision.tvkabel.domain.persistence.repository.KelurahanRepository;
import com.unitedvision.tvkabel.domain.persistence.repository.KotaRepository;
import com.unitedvision.tvkabel.domain.persistence.repository.PegawaiRepository;
import com.unitedvision.tvkabel.domain.persistence.repository.PelangganRepository;
import com.unitedvision.tvkabel.domain.persistence.repository.PembayaranRepository;
import com.unitedvision.tvkabel.domain.persistence.repository.PerusahaanRepository;
import com.unitedvision.tvkabel.domain.Perusahaan;
import com.unitedvision.tvkabel.exception.DataDuplicationException;
import com.unitedvision.tvkabel.exception.EntityNotExistException;
import com.unitedvision.tvkabel.exception.UncompatibleTypeException;
import com.unitedvision.tvkabel.exception.UnpaidBillException;

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

	public Kecamatan validate(Kecamatan kecamatan) throws UncompatibleTypeException {
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
	
	public Kelurahan validate(Kelurahan kelurahan) throws UncompatibleTypeException {
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

	public Alamat validate(Alamat alamat) throws UncompatibleTypeException {
		Kelurahan kelurahan = alamat.getKelurahan();
		
		try {
			if (kelurahan.isNew())
				kelurahan = kelurahanRepository.findByNama(kelurahan.getNama());
		} catch (EntityNotExistException e) {
			kelurahan = validate(kelurahan);
			kelurahan = kelurahanRepository.save(kelurahan);
		}

		alamat.setKelurahan(kelurahan);
		
		return alamat;
	}
	
	public Perusahaan validate(Perusahaan perusahaan) throws UncompatibleTypeException {
		Alamat alamat = perusahaan.getAlamat();
		alamat = validate(alamat);
		perusahaan.setAlamat((Alamat)alamat);
		
		return perusahaan;
	}
	
	public Pelanggan validate(Pelanggan pelanggan) throws UncompatibleTypeException {
		Alamat alamat = pelanggan.getAlamat();
		alamat = validate(alamat);
		pelanggan.setAlamat((Alamat)alamat);
		
		return pelanggan;
	}

	/**
	 * Validates {@link Pegawai} object.
	 * @param pegawai
	 * @return valid {@link Pegawai} object.
	 * @throws UncompatibleTypeException
	 */
	public Pegawai validate(Pegawai pegawai) throws UncompatibleTypeException {
		Perusahaan perusahaan = pegawai.getPerusahaan();
		perusahaan = validate(perusahaan);
		pegawai.setPerusahaan(perusahaan);
		
		return pegawai;
	}

	/**
	 * Validates {@link Pembayaran} object.
	 * @param pembayaran
	 * @return valid {@link Pembayaran} object.
	 * @throws UnpaidBillException 'pembayaran' is not valid due to Bill Payment error. See {@link Validator.isPreceding} method.
	 * @throws EntityNotExistException
	 * @throws DataDuplicationException 'pembayaran' is not valid due to Bill Payment error. See {@link Validator.isPaid} method.
	 */
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
