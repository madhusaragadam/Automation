package appfactory.multitenant;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.testng.Assert;
import org.testng.annotations.Test;

import appfactory.exception.InvalidInputException;
import appfactory.jenkins.BuildStatus;
import appfactory.tests.MultiTenant;
import appfactory.utils.ResultsValidator;

public class MultiTenantTest1 extends MultiTenant {

	public MultiTenantTest1() throws IOException, InvalidInputException {
		super();
	}
	
	@Test
	public void testErrorCase() throws ClientProtocolException, IOException, InterruptedException {
		Assert.assertEquals(ResultsValidator.validateResult(super.executeBuild(), BuildStatus.FAILED), true);
	}
}
