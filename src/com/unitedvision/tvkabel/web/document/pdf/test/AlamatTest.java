package com.unitedvision.tvkabel.web.document.pdf.test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import com.unitedvision.tvkabel.web.document.pdf.AlamatPdfView;

public class AlamatTest extends AlamatPdfView {
	private static AlamatTest kartu = new AlamatTest();

	public static void main(String[] args) {
        Document document = kartu.newDocument();

        try {
            PdfWriter.getInstance(document,
                new FileOutputStream("E:\\test.pdf"));

            document.open();
            KelurahanEntity kelurahan = new KelurahanEntity(null, "Paniki Bawah");
            AlamatValue alamat = new AlamatValue(kelurahan, 1, "Rumah Saya");
            KontakValue kontak = new KontakValue("", "082347643198", "");
            Detail detail = new Detail(new Date(), 1, 50000, 0);
            PerusahaanEntity perusahaan = new PerusahaanEntity(1, "COM1", "PT. GALAU", alamat, kontak, 1000, Perusahaan.Status.AKTIF);
            PelangganEntity pelanggan = new PelangganEntity(1, perusahaan, "PB01001", "Deddy Kakunsi", "Programmer", alamat, kontak, detail, Pelanggan.Status.AKTIF);
            
            KelurahanEntity kelurahan2 = new KelurahanEntity(null, "Paniki Atas");
            AlamatValue alamat2 = new AlamatValue(kelurahan2, 1, "Rumah Saya");
            KontakValue kontak2 = new KontakValue("", "089669926194", "");
            Detail detail2 = new Detail(new Date(), 1, 50000, 0);
            PerusahaanEntity perusahaan2 = new PerusahaanEntity(1, "COM1", "PT. GALAU", alamat2, kontak2, 1000, Perusahaan.Status.AKTIF);
            PelangganEntity pelanggan2 = new PelangganEntity(1, perusahaan2, "PA01001", "Joseph Katiandagho", "Programmer", alamat2, kontak2, detail2, Pelanggan.Status.AKTIF);

            PelangganEntity pelanggan3 = new PelangganEntity(1, perusahaan, "PB01002", "Christoper Kakunsi", "Programmer", alamat, kontak, detail, Pelanggan.Status.AKTIF);

            List<PelangganEntity> list = new ArrayList<>();
            list.add(pelanggan);
            list.add(pelanggan3);
            list.add(pelanggan2);
            
            Map<String, Object> model = new HashMap<>();
            model.put("listPelanggan", list);
            
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
