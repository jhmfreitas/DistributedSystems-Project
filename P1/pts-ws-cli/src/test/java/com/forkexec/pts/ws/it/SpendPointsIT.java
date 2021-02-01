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



    @Test
    public void success() throws InvalidEmailFault_Exception, EmailAlreadyExistsFault_Exception, InvalidPointsFault_Exception, NotEnoughBalanceFault_Exception {

        client.activateUser("spendPointEmail@spendPointEmail");
        client.spendPoints("spendPointEmail@spendPointEmail", 30);
        Assert.assertEquals(client.pointsBalance("spendPointEmail@spendPointEmail"), 70);
    }



    @Test(expected = InvalidPointsFault_Exception.class)
    public void InvalidPointsTest() throws InvalidEmailFault_Exception, EmailAlreadyExistsFault_Exception,InvalidPointsFault_Exception, NotEnoughBalanceFault_Exception {
        client.activateUser("InvalidPointsTest@InvalidPointsTest");
        client.spendPoints("InvalidPointsTest@InvalidPointsTest", -30);
    }

    @Test(expected = NotEnoughBalanceFault_Exception.class)
    public void NotEnoughBalanceTest() throws InvalidEmailFault_Exception, EmailAlreadyExistsFault_Exception,InvalidPointsFault_Exception, NotEnoughBalanceFault_Exception {
        client.activateUser("NotEnoughBalance@NotEnoughBalance");
        client.spendPoints("NotEnoughBalance@NotEnoughBalance", 120);
    }


    @AfterClass
    public static void oneTimeTearDown() {
        client.ctrlClear();
    }


}
