package br.com.developen.sig.orm;


import org.hibernate.Session;

public class SubjectDAO extends DAO<Subject, Integer>{

	public SubjectDAO(Session session) {

		super(session, Subject.class);

	}
	
}