package com.unitedvision.tvkabel.core.document.pdf.test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfWriter;
import com.unitedvision.tvkabel.configuration.ApplicationConfig;
import com.unitedvision.tvkabel.document.pdf.AlamatPdfView;
import com.unitedvision.tvkabel.entity.Kelurahan;
import com.unitedvision.tvkabel.entity.Pelanggan;
import com.unitedvision.tvkabel.entity.Perusahaan;
import com.unitedvision.tvkabel.exception.ApplicationException;
import com.unitedvision.tvkabel.service.KelurahanService;
import com.unitedvision.tvkabel.service.PelangganService;
import com.unitedvision.tvkabel.service.PerusahaanService;

public class AlamatTest extends AlamatPdfView {
	private static AlamatTest kartu = new AlamatTest();
	private static ApplicationContext appContext = new AnnotationConfigApplicationContext(ApplicationConfig.class);
	private static PelangganService pelangganService = appContext.getBean(PelangganService.class);
	private static PerusahaanService perusahaanService = appContext.getBean(PerusahaanService.class);
	private static KelurahanService kelurahanService = appContext.getBean(KelurahanService.class);

	public static void main(String[] args) throws ApplicationException {
        Document document = kartu.newDocument();

        try {
            PdfWriter.getInstance(document, new FileOutputStream("E:\\alamat-test.pdf"));

            document.open();
            Perusahaan perusahaan = perusahaanService.getOne(17);
            Kelurahan kelurahan = kelurahanService.getOne(22);
            List<Pelanggan> list = pelangganService.get(perusahaan, Pelanggan.Status.AKTIF, kelurahan, 1);
            
            Map<String, Object> model = new HashMap<>();
            model.put("listPelanggan", list);
            
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
