package br.com.developen.sig.orm;


import java.util.List;

import org.hibernate.Session;

public class UserDAO extends DAO<User, Integer>{

	public UserDAO(Session session) {

		super(session, User.class);

	}

	public User retrieveByLogin(String login){

		List<User> list = getSession().
				createNamedQuery(User.FIND_BY_LOGIN, User.class).
				setParameter("login", login).
				setMaxResults(1).
				getResultList(); 

		return list.isEmpty() ? null : list.get(0); 

	}

}