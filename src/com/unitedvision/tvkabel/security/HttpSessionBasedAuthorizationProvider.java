package com.unitedvision.tvkabel.security;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;

import com.unitedvision.tvkabel.domain.entity.Operator;
import com.unitedvision.tvkabel.domain.entity.Pegawai;
import com.unitedvision.tvkabel.domain.entity.Perusahaan;
import com.unitedvision.tvkabel.domain.entity.Pegawai.Role;

/**
 * Class that provides authorization mechanism based on {@code HttpSession} class.
 * @author Deddy Christoper Kakunsi
 *
 */
@Component
public class HttpSessionBasedAuthorizationProvider extends AuthorizationProvider {

	/**
	 * Returns user's role from session.<br />
	 * User's role was defined in {@code Role} class.
	 * @param session
	 * @return User's {@code Role}.
	 */
	public Role getUserRole(final HttpSession session) {
		if (session == null)
			return Role.GUEST;
		return getUserRole((Operator)session.getAttribute("operator"));
	}

	/**
	 * Returns user's role in string format, based on {@code HttpSession}.
	 * @param session
	 * @return
	 */
	public String getUserRoleStr(final HttpSession session) {
		return getUserRoleStr(getUserRole(session));
	}

	/**
	 * Returns user in {@code Operator} type.
	 * @param session
	 * @return user in {@code Operator} type.
	 */
	public Operator getOperator(final HttpSession session) {
		return getPegawai(session);
	}
	
	/**
	 * Returns user in {@code Pegawai} type, from {@code HttpSession}.
	 * @param session
	 * @return user in {@code Pegawai} type.
	 */
	public Pegawai getPegawai(final HttpSession session) {
		return (Pegawai)session.getAttribute("operator");
	}
	
	/**
	 * Returns user's company from {@code Authentication}.
	 * @param session
	 * @return user's company.
	 */
	public Perusahaan getPerusahaan(final HttpSession session) {
		return getOperator(session).getPerusahaan();
	}

}
