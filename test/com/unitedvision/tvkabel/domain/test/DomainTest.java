package com.unitedvision.tvkabel.domain.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.unitedvision.tvkabel.domain.entity.Domain;
import com.unitedvision.tvkabel.domain.entity.Perusahaan;
import com.unitedvision.tvkabel.exception.EmptyIdException;

public class DomainTest {
	
	@Test
	public void isNew() {
		Domain domain = new Perusahaan();
		
		assertEquals(0, domain.getId());
		assertEquals(true, domain.isNew());
	}

	@Test
	public void setIdWorks() throws EmptyIdException {
		Domain domain = new Perusahaan();
		domain.setId(1);
		
		assertEquals(1, domain.getId());
		assertEquals(false, domain.isNew());
	}
	
	@Test (expected = EmptyIdException.class)
	public void setIdWithZeroId() throws EmptyIdException {
		Domain domain = new Perusahaan();
		domain.setId(0);
	}

	@Test (expected = EmptyIdException.class)
	public void setIdWithNegativeId() throws EmptyIdException {
		Domain domain = new Perusahaan();
		domain.setId(-1);
	}
}
