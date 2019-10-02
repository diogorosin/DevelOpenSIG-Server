package br.com.developen.sig.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.json.JSONObject;
import org.json.JSONTokener;

import br.com.developen.sig.exception.RecaptchaValidationErrorException;


public class Recaptcha {

	
    private static final String SITE_VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";

    private static final String SECRET_PARAM = "secret";

    private static final String RESPONSE_PARAM = "response";


    public void verify(String token, String action) throws RecaptchaValidationErrorException, IOException {

    	JSONObject jsonObject = performRecaptchaSiteVerify(token);	

    	if (!jsonObject.getBoolean("success") ||
    			!jsonObject.getString("action").equals(action) || 
    			jsonObject.getDouble("score") < 0.5d)
    		
    		throw new RecaptchaValidationErrorException();

    }


    private JSONObject performRecaptchaSiteVerify(String recaptchaResponseToken) throws IOException {

        URL url = new URL(SITE_VERIFY_URL);

        StringBuilder postData = new StringBuilder();

        addParam(postData, SECRET_PARAM, Constants.RECAPTCHA_SECRET);
        
        addParam(postData, RESPONSE_PARAM, recaptchaResponseToken);
        
        return postAndParseJSON(url, postData.toString());

    }


    private JSONObject postAndParseJSON(URL url, String postData) throws IOException {
    	
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        
        urlConnection.setDoOutput(true);
        
        urlConnection.setRequestMethod("POST");
        
        urlConnection.setRequestProperty(
                "Content-Type", "application/x-www-form-urlencoded");
        
        urlConnection.setRequestProperty(
                "charset", StandardCharsets.UTF_8.displayName());
        
        urlConnection.setRequestProperty(
                "Content-Length", Integer.toString(postData.length()));
        
        urlConnection.setUseCaches(false);
        
        urlConnection.getOutputStream()
                .write(postData.getBytes(StandardCharsets.UTF_8));
        
        JSONTokener jsonTokener = new JSONTokener(urlConnection.getInputStream());
        
        return new JSONObject(jsonTokener);

    }


    private StringBuilder addParam(StringBuilder postData, String param, String value)
            throws UnsupportedEncodingException {
    	
        if (postData.length() != 0)
        	
            postData.append("&");

        return postData.append(
                String.format("%s=%s",
                        URLEncoder.encode(param, StandardCharsets.UTF_8.displayName()),
                        URLEncoder.encode(value, StandardCharsets.UTF_8.displayName())));

    }	

}