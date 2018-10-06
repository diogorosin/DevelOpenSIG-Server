package br.com.developen.sig.orm;


import java.util.List;

import org.hibernate.Session;

public class GovernmentDAO extends DAO<Government, Integer>{

	public GovernmentDAO(Session session) {

		super(session, Government.class);

	}

	public List<Government> list(){

		return getSession().
				createNamedQuery(Government.FIND_ALL, Government.class).
				getResultList();

	}

	public List<Government> list(int firstResult, int maxResults){

		return getSession().
				createNamedQuery(Government.FIND_ALL, Government.class).
				setFirstResult(firstResult).
				setMaxResults(maxResults).
				getResultList();

	}

	public int getCount(){

		return ((Long) getSession().
				getNamedQuery(Government.ROW_COUNT).
				uniqueResult()).
				intValue();

	}
	
}