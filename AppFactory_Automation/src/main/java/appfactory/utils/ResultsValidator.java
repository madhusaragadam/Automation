package appfactory.utils;

import appfactory.jenkins.BuildStatus;

public class ResultsValidator {

	public static boolean validateResult(BuildStatus[] array, BuildStatus expected) {
	
		int index = 0;
		while(index < array.length) {
			
			if(array[index] != expected) {
				return false;
			}
			index++;
		}
		return true;
	}
}
