package appfactory.jenkins;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import Visualizer.RestHelper;
import appfactory.auth.Jwt;


/**
 * 
 * @author Madhu Avinash
 * This class is a wrapper for all the jenkins functionalities.
 *
 */
public class Jenkins {

	String url;
	String username;
	String password;
	String jwtToken;
	HashMap<String, String> headers;

	private static JsonObject map;
	private Jenkins shared_instance = null;
	
	private Jenkins(String url, String username, String password) {
		this.url = url;
		this.username = username;
		this.password = password;
		initializeJenkinsConfig();
	}
	
	public Jenkins getInstance(String url, String username, String password) {
		
		if(shared_instance == null) {
			return new Jenkins(url, username, password);
		}
		return shared_instance;
	}

	private String fetchJwtToken() {
		try {
			return Jwt.getJWT(username, password);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public void initializeJenkinsConfig() {
		headers = new HashMap<String, String>();
		headers.put("x-kony-authorization", fetchJwtToken());
	}

	/**
	 * 
	 * @param queueUrl is the queue url that we get as a response when we trigger a build
	 * @return It returns the build url of that job, it waits till it goes from queue state to build state
	 * @throws UnsupportedOperationException
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws InterruptedException
	 */
	private String getBuildUrl(String queueUrl) throws UnsupportedOperationException, ClientProtocolException, IOException, InterruptedException {
		
		String resultUrl = queueUrl+"/api/json";
		String response = RestHelper.getResponseContent(RestHelper.makePostCall(resultUrl, null, headers));
		JsonObject convertedObject = new Gson().fromJson(response, JsonObject.class);
		map = (JsonObject) convertedObject.get("executable");
		while(map == null) {
			response = RestHelper.getResponseContent(RestHelper.makePostCall(resultUrl, null, headers));
			convertedObject = new Gson().fromJson(response, JsonObject.class);
			map = (JsonObject) convertedObject.get("executable");
			Thread.sleep(2000);
		}
		System.out.println(map.toString());
		return map.get("url").getAsString();
	}
	/**
	 * 
	 * @param queueUrl is the queue url that we get as a response when we trigger a build
	 * @return It returns the status of the build whether "SUCCESS, INPROGRESS, FAILED"
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public String getStatus(String queueUrl) throws ClientProtocolException, IOException, InterruptedException {
		
		
		String buildUrl = getBuildUrl(queueUrl);
		String response = RestHelper.getResponseContent(RestHelper.makePostCall(buildUrl+"api/json", null, headers));
		JsonObject convertedObject = new Gson().fromJson(response, JsonObject.class);
		System.out.println(convertedObject.toString());
		if(convertedObject.has("result")) {		
			if(convertedObject.get("result").toString().equals("SUCCESS")) {
				return "SUCCESS";
			}else if(convertedObject.get("result").toString().equals("null")) {
				return "IN_PROGRESS";
			}
			else {
				return "FAILED";
			}
			
		}else {
			return "IN_PROGRESS";
		}
	}
	
	/**
	 * 
	 * @param response is the http response of the jenkins build call
	 * @return it returns the queueurl of the triggered build
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	private String getQueueUrl(HttpResponse response) throws ClientProtocolException, IOException {
	
		String queueUrl = RestHelper.getHeaderValue(response,"Location");
		return queueUrl;
	}

	/**
	 * 
	 * @param parameters is the list of parameters that has to be passed for build call
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public String makeBuildCall(HashMap<String, String> parameters) throws ClientProtocolException, IOException {

		HashMap<String, String> headers = new HashMap<String, String>();
		return getQueueUrl(RestHelper.makePostCall(url+"buildWithParameters", parameters,headers));		
	}
}
