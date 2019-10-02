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

public class UserDAO extends DAO<User, Integer>{

	public UserDAO(Session session) {

		super(session, User.class);

	}

	public User retrieveByLogin(String login){

		List<User> list = getSession().
				createNamedQuery(User.RETRIEVE_BY_LOGIN, User.class).
				setParameter("login", login).
				setMaxResults(1).
				getResultList(); 

		return list.isEmpty() ? null : list.get(0); 

	}

	public User retrieveBySecret(String secret){

		List<User> list = getSession().
				createNamedQuery(User.RETRIEVE_BY_SECRET, User.class).
				setParameter("secret", secret).
				setMaxResults(1).
				getResultList(); 

		return list.isEmpty() ? null : list.get(0); 

	}

	/* COM FILTRO PELO NOME */
	public List<User> listByDenomination(String orderColumn, String orderDirection, String name){

		CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();

		CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);

		Root<User> user = criteriaQuery.from(User.class);

		Predicate predicate = criteriaBuilder.like(criteriaBuilder.function("unaccent",         
				String.class, criteriaBuilder.lower(user.get("name"))),
				"%" + StringUtils.removeAccent(name) + "%");

		Order order = null;
		
		if (orderColumn.equals("name") || orderColumn.equals("active")) {

			order = orderDirection.equals("asc") ? 
					criteriaBuilder.asc(user.get(orderColumn)) : 
						criteriaBuilder.desc(user.get(orderColumn));

		} else {

			if (orderColumn.equals("levelDenomination")) {

				order = orderDirection.equals("asc") ? 
						criteriaBuilder.asc(user.join("level").get("denomination")) : 
							criteriaBuilder.desc(user.join("level").get("denomination"));

			} else {
				
				if (orderColumn.equals("organizationFancyName")) {

					order = orderDirection.equals("asc") ? 
							criteriaBuilder.asc(user.join("organization").get("fancyName")) : 
								criteriaBuilder.desc(user.join("organization").get("fancyName"));

				}	

			}

		}

		criteriaQuery.select(user).where(predicate);

		if (order!=null)
			
			criteriaQuery.orderBy(order);

		TypedQuery<User> query = getSession().createQuery(criteriaQuery);

		return query.getResultList();

	}

	public List<User> listByName(String orderColumn, String orderDirection, String name, int firstResult, int maxResults){

		CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();

		CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);

		Root<User> user = criteriaQuery.from(User.class);

		Predicate predicate = criteriaBuilder.like(criteriaBuilder.function("unaccent",         
				String.class, criteriaBuilder.lower(user.get("name"))),
				StringUtils.removeAccent(name));

		Order order = null;
		
		if (orderColumn.equals("name") || orderColumn.equals("active")) {

			order = orderDirection.equals("asc") ? 
					criteriaBuilder.asc(user.get(orderColumn)) : 
						criteriaBuilder.desc(user.get(orderColumn));

		} else {

			if (orderColumn.equals("levelDenomination")) {

				order = orderDirection.equals("asc") ? 
						criteriaBuilder.asc(user.join("level").get("denomination")) : 
							criteriaBuilder.desc(user.join("level").get("denomination"));

			} else {
				
				if (orderColumn.equals("organizationFancyName")) {

					order = orderDirection.equals("asc") ? 
							criteriaBuilder.asc(user.join("organization").get("fancyName")) : 
								criteriaBuilder.desc(user.join("organization").get("fancyName"));

				}	

			}

		}

		criteriaQuery.select(user).where(predicate);

		if (order!=null)
			
			criteriaQuery.orderBy(order);

		return getSession().
				createQuery(criteriaQuery).
				setFirstResult(firstResult).
				setMaxResults(maxResults).
				getResultList();

	}

	public int getCountByDenomination(String name){

		CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();

		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);

		Root<User> user = criteriaQuery.from(User.class);

		Predicate predicate = criteriaBuilder.like(criteriaBuilder.function("unaccent",         
				String.class, criteriaBuilder.lower(user.get("name"))),
				StringUtils.removeAccent(name));

		criteriaQuery.
		select(criteriaBuilder.count(user)).
		where(predicate);

		TypedQuery<Long> query = getSession().createQuery(criteriaQuery);

		return query.getSingleResult().intValue();		

	}
	
	/* SEM FILTRO */
	public List<User> list(String orderColumn, String orderDirection){

		CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();

		CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);

		Root<User> user = criteriaQuery.from(User.class);

		Order order = null;
		
		if (orderColumn.equals("name") || orderColumn.equals("active")) {

			order = orderDirection.equals("asc") ? 
					criteriaBuilder.asc(user.get(orderColumn)) : 
						criteriaBuilder.desc(user.get(orderColumn));

		} else {

			if (orderColumn.equals("levelDenomination")) {

				order = orderDirection.equals("asc") ? 
						criteriaBuilder.asc(user.join("level").get("denomination")) : 
							criteriaBuilder.desc(user.join("level").get("denomination"));

			} else {
				
				if (orderColumn.equals("organizationFancyName")) {

					order = orderDirection.equals("asc") ? 
							criteriaBuilder.asc(user.join("organization").get("fancyName")) : 
								criteriaBuilder.desc(user.join("organization").get("fancyName"));

				}	

			}

		}

		criteriaQuery.select(user);

		if (order!=null)

			criteriaQuery.orderBy(order);

		TypedQuery<User> query = getSession().createQuery(criteriaQuery);

		return query.getResultList();

	}
	
	public List<User> list(String orderColumn, String orderDirection, int firstResult, int maxResults){

		CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();

		CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);

		Root<User> user = criteriaQuery.from(User.class);

		Order order = null;
		
		if (orderColumn.equals("name") || orderColumn.equals("active")) {

			order = orderDirection.equals("asc") ? 
					criteriaBuilder.asc(user.get(orderColumn)) : 
						criteriaBuilder.desc(user.get(orderColumn));

		} else {

			if (orderColumn.equals("levelDenomination")) {

				order = orderDirection.equals("asc") ? 
						criteriaBuilder.asc(user.join("level").get("denomination")) : 
							criteriaBuilder.desc(user.join("level").get("denomination"));

			} else {
				
				if (orderColumn.equals("organizationFancyName")) {

					order = orderDirection.equals("asc") ? 
							criteriaBuilder.asc(user.join("organization").get("fancyName")) : 
								criteriaBuilder.desc(user.join("organization").get("fancyName"));

				}	

			}

		}

		criteriaQuery.select(user);

		if (order!=null)
			
			criteriaQuery.orderBy(order);

		TypedQuery<User> query = getSession().createQuery(criteriaQuery);

		return query.
				setFirstResult(firstResult).
				setMaxResults(maxResults).
				getResultList();

	}

	public int getCount(){

		CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();

		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);

		Root<User> user = criteriaQuery.from(User.class);

		criteriaQuery.select(criteriaBuilder.count(user));

		TypedQuery<Long> query = getSession().createQuery(criteriaQuery);

		return query.getSingleResult().intValue();		
		
	}

}