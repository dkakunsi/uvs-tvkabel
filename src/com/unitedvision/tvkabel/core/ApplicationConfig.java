package com.unitedvision.tvkabel.core;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.unitedvision.tvkabel.persistence.interceptor.NullObjectInterceptor;

/**
 * <p>Base configuration class. It will scan every bean in core package, and tell Spring application context to manage those beans.
 * AspectJ support is enabled here.</p>
 * 
 * <p>This class is used by Spring Framework. So, developer will not working directly with this class.</p>
 * 
 * @author Deddy Christoper Kakunsi
 *
 */
@Configuration
@ComponentScan("com.unitedvision.tvkabel.core")
@EnableAspectJAutoProxy
public class ApplicationConfig {

	/**
	 * <p>NullObjectInterceptor bean definition. It tells Spring application context to manage NullObjectInterceptor creation.</p>
	 * 
	 * @return NullObjectInterceptor
	 */
	@Bean
	public NullObjectInterceptor nullObjectInterceptor() {
		return new NullObjectInterceptor();
	}
}
