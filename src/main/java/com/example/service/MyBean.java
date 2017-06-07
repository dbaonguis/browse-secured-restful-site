package com.example.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.domain.RestContestant;

@Service
public class MyBean {
	
	private final RestTemplate restTemplate;

    public MyBean(RestTemplateBuilder restTemplateBuilder) {
    	//restTemplateBuilder.basicAuthorization(username, password); POSSIBLE WAY of authentication
        this.restTemplate = restTemplateBuilder.build();
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	
	
    public RestContestant someRestCall(Long id, String jSessionID) {
    	HttpHeaders requestHeaders = new HttpHeaders();
    	requestHeaders.add("Cookie", jSessionID);
		HttpEntity<?> requestEntity = new HttpEntity(null, requestHeaders);
		Map<String, Long> params = new HashMap<String, Long>();
		params.put("id", id);
    	ResponseEntity<RestContestant> responseContestant = this.restTemplate.exchange("http://localhost:8080/rest/contestant/{id}", HttpMethod.GET, requestEntity, RestContestant.class, params);
    	RestContestant restContestant = responseContestant.getBody();
    	
    	return restContestant;
    }
}
