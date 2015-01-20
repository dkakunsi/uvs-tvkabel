package com.unitedvision.tvkabel.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.unitedvision.tvkabel.persistence.entity.Pegawai.Role;

@Configuration
@ComponentScan("com.unitedvision.tvkabel.security")
@EnableWebSecurity
public class SecurityConfig {
	@Autowired
	private CustomAuthenticationProvider authenticationProvider;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider);
	}

	@Configuration
	public static class WebApiSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http
			.csrf().disable()
	        .authorizeRequests()
	        	.antMatchers("/api/**").hasRole(Role.OPERATOR.name())
	        	.antMatchers("/**/master").hasRole(Role.OWNER.name())
	        	.antMatchers("/api/alamat/**").permitAll()
	        	.antMatchers("/**/registrasi").permitAll()
	        	.antMatchers("/api/login").permitAll()
	            .and()
	        .httpBasic();
		}
	}
	
	@Configuration
	@Order(1)
	public static class WebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
		@Override
		public void configure(WebSecurity web) throws Exception {
	    	web.ignoring().antMatchers("/resources/**");
			super.configure(web);
		}

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http
			.csrf().disable()
	        .authorizeRequests()
				.antMatchers("/admin/**").permitAll() // TODO implements admin channel
	            .and()
	        .formLogin()
	        	.permitAll()
	            .and()
	        .logout()
	        	.invalidateHttpSession(true);
		}
	}
}
