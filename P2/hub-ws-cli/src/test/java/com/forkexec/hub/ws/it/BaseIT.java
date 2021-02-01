package com.forkexec.hub.ws.it;

import java.io.IOException;
import java.util.Properties;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import com.forkexec.hub.ws.cli.HubClient;
import com.forkexec.rst.ws.cli.RestaurantClient;

/**
 * Base class for testing a Park Load properties from test.properties
 */
public class BaseIT {

	private static final String TEST_PROP_FILE = "/test.properties";
	private static final int NR_RST = 2;
	protected static Properties testProps;

	protected static HubClient client;
	
	protected static RestaurantClient[] restaurantClients = new RestaurantClient[NR_RST];
	protected static String[] restaurantUrls = new String[NR_RST];
	protected static String[] restaurantNames = new String[NR_RST];

	@BeforeClass
	public static void oneTimeSetup() throws Exception {
		testProps = new Properties();
		try {
			testProps.load(BaseIT.class.getResourceAsStream(TEST_PROP_FILE));
			System.out.println("Loaded test properties:");
			System.out.println(testProps);
		} catch (IOException e) {
			final String msg = String.format("Could not load properties file {}", TEST_PROP_FILE);
			System.out.println(msg);
			throw e;
		}

		final String uddiEnabled = testProps.getProperty("uddi.enabled");
		final String verboseEnabled = testProps.getProperty("verbose.enabled");

		final String uddiURL = testProps.getProperty("uddi.url");
		final String wsName = testProps.getProperty("ws.name");
		final String wsURL = testProps.getProperty("ws.url");
		
		///////////////////////////////////////////////////////////
		String rstUrlBase = testProps.getProperty("rst.ws.url");
		String rstNameBase = testProps.getProperty("rst.ws.name");
		
		for (int i = 0; i < NR_RST; i++) {
			restaurantNames[i] = rstNameBase + (i + 1);
			restaurantUrls[i] = rstUrlBase.replaceAll("\\$i", Integer.toString(i + 1));
		}
		/////////////////////////////////////////////////////////
	
		
		if ("true".equalsIgnoreCase(uddiEnabled)) {
			client = new HubClient(uddiURL, wsName);
			for (int i = 0; i < NR_RST; i++)
				restaurantClients[i] = new RestaurantClient(uddiURL, restaurantNames[i]);
		} else {
			client = new HubClient(wsURL);
			for (int i = 0; i < NR_RST; i++)
				restaurantClients[i] = new RestaurantClient(restaurantUrls[i]);
		}
		client.setVerbose("true".equalsIgnoreCase(verboseEnabled));
	}

	@AfterClass
	public static void cleanup() {
		for (int i = 0; i < NR_RST; i++)
			restaurantClients[i] = null;
	}

}
