package com.unitedvision.tvkabel;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.unitedvision.tvkabel.core.service.PelangganService;
import com.unitedvision.tvkabel.core.service.PembayaranService;
import com.unitedvision.tvkabel.core.service.PerusahaanService;
import com.unitedvision.tvkabel.exception.EntityNotExistException;
import com.unitedvision.tvkabel.persistence.SpringDataJpaConfig;
import com.unitedvision.tvkabel.persistence.entity.Pelanggan;
import com.unitedvision.tvkabel.persistence.entity.Pelanggan.Status;
import com.unitedvision.tvkabel.persistence.entity.Pembayaran;
import com.unitedvision.tvkabel.persistence.entity.Perusahaan;
import com.unitedvision.tvkabel.persistence.repository.PelangganRepository;

public class ResetPembayaranTerakhirPelanggan {
	private static ApplicationContext appContext = new AnnotationConfigApplicationContext(SpringDataJpaConfig.class);
	private static PelangganService pelangganService = appContext.getBean(PelangganService.class);
	private static PembayaranService pembayaranService = appContext.getBean(PembayaranService.class);
	private static PerusahaanService perusahaanService = appContext.getBean(PerusahaanService.class);

	private static PelangganRepository pelangganRepository = appContext.getBean(PelangganRepository.class);

	public static void main(String[] args) {
		try {
			Perusahaan perusahaan = perusahaanService.getOne(17);
		
			resetPembayaranTerakhir(perusahaan, Status.AKTIF);
			resetPembayaranTerakhir(perusahaan, Status.GRATIS);
			resetPembayaranTerakhir(perusahaan, Status.BERHENTI);
			resetPembayaranTerakhir(perusahaan, Status.PUTUS);
		} catch (EntityNotExistException e) {
			e.printStackTrace();
		}
	}

	public static void resetPembayaranTerakhir(Perusahaan perusahaan, Status status) throws EntityNotExistException {
		List<Pelanggan> listPelanggan = pelangganService.get(perusahaan, status);
		List<Pelanggan> toSave = new ArrayList<>();
		
		System.out.println("Ready to update...");
		for (Pelanggan pelanggan : listPelanggan) {
			System.out.println(String.format("Retrieving last payment for %s", pelanggan.getNama()));
			Pembayaran last = pembayaranService.getLast(pelanggan);
			
			if (last != null) {
				System.out.println(String.format("Updating last payment for %s", pelanggan.getNama()));
				pelanggan.setPembayaranTerakhir(last);
				toSave.add(pelanggan);
			} else {
				System.out.println(String.format("Excluding %s from list", pelanggan.getNama()));
			}
		}
		
		pelangganRepository.save(toSave);
		
		System.out.println(String.format("Daftar Perubahan : %d pelanggan", toSave.size()));
		for (Pelanggan pelanggan : toSave) {
			System.out.println(pelanggan.getNama());
		}
	}
}
