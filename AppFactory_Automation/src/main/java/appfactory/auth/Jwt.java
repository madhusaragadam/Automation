package appfactory.auth;

import java.io.IOException;
import java.util.HashMap;
import org.apache.http.client.ClientProtocolException;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import Visualizer.RestHelper;
import appfactory.Constants.AuthConstants;

public class Jwt {

	static String url;
	
	static String username;
	static String password;
	
	public static String getJWT(String username, String password) throws ClientProtocolException, IOException  {
		
		String url = AuthConstants.auth_url;
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("userid", username);
		params.put("password", password);
		String response = RestHelper.getResponseContent(RestHelper.makePostCall(url, params,null));	
		JsonObject convertedObject = new Gson().fromJson(response, JsonObject.class);
		JsonObject map = (JsonObject) convertedObject.get("claims_token");
		return map.get("value").toString().replaceAll("^\"|\"$", "");
	}
}
