package appfactory.boot;

import appfactory.Constants.AuthConstants;
import appfactory.Constants.JenkinsConstants;

public class Initialize {
	
	public void load() {
		loadAuthVariables();
		loadJenkinsVariables();
	}
	
	public void loadAuthVariables() {
		AuthConstants.auth_url = System.getProperty("authUrl");
	}
	
	public void loadJenkinsVariables() {
		JenkinsConstants.jenkinsUrl = System.getProperty("url");
		JenkinsConstants.jenkinsUsername = System.getProperty("username");
		JenkinsConstants.jenkinsPassword = System.getProperty("password");
	}
}
