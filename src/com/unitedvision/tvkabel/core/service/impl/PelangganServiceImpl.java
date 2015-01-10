package com.unitedvision.tvkabel.core.service.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unitedvision.tvkabel.core.domain.Kelurahan;
import com.unitedvision.tvkabel.core.domain.Pegawai;
import com.unitedvision.tvkabel.core.domain.Pelanggan;
import com.unitedvision.tvkabel.core.domain.Pembayaran;
import com.unitedvision.tvkabel.core.domain.Removable;
import com.unitedvision.tvkabel.core.domain.Pelanggan.Status;
import com.unitedvision.tvkabel.core.domain.Perusahaan;
import com.unitedvision.tvkabel.core.service.PelangganService;
import com.unitedvision.tvkabel.core.service.PembayaranService;
import com.unitedvision.tvkabel.core.validator.Validator;
import com.unitedvision.tvkabel.exception.ApplicationException;
import com.unitedvision.tvkabel.exception.DataDuplicationException;
import com.unitedvision.tvkabel.exception.EntityNotExistException;
import com.unitedvision.tvkabel.exception.StatusChangeException;
import com.unitedvision.tvkabel.exception.UncompatibleTypeException;
import com.unitedvision.tvkabel.persistence.entity.AlamatValue;
import com.unitedvision.tvkabel.persistence.entity.PelangganEntity;
import com.unitedvision.tvkabel.persistence.entity.PembayaranEntity;
import com.unitedvision.tvkabel.persistence.repository.PelangganRepository;
import com.unitedvision.tvkabel.persistence.repository.PerusahaanRepository;
import com.unitedvision.tvkabel.util.CodeUtil;
import com.unitedvision.tvkabel.util.CodeUtil.CodeGenerator;
import com.unitedvision.tvkabel.util.DateUtil;
import com.unitedvision.tvkabel.util.PageSizeUtil;

@Service
@Transactional(readOnly = true)
public class PelangganServiceImpl implements PelangganService {
	@Autowired
	private PelangganRepository pelangganRepository;
	@Autowired
	private PembayaranService pembayaranService;
	@Autowired
	private PerusahaanRepository perusahaanRepository;
	@Autowired
	private Validator validator;

