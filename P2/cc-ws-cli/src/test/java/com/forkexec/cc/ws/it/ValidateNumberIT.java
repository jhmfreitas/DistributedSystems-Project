package com.forkexec.cc.ws.it;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Class that tests Ping operation
 */
public class ValidateNumberIT extends BaseIT {

	@Test
	public void successTest() {
		assertTrue(client.validateNumber("4024007102923926"));
	}
	
	@Test
	public void failTest() {
		assertFalse(client.validateNumber("4024007"));
	}

}
