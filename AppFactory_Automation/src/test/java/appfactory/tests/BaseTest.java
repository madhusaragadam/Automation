package appfactory.tests;

import java.io.IOException;

import appfactory.boot.Initialize;
import appfactory.exception.InvalidInputException;

public class BaseTest {

	public BaseTest() throws IOException, InvalidInputException {
		
		Initialize obj = new Initialize();
		obj.load();
	}
	
	
}
