package appfactory.boot;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import appfactory.Constants.AuthConstants;
import appfactory.Constants.Environments;
import appfactory.Constants.JenkinsConstants;
import appfactory.Constants.Tenants;
import appfactory.Constants.TestConstants;
import appfactory.auth.Jwt;
import appfactory.exception.InvalidInputException;


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
	 * @throws InvalidInputException 
	 */
	public void load() throws IOException, InvalidInputException {
		
		loadCommandLineArgs();
		loadAuthVariables();
		loadJenkinsVariables();
	}
	
	/**
	 * Loads command line arguments for executing test cases
	 * @throws InvalidInputException 
	 */
	public void loadCommandLineArgs() throws InvalidInputException {
		
		String environmentType = System.getProperty("env");
		String tenantType = System.getProperty("tenant");
		String projectsList = System.getProperty("projects");
		String testCases = System.getProperty("testCases");
		
		if(environmentType == null || tenantType == null || projectsList == null || testCases == null ) {
			throw new InvalidInputException("environment type, tenant type, projects list, testCases are mandatory");
		}	
		TestConstants.environment = Environments.valueOf(environmentType);
		TestConstants.tenant = Tenants.valueOf(tenantType);
		TestConstants.projectsList = projectsList;
	}
	
	/**
	 * This methods loads all the variables related to Authentication part
	 * @throws IOException
	 */
	public void loadAuthVariables() throws IOException {
		AuthConstants.auth_url = loadValue("AUTHURL");
		String authType = loadValue("AUTHTYPE");
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
		JenkinsConstants.jenkinsUrl = loadValue("URL");
		JenkinsConstants.jenkinsUsername = loadValue("USERNAME");
		JenkinsConstants.jenkinsPassword = loadValue("PASSWORD");
	}
	
	/**
	 * This function loads a specific property either from System Variables or Properties file.
	 * @param key is the key for which we need a value
	 * @return value for the specified key
	 * @throws IOException
	 */
	public String loadValue(String key) throws IOException {
		
		Properties prop = new Properties();
		String propFileName = TestConstants.environment.toString().toLowerCase()+".properties";
		InputStream inputStream;
		key = TestConstants.tenant.toString()+"_"+key;

		inputStream =new FileInputStream(propFileName);
		prop.load(inputStream);
		
		String value = System.getProperty(key);
		
		if(value != null && value.length() > 0)
			return value;
		else			
			return prop.get(key).toString();
	}
}
