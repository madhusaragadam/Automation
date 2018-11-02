package appfactory.tests;

import java.util.ArrayList;

public class TestCases {
	
	static int successCount=0;
	static int failureCount=0;
	static int index=0;
	
	static ArrayList<ArrayList<String>> successCases  = new ArrayList<ArrayList<String>>();
	static ArrayList<ArrayList<String>> failureCases  = new ArrayList<ArrayList<String>>();

	public static void validateTestCases(ArrayList<String> result) {
	
		 boolean androidUniversal = false;
		 boolean iOSUniversal = false;
		 boolean androidNormal = false;
		 boolean iOSNormal = false;
		 
		 int index=0;
		 
		 while(index < result.size()) {
			 
			 if(result.get(index).contains("ANDROID_UNIVERSAL")) {
				 androidUniversal = true;
			 }
			 else if(result.get(index).contains("IOS_UNIVERSAL")) {
				 iOSUniversal = true;
			 }
			 else if(result.get(index).contains("ANDROID")) {
				 androidNormal = true;
			 }
			 else if(result.get(index).contains("IOS")) {
				 iOSNormal = true;
			 }
			 index++;
		 }
		 
		 if(iOSUniversal && iOSNormal) {
			 failureCases.add(result);
			 return;
		 }
		 if(androidUniversal && androidNormal) {
			 failureCases.add(result);
			 return;
		 }
		 successCases.add(result);
		 successCount++;
	}
	
	static {
			
		ArrayList<String> list = new ArrayList<String>();
		list.add("ANDROID_MOBILE_NATIVE");
		list.add("ANDROID_TABLET_NATIVE");
		list.add("IOS_MOBILE_NATIVE");
		list.add("IOS_TABLET_NATIVE");
		
		list.add("IOS_UNIVERSAL_NATIVE");
		list.add("ANDROID_UNIVERSAL_NATIVE");
		
		
		//logic to iterate over all the combinations
		for(int index=1; index < Math.pow(2, list.size()); index++) {
			
			int temp = index;
			int index1=0;
			ArrayList<String> result = new ArrayList<String>();
			
			while(temp!=0) {
				if((temp&1)==1) {
					result.add(list.get(index1));
				}
				temp=temp>>1;
				index1++;
			}

			validateTestCases(result);
		}
	}
	
}