package com.unitedvision.tvkabel.interceptor;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import com.unitedvision.tvkabel.entity.Alat;
import com.unitedvision.tvkabel.entity.Kecamatan;
import com.unitedvision.tvkabel.entity.Kelurahan;
import com.unitedvision.tvkabel.entity.Kota;
import com.unitedvision.tvkabel.entity.Pegawai;
import com.unitedvision.tvkabel.entity.Pelanggan;
import com.unitedvision.tvkabel.entity.Pembayaran;
import com.unitedvision.tvkabel.entity.Perusahaan;
import com.unitedvision.tvkabel.exception.ValidationException;

@Aspect
@Component
public class Validation {
	
	@Before(
		value = "execution(public * com.unitedvision.tvkabel.service.impl.KotaServiceImpl.*(com.unitedvision.tvkabel.entity.Kota) throws com.unitedvision.tvkabel.exception.ApplicationException) && args(kota)",
		argNames = "kota")
	public void validate(Kota kota) throws ValidationException {
		if (kota == null)
			throw new ValidationException("Silahkan pilih kota");
		
		if (kota.getNama() == null || kota.getNama() == "")
			throw new ValidationException("Silahkan masukan nama kota");
		
	}
	
	@Before(
		value = "execution(public * com.unitedvision.tvkabel.service.impl.KecamatanServiceImpl.*(com.unitedvision.tvkabel.entity.Kecamatan) throws com.unitedvision.tvkabel.exception.ApplicationException) && args(kecamatan)",
		argNames = "kecamatan")
	public void validate(Kecamatan kecamatan) throws ValidationException {
		if (kecamatan == null)
			throw new ValidationException("Silahkan pilih kota");
		
		if (kecamatan.getNama() == null || kecamatan.getNama() == "")
			throw new ValidationException("Silahkan masukan nama kecamatan");
		
		if (kecamatan.getKota() == null)
			throw new ValidationException("Silahkan tentukan kota untuk kecamatan");
		
	}
	
	@Before(
		value = "execution(public * com.unitedvision.tvkabel.service.impl.KelurahanServiceImpl.*(com.unitedvision.tvkabel.entity.Kelurahan) throws com.unitedvision.tvkabel.exception.ApplicationException) && args(kelurahan)",
		argNames = "kelurahan")
	public void validate(Kelurahan kelurahan) throws ValidationException {
		if (kelurahan == null)
			throw new ValidationException("Silahkan pilih kota");
		
		if (kelurahan.getNama() == null || kelurahan.getNama() == "")
			throw new ValidationException("Silahkan masukan nama kota");
		
		if (kelurahan.getKecamatan() == null)
			throw new ValidationException("Silahkan tentukan kecamatan untuk kelurahan");
	}
	
	@Before(
		value = "execution(public * com.unitedvision.tvkabel.service.impl.PerusahaanServiceImpl.*(com.unitedvision.tvkabel.entity.Perusahaan) throws com.unitedvision.tvkabel.exception.ApplicationException) && args(perusahaan)",
		argNames = "perusahaan")
	public void validate(Perusahaan perusahaan) throws ValidationException {
		if (perusahaan == null)
			throw new ValidationException("Silahkan pilih perusahaan");
		
		if (perusahaan.getKode() == null || perusahaan.getKode().equals(""))
			throw new ValidationException("Silahkan masukan kode perusahaan");
		
		if (perusahaan.getNama() == null || perusahaan.getNama().equals("") )
			throw new ValidationException("Silahkan masukan nama perusahaan");
		
		if (perusahaan.getLingkungan() == 0)
			throw new ValidationException("Silahkan masukan lingkungan");
	
		validate(perusahaan.getKelurahan());
		
	}
	
	@Before(
		value = "execution(public * com.unitedvision.tvkabel.service.impl.PelangganServiceImpl.*(com.unitedvision.tvkabel.entity.Pelanggan) throws com.unitedvision.tvkabel.exception.ApplicationException) && args(pelanggan)",
		argNames = "pelanggan")
	public void validate(Pelanggan pelanggan) throws ValidationException {
		if (pelanggan == null)
			throw new ValidationException("Silahkan pilih pelanggan");
		
		if (pelanggan.getKode() == null || pelanggan.getKode().equals(""))
			throw new ValidationException("Silahkan masukan kode pelanggan");
		
		if (pelanggan.getNama() == null || pelanggan.getNama().equals(""))
			throw new ValidationException("Silahkan masukan nama pelanggan");
		
		if (pelanggan.getLingkungan() == 0)
			throw new ValidationException("Silahkan masukan lingkungan");
	
		validate(pelanggan.getPerusahaan());
		validate(pelanggan.getKelurahan());
		
	}
	
	@Before(
		value = "execution(public * com.unitedvision.tvkabel.service.impl.PegawaiServiceImpl.*(com.unitedvision.tvkabel.entity.Pegawai) throws com.unitedvision.tvkabel.exception.ApplicationException) && args(pegawai)",
		argNames = "pegawai")
	public void validate(Pegawai pegawai) throws ValidationException {
		if (pegawai == null)
			throw new ValidationException("Silahkan pilih pegawai");
		
		if (pegawai.getKode() == null || pegawai.getKode().equals(""))
			throw new ValidationException("Silahkan masukan kode pegawai");
		
		if (pegawai.getNama() == null || pegawai.getNama().equals(""))
			throw new ValidationException("Silahkan masukan nama pegawai");
	
		validate(pegawai.getPerusahaan());
		
	}

	@Before(
		value = "execution(public * com.unitedvision.tvkabel.service.impl.PembayaranServiceImpl.*(com.unitedvision.tvkabel.entity.Pembayaran) throws com.unitedvision.tvkabel.exception.ApplicationException) && args(pembayaran)",
		argNames = "pembayaran")
	public void validate(Pembayaran pembayaran) throws ValidationException {
		if (pembayaran == null)
			throw new ValidationException("Silahkan pilih pembayaran");
		
		if (pembayaran.getKode() == null || pembayaran.getKode().equals(""))
			throw new ValidationException("Silahkan masukan kode pembayaran");
		
	
		validate(pembayaran.getPegawai());
		validate(pembayaran.getPelanggan());
		
	}

	@Before(
		value = "execution(public * com.unitedvision.tvkabel.service.impl.AlatServiceImpl.*(com.unitedvision.tvkabel.entity.Alat) throws com.unitedvision.tvkabel.exception.ApplicationException) && args(alat)",
		argNames = "alat")
	public void validate(Alat alat) throws ValidationException {
		if (alat == null)
			throw new ValidationException("Silahkan pilih alat");
		
		if (alat.getKode() == null || alat.getKode().equals(""))
			throw new ValidationException("Silahkan masukan kode alat");
		
		if (alat.getLingkungan() == 0)
			throw new ValidationException("Silahkan masukan lingkungan");
		
		validate(alat.getPerusahaan());
		validate(alat.getKelurahan());
		
	}

}
