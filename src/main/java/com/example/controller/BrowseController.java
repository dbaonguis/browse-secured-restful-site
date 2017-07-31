package com.example.controller;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.example.domain.JSession;
import com.example.domain.RestContestant;
import com.example.service.JSessionService;
import com.example.service.MyBean;


@Controller
public class BrowseController {

	@Autowired
	private MyBean myBean;
	
	@Autowired
	private JSessionService jsessionService;
	
	@RequestMapping(path="/browse", method=RequestMethod.GET)
	public ModelAndView browse() {
		try {
			/*
			Document doc = Jsoup.connect("http://localhost:8080/login").get();
			Elements elements = doc.select("input[type='hidden'][name='_csrf']");
			String csrf = elements.get(0).attr("value");
			*/
			
			Connection.Response loginForm = Jsoup.connect("http://localhost:8080/login").method(Connection.Method.GET).execute();
			Document document = loginForm.parse();
			Elements elements = document.select("input[type='hidden'][name='_csrf']");
			String csrf = elements.get(0).attr("value");
			
			Connection.Response responseAfterLogin = Jsoup.connect("http://localhost:8080/login")
						.data("username", "admin")
						.data("password", "admin")
						.data("_csrf", csrf)
						.cookies(loginForm.cookies())
						.method(Connection.Method.POST).execute();
			
			//This is not a GREAT validation, but this will do for now..
			if ((responseAfterLogin.url().toString().equalsIgnoreCase("http://localhost:8080/")) && (responseAfterLogin.statusCode() == 200)) {
				
				//save the JSessionID to inmemory DB
				JSession jsession = new JSession();
				Calendar calendar = Calendar.getInstance();
				Date now = calendar.getTime();
				jsession.setDateCreated(now);
				jsession.setCode( responseAfterLogin.cookies().get("JSESSIONID") );
				jsessionService.save(jsession);
				
				try {
					//consume...
					//RestTemplate restTemplate = new RestTemplate();
					//RestContestant restContestant = restTemplate.getForObject("http://localhost:8080/rest/contestant/1", RestContestant.class);
					
					RestContestant restContestant = myBean.someRestCall(new Long(2), "JSESSIONID="+responseAfterLogin.cookies().get("JSESSIONID"));
				
					System.out.println("test");
				} catch (RestClientException restEx) {
					restEx.printStackTrace();
				} 
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		return new ModelAndView("browse");
	}
	
	
	@RequestMapping(path="/browse2", method=RequestMethod.GET)
	public ModelAndView browse2() {
		
		System.out.println("Last session: " + jsessionService.findLastJSession().getCode() );
		
		try {
			RestContestant restContestant = myBean.someRestCall(new Long(2), "JSESSIONID="+jsessionService.findLastJSession().getCode());
		
			System.out.println("test");
		} catch (RestClientException restEx) {
			restEx.printStackTrace();
		} 
		
		
		return new ModelAndView("browse");
	}
	
}