package appfactory.multitenant;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import appfactory.Constants.TestConstants;
import appfactory.exception.InvalidInputException;
import appfactory.jenkins.Tenant;
import appfactory.tests.MultiTenant;
import appfactory.jenkins.*;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.ITest;
import junit.framework.Assert;


public class BaseTest implements ITest {
	
	JSONArray testCases;
	Tenant tenant;
	
	private int counter = 0;
	
	public BaseTest() throws IOException, InvalidInputException {
		super();
	}
	
	@BeforeClass
	public void setUp() throws IOException, InvalidInputException {
		testCases = TestConstants.json.getJSONArray("testCases");
		switch(TestConstants.tenant) {
		
		case MULTI:
			tenant = new MultiTenant();
			break;
		case SINGLE:
			//TODO change this to single tenant
			tenant = new MultiTenant();
			break;
		}
	}

	public BuildStatus testBuild(HashMap<String, String> testCaseParameters) throws ClientProtocolException, IOException, InterruptedException {
		
		String buildURL =  TestConstants.projectURL;;
		tenant.updateParameters(testCaseParameters);
		counter++;
		return tenant.executeBuild(buildURL);
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

	/**
	 * This method returns the current test case name
	 */
	@Override
	public String getTestName() {
		return "Automation 1";
	}

	
}
