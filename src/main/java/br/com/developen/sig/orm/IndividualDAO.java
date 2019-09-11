package br.com.developen.sig.orm;


import org.hibernate.Session;

public class IndividualDAO extends DAO<Individual, Integer>{

	public IndividualDAO(Session session) {

		super(session, Individual.class);

	}

}