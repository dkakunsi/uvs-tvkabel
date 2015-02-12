package com.unitedvision.tvkabel.core.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.PersistenceException;

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
import com.unitedvision.tvkabel.exception.StatusChangeException;
import com.unitedvision.tvkabel.exception.UnpaidBillException;
import com.unitedvision.tvkabel.persistence.entity.Alamat;
import com.unitedvision.tvkabel.persistence.entity.Kelurahan;
import com.unitedvision.tvkabel.persistence.entity.Pegawai;
import com.unitedvision.tvkabel.persistence.entity.Pelanggan;
import com.unitedvision.tvkabel.persistence.entity.Pembayaran;
import com.unitedvision.tvkabel.persistence.entity.Perusahaan;
import com.unitedvision.tvkabel.persistence.entity.Removable;
import com.unitedvision.tvkabel.persistence.entity.Pelanggan.Status;
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
	public Pelanggan save(Pelanggan domain) throws DataDuplicationException {
		domain = validator.validate(domain);

		if (domain.isNew())
			domain.countTunggakan(); //secara otomatis atribut tanggalMulai digunakan sebagai tagihan awal(pertama)

		try {
			domain = pelangganRepository.save(domain);
		} catch(PersistenceException e) {
			throw new DataDuplicationException(e.getMessage());
		}

		return domain;
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(Pelanggan domain) {
		domain = pelangganRepository.findOne(domain.getId());
		pelangganRepository.delete(domain);
	}

	@Override
	public void remove(Pelanggan domain) throws StatusChangeException {
		domain = pelangganRepository.findOne(domain.getId());
		((Removable)domain).remove();

		pelangganRepository.save(domain);
	}

	@Override
	public void activate(Pelanggan pelanggan) throws StatusChangeException, DataDuplicationException {
		if (pelanggan.getStatus().equals(Status.AKTIF))
			throw new StatusChangeException("Tidak mengaktivasi pelanggan. Karena pelanggan merupakan pelanggan aktif");
		
		pelanggan.setStatus(Status.AKTIF);
		pelanggan.getDetail().setTanggalMulai(DateUtil.getNow());
		pelanggan.getDetail().setTunggakan(0);
		
		save(pelanggan);
	}
	
	@Override
	public void passivate(Pelanggan pelanggan) throws StatusChangeException, DataDuplicationException {
		if (pelanggan.getStatus().equals(Status.BERHENTI))
			throw new StatusChangeException("Tidak memutuskan pelanggan. Karena pelanggan merupakan pelanggan berhenti");

		pelanggan.setStatus(Status.BERHENTI);
		
		save(pelanggan);
	}
	
	@Override
	public void banned(Pelanggan pelanggan) throws StatusChangeException, DataDuplicationException {
		if (pelanggan.getStatus().equals(Status.PUTUS))
			throw new StatusChangeException("Tidak mem-banned pelanggan. Karena pelanggan merupakan pelanggan putus");

		pelanggan.setStatus(Status.PUTUS);

		Pembayaran last = pembayaranService.getLast(pelanggan);

		pelanggan.countTunggakan(last);
		
		save(pelanggan);
	}
	
	@Override
	public void free(Pelanggan pelanggan) throws ApplicationException {
		if (pelanggan.getStatus().equals(Status.GRATIS))
			throw new StatusChangeException("Tidak menggratiskan pelanggan. Karena pelanggan merupakan pelanggan gratis");

		pelanggan.setStatus(Status.GRATIS);

		save(pelanggan);
	}
	
	@Override
	public void setMapLocation(Pelanggan pelanggan, float latitude, float longitude) throws ApplicationException {
		Alamat alamat = pelanggan.getAlamat();
		alamat.setLatitude(latitude);;
		alamat.setLongitude(longitude);
		
		pelanggan.setAlamat(alamat);
		
		save(pelanggan);
	}

	@Override
	public void recountTunggakan() throws ApplicationException {
		for (int i = 1; i <= 31; i++) {
			recountTunggakan(Integer.toString(i));
		}
	}
	
	@Override
	public void recountTunggakan(String tanggal) throws ApplicationException {
		tanggal = DateUtil.getDayString(tanggal);
		recountTunggakanStatusAktif(tanggal);
		recountTunggakanStatusGratis(tanggal);
	}
	
	private void recountTunggakanStatusAktif(String tanggal) throws EntityNotExistException {
		List<Pelanggan> listPelanggan = get(Status.AKTIF, tanggal);
		
		for (Pelanggan pelanggan : listPelanggan)
			recountTunggakan(pelanggan);
	}
	
	private void recountTunggakanStatusGratis(String tanggal) throws EntityNotExistException, NotPayableCustomerException, UnpaidBillException, DataDuplicationException, EmptyIdException {
		List<Pelanggan> listPelanggan = get(Status.GRATIS, tanggal);
		
		for (Pelanggan pelanggan : listPelanggan) {
			pembayaranService.pay(pelanggan);
			recountTunggakan(pelanggan);
		}
	}
	
	@Override
	public void recountTunggakan(Pelanggan pelanggan) {
		Pembayaran pembayaran = pembayaranService.getLast(pelanggan);
		
		if (pembayaran == null) {
			pelanggan.countTunggakan(); //secara otomatis atribut tanggalMulai digunakan sebagai tagihan awal(pertama)
		} else {
			pelanggan.countTunggakan(pembayaran);
		}
		
		pelangganRepository.save(pelanggan);
	};
	
	@Override
	public Pelanggan getOne(int id) throws EntityNotExistException {
		return pelangganRepository.findOne(id);
	}

	@Override
	public Pelanggan getOneByNama(Perusahaan perusahaan, String nama) throws EntityNotExistException {
		return pelangganRepository.findByPerusahaanAndNama(perusahaan, nama);
	}

	@Override
	public Pelanggan getOneByKode(Perusahaan perusahaan, String kode) throws EntityNotExistException {
		return pelangganRepository.findByPerusahaanAndKode(perusahaan, kode);
	}

	@Override
	public List<Pelanggan> getByKode(Perusahaan perusahaan, String kode, int pageNumber) throws EntityNotExistException {
		PageRequest page = new PageRequest(pageNumber, PageSizeUtil.DATA_NUMBER);

		return pelangganRepository.findByPerusahaanAndKodeContainingOrderByKodeAsc(perusahaan, kode, page);
	}

	@Override
	public List<Pelanggan> getByNama(Perusahaan perusahaan, String nama, int pageNumber) throws EntityNotExistException {
		PageRequest page = new PageRequest(pageNumber, PageSizeUtil.DATA_NUMBER);

		return pelangganRepository.findByPerusahaanAndNamaContainingOrderByKodeAsc(perusahaan, nama, page);
	}
	
	@Override
	public List<Pelanggan> get(Status status, String tanggal) throws EntityNotExistException {
		return pelangganRepository.findByTanggalMulai(status.ordinal(), tanggal);
	}
	
	@Override
	public List<Pelanggan> get(Perusahaan perusahaan, Status status) throws EntityNotExistException {
		return pelangganRepository.findByPerusahaanAndStatusOrderByKodeAsc(perusahaan, status);
	}

	@Override
	public List<Pelanggan> get(Perusahaan perusahaan, int nomorBuku, int pageNumber) throws EntityNotExistException {
		PageRequest page = new PageRequest(pageNumber, PageSizeUtil.DATA_NUMBER);

		return pelangganRepository.findByPerusahaanAndNomorBukuContainingOrderByKodeAsc(perusahaan, nomorBuku, page);
	}
	
	@Override
	public List<Pelanggan> get(Perusahaan perusahaan, Status status, int nomorBuku, int pageNumber) throws EntityNotExistException {
		PageRequest page = new PageRequest(pageNumber, PageSizeUtil.DATA_NUMBER);

		return pelangganRepository.findByPerusahaanAndStatusAndNomorBukuContainingOrderByKodeAsc(perusahaan, status, nomorBuku, page);
	}
	
	@Override
	public List<Pelanggan> get(Perusahaan perusahaan, Status status, int pageNumber) throws EntityNotExistException {
		PageRequest page = new PageRequest(pageNumber, PageSizeUtil.DATA_NUMBER);
		
		return pelangganRepository.findByPerusahaanAndStatusOrderByKodeAsc(perusahaan, status, page);
	}

	@Override
	public List<Pelanggan> getByTunggakan(Perusahaan perusahaan, Status status, int tunggakan) throws EntityNotExistException {
		return pelangganRepository.findByPerusahaanAndStatusAndDetail_TunggakanOrderByKodeAsc(perusahaan, status, tunggakan);
	}

	@Override
	public List<Pelanggan> getByNama(Perusahaan perusahaan, Status status, String nama, int pageNumber) throws EntityNotExistException {
		PageRequest page = new PageRequest(pageNumber, PageSizeUtil.DATA_NUMBER);
		
		return pelangganRepository.findByPerusahaanAndStatusAndNamaContainingOrderByKodeAsc(perusahaan, status, nama, page);
	}

	@Override
	public List<Pelanggan> getByKode(Perusahaan perusahaan, Status status, String kode, int pageNumber) throws EntityNotExistException {
		PageRequest page = new PageRequest(pageNumber, PageSizeUtil.DATA_NUMBER);
		
		return pelangganRepository.findByPerusahaanAndStatusAndKodeContainingOrderByKodeAsc(perusahaan, status, kode, page);
	}

	@Override
	public List<Pelanggan> get(Perusahaan perusahaan, Status status, Kelurahan kelurahan, int lingkungan) throws EntityNotExistException {
		return pelangganRepository.findByPerusahaanAndStatusAndKelurahanAndAlamat_LingkunganOrderByKodeAsc(perusahaan, status, kelurahan, lingkungan);
	}

	@Override
	public List<Pelanggan> get(Perusahaan perusahaan, Date tanggalBayarAwal, Date tanggalBayarAkhir) throws EntityNotExistException {
		String tanggalBayarAwalStr = DateUtil.toDatabaseString(tanggalBayarAwal, "-");
		String tanggalBayarAkhirStr = DateUtil.toDatabaseString(tanggalBayarAkhir, "-");
		
		return pelangganRepository.findByPembayaran_RekapBulanan(perusahaan.getId(), tanggalBayarAwalStr, tanggalBayarAkhirStr);
	}
	
	@Override
	public List<Pelanggan> get(Pegawai pegawai, Date tanggalBayarAwal, Date tanggalBayarAkhir) throws EntityNotExistException {
		String tanggalBayarAwalStr = DateUtil.toDatabaseString(tanggalBayarAwal, "-");
		String tanggalBayarAkhirStr = DateUtil.toDatabaseString(tanggalBayarAkhir, "-");
		
		return pelangganRepository.findByPembayaran(pegawai.getId(), tanggalBayarAwalStr, tanggalBayarAkhirStr);
	}
	
	@Override
	public long count(Perusahaan perusahaan, Status status) {
		return pelangganRepository.countByPerusahaanAndStatus(perusahaan, status);
	}

	@Override
	public long countByKode(Perusahaan perusahaan, String kode) {
		return pelangganRepository.countByPerusahaanAndKodeContaining(perusahaan, kode);
	}

	@Override
	public long countByNama(Perusahaan perusahaan, String nama) {
		return pelangganRepository.countByPerusahaanAndNamaContaining(perusahaan, nama);
	}
	
	@Override
	public long countByNama(Perusahaan perusahaan, Status status, String nama) {
		return pelangganRepository.countByPerusahaanAndStatusAndNamaContaining(perusahaan, status, nama);
	}

	@Override
	public long countByKode(Perusahaan perusahaan, Status status, String kode) {
		return pelangganRepository.countByPerusahaanAndStatusAndKodeContaining(perusahaan, status, kode);
	}

	@Override
	public long countByNomorBuku(Perusahaan perusahaan, int nomorBuku) {
		return pelangganRepository.countByPerusahaanAndNomorBukuContaining(perusahaan, nomorBuku);
	}
	
	@Override
	public long countByNomorBuku(Perusahaan perusahaan, Status status, int nomorBuku) {
		return pelangganRepository.countByPerusahaanAndStatusAndNomorBukuContaining(perusahaan, status, nomorBuku);
	}
	
	@Override
	public long countByTunggakan(Perusahaan perusahaan, Status status, int tunggakan) {
		return pelangganRepository.countByPerusahaanAndStatusAndDetail_Tunggakan(perusahaan, status, tunggakan);
	}
	
	@Override
	public long countByTunggakanLessThan(Perusahaan perusahaan, Status status, int tunggakan) {
		return pelangganRepository.countByPerusahaanAndStatusAndDetail_TunggakanLessThan(perusahaan, status, tunggakan);
	}
	
	@Override
	public long countByTunggakanGreaterThan(Perusahaan perusahaan, Status status, int tunggakan) {
		return pelangganRepository.countByPerusahaanAndStatusAndDetail_TunggakanGreaterThan(perusahaan, status, tunggakan);
	}

	@Override
	public Pelanggan cetakKartu(Pelanggan pelanggan) {
		int tahun = DateUtil.getYearNow();
		List<Pembayaran> listPembayaran;
		try {
			listPembayaran = pembayaranService.get(pelanggan, tahun);
			PembayaranServiceImpl.Verifier.verifyListPembayaran(listPembayaran, tahun, pelanggan);
		} catch (EntityNotExistException e) {
			listPembayaran = new ArrayList<>();
		}
		pelanggan.setListPembayaran(listPembayaran);

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
		List<Pelanggan> listPelanggan = pelangganRepository.findByPerusahaanAndStatusAndKelurahanAndAlamat_LingkunganOrderByKodeAsc(perusahaan, Pelanggan.Status.AKTIF, kelurahan, lingkungan);

		CodeUtil.CodeGenerator codeGenerator = new CodeGenerator();
		String message = "";
		int numOfChange = 0;
		for (Pelanggan pelanggan : listPelanggan) {
			String generatedKode = codeGenerator.createKode(pelanggan);
			message = String.format("%sKode untuk %s: %s\n", message, pelanggan.getNama(), generatedKode);
			
			try {
				pelanggan.setKode(generatedKode);
				pelangganRepository.save(pelanggan);

				if (generatedKode.contains("W")) {
					codeGenerator.increase();
					numOfChange++;
				}
			} catch (Exception e) { }
		}
		
		message = String.format("%s \n\n Jumlah Perubahan : %d dari %d pelanggan.", message, numOfChange, listPelanggan.size());
		
		return message;
	}

	@Override
	public String resetKode(Perusahaan perusahaan) {
		List<Pelanggan> listPelanggan = pelangganRepository.findByPerusahaanAndStatusOrderByKodeAsc(perusahaan, Pelanggan.Status.AKTIF);

		CodeUtil.CodeGenerator codeGenerator = new CodeGenerator();
		String message = "";
		int numOfChange = 0;
		for (Pelanggan pelanggan : listPelanggan) {
			String generatedKode = codeGenerator.createKode(pelanggan);
			message = String.format("%sKode untuk %s: %s\n", message, pelanggan.getNama(), generatedKode);
			
			try {
				pelanggan.setKode(generatedKode);
				pelangganRepository.save(pelanggan);

				if (generatedKode.contains("W")) {
					codeGenerator.increase();
					numOfChange++;
				}
			} catch (Exception e) { }
		}
		
		message = String.format("%s \n\n Jumlah Perubahan : %d dari %d pelanggan.", message, numOfChange, listPelanggan.size());
		
		return message;
	}

	@Override
	public List<Pelanggan> getOrdered(Perusahaan perusahaan, Status status) {
		return pelangganRepository.findByPerusahaanAndStatusOrderByAlamat(perusahaan.getId(), Status.AKTIF);
	}
}
