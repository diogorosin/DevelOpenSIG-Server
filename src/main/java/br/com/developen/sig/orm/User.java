package br.com.developen.sig.orm;

import java.security.Principal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
@Table(name="\"User\"")
@PrimaryKeyJoinColumn(name="individual")
@NamedQueries({
	@NamedQuery(
			name = User.FIND_BY_LOGIN,
			query = "FROM User U WHERE U.login = :login"
	)
})
public class User extends Individual implements Principal{

	private static final long serialVersionUID = 1L;

	public static final String FIND_BY_LOGIN = "User.findByLogin";	

	@Email
	@NotNull
	@Size(min=1, max=254)
	@Column(unique=true)
	private String login;

	@NotNull
	@Size(min=64, max=64)
	private String password;

	@ManyToOne(optional=true)
	@JoinColumn(name="\"lastLoggedIn\"", nullable=true)
	private Government lastLoggedIn;

	public String getLogin() {

		return this.login;

	}

	public void setLogin(String login) {

		this.login = login;

	}

	public String getPassword() {

		return this.password;

	}

	public void setPassword(String password) {

		this.password = password;

	}

	public Government getLastLoggedIn() {
		
		return lastLoggedIn;
		
	}

	public void setLastLoggedIn(Government lastLoggedIn) {
		
		this.lastLoggedIn = lastLoggedIn;
		
	}

	public String toString(){

		return "(" + getIdentifier() + ") " + getName();

	}

}