package appfactory.multitenant;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

import appfactory.Constants.TestConstants;
import appfactory.boot.Initialize;
import appfactory.exception.InvalidInputException;
import appfactory.tests.BaseTest;

public class TestListener extends BaseTest implements IAnnotationTransformer {

	public TestListener() throws IOException, InvalidInputException {
		super();
		
		// TODO Auto-generated constructor stub
	}

	private int counter = TestConstants.json.getJSONArray("testCases").length();

	@Override
	public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
		// TODO Auto-generated method stub
		System.out.println("The listener is activated for:-" + testMethod.getName());
		annotation.setInvocationCount(counter);
		System.out.println("Invocation count is set to :-" + counter);

	}

}
