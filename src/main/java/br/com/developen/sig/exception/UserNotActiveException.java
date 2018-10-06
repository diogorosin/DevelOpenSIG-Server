package br.com.developen.sig.exception;

import br.com.developen.sig.util.I18N;

public class UserNotActiveException extends UnauthorizedException {

	private static final long serialVersionUID = 1L;

	public UserNotActiveException(){

		super(I18N.get(I18N.USER_NOT_ACTIVE));

	}

}