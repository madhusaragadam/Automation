package appfactory.boot;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import appfactory.Constants.AuthConstants;
import appfactory.Constants.JenkinsConstants;
import appfactory.auth.Jwt;


/**
 * 
 * This class is responsible for initializing the variables either from properties file or from system variables.
 * System variables takes precedence over Properties variables
 * 
 */
public class Initialize {
	
	/**
	 * Starting point for loading variables
	 * @throws IOException
	 */
	public void load() throws IOException {
		loadAuthVariables();
		loadJenkinsVariables();
	}
	
	/**
	 * This methods loads all the variables related to Authentication part
	 * @throws IOException
	 */
	public void loadAuthVariables() throws IOException {
		AuthConstants.auth_url = loadValue("authUrl");
		String authType = loadValue("authType");
		switch(authType) {
		
		case "jwt":
				AuthConstants.auth = new Jwt();
				break;
		
		}
	}
	
	/**
	 * This method loads all the variables related to Jenkins
	 * @throws IOException
	 */
	public void loadJenkinsVariables() throws IOException {
		JenkinsConstants.jenkinsUrl = loadValue("url");
		JenkinsConstants.jenkinsUsername = loadValue("username");
		JenkinsConstants.jenkinsPassword = loadValue("password");
	}
	
	/**
	 * This function loads a specific property either from System Variables or Properties file.
	 * @param key is the key for which we need a value
	 * @return value for the specified key
	 * @throws IOException
	 */
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
