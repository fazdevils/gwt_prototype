package com.vincentfazio.ui.model.parser.xml;

import org.junit.Test;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.xml.client.impl.DOMParseException;
import com.vincentfazio.ui.bean.UserDetailsBean;
import com.vincentfazio.ui.model.parser.xml.UserDetailsXmlParser;

public class UserDetailsXmlParserTest extends GWTTestCase {

    @Test
    public void testParseUsers() {
        UserDetailsXmlParser xmlParser = new UserDetailsXmlParser();
        
        UserDetailsBean user;
        
        try {
            user = xmlParser.parse("");
            fail();
        } catch (DOMParseException e) {
        }

        user = xmlParser.parse("<contact></contact>");
        assertNull(user.getUserId());
        assertNull(user.getName());
        assertNull(user.getEmail());
        assertNull(user.getPhone());
        assertNull(user.getTitle());

        user = xmlParser.parse("<contact><user-id>user1</user-id></contact>");
        assertEquals("user1", user.getUserId());
        assertNull(user.getName());
        assertNull(user.getEmail());
        assertNull(user.getPhone());
        assertNull(user.getTitle());
        assertFalse(user.isAdministrator());
        assertFalse(user.isCustomer());
        assertFalse(user.isVendor());

        user = xmlParser.parse(
        		"<contact>" +
        		   "<user-id>user1</user-id>" +
        		   "<firstname>userFn</firstname>" +
                   "<lastname>userLn</lastname>" +
                   "<telephonenumber>phone1</telephonenumber>" +
                   "<email>email1</email>" +
                   "<title>title1</title>" +
                   "<roles>" +
                       "<role><name>Customer</name></role>" +
                       "<role><name>Vendor</name></role>" +
                       "<role><name>Admin</name></role>" +
                   "</roles>" +
        		"</contact>");
        assertEquals("user1", user.getUserId());
        assertEquals("userFn userLn", user.getName());
        assertEquals("email1", user.getEmail());
        assertEquals("phone1", user.getPhone());
        assertEquals("title1", user.getTitle());
        assertTrue(user.isAdministrator());
        assertTrue(user.isCustomer());
        assertTrue(user.isVendor());
	}

    @Test
    public void testCtreateUserXml() {
        UserDetailsXmlParser xmlParser = new UserDetailsXmlParser();
        
        UserDetailsBean user = new UserDetailsBean();
        
        String userXml = "<contact></contact>";
        assertEquals(userXml, xmlParser.createXml(user));

        user.setUserId("user1");
        userXml= "<contact><user-id>user1</user-id></contact>";
        assertEquals(userXml, xmlParser.createXml(user));

        user.setName("userFn userLn");
        user.setEmail("email1");
        user.setPhone("phone1");
        user.setTitle("title1");
        user.setAdministrator(true);
        user.setCustomer(true);
        user.setVendor(true);
        userXml = 
                "<contact>" +
                        "<user-id>user1</user-id>" +
                        "<firstname>userFn</firstname>" +
                        "<lastname>userLn</lastname>" +
                        "<email>email1</email>" +
                        "<telephonenumber>phone1</telephonenumber>" +
                        "<title>title1</title>" +
                        "<roles>" +
                            "<role><name>Admin</name></role>" +
                            "<role><name>Customer</name></role>" +
                            "<role><name>Vendor</name></role>" +
                        "</roles>" +
                     "</contact>";
        assertEquals(userXml, xmlParser.createXml(user));

    }

    @Override
    public String getModuleName() {
        return "com.vincentfazio.ui.gwtprototype";
    }
 
}
