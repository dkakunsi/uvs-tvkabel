package com.unitedvision.tvkabel.interceptor;

import java.util.List;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.unitedvision.tvkabel.exception.EntityNotExistException;

@Aspect
@Component
public class NullObjectInterceptor {

	@AfterReturning(
		pointcut = "execution(public * com.unitedvision.tvkabel.repository.*.find*(..) throws com.unitedvision.tvkabel.exception.EntityNotExistException)",
		returning = "returnValue")
	public void nullObjectReturned(Object returnValue) throws EntityNotExistException {
		if (returnValue == null)
			throw new EntityNotExistException("Data tidak ditemukan");
	}

	@AfterReturning(
		pointcut = "execution(public * com.unitedvision.tvkabel.service.impl.*.get*(..) throws com.unitedvision.tvkabel.exception.EntityNotExistException)",
		returning = "returnValue")
	public void nullObjectReturned2(Object returnValue) throws EntityNotExistException {
		if (returnValue == null)
			throw new EntityNotExistException("Data tidak ditemukan");
		if ((returnValue instanceof List) && (((List<?>)returnValue).size() <= 0))
			throw new EntityNotExistException("Data tidak ditemukan");
	}
}
