package br.com.developen.sig.util;

import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

	public static String removeAccent(String str) {
		
	    return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");

	}
	
    public static String dateAsFolder(Date dateTime) {

    	SimpleDateFormat format = new SimpleDateFormat("ddMMyyyy");

    	return format.format(dateTime) + '/';

    }
	
	public static boolean isBrasilianMercosulPlate(String plate) {

		Pattern pattern = Pattern.compile("[A-Z]{3}[0-9][A-Z][0-9]{2}");

		Matcher matcher = pattern.matcher(plate);

		return matcher.matches();

	}	

    public static String formatDateTime(Date dateTime) {

    	SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    	return format.format(dateTime);

    }	

}