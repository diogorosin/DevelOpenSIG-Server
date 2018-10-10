package br.com.developen.sig.orm;


import java.util.List;

import org.hibernate.Session;

public class StateDAO extends DAO<State, Integer>{

	public StateDAO(Session session) {

		super(session, State.class);

	}

	public List<State> list(){

		return getSession().
				createNamedQuery(State.FIND_ALL, State.class).
				getResultList();

	}
	
}