package com.unitedvision.tvkabel.web.controller;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.unitedvision.tvkabel.exception.ApplicationException;
import com.unitedvision.tvkabel.util.CodeUtil;
import com.unitedvision.tvkabel.web.rest.RestResult;

@Controller
public class PageController extends AbstractController {
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String showHome() {
		return "redirect:/admin";
	}	
	
	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String showHomeAdmin(@RequestParam(required = false) String message, Map<String, Object> model) {
		return "cover_contact";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "cover_login";
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		if (auth != null)
			new SecurityContextLogoutHandler().logout(request, response, auth);

		try {
			clearBasicAuthentication();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return "redirect:/";
	}
	
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
	
	@RequestMapping(value = "/admin/tunggakan/recount/{kode}", method = RequestMethod.GET)
	public @ResponseBody RestResult recountTunggakan(@PathVariable String kode, Map<String, Object> model) {
		String message;
		try {
			if (!kode.equals(CodeUtil.getKode())) {
				message = "Gagal! Anda tidak punya otoritas!";
			} else {
				pelangganService.recountTunggakan();
				message = "Berhasil!";
			}
		} catch (ApplicationException e) {
			message = String.format("Gagal! %s", e.getMessage());
		}

		return RestResult.create(message);
	}
	
	@RequestMapping(value = "/admin/tunggakan/recount/{kode}/{tanggal}", method = RequestMethod.GET)
	public @ResponseBody RestResult recountTunggakanWithTanggal(@PathVariable String kode, @PathVariable Integer tanggal, Map<String, Object> model) {
		String message;
		try {
			if (!kode.equals(CodeUtil.getKode())) {
				message = "Gagal! Anda tidak memiliki otoritas!";
			} else {
				pelangganService.recountTunggakan(tanggal);
				message = "Berhasil!";
			}
		} catch (ApplicationException e) {
			message = String.format("Gagal! %s", e.getMessage());
		}

		return RestResult.create(message);
	}
}
