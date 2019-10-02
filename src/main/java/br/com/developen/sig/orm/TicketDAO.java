package br.com.developen.sig.orm;

import org.hibernate.Session;

public class TicketDAO extends DAO<Ticket, String>{

	public TicketDAO(Session session) {

		super(session, Ticket.class);

	}

}