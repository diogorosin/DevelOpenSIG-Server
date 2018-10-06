package br.com.developen.sig.orm;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@Table(name="\"SubjectSubject\"")
@NamedQueries({
	@NamedQuery(
			name = SubjectSubject.PARENTS_OF_CHILD,
			query = "FROM SubjectSubject SS WHERE SS.identifier.child = :child"
	)
})
public class SubjectSubject implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public static final String PARENTS_OF_CHILD = "SubjectSubject.parentsOfChild";	

	@EmbeddedId
	private SubjectSubjectPK identifier;

	@Enumerated(EnumType.ORDINAL)
	private Level level;

	public SubjectSubjectPK getIdentifier() {

		return this.identifier;

	}

	public void setIdentifier(SubjectSubjectPK identifier) {

		this.identifier = identifier;

	}

	public Level getLevel() {

		return level;

	}

	public void setLevel(Level level) {

		this.level = level;

	}

	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + ((identifier == null) ? 0 : identifier.hashCode());
		return result;

	}

	public boolean equals(Object obj) {

		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SubjectSubject other = (SubjectSubject) obj;
		if (identifier == null) {
			if (other.identifier != null)
				return false;
		} else if (!identifier.equals(other.identifier))
			return false;
		return true;

	}

}