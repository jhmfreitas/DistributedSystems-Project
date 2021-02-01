package com.forkexec.rst.ws.it;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

/**
 * Class that tests Ping operation
 */
public class PingIT extends BaseIT {

	// tests
	// assertEquals(expected, actual);

	// public String ping(String x)

	@Test
	public void pingEmptyTest() {
		assertNotNull(client.ctrlPing("test"));
	}
	
	@Test
	public void success() {
		assertEquals("Hello test from T22_Restaurant1",client.ctrlPing("test"));
	}

}
