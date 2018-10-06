package br.com.developen.sig.bean;

public class ExceptionBean001 {

	private String[] messages;

	public ExceptionBean001(){
		
		this.messages = new String[]{};

	}

	public ExceptionBean001(String message){
		
		this.messages = new String[]{ message };

	}

	public String[] getMessages() {

		return this.messages;

	}

	public void setMessages(String[] messages) {

		this.messages = messages;

	}

}