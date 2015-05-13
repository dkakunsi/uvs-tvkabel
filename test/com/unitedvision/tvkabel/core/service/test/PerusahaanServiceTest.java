package com.unitedvision.tvkabel.core.service.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.unitedvision.tvkabel.configuration.ApplicationConfig;
import com.unitedvision.tvkabel.entity.Alamat;
import com.unitedvision.tvkabel.entity.Kecamatan;
import com.unitedvision.tvkabel.entity.Kelurahan;
import com.unitedvision.tvkabel.entity.Kontak;
import com.unitedvision.tvkabel.entity.Kota;
import com.unitedvision.tvkabel.entity.Perusahaan;
import com.unitedvision.tvkabel.exception.ApplicationException;
import com.unitedvision.tvkabel.exception.EmptyCodeException;
import com.unitedvision.tvkabel.exception.EmptyIdException;
import com.unitedvision.tvkabel.exception.EntityNotExistException;
import com.unitedvision.tvkabel.repository.KecamatanRepository;
import com.unitedvision.tvkabel.repository.KelurahanRepository;
import com.unitedvision.tvkabel.repository.KotaRepository;
import com.unitedvision.tvkabel.repository.PegawaiRepository;
import com.unitedvision.tvkabel.repository.PelangganRepository;
import com.unitedvision.tvkabel.service.KelurahanService;
import com.unitedvision.tvkabel.service.PerusahaanService;

@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration (classes = {ApplicationConfig.class})
@Transactional
@TransactionConfiguration (defaultRollback = true)
public class PerusahaanServiceTest {
	
	@Autowired
	private PerusahaanService perusahaanService;
	@Autowired
	private KelurahanService kelurahanService;

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
	private int idKelurahan;

	@Before
	public void setup() throws ApplicationException {
		Kota kota = new Kota();
		kota.setNama("Manado");
		
		kotaRepository.save(kota);
		
		Kecamatan kecamatan = new Kecamatan();
		kecamatan.setKota(kota);
		kecamatan.setNama("Mapanget");
		
		kecamatanRepository.save(kecamatan);
		
		Kelurahan kelurahan = new Kelurahan();
		kelurahan.setKecamatan(kecamatan);
		kelurahan.setNama("Paniki Bawah");
		
		kelurahanRepository.save(kelurahan);
		idKelurahan = kelurahan.getId();
		
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
	}
	
	@Test
	public void testSaveWithExsistedEmail() throws EntityNotExistException, EmptyIdException, EmptyCodeException {
		Kelurahan kelurahan = kelurahanService.getOne(idKelurahan);

		Perusahaan perusahaan = new Perusahaan();
		perusahaan.setNama("Test");
		perusahaan.setKode("DEFAULT");
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
		
		perusahaan.setStatus(Perusahaan.Status.AKTIF);
		
		try {
			perusahaanService.regist(perusahaan);
		} catch (Exception ex) {
			assertEquals("Email yang anda masukkan sudah digunakan.", ex.getMessage());
		}
	}
}
