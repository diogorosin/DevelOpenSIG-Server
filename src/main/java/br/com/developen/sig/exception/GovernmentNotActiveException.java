package br.com.developen.sig.exception;

import br.com.developen.sig.util.I18N;

public class GovernmentNotActiveException extends UnauthorizedException {

	private static final long serialVersionUID = 1L;

	public GovernmentNotActiveException(){

		super(I18N.get(I18N.GOVERNMENT_NOT_ACTIVE));

	}

}