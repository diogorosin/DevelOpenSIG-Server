package br.com.developen.sig.util;

import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;

public class I18N {

	public static final String INVALID_PASSWORD = "invalidPassword";	
	public static final String INVALID_RANGE = "invalidRange";
	public static final String INVALID_TOKEN = "invalidToken";	
	public static final String EXPIRED_TOKEN = "expiredToken";
	public static final String USER_NOT_ACTIVE = "userNotActive";
	public static final String USER_NOT_FOUND = "userNotFound";
	public static final String ORGANIZATION_NOT_FOUND = "organizationNotFound";
	public static final String HTTP_AUTHORIZATION_HEADER_NOT_FOUND = "httpAuthorizationHeaderNotFound";
	public static final String RESOURCE_NOT_ALLOWED = "resourceNotAllowed";
	public static final String RECAPTCHA_VALIDATION_ERROR = "recaptchaValidationError";
	public static final String INTERNAL_SERVER_ERROR = "internalServerError";
	
	private static HashMap<Locale, ResourceBundle> languages;	

	static{

		languages = new HashMap<Locale, ResourceBundle>();

		Locale pt_BR = new Locale("pt", "BR");

		languages.put(pt_BR, ResourceBundle.getBundle("Language", pt_BR));

		Locale en_US = new Locale("en", "US");

		languages.put(en_US, ResourceBundle.getBundle("Language", en_US));

	}

	public static String get(String key){

		return languages.get(Locale.getDefault()).getString(key);

	}

	public static String get(String key, Locale locale){

		return languages.get(locale).getString(key);

	}

}