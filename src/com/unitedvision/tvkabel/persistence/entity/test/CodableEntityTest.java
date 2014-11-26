package com.unitedvision.tvkabel.persistence.entity.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.unitedvision.tvkabel.exception.EmptyCodeException;
import com.unitedvision.tvkabel.persistence.entity.CodableEntity;
import com.unitedvision.tvkabel.persistence.entity.PerusahaanEntity;

public class CodableEntityTest {

	@Test
	public void setKodeWorks() throws EmptyCodeException {
		CodableEntity codableEntity = new PerusahaanEntity();
		codableEntity.setKode("1");
		
		assertEquals("1", codableEntity.getKode());
	}

	@Test (expected = EmptyCodeException.class)
	public void setKodeWithNull() throws EmptyCodeException {
		CodableEntity codableEntity = new PerusahaanEntity();
		codableEntity.setKode(null);
	}

	@Test (expected = EmptyCodeException.class)
	public void setKodeWithEmptyString() throws EmptyCodeException {
		CodableEntity codableEntity = new PerusahaanEntity();
		codableEntity.setKode("");
	}
}
