package br.com.developen.sig.exception;

import br.com.developen.sig.util.I18N;

public class InvalidRangeException extends Exception {

	private static final long serialVersionUID = 5911591548061355586L;

	public InvalidRangeException(){

		super(I18N.get(I18N.INVALID_RANGE));

	}

}