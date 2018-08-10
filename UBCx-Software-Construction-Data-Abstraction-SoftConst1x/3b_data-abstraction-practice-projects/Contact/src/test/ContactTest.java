package test;

import model.Contact;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class ContactTest {

    private Contact testContact;
    private List<String> callLog = new ArrayList<>();

    @Before
    public void setUp(){
        testContact = new Contact("Jane Doe");
    }

    @Test
    public void testConstructor(){
        assertEquals(testContact.getName(), "Jane Doe");
        assertEquals(null, testContact.getNumber());
        assertEquals(testContact.getCallLog(), callLog);
    }

    @Test
    public void testCalls(){
        testContact.call("04/26/16");
        callLog.add("04/26/16");

        assertEquals(testContact.getCallLog(), callLog);

        loadCalls();
        assertEquals(testContact.getCallLog().size(), callLog.size());
        assertTrue(testContact.getCallLog().contains("04/05/17"));
    }

    @Test
    public void testGenerateResponse(){
        testContact.setContactType(Contact.ContactType.FAMILY);
        assertEquals(testContact.generateResponse(), "See you at home.");

        testContact.setContactType(Contact.ContactType.FRIEND);
        assertEquals(testContact.generateResponse(), "Meet you at the cafe.");

        testContact.setContactType(Contact.ContactType.WORK);
        assertEquals(testContact.generateResponse(), "I'll get back to you at work.");
    }


    private void loadCalls(){
        testContact.call("03/11/17");
        testContact.call("03/23/17");
        testContact.call("04/05/17");
        testContact.call("04/06/17");

        callLog.add("03/11/17");
        callLog.add("03/23/17");
        callLog.add("04/05/17");
        callLog.add("04/06/17");
    }


}
