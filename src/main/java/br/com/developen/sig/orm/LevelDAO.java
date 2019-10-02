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

public class LevelDAO extends DAO<Level, Integer>{

	public LevelDAO(Session session) {

		super(session, Level.class);

	}	

	/* COM FILTRO PELO NOME */
	public List<Level> listByDenomination(String orderColumn, String orderDirection, String denomination){

		CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();

		CriteriaQuery<Level> criteriaQuery = criteriaBuilder.createQuery(Level.class);

		Root<Level> level = criteriaQuery.from(Level.class);

		Predicate predicate = criteriaBuilder.like(criteriaBuilder.function("unaccent",         
				String.class, criteriaBuilder.lower(level.get("denomination"))),
				"%" + StringUtils.removeAccent(denomination) + "%");

		Order order = null;

		if (orderColumn.equals("identifier")) {

			order = orderDirection.equals("asc") ? 
					criteriaBuilder.asc(level.get(orderColumn)) : 
						criteriaBuilder.desc(level.get(orderColumn));

		} else {

			if (orderColumn.equals("denomination")) {

				order = orderDirection.equals("asc") ? 
						criteriaBuilder.asc(level.get(orderColumn)) : 
							criteriaBuilder.desc(level.get(orderColumn));

			} 

		}

		criteriaQuery.select(level).where(predicate);

		if (order!=null)
			
			criteriaQuery.orderBy(order);

		TypedQuery<Level> query = getSession().createQuery(criteriaQuery);

		return query.getResultList();

	}

	public List<Level> listByDenomination(String orderColumn, String orderDirection, String denomination, int firstResult, int maxResults){

		CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();

		CriteriaQuery<Level> criteriaQuery = criteriaBuilder.createQuery(Level.class);

		Root<Level> level = criteriaQuery.from(Level.class);

		Predicate predicate = criteriaBuilder.like(criteriaBuilder.function("unaccent",         
				String.class, criteriaBuilder.lower(level.get("denomination"))),
				StringUtils.removeAccent(denomination));

		Order order = null;
		
		if (orderColumn.equals("identifier")) {

			order = orderDirection.equals("asc") ? 
					criteriaBuilder.asc(level.get(orderColumn)) : 
						criteriaBuilder.desc(level.get(orderColumn));

		} else {

			if (orderColumn.equals("denomination")) {

				order = orderDirection.equals("asc") ? 
						criteriaBuilder.asc(level.get(orderColumn)) : 
							criteriaBuilder.desc(level.get(orderColumn));

			} 

		}

		criteriaQuery.select(level).where(predicate);

		if (order!=null)
			
			criteriaQuery.orderBy(order);

		return getSession().
				createQuery(criteriaQuery).
				setFirstResult(firstResult).
				setMaxResults(maxResults).
				getResultList();

	}

	public int getCountByDenomination(String denomination){

		CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();

		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);

		Root<Level> level = criteriaQuery.from(Level.class);

		Predicate predicate = criteriaBuilder.like(criteriaBuilder.function("unaccent",         
				String.class, criteriaBuilder.lower(level.get("denomination"))),
				StringUtils.removeAccent(denomination));

		criteriaQuery.
		select(criteriaBuilder.count(level)).
		where(predicate);

		TypedQuery<Long> query = getSession().createQuery(criteriaQuery);

		return query.getSingleResult().intValue();		

	}
	
	/* SEM FILTRO */
	public List<Level> list(String orderColumn, String orderDirection){

		CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();

		CriteriaQuery<Level> criteriaQuery = criteriaBuilder.createQuery(Level.class);

		Root<Level> level = criteriaQuery.from(Level.class);

		Order order = null;

		if (orderColumn.equals("identifier")) {

			order = orderDirection.equals("asc") ? 
					criteriaBuilder.asc(level.get(orderColumn)) : 
						criteriaBuilder.desc(level.get(orderColumn));

		} else {

			if (orderColumn.equals("denomination")) {

				order = orderDirection.equals("asc") ? 
						criteriaBuilder.asc(level.get(orderColumn)) : 
							criteriaBuilder.desc(level.get(orderColumn));

			} 

		}

		criteriaQuery.select(level);

		if (order!=null)

			criteriaQuery.orderBy(order);

		TypedQuery<Level> query = getSession().createQuery(criteriaQuery);

		return query.getResultList();

	}
	
	public List<Level> list(String orderColumn, String orderDirection, int firstResult, int maxResults){

		CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();

		CriteriaQuery<Level> criteriaQuery = criteriaBuilder.createQuery(Level.class);

		Root<Level> level = criteriaQuery.from(Level.class);

		Order order = null;
		
		if (orderColumn.equals("identifier")) {

			order = orderDirection.equals("asc") ? 
					criteriaBuilder.asc(level.get(orderColumn)) : 
						criteriaBuilder.desc(level.get(orderColumn));

		} else {

			if (orderColumn.equals("denomination")) {

				order = orderDirection.equals("asc") ? 
						criteriaBuilder.asc(level.get(orderColumn)) : 
							criteriaBuilder.desc(level.get(orderColumn));

			} 

		}

		criteriaQuery.select(level);

		if (order!=null)
			
			criteriaQuery.orderBy(order);

		TypedQuery<Level> query = getSession().createQuery(criteriaQuery);

		return query.
				setFirstResult(firstResult).
				setMaxResults(maxResults).
				getResultList();

	}

	public int getCount(){

		CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();

		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);

		Root<Level> level = criteriaQuery.from(Level.class);

		criteriaQuery.select(criteriaBuilder.count(level));

		TypedQuery<Long> query = getSession().createQuery(criteriaQuery);

		return query.getSingleResult().intValue();		

	}

}