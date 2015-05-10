package com.unitedvision.tvkabel.interceptor;

import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.unitedvision.tvkabel.util.EntityRestMessage;
import com.unitedvision.tvkabel.util.ListEntityRestMessage;
import com.unitedvision.tvkabel.util.RestMessage;

@Aspect
@Component
public class MessageConverter {
	
	@Around("execution(public com.unitedvision.tvkabel.util.RestMessage com.unitedvision.tvkabel.controller.*.save(..) throws com.unitedvision.tvkabel.exception.ApplicationException)")
	public @ResponseBody RestMessage save(ProceedingJoinPoint jointPoint) {
		try {
			return (RestMessage) jointPoint.proceed();
		} catch (Throwable e) {
			e.printStackTrace();
			System.out.println("Error : " + e);
			return RestMessage.error(new Exception(e.getMessage()));
		}
	}
	
	@Around("execution(public com.unitedvision.tvkabel.util.RestMessage com.unitedvision.tvkabel.controller.*.update*(..) throws com.unitedvision.tvkabel.exception.ApplicationException)")
	public @ResponseBody RestMessage update(ProceedingJoinPoint jointPoint) {
		return save(jointPoint);
	}

	@Around("execution(public com.unitedvision.tvkabel.util.RestMessage com.unitedvision.tvkabel.controller.*.delete(..) throws com.unitedvision.tvkabel.exception.ApplicationException)")
	public @ResponseBody RestMessage delete(ProceedingJoinPoint jointPoint) {
		return save(jointPoint);
	}

	@Around("execution(public com.unitedvision.tvkabel.util.RestMessage com.unitedvision.tvkabel.controller.*.remove(..) throws com.unitedvision.tvkabel.exception.ApplicationException)")
	public @ResponseBody RestMessage remove(ProceedingJoinPoint jointPoint) {
		return save(jointPoint);
	}
	
	@Around("execution(public com.unitedvision.tvkabel.util.RestMessage com.unitedvision.tvkabel.controller.*.set*(..) throws com.unitedvision.tvkabel.exception.ApplicationException)")
	public @ResponseBody RestMessage set(ProceedingJoinPoint jointPoint) {
		return save(jointPoint);
	}
	
	@Around("execution(public com.unitedvision.tvkabel.util.RestMessage com.unitedvision.tvkabel.controller.*.get*(..) throws com.unitedvision.tvkabel.exception.ApplicationException)")
	public @ResponseBody RestMessage get(ProceedingJoinPoint jointPoint) {
		return save(jointPoint);
	}

	@Around("execution(public com.unitedvision.tvkabel.util.EntityRestMessage com.unitedvision.tvkabel.controller.*.get*(..) throws com.unitedvision.tvkabel.exception.ApplicationException)")
	public @ResponseBody EntityRestMessage<?> getEntity(ProceedingJoinPoint jointPoint) {
		try {
			return (EntityRestMessage<?>) jointPoint.proceed();
		} catch (Throwable e) {
			return EntityRestMessage.entityError((Exception)e);
		}
	}
	
	@Around("execution(public com.unitedvision.tvkabel.util.ListEntityRestMessage com.unitedvision.tvkabel.controller.*.get*(..) throws com.unitedvision.tvkabel.exception.ApplicationException)")
	public @ResponseBody ListEntityRestMessage<?> getListEntity(ProceedingJoinPoint jointPoint) {
		try {
			return (ListEntityRestMessage<?>) jointPoint.proceed();
		} catch (Throwable e) {
			return ListEntityRestMessage.listEntityError((Exception)e);
		}
		
	}
	
	// Khusus Pelanggan Controller
	@Around("execution(public com.unitedvision.tvkabel.util.RestMessage com.unitedvision.tvkabel.controller.PelangganController.activate(..) throws com.unitedvision.tvkabel.exception.ApplicationException)")
	public @ResponseBody RestMessage activate(ProceedingJoinPoint jointPoint) {
		return save(jointPoint);
	}
	
	@Around("execution(public com.unitedvision.tvkabel.util.RestMessage com.unitedvision.tvkabel.controller.PelangganController.passivate(..) throws com.unitedvision.tvkabel.exception.ApplicationException)")
	public @ResponseBody RestMessage passivate(ProceedingJoinPoint jointPoint) {
		return save(jointPoint);
	}
	
	@Around("execution(public com.unitedvision.tvkabel.util.RestMessage com.unitedvision.tvkabel.controller.PelangganController.ban(..) throws com.unitedvision.tvkabel.exception.ApplicationException)")
	public @ResponseBody RestMessage ban(ProceedingJoinPoint jointPoint) {
		return save(jointPoint);
	}
	
	@Around("execution(public com.unitedvision.tvkabel.util.RestMessage com.unitedvision.tvkabel.controller.PelangganController.free(..) throws com.unitedvision.tvkabel.exception.ApplicationException)")
	public @ResponseBody RestMessage free(ProceedingJoinPoint jointPoint) {
		return save(jointPoint);
	}
	
	//Khusus Pembayaran Controller
	@Around("execution(public com.unitedvision.tvkabel.util.RestMessage com.unitedvision.tvkabel.controller.PembayaranController.pay(..) throws com.unitedvision.tvkabel.exception.ApplicationException)")
	public @ResponseBody RestMessage pay(ProceedingJoinPoint jointPoint) {
		return save(jointPoint);
	}

	//Khusus Perusahaan Controller
	@Around("execution(public com.unitedvision.tvkabel.util.RestMessage com.unitedvision.tvkabel.controller.PerusahaanController.registrasi(..) throws com.unitedvision.tvkabel.exception.ApplicationException)")
	public @ResponseBody RestMessage registrasi(ProceedingJoinPoint jointPoint) {
		return save(jointPoint);
	}

	//Khusus Token Controller
	@Around("execution(public com.unitedvision.tvkabel.util.RestMessage com.unitedvision.tvkabel.controller.TokenController.log*(..) throws com.unitedvision.tvkabel.exception.ApplicationException)")
	public @ResponseBody RestMessage log(ProceedingJoinPoint jointPoint) {
		return save(jointPoint);
	}

	
	// Khusus method print*
	@Around("execution(public org.springframework.web.servlet.ModelAndView com.unitedvision.tvkabel.controller.*.print*(..) throws com.unitedvision.tvkabel.exception.ApplicationException)")
	public ModelAndView print(ProceedingJoinPoint jointPoint) {
		try {
			
			return (ModelAndView) jointPoint.proceed();
			
		} catch (Throwable e) {
			
			e.printStackTrace();
			
			Map<String, Object> model = new HashMap<>();
			model.put("message", e.getMessage());

			return new ModelAndView("pdfException", model);
		}
	}
}
