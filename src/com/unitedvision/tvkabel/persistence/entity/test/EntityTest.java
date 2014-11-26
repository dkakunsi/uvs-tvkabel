package com.unitedvision.tvkabel.persistence.entity.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.unitedvision.tvkabel.exception.EmptyIdException;
import com.unitedvision.tvkabel.persistence.entity.Entity;
import com.unitedvision.tvkabel.persistence.entity.PerusahaanEntity;

public class EntityTest {
	
	@Test
	public void isNew() {
		Entity entity = new PerusahaanEntity();
		
		assertEquals(0, entity.getId());
		assertEquals(true, entity.isNew());
	}

	@Test
	public void setIdWorks() throws EmptyIdException {
		Entity entity = new PerusahaanEntity();
		entity.setId(1);
		
		assertEquals(1, entity.getId());
		assertEquals(false, entity.isNew());
	}
	
	@Test (expected = EmptyIdException.class)
	public void setIdWithZeroId() throws EmptyIdException {
		Entity entity = new PerusahaanEntity();
		entity.setId(0);
	}

	@Test (expected = EmptyIdException.class)
	public void setIdWithNegativeId() throws EmptyIdException {
		Entity entity = new PerusahaanEntity();
		entity.setId(-1);
	}
}
