package appfactory.tests;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;

import appfactory.Constants.AuthConstants;
import appfactory.Constants.JenkinsConstants;
import appfactory.Constants.Tenants;
import appfactory.Constants.TestConstants;
import appfactory.boot.Initialize;
import appfactory.exception.InvalidInputException;
import appfactory.jenkins.Jenkins;
import appfactory.utils.PropsReader;

public class BaseTest {

	Jenkins jenkins;
	HashMap<String, String> buildParameters;
	HashMap<String, HashMap<String, String>> projectProperties;
	String projectName;
	String projectsList;
	ArrayList<String> listOfProjects;

	public BaseTest() throws IOException, InvalidInputException  {
		
		Initialize obj = new Initialize();
		obj.load();
		jenkins = Jenkins.getInstance(JenkinsConstants.jenkinsUrl, JenkinsConstants.jenkinsUsername, JenkinsConstants.jenkinsPassword, AuthConstants.auth);
		buildParameters = new HashMap<String, String>();
		buildParameters = TestConstants.tenant == Tenants.MULTI ?PropsReader.readProperties("MultiTenant") : PropsReader.readProperties("SingleTenant");
		projectProperties = new HashMap<String, HashMap<String, String>>();
		projectsList = TestConstants.projectsList;
		projectName = TestConstants.projectName;
		listOfProjects = new ArrayList<String>();
		loadProjectProperties();
	}
	/**
	 * If list of projects are selected for execution, we extract all the parameters which are specific to each individual project
	 * and keep them in a map with key as project name
	 * 
	 * @throws IOException
	 */
	public void loadProjectProperties() throws IOException {
		
		if(projectsList == null) {
			projectProperties.put(projectName, PropsReader.readProperties(projectName));
			return;
		}
		Properties prop = new Properties();
		String propFileName = projectsList+".properties";
		InputStream inputStream;

		inputStream =new FileInputStream(propFileName);
		prop.load(inputStream);
		
		Iterator<Object> iterator = prop.keySet().iterator();
		
		while(iterator.hasNext()) {
			String key = iterator.next().toString();
			listOfProjects.add(prop.getProperty(key));
		}
		inputStream.close();
		
		int index = 0;
		
		while(index < listOfProjects.size()) {
			
			String projectName = listOfProjects.get(index);
			projectProperties.put(projectName, PropsReader.readProperties(projectName));
			index++;
		}
		
	}
}
