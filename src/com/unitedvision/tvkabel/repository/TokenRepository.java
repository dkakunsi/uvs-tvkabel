package com.unitedvision.tvkabel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unitedvision.tvkabel.entity.Token;
import com.unitedvision.tvkabel.exception.EntityNotExistException;

public interface TokenRepository extends JpaRepository<Token, String> {
	Token findByToken(String token) throws EntityNotExistException;
	
	/**
	 * Deprecated karena tidak akan diintercept.
	 */
	@Deprecated
	Token findOne(String id);
}
