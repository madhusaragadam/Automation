package appfactory.tests;

import java.io.IOException;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import appfactory.Constants.TestConstants;
import appfactory.jenkins.Jenkins;
import appfactory.utils.FileUtils;

public class Listener implements ITestListener {

	@Override
	public void onFinish(ITestContext arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStart(ITestContext arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTestFailure(ITestResult arg0) {

		String requiredName = "NAN";
		Jenkins jenkins = Jenkins.getInstance("", "", "", null);
		String[] groups  = arg0.getMethod().getGroups();
		for (int i = 0; i < groups.length; i++) {
			if(groups[i].contains("TUNG")) {
				requiredName = groups[i];
			}
		}
		try {
			FileUtils.writeContentToFile(jenkins.getLogOfBuild(TestConstants.buildNumber),requiredName);
		} catch (UnsupportedOperationException | IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onTestSkipped(ITestResult arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTestStart(ITestResult arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTestSuccess(ITestResult arg0) {
		// TODO Auto-generated method stub

	}

}
