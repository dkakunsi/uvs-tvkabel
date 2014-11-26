package com.unitedvision.tvkabel.util;

public final class MessageUtil {
	public static String getMessage(String process, Integer state, String source) {
		String message;
		if (state == 0) {
			message = "Berhasil";
		} else {
			message = "Gagal";
		}
		
		if (process.toLowerCase().equals("save")) {
			message = String.format("%s menyimpan data", message);
		} else if (process.toLowerCase().equals("delete")) {
			message = String.format("%s menghapus data", message);
		}
		
		return String.format("%s %s", message, source);
	}
}
