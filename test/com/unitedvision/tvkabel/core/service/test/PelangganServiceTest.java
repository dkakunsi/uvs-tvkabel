package com.unitedvision.tvkabel.core.service.test;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.unitedvision.tvkabel.configuration.ApplicationConfig;
import com.unitedvision.tvkabel.entity.Alamat;
import com.unitedvision.tvkabel.entity.History;
import com.unitedvision.tvkabel.entity.Kecamatan;
import com.unitedvision.tvkabel.entity.Kelurahan;
import com.unitedvision.tvkabel.entity.Kontak;
import com.unitedvision.tvkabel.entity.Kota;
import com.unitedvision.tvkabel.entity.Pegawai;
import com.unitedvision.tvkabel.entity.Pelanggan;
import com.unitedvision.tvkabel.entity.Pembayaran;
import com.unitedvision.tvkabel.entity.Perusahaan;
import com.unitedvision.tvkabel.entity.Pelanggan.Detail;
import com.unitedvision.tvkabel.entity.Pelanggan.Status;
import com.unitedvision.tvkabel.exception.ApplicationException;
import com.unitedvision.tvkabel.exception.EntityNotExistException;
import com.unitedvision.tvkabel.exception.StatusChangeException;
import com.unitedvision.tvkabel.repository.KecamatanRepository;
import com.unitedvision.tvkabel.repository.KelurahanRepository;
import com.unitedvision.tvkabel.repository.KotaRepository;
import com.unitedvision.tvkabel.repository.PegawaiRepository;
import com.unitedvision.tvkabel.repository.PelangganRepository;
import com.unitedvision.tvkabel.service.HistoryService;
import com.unitedvision.tvkabel.service.PelangganService;
import com.unitedvision.tvkabel.service.PembayaranService;
import com.unitedvision.tvkabel.service.PerusahaanService;
import com.unitedvision.tvkabel.util.DateUtil;

@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration (classes = {ApplicationConfig.class})
@Transactional
@TransactionConfiguration (defaultRollback = true)
public class PelangganServiceTest {
	@Autowired
	private PelangganService pelangganService;
	@Autowired
	private PerusahaanService perusahaanService;
	@Autowired
	private PembayaranService pembayaranService;
	@Autowired
	private HistoryService historyService;

	@Autowired
	private PelangganRepository pelangganRepository;
	@Autowired
	private PegawaiRepository pegawaiRepository;
	@Autowired
	private KelurahanRepository kelurahanRepository;
	@Autowired
	private KotaRepository kotaRepository;
	@Autowired
	private KecamatanRepository kecamatanRepository;
	
	private Perusahaan perusahaan;
	private Kelurahan kelurahan;
	private Pelanggan pelanggan;
	
	@Before
	public void setup() throws ApplicationException {
		Kota kota = new Kota();
		kota.setNama("Manado");
		
		kotaRepository.save(kota);
		
		Kecamatan kecamatan = new Kecamatan();
		kecamatan.setKota(kota);
		kecamatan.setNama("Mapanget");
		
		kecamatanRepository.save(kecamatan);
		
		kelurahan = new Kelurahan();
		kelurahan.setKecamatan(kecamatan);
		kelurahan.setNama("Paniki Bawah");
		
		kelurahanRepository.save(kelurahan);
		
		perusahaan = new Perusahaan();
		perusahaan.setKode("DEFAULT");
		perusahaan.setNama("TVK. Global Vision");
		perusahaan.setNamaPT("PT. Aspetika Manasa SULUT");
		perusahaan.setStatus(Perusahaan.Status.AKTIF);
		
		Alamat alamat = new Alamat();
		alamat.setKelurahan(kelurahan);
		alamat.setLingkungan(1);
		perusahaan.setAlamat(alamat);
		
		Kontak kontak = new Kontak();
		kontak.setEmail("admin@globalvision.com");
		kontak.setHp("082323787878");
		perusahaan.setKontak(kontak);
		
		perusahaan.generateKode(0);
		
		perusahaanService.regist(perusahaan);
		
        alamat = new Alamat();
        alamat.setKelurahan(kelurahan);
        alamat.setLingkungan(1);
        
        kontak = new Kontak("823586", "081377653421", "email@gmail.com");
		Detail detail = new Detail(DateUtil.getNow(), 1, 50000, 0);

		pelanggan = new Pelanggan();
		pelanggan.setKode("0101001");
		pelanggan.setPerusahaan(perusahaan);
		pelanggan.setNama("Kel. Pelanggan");
		pelanggan.setNomorBuku("1");
		pelanggan.setAlamat(alamat);
		pelanggan.setKontak(kontak);
		pelanggan.setDetail(detail);
		
		pelangganService.add(pelanggan);
	}
	
