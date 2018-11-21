package appfactory.tests;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.http.client.ClientProtocolException;
import appfactory.exception.InvalidInputException;
import appfactory.jenkins.*;

public class MultiTenant extends BaseTest implements Tenant {

	public MultiTenant() throws IOException, InvalidInputException {
		super();
	}

	@Override
	public void updateParameters(HashMap<String, String> parameters) {
		Iterator<String> keySet = parameters.keySet().iterator();

		while (keySet.hasNext()) {
			String key = keySet.next();
			buildParameters.put(key, parameters.get(key));
		}
	}

	public void prepareBuild(HashMap<String, String> projectSpecificProperties) {
			
	}
	
	@Override
	public BuildStatus[] executeBuild() throws ClientProtocolException, IOException, InterruptedException {

		BuildStatus statusArray[] = new BuildStatus[listOfProjects.size()];
		int index = 0;
		while (index < listOfProjects.size()) {
			//Inserts project specific properties into default build parameters
			buildParameters.putAll(projectProperties.get(listOfProjects.get(index)));
			String queueUrl = jenkins.doBuild(buildParameters);
			System.out.println("The queue url is "+queueUrl);
			statusArray[index] = jenkins.getStatus(queueUrl);
			while (statusArray[index] == BuildStatus.INPROGRESS) {
				Thread.sleep(10000);
				statusArray[index] = jenkins.getStatus(queueUrl);
			}
			index++;
		}
		return statusArray;
	}
}
