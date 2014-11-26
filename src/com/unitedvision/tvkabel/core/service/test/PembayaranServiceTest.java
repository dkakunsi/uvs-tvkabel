package com.unitedvision.tvkabel.core.service.test;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.unitedvision.tvkabel.core.domain.Pembayaran;
import com.unitedvision.tvkabel.core.domain.Perusahaan;
import com.unitedvision.tvkabel.core.service.PembayaranService;
import com.unitedvision.tvkabel.core.service.PerusahaanService;
import com.unitedvision.tvkabel.exception.EntityNotExistException;
import com.unitedvision.tvkabel.persistence.SpringDataJpaConfig;
import com.unitedvision.tvkabel.persistence.entity.PembayaranEntity;
import com.unitedvision.tvkabel.util.DateUtil;

@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration (classes = {SpringDataJpaConfig.class})
@Transactional
@TransactionConfiguration (defaultRollback = true)
public class PembayaranServiceTest {
	@Autowired
	private PembayaranService pembayaranService;
	@Autowired
	private PerusahaanService perusahaanService;
	
	@Test
	public void testGet() throws EntityNotExistException {
		Perusahaan perusahaan = perusahaanService.getOne(17);
		Date tanggalMulai = DateUtil.getFirstDate();
		Date tanggalAkhir = DateUtil.getLastDate();
		int lastNumber = 0;
		
		@SuppressWarnings("unchecked")
		List<PembayaranEntity> list = (List<PembayaranEntity>)pembayaranService.get(perusahaan, tanggalMulai, tanggalAkhir, lastNumber);
		
		for (Pembayaran pembayaran : list) {
			assertNotNull(pembayaran.getId());
			assertNotEquals(0, pembayaran.getId());
		}
	}
}