	@Test
	public void insertPelanggan_Success() throws ApplicationException {
        Alamat alamat = new Alamat();
        alamat.setKelurahan(kelurahan);
        alamat.setLingkungan(1);
        
        Kontak kontak = new Kontak("823586", "081377653421", "email@gmail.com");
		Detail detail = new Detail(DateUtil.getNow(), 1, 50000, 0);

		Pelanggan pelanggan = new Pelanggan();
		pelanggan.setKode("0101002");
		pelanggan.setNama("Kel. Pelanggan 2");
		pelanggan.setNomorBuku("2");
		pelanggan.setAlamat(alamat);
		pelanggan.setKontak(kontak);
		pelanggan.setDetail(detail);
		
		pelangganService.add(pelanggan);

		Date date = pelanggan.getTanggalMulai();
		Date now = DateUtil.getSimpleNow();
		assertEquals(DateUtil.getYear(now), DateUtil.getYear(date));
		assertEquals(DateUtil.getMonth(now), DateUtil.getMonth(date));
		assertEquals(DateUtil.getDay(now), DateUtil.getDay(date));
	}

	@Test
	public void insertPelanggan_KodeException() throws ApplicationException {
        Alamat alamat = new Alamat();
        alamat.setKelurahan(kelurahan);
        alamat.setLingkungan(1);
        
        Kontak kontak = new Kontak("823586", "081377653421", "email@gmail.com");
		Detail detail = new Detail(DateUtil.getNow(), 1, 50000, 0);

		Pelanggan pelanggan = new Pelanggan();
		pelanggan.setKode("0101001");
		pelanggan.setNama("Kel. Pelanggan 2");
		pelanggan.setNomorBuku("2");
		pelanggan.setAlamat(alamat);
		pelanggan.setKontak(kontak);
		pelanggan.setDetail(detail);
		
		pelangganService.add(pelanggan);

		try {
			pelangganService.save(pelanggan);
		} catch (ApplicationException e) {
			String message = e.getMessage();
			assertEquals("Kode yang anda masukkan sudah digunakan.", message);
		}
	}

	@Test
	public void insertPelanggan_NomorBukuException() throws ApplicationException {
        Alamat alamat = new Alamat();
        alamat.setKelurahan(kelurahan);
        alamat.setLingkungan(1);
        
        Kontak kontak = new Kontak("823586", "081377653421", "email@gmail.com");
		Detail detail = new Detail(DateUtil.getNow(), 1, 50000, 0);

		Pelanggan pelanggan = new Pelanggan();
		pelanggan.setKode("0101002");
		pelanggan.setNama("Kel. Pelanggan 2");
		pelanggan.setNomorBuku("1");
		pelanggan.setAlamat(alamat);
		pelanggan.setKontak(kontak);
		pelanggan.setDetail(detail);
		
		pelangganService.add(pelanggan);

		try {
			pelangganService.save(pelanggan);
		} catch (ApplicationException e) {
			String message = e.getMessage();
			assertEquals("Nomor Buku yang anda masukkan sudah digunakan.", message);
		}
	}

	@Test
	public void insertPelanggan_NamaException() throws ApplicationException {
        Alamat alamat = new Alamat();
        alamat.setKelurahan(kelurahan);
        alamat.setLingkungan(1);
        
        Kontak kontak = new Kontak("823586", "081377653421", "email@gmail.com");
		Detail detail = new Detail(DateUtil.getNow(), 1, 50000, 0);

		Pelanggan pelanggan = new Pelanggan();
		pelanggan.setKode("0101002");
		pelanggan.setNama("Kel. Pelanggan");
		pelanggan.setNomorBuku("2");
		pelanggan.setAlamat(alamat);
		pelanggan.setKontak(kontak);
		pelanggan.setDetail(detail);
		
		pelangganService.add(pelanggan);

		try {
			pelangganService.save(pelanggan);
		} catch (ApplicationException e) {
			String message = e.getMessage();
			assertEquals("Nama yang anda masukkan sudah digunakan.", message);
		}
	}

	@Test
	public void bannedWorks() throws ApplicationException {
		pelanggan.setStatus(Status.AKTIF);
		
		String keterangan = "Menunggak";
		pelangganService.banned(pelanggan, keterangan);

		Assert.assertEquals(Status.PUTUS, pelanggan.getStatus());
		Assert.assertEquals(0, pelanggan.getTunggakan());
		
		List<History> listHistory = historyService.get(pelanggan);
		
		assertNotEquals(0, listHistory.size());
		assertEquals(keterangan, listHistory.get(0).getKeterangan());
	}
	
	@Test(expected = StatusChangeException.class)
	public void banBannedPelanggan() throws ApplicationException {
		pelanggan.setStatus(Status.AKTIF);
		
		pelangganService.banned(pelanggan, "Menunggakan");

		Assert.assertEquals(Status.PUTUS, pelanggan.getStatus());
		Assert.assertEquals(0, pelanggan.getTunggakan());

		//REBEND PROCESS
		pelangganService.banned(pelanggan, "BANNED");
	}
	
	@Test
	public void passivateWorks() throws ApplicationException {
		pelanggan.setStatus(Status.AKTIF);
		
		String keterangan = "Pindah Rumah";
		
		pelangganService.passivate(pelanggan, keterangan);

		Assert.assertEquals(Status.BERHENTI, pelanggan.getStatus());
		Assert.assertEquals(0, pelanggan.getTunggakan());
		
		List<History> listHistory = historyService.get(pelanggan);
		
		assertNotEquals(0, listHistory.size());
		assertEquals(keterangan, listHistory.get(0).getKeterangan());
	}

