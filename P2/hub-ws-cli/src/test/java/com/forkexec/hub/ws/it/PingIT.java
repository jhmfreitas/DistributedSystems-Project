package com.forkexec.hub.ws.it;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

/**
 * Class that tests Ping operation
 */
public class PingIT extends BaseIT {


	@Test
	public void pingEmptyTest() {
		System.out.println("***** Começar testes *****");
		assertNotNull(client.ctrlPing("test"));
	}

}
