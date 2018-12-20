package appfactory.tests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import appfactory.Constants.Tenants;
import appfactory.Constants.TestConstants;
import appfactory.boot.Initialize;
import appfactory.exception.InvalidInputException;
import appfactory.jenkins.Tenant;
import appfactory.tests.MultiTenant;
import appfactory.utils.ProjectUrlConstructor;
import appfactory.jenkins.*;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONObject;


public class BaseTest {
	
	JSONArray testCases;
	public Tenant tenant;
	public Tenants jobType;
	
	static {
		Initialize obj = new Initialize();
		try {
			obj.load();
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			System.exit(1);
		} 
	}
	
	public BaseTest(Tenants jobType) throws IOException, InvalidInputException {
		this.jobType = jobType;
		setUp();
	}
	
	
	public void setUp() throws IOException, InvalidInputException {
		switch(TestConstants.tenant) {
		case MULTI:
			tenant = new MultiTenant();
			break;
		case SINGLE:
			tenant = new MultiTenant();
			break;
		case ANDROID:
			tenant = new MultiTenant();
			break;
		default:
			break;
		}
		
	}

	public BuildStatus testBuild(HashMap<String, String> testCaseParameters) throws ClientProtocolException, IOException, InterruptedException {
		
		tenant.updateParameters(testCaseParameters, jobType);
		return tenant.executeBuild(ProjectUrlConstructor.constructUrl(TestConstants.projectName, jobType));
	}
	
	/**
	 * this method is responsible for converting a json into a map
	 * @param json
	 * @return
	 */
	public HashMap<String, String> getMapFromJson(JSONObject json) {
	
		HashMap<String, String> result = new HashMap<String, String>();
		result = new Gson().fromJson(
			    json.toString(), new TypeToken<HashMap<String, String>>() {}.getType()
			);
		
		return result;
	}
}
