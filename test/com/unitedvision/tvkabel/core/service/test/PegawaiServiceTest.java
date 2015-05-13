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
import com.unitedvision.tvkabel.entity.Pegawai;
import com.unitedvision.tvkabel.entity.Perusahaan;
import com.unitedvision.tvkabel.entity.Pegawai.Kredensi;
import com.unitedvision.tvkabel.entity.Pegawai.Role;
import com.unitedvision.tvkabel.exception.ApplicationException;
import com.unitedvision.tvkabel.exception.EmptyCodeException;
import com.unitedvision.tvkabel.exception.EmptyIdException;
import com.unitedvision.tvkabel.exception.EntityNotExistException;
import com.unitedvision.tvkabel.repository.KecamatanRepository;
import com.unitedvision.tvkabel.repository.KelurahanRepository;
import com.unitedvision.tvkabel.repository.KotaRepository;
import com.unitedvision.tvkabel.repository.PerusahaanRepository;
import com.unitedvision.tvkabel.service.PegawaiService;
import com.unitedvision.tvkabel.service.PerusahaanService;

@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration (classes = {ApplicationConfig.class})
@Transactional
@TransactionConfiguration (defaultRollback = true)
public class PegawaiServiceTest {
	
	@Autowired
	private PegawaiService pegawaiService;
	@Autowired
	private PerusahaanService perusahaanService;
	
	@Autowired
	private PerusahaanRepository perusahaanRepository;
	@Autowired
	private KotaRepository kotaRepository;
	@Autowired
	private KecamatanRepository kecamatanRepository;
	@Autowired
	private KelurahanRepository kelurahanRepository;

	private Perusahaan perusahaan;
	private Pegawai pegawai;
	
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
		
		perusahaan = new Perusahaan();
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
		
		perusahaanRepository.save(perusahaan);
		
		pegawai = new Pegawai();
		pegawai.setId(0);
		pegawai.setKode("01001");
		pegawai.setKredensi(new Kredensi("admin", "admin", Role.OPERATOR));
		pegawai.setNama("ADMIN");
		pegawai.setPerusahaan(perusahaan);
		pegawai.setStatus(Pegawai.Status.AKTIF);

		pegawaiService.add(pegawai);
	}
	
	@Test
	public void testSaveWithExsistedUsername() throws EntityNotExistException, EmptyIdException, EmptyCodeException {
		Pegawai pegawai = new Pegawai();
		pegawai.setId(0);
		pegawai.setKode("TEST");
		pegawai.setKredensi(new Kredensi("admin", "admin", Role.OPERATOR));
		pegawai.setNama("Test");
		pegawai.setPerusahaan(perusahaan);
		pegawai.setStatus(Pegawai.Status.AKTIF);
		
		try {
			pegawaiService.save(pegawai);
		} catch (Exception e) {
			assertEquals("Username yang anda masukkan sudah digunakan.", e.getMessage());
		}
	}

}
