package appfactory.tests;

import appfactory.boot.Initialize;

public class BaseTest {

	public BaseTest() {
		
		Initialize initialize = new Initialize();
		initialize.load();
	}
	
	
}
