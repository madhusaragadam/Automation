package appfactory.tests;

import java.io.IOException;

import appfactory.Constants.AuthConstants;
import appfactory.Constants.JenkinsConstants;
import appfactory.boot.Initialize;
import appfactory.exception.InvalidInputException;
import appfactory.jenkins.Jenkins;

public class BaseTest {

	Jenkins jenkins;
	
	public BaseTest() throws IOException, InvalidInputException  {
		
		Initialize obj = new Initialize();
		obj.load();
		jenkins = Jenkins.getInstance(JenkinsConstants.jenkinsUrl, JenkinsConstants.jenkinsUsername, JenkinsConstants.jenkinsPassword, AuthConstants.auth);
	}
}
