package com.forkexec.pts.ws.it;

import com.forkexec.pts.ws.EmailAlreadyExistsFault_Exception;
import com.forkexec.pts.ws.InvalidEmailFault_Exception;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;


/**
 * Class that tests SpendPoints operation
 */

public class PointsBalanceIT extends BaseIT{



    @Test
    public void success() throws InvalidEmailFault_Exception, EmailAlreadyExistsFault_Exception {

        client.activateUser("goodEmail@goodEmail");
        Assert.assertEquals(client.getPointsBalance("goodEmail@goodEmail").getValue(), 100);
    }


    @AfterClass
    public static void oneTimeTearDown() {
        client.ctrlClear();
    }


}
