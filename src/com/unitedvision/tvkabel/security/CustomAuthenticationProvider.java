package com.unitedvision.tvkabel.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import com.unitedvision.tvkabel.exception.EntityNotExistException;
import com.unitedvision.tvkabel.exception.UnauthenticatedAccessException;

@Service
public class CustomAuthenticationProvider implements AuthenticationProvider {
	@Autowired
	private CustomUserDetailsService userDetailService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
	    String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        
        CustomUser user;

        if (username.equals("admin")) {
        	user = userDetailService.loadAdmin(username);
        } else {
            try {
            	user = userDetailService.loadUserByToken(password);
            } catch (EntityNotExistException e) {
            	user = userDetailService.loadUserByUsername(username);
            } catch (UnauthenticatedAccessException e) {
                throw new BadCredentialsException(e.getMessage());
    		}
        }
 
        if (!(password.equals(user.getPassword())) || !(username.equals(user.getUsername())))
            throw new BadCredentialsException("Kombinasi Username dan Password Salah!");
        return new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return true;
	}
}
