package com.forkexec.pts.ws.it;

import com.forkexec.pts.ws.EmailAlreadyExistsFault_Exception;
import com.forkexec.pts.ws.InvalidEmailFault_Exception;
import com.forkexec.pts.ws.InvalidPointsFault_Exception;
import com.forkexec.pts.ws.NotEnoughBalanceFault_Exception;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;


/**
 * Class that tests SpendPoints operation
 */

public class SpendPointsIT extends BaseIT{


	/*
    @Test
    public void success() throws InvalidEmailFault_Exception, EmailAlreadyExistsFault_Exception, InvalidPointsFault_Exception, NotEnoughBalanceFault_Exception {

        client.activateUser("spendPointEmail@spendPointEmail");
        client.spendPointsAsync("spendPointEmail@spendPointEmail", 30, 1);
        Assert.assertEquals(client.getPointsBalance("spendPointEmail@spendPointEmail").getValue(), 70);
    }


    @Test(expected = NotEnoughBalanceFault_Exception.class)
    public void NotEnoughBalanceTest() throws InvalidEmailFault_Exception, EmailAlreadyExistsFault_Exception,InvalidPointsFault_Exception, NotEnoughBalanceFault_Exception {
        client.activateUser("NotEnoughBalance@NotEnoughBalance");
        client.spendPointsAsync("NotEnoughBalance@NotEnoughBalance", 120, 3);
    }*/


    @AfterClass
    public static void oneTimeTearDown() {
        client.ctrlClear();
    }


}