	@Test(expected = StatusChangeException.class)
	public void passivatePassivePelanggan() throws ApplicationException {
		pelanggan.setStatus(Status.AKTIF);
		
		pelangganService.passivate(pelanggan, "Pindah Rumah");

		Assert.assertEquals(Status.BERHENTI, pelanggan.getStatus());
		Assert.assertEquals(0, pelanggan.getTunggakan());

		//REPASSIVATE PROCESS
		pelangganService.passivate(pelanggan, "PASSIVATED");
	}

	@Test
	public void activateWorks() throws ApplicationException {
		pelanggan.setStatus(Status.BERHENTI);
		
		String keterangan = "Gabung Lagi";
		
		pelangganService.activate(pelanggan, keterangan);

		Assert.assertEquals(Status.AKTIF, pelanggan.getStatus());
		Assert.assertEquals(0, pelanggan.getTunggakan());
		Assert.assertEquals(DateUtil.getSimpleNow().getTime(), DateUtil.getSimpleDate(pelanggan.getTanggalMulai()).getTime());
		
		List<History> listHistory = historyService.get(pelanggan);
		
		assertNotEquals(0, listHistory.size());
		assertEquals(keterangan, listHistory.get(0).getKeterangan());
	}
	
	@Test(expected = StatusChangeException.class)
	public void activateActivePelanggan() throws ApplicationException {
		pelanggan.setStatus(Status.BERHENTI);
		
		pelangganService.activate(pelanggan, "Gabung Lagi");

		Assert.assertEquals(Status.AKTIF, pelanggan.getStatus());
		Assert.assertEquals(0, pelanggan.getTunggakan());
		Assert.assertEquals(DateUtil.getSimpleNow().getTime(), DateUtil.getSimpleDate(pelanggan.getTanggalMulai()).getTime());

		//REACTIVATE PROCESS
		pelanggan.setStatus(Status.AKTIF);
		
		pelangganService.activate(pelanggan, "BANNED");
	}
	
	@Test
	public void testGetByPerusahaanAndKodeAndStatus() throws ApplicationException {
		assertNotEquals(0, pelangganRepository.count());

		String kode = "0101001";
		Status status = Status.AKTIF;

		assertEquals(kode, pelanggan.getKode());
		assertEquals(status, pelanggan.getStatus());
		
		List<Pelanggan> list = pelangganService.getByKode(perusahaan, kode, status);
		
		assertNotEquals(0, list.size());
	}

	@Test (expected = EntityNotExistException.class)
	public void testGetOne() throws EntityNotExistException {
		Pelanggan pelanggan = pelangganService.getOne(0);
		
		assertNotNull(pelanggan);
	}
	
	@Test (expected = EntityNotExistException.class)
	public void testGet() throws ApplicationException {
		List<Pelanggan> listPelanggan = pelangganService.get(perusahaan, Status.REMOVED);
		
		assertNotNull(listPelanggan);
		assertNotEquals(0, listPelanggan.size());
	}
	
	@Test
	public void testGetByTanggal_Like() throws ApplicationException {
		String tanggal = "13";
		tanggal = DateUtil.getDayString(tanggal);
		
		List<Pelanggan> listPelanggan = pelangganService.get(Status.AKTIF, tanggal);
		
		assertNotNull(listPelanggan);
		assertNotEquals(0, listPelanggan.size());
		assertEquals(1, listPelanggan.size());
	}

	@Test
	@Ignore
	public void recountTunggakanWorks() throws ApplicationException {
		pelangganService.recountTunggakan();
		
		Pelanggan pelanggan35 = pelangganService.getOne(35); //Pelanggan Aktif
		Assert.assertEquals(3, pelanggan35.getTunggakan());
		
		Pelanggan pelanggan842 = pelangganService.getOne(842); //Pelanggan Removed
		Assert.assertEquals(9, pelanggan842.getTunggakan());

		Pelanggan pelanggan793 = pelangganService.getOne(793); //Pelanggan Putus
		Assert.assertEquals(4, pelanggan793.getTunggakan());
	}
	
	@Test
	@Ignore
	public void testRecountTunggakan() throws ApplicationException {
		Pelanggan pelanggan = pelangganService.getOne(35);
		
		pelangganService.recountTunggakan(pelanggan);
		
		Pelanggan pelangganUpdated = pelangganService.getOne(35);
		
		assertEquals(0, pelangganUpdated.getTunggakan());
	}
	
	@Test
	@Ignore
	public void testRecountTunggakan_WithTanggal() throws ApplicationException {
		String tanggal = "13";
		
		pelangganService.recountTunggakan(tanggal);

		Pegawai pegawai = pegawaiRepository.getOne(14);
		Date now = DateUtil.getNow();
		List<Pembayaran> listPembayaran = pembayaranService.get(pegawai, now, now);
		
		assertNotNull(listPembayaran);
		assertEquals(0, listPembayaran.size());
	}
}
