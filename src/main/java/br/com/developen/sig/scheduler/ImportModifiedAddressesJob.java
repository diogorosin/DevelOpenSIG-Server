package br.com.developen.sig.scheduler;

import org.hibernate.Session;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import br.com.developen.sig.util.HibernateUtil;

/* 
 * 
 * https://vladmihalcea.com/how-to-call-postgresql-functions-from-hibernate/
 */
public class ImportModifiedAddressesJob implements Job {

	@Override
	public void execute(final JobExecutionContext ctx) throws JobExecutionException {

		Session session = HibernateUtil.getSessionFactory().openSession();

		session.beginTransaction();

		session.createStoredProcedureCall("ImportModifiedAddress").getOutputs();

		session.getTransaction().commit();
		
		session.flush();

		session.close();

	}

}