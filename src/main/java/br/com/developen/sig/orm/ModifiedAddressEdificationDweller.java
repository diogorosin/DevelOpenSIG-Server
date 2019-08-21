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
import javax.validation.constraints.Size;


@Entity
@Table(name="\"ModifiedAddressEdificationDweller\"")
public class ModifiedAddressEdificationDweller implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ModifiedAddressEdificationDwellerPK identifier;

	@ManyToOne(optional=true)
	@JoinColumn(name="subject", nullable=true)
	private Subject subject;

	@Size(min=1, max=150)
	@Column(name="\"nameOrDenomination\"", nullable=false)
	private String nameOrDenomination;

	@Size(min=1, max=1)
	@Column(name="\"type\"", nullable=false)
	private String type;
	
	@Size(min=1, max=32)
	@Column(name="\"fancyName\"", nullable=true)
	private String fancyName;
	
	@Size(min=1, max=150)
	@Column(name="\"motherName\"", nullable=true)
	private String motherName;

	@Size(min=1, max=150)
	@Column(name="\"fatherName\"", nullable=true)
	private String fatherName;

	@Column(name="\"cpf\"")
	private Long cpf;

	@Column(name="\"rgNumber\"")
	private Long rgNumber;

	@ManyToOne(optional=true)
	@JoinColumn(name="\"rgAgency\"", nullable=true)
	private Agency rgAgency;

	@ManyToOne(optional=true)
	@JoinColumn(name="\"rgState\"", nullable=true)
	private State rgState;

	@ManyToOne(optional=true)
	@JoinColumn(name="\"birthPlace\"", nullable=true)
	private City birthPlace;

	@Temporal(TemporalType.DATE)
	@Column(name="\"birthDate\"", nullable = true)
	private Date birthDate;

	@Column(name="\"gender\"", nullable = true)
	private String gender;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="\"from\"", nullable = false)
	private Date from;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="\"to\"", nullable = true)
	private Date to;
	
	public ModifiedAddressEdificationDwellerPK getIdentifier() {

		return identifier;

	}

	public void setIdentifier(ModifiedAddressEdificationDwellerPK identifier) {

		this.identifier = identifier;

	}

	public Subject getSubject() {

		return subject;

	}

	public void setSubject(Subject subject) {

		this.subject = subject;

	}
	
	public String getNameOrDenomination() {
		
		return nameOrDenomination;
		
	}

	public void setNameOrDenomination(String nameOrDenomination) {
		
		this.nameOrDenomination = nameOrDenomination;
		
	}

	public String getType() {
		
		return type;
		
	}

	public void setType(String type) {
		
		this.type = type;
		
	}

	public String getFancyName() {
		
		return fancyName;
		
	}

	public void setFancyName(String fancyName) {
		
		this.fancyName = fancyName;
		
	}

	public String getMotherName() {
		
		return motherName;
		
	}

	public void setMotherName(String motherName) {
		
		this.motherName = motherName;
		
	}

	public String getFatherName() {
		
		return fatherName;
		
	}

	public void setFatherName(String fatherName) {
		
		this.fatherName = fatherName;
		
	}

	public Long getCpf() {
		
		return cpf;
		
	}

	public void setCpf(Long cpf) {
		
		this.cpf = cpf;
		
	}

	public Long getRgNumber() {
		
		return rgNumber;
		
	}

	public void setRgNumber(Long rgNumber) {
		
		this.rgNumber = rgNumber;
		
	}

	public Agency getRgAgency() {
		
		return rgAgency;
		
	}

	public void setRgAgency(Agency rgAgency) {
		
		this.rgAgency = rgAgency;
		
	}

	public State getRgState() {
		
		return rgState;
		
	}

	public void setRgState(State rgState) {
		
		this.rgState = rgState;
		
	}

	public City getBirthPlace() {
		
		return birthPlace;
		
	}

	public void setBirthPlace(City birthPlace) {
		
		this.birthPlace = birthPlace;
		
	}

	public Date getBirthDate() {
		
		return birthDate;
		
	}

	public void setBirthDate(Date birthDate) {
		
		this.birthDate = birthDate;
		
	}

	public String getGender() {
		
		return gender;
		
	}

	public void setGender(String gender) {
		
		this.gender = gender;

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
		ModifiedAddressEdificationDweller other = (ModifiedAddressEdificationDweller) obj;
		if (identifier == null) {
			if (other.identifier != null)
				return false;
		} else if (!identifier.equals(other.identifier))
			return false;
		return true;

	}

}