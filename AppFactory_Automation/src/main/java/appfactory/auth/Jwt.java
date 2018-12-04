package appfactory.auth;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpRequestBase;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import appfactory.Constants.AuthConstants;
import appfactory.Constants.JenkinsConstants;
import appfactory.utils.RestHelper;

/**
 * 
 * This class implements the contract for Authentication. This uses JWT Token for authentication
 */

public class Jwt implements Authenticator {

	static String url;
	
	static String username;
	static String password;
	
	/**
	 * This method is responsible for fetching the JWT token by hitting the Auth api
	 * @param username is the credential
	 * @param password is the credential
	 * @return it returns JWT Token
	 */
	public String getToken(String username, String password)  {
		
		String url = AuthConstants.auth_url;
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("userid", username);
		params.put("password", password);
		String response = null;
		try {
			response = RestHelper.getResponseContent(RestHelper.executePostRequest(url, params, null, null));
		} catch (UnsupportedOperationException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
		JsonObject convertedObject = new Gson().fromJson(response, JsonObject.class);
		JsonObject map = (JsonObject) convertedObject.get("claims_token");
		return map.get("value").toString().replaceAll("^\"|\"$", "");
	}

	@Override
	/**
	 * This method is responsible for altering a http request in order to make it authenticated
	 * @param request is the http request which we want to modify
	 */
	public HttpRequestBase modifyRequest(HttpRequestBase request) {
		// TODO Auto-generated method stub
		
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("x-kony-authorization", getToken(JenkinsConstants.jenkinsUsername, JenkinsConstants.jenkinsPassword));
		
		if (headers != null) {
			Iterator<String> iterator = headers.keySet().iterator();
			while (iterator.hasNext()) {
				String key = iterator.next();
				request.addHeader(key, headers.get(key));
			}
		}
		return request;
	}
}
