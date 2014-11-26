package com.unitedvision.tvkabel.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.XmlViewResolver;
import org.springframework.web.servlet.view.tiles2.TilesConfigurer;
import org.springframework.web.servlet.view.tiles2.TilesView;
import org.springframework.web.servlet.view.tiles2.TilesViewResolver;

import com.unitedvision.tvkabel.persistence.SpringDataJpaConfig;

@Configuration
@EnableWebMvc
@Import(SpringDataJpaConfig.class)
@ComponentScan("com.unitedvision.tvkabel.web")
public class WebConfig extends WebMvcConfigurerAdapter {

	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}
	
	@Bean
	public TilesConfigurer tilesConfigurer() {
		TilesConfigurer tilesConfigurer = new TilesConfigurer();

		tilesConfigurer.setDefinitions("/WEB-INF/views/dashboard/tiles-definitions.xml"
						+ "/WEB-INF/views/cover/tiles-definitions.xml");
		tilesConfigurer.setCompleteAutoload(true);
		
		return tilesConfigurer;
	}
	
	@Bean
	public TilesViewResolver tilesViewResolver() {
		TilesViewResolver tilesViewResolver = new TilesViewResolver();
		tilesViewResolver.setViewClass(TilesView.class);
		
		return tilesViewResolver;
	}
	
	@Bean
	public XmlViewResolver xmlViewResolver() {
		XmlViewResolver xmlViewResolver = new XmlViewResolver();

		return xmlViewResolver;
	}
}
