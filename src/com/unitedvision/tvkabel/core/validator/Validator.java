package com.unitedvision.tvkabel.core.validator;

import java.time.Month;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.unitedvision.tvkabel.core.domain.Alamat;
import com.unitedvision.tvkabel.core.domain.Pembayaran;
import com.unitedvision.tvkabel.core.domain.Tagihan;
import com.unitedvision.tvkabel.exception.DataDuplicationException;
import com.unitedvision.tvkabel.exception.EntityNotExistException;
import com.unitedvision.tvkabel.exception.UncompatibleTypeException;
import com.unitedvision.tvkabel.exception.UnpaidBillException;
import com.unitedvision.tvkabel.persistence.entity.AlamatValue;
import com.unitedvision.tvkabel.persistence.entity.KecamatanEntity;
import com.unitedvision.tvkabel.persistence.entity.KelurahanEntity;
import com.unitedvision.tvkabel.persistence.entity.KotaEntity;
import com.unitedvision.tvkabel.persistence.entity.PegawaiEntity;
import com.unitedvision.tvkabel.persistence.entity.PelangganEntity;
import com.unitedvision.tvkabel.persistence.entity.PembayaranEntity;
import com.unitedvision.tvkabel.persistence.entity.PerusahaanEntity;
import com.unitedvision.tvkabel.persistence.entity.PembayaranEntity.TagihanValue;
import com.unitedvision.tvkabel.persistence.repository.KecamatanRepository;
import com.unitedvision.tvkabel.persistence.repository.KelurahanRepository;
import com.unitedvision.tvkabel.persistence.repository.KotaRepository;
import com.unitedvision.tvkabel.persistence.repository.PegawaiRepository;
import com.unitedvision.tvkabel.persistence.repository.PelangganRepository;
import com.unitedvision.tvkabel.persistence.repository.PembayaranRepository;
import com.unitedvision.tvkabel.persistence.repository.PerusahaanRepository;

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

	public KecamatanEntity validate(KecamatanEntity kecamatanEntity) throws UncompatibleTypeException {
		KotaEntity kotaEntity = kecamatanEntity.getKota();

		try {
			if (kotaEntity.isNew())
				kotaEntity = kotaRepository.findByNama(kotaEntity.getNama());
		} catch (EntityNotExistException e) {
			kotaEntity = kotaRepository.save(kotaEntity.toEntity());
		}

		kecamatanEntity.setKota(kotaEntity);

		return kecamatanEntity;
	}
	
	public KelurahanEntity validate(KelurahanEntity kelurahanEntity) throws UncompatibleTypeException {
		KecamatanEntity kecamatanEntity = kelurahanEntity.getKecamatan();
		
		try {
			if (kecamatanEntity.isNew())
				kecamatanEntity = kecamatanRepository.findByNama(kecamatanEntity.getNama());
		} catch (EntityNotExistException e) {
			kecamatanEntity = validate(kecamatanEntity);
			kecamatanEntity = kecamatanRepository.save(kecamatanEntity.toEntity());
		}

		kelurahanEntity.setKecamatan(kecamatanEntity);
		
		return kelurahanEntity;
	}

	public AlamatValue validate(AlamatValue alamatValue) throws UncompatibleTypeException {
		KelurahanEntity kelurahanEntity = alamatValue.getKelurahan();
		
		try {
			if (kelurahanEntity.isNew())
				kelurahanEntity = kelurahanRepository.findByNama(kelurahanEntity.getNama());
		} catch (EntityNotExistException e) {
			kelurahanEntity = validate(kelurahanEntity);
			kelurahanEntity = kelurahanRepository.save(kelurahanEntity.toEntity());
		}

		alamatValue.setKelurahan(kelurahanEntity);
		
		return alamatValue;
	}
	
	public PerusahaanEntity validate(PerusahaanEntity perusahaanEntity) throws UncompatibleTypeException {
		AlamatValue alamatValue = perusahaanEntity.getAlamat();
		alamatValue = validate(alamatValue);
		perusahaanEntity.setAlamat((Alamat)alamatValue);
		
		return perusahaanEntity;
	}
	
	public PelangganEntity validate(PelangganEntity pelangganEntity) throws UncompatibleTypeException {
		AlamatValue alamatValue = pelangganEntity.getAlamat();
		alamatValue = validate(alamatValue);
		pelangganEntity.setAlamat((Alamat)alamatValue);
		
		return pelangganEntity;
	}

	/**
	 * Validates {@link PegawaiEntity} object.
	 * @param pegawaiEntity
	 * @return valid {@link PegawaiEntity} object.
	 * @throws UncompatibleTypeException
	 */
	public PegawaiEntity validate(PegawaiEntity pegawaiEntity) throws UncompatibleTypeException {
		PerusahaanEntity perusahaanEntity = pegawaiEntity.getPerusahaan();
		perusahaanEntity = validate(perusahaanEntity);
		pegawaiEntity.setPerusahaan(perusahaanEntity);
		
		return pegawaiEntity;
	}

	/**
	 * Validates {@link PembayaranEntity} object.
	 * @param pembayaranEntity
	 * @return valid {@link PembayaranEntity} object.
	 * @throws UnpaidBillException 'pembayaranEntity' is not valid due to Bill Payment error. See {@link Validator.isPreceding} method.
	 * @throws EntityNotExistException
	 * @throws DataDuplicationException 'pembayaranEntity' is not valid due to Bill Payment error. See {@link Validator.isPaid} method.
	 */
	public PembayaranEntity validate(PembayaranEntity pembayaranEntity) throws UnpaidBillException, EntityNotExistException, DataDuplicationException {
		if (!(compareWithTanggalMulai(pembayaranEntity))) {
			PembayaranEntity last = pembayaranRepository.findFirstByPelangganOrderByIdDesc(pembayaranEntity.getPelanggan().toEntity());
			
			isPaid(pembayaranEntity, last);
			isPreceding(pembayaranEntity, last);
		}
		
		return pembayaranEntity;
	}

	/**
	 * Check whether this is the first 'Pembayaran'/'Tagihan'.
	 * @param pembayaran
	 * @return true if {@link Tagihan} of 'pembayaran' equals to month and year of the related {@link Pelanggan}'s 'tanggalMulai'. Otherwise, false.
	 */
	private boolean compareWithTanggalMulai(Pembayaran pembayaran) {
		Date tanggalMulai = pembayaran.getPelanggan().getTanggalMulai();
		Tagihan tagihanAwal = TagihanValue.create(tanggalMulai);
		Tagihan tagihan = pembayaran.getTagihan();
		
		return tagihan.equals(tagihanAwal);
	}

	/**
	 * Check whether {@link TagihanValue} of 'pembayaran' has been paid or persisted in database before.
	 * @param pembayaran
	 * @param last
	 * @throws DataDuplicationException {@link TagihanValue} of 'pembayaran' has been paid or persisted in database before.
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
