package com.unitedvision.tvkabel.web.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.unitedvision.tvkabel.core.domain.Operator;
import com.unitedvision.tvkabel.core.domain.Pegawai;
import com.unitedvision.tvkabel.core.domain.Perusahaan;
import com.unitedvision.tvkabel.core.domain.Kredensi.Role;
import com.unitedvision.tvkabel.exception.ApplicationException;
import com.unitedvision.tvkabel.exception.UnauthenticatedAccessException;
import com.unitedvision.tvkabel.web.model.PegawaiModel;

/**
 * Class that provides authentication mechanism based on Spring's {@code Authentication} class.
 * @author Deddy Christoper Kakunsi
 *
 */
@Component
public class SpringAuthenticationBasedAuthorizationProvider extends AuthorizationProvider {

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
	public Operator getOperator(final Authentication authentication) {
		Object principal = authentication.getPrincipal();
		
		if (principal instanceof String)
			return PegawaiModel.createGuest();
		return ((CustomUser)principal).getOperator();
	}
	
	/**
	 * Returns user in {@code Pegawai} type, from {@code Authentication}.
	 * @param authentication
	 * @return user in {@code Pegawai} type.
	 */
	public Pegawai getPegawai(final Authentication authentication) {
		return (Pegawai)getOperator(authentication);
	}
	
	/**
	 * Returns user's company from {@code Authentication}.
	 * @param authentication
	 * @return user's company.
	 * @throws ApplicationException 
	 */
	public Perusahaan getPerusahaan(final Authentication authentication) throws UnauthenticatedAccessException {
		try {
			return getOperator(authentication).getPerusahaan().toEntity();
		} catch(NullPointerException e) {
			throw new UnauthenticatedAccessException(e.getMessage());
		}
	}

}
