package com.unitedvision.tvkabel.web.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.unitedvision.tvkabel.core.domain.Operator;
import com.unitedvision.tvkabel.persistence.entity.PerusahaanEntity;

public class CustomUser extends User {
	private static final long serialVersionUID = 1L;
	private Operator operator;

	public CustomUser(String username, String password, boolean enabled,
			boolean accountNonExpired, boolean credentialsNonExpired,
			boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired,
				accountNonLocked, authorities);
	}
	
	public CustomUser(String username, String password, Operator operator,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
		this.operator = operator;
	}

	public void setOperator(Operator operator) {
		this.operator = operator;
	}
	
	public Operator getOperator() {
		return operator;
	}
	
	public PerusahaanEntity getPerusahaan() {
		return operator.getPerusahaan().toEntity();
	}
}
