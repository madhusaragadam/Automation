package appfactory.multitenant.android;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.client.ClientProtocolException;
import org.testng.annotations.Test;

import appfactory.exception.InvalidInputException;
import appfactory.jenkins.BuildStatus;
import appfactory.multitenant.BaseTest;
import junit.framework.Assert;

public class TestCase1 extends BaseTest {

	public TestCase1() throws IOException, InvalidInputException {
		super();
	}
	
	@Test
	public void testCase() throws ClientProtocolException, IOException, InterruptedException {
		
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("ANDROID_MOBILE_NATIVE", "true");
		Assert.assertEquals(testBuild(params), BuildStatus.SUCCESS);
	}
	
}
