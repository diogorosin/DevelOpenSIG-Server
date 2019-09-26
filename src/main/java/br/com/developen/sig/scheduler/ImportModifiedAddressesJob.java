package br.com.developen.sig.scheduler;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
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

	static Logger log = LogManager.getRootLogger();	

	@Override
	public void execute(final JobExecutionContext ctx) throws JobExecutionException {

		Session session = HibernateUtil.getSessionFactory().openSession();

		try {

			session.beginTransaction();

			session.createStoredProcedureCall("ImportModifiedAddress").getOutputs();

			session.getTransaction().commit();

		} catch (Exception e) {

			if (session.getTransaction().isActive())

				session.getTransaction().rollback();

			log.error(ImportModifiedAddressesJob.class.getSimpleName() + ": " + 
					e.getMessage(), 
					e.getCause());

		} finally {

			session.close();

		}

	}

}