package com.unitedvision.tvkabel.util.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.unitedvision.tvkabel.entity.Alamat;
import com.unitedvision.tvkabel.entity.Kelurahan;
import com.unitedvision.tvkabel.entity.Pelanggan;
import com.unitedvision.tvkabel.exception.EmptyCodeException;
import com.unitedvision.tvkabel.exception.EmptyIdException;
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
	public void testCreateKode() throws EmptyIdException, EmptyCodeException {
		CodeUtil.CodeGenerator codeGenerator = new CodeUtil.CodeGenerator();
		
		Kelurahan kelurahanEntity = new Kelurahan(0, null, "Winangun 1");
		//Alamat alamat = new Alamat(6, "", 0, 0);
        Alamat alamat = new Alamat();
		Pelanggan pelanggan = new Pelanggan(0, "1", null, "", "", "", kelurahanEntity, alamat, null, null, null);
		
		String generatedKode = codeGenerator.createKode(pelanggan);
		
		assertEquals("WS06001", generatedKode);
	}
}
