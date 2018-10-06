package br.com.developen.sig.exception;

import br.com.developen.sig.util.I18N;

public class GovernmentNotFoundException extends NotFoundException{

	private static final long serialVersionUID = 1L;

	public GovernmentNotFoundException(){

		super(I18N.get(I18N.GOVERNMENT_NOT_FOUND));

	}

}