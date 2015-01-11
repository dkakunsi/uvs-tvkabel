package com.unitedvision.tvkabel.persistence.repository.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.unitedvision.tvkabel.configuration.persistence.SpringDataJpaConfig;
import com.unitedvision.tvkabel.domain.Perusahaan;
import com.unitedvision.tvkabel.domain.persistence.repository.PerusahaanRepository;

@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration (classes = {SpringDataJpaConfig.class})
@Transactional
@TransactionConfiguration (defaultRollback = true)
public class PerusahaanRepositoryTest {
	@Autowired
	private PerusahaanRepository perusahaanRepository;
	
	@Test
	public void testFindFirstByOrderByIdDesc() {
		Perusahaan perusahaan = perusahaanRepository.findFirstByOrderByIdDesc();
		
		assertNotNull(perusahaan);
		assertNotEquals(0, perusahaan.getId());
	}
}
