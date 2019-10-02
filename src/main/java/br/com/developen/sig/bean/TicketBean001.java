package br.com.developen.sig.bean;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;

public class TicketBean001 {

	private Boolean active;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ssZ")
	private Date date;

	private Map<Integer, UserBean001> user;
	
	private String email;

	public Boolean getActive() {

		return active;

	}

	public void setActive(Boolean active) {

		this.active = active;

	}

	public Date getDate() {

		return date;

	}

	public void setDate(Date date) {

		this.date = date;

	}

	public Map<Integer, UserBean001> getUser() {
		
		if (this.user==null)
			
			user = new HashMap<Integer, UserBean001>();

		return user;

	}

	public void setUser(Map<Integer, UserBean001> user) {

		this.user = user;

	}

	public String getEmail() {
		
		return email;
		
	}

	public void setEmail(String email) {

		this.email = email;

	}

}