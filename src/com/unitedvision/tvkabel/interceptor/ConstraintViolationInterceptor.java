package com.unitedvision.tvkabel.interceptor;

import javax.persistence.PersistenceException;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ConstraintViolationInterceptor {

	@AfterThrowing(
		pointcut = "execution(public * com.unitedvision.tvkabel.persistence.repository.*.save(..))",
		throwing = "ex")
	public void errorThrown(PersistenceException ex) throws PersistenceException {
		final Throwable throwable = ex.getCause();
		
		if (throwable instanceof ConstraintViolationException) {
			final String message = throwable.getMessage();

			if (message.contains("username")) {
				throw new PersistenceException("Username yang anda masukkan sudah digunakan.");
			} else if (message.contains("email")) {
				throw new PersistenceException("Email yang anda masukkan sudah digunakan.");
			} else if (message.contains("kode")) {
				throw new PersistenceException("Kode yang anda masukkan sudah digunakan.");
			} else if (message.contains("nomor_buku")) {
				throw new PersistenceException("Nomor Buku yang anda masukkan sudah digunakan.");
			} else if (message.contains("nama")) {
				throw new PersistenceException("Nama yang anda masukkan sudah digunakan.");
			}
		}
	}
}
