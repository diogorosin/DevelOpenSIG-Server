package br.com.developen.sig.bean;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;

public class TokenBean001 {

	private Map<Integer, UserBean001> user;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ssZ")
	private Date expirre;

	private String note;
	
	public Map<Integer, UserBean001> getUser() {

		if (user==null)

			user = new LinkedHashMap<Integer, UserBean001>();

		return user;

	}

	public void setUser(Map<Integer, UserBean001> user) {

		this.user = user;

	}

	public Date getExpirre() {
		
		return expirre;
		
	}

	public void setExpirre(Date expirre) {

		this.expirre = expirre;

	}

	public String getNote() {

		return note;

	}

	public void setNote(String note) {

		this.note = note;

	}

}