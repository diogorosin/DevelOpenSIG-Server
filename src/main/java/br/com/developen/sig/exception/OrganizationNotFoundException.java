package br.com.developen.sig.exception;

import br.com.developen.sig.util.I18N;

public class OrganizationNotFoundException extends NotFoundException{

	private static final long serialVersionUID = 1L;

	public OrganizationNotFoundException(){

		super(I18N.get(I18N.ORGANIZATION_NOT_FOUND));

	}

}
