package appfactory.auth;

import javax.xml.bind.DatatypeConverter;
import org.apache.http.client.methods.HttpRequestBase;
import appfactory.Constants.JenkinsConstants;

/**
 * This class is responsible for doing a basic auth
 *
 */
public class Basic implements Authenticator {

	/**
	 * This method is responsible for modifying the request to support basic authorization
	 * @param request is the object which we are trying to execute, which had to be modified to support basic auth
	 * @return returns the modified request object
	 */
	@Override
	public HttpRequestBase modifyRequest(HttpRequestBase request) {	
	
		String authValue = JenkinsConstants.jenkinsUsername+":"+JenkinsConstants.jenkinsPassword;
        String encodedAuthString = DatatypeConverter.printBase64Binary(authValue.getBytes());
		request.addHeader("Authorization","Basic "+encodedAuthString);
		return request;
	}
}
