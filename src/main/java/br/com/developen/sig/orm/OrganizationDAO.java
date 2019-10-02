package br.com.developen.sig.orm;



import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;

import br.com.developen.sig.util.StringUtils;

public class OrganizationDAO extends DAO<Organization, Integer>{

	public OrganizationDAO(Session session) {

		super(session, Organization.class);

	}

	/* COM FILTRO PELO NOME */
	public List<Organization> listByDenomination(String orderColumn, String orderDirection, String fancyName){

		CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();

		CriteriaQuery<Organization> criteriaQuery = criteriaBuilder.createQuery(Organization.class);

		Root<Organization> organization = criteriaQuery.from(Organization.class);

		Predicate predicate = criteriaBuilder.like(criteriaBuilder.function("unaccent",         
				String.class, criteriaBuilder.lower(organization.get("fancyName"))),
				"%" + StringUtils.removeAccent(fancyName) + "%");

		Order order = null;
		
		if (orderColumn.equals("denomination")) {

			order = orderDirection.equals("asc") ? 
					criteriaBuilder.asc(organization.get(orderColumn)) : 
						criteriaBuilder.desc(organization.get(orderColumn));

		} else {

			if (orderColumn.equals("fancyName")) {

				order = orderDirection.equals("asc") ? 
						criteriaBuilder.asc(organization.get(orderColumn)) : 
							criteriaBuilder.desc(organization.get(orderColumn));

			}

		}

		criteriaQuery.select(organization).where(predicate);

		if (order!=null)
			
			criteriaQuery.orderBy(order);

		TypedQuery<Organization> query = getSession().createQuery(criteriaQuery);

		return query.getResultList();

	}

	public List<Organization> listByDenomination(String orderColumn, String orderDirection, String fancyName, int firstResult, int maxResults){

		CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();

		CriteriaQuery<Organization> criteriaQuery = criteriaBuilder.createQuery(Organization.class);

		Root<Organization> organization = criteriaQuery.from(Organization.class);

		Predicate predicate = criteriaBuilder.like(criteriaBuilder.function("unaccent",         
				String.class, criteriaBuilder.lower(organization.get("fancyName"))),
				StringUtils.removeAccent(fancyName));

		Order order = null;
		
		if (orderColumn.equals("denomination")) {

			order = orderDirection.equals("asc") ? 
					criteriaBuilder.asc(organization.get(orderColumn)) : 
						criteriaBuilder.desc(organization.get(orderColumn));

		} else {

			if (orderColumn.equals("fancyName")) {

				order = orderDirection.equals("asc") ? 
						criteriaBuilder.asc(organization.get(orderColumn)) : 
							criteriaBuilder.desc(organization.get(orderColumn));

			} 

		}

		criteriaQuery.select(organization).where(predicate);

		if (order!=null)
			
			criteriaQuery.orderBy(order);

		return getSession().
				createQuery(criteriaQuery).
				setFirstResult(firstResult).
				setMaxResults(maxResults).
				getResultList();

	}

	public int getCountByDenomination(String fancyName){

		CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();

		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);

		Root<Organization> organization = criteriaQuery.from(Organization.class);

		Predicate predicate = criteriaBuilder.like(criteriaBuilder.function("unaccent",         
				String.class, criteriaBuilder.lower(organization.get("fancyName"))),
				StringUtils.removeAccent(fancyName));

		criteriaQuery.
		select(criteriaBuilder.count(organization)).
		where(predicate);

		TypedQuery<Long> query = getSession().createQuery(criteriaQuery);

		return query.getSingleResult().intValue();		

	}
	
	/* SEM FILTRO */
	public List<Organization> list(String orderColumn, String orderDirection){

		CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();

		CriteriaQuery<Organization> criteriaQuery = criteriaBuilder.createQuery(Organization.class);

		Root<Organization> organization = criteriaQuery.from(Organization.class);

		Order order = null;

		if (orderColumn.equals("denomination")) {

			order = orderDirection.equals("asc") ? 
					criteriaBuilder.asc(organization.get(orderColumn)) : 
						criteriaBuilder.desc(organization.get(orderColumn));

		} else {

			if (orderColumn.equals("fancyName")) {

				order = orderDirection.equals("asc") ? 
						criteriaBuilder.asc(organization.get(orderColumn)) : 
							criteriaBuilder.desc(organization.get(orderColumn));

			} 

		}

		criteriaQuery.select(organization);

		if (order!=null)

			criteriaQuery.orderBy(order);

		TypedQuery<Organization> query = getSession().createQuery(criteriaQuery);

		return query.getResultList();

	}
	
	public List<Organization> list(String orderColumn, String orderDirection, int firstResult, int maxResults){

		CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();

		CriteriaQuery<Organization> criteriaQuery = criteriaBuilder.createQuery(Organization.class);

		Root<Organization> organization = criteriaQuery.from(Organization.class);

		Order order = null;
		
		if (orderColumn.equals("denomination")) {

			order = orderDirection.equals("asc") ? 
					criteriaBuilder.asc(organization.get(orderColumn)) : 
						criteriaBuilder.desc(organization.get(orderColumn));

		} else {

			if (orderColumn.equals("fancyName")) {

				order = orderDirection.equals("asc") ? 
						criteriaBuilder.asc(organization.get(orderColumn)) : 
							criteriaBuilder.desc(organization.get(orderColumn));

			} 

		}

		criteriaQuery.select(organization);

		if (order!=null)
			
			criteriaQuery.orderBy(order);

		TypedQuery<Organization> query = getSession().createQuery(criteriaQuery);

		return query.
				setFirstResult(firstResult).
				setMaxResults(maxResults).
				getResultList();

	}

	public int getCount(){

		CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();

		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);

		Root<Organization> organization = criteriaQuery.from(Organization.class);

		criteriaQuery.select(criteriaBuilder.count(organization));

		TypedQuery<Long> query = getSession().createQuery(criteriaQuery);

		return query.getSingleResult().intValue();		

	}
	
}