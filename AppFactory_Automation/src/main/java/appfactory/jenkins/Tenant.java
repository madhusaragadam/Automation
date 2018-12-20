package appfactory.jenkins;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.client.ClientProtocolException;

import appfactory.Constants.Tenants;

public interface Tenant {
	public void updateParameters(HashMap<String, String> parameters, Tenants tenantType);
	public BuildStatus executeBuild(String url) throws ClientProtocolException, IOException, InterruptedException;
}
