package com.unitedvision.tvkabel.web.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.unitedvision.tvkabel.core.domain.Pegawai;
import com.unitedvision.tvkabel.core.domain.Kredensi.Role;
import com.unitedvision.tvkabel.core.service.PegawaiService;
import com.unitedvision.tvkabel.exception.EntityNotExistException;
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

	@Override
	public CustomUser loadUserByUsername(String username) throws UsernameNotFoundException {
		if (username.toLowerCase().equals("admin"))
			return generateAdmin(username);
		return generateOperator(username);
	}
	
	private CustomUser generateAdmin(String username) {
		return new CustomUser(username, CodeUtil.getKode(), null, getAuthorities(Role.ADMIN));
	}
	
	private CustomUser generateOperator(String username) throws UsernameNotFoundException {
		try {
			final Pegawai pegawai = pegawaiService.getOneByUsername(username);

			return new CustomUser(username, pegawai.getPassword(), pegawai.toOperator(), getAuthorities(pegawai.getRole()));
		} catch (EntityNotExistException e) {
			throw new UsernameNotFoundException(e.getMessage());
		}
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