	@Override
	@Transactional(readOnly = false)
	public Pelanggan save(Pelanggan domain) throws UncompatibleTypeException, DataDuplicationException {
		domain = validator.validate(domain.toEntity());

		if (domain.isNew())
			domain.countTunggakan(); //secara otomatis atribut tanggalMulai digunakan sebagai tagihan awal(pertama)

		try {
			domain = pelangganRepository.save(domain.toEntity());
		} catch(PersistenceException e) {
			throw new DataDuplicationException(e.getMessage());
		}

		return domain;
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(Pelanggan domain) {
		domain = pelangganRepository.findOne(domain.getId());
		pelangganRepository.delete(domain.toEntity());
	}

	@Override
	public void remove(Pelanggan domain) throws EntityNotExistException, StatusChangeException {
		domain = pelangganRepository.findOne(domain.getId());
		((Removable)domain).remove();

		pelangganRepository.save(domain.toEntity());
	}

	@Override
	public void activate(Pelanggan pelanggan) throws UncompatibleTypeException, StatusChangeException, DataDuplicationException {
		if (pelanggan.getStatus().equals(Status.AKTIF))
			throw new StatusChangeException("Tidak mengaktivasi pelanggan.<br />"
					+ "Karena pelanggan merupakan pelanggan aktif");
		
		pelanggan.setStatus(Status.AKTIF);
		pelanggan.getDetail().setTanggalMulai(DateUtil.getNow());
		pelanggan.getDetail().setTunggakan(0);
		
		save(pelanggan);
	}
	
	@Override
	public void passivate(Pelanggan pelanggan) throws UncompatibleTypeException, StatusChangeException, DataDuplicationException {
		if (pelanggan.getStatus().equals(Status.BERHENTI))
			throw new StatusChangeException("Tidak memutuskan pelanggan.<br />"
					+ "Karena pelanggan merupakan pelanggan berhenti");

		pelanggan.setStatus(Status.BERHENTI);
		
		save(pelanggan);
	}
	
	@Override
	public void banned(Pelanggan pelanggan) throws UncompatibleTypeException, EntityNotExistException, StatusChangeException, DataDuplicationException {
		if (pelanggan.getStatus().equals(Status.PUTUS))
			throw new StatusChangeException("Tidak mem-banned pelanggan.<br />"
					+ "Karena pelanggan merupakan pelanggan putus");

		pelanggan.setStatus(Status.PUTUS);
		pelanggan.countTunggakan(pembayaranService.getLast(pelanggan));
		
		save(pelanggan);
	}
	
	@Override
	public void setMapLocation(Pelanggan pelanggan, float latitude, float longitude) throws ApplicationException {
		AlamatValue alamat = pelanggan.getAlamat().toEntity();
		alamat.setLatitude(latitude);;
		alamat.setLongitude(longitude);
		
		pelanggan.setAlamat(alamat);
		
		save(pelanggan);
	}

	@Override
	public void recountTunggakan() throws ApplicationException {
		Date now = DateUtil.getNow();

		recountTunggakan(DateUtil.getDay(now));
	}
	
	@Override
	public void recountTunggakan(int tanggal) throws ApplicationException {
		List<? extends Pelanggan> listPelanggan = get(Status.AKTIF, tanggal);
		
		for (Pelanggan pelanggan : listPelanggan) {
			Pembayaran pembayaranTerakhir = pembayaranService.getLast(pelanggan);
			
			if (pembayaranTerakhir == null) {
				pelanggan.countTunggakan(); //secara otomatis atribut tanggalMulai digunakan sebagai tagihan awal(pertama)
			} else {
				pelanggan.countTunggakan(pembayaranTerakhir);
			}
			
			pelangganRepository.save(pelanggan.toEntity());
		}
	}
	
	@Override
	public PelangganEntity getOne(int id){
		return pelangganRepository.findOne(id);
	}

	@Override
	public PelangganEntity getOneByNama(Perusahaan perusahaan, String nama) throws EntityNotExistException {
		return pelangganRepository.findByPerusahaanAndNama(perusahaan.toEntity(), nama);
	}

	@Override
	public PelangganEntity getOneByKode(Perusahaan perusahaan, String kode) throws EntityNotExistException {
		return pelangganRepository.findByPerusahaanAndKode(perusahaan.toEntity(), kode);
	}

	@Override
	public List<? extends Pelanggan> getByKode(Perusahaan perusahaan, String kode, int pageNumber) {
		PageRequest page = new PageRequest(pageNumber, PageSizeUtil.DATA_NUMBER);

		return pelangganRepository.findByPerusahaanAndKodeContainingOrderByKodeAsc(perusahaan.toEntity(), kode, page);
	}

	@Override
	public List<? extends Pelanggan> getByNama(Perusahaan perusahaan, String nama, int pageNumber) {
		PageRequest page = new PageRequest(pageNumber, PageSizeUtil.DATA_NUMBER);

		return pelangganRepository.findByPerusahaanAndNamaContainingOrderByKodeAsc(perusahaan.toEntity(), nama, page);
	}
	
	@Override
	public List<? extends Pelanggan> get(Status status, int tanggal) {
		return pelangganRepository.findByTanggalMulai(status, tanggal);
	}
	
	@Override
	public List<PelangganEntity> get(Perusahaan perusahaan, Status status) {
		return pelangganRepository.findByPerusahaanAndStatusOrderByKodeAsc(perusahaan.toEntity(), status);
	}

	@Override
	public List<PelangganEntity> get(Perusahaan perusahaan, Status status, int pageNumber) {
		PageRequest page = new PageRequest(pageNumber, PageSizeUtil.DATA_NUMBER);
		
		return pelangganRepository.findByPerusahaanAndStatusOrderByKodeAsc(perusahaan.toEntity(), status, page);
	}

	@Override
	public List<PelangganEntity> getByTunggakan(Perusahaan perusahaan, Status status, int tunggakan) {
		return pelangganRepository.findByPerusahaanAndStatusAndDetail_TunggakanOrderByKodeAsc(perusahaan.toEntity(), status, tunggakan);
	}

	@Override
	public List<PelangganEntity> getByTunggakan(Perusahaan perusahaan, Status status, int tunggakan, int pageNumber) {
		PageRequest page = new PageRequest(pageNumber, PageSizeUtil.DATA_NUMBER);
		
		return pelangganRepository.findByPerusahaanAndStatusAndDetail_TunggakanOrderByKodeAsc(perusahaan.toEntity(), status, tunggakan, page);
	}

	@Override
	public List<PelangganEntity> getByNama(Perusahaan perusahaan, Status status, String nama, int pageNumber) {
		PageRequest page = new PageRequest(pageNumber, PageSizeUtil.DATA_NUMBER);
		
		return pelangganRepository.findByPerusahaanAndStatusAndNamaContainingOrderByKodeAsc(perusahaan.toEntity(), status, nama, page);
	}

	@Override
	public List<PelangganEntity> getByKode(Perusahaan perusahaan, Status status, String kode, int pageNumber) {
		PageRequest page = new PageRequest(pageNumber, PageSizeUtil.DATA_NUMBER);
		
		return pelangganRepository.findByPerusahaanAndStatusAndKodeContainingOrderByKodeAsc(perusahaan.toEntity(), status, kode, page);
	}

	@Override
	public List<PelangganEntity> get(Perusahaan perusahaan, Status status, Kelurahan kelurahan, int lingkungan) {
		return pelangganRepository.findByPerusahaanAndStatusAndKelurahanAndAlamat_LingkunganOrderByKodeAsc(perusahaan.toEntity(), status, kelurahan.toEntity(), lingkungan);
	}

	@Override
	public List<PelangganEntity> get(Perusahaan perusahaan, Status status, Kelurahan kelurahan, int lingkungan, int pageNumber) {
		PageRequest page = new PageRequest(pageNumber, PageSizeUtil.DATA_NUMBER);
		
		return pelangganRepository.findByPerusahaanAndStatusAndKelurahanAndAlamat_LingkunganOrderByKodeAsc(perusahaan.toEntity(), status, kelurahan.toEntity(), lingkungan, page);
	}

	@Override
	public List<? extends Pelanggan> get(Pegawai pegawai, Date tanggalBayar) {
		String tanggalBayarStr = DateUtil.toDatabaseString(tanggalBayar, "-");
		
		return pelangganRepository.findByPembayaran(pegawai.getId(), tanggalBayarStr);
	}

	@Override
	public long count(Pegawai pegawai, Date tanggalBayar) {
		String tanggalBayarStr = DateUtil.toDatabaseString(tanggalBayar, "-");
		
		return pelangganRepository.countByPembayaran(pegawai.getId(), tanggalBayarStr);
	}

	@Override
	public List<? extends Pelanggan> get(Pegawai pegawai, Date tanggalBayar, int pageNumber) {
		//PageRequest page = new PageRequest(pageNumber, PageSizeUtil.DATA_NUMBER);
		String tanggalBayarStr = DateUtil.toDatabaseString(tanggalBayar, "-");
		
		return pelangganRepository.findByPembayaran(pegawai.getId(), tanggalBayarStr);
	}
	
	@Override
	public long count(Perusahaan perusahaan, Status status) {
		return pelangganRepository.countByPerusahaanAndStatus(perusahaan.toEntity(), status);
	}

	@Override
	public long countByKode(Perusahaan perusahaan, String kode) {
		return pelangganRepository.countByPerusahaanAndKodeContaining(perusahaan.toEntity(), kode);
	}

	@Override
	public long countByNama(Perusahaan perusahaan, String nama) {
		return pelangganRepository.countByPerusahaanAndNamaContaining(perusahaan.toEntity(), nama);
	}
	
	@Override
	public long countByNama(Perusahaan perusahaan, Status status, String nama) {
		return pelangganRepository.countByPerusahaanAndStatusAndNamaContaining(perusahaan.toEntity(), status, nama);
	}

	@Override
	public long countByKode(Perusahaan perusahaan, Status status, String kode) {
		return pelangganRepository.countByPerusahaanAndStatusAndKodeContaining(perusahaan.toEntity(), status, kode);
	}

	@Override
	public long countByTunggakan(Perusahaan perusahaan, Status status, int tunggakan) {
		return pelangganRepository.countByPerusahaanAndStatusAndDetail_Tunggakan(perusahaan.toEntity(), status, tunggakan);
	}
	
	@Override
	public long countByTunggakanLessThan(Perusahaan perusahaan, Status status, int tunggakan) {
		return pelangganRepository.countByPerusahaanAndStatusAndDetail_TunggakanLessThan(perusahaan.toEntity(), status, tunggakan);
	}
	
	@Override
	public long countByTunggakanGreaterThan(Perusahaan perusahaan, Status status, int tunggakan) {
		return pelangganRepository.countByPerusahaanAndStatusAndDetail_TunggakanGreaterThan(perusahaan.toEntity(), status, tunggakan);
	}

	@Override
	public long count(Perusahaan perusahaan, Status status, Kelurahan kelurahan, int lingkungan) {
		return pelangganRepository.countByPerusahaanAndStatusAndKelurahanAndAlamat_Lingkungan(perusahaan.toEntity(), status, kelurahan.toEntity(), lingkungan);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Pelanggan cetakKartu(Pelanggan pelanggan) {
		int tahun = DateUtil.getYearNow();
		pelanggan.setListPembayaran((List<PembayaranEntity>)pembayaranService.get(pelanggan, tahun));

		return pelanggan;
	}

	@Override
	public List<Pelanggan> cetakKartu(List<Pelanggan> listPelanggan) {
		for (Pelanggan pelanggan : listPelanggan) {
			cetakKartu(pelanggan);
		}

		return listPelanggan;
	}

	@Override
	public String resetKode(Perusahaan perusahaan, Kelurahan kelurahan, int lingkungan) {
		List<PelangganEntity> listPelanggan = pelangganRepository.findByPerusahaanAndStatusAndKelurahanAndAlamat_LingkunganOrderByKodeAsc(perusahaan.toEntity(), Pelanggan.Status.AKTIF, kelurahan.toEntity(), lingkungan);

		CodeUtil.CodeGenerator codeGenerator = new CodeGenerator();
		String message = "";
		int numOfChange = 0;
		for (Pelanggan pelanggan : listPelanggan) {
			String generatedKode = codeGenerator.createKode(pelanggan);
			message = String.format("%sKode untuk %s: %s\n", message, pelanggan.getNama(), generatedKode);
			
			try {
				pelanggan.setKode(generatedKode);
				pelangganRepository.save(pelanggan.toEntity());

				if (generatedKode.contains("W")) {
					codeGenerator.increase();
					numOfChange++;
				}
			} catch (Exception e) { }
		}
		
		message = String.format("%s \n\n Jumlah Perubahan : %d dari %d pelanggan.", message, numOfChange, listPelanggan.size());
		
		return message;
	}
}
