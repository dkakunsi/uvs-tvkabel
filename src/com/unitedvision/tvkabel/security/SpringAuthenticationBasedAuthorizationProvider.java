package com.unitedvision.tvkabel.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.unitedvision.tvkabel.entity.Operator;
import com.unitedvision.tvkabel.entity.Pegawai;
import com.unitedvision.tvkabel.entity.Perusahaan;
import com.unitedvision.tvkabel.entity.Pegawai.Role;
import com.unitedvision.tvkabel.exception.ApplicationException;
import com.unitedvision.tvkabel.exception.UnauthenticatedAccessException;

/**
 * Class that provides authentication mechanism based on Spring's {@code Authentication} class.
 * @author Deddy Christoper Kakunsi
 *
 */
@Component
public class SpringAuthenticationBasedAuthorizationProvider extends AuthorizationProvider {
	public Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}
	
	public CustomUser getUserDetails() {
		return (CustomUser) getAuthentication().getPrincipal();
	}

	/**
	 * Returns user's role depends on {@code Authentication}.<br />
	 * User's role was defined in {@code Role} class.
	 * @param authentication
	 * @return User's {@code Role}.
	 */
	public Role getUserRole(final Authentication authentication) {
		String authority = getAuthority(authentication);
		
		switch (authority) {
			case "ROLE_ADMIN": return Role.ADMIN;
			case "ROLE_OWNER": return Role.OWNER;
			case "ROLE_OPERATOR": return Role.OPERATOR;
			case "ROLE_NOTHING": return Role.NOTHING;
			default: return Role.GUEST;
		}
	}
	
	/**
	 * Returns user's authority depends on {@code Authentication}.<br />
	 * @param authentication
	 * @return User's {@code Role}.
	 */
	public String getAuthority(final Authentication authentication) {
		String authority = "ROLE_GUEST";
		
		for (GrantedAuthority ga : authentication.getAuthorities()) {
			authority = ga.getAuthority();
			break;
		}
		
		return authority;
	}

	/**
	 * Returns user's role in string format, based on {@code Authentication}.
	 * @param authentication
	 * @return user's role in string format
	 */
	public String getUserRoleStr(final Authentication authentication) {
		return getUserRoleStr(getUserRole(authentication));
	}
	
	/**
	 * Returns user in {@code Operator} type, based on {@code Authentication}.
	 * @param authentication
	 * @return user in {@code Operator} type.
	 */
	public Operator getOperator() {
		return getUserDetails().getOperator();
	}
	
	/**
	 * Returns user in {@code Pegawai} type, from {@code Authentication}.
	 * @param authentication
	 * @return user in {@code Pegawai} type.
	 */
	public Pegawai getPegawai() {
		return (Pegawai)getOperator();
	}
	
	/**
	 * Returns user's company from {@code Authentication}.
	 * @param authentication
	 * @return user's company.
	 * @throws ApplicationException 
	 */
	public Perusahaan getPerusahaan() throws UnauthenticatedAccessException {
		try {
			return getOperator().getPerusahaan();
		} catch(NullPointerException e) {
			throw new UnauthenticatedAccessException(e.getMessage());
		}
	}

}
