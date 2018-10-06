package br.com.developen.sig.exception;

import br.com.developen.sig.util.I18N;

public class UserNotFoundException extends NotFoundException{

	private static final long serialVersionUID = 1L;

	public UserNotFoundException(){

		super(I18N.get(I18N.USER_NOT_FOUND));

	}

}
