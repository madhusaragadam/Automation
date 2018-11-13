package appfactory.auth;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import appfactory.Constants.AuthConstants;
import appfactory.Constants.JenkinsConstants;
import appfactory.utils.RestHelper;

public class Jwt implements Authenticator {

	static String url;
	
	static String username;
	static String password;
	
	public String getToken(String username, String password)  {
		
		String url = AuthConstants.auth_url;
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("userid", username);
		params.put("password", password);
		String response = null;
		try {
			response = RestHelper.getResponseContent(RestHelper.executePostRequest(url, params, null, null));
		} catch (UnsupportedOperationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		JsonObject convertedObject = new Gson().fromJson(response, JsonObject.class);
		JsonObject map = (JsonObject) convertedObject.get("claims_token");
		return map.get("value").toString().replaceAll("^\"|\"$", "");
	}

	@Override
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
