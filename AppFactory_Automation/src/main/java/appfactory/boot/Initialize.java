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
import appfactory.utils.FileReader;
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

		loadCommandLineArgs();
		loadTestConstants();
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
		TestConstants.json = FileReader.readFileContentIntoJson(TestConstants.testJsonFile);
		TestConstants.buildParameters = new HashMap<String, String>();
		TestConstants.buildParameters = TestConstants.tenant == Tenants.MULTI ?PropsReader.readProperties("MultiTenant") : PropsReader.readProperties("SingleTenant");
	}

	/**
	 * Loads command line arguments for executing test cases
	 * 
	 * @throws InvalidInputException
	 */
	public void loadCommandLineArgs() throws InvalidInputException {
		TestConstants.environment = Environments.DEV;
		TestConstants.tenant = Tenants.SINGLE;
		TestConstants.testJsonFile = "TestCases_Sample.json";
		TestConstants.projectURL = "https://afdev04.ci.konycloud.com/job/Hgyugu/job/Visualizer/job/Builds/job/buildVisualizerApp/";
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
	 * 
	 * @throws IOException
	 */
	public void loadJenkinsVariables() throws IOException {
		JenkinsConstants.jenkinsUrl = TestConstants.projectURL;
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
