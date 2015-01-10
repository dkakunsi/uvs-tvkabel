package com.unitedvision.tvkabel.util.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.unitedvision.tvkabel.core.domain.Pelanggan;
import com.unitedvision.tvkabel.persistence.entity.AlamatValue;
import com.unitedvision.tvkabel.persistence.entity.KelurahanEntity;
import com.unitedvision.tvkabel.persistence.entity.PelangganEntity;
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
		codeGenerator.setWSNumber(6, 23);
		
		KelurahanEntity kelurahanEntity = new KelurahanEntity(null, "Winangun 1");
		AlamatValue alamatValue = new AlamatValue(kelurahanEntity, 6, "");
		Pelanggan pelanggan = new PelangganEntity(null, "", "", alamatValue, null, null, null);
		
		String generatedKode = codeGenerator.createKode(pelanggan);
		
		assertEquals("WS06024", generatedKode);
	}
}
