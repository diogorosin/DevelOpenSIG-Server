package br.com.developen.sig.orm;


import java.util.List;

import org.hibernate.Session;

public class TypeDAO extends DAO<Type, Integer>{

	public TypeDAO(Session session) {

		super(session, Type.class);

	}

	public List<Type> list(){

		return getSession().
				createNamedQuery(Type.FIND_ALL, Type.class).
				getResultList();

	}
	
}