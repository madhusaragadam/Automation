package appfactory.multitenant;

import org.testng.annotations.Test;
import org.testng.Assert;
import java.io.IOException;
import java.util.HashMap;

import org.apache.http.client.ClientProtocolException;

import appfactory.Constants.Tenants;
import appfactory.exception.InvalidInputException;
import appfactory.jenkins.BuildStatus;
import appfactory.tests.BaseTest;

public class Sanity extends BaseTest {
	
	public Sanity() throws IOException, InvalidInputException {
		super(Tenants.MULTI);
		// TODO Auto-generated constructor stub
	}

	@Test(groups = {"TUNG1"})
	public void allChannels() throws ClientProtocolException, IOException, InterruptedException {
		
		HashMap<String, String> params = new HashMap<String, String>();
		
		params.put("ANDROID_MOBILE_NATIVE", "true");
		params.put("IOS_MOBILE_NATIVE", "true");
		params.put("IOS_UNIVERSAL_NATIVE", "true");
		params.put("ANDROID_TABLET_NATIVE", "true");
		params.put("IOS_TABLET_NATIVE", "true");
		params.put("PROJECT_SOURCE_URL", "https://s3.amazonaws.com/buildservice/StarterProject2Watch.zip");

		Assert.assertEquals(testBuild(params), BuildStatus.SUCCESS);
	}
	
	@Test(groups = {"sanity","success"})
	public void onlyAndroidMobile() throws ClientProtocolException, IOException, InterruptedException {
		
		HashMap<String, String> params = new HashMap<String, String>();
		
		params.put("ANDROID_MOBILE_NATIVE", "true");
		params.put("PROJECT_SOURCE_URL", "https://s3.amazonaws.com/buildservice/StarterProject2Watch.zip");

		Assert.assertEquals(testBuild(params), BuildStatus.SUCCESS);
	}
	
	@Test(groups = {"sanity","success"})
	public void onlyiOSMobile() throws ClientProtocolException, IOException, InterruptedException {
		
		HashMap<String, String> params = new HashMap<String, String>();
		
		params.put("IOS_MOBILE_NATIVE", "true");
		params.put("PROJECT_SOURCE_URL", "https://s3.amazonaws.com/buildservice/StarterProject2Watch.zip");

		Assert.assertEquals(testBuild(params), BuildStatus.SUCCESS);
	}
	
	@Test(groups = {"sanity","success"})
	public void onlyAndroidTablet() throws ClientProtocolException, IOException, InterruptedException {
		
		HashMap<String, String> params = new HashMap<String, String>();
		
		params.put("ANDROID_TABLET_NATIVE", "true");
		params.put("PROJECT_SOURCE_URL", "https://s3.amazonaws.com/buildservice/StarterProject2Watch.zip");

		Assert.assertEquals(testBuild(params), BuildStatus.SUCCESS);
	}

	@Test(groups = {"sanity","success"})
	public void onlyiOSTablet() throws ClientProtocolException, IOException, InterruptedException {
		
		HashMap<String, String> params = new HashMap<String, String>();
		
		params.put("IOS_TABLET_NATIVE", "true");
		params.put("PROJECT_SOURCE_URL", "https://s3.amazonaws.com/buildservice/StarterProject2Watch.zip");

		Assert.assertEquals(testBuild(params), BuildStatus.SUCCESS);
	}
	
	@Test(groups = {"sanity","success"})
	public void onlyiOSUniversal() throws ClientProtocolException, IOException, InterruptedException {
		
		HashMap<String, String> params = new HashMap<String, String>();
		
		params.put("IOS_UNIVERSAL_NATIVE", "true");
		params.put("PROJECT_SOURCE_URL", "https://s3.amazonaws.com/buildservice/StarterProject2Watch.zip");

		Assert.assertEquals(testBuild(params), BuildStatus.SUCCESS);
	}
	
	@Test(groups = {"sanity","success"})
	public void onlyAndroidUniversal() throws ClientProtocolException, IOException, InterruptedException {
		
		HashMap<String, String> params = new HashMap<String, String>();
		
		params.put("ANDROID_UNIVERSAL_NATIVE", "true");
		params.put("PROJECT_SOURCE_URL", "https://s3.amazonaws.com/buildservice/StarterProject2Watch.zip");

		Assert.assertEquals(testBuild(params), BuildStatus.SUCCESS);
	}
	
	@Test(groups = {"sanity", "failure"})
	public void validateAndroidMobileAndUniversal() throws ClientProtocolException, IOException, InterruptedException {
		
		HashMap<String, String> params = new HashMap<String, String>();
		
		params.put("ANDROID_MOBILE_NATIVE", "true");
		params.put("ANDROID_UNIVERSAL_NATIVE", "true");
		params.put("PROJECT_SOURCE_URL", "https://s3.amazonaws.com/buildservice/StarterProject2Watch.zip");

		Assert.assertEquals(testBuild(params), BuildStatus.FAILED);
	}
	
	@Test(groups = {"sanity", "failure"})
	public void validateiOSMobileAndUniversal() throws ClientProtocolException, IOException, InterruptedException {
		
		HashMap<String, String> params = new HashMap<String, String>();
		
		params.put("IOS_MOBILE_NATIVE", "true");
		params.put("IOS_UNIVERSAL_NATIVE", "true");
		params.put("PROJECT_SOURCE_URL", "https://s3.amazonaws.com/buildservice/StarterProject2Watch.zip");

		Assert.assertEquals(testBuild(params), BuildStatus.FAILED);
	}
	
	@Test(groups = {"sanity", "failure"})
	public void validateMissingProjectDetails() throws ClientProtocolException, IOException, InterruptedException {
		
		HashMap<String, String> params = new HashMap<String, String>();
		
		params.put("IOS_MOBILE_NATIVE", "true");
		params.put("IOS_UNIVERSAL_NATIVE", "true");
		params.put("PROJECT_SOURCE_URL", "https://s3.amazonaws.com/buildservice/StarterProject2Watch.zip");
		
		Assert.assertEquals(testBuild(params), BuildStatus.FAILED);
	}
	
	//P1
	
	@Test(groups = {"sanity","success"})
	public void validateAndroidIosUniversal() throws ClientProtocolException, IOException, InterruptedException {
		
		HashMap<String, String> params = new HashMap<String, String>();
		
		params.put("ANDROID_UNIVERSAL_NATIVE", "true");
		params.put("IOS_UNIVERSAL_NATIVE", "true");
		params.put("PROJECT_SOURCE_URL", "https://s3.amazonaws.com/buildservice/StarterProject2Watch.zip");

		Assert.assertEquals(testBuild(params), BuildStatus.SUCCESS);
	}
	
	@Test(groups = {"sanity","success"})
	public void validateAndroidIosMobile() throws ClientProtocolException, IOException, InterruptedException {
		
		HashMap<String, String> params = new HashMap<String, String>();
		
		params.put("ANDROID_MOBILE_NATIVE", "true");
		params.put("IOS_MOBILE_NATIVE", "true");
		params.put("PROJECT_SOURCE_URL", "https://s3.amazonaws.com/buildservice/StarterProject2Watch.zip");

		Assert.assertEquals(testBuild(params), BuildStatus.SUCCESS);
	}
	
	@Test(groups = {"sanity","success"})
	public void validateAndroidIosTablet() throws ClientProtocolException, IOException, InterruptedException {
		
		HashMap<String, String> params = new HashMap<String, String>();
		
		params.put("ANDROID_TABLET_NATIVE", "true");
		params.put("IOS_TABLET_NATIVE", "true");
		params.put("PROJECT_SOURCE_URL", "https://s3.amazonaws.com/buildservice/StarterProject2Watch.zip");

		Assert.assertEquals(testBuild(params), BuildStatus.SUCCESS);
	}
	
