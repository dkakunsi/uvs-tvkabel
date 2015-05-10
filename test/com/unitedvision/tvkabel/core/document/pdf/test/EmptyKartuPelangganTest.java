package com.unitedvision.tvkabel.core.document.pdf.test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfWriter;
import com.unitedvision.tvkabel.document.pdf.DefaultKartuPelangganPdfView;
import com.unitedvision.tvkabel.entity.Alamat;
import com.unitedvision.tvkabel.entity.Kelurahan;
import com.unitedvision.tvkabel.entity.Kontak;
import com.unitedvision.tvkabel.entity.Perusahaan;
import com.unitedvision.tvkabel.exception.ApplicationException;

public class EmptyKartuPelangganTest extends DefaultKartuPelangganPdfView {
	private static EmptyKartuPelangganTest kartu = new EmptyKartuPelangganTest();

	public static void main(String[] args) throws ApplicationException {
        Document document = kartu.newDocument();

        try {
            PdfWriter.getInstance(document,
                new FileOutputStream("E:\\test.pdf"));

            document.open();
            Kelurahan kelurahan = new Kelurahan(0, null, "Paniki Bawah");
            //Alamat alamat = new Alamat(1, "Rumah Saya", 0, 0);
            Alamat alamat = new Alamat();
            Kontak kontak = new Kontak("", "082347643198", "dkakunsi@gmail.com");
            Perusahaan perusahaan = new Perusahaan(1, "COM1", "TVK. GALAU", "PT. MOVE ON", kelurahan, alamat, kontak, Perusahaan.Status.AKTIF);

            Map<String, Object> model = new HashMap<>();
            model.put("perusahaan", perusahaan);
            
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
