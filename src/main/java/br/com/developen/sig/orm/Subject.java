package br.com.developen.sig.orm;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


@Entity
@Table(name="\"Subject\"")
@Inheritance(strategy=InheritanceType.JOINED)  
public class Subject implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)	
	private Integer identifier;

	@NotNull
	private Boolean active;

	@OneToMany(
			fetch=FetchType.LAZY,
			mappedBy="identifier.child", 
			cascade={CascadeType.ALL}, 
			orphanRemoval=true)
	private List<SubjectSubject> parents;

	@OneToMany(
			fetch=FetchType.LAZY,
			mappedBy="identifier.parent", 
			cascade={CascadeType.ALL}, 
			orphanRemoval=true)
	private List<SubjectSubject> childs;

	public Integer getIdentifier() {

		return this.identifier;

	}

	public void setIdentifier(Integer identifier) {

		this.identifier = identifier;

	}

	public Boolean getActive() {

		return this.active;

	}

	public void setActive(Boolean active) {

		this.active = active;

	}

	public List<SubjectSubject> getParents() {

		return parents;

	}

	public void setParents(List<SubjectSubject> parents) {

		this.parents = parents;

	}

	public List<SubjectSubject> getChilds() {

		return childs;

	}

	public void setChilds(List<SubjectSubject> childs) {

		this.childs = childs;

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
		Subject other = (Subject) obj;
		if (identifier == null) {
			if (other.identifier != null)
				return false;
		} else if (!identifier.equals(other.identifier))
			return false;
		return true;

	}

}