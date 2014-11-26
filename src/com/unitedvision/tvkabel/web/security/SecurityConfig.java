package com.unitedvision.tvkabel.web.security;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.unitedvision.tvkabel.core.domain.Kredensi.Role;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Resource(name = "authService")
	private UserDetailsService userDetailsService;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}

	@Configuration
	public static class WebApiSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http
			.csrf().disable()
	        .authorizeRequests()
	        	.antMatchers("/api/**").hasRole(Role.OPERATOR.name())
	        	.antMatchers("/api/**/master").hasRole(Role.OWNER.name())
	        	.antMatchers("/api/alamat/**").permitAll()
	        	.antMatchers("/api/**/registrasi").permitAll()
	        	.antMatchers("/login").permitAll()
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
	        .authorizeRequests()
				.antMatchers("/admin").hasRole(Role.ADMIN.name())
	            .and()
	        .formLogin()
	        	.loginPage("/login").permitAll()
	            .and()
	        .logout()
	        	.invalidateHttpSession(true)
	        	.logoutSuccessUrl("/");
		}
	}
}
