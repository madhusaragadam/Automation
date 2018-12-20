package appfactory.Constants;

import java.util.HashMap;

import org.json.JSONObject;

import appfactory.jenkins.Tenant;

public class TestConstants {

	public static Environments environment;
	public static Tenants tenant;
	public static String projectURL;
	public static String projectName;
	public static Tenant tenantBuilder;
	public static String buildNumber;
	public static HashMap<String, HashMap<String, String>> buildParameters;
}
