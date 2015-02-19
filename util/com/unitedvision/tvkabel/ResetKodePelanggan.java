package com.unitedvision.tvkabel;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.unitedvision.tvkabel.core.service.KelurahanService;
import com.unitedvision.tvkabel.core.service.PelangganService;
import com.unitedvision.tvkabel.core.service.PerusahaanService;
import com.unitedvision.tvkabel.exception.EntityNotExistException;
import com.unitedvision.tvkabel.persistence.SpringDataJpaConfig;
import com.unitedvision.tvkabel.persistence.entity.Kelurahan;
import com.unitedvision.tvkabel.persistence.entity.Perusahaan;
import com.unitedvision.tvkabel.util.CodeUtil;

public class ResetKodePelanggan {
	private static ApplicationContext appContext = new AnnotationConfigApplicationContext(SpringDataJpaConfig.class);
	private static PerusahaanService perusahaanService = appContext.getBean(PerusahaanService.class);
	private static KelurahanService kelurahanService = appContext.getBean(KelurahanService.class);
	private static PelangganService pelangganService = appContext.getBean(PelangganService.class);
	
	public static void main(String[] args) {
		String kode = "";
		int idPerusahaan = 0;
		int idKelurahan = 0;
		int lingkungan = 0;
		
		resetKode(kode, idPerusahaan, idKelurahan, lingkungan);
	}

	public static void resetKode(String kode, Integer idPerusahaan, Integer idKelurahan, Integer lingkungan) {
		String message;

		try {
			Perusahaan perusahaan = perusahaanService.getOne(idPerusahaan);
			Kelurahan kelurahan = kelurahanService.getOne(idKelurahan);
			
			if (!kode.equals(CodeUtil.getKode())) {
				message = "Gagal! Anda tidak memiliki otoritas!";
			} else {
				message = pelangganService.resetKode(perusahaan, kelurahan, lingkungan);
			}
			
			System.out.println(message);
		} catch (EntityNotExistException e) {
			e.printStackTrace();
		}
	}


}
