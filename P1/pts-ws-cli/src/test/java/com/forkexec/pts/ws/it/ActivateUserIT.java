package com.forkexec.pts.ws.it;

import com.forkexec.pts.ws.BadInitFault_Exception;
import com.forkexec.pts.ws.EmailAlreadyExistsFault_Exception;
import com.forkexec.pts.ws.InvalidEmailFault_Exception;
import org.junit.*;


/**
 * Class that tests ActivateUser operation
 */

public class ActivateUserIT extends BaseIT{



    @Test
    public void success() throws BadInitFault_Exception, EmailAlreadyExistsFault_Exception, InvalidEmailFault_Exception {
        client.activateUser("pedro@pedro");
        client.activateUser("pe-dr.o@ped.ro");
        client.activateUser("87693pedro@87671.joao");
    }



    @Test(expected = InvalidEmailFault_Exception.class)
    public void invalidEmailTest1() throws BadInitFault_Exception, EmailAlreadyExistsFault_Exception, InvalidEmailFault_Exception {
        client.activateUser("pedropedro");
    }

    @Test(expected = InvalidEmailFault_Exception.class)
    public void invalidEmailTest2() throws BadInitFault_Exception, EmailAlreadyExistsFault_Exception, InvalidEmailFault_Exception {
        client.activateUser("pedro @pedro");
    }

    @Test(expected = InvalidEmailFault_Exception.class)
    public void invalidEmailTest3() throws BadInitFault_Exception, EmailAlreadyExistsFault_Exception, InvalidEmailFault_Exception {
        client.activateUser("pedro@pedro ");
    }

    @Test(expected = InvalidEmailFault_Exception.class)
    public void invalidEmailTest4() throws BadInitFault_Exception, EmailAlreadyExistsFault_Exception, InvalidEmailFault_Exception {
        client.activateUser("@");
    }

    @Test(expected = InvalidEmailFault_Exception.class)
    public void invalidEmailTest5() throws BadInitFault_Exception, EmailAlreadyExistsFault_Exception, InvalidEmailFault_Exception {
        client.activateUser("@pedro");
    }

    @Test(expected = InvalidEmailFault_Exception.class)
    public void invalidEmailTest6() throws BadInitFault_Exception, EmailAlreadyExistsFault_Exception, InvalidEmailFault_Exception {
        client.activateUser("pedro@");
    }


    @Test(expected = InvalidEmailFault_Exception.class)
    public void invalidEmailTest7() throws BadInitFault_Exception, EmailAlreadyExistsFault_Exception, InvalidEmailFault_Exception {
        client.activateUser("pedro.@pedro.");
    }

    @Test(expected = InvalidEmailFault_Exception.class)
    public void invalidEmailTest8() throws BadInitFault_Exception, EmailAlreadyExistsFault_Exception, InvalidEmailFault_Exception {
        client.activateUser("pedro@pedro.pedro.");
    }


    @Test(expected = EmailAlreadyExistsFault_Exception.class)
    public void alreadyExitsTest() throws BadInitFault_Exception, EmailAlreadyExistsFault_Exception, InvalidEmailFault_Exception {
        client.activateUser("aaa@aaa");
        client.activateUser("aaa@aaa");
    }


    @AfterClass
    public static void oneTimeTearDown() {
        client.ctrlClear();
    }


}
