package br.com.developen.sig.orm;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;


@Entity
@Table(name="\"ModifiedAddressEdification\"")
public class ModifiedAddressEdification implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ModifiedAddressEdificationPK identifier;

	@ManyToOne(optional=false)
	@JoinColumn(name = "\"type\"")
	private Type type;

	@Size(min=0, max=10)
	@Column(name="\"reference\"", nullable = true)	
	private String reference;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="\"from\"", nullable = true)
	private Date from;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="\"to\"", nullable = true)
	private Date to;

	@OneToMany(
			fetch=FetchType.LAZY,
			mappedBy="identifier.modifiedAddressEdification",
			cascade={CascadeType.ALL}, 
			orphanRemoval=true)
	private List<ModifiedAddressEdificationDweller> dwellers;

	public ModifiedAddressEdificationPK getIdentifier() {
		
		return identifier;
		
	}

	public void setIdentifier(ModifiedAddressEdificationPK identifier) {

		this.identifier = identifier;

	}

	public Type getType() {

		return type;

	}

	public void setType(Type type) {

		this.type = type;

	}

	public String getReference() {

		return reference;

	}

	public void setReference(String reference) {

		this.reference = reference;

	}

	public Date getFrom() {
		
		return from;
		
	}

	public void setFrom(Date from) {
		
		this.from = from;
		
	}

	public Date getTo() {
		
		return to;
		
	}

	public void setTo(Date to) {

		this.to = to;

	}

	public List<ModifiedAddressEdificationDweller> getDwellers() {

		return dwellers;

	}

	public void setDwellers(List<ModifiedAddressEdificationDweller> dwellers) {

		this.dwellers = dwellers;

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
		ModifiedAddressEdification other = (ModifiedAddressEdification) obj;
		if (identifier == null) {
			if (other.identifier != null)
				return false;
		} else if (!identifier.equals(other.identifier))
			return false;
		return true;

	}

}