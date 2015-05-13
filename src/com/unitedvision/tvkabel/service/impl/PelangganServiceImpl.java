package com.unitedvision.tvkabel.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unitedvision.tvkabel.entity.Alamat;
import com.unitedvision.tvkabel.entity.History;
import com.unitedvision.tvkabel.entity.Kelurahan;
import com.unitedvision.tvkabel.entity.Pegawai;
import com.unitedvision.tvkabel.entity.Pelanggan;
import com.unitedvision.tvkabel.entity.Pembayaran;
import com.unitedvision.tvkabel.entity.Perusahaan;
import com.unitedvision.tvkabel.entity.History.HistoryJumlah;
import com.unitedvision.tvkabel.entity.Pelanggan.Status;
import com.unitedvision.tvkabel.exception.ApplicationException;
import com.unitedvision.tvkabel.exception.DataDuplicationException;
import com.unitedvision.tvkabel.exception.EntityNotExistException;
import com.unitedvision.tvkabel.exception.StatusChangeException;
import com.unitedvision.tvkabel.repository.HistoryRepository;
import com.unitedvision.tvkabel.repository.PelangganRepository;
import com.unitedvision.tvkabel.repository.PerusahaanRepository;
import com.unitedvision.tvkabel.service.PelangganService;
import com.unitedvision.tvkabel.service.PembayaranService;
import com.unitedvision.tvkabel.util.CodeUtil;
import com.unitedvision.tvkabel.util.CodeUtil.CodeGenerator;
import com.unitedvision.tvkabel.util.DateUtil;

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
	private HistoryRepository historyRepository;

	@Override
	@Transactional(readOnly = false)
	public Pelanggan add(Pelanggan pelanggan) throws DataDuplicationException {
		pelanggan.setStatus(Status.AKTIF);
		
		return save(pelanggan);
	}

	@Override
	@Transactional(readOnly = false)
	public Pelanggan save(Pelanggan pelanggan) throws DataDuplicationException {
		if (pelanggan.isNew())
			pelanggan.countTunggakan(); // Secara otomatis atribut tanggalMulai digunakan sebagai tagihan awal(pertama)

		pelanggan = pelangganRepository.save(pelanggan);

		return pelanggan;
	}
	
	@Override
	@Transactional(readOnly = false)
	public void delete(Integer id) {
		pelangganRepository.delete(id);
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(Pelanggan pelanggan) {
		pelangganRepository.delete(pelanggan);
	}

	@Override
	@Transactional(readOnly = false)
	public Pelanggan activate(Pelanggan pelanggan, String keterangan) throws StatusChangeException, DataDuplicationException, EntityNotExistException {
		pelanggan.activate();
		
		pelangganRepository.save(pelanggan);

		createHistory(pelanggan, Status.AKTIF, keterangan);
		
		return pelanggan;
	}

	@Override
	@Transactional(readOnly = false)
	public Pelanggan activate(Integer id, String keterangan) throws ApplicationException {
		Pelanggan pelanggan = getOne(id);
		
		return activate(pelanggan, keterangan);
	}
	
	@Override
	@Transactional(readOnly = false)
	public Pelanggan passivate(Pelanggan pelanggan, String keterangan) throws StatusChangeException, DataDuplicationException, EntityNotExistException {
		pelanggan.passivate();
		
		pelangganRepository.save(pelanggan);

		createHistory(pelanggan, Status.BERHENTI, keterangan);
		
		return pelanggan;
	}

	@Override
	@Transactional(readOnly = false)
	public Pelanggan passivate(Integer id, String keterangan) throws ApplicationException {
		Pelanggan pelanggan = getOne(id);
		
		return passivate(pelanggan, keterangan);
	}
	
	@Override
	@Transactional(readOnly = false)
	public Pelanggan banned(Pelanggan pelanggan, String keterangan) throws StatusChangeException, DataDuplicationException, EntityNotExistException {
		Pembayaran last = pembayaranService.getLast(pelanggan);
		pelanggan.setPembayaranTerakhir(last);
		pelanggan.ban();

		pelangganRepository.save(pelanggan);

		createHistory(pelanggan, Status.PUTUS, keterangan);
		
		return pelanggan;
	}

	@Override
	@Transactional(readOnly = false)
	public Pelanggan banned(Integer id, String keterangan) throws ApplicationException {
		Pelanggan pelanggan = getOne(id);
		
		return banned(pelanggan, keterangan);
	}
	
	@Override
	@Transactional(readOnly = false)
	public Pelanggan free(Pelanggan pelanggan, String keterangan) throws ApplicationException {
		if (pelanggan.getStatus().equals(Status.GRATIS))
			throw new StatusChangeException("Tidak menggratiskan pelanggan. Karena pelanggan merupakan pelanggan gratis");

		pelanggan.setStatus(Status.GRATIS);

		pelangganRepository.save(pelanggan);
		createHistory(pelanggan, Status.GRATIS, keterangan);
		
		return pelanggan;
	}

	@Override
	@Transactional(readOnly = false)
	public Pelanggan free(Integer id, String keterangan) throws ApplicationException {
		Pelanggan pelanggan = getOne(id);
		
		return free(pelanggan, keterangan);
	}
	
	@Override
	@Transactional(readOnly = false)
	public Pelanggan remove(Pelanggan pelanggan) throws StatusChangeException {
		pelanggan.remove();

		return pelangganRepository.save(pelanggan);
	}
	
	@Override
	@Transactional(readOnly = false)
	public Pelanggan remove(Integer id) throws StatusChangeException, EntityNotExistException {
		Pelanggan pelanggan = getOne(id);
		
		return remove(pelanggan);
	}
	
	@Override
	@Transactional(readOnly = false)
	public Pelanggan updateLastPayment(Pelanggan pelanggan) {
		Pembayaran last = pembayaranService.getLast(pelanggan);

		pelanggan.setPembayaranTerakhir(last);
		
		return pelanggan;
	}
	
	@Override
	@Transactional(readOnly = false)
	public void updateTunggakan(Integer id, Integer tunggakan) throws DataDuplicationException, EntityNotExistException {
		Pelanggan pelanggan = getOne(id);
		pelanggan.setTunggakan(tunggakan);

		save(pelanggan);
	}
	
	@Transactional(readOnly = false)
	public History createHistory(Pelanggan pelanggan, Status status, String keterangan) throws EntityNotExistException {
		History history = new History();
		history.setPelanggan(pelanggan);
		history.setStatus(status);
		history.setTanggal(DateUtil.getNow());
		history.setKeterangan(keterangan);
		
		HistoryJumlah historyJumlah = new HistoryJumlah();
		historyJumlah.setJumlahAktif(pelangganRepository.countByPerusahaanAndStatus(pelanggan.getPerusahaan(), Status.AKTIF));
		historyJumlah.setJumlahPutus(pelangganRepository.countByPerusahaanAndStatus(pelanggan.getPerusahaan(), Status.PUTUS));
		historyJumlah.setJumlahBerhenti(pelangganRepository.countByPerusahaanAndStatus(pelanggan.getPerusahaan(), Status.BERHENTI));
		historyJumlah.setJumlahGratis(pelangganRepository.countByPerusahaanAndStatus(pelanggan.getPerusahaan(), Status.GRATIS));

		history.setHistoryJumlah(historyJumlah);
		
		return historyRepository.save(history);
	}
	
	@Override
	@Transactional(readOnly = false)
	public Pelanggan setMapLocation(Integer id, float latitude, float longitude) throws ApplicationException {
		Pelanggan pelanggan = getOne(id);
		
		return setMapLocation(pelanggan, latitude, longitude);
	}
	
	@Override
	@Transactional(readOnly = false)
	public Pelanggan setMapLocation(Pelanggan pelanggan, float latitude, float longitude) throws ApplicationException {
		Alamat alamat = pelanggan.getAlamat();
		alamat.getLokasi().setLatitude(latitude);;
		alamat.getLokasi().setLongitude(longitude);
		
		pelanggan.setAlamat(alamat);

		return save(pelanggan);
	}

	@Override
	@Transactional(readOnly = false)
	public void recountTunggakan() throws ApplicationException {
		for (int i = 1; i <= 31; i++) {
			recountTunggakan(Integer.toString(i));
		}
	}
	
	@Override
	@Transactional(readOnly = false)
	public void recountTunggakan(String tanggal) throws ApplicationException {
		tanggal = DateUtil.getDayString(tanggal);

		recountTunggakanStatusAktif(tanggal);
		recountTunggakanStatusGratis(tanggal);
	}
	
	@Transactional(readOnly = false)
	private void recountTunggakanStatusAktif(String tanggal) throws EntityNotExistException, DataDuplicationException {
		List<Pelanggan> listPelanggan = get(Status.AKTIF, tanggal);
		for (Pelanggan pelanggan : listPelanggan)
			recountTunggakan(pelanggan);
	}
	
	@Transactional(readOnly = false)
	private void recountTunggakanStatusGratis(String tanggal) throws ApplicationException {
		List<Pelanggan> listPelanggan = get(Status.GRATIS, tanggal);
		for (Pelanggan pelanggan : listPelanggan) {
			pembayaranService.pay(pelanggan);
			recountTunggakan(pelanggan);
		}
	}
	
	@Override
	@Transactional(readOnly = false)
	public Pelanggan recountTunggakan(Pelanggan pelanggan) throws DataDuplicationException {
		pelanggan.countTunggakan();

		return save(pelanggan);
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
	public List<Pelanggan> getByKode(Perusahaan perusahaan, String kode) throws EntityNotExistException {
		return pelangganRepository.findByPerusahaanAndKodeContainingOrderByKodeAsc(perusahaan, kode);
	}

	@Override
	public List<Pelanggan> getByNama(Perusahaan perusahaan, String nama) throws EntityNotExistException {
		return pelangganRepository.findByPerusahaanAndNamaContainingOrderByKodeAsc(perusahaan, nama);
	}
	
	@Override
	public List<Pelanggan> get(Status status, String tanggal) throws EntityNotExistException {
		return pelangganRepository.findByTanggalMulai(status.ordinal(), tanggal);
	}

	@Override
	public List<Pelanggan> get(Perusahaan perusahaan, Integer nomorBuku) throws EntityNotExistException {
		return pelangganRepository.findByPerusahaanAndNomorBukuContainingOrderByKodeAsc(perusahaan, nomorBuku);
	}
	
	@Override
	public List<Pelanggan> get(Perusahaan perusahaan, Integer nomorBuku, Status status) throws EntityNotExistException {
		return pelangganRepository.findByPerusahaanAndStatusAndNomorBukuContainingOrderByKodeAsc(perusahaan, status, nomorBuku);
	}
	
	@Override
	public List<Pelanggan> get(Perusahaan perusahaan, Status status) throws EntityNotExistException {
		return pelangganRepository.findByPerusahaanAndStatusOrderByKodeAsc(perusahaan, status);
	}

	@Override
	public List<Pelanggan> getByTunggakan(Perusahaan perusahaan, Status status, int tunggakan) throws EntityNotExistException {
		return pelangganRepository.findByPerusahaanAndStatusAndDetail_TunggakanOrderByKodeAsc(perusahaan, status, tunggakan);
	}
	
	@Override
	public List<Pelanggan> getByTunggakan(Perusahaan perusahaan, Status status, int tunggakanAwal, int tunggakanAkhir) throws EntityNotExistException {
		return pelangganRepository.findByPerusahaanAndStatusAndDetail_TunggakanBetweenOrderByKodeAsc(perusahaan, status, tunggakanAwal, tunggakanAkhir);
	}

	@Override
	public List<Pelanggan> getByNama(Perusahaan perusahaan, String nama, Status status) throws EntityNotExistException {
		return pelangganRepository.findByPerusahaanAndStatusAndNamaContainingOrderByKodeAsc(perusahaan, status, nama);
	}

	@Override
	public List<Pelanggan> getByKode(Perusahaan perusahaan, String kode, Status status) throws EntityNotExistException {
		return pelangganRepository.findByPerusahaanAndStatusAndKodeContainingOrderByKodeAsc(perusahaan, status, kode);
	}

	@Override
	public List<Pelanggan> get(Perusahaan perusahaan, Status status, Kelurahan kelurahan, int lingkungan) throws EntityNotExistException {
		return pelangganRepository.findByPerusahaanAndStatusAndAlamat_KelurahanAndAlamat_LingkunganOrderByKodeAsc(perusahaan, status, kelurahan, lingkungan);
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
	public long count(Perusahaan perusahaan, Status status) throws EntityNotExistException {
		return pelangganRepository.countByPerusahaanAndStatus(perusahaan, status);
	}

	@Override
	public long countByKode(Perusahaan perusahaan, String kode) throws EntityNotExistException {
		return pelangganRepository.countByPerusahaanAndKodeContaining(perusahaan, kode);
	}

	@Override
	public long countByNama(Perusahaan perusahaan, String nama) throws EntityNotExistException {
		return pelangganRepository.countByPerusahaanAndNamaContaining(perusahaan, nama);
	}
	
	@Override
	public long countByNama(Perusahaan perusahaan, String nama, Status status) throws EntityNotExistException {
		return pelangganRepository.countByPerusahaanAndStatusAndNamaContaining(perusahaan, status, nama);
	}

	@Override
	public long countByKode(Perusahaan perusahaan, String kode, Status status) throws EntityNotExistException {
		return pelangganRepository.countByPerusahaanAndStatusAndKodeContaining(perusahaan, status, kode);
	}

	@Override
	public long countByNomorBuku(Perusahaan perusahaan, Integer nomorBuku) throws EntityNotExistException {
		return pelangganRepository.countByPerusahaanAndNomorBukuContaining(perusahaan, nomorBuku);
	}
	
	@Override
	public long countByNomorBuku(Perusahaan perusahaan, Integer nomorBuku, Status status) throws EntityNotExistException {
		return pelangganRepository.countByPerusahaanAndStatusAndNomorBukuContaining(perusahaan, status, nomorBuku);
	}
	
	@Override
	public long countByTunggakan(Perusahaan perusahaan, Integer tunggakan, Status status) {
		return pelangganRepository.countByPerusahaanAndStatusAndDetail_Tunggakan(perusahaan, status, tunggakan);
	}
	
	@Override
	public long countByTunggakanLessThan(Perusahaan perusahaan, Integer tunggakan, Status status) {
		return pelangganRepository.countByPerusahaanAndStatusAndDetail_TunggakanLessThan(perusahaan, status, tunggakan);
	}
	
	@Override
	public long countByTunggakanGreaterThan(Perusahaan perusahaan, Integer tunggakan, Status status) {
		return pelangganRepository.countByPerusahaanAndStatusAndDetail_TunggakanGreaterThan(perusahaan, status, tunggakan);
	}

	@Override
	public List<Pelanggan> cetakKartu(List<Pelanggan> listPelanggan) {
		for (Pelanggan pelanggan : listPelanggan) {
			cetakKartu(pelanggan);
		}

		return listPelanggan;
	}

	@Override
	public Pelanggan cetakKartu(Pelanggan pelanggan) {
		int tahun = DateUtil.getYearNow();
		
		return cetakKartu(pelanggan, tahun);
	}
	
	@Override
	public Pelanggan cetakKartu(Pelanggan pelanggan, Integer tahun) {
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
	public String resetKode(Perusahaan perusahaan, Kelurahan kelurahan, Integer lingkungan) throws EntityNotExistException {
		List<Pelanggan> listPelanggan = pelangganRepository.findByPerusahaanAndStatusAndAlamat_KelurahanAndAlamat_LingkunganOrderByKodeAsc(perusahaan, Pelanggan.Status.AKTIF, kelurahan, lingkungan);

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
	public String resetKode(Perusahaan perusahaan) throws EntityNotExistException {
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
