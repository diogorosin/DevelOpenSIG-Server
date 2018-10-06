package br.com.developen.sig.orm;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.cfg.NotYetImplementedException;

public abstract class DAO<T extends Serializable, I extends Serializable> {

	private Session session;

	private Class<T> valueObjectClass;

	public DAO(Session session, Class<T> valueObjectClass){

		this.setSession(session);
		
		this.setValueObjectClass(valueObjectClass);

	}

	public void create(T object){

		getSession().save(object);

	}

	public T retrieve(I identifier){

		return (T) getSession().get(this.getValueObjectClass(), identifier);

	}

	public void update(T object){

		getSession().update(object);

	}

	public void delete(T object){

		getSession().delete(object);

	}

	public void load(T object, I identifier){

		getSession().load(object, identifier);

	}

	public List<T> list(){

		throw new NotYetImplementedException();

	}

	public Session getSession() {

		return session;

	}

	public void setSession(Session session) {

		this.session = session;

	}

	public Class<T> getValueObjectClass() {

		return valueObjectClass;

	}

	public void setValueObjectClass(Class<T> valueObjectClass) {

		this.valueObjectClass = valueObjectClass;

	}

}