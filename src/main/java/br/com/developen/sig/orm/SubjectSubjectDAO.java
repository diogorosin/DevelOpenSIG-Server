package br.com.developen.sig.orm;


import java.util.List;

import org.hibernate.Session;

public class SubjectSubjectDAO extends DAO<SubjectSubject, SubjectSubjectPK>{

	public SubjectSubjectDAO(Session session) {

		super(session, SubjectSubject.class);

	}
	
	public List<SubjectSubject> getParentsOfChild(Subject child){

		List<SubjectSubject> list = getSession().
				createNamedQuery(SubjectSubject.PARENTS_OF_CHILD, SubjectSubject.class).
				setParameter("child", child).
				getResultList(); 

		return list; 

	}

}