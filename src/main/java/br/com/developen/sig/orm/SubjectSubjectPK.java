package br.com.developen.sig.orm;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Embeddable
public class SubjectSubjectPK implements Serializable{

	private static final long serialVersionUID = 1L;

	@ManyToOne(optional=false)
	@JoinColumn(name="parent", nullable=false)
	private Subject parent;

	@ManyToOne(optional=false)
	@JoinColumn(name="child", nullable=false)
	private Subject child;

	public Subject getParent() {
		
		return this.parent;
		
	}

	public void setParent(Subject parent) {
		
		this.parent = parent;
		
	}

	public Subject getChild() {

		return this.child;

	}

	public void setChild(Subject child) {

		this.child = child;

	}

	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + ((child == null) ? 0 : child.hashCode());
		result = prime * result + ((parent == null) ? 0 : parent.hashCode());
		return result;

	}

	public boolean equals(Object obj) {

		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SubjectSubjectPK other = (SubjectSubjectPK) obj;
		if (child == null) {
			if (other.child != null)
				return false;
		} else if (!child.equals(other.child))
			return false;
		if (parent == null) {
			if (other.parent != null)
				return false;
		} else if (!parent.equals(other.parent))
			return false;
		return true;

	}

}