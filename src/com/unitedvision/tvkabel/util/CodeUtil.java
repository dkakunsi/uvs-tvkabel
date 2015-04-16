package com.unitedvision.tvkabel.util;

import com.unitedvision.tvkabel.entity.Pelanggan;

public class CodeUtil {
	private static final String kode = "Dk4kuN51";
	
	public static String getKode() {
		return kode;
	}
	
	public static class CodeGenerator {
		private int counter;
		
		public void increase() {
			counter++;
		}

		public String createNumText(int num) {
			int numLength = Integer.toString(num).length();
			String numText = "";
			
			for (int i = 0; i < (3 - numLength); i++) {
				numText += "0";
			}
			
			return String.format("%s%s", numText, Integer.toString(num));
		}
		
		public String createKode(Pelanggan pelanggan) {
			String kode = pelanggan.getKode();
			String numText = createNumText(counter + 1);
			
			if (pelanggan.getNamaKelurahan().equals("Winangun 1") && pelanggan.getLingkungan() == 1) {
				kode = String.format("WS01%s", numText);
			} else if (pelanggan.getNamaKelurahan().equals("Winangun 1") && pelanggan.getLingkungan() == 2) {
				kode = String.format("WS02%s", numText);
			} else if (pelanggan.getNamaKelurahan().equals("Winangun 1") && pelanggan.getLingkungan() == 3) {
				kode = String.format("WS03%s", numText);
			} else if (pelanggan.getNamaKelurahan().equals("Winangun 1") && pelanggan.getLingkungan() == 4) {
				kode = String.format("WS04%s", numText);
			} else if (pelanggan.getNamaKelurahan().equals("Winangun 1") && pelanggan.getLingkungan() == 5) {
				kode = String.format("WS05%s", numText);
			} else if (pelanggan.getNamaKelurahan().equals("Winangun 1") && pelanggan.getLingkungan() == 6) {
				kode = String.format("WS06%s", numText);
			} else if (pelanggan.getNamaKelurahan().equals("Winangun 1") && pelanggan.getLingkungan() == 7) {
				kode = String.format("WS07%s", numText);
			} else if (pelanggan.getNamaKelurahan().equals("Winangun 2") && pelanggan.getLingkungan() == 1) {
				kode = String.format("WD01%s", numText);
			} else if (pelanggan.getNamaKelurahan().equals("Winangun 2") && pelanggan.getLingkungan() == 2) {
				kode = String.format("WD02%s", numText);
			} else if (pelanggan.getNamaKelurahan().equals("Winangun 2") && pelanggan.getLingkungan() == 3) {
				kode = String.format("WD03%s", numText);
			} else if (pelanggan.getNamaKelurahan().equals("Winangun 2") && pelanggan.getLingkungan() == 4) {
				kode = String.format("WD04%s", numText);
			} else if (pelanggan.getNamaKelurahan().equals("Winangun 2") && pelanggan.getLingkungan() == 5) {
				kode = String.format("WD05%s", numText);
			} else if (pelanggan.getNamaKelurahan().equals("Winangun 2") && pelanggan.getLingkungan() == 6) {
				kode = String.format("WD06%s", numText);
			} else if (pelanggan.getNamaKelurahan().equals("Winangun 2") && pelanggan.getLingkungan() == 7) {
				kode = String.format("WD07%s", numText);
			}
			
			return kode;
		}
	}
}
