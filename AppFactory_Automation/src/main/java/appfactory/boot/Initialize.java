package appfactory.boot;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import appfactory.Constants.AuthConstants;
import appfactory.Constants.JenkinsConstants;
import appfactory.auth.Authenticator;
import appfactory.auth.Jwt;

public class Initialize {
	
	public void load() throws IOException {
		loadAuthVariables();
		loadJenkinsVariables();
	}
	
	public void loadAuthVariables() throws IOException {
		AuthConstants.auth_url = loadValue("authUrl");
		String authType = loadValue("authType");
		switch(authType) {
		
		case "jwt":
				AuthConstants.auth = new Jwt();
				break;
		
		}
	}
	
	public void loadJenkinsVariables() throws IOException {
		JenkinsConstants.jenkinsUrl = loadValue("url");
		JenkinsConstants.jenkinsUsername = loadValue("username");
		JenkinsConstants.jenkinsPassword = loadValue("password");
	}
	
	public String loadValue(String key) throws IOException {
		
		Properties prop = new Properties();
		String propFileName = "common.properties";
		InputStream inputStream;

		inputStream =new FileInputStream(propFileName);
		prop.load(inputStream);
		
		String value = System.getProperty(key);
		
		if(value != null && value.length() > 0)
			return value;
		else			
			return prop.get(key).toString();
	}
}
