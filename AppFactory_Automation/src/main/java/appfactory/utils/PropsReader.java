package appfactory.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;

public class PropsReader {

	
	public static HashMap<String, String> readProperties(String propertyFileName) throws IOException {
		
		HashMap<String, String> parameter = new HashMap<String, String>();
		
		Properties prop = new Properties();
		String propFileName = propertyFileName+".properties";
		InputStream inputStream;

		inputStream =new FileInputStream(propFileName);
		prop.load(inputStream);
		
		Iterator<Object> iterator = prop.keySet().iterator();
		
		while(iterator.hasNext()) {
			String key = iterator.next().toString();
			parameter.put(key, prop.getProperty(key));
		}
		
		return parameter;
	}
	
}
