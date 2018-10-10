package br.com.developen.sig.orm;


import java.util.List;

import org.hibernate.Session;

public class CountryDAO extends DAO<Country, Integer>{

	public CountryDAO(Session session) {

		super(session, Country.class);

	}

	public List<Country> list(){

		return getSession().
				createNamedQuery(Country.FIND_ALL, Country.class).
				getResultList();

	}
	
}