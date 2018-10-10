package br.com.developen.sig.orm;


import java.util.List;

import org.hibernate.Session;

public class CityDAO extends DAO<City, Integer>{

	public CityDAO(Session session) {

		super(session, City.class);

	}

	public List<City> list(){

		return getSession().
				createNamedQuery(City.FIND_ALL, City.class).
				getResultList();

	}
	
}