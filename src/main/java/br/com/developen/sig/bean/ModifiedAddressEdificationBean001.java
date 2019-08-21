package br.com.developen.sig.bean;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ModifiedAddressEdificationBean001 {

	private Integer type;

	private String reference;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private Date from;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private Date to;

	private Map<Integer, ModifiedAddressEdificationDwellerBean001> dwellers;

	public Integer getType() {

		return type;

	}

	public void setType(Integer type) {

		this.type = type;

	}

	public String getReference() {

		return reference;

	}

	public void setReference(String reference) {

		this.reference = reference;

	}
	
	public Date getFrom() {
		
		return from;
		
	}

	public void setFrom(Date from) {
		
		this.from = from;
		
	}

	public Date getTo() {

		return to;

	}

	public void setTo(Date to) {

		this.to = to;

	}

	public Map<Integer, ModifiedAddressEdificationDwellerBean001> getDwellers() {

		if (dwellers==null) 

			dwellers = new HashMap<Integer, ModifiedAddressEdificationDwellerBean001>();

		return dwellers;

	}

	public void setDwellers(Map<Integer, ModifiedAddressEdificationDwellerBean001> dwellers) {

		this.dwellers = dwellers;

	}

}