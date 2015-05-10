package com.unitedvision.tvkabel.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.unitedvision.tvkabel.entity.Pegawai;
import com.unitedvision.tvkabel.entity.Token;
import com.unitedvision.tvkabel.entity.Pegawai.Role;
import com.unitedvision.tvkabel.exception.ApplicationException;
import com.unitedvision.tvkabel.exception.EntityNotExistException;
import com.unitedvision.tvkabel.exception.UnauthenticatedAccessException;
import com.unitedvision.tvkabel.service.PegawaiService;
import com.unitedvision.tvkabel.service.TokenService;
import com.unitedvision.tvkabel.util.CodeUtil;

/**
 * Custom Authentication Provider.
 * 
 * @author Deddy Christoper Kakunsi
 *
 */
@Service("authService")
public class CustomUserDetailsService implements UserDetailsService {
	@Autowired
	private PegawaiService pegawaiService;
	@Autowired
	private TokenService tokenService;

	@Override
	public CustomUser loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			Pegawai pegawai = pegawaiService.getOneByUsername(username);
			return new CustomUser(username, pegawai.getPassword(), pegawai.toOperator(), getAuthorities(pegawai.getRole()));
		} catch (ApplicationException e) {
			throw new UsernameNotFoundException(e.getMessage());
		}
	}
	
	public CustomUser loadAdmin(String username) {
		return new CustomUser(username, CodeUtil.getKode(), null, getAuthorities(Role.ADMIN));
	}
	
	public CustomUser loadUserByToken(String tokenString) throws UnauthenticatedAccessException, EntityNotExistException {
		Token token = tokenService.get(tokenString);
		
		return new CustomUser(token.getPegawai().getUsername(), token.getToken(), token.getPegawai(), getAuthorities(token.getPegawai().getRole()));
	}

	public static List<GrantedAuthority> getAuthorities(Role role) {
		List<GrantedAuthority> authList = new ArrayList<>();

		if (role.equals(Role.ADMIN)) {
			authList.add(new SimpleGrantedAuthority("ROLE_OPERATOR"));
			authList.add(new SimpleGrantedAuthority("ROLE_OWNER"));
		} else if (role.equals(Role.OWNER)) {
			authList.add(new SimpleGrantedAuthority("ROLE_OPERATOR"));
		}
		authList.add(new SimpleGrantedAuthority("ROLE_" + role.name()));

		return authList;
	}
}
