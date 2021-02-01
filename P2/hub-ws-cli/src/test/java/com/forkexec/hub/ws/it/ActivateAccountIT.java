package com.forkexec.hub.ws.it;

import com.forkexec.hub.ws.InvalidInitFault_Exception;
import com.forkexec.hub.ws.InvalidUserIdFault_Exception;

import org.junit.AfterClass;
import org.junit.Test;


/**
 * Class that tests ActivateAccount operation
 */

public class ActivateAccountIT extends BaseIT{

	

    @Test
    public void success() throws InvalidUserIdFault_Exception, InvalidInitFault_Exception {
    	System.out.println("***** Começar testes *****");
        client.ctrlClear();
        client.activateAccount("pedro@pedro");
        client.activateAccount("87693pedro@87671.joao");
    }



    @Test(expected = InvalidUserIdFault_Exception.class)
    public void invalidEmailTest1() throws InvalidUserIdFault_Exception {
        client.activateAccount("pedropedro");
    }


    @AfterClass
    public static void oneTimeTearDown() {
        client.ctrlClear();
    }


}
