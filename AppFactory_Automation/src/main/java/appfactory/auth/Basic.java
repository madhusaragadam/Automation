package appfactory.auth;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.client.methods.HttpRequestBase;

import appfactory.Constants.JenkinsConstants;

/**
 * This class is responsible for doing a basic auth
 *
 */
public class Basic implements Authenticator {

	@Override
	public HttpRequestBase modifyRequest(HttpRequestBase request) {
		
		String url = request.getURI().getScheme()+"://"+JenkinsConstants.jenkinsUsername+":"+JenkinsConstants.jenkinsPassword;		
		url += request.getURI().getAuthority()+request.getURI().getPath();
		try {
			request.setURI(new URI(url));
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return request;
	}
}
