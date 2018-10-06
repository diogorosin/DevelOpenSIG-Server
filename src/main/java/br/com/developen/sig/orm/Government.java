package br.com.developen.sig.orm;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="\"Government\"")
@PrimaryKeyJoinColumn(name="organization")
@NamedQueries({
	@NamedQuery(
			name = Government.FIND_ALL, 
			query = "FROM Government G ORDER BY G.denomination"
	),
	@NamedQuery(
			name = Government.ROW_COUNT, 
			query = "SELECT COUNT(G) FROM Government G"
	)
})
public class Government extends Organization {

	private static final long serialVersionUID = 1L;

	public static final String FIND_ALL = "Government.findAll";

	public static final String ROW_COUNT = "Government.rowCount";

	public String toString(){

		return "(" + getIdentifier() + ") " + getDenomination();

	}

}