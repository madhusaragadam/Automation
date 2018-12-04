package appfactory.jenkins;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import appfactory.Constants.AuthConstants;
import appfactory.Constants.JenkinsConstants;
import appfactory.auth.Authenticator;
import appfactory.auth.Jwt;
import appfactory.utils.RestHelper;

/**
 * 
 * This class is a wrapper for all the jenkins
 * functionalities.
 *
 */
public class Jenkins {

	public String url;
	String username;
	String password;
	String jwtToken;
	Authenticator auth;
	HashMap<String, String> header;
	
	private static Jenkins shared_instance = null;

	/**
	 * Jenkins private constructor
	 * @param url is the jenkins url
	 * @param username is the username for jenkins
	 * @param password is the password for jenkins
	 * @param auth is the Authencticator class which will be used to authenticate
	 */
	private Jenkins(String url, String username, String password, Authenticator auth) {
		this.url = url;
		this.username = username;
		this.password = password;
		this.auth = auth;
		header = new HashMap<String, String>();
		header.put("jenkins-crumb", fetchCrumbToken());
	}

	/**
	 * This function returns a csrf token
	 * @return a valid csrf token
	 */
	private String fetchCrumbToken() {
		
		String response = null;
		try {
			response = RestHelper.getResponseContent(RestHelper.executeGet(JenkinsConstants.jenkinsCrumbsURL, null, null, auth));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(response != null) {
			JsonObject convertedObject = new Gson().fromJson(response, JsonObject.class);
			return convertedObject.get("crumb").getAsString();
		}else {
			return null;
		}
	}
	
	/**
	 * This method returns the shared single instance of jenkins class 
	 * @param url is the jenkins url
	 * @param username is the username for jenkins
	 * @param password is the password for jenkins
	 * @param auth is the Authencticator class which will be used to authenticate
	 * @return it returns the single shared instance of this class
	 */
	public static Jenkins getInstance(String url, String username, String password, Authenticator auth) {

		if (shared_instance == null) {
			shared_instance = new Jenkins(url, username, password, auth);
			return shared_instance;
		}
		return shared_instance;
	}

	/**
	 * 
	 * @param queueUrl is the queue url that we get as a response when we trigger a
	 *                 build
	 * @return It returns the build url of that job, it waits till it goes from
	 *         queue state to build state
	 * @throws UnsupportedOperationException
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public String getBuildUrl(String queueUrl)
			throws UnsupportedOperationException, ClientProtocolException, IOException, InterruptedException {
		
		JsonObject map;
		String resultUrl = queueUrl + "/api/json";
		String response = RestHelper.getResponseContent(RestHelper.executePostRequest(resultUrl, null, header, auth));
		System.out.println("Response is "+response);
		JsonObject convertedObject = new Gson().fromJson(response, JsonObject.class);
		System.out.println("JSON is "+convertedObject.toString());
		map = (JsonObject) convertedObject.get("executable");
		while (map == null) {
			response = RestHelper.getResponseContent(RestHelper.executePostRequest(resultUrl, null, header, auth));
			convertedObject = new Gson().fromJson(response, JsonObject.class);
			map = (JsonObject) convertedObject.get("executable");
			Thread.sleep(2000);
		}
		return map.get("url").getAsString();
	}

	/**
	 * 
	 * @param queueUrl is the queue url that we get as a response when we trigger a
	 *                 build
	 * @return It returns the status of the build whether "SUCCESS, INPROGRESS,
	 *         FAILED"
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public BuildStatus getStatus(String buildUrl) throws ClientProtocolException, IOException, InterruptedException {

		String response = RestHelper.getResponseContent(RestHelper.executePostRequest(buildUrl + "api/json", null, header, auth));
		JsonObject convertedObject = new Gson().fromJson(response, JsonObject.class);
		if (convertedObject.has("result")) {
			if (convertedObject.get("result").toString().contains(BuildStatus.SUCCESS.toString())) {
				return BuildStatus.SUCCESS;
			} else if (convertedObject.get("result").toString().contains("null")) {
				return BuildStatus.INPROGRESS;
			}else if (convertedObject.get("result").toString().contains(BuildStatus.UNSTABLE.toString())) {
				return BuildStatus.UNSTABLE;
			} else {
				return BuildStatus.FAILED;
			}
		} else {
			return BuildStatus.INPROGRESS;
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
		String queueUrl = RestHelper.getHeaderValue(response, "Location");
		return queueUrl;
	}

	/**
	 * 
	 * @param parameters is the list of parameters that has to be passed for build
	 *                   call
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public String doBuild(HashMap<String, String> parameters) throws ClientProtocolException, IOException {
		return getQueueUrl(RestHelper.executePostRequest(url + "buildWithParameters", parameters, header, auth));
	}
}
