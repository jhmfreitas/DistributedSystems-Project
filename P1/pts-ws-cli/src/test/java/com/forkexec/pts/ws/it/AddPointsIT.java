package com.forkexec.pts.ws.it;

import com.forkexec.pts.ws.*;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;


/**
 * Class that tests AddPoints operation
 */

public class AddPointsIT extends BaseIT{


    @Test
    public void success() throws InvalidEmailFault_Exception, EmailAlreadyExistsFault_Exception, InvalidPointsFault_Exception, NotEnoughBalanceFault_Exception {
        client.activateUser("addPointEmail@addPointEmail");
        client.addPoints("addPointEmail@addPointEmail", 30);
        Assert.assertEquals(client.pointsBalance("addPointEmail@addPointEmail"), 130);

    }


    @Test(expected = InvalidPointsFault_Exception.class)
    public void InvalidPointsTest() throws InvalidEmailFault_Exception, EmailAlreadyExistsFault_Exception,InvalidPointsFault_Exception {
        client.activateUser("InvalidPointsTest@InvalidPointsTest");
        client.addPoints("InvalidPointsTest@InvalidPointsTest", -30);
    }


    @AfterClass
    public static void oneTimeTearDown() {
        client.ctrlClear();
    }


}
