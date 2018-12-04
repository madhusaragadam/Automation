package appfactory.utils;

import java.util.Scanner;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;

public class FileReader {

	/**
	 * Reads file content into a string
	 * 
	 * @param filePath is the location of the file to be read
	 * @return returns a string with file content
	 * @throws FileNotFoundException is thrown in case of file is not present at the
	 *                               prescribed location
	 */
	public static String readFileContent(String filePath) throws FileNotFoundException {

		File file = new File(filePath);
		StringBuilder fileContents = new StringBuilder((int) file.length());

		try (Scanner scanner = new Scanner(file)) {
			while (scanner.hasNextLine()) {
				fileContents.append(scanner.nextLine() + System.lineSeparator());
			}
			return fileContents.toString();
		}
	}

	/**
	 * Reads the file content into a json object
	 * 
	 * @param filePath is the location of the file to be read
	 * @return returns a JSONObject
	 * @throws JSONException incase of the file content not being parsed into a json object
	 * @throws FileNotFoundException FileNotFoundException is thrown in case of file is not present at the
	 *                               prescribed location
	 */
	public static JSONObject readFileContentIntoJson(String filePath) throws JSONException, FileNotFoundException {
		return new JSONObject(readFileContent(filePath));
	}
	
}
