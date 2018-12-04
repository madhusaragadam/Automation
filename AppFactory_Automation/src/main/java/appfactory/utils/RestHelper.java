package appfactory.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

import appfactory.auth.Authenticator;


/**
 * This class is responsible for making any kind of a rest call
 *
 */
public class RestHelper {	
	
	
	/**
	 * Given a map of <key, value> parameters it will change it to ArrayList<NameValuePair> which is accepted by a http request
	 * @param parameters are the list of parameters to be added
	 * @return ArrayList of NameValuePair objects accepted by http request
	 */
	private static ArrayList<NameValuePair> getParamsFromMap(HashMap<String, String> parameters){
		
		ArrayList<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
		Iterator<String> iterator = parameters.keySet().iterator();
		while(iterator.hasNext()) {
			String key = iterator.next();
			String value = parameters.get(key);
			urlParameters.add(new BasicNameValuePair(key, value));
		}
		return urlParameters;
	}
	
	/**
	 * This method is responsible in making a post call
	 * @param url is the target url
	 * @param parameters  that has to be passed as body
	 * @param headers that has to be attached to request headers
	 * @param authenticator holds the mechanism in which we want to do authentication
	 * @return It returns httpresponse
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static HttpResponse executePostRequest(String url, HashMap<String, String> parameters, HashMap<String, String> headers, Authenticator authenticator) throws ClientProtocolException, IOException {
		
		HttpPost post = new HttpPost(url);
		if(parameters!=null) {
			List<NameValuePair> urlParameters = getParamsFromMap(parameters);
			post.setEntity(new UrlEncodedFormEntity(urlParameters));
		}
		return executeRequest(post, parameters, headers, authenticator);
	}
	
	/**
	 * This method is responsible in making a either a post or get call
	 * @param url is the target url
	 * @param parameters  that has to be passed as body
	 * @param headers that has to be attached to request headers
	 * @param authenticator holds the mechanism in which we want to do authentication
	 * @return It returns httpresponse
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	private static HttpResponse executeRequest(HttpRequestBase request, HashMap<String, String> parameters, HashMap<String, String> headers, Authenticator authenticator) throws ClientProtocolException, IOException {
		
		HttpClient client = HttpClientBuilder.create().build();
	
		if (headers != null) {
			Iterator<String> iterator = headers.keySet().iterator();

			while (iterator.hasNext()) {
				String key = iterator.next();
				if(headers.get(key) != null)
					request.addHeader(key, headers.get(key));
			}
		}
		if(authenticator != null) {
			request = authenticator.modifyRequest(request);
		}
		HttpResponse response = client.execute(request);
		return response;
	}
	
	/**
	 * This method is responsible in making a get call
	 * @param url is the target url
	 * @param parameters  that has to be passed as body
	 * @param headers that has to be attached to request headers
	 * @param authenticator holds the mechanism in which we want to do authentication
	 * @return It returns httpresponse
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static HttpResponse executeGet(String url, HashMap<String, String> parameters, HashMap<String, String> headers, Authenticator authenticator) throws ClientProtocolException, IOException {
		
		
		HttpGet get = new HttpGet(url);
		return executeRequest(get, parameters, headers, authenticator);
	}
	
	/**
	 * This function returns value for a specific header in response
	 * @param response holds response for a request
	 * @param headerKey is the key for which we need the value
	 * @return it returns the value for the header
	 */
	public static String getHeaderValue(HttpResponse response, String headerKey) {
		
		List<Header> httpHeaders = Arrays.asList(response.getAllHeaders());
		for (Header header : httpHeaders) {
			if(header.getName().equals(headerKey)) {
				return header.getValue();
			}
		}
		return "";
	}
	
	/**
	 * This returns the content for a specific request
	 * @param response holds the response object
	 * @return Content of that response object
	 * @throws UnsupportedOperationException
	 * @throws IOException
	 */
	public static String getResponseContent(HttpResponse response) throws UnsupportedOperationException, IOException {
		
		BufferedReader rd = new BufferedReader(
		        new InputStreamReader(response.getEntity().getContent()));
		
		if(response.getStatusLine().getStatusCode() != 200 && response.getStatusLine().getStatusCode() != 201) {
			System.out.println("This is the status line "+response.getStatusLine());
			return null;
		}
		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		return result.toString();
	}
	

}
