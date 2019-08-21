package br.com.developen.sig.orm;


import org.hibernate.Session;

public class ModifiedAddressDAO extends DAO<ModifiedAddress, Integer>{

	public ModifiedAddressDAO(Session session) {

		super(session, ModifiedAddress.class);

	}

}