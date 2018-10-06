package br.com.developen.sig.orm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
@Table(name="\"Organization\"")
@PrimaryKeyJoinColumn(name="subject")  
public class Organization extends Subject {

	private static final long serialVersionUID = 1L;

	@NotNull
	@Size(min=1, max=100)
	@Column(name="\"denomination\"")
	private String denomination;

	@Size(min=0, max=32)
	@Column(name="\"fancyName\"")
	private String fancyName;

	public String getDenomination() {

		return this.denomination;

	}

	public void setDenomination(String denomination) {

		this.denomination = denomination;

	}

	public String getFancyName() {

		return fancyName;

	}

	public void setFancyName(String fancyName) {

		this.fancyName = fancyName;

	}

}