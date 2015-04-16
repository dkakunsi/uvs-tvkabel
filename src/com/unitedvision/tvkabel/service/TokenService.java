package com.unitedvision.tvkabel.service;

import com.unitedvision.tvkabel.entity.Pegawai;
import com.unitedvision.tvkabel.entity.Token;
import com.unitedvision.tvkabel.exception.ApplicationException;
import com.unitedvision.tvkabel.exception.EntityNotExistException;
import com.unitedvision.tvkabel.exception.UnauthenticatedAccessException;

public interface TokenService {
	Token create(Pegawai pegawai) throws ApplicationException;
	Token get(String token) throws EntityNotExistException, UnauthenticatedAccessException;
	void lock(String tokenString) throws ApplicationException;
	void lock(Token token) throws ApplicationException;
}
