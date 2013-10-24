package com.vincentfazio.ui.model.parser.xml;

import java.util.ArrayList;

import org.junit.Test;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.xml.client.impl.DOMParseException;
import com.vincentfazio.ui.model.parser.xml.UserListXmlParser;

public class UserListXmlParserTest extends GWTTestCase {

    @Test
    public void testParseUsers() {
        UserListXmlParser xmlParser = new UserListXmlParser();
        ArrayList<String> users;
        
        try {
            users = xmlParser.parse("");
            fail();
        } catch (DOMParseException e) {
        }

        users = xmlParser.parse("<users></users>");
        assertEquals(0, users.size());

        try {
            users = xmlParser.parse("<users><user></user></users>");
            fail();
        } catch (NullPointerException e) {
        }

        users = xmlParser.parse("<users><user><user-id>user1</user-id></user></users>");
        assertEquals(1, users.size());

        users = xmlParser.parse("<users><user><user-id>user1</user-id></user><user><user-id>user2</user-id></user></users>");
        assertEquals(2, users.size());
	}
 
    @Override
    public String getModuleName() {
        return "com.vincentfazio.ui.gwtprototype";
    } 
}
