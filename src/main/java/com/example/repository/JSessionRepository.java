package com.example.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.example.domain.JSession;

@Repository
public interface JSessionRepository extends CrudRepository<JSession, Long> {

	JSession findById(Long id);
	
	JSession findFirstByOrderByIdDesc();
}