package br.com.developen.sig.orm;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;

public class CityDAO extends DAO<City, Integer>{


	public CityDAO(Session session) {

		super(session, City.class);

	}

	public City findByIbge(Integer ibge){

		CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();

		CriteriaQuery<City> criteriaQuery = criteriaBuilder.createQuery(City.class);

		Root<City> city = criteriaQuery.from(City.class);

		Predicate predicate = criteriaBuilder.equal(city.get("ibge"), ibge);

		criteriaQuery.select(city).where(predicate);

		TypedQuery<City> query = getSession().createQuery(criteriaQuery);

		return query.getSingleResult();

	}
	
	public City findByPostalCode(Integer postalCode){

		CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();

		CriteriaQuery<City> criteriaQuery = criteriaBuilder.createQuery(City.class);

		Root<City> city = criteriaQuery.from(City.class);

		Predicate predicate1 = criteriaBuilder.lessThanOrEqualTo(city.get("postalCodeBegin"), postalCode);

		Predicate predicate2 = criteriaBuilder.greaterThanOrEqualTo(city.get("postalCodeEnd"), postalCode);

		criteriaQuery.select(city).where(predicate1, predicate2);

		TypedQuery<City> query = getSession().createQuery(criteriaQuery);

		return query.getSingleResult();

	}

	/* COM FILTRO PELA DESCRICAO */
	public List<City> listByDenomination(String denomination){

		return getSession().
				createNamedQuery(City.FIND_BY_DENOMINATION, City.class).
				setParameter("denomination", denomination).
				getResultList();

	}

	
	public List<City> listByDenomination(String denomination, int firstResult, int maxResults){

		return getSession().
				createNamedQuery(City.FIND_BY_DENOMINATION, City.class).
				setParameter("denomination", denomination).
				setFirstResult(firstResult).
				setMaxResults(maxResults).
				getResultList();

	}

	
	public int getCountByDenomination(String denomination){

		return ((Long) getSession().
				getNamedQuery(City.COUNT_BY_DENOMINATION).
				setParameter("denomination", denomination).
				uniqueResult()).
				intValue();

	}


	/* SEM FILTRO */
	public List<City> list(){

		return getSession().
				createNamedQuery(City.FIND_ALL, City.class).
				getResultList();

	}


	public List<City> list(int firstResult, int maxResults){

		return getSession().
				createNamedQuery(City.FIND_ALL, City.class).
				setFirstResult(firstResult).
				setMaxResults(maxResults).
				getResultList();

	}


	public int getCount(){

		return ((Long) getSession().
				getNamedQuery(City.COUNT_ALL).
				uniqueResult()).
				intValue();

	}


}