package appfactory.jenkins;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.client.ClientProtocolException;

public interface Tenant {

	
	public void updateParameters(HashMap<String, String> parameters);
	
	public BuildStatus executeBuild(String url) throws ClientProtocolException, IOException, InterruptedException;
	
}
