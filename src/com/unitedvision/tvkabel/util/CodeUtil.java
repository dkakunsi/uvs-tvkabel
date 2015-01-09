package com.unitedvision.tvkabel.util;

import com.unitedvision.tvkabel.core.domain.Pelanggan;

public class CodeUtil {
	private static final String kode = "Dk4kuN51";
	
	public static String getKode() {
		return kode;
	}
	
	public static class CodeGenerator {
		private int[] WS = {0, 0, 0, 0, 0, 0, 0};
		private int[] WD = {0, 0, 0, 0, 0, 0, 0};
		
		public void setWSNumber(int index, int number) {
			WS[index - 1] = number;
		}

		public void setWDNumber(int index, int number) {
			WD[index - 1] = number;
		}
		
		public void increase(String namaKelurahan, int lingkungan) {
			if (namaKelurahan.equals("Winangun 1")) {
				WS[lingkungan - 1] += 1;
			} else if (namaKelurahan.equals("Winangun 2")) {
				WD[lingkungan - 1] += 1;
			}
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
			
			if (pelanggan.getNamaKelurahan().equals("Winangun 1") && pelanggan.getLingkungan() == 1) {
				kode = String.format("WS01%s", createNumText(WS[0] + 1));
			} else if (pelanggan.getNamaKelurahan().equals("Winangun 1") && pelanggan.getLingkungan() == 2) {
				kode = String.format("WS02%s", createNumText(WS[1] + 1));
			} else if (pelanggan.getNamaKelurahan().equals("Winangun 1") && pelanggan.getLingkungan() == 3) {
				kode = String.format("WS03%s", createNumText(WS[2] + 1));
			} else if (pelanggan.getNamaKelurahan().equals("Winangun 1") && pelanggan.getLingkungan() == 4) {
				kode = String.format("WS04%s", createNumText(WS[3] + 1));
			} else if (pelanggan.getNamaKelurahan().equals("Winangun 1") && pelanggan.getLingkungan() == 5) {
				kode = String.format("WS05%s", createNumText(WS[4] + 1));
			} else if (pelanggan.getNamaKelurahan().equals("Winangun 1") && pelanggan.getLingkungan() == 6) {
				kode = String.format("WS06%s", createNumText(WS[5] + 1));
			} else if (pelanggan.getNamaKelurahan().equals("Winangun 1") && pelanggan.getLingkungan() == 7) {
				kode = String.format("WS07%s", createNumText(WS[6] + 1));
			} else if (pelanggan.getNamaKelurahan().equals("Winangun 2") && pelanggan.getLingkungan() == 1) {
				kode = String.format("WD01%s", createNumText(WD[0] + 1));
			} else if (pelanggan.getNamaKelurahan().equals("Winangun 2") && pelanggan.getLingkungan() == 2) {
				kode = String.format("WD02%s", createNumText(WD[1] + 1));
			} else if (pelanggan.getNamaKelurahan().equals("Winangun 2") && pelanggan.getLingkungan() == 3) {
				kode = String.format("WD03%s", createNumText(WD[2] + 1));
			} else if (pelanggan.getNamaKelurahan().equals("Winangun 2") && pelanggan.getLingkungan() == 4) {
				kode = String.format("WD04%s", createNumText(WD[3] + 1));
			} else if (pelanggan.getNamaKelurahan().equals("Winangun 2") && pelanggan.getLingkungan() == 5) {
				kode = String.format("WD05%s", createNumText(WD[4] + 1));
			} else if (pelanggan.getNamaKelurahan().equals("Winangun 2") && pelanggan.getLingkungan() == 6) {
				kode = String.format("WD06%s", createNumText(WD[5] + 1));
			} else if (pelanggan.getNamaKelurahan().equals("Winangun 2") && pelanggan.getLingkungan() == 7) {
				kode = String.format("WD07%s", createNumText(WD[6] + 1));
			}
			
			return kode;
		}
	}
}
