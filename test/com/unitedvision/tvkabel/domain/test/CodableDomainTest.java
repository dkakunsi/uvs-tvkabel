package com.unitedvision.tvkabel.domain.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.unitedvision.tvkabel.domain.entity.CodableDomain;
import com.unitedvision.tvkabel.domain.entity.Perusahaan;
import com.unitedvision.tvkabel.exception.EmptyCodeException;

public class CodableDomainTest {

	@Test
	public void setKodeWorks() throws EmptyCodeException {
		CodableDomain codable = new Perusahaan();
		codable.setKode("1");
		
		assertEquals("1", codable.getKode());
	}

	@Test (expected = EmptyCodeException.class)
	public void setKodeWithNull() throws EmptyCodeException {
		CodableDomain codable = new Perusahaan();
		codable.setKode(null);
	}

	@Test (expected = EmptyCodeException.class)
	public void setKodeWithEmptyString() throws EmptyCodeException {
		CodableDomain codable = new Perusahaan();
		codable.setKode("");
	}
}
