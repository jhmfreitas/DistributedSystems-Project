package com.forkexec.hub.ws.it;

import com.forkexec.hub.ws.InvalidInitFault_Exception;
import com.forkexec.hub.ws.InvalidUserIdFault_Exception;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;

public class AccountBalanceIT extends BaseIT {

    @Test
    public void initialBalanceTestNonExistingUser() throws InvalidUserIdFault_Exception, InterruptedException {
    	System.out.println("***** Começar testes *****");
        assertEquals(100, client.accountBalance("sd.test@tecnico.ulisboa"));
    }
    
    
    @Test
    public void initialBalanceTestExistingUser() throws InvalidUserIdFault_Exception, InvalidInitFault_Exception, InterruptedException {
        assertEquals(100, client.accountBalance("sd.test@tecnico.ulisboa"));
    }

    @Test(expected = InvalidUserIdFault_Exception.class)
    public void nullEmailTest() throws InvalidUserIdFault_Exception {
        client.accountBalance(null);
    }
    
    @Test(expected = InvalidUserIdFault_Exception.class)
    public void emptyEmailTest() throws InvalidUserIdFault_Exception {
        client.accountBalance("");
    }

    @Test(expected = InvalidUserIdFault_Exception.class)
    public void spacesEmailTest() throws InvalidUserIdFault_Exception {
        client.accountBalance("  \t");
    }
    
    @AfterClass
    public static void oneTimeTearDown() {
        client.ctrlClear();
    }

}
