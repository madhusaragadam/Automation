package appfactory.multitenant;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.client.ClientProtocolException;
import org.testng.Assert;
import org.testng.annotations.Test;

import appfactory.exception.InvalidInputException;
import appfactory.jenkins.BuildStatus;
import appfactory.tests.MultiTenant;
import appfactory.utils.ResultsValidator;

public class MultiTenantTest2 extends MultiTenant {

	public MultiTenantTest2() throws IOException, InvalidInputException {
		super();
	}
	
	@Test
	public void testErrorCase() throws ClientProtocolException, IOException, InterruptedException {

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("ANDROID_MOBILE_NATIVE", "true");
		super.updateParameters(map);
		Assert.assertEquals(ResultsValidator.validateResult(super.executeBuild(), BuildStatus.UNSTABLE), true);
	}
}
