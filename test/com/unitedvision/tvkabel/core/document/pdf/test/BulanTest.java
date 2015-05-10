package com.unitedvision.tvkabel.core.document.pdf.test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.Month;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfWriter;
import com.unitedvision.tvkabel.configuration.ApplicationConfig;
import com.unitedvision.tvkabel.document.pdf.BulanPdfView;
import com.unitedvision.tvkabel.entity.Pelanggan;
import com.unitedvision.tvkabel.entity.Perusahaan;
import com.unitedvision.tvkabel.exception.ApplicationException;
import com.unitedvision.tvkabel.service.PembayaranService;
import com.unitedvision.tvkabel.service.PerusahaanService;

public class BulanTest extends BulanPdfView {
	private static BulanTest kartu = new BulanTest();
	private static ApplicationContext appContext = new AnnotationConfigApplicationContext(ApplicationConfig.class);
	private static PembayaranService pembayaranService = appContext.getBean(PembayaranService.class);
	private static PerusahaanService perusahaanService = appContext.getBean(PerusahaanService.class);

	public static void main(String[] args) throws ApplicationException {
        Document document = kartu.newDocument();

        try {
            PdfWriter.getInstance(document, new FileOutputStream("E:\\bulan-test.pdf"));

            document.open();
            String bulan = "JANUARY";
            Perusahaan perusahaan = perusahaanService.getOne(17);
            Month month = Month.valueOf(bulan);
            int tahun = 2015;
            
			List<Pelanggan> list = pembayaranService.rekapBulanan(perusahaan, month, tahun);
            
            Map<String, Object> model = new HashMap<>();
            model.put("rekap", list);
            model.put("bulan", bulan);
            model.put("tahun", 2015);
            
            kartu.create(model, document);
            document.close();

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
			e.printStackTrace(); 
		}
        
        System.out.println("DONE...");
	}
	
	@Override
	protected Document newDocument() {
		return super.newDocument();
	}
}
