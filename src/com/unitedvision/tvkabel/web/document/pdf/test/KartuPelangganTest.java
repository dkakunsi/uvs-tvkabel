package com.unitedvision.tvkabel.web.document.pdf.test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfWriter;
import com.unitedvision.tvkabel.core.domain.Pelanggan;
import com.unitedvision.tvkabel.core.domain.Perusahaan;
import com.unitedvision.tvkabel.exception.EmptyCodeException;
import com.unitedvision.tvkabel.exception.EmptyIdException;
import com.unitedvision.tvkabel.persistence.entity.AlamatValue;
import com.unitedvision.tvkabel.persistence.entity.KelurahanEntity;
import com.unitedvision.tvkabel.persistence.entity.KontakValue;
import com.unitedvision.tvkabel.persistence.entity.PelangganEntity;
import com.unitedvision.tvkabel.persistence.entity.PerusahaanEntity;
import com.unitedvision.tvkabel.persistence.entity.PelangganEntity.Detail;
import com.unitedvision.tvkabel.web.document.pdf.KartuPelangganPdfView;

public class KartuPelangganTest extends KartuPelangganPdfView {
	private static KartuPelangganTest kartu = new KartuPelangganTest();

	public static void main(String[] args) {
        Document document = kartu.newDocument();

        try {
            PdfWriter.getInstance(document,
                new FileOutputStream("E:\\test.pdf"));

            document.open();
            KelurahanEntity kelurahan = new KelurahanEntity(null, "Paniki Bawah");
            AlamatValue alamat = new AlamatValue(kelurahan, 1, "Rumah Saya");
            KontakValue kontak = new KontakValue("", "082347643198", "dkakunsi@gmail.com");
            Detail detail = new Detail(new Date(), 1, 50000, 0);
            PerusahaanEntity perusahaan = new PerusahaanEntity(1, "COM1", "PT. GALAU", alamat, kontak, 1000, Perusahaan.Status.AKTIF);
            PelangganEntity pelanggan = new PelangganEntity(1, perusahaan, "PLGT1", "Deddy Kakunsi", "Programmer", alamat, kontak, detail, Pelanggan.Status.AKTIF);
            
            Map<String, Object> model = new HashMap<>();
            model.put("pelanggan", pelanggan);
            model.put("pembayaran", 0);
            
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
        
        System.out.println("DONE...");
	}
	
	@Override
	protected Document newDocument() {
		return super.newDocument();
	}
}
