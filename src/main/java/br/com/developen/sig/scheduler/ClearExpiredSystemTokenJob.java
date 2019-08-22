package br.com.developen.sig.scheduler;

import org.hibernate.Session;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import br.com.developen.sig.util.HibernateUtil;

public class ClearExpiredSystemTokenJob implements Job {

	@Override
	public void execute(final JobExecutionContext ctx) throws JobExecutionException {

		Session session = HibernateUtil.getSessionFactory().openSession();

		session.beginTransaction();

		session.createStoredProcedureQuery("ClearExpiredToken").execute();

		session.getTransaction().commit();

		session.close(); 

	}

}