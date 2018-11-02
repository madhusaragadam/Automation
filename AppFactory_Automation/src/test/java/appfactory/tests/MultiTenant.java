package appfactory.tests;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;

import org.testng.annotations.Test;

import appfactory.jenkins.*;

public class MultiTenant extends BaseTest {

	@Test
	public void triggerTestCases() throws IOException, InterruptedException {
		
		
		HashMap<String, String> parameter = new HashMap<String, String>();
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
				//System.out.print(parameters.get(index1)+" - ");
				index1++;
			}
			String queueUrl = jenkins.makeBuildCall(parameter);
			System.out.println(queueUrl);
			System.out.println(jenkins.getStatus(queueUrl));
		
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
