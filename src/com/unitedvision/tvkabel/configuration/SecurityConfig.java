package com.unitedvision.tvkabel.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.unitedvision.tvkabel.entity.Pegawai.Role;
import com.unitedvision.tvkabel.security.CustomAuthenticationProvider;

@Configuration
@EnableWebSecurity
@ComponentScan("com.unitedvision.tvkabel.security")
@Import(ApplicationConfig.class)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private CustomAuthenticationProvider authenticationProvider;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider);
	}

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
        	.antMatchers("/perusahaan/daftar").permitAll()
        	.antMatchers("/alamat/**").permitAll()
        	.antMatchers("/**/admin").hasRole(Role.ADMIN.name())
        	.anyRequest().authenticated()
            .and()
        .httpBasic();
	}
}
