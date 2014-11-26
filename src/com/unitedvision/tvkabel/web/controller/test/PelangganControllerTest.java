package com.unitedvision.tvkabel.web.controller.test;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.unitedvision.tvkabel.core.service.PelangganService;
import com.unitedvision.tvkabel.web.WebConfig;

@RunWith (SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfig.class})
@Transactional
@TransactionConfiguration (defaultRollback = true)
public class PelangganControllerTest {
	@Autowired
	private PelangganService pelangganService;
	@Autowired
	private WebApplicationContext wac;
	
	@SuppressWarnings("unused")
	private MockMvc mockMvc;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	public void activatePelangganWorks() {
	}

	public void passivatePelangganWorks() {
		
	}

	public void banPelangganWorks() {
		
	}
}
