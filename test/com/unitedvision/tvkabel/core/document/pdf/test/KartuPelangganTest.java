package com.unitedvision.tvkabel.core.document.pdf.test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.Month;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfWriter;
import com.unitedvision.tvkabel.core.document.pdf.KartuPelangganPdfView;
import com.unitedvision.tvkabel.core.service.PelangganService;
import com.unitedvision.tvkabel.exception.EmptyCodeException;
import com.unitedvision.tvkabel.exception.EmptyIdException;
import com.unitedvision.tvkabel.exception.EntityNotExistException;
import com.unitedvision.tvkabel.persistence.SpringDataJpaConfig;
import com.unitedvision.tvkabel.persistence.entity.Alamat;
import com.unitedvision.tvkabel.persistence.entity.Kelurahan;
import com.unitedvision.tvkabel.persistence.entity.Kontak;
import com.unitedvision.tvkabel.persistence.entity.Pegawai;
import com.unitedvision.tvkabel.persistence.entity.Pelanggan;
import com.unitedvision.tvkabel.persistence.entity.Pelanggan.Detail;
import com.unitedvision.tvkabel.persistence.entity.Pembayaran;
import com.unitedvision.tvkabel.persistence.entity.Pembayaran.Tagihan;
import com.unitedvision.tvkabel.persistence.entity.Perusahaan;
import com.unitedvision.tvkabel.util.DateUtil;

public class KartuPelangganTest extends KartuPelangganPdfView {
	private static KartuPelangganTest kartu = new KartuPelangganTest();

	public static void main(String[] args) {
        Document document = kartu.newDocument();
        //create(document);
        try {
			createWithDb(document);
		} catch (EntityNotExistException e) {
			e.printStackTrace();
		}
        System.out.println("DONE...");
	}
	
	@SuppressWarnings("unused")
	private static void create(Document document) {
        try {
            PdfWriter.getInstance(document,
                new FileOutputStream("E:\\test.pdf"));

            document.open();
            Kelurahan kelurahan = new Kelurahan(0, null, "Paniki Bawah");
            Alamat alamat = new Alamat(1, "Rumah Saya", 0, 0);
            Kontak kontak = new Kontak("", "082347643198", "dkakunsi@gmail.com");
            Detail detail = new Detail(new Date(), 1, 50000, 0);
            Perusahaan perusahaan = new Perusahaan(1, "COM1", "TVK. GALAU", "PT. MOVE ON", kelurahan, alamat, kontak, 1000, Perusahaan.Status.AKTIF);
            Pelanggan pelanggan = new Pelanggan(1, "1", perusahaan, "PLGT1", "Deddy Kakunsi", "Programmer", kelurahan, alamat, kontak, detail, Pelanggan.Status.AKTIF);
            Pegawai pegawai = new Pegawai(0, "PG001", perusahaan, "Test", null, Pegawai.Status.AKTIF);
            
            Tagihan tagihan = new Tagihan(2015, Month.JANUARY);
            Pembayaran pembayaran = new Pembayaran(0, "", DateUtil.getNow(), pelanggan, pegawai, detail.getIuran(), tagihan);
            
            List<Pembayaran> listPembayaran = new ArrayList<>(); 
            listPembayaran.add(pembayaran);
            
            pelanggan.setListPembayaran(listPembayaran);

            Map<String, Object> model = new HashMap<>();
            model.put("pelanggan", pelanggan);
            model.put("pembayaran", true);
            
            kartu.create(model, document);
            document.close();

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (EmptyIdException e) {
			e.printStackTrace();
		} catch (EmptyCodeException e) {
			e.printStackTrace();
		}
	}

	private static void createWithDb(Document document) throws EntityNotExistException {
		@SuppressWarnings("resource")
		ApplicationContext appContext = new AnnotationConfigApplicationContext(SpringDataJpaConfig.class);
		PelangganService pelangganService = appContext.getBean(PelangganService.class);

        try {
            PdfWriter.getInstance(document, new FileOutputStream("E:\\test.pdf"));

            document.open();
            
            Pelanggan pelanggan = pelangganService.getOne(35);
            pelanggan = pelangganService.cetakKartu(pelanggan);

            Map<String, Object> model = new HashMap<>();
            model.put("pelanggan", pelanggan);
            model.put("pembayaran", true);
            
            kartu.create(model, document);
            
            document.close();

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
		}
	}

	@Override
	protected Document newDocument() {
		return super.newDocument();
	}
}
