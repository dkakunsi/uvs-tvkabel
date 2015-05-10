package com.unitedvision.tvkabel.interceptor;

import javax.persistence.PersistenceException;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ConstraintViolationInterceptor {

	@AfterThrowing(
		pointcut = "execution(public * com.unitedvision.tvkabel.repository.*.save(..))",
		throwing = "ex")
	public void errorThrown(PersistenceException ex) throws PersistenceException {
		final Throwable throwable = ex.getCause();
		
		if (throwable instanceof ConstraintViolationException) {
			final String message = throwable.getMessage();
			throw new PersistenceException(createMessage(message));
		}
	}
	

	@AfterThrowing(
		pointcut = "execution(public * com.unitedvision.tvkabel.repository.*.save(..))",
		throwing = "ex")
	public void errorThrown(DataIntegrityViolationException ex) throws PersistenceException {
		final Throwable throwable = ex.getCause();
		
		if (throwable instanceof ConstraintViolationException) {
			final String message = throwable.getMessage();
			throw new PersistenceException(createMessage(message));
		}
	}
	
	private String createMessage(String key) {
		if (key.contains("username")) {
			return "Username yang anda masukkan sudah digunakan.";
		} else if (key.contains("email")) {
			return "Email yang anda masukkan sudah digunakan.";
		} else if (key.contains("kode")) {
			return "Kode yang anda masukkan sudah digunakan.";
		} else if (key.contains("nomor")) {
			return "Nomor Buku yang anda masukkan sudah digunakan.";
		} else if (key.contains("nama")) {
			return "Nama yang anda masukkan sudah digunakan.";
		}
		
		return key;
	}
}
