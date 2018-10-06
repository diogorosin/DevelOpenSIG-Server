package br.com.developen.sig.util;

import javax.validation.Validation;
import javax.validation.ValidatorFactory;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;


public class HibernateUtil {

	private static SessionFactory sessionFactory;

	private static ValidatorFactory validatorFactory;

	public static SessionFactory getSessionFactory() {

		if (sessionFactory == null){

			try {

				StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder().configure().build();

				Metadata metaData = new MetadataSources(standardRegistry).getMetadataBuilder().build();

				sessionFactory = metaData.getSessionFactoryBuilder().build();

			} catch (Throwable th) {

				throw new ExceptionInInitializerError(th);

			}

		}

		return sessionFactory;

	}

	public static ValidatorFactory getValidatorFactory() {

		if (validatorFactory == null){

			validatorFactory = Validation.
					byDefaultProvider().
					configure().
					buildValidatorFactory();			

		}

		return validatorFactory;

	}

}