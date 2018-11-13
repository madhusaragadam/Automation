package appfactory.auth;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;

public interface Authenticator {
	public HttpRequestBase modifyRequest(HttpRequestBase request);
}
