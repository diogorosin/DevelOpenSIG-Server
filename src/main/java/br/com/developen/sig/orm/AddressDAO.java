package br.com.developen.sig.orm;


import org.hibernate.Session;

public class AddressDAO extends DAO<Address, Integer>{

	public AddressDAO(Session session) {

		super(session, Address.class);

	}
	
}