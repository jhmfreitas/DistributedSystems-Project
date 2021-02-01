package com.forkexec.pts.ws.it;

import com.forkexec.pts.ws.*;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;


/**
 * Class that tests AddPoints operation
 */

public class AddPointsIT extends BaseIT{

	/*
    @Test
    public void success() throws InvalidEmailFault_Exception, EmailAlreadyExistsFault_Exception, InvalidPointsFault_Exception, NotEnoughBalanceFault_Exception, InterruptedException {
        client.activateUser("addPointEmail@addPointEmail");
        client.addPointsAsync("addPointEmail@addPointEmail", 30 , 1);
        Assert.assertEquals(client.getPointsBalance("addPointEmsail@addPointEmail").getValue(), 130);

    }*/


    @AfterClass
    public static void oneTimeTearDown() {
        client.ctrlClear();
    }


}
