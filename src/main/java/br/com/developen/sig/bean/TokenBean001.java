package br.com.developen.sig.bean;


public class TokenBean001 {

	private String identifier;
	
	private GovernmentBean001 government;

	private Integer level;

	private UserBean001 user;

	public String getIdentifier() {

		return identifier;

	}

	public void setIdentifier(String identifier) {

		this.identifier = identifier;

	}

	public GovernmentBean001 getGovernment() {
		
		return government;
		
	}

	public void setGovernment(GovernmentBean001 government) {
		
		this.government = government;
		
	}

	public UserBean001 getUser() {
		
		return user;
		
	}

	public void setUser(UserBean001 user) {

		this.user = user;

	}

	public Integer getLevel() {

		return level;

	}

	public void setLevel(Integer level) {

		this.level = level;

	}

}