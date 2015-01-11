package com.unitedvision.tvkabel.util.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.unitedvision.tvkabel.domain.Alamat;
import com.unitedvision.tvkabel.domain.Kelurahan;
import com.unitedvision.tvkabel.domain.Pelanggan;
import com.unitedvision.tvkabel.util.CodeUtil;

public class CodeUtil_CodeGeneratorTest {
	
	@Test
	public void testCreateNumText_OneDigit() {
		CodeUtil.CodeGenerator codeGenerator = new CodeUtil.CodeGenerator();
		int num = 3;
		String numText = codeGenerator.createNumText(num);
		
		assertEquals("003", numText);
	}
	
	@Test
	public void testCreateNumText_TwoDigit() {
		CodeUtil.CodeGenerator codeGenerator = new CodeUtil.CodeGenerator();
		int num = 11;
		String numText = codeGenerator.createNumText(num);
		
		assertEquals("011", numText);
	}
	
	@Test
	public void testCreateNumText_ThreeDigit() {
		CodeUtil.CodeGenerator codeGenerator = new CodeUtil.CodeGenerator();
		int num = 303;
		String numText = codeGenerator.createNumText(num);
		
		assertEquals("303", numText);
	}
	
	@Test
	public void testCreateKode() {
		CodeUtil.CodeGenerator codeGenerator = new CodeUtil.CodeGenerator();
		
		Kelurahan kelurahanEntity = new Kelurahan(null, "Winangun 1");
		Alamat alamatValue = new Alamat(kelurahanEntity, 6, "");
		Pelanggan pelanggan = new Pelanggan(null, "", "", alamatValue, null, null, null);
		
		String generatedKode = codeGenerator.createKode(pelanggan);
		
		assertEquals("WS06001", generatedKode);
	}
}
