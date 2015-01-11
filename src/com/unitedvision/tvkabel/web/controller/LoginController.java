package com.unitedvision.tvkabel.web.controller;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.unitedvision.tvkabel.exception.UnauthenticatedAccessException;
import com.unitedvision.tvkabel.persistence.entity.Pegawai;
import com.unitedvision.tvkabel.security.CustomAuthenticationProvider;
import com.unitedvision.tvkabel.web.rest.LoginRestRequest;
import com.unitedvision.tvkabel.web.rest.PegawaiRestResult;

@Controller
public class LoginController extends AbstractController {
	@Autowired
	private CustomAuthenticationProvider authenticationProvider;
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "cover_login";
	}

	private void persistAuthentication(Authentication authentication) {
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}
	
	@RequestMapping(value = "/api/login", method = RequestMethod.POST)
	public @ResponseBody PegawaiRestResult loginApi(@RequestBody LoginRestRequest request, HttpServletRequest req) throws UnauthenticatedAccessException {
		try {
			Authentication token = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
			Authentication authentication = authenticationProvider.authenticate(token);
			persistAuthentication(authentication);
			
			return PegawaiRestResult.create("Berhasil!", getPegawai());
		} catch (AuthenticationException e) {
			return PegawaiRestResult.create(String.format("Gagal! %s", e.getMessage()));
		}
	}
	
	@RequestMapping(value = "/api/logout", method = RequestMethod.POST)
	public @ResponseBody PegawaiRestResult logoutApi(HttpServletRequest request, HttpServletResponse response) {
		try {
			logoutProcess(request, response);
			
			return PegawaiRestResult.create("Berhasil", Pegawai.createGuest());
		} catch (IOException e) {
			return PegawaiRestResult.create(String.format("Gagal! %s", e.getMessage()));
		}
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		try {
			logoutProcess(request, response);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "redirect:/";
	}
	
	private void logoutProcess(HttpServletRequest request, HttpServletResponse response) throws IOException {
		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		if (auth != null)
			new SecurityContextLogoutHandler().logout(request, response, auth);
		//clearBasicAuthentication();
	}
	
	@SuppressWarnings("unused")
	private void clearBasicAuthentication() throws IOException {
		URL url = new URL("http://localhost:8080/tvkabel/login");
		HttpURLConnection con = (HttpURLConnection)url.openConnection();
		String params = "username=logout&password=logout";

		con.setRequestMethod("POST");
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		con.setDoOutput(true);
		
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(params);
		wr.flush();
		wr.close();
		
		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Post parameters : " + params);
		System.out.println("Response Code : " + responseCode);
 
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
 
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
 
		//print result
		System.out.println(response.toString());
	}
}
