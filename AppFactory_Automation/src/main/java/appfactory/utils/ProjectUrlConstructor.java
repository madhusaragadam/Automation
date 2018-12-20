package appfactory.utils;

import appfactory.Constants.JenkinsConstants;
import appfactory.Constants.Tenants;

public class ProjectUrlConstructor {

	public static String constructUrl(String projectName, Tenants jobType) {
	
		String baseUrl = JenkinsConstants.jenkinsUrl+"job/"+projectName+"/job/Visualizer/job/Builds/job/";
		
		if(jobType.toString().equals("SINGLE")) {
			return baseUrl+"buildVisualizerApp/";
		}else if(jobType.toString().equals("MULTI")) {
			return baseUrl+"cloudBuildVisualizerApp/";
		}else if(jobType.toString().equals("ANDROID")) {
			return baseUrl+"Channels/job/buildAndroid/";
		}else if(jobType.toString().equals("IOS")) {
			return baseUrl+"Channels/job/buildIos/";
		}
		
		return baseUrl;
	}
}
