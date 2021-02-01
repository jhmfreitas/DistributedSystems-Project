package com.forkexec.hub.ws.it;

import com.forkexec.hub.ws.InvalidCreditCardFault_Exception;
import com.forkexec.hub.ws.InvalidMoneyFault_Exception;
import com.forkexec.hub.ws.InvalidUserIdFault_Exception;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;

public class LoadAccountIT extends BaseIT {

    @Test
    public void independentAccountsTest() throws InvalidUserIdFault_Exception, InvalidCreditCardFault_Exception, InvalidMoneyFault_Exception {
    	System.out.println("***** Começar testes *****");
    	assertEquals(100,client.accountBalance("sd.test@tecnico.ulisboa"));
        client.loadAccount("sd.test@tecnico.ulisboa", 10, "4156321808160885");
        assertEquals(1100, client.accountBalance("sd.test@tecnico.ulisboa"));
        assertEquals(100, client.accountBalance("sd.test2@tecnico.ulisboa"));
    }
    
    @Test
    public void add10EurosTest() throws InvalidUserIdFault_Exception, InvalidCreditCardFault_Exception, InvalidMoneyFault_Exception {
        client.loadAccount("sd.test@tecnico.ulisboa", 10, "3530552847191833");
        assertEquals(2100, client.accountBalance("sd.test@tecnico.ulisboa"));
    }


    @Test(expected = InvalidCreditCardFault_Exception.class)
    public void nullCreditCardTest() throws InvalidUserIdFault_Exception, InvalidCreditCardFault_Exception, InvalidMoneyFault_Exception {
        client.loadAccount("sd.test@tecnico.ulisboa", 10, null);
    }
    
    @AfterClass
    public static void oneTimeTearDown() {
        client.ctrlClear();
    }
}
