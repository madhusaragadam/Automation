package appfactory.tests;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import org.apache.http.client.ClientProtocolException;
import appfactory.Constants.AuthConstants;
import appfactory.Constants.JenkinsConstants;
import appfactory.Constants.Tenants;
import appfactory.Constants.TestConstants;
import appfactory.exception.InvalidInputException;
import appfactory.jenkins.*;

public class MultiTenant implements Tenant {

	HashMap<String, String> testCaseParameters;
	Jenkins jenkins;
	
	public MultiTenant() throws IOException, InvalidInputException {
		super();
		testCaseParameters = new HashMap<String, String>();
		jenkins = Jenkins.getInstance(JenkinsConstants.jenkinsUrl, JenkinsConstants.jenkinsUsername, JenkinsConstants.jenkinsPassword, AuthConstants.auth);
	}
	/**
	 * This method is responsible for updating the existing parameters with individual testcase parameters
	 * @param parameters holds the current test case parameteres
	 */
	public void updateParameters(HashMap<String, String> parameters, Tenants tenantType) {
		Iterator<String> keySet = parameters.keySet().iterator();
		testCaseParameters = new HashMap<String, String>(TestConstants.buildParameters.get(tenantType.toString()));
		while (keySet.hasNext()) {
			String key = keySet.next();
			testCaseParameters.put(key, parameters.get(key));
		}
	}

	/**
	 * This method is responsible for executing the build
	 * @param url holds the project url for which we have to build
	 * @return it returns any of these responses {SUCCESS, FAILED, IN_PROGRESS}
	 */
	public BuildStatus executeBuild(String url) throws ClientProtocolException, IOException, InterruptedException {

		jenkins.url = url;
		String queueUrl = jenkins.doBuild(testCaseParameters);
		String buildUrl = jenkins.getBuildUrl(queueUrl);
		BuildStatus status = jenkins.getStatus(buildUrl);
		while (status == BuildStatus.INPROGRESS) {
			Thread.sleep(10000);
			status = jenkins.getStatus(buildUrl);
		}
		return status;
	}
}
