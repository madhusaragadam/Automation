package appfactory.tests;

import appfactory.Constants.JenkinsConstants;
import appfactory.boot.Initialize;
import appfactory.jenkins.Jenkins;

public class BaseTest {

	Jenkins jenkins;
	
	public BaseTest() {
		
		Initialize initialize = new Initialize();
		initialize.load();
		jenkins = Jenkins.getInstance(JenkinsConstants.jenkinsUrl, JenkinsConstants.jenkinsUsername, JenkinsConstants.jenkinsPassword);
	}
	
	
}
