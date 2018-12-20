package appfactory.boot;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

import org.json.JSONException;

import appfactory.Constants.AuthConstants;
import appfactory.Constants.Environments;
import appfactory.Constants.JenkinsConstants;
import appfactory.Constants.Tenants;
import appfactory.Constants.TestConstants;
import appfactory.auth.Basic;
import appfactory.auth.Jwt;
import appfactory.exception.InvalidInputException;
import appfactory.utils.PropsReader;

/**
 * 
 * This class is responsible for initializing the variables either from
 * properties file or from system variables. System variables takes precedence
 * over Properties variables
 * 
 */
public class Initialize {

	/**
	 * Starting point for loading variables
	 * 
	 * @throws IOException
	 * @throws InvalidInputException
	 */
	public void load() throws IOException, InvalidInputException {

		loadTestConstants();
		loadCommandLineArgs();
		loadAuthVariables();
		loadJenkinsVariables();
	}

	/**
	 * Reads the test cases into memory in JSON Format
	 * 
	 * @throws JSONException
	 * @throws IOException
	 */
	public void loadTestConstants() throws JSONException, IOException {
		
		TestConstants.buildParameters = new HashMap<String, HashMap<String, String>>();
		HashMap<String, String> map = PropsReader.readProperties("MultiTenant");
		TestConstants.buildParameters.put(Tenants.MULTI.toString(), map);
		
		map = PropsReader.readProperties("SingleTenant");
		TestConstants.buildParameters.put(Tenants.SINGLE.toString(), map);
		
		map = PropsReader.readProperties("AndroidChannel");
		TestConstants.buildParameters.put(Tenants.ANDROID.toString(), map);
		
		map = PropsReader.readProperties("IosChannel");
		TestConstants.buildParameters.put(Tenants.IOS.toString(), map);
	}

	/**
	 * Loads command line arguments for executing test cases
	 * 
	 * @throws InvalidInputException
	 */
	public void loadCommandLineArgs() throws InvalidInputException {
		String environment = System.getProperty("env");
		environment = "dev";
		String tenant = System.getProperty("tenantType");
		tenant = "multi";
		String projectName = System.getProperty("projectName");
		projectName = "RetailBanking82";
		String branchName = System.getProperty("branch");
		
		//validateCommandLineArgs(environment, tenant, projectName, branchName);
		TestConstants.environment = Environments.valueOf(environment.toUpperCase());
		TestConstants.tenant = Tenants.valueOf(tenant.toUpperCase());
		TestConstants.projectName = TestConstants.tenant == Tenants.MULTI ? "CloudBuildService" :projectName;
		
		
		if(TestConstants.tenant != Tenants.MULTI && branchName != null) {
			String key = "PROJECT_SOURCE_CODE_BRANCH";
			TestConstants.buildParameters.get("SINGLE").put(key, branchName);
			TestConstants.buildParameters.get("ANDROID").put(key, branchName);
			TestConstants.buildParameters.get("IOS").put(key, branchName);
		}
	}

	/**
	 * @param branchName 
	 * @param projectName 
	 * @param tenant 
	 * @param environement 
	 * @throws InvalidInputException 
	 * 
	 */
	public void validateCommandLineArgs(String environment, String tenant, String projectName, String branchName) throws InvalidInputException {
		
		if(environment == null || tenant == null)
			throw new InvalidInputException("Please specify project name while testing for "+TestConstants.tenant);
		if(TestConstants.tenant != Tenants.MULTI && TestConstants.projectName == null) {
			throw new InvalidInputException("Please specify project name while testing for "+TestConstants.tenant);
		}
	}
	
	/**
	 * This methods loads all the variables related to Authentication part
	 * 
	 * @throws IOException
	 */
	public void loadAuthVariables() throws IOException {
		AuthConstants.auth_url = loadValue("AUTHURL");
		String authType = loadValue("AUTHTYPE");
		switch (authType) {

		case "jwt":
			AuthConstants.auth = new Jwt();
			break;

		case "basic":
			AuthConstants.auth = new Basic();
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
		JenkinsConstants.jenkinsCrumbsURL = loadValue("CRUMBSTOKEN_URL");
	}

	/**
	 * This function loads a specific property either from System Variables or
	 * Properties file.
	 * 
	 * @param key is the key for which we need a value
	 * @return value for the specified key
	 * @throws IOException
	 */
	public String loadValue(String key) throws IOException {

		Properties prop = new Properties();
		String propFileName = TestConstants.environment.toString().toLowerCase() + ".properties";
		InputStream inputStream;
		key = TestConstants.tenant.toString() + "_" + key;

		inputStream = new FileInputStream(propFileName);
		prop.load(inputStream);

		String value = System.getProperty(key);
		String returnValue;
		if (value != null && value.length() > 0)
			returnValue = value;
		else
			returnValue = prop.get(key).toString();

		inputStream.close();
		return returnValue;
	}
}
