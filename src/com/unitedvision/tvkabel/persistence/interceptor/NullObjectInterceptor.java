package com.unitedvision.tvkabel.persistence.interceptor;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.unitedvision.tvkabel.exception.EntityNotExistException;

@Aspect
@Component
public class NullObjectInterceptor {

	@AfterReturning(
		pointcut = "execution(public * com.unitedvision.tvkabel.persistence.repository.*.find*(..) throws com.unitedvision.tvkabel.exception.EntityNotExistException)",
		returning = "returnValue")
	public void nullObjectReturned(Object returnValue) throws EntityNotExistException {
		if (returnValue == null)
			throw new EntityNotExistException("Data tidak ditemukan");
	}
}
