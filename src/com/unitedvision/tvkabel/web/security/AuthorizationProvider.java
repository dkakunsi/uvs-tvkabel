package com.unitedvision.tvkabel.web.security;

import org.springframework.stereotype.Component;

import com.unitedvision.tvkabel.core.domain.Kredensi;
import com.unitedvision.tvkabel.core.domain.Kredensi.Role;
import com.unitedvision.tvkabel.core.domain.Pegawai.Status;
import com.unitedvision.tvkabel.core.domain.Operator;
import com.unitedvision.tvkabel.exception.EmptyCodeException;
import com.unitedvision.tvkabel.persistence.entity.PegawaiEntity;
import com.unitedvision.tvkabel.persistence.entity.PegawaiEntity.KredensiValue;

/**
 * Class that provides authorization mechanism.
 * 
 * @author Deddy Christoper Kakunsi
 *
 */
@Component
public class AuthorizationProvider {
	
	/**
	 * Returns user's role depend on {@code Operator}.<br />
	 * User's role was defined in {@code Role} class.
	 * @param operator
	 * @return User's {@code Role}.
	 */
	public Role getUserRole(final Operator operator) {
		if (operator == null)
			return Role.GUEST;
		return operator.getRole();
	}

	/**
	 * Returns user's role in string format.
	 * @param role
	 * @return User's role in string format.
	 */
	public String getUserRoleStr(final Role role) {
		if (role == Role.ADMIN) {
			return "admin";
		} else if (role == Role.OWNER) {
			return "owner";
		} else if (role == Role.OPERATOR) {
			return "operator";
		} else {
			return "guest";
		}
	}

	/**
	 * Returns user's role in string format, based on {@code Operator}.
	 * @param operator
	 * @return user's role in string format.
	 */
	public String getUserRoleStr(final Operator operator) {
		return getUserRoleStr(getUserRole(operator));
	}
	
	/**
	 * Creates GUEST user.
	 * @return GUEST user.
	 */
	protected Operator createGuest() {
		Kredensi kredensi = new KredensiValue(false, "", "", Role.GUEST);

		try {
			return new PegawaiEntity("GUEST", null, "GUEST", kredensi.toEntity(), Status.AKTIF);
		} catch (EmptyCodeException e) {
			return null;
		}
	}
}
