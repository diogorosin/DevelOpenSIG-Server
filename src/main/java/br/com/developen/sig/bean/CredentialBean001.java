package br.com.developen.sig.bean;


public class CredentialBean001 {

	private String login;

	private String password;

	private String recaptchaToken;

	private String recaptchaAction;

	public String getLogin() {

		return login;

	}

	public void setLogin(String login) {

		this.login = login;

	}

	public String getPassword() {

		return password;

	}

	public void setPassword(String password) {

		this.password = password;

	}

	public String getRecaptchaToken() {
		
		return recaptchaToken;
		
	}

	public void setRecaptchaToken(String recaptchaToken) {
		
		this.recaptchaToken = recaptchaToken;
		
	}

	public String getRecaptchaAction() {
		
		return recaptchaAction;
		
	}

	public void setRecaptchaAction(String recaptchaAction) {
		
		this.recaptchaAction = recaptchaAction;
		
	}

}