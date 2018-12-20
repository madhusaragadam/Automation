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

public class TestCase2 extends BaseTest {

	public TestCase2() throws IOException, InvalidInputException {
		super(Tenants.MULTI);
	}

}
