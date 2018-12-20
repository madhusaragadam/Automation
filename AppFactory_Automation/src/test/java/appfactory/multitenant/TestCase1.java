package appfactory.multitenant;

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
		super(Tenants.MULTI);
	}
	
	@Test(groups = {"multi" })	
	public void testCase() throws ClientProtocolException, IOException, InterruptedException {
		
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("ANDROID_MOBILE_NATIVE", "true");
		params.put("PROJECT_SOURCE_URL", "https://s3.amazonaws.com/buildservice/Test.zip");

		Assert.assertEquals(testBuild(params), BuildStatus.SUCCESS);
	}
}