	@Test(groups = {"sanity","success"})
	public void validateAndroidTabletIosMobile() throws ClientProtocolException, IOException, InterruptedException {
		
		HashMap<String, String> params = new HashMap<String, String>();
		
		params.put("ANDROID_TABLET_NATIVE", "true");
		params.put("IOS_MOBILE_NATIVE", "true");
		params.put("PROJECT_SOURCE_URL", "https://s3.amazonaws.com/buildservice/StarterProject2Watch.zip");

		Assert.assertEquals(testBuild(params), BuildStatus.SUCCESS);
	}
	
	@Test(groups = {"sanity","success"})
	public void validateAndroidMobileIosTablet() throws ClientProtocolException, IOException, InterruptedException {
		
		HashMap<String, String> params = new HashMap<String, String>();
		
		params.put("ANDROID_MOBILE_NATIVE", "true");
		params.put("IOS_TABLET_NATIVE", "true");
		params.put("PROJECT_SOURCE_URL", "https://s3.amazonaws.com/buildservice/StarterProject2Watch.zip");

		Assert.assertEquals(testBuild(params), BuildStatus.SUCCESS);
	}
	
	@Test(groups = {"sanity","success"})
	public void validateAndroidUniversalIosTablet() throws ClientProtocolException, IOException, InterruptedException {
		
		HashMap<String, String> params = new HashMap<String, String>();
		
		params.put("ANDROID_UNIVERSAL_NATIVE", "true");
		params.put("IOS_TABLET_NATIVE", "true");
		params.put("PROJECT_SOURCE_URL", "https://s3.amazonaws.com/buildservice/StarterProject2Watch.zip");

		Assert.assertEquals(testBuild(params), BuildStatus.SUCCESS);
	}
	
	@Test(groups = {"sanity","success"})
	public void validateAndroidUniversalIosMobile() throws ClientProtocolException, IOException, InterruptedException {
		
		HashMap<String, String> params = new HashMap<String, String>();
		
		params.put("ANDROID_UNIVERSAL_NATIVE", "true");
		params.put("IOS_MOBILE_NATIVE", "true");
		params.put("PROJECT_SOURCE_URL", "https://s3.amazonaws.com/buildservice/StarterProject2Watch.zip");

		Assert.assertEquals(testBuild(params), BuildStatus.SUCCESS);
	}
	
	@Test(groups = {"sanity","success"})
	public void validateAndroidMobileIosUniversal() throws ClientProtocolException, IOException, InterruptedException {
		
		HashMap<String, String> params = new HashMap<String, String>();
		
		params.put("ANDROID_MOBILE_NATIVE", "true");
		params.put("IOS_UNIVERSAL_NATIVE", "true");
		params.put("PROJECT_SOURCE_URL", "https://s3.amazonaws.com/buildservice/StarterProject2Watch.zip");

		Assert.assertEquals(testBuild(params), BuildStatus.SUCCESS);
	}
	
	@Test(groups = {"sanity","success"})
	public void validateAndroidTabletIosUniversal() throws ClientProtocolException, IOException, InterruptedException {
		
		HashMap<String, String> params = new HashMap<String, String>();
		
		params.put("ANDROID_TABLET_NATIVE", "true");
		params.put("IOS_UNIVERSAL_NATIVE", "true");
		params.put("PROJECT_SOURCE_URL", "https://s3.amazonaws.com/buildservice/StarterProject2Watch.zip");

		Assert.assertEquals(testBuild(params), BuildStatus.SUCCESS);
	}
	
	@Test(groups = {"sanity","failure"})
	public void validateIosWithInvalidCreds() throws ClientProtocolException, IOException, InterruptedException {
		
		HashMap<String, String> params = new HashMap<String, String>();
		
		params.put("IOS_UNIVERSAL_NATIVE", "true");
		params.put("APPLE_PASSWORD", "DUMMY");
		params.put("PROJECT_SOURCE_URL", "https://s3.amazonaws.com/buildservice/StarterProject2Watch.zip");

		Assert.assertEquals(testBuild(params), BuildStatus.UNSTABLE);
	}
	
	
	
}
