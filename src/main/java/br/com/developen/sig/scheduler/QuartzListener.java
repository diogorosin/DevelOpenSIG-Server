package br.com.developen.sig.scheduler;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.ee.servlet.QuartzInitializerListener;
import org.quartz.impl.StdSchedulerFactory;

@WebListener
public class QuartzListener extends QuartzInitializerListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        super.contextInitialized(sce);

        ServletContext ctx = sce.getServletContext();

        StdSchedulerFactory factory = (StdSchedulerFactory) ctx.getAttribute(QUARTZ_FACTORY_KEY);

        try {

            Scheduler scheduler = factory.getScheduler();

           
            JobDetail clearExpiredSystemTokenJobDetail = JobBuilder.
            		newJob(ClearExpiredSystemTokenJob.class).
            		build();

            Trigger clearExpiredSystemTokenTrigger = TriggerBuilder.
            		newTrigger().
            		withIdentity("ClearExpiredSystemToken").
            		withSchedule(CronScheduleBuilder.cronSchedule("0 00 12 ? * *")).
            		build();

            scheduler.scheduleJob(clearExpiredSystemTokenJobDetail, clearExpiredSystemTokenTrigger);



            JobDetail importModifiedAddressesJobDetail = JobBuilder.
            		newJob(ImportModifiedAddressesJob.class).
            		build();

            Trigger importModifiedAddressesTrigger = TriggerBuilder.
            		newTrigger().
            		withIdentity("ImportModifiedAddresses").
            		withSchedule(SimpleScheduleBuilder.
            				simpleSchedule().
            				withIntervalInSeconds(15).
            				repeatForever()).
            		build();

            scheduler.scheduleJob(importModifiedAddressesJobDetail, importModifiedAddressesTrigger);


            scheduler.start();

        } catch (Exception e) {

        	e.printStackTrace();
        	
            ctx.log("There was an error scheduling the job.", e);

        }

    }

}