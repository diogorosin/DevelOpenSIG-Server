package br.com.developen.sig.orm;


import java.util.List;

import org.hibernate.Session;

public class AgencyDAO extends DAO<Agency, Integer>{

	public AgencyDAO(Session session) {

		super(session, Agency.class);

	}

	public List<Agency> list(){

		return getSession().
				createNamedQuery(Agency.FIND_ALL, Agency.class).
				getResultList();

	}
	
}