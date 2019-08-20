package br.com.developen.sig.orm;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name="\"ModifiedAddressEdification\"")
public class ModifiedAddressEdification implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ModifiedAddressEdificationPK identifier;

	@ManyToOne(optional=false)
	@JoinColumn(name = "type")
	private Type type;

	@Column(name="reference", nullable = true)	
	private String reference;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="from", nullable = false)
	private Date from;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="to", nullable = true)
	private Date to;

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