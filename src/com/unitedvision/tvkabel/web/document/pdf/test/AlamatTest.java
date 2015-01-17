package com.unitedvision.tvkabel.web.document.pdf.test;

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
import com.unitedvision.tvkabel.core.domain.Pelanggan;
import com.unitedvision.tvkabel.core.domain.Perusahaan;
import com.unitedvision.tvkabel.core.service.PerusahaanService;
import com.unitedvision.tvkabel.core.service.RekapService;
import com.unitedvision.tvkabel.exception.EntityNotExistException;
import com.unitedvision.tvkabel.persistence.SpringDataJpaConfig;
import com.unitedvision.tvkabel.web.document.pdf.AlamatPdfView;

public class AlamatTest extends AlamatPdfView {
	private static AlamatTest kartu = new AlamatTest();
	private static ApplicationContext appContext = new AnnotationConfigApplicationContext(SpringDataJpaConfig.class);
	private static RekapService rekapService = appContext.getBean(RekapService.class);
	private static PerusahaanService perusahaanService = appContext.getBean(PerusahaanService.class);

	public static void main(String[] args) {
        Document document = kartu.newDocument();

        try {
            PdfWriter.getInstance(document, new FileOutputStream("E:\\test.pdf"));

            document.open();
            Perusahaan perusahaan = perusahaanService.getOne(17);
            List<? extends Pelanggan> list = rekapService.rekapAlamat(perusahaan);
            
            Map<String, Object> model = new HashMap<>();
            model.put("listPelanggan", list);
            
            kartu.create(model, document);
            document.close();

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
			e.printStackTrace(); 
		} catch (EntityNotExistException e) {
			e.printStackTrace();
		}
        
        System.out.println("DONE...");
	}
	
	@Override
	protected Document newDocument() {
		return super.newDocument();
	}
}
