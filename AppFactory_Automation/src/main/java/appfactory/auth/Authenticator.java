package appfactory.auth;

import org.apache.http.client.methods.HttpRequestBase;

/**
 * 
 * This interface defines the basic contract required for authentication
 *
 */
public interface Authenticator {
	public HttpRequestBase modifyRequest(HttpRequestBase request);
}
