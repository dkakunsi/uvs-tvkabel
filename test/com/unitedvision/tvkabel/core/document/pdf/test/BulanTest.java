package com.unitedvision.tvkabel.core.document.pdf.test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.Month;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfWriter;
import com.unitedvision.tvkabel.core.document.pdf.BulanPdfView;
import com.unitedvision.tvkabel.core.service.PerusahaanService;
import com.unitedvision.tvkabel.core.service.RekapService;
import com.unitedvision.tvkabel.exception.EmptyIdException;
import com.unitedvision.tvkabel.exception.EntityNotExistException;
import com.unitedvision.tvkabel.persistence.SpringDataJpaConfig;
import com.unitedvision.tvkabel.persistence.entity.Pelanggan;
import com.unitedvision.tvkabel.persistence.entity.Perusahaan;

public class BulanTest extends BulanPdfView {
	private static BulanTest kartu = new BulanTest();
	private static ApplicationContext appContext = new AnnotationConfigApplicationContext(SpringDataJpaConfig.class);
	private static RekapService rekapService = appContext.getBean(RekapService.class);
	private static PerusahaanService perusahaanService = appContext.getBean(PerusahaanService.class);

	public static void main(String[] args) throws EmptyIdException {
        Document document = kartu.newDocument();

        try {
            PdfWriter.getInstance(document, new FileOutputStream("E:\\test.pdf"));

            document.open();
            String bulan = "JANUARY";
            Perusahaan perusahaan = perusahaanService.getOne(17);
            Month month = Month.valueOf(bulan);
            int tahun = 2015;
            
			List<Pelanggan> list = (List<Pelanggan>)rekapService.rekapBulanan(perusahaan, month, tahun);

            Month[] listBulan = {Month.SEPTEMBER, Month.OCTOBER, Month.NOVEMBER, Month.DECEMBER, Month.JANUARY};
            
            Map<String, Object> model = new HashMap<>();
            model.put("rekap", list);
            model.put("bulan", bulan);
            model.put("tahun", 2015);
            model.put("listBulan", Arrays.asList(listBulan));
            
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
