package appfactory.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
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



public class RestHelper {	
	
	
	public static ArrayList<NameValuePair> getParamsFromMap(HashMap<String, String> parameters){
		
		ArrayList<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
		Iterator<String> iterator = parameters.keySet().iterator();
		while(iterator.hasNext()) {
			String key = iterator.next();
			String value = parameters.get(key);
			urlParameters.add(new BasicNameValuePair(key, value));
		}
		return urlParameters;
	}
	

	public static HttpResponse executePostRequest(String url, HashMap<String, String> parameters, HashMap<String, String> headers, Authenticator authenticator) throws ClientProtocolException, IOException {
		
		HttpPost post = new HttpPost(url);
		if(parameters!=null) {
			List<NameValuePair> urlParameters = getParamsFromMap(parameters);
			post.setEntity(new UrlEncodedFormEntity(urlParameters));
		}
		return executeRequest(post, parameters, headers, authenticator);
	}
	
	private static HttpResponse executeRequest(HttpRequestBase request, HashMap<String, String> parameters, HashMap<String, String> headers, Authenticator authenticator) throws ClientProtocolException, IOException {
		
		HttpClient client = HttpClientBuilder.create().build();
	
		if (headers != null) {
			Iterator<String> iterator = headers.keySet().iterator();

			while (iterator.hasNext()) {
				String key = iterator.next();
				request.addHeader(key, headers.get(key));
			}
		}
		if(authenticator != null) {
			request = authenticator.modifyRequest(request);
		}
		HttpResponse response = client.execute(request);
		return response;
	}
	
	
	public static HttpResponse makeGetCall(String url, HashMap<String, String> parameters, HashMap<String, String> headers, Authenticator authenticator) throws ClientProtocolException, IOException {
		
		
		HttpGet get = new HttpGet(url);
		return executeRequest(get, parameters, headers, authenticator);
	}
	
	public static String getHeaderValue(HttpResponse response, String headerKey) {
		
		List<Header> httpHeaders = Arrays.asList(response.getAllHeaders());
		for (Header header : httpHeaders) {
			if(header.getName().equals(headerKey)) {
				return header.getValue();
			}
		}
		return "";
	}
	
	public static String getResponseContent(HttpResponse response) throws UnsupportedOperationException, IOException {
		
		BufferedReader rd = new BufferedReader(
		        new InputStreamReader(response.getEntity().getContent()));
		
		if(response.getStatusLine().getStatusCode() != 200 && response.getStatusLine().getStatusCode() != 201)
			return null;
		
		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		return result.toString();
	}
	

}
