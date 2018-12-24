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
@Table(name="\"AddressEdificationDweller\"")
public class AddressEdificationDweller implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private AddressEdificationDwellerPK identifier;

	@ManyToOne(optional=false)
	@JoinColumn(name="subject", nullable=false)
	private Subject subject;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="from", nullable = false)
	private Date from;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="to", nullable = true)
	private Date to;

	@ManyToOne(optional=false)
	@JoinColumn(name="\"verifiedBy\"", nullable=false)
	private User verifiedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="\"verifiedAt\"", nullable = false)
	private Date verifiedAt;

	public AddressEdificationDwellerPK getIdentifier() {

		return identifier;

	}

	public void setIdentifier(AddressEdificationDwellerPK identifier) {

		this.identifier = identifier;

	}

	public Subject getSubject() {

		return subject;

	}

	public void setSubject(Subject subject) {

		this.subject = subject;

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

	public User getVerifiedBy() {
		
		return verifiedBy;
		
	}

	public void setVerifiedBy(User verifiedBy) {
		
		this.verifiedBy = verifiedBy;
		
	}

	public Date getVerifiedAt() {
		
		return verifiedAt;
		
	}

	public void setVerifiedAt(Date verifiedAt) {
		
		this.verifiedAt = verifiedAt;
		
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
		AddressEdificationDweller other = (AddressEdificationDweller) obj;
		if (identifier == null) {
			if (other.identifier != null)
				return false;
		} else if (!identifier.equals(other.identifier))
			return false;
		return true;

	}

}