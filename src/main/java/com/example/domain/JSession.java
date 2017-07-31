package com.example.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class JSession {

	@Id @GeneratedValue
	private Long id;
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;
	
	@NotNull
	@Column(length=200)
	@Size(max=200)
	private String code;

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return "JSession [id=" + id + ", dateCreated=" + dateCreated + ", code=" + code + "]";
	}
}
