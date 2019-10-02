package br.com.developen.sig.exception;

import br.com.developen.sig.util.I18N;

public class RecaptchaValidationErrorException extends UnauthorizedException{

	private static final long serialVersionUID = 1L;

	public RecaptchaValidationErrorException(){

		super(I18N.get(I18N.RECAPTCHA_VALIDATION_ERROR));

	}

}