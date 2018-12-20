package appfactory.singletenant.buildviz;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.client.ClientProtocolException;
import org.testng.annotations.Test;

import appfactory.tests.BaseTest;
import appfactory.Constants.Tenants;
import appfactory.exception.InvalidInputException;
import appfactory.jenkins.BuildStatus;
import junit.framework.Assert;

public class TestCase1 extends BaseTest {

	public TestCase1() throws IOException, InvalidInputException {
		super(Tenants.SINGLE);
	}
	
	@Test(groups = { "android", "single" })	
	public void testCase() throws ClientProtocolException, IOException, InterruptedException {
		
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("ANDROID_MOBILE_NATIVE", "true");
		params.put("PROJECT_SOURCE_CODE_REPOSITORY_CREDENTIALS_ID", "SwathiGitHubCredentials");
		params.put("CLOUD_CREDENTIALS_ID", "Saheta");
		params.put("FABRIC_APP_CONFIG","Kitchensinkappfactlatest");
		Assert.assertEquals(testBuild(params), BuildStatus.SUCCESS);
	}
}
