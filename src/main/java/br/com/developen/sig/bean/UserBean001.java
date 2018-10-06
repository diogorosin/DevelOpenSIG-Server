package br.com.developen.sig.bean;


public class UserBean001 extends IndividualBean001{

	private String login;

	private String password;
	
	private Integer level;

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
	
	public Integer getLevel() {

		return level;

	}

	public void setLevel(Integer level) {

		this.level = level;

	}

}