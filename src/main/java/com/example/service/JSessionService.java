package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.JSession;
import com.example.repository.JSessionRepository;

@Service
public class JSessionService {

	private JSessionRepository jsessionRepository;
	
	@Autowired
	public JSessionService(JSessionRepository jsessionRepository) {
		this.jsessionRepository = jsessionRepository;
	}
	
	public JSession findById(Long id) {
		return jsessionRepository.findById(id);
	}
	
	public JSession save(JSession jsession) {
		return jsessionRepository.save(jsession);
	}
	
	public JSession findLastJSession() {
		return jsessionRepository.findFirstByOrderByIdDesc();
	}
	
}
