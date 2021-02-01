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

        client.ctrlClear();
        client.activateAccount("pedro@pedro");
        client.activateAccount("pe-dr.o@ped.ro");
        client.activateAccount("87693pedro@87671.joao");


        client.ctrlInitUserPoints(100);
    }



    @Test(expected = InvalidUserIdFault_Exception.class)
    public void invalidEmailTest1() throws InvalidUserIdFault_Exception {
        client.activateAccount("pedropedro");
    }

    @Test(expected = InvalidUserIdFault_Exception.class)
    public void invalidEmailTest2() throws InvalidUserIdFault_Exception {
        client.activateAccount("pedro @pedro");
    }

    @Test(expected = InvalidUserIdFault_Exception.class)
    public void invalidEmailTest3() throws InvalidUserIdFault_Exception {
        client.activateAccount("pedro@pedro ");
    }

    @Test(expected = InvalidUserIdFault_Exception.class)
    public void invalidEmailTest4() throws InvalidUserIdFault_Exception {
        client.activateAccount("@");
    }

    @Test(expected = InvalidUserIdFault_Exception.class)
    public void invalidEmailTest5() throws InvalidUserIdFault_Exception {
        client.activateAccount("@pedro");
    }

    @Test(expected = InvalidUserIdFault_Exception.class)
    public void invalidEmailTest6() throws InvalidUserIdFault_Exception {
        client.activateAccount("pedro@");
    }


    @Test(expected = InvalidUserIdFault_Exception.class)
    public void invalidEmailTest7() throws InvalidUserIdFault_Exception {
        client.activateAccount("pedro.@pedro.");
    }

    @Test(expected = InvalidUserIdFault_Exception.class)
    public void invalidEmailTest8() throws InvalidUserIdFault_Exception {
        client.activateAccount("pedro@pedro.pedro.");
    }


    /*
    @Test(expected = EmailAlreadyExistsFault_Exception.class)
    public void alreadyExitsTest() throws InvalidUserIdFault_Exception, InvalidInitFault_Exception {
        client.activateUser("aaa@aaa");
        client.activateUser("aaa@aaa");
    }

     */


    @AfterClass
    public static void oneTimeTearDown() {
        client.ctrlClear();
    }


}
