package appfactory.tests;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;

import org.apache.http.client.ClientProtocolException;
import org.testng.Assert;
import org.testng.annotations.Test;

import appfactory.Constants.Channels;
import appfactory.exception.InvalidInputException;
import appfactory.jenkins.*;

public class MultiTenant extends BaseTest {

	HashMap<String, String> parameter;
	String projectName;
	
	public MultiTenant(String projectName) throws IOException, InvalidInputException {
		super();
		parameter = new HashMap<String, String>();
		this.projectName = projectName;
	}

	public void loadProperties() throws IOException {
		
		Properties prop = new Properties();
		String propFileName = projectName+".properties";
		InputStream inputStream;

		inputStream =new FileInputStream(propFileName);
		prop.load(inputStream);
		
		Iterator<Object> iterator = prop.keySet().iterator();
		
		while(iterator.hasNext()) {
			String key = iterator.next().toString();
			parameter.put(key, prop.getProperty(key));
		}
	}
	
	
	public BuildStatus executeBuild(ArrayList<Channels> parameters) throws ClientProtocolException, IOException, InterruptedException {
	
		int index = 0;
		while(index < parameters.size()) {
			parameter.put(parameters.get(index).toString(), "true");
			index++;
		}
		String queueUrl = jenkins.makeBuildCall(parameter);
		BuildStatus status = jenkins.getStatus(queueUrl);
		while(status == BuildStatus.INPROGRESS) {
			Thread.sleep(10000);
			status = jenkins.getStatus(queueUrl);
		}
		return status;

	}
	
	@Test
	public void triggerTestCases() throws IOException, InterruptedException {
		
		
		
		Properties prop = new Properties();
		String propFileName = "KitchenSink.properties";
		InputStream inputStream;

		inputStream =new FileInputStream(propFileName);
		prop.load(inputStream);
		
		Iterator<Object> iterator = prop.keySet().iterator();
		
		while(iterator.hasNext()) {
			String key = iterator.next().toString();
			parameter.put(key, prop.getProperty(key));
		}
		
		ArrayList<ArrayList<String>> params = TestCases.successCases;
		int index=1;
		while(index < 2) {
		
			ArrayList<String> parameters = params.get(index);
			int index1=0;
			while(index1 < parameters.size()) {
				parameter.put(parameters.get(index1), "true");
				System.out.print(parameters.get(index1)+" - ");
				index1++;
			}
			String queueUrl = jenkins.makeBuildCall(parameter);
			System.out.println(queueUrl);
			BuildStatus status = jenkins.getStatus(queueUrl);
			while(status == BuildStatus.INPROGRESS) {
				Thread.sleep(10000);
				status = jenkins.getStatus(queueUrl);
			}
			Assert.assertEquals(status, BuildStatus.SUCCESS);
			index1=0;
			while(index1 < parameters.size()) {
				parameter.put(parameters.get(index1), "false");
				index1++;
			}
			System.out.println();
			Thread.sleep(10000);
			index++;
		}
	}
}
