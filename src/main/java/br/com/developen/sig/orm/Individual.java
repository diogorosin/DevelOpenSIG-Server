package br.com.developen.sig.orm;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
@Table(name="\"Individual\"")
@PrimaryKeyJoinColumn(name="subject") 
public class Individual extends Subject {

	private static final long serialVersionUID = 1L;

	@NotNull
	@Size(min=1, max=150)
	@Column(name="\"name\"")
	private String name;

	@NotNull
	@Size(min=1, max=150)
	@Column(name="\"motherName\"")
	private String motherName;

	@Size(min=0, max=150)
	@Column(name="\"fatherName\"")
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

	@ManyToOne(optional=false)
	@JoinColumn(name="\"birthPlace\"", nullable=false)
	private City birthPlace;

	@Temporal(TemporalType.DATE)
	@Column(name="\"birthDate\"", nullable = false)
	private Date birthDate;

	@Column(name="\"gender\"", nullable = false)
	private String gender;

	public String getName() {

		return name;

	}

	public void setName(String name) {

		this.name = name;

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

}