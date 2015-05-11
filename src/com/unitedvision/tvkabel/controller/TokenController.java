package com.unitedvision.tvkabel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.unitedvision.tvkabel.entity.Token;
import com.unitedvision.tvkabel.exception.ApplicationException;
import com.unitedvision.tvkabel.service.TokenService;
import com.unitedvision.tvkabel.util.EntityRestMessage;
import com.unitedvision.tvkabel.util.RestMessage;

@Controller
@RequestMapping("/token")
public class TokenController extends AbstractController {
	
	@Autowired
	private TokenService tokenService;
	
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody RestMessage login() throws ApplicationException {
		Token token = tokenService.create(getPegawai());
		return EntityRestMessage.create(token);
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	public @ResponseBody RestMessage logout(@RequestBody Token token) throws ApplicationException {
		tokenService.lock(token);
		return RestMessage.success();
	}
}
