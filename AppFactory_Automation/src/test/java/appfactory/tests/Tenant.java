package appfactory.tests;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.client.ClientProtocolException;

import appfactory.jenkins.BuildStatus;

public interface Tenant {

	
	public void updateParameters(HashMap<String, String> parameters);
	
	public BuildStatus[] executeBuild() throws ClientProtocolException, IOException, InterruptedException;
	
}
