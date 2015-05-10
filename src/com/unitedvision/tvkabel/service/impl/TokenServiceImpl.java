package com.unitedvision.tvkabel.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unitedvision.tvkabel.util.DateUtil;
import com.unitedvision.tvkabel.entity.Pegawai;
import com.unitedvision.tvkabel.entity.Token;
import com.unitedvision.tvkabel.entity.Token.Status;
import com.unitedvision.tvkabel.exception.ApplicationException;
import com.unitedvision.tvkabel.exception.EntityNotExistException;
import com.unitedvision.tvkabel.exception.UnauthenticatedAccessException;
import com.unitedvision.tvkabel.repository.TokenRepository;
import com.unitedvision.tvkabel.service.TokenService;

@Service
@Transactional(readOnly = true)
public class TokenServiceImpl implements TokenService {
	@Autowired
	private TokenRepository tokenRepository;

	@Override
	@Transactional(readOnly = false)
	public Token create(Pegawai pegawai) throws ApplicationException {
		Token token = new Token();
		token.setPegawai(pegawai);
		token.setTanggal(DateUtil.getNow());
		token.generateExpireDate();
		token.generateToken();

		return tokenRepository.save(token);
	}

	@Override
	@Transactional(readOnly = false)
	public void lock(String tokenString) throws EntityNotExistException {
		Token token = tokenRepository.findByToken(tokenString);
		lock(token);
	}
	
	@Override
	@Transactional(readOnly = false)
	public void lock(Token token) {
		token.setStatus(Status.NON_AKTIF);
		tokenRepository.save(token);
	}

	@Override
	public Token get(String tokenString) throws EntityNotExistException, UnauthenticatedAccessException {
		Token token = tokenRepository.findByToken(tokenString);
		
		if (token.getExpire().before(DateUtil.getNow()))
			throw new UnauthenticatedAccessException("Session sudah expire");
		if (token.getStatus().equals(Status.NON_AKTIF))
			throw new UnauthenticatedAccessException("Session sudah di lock");
		
		return token;
	}
}
