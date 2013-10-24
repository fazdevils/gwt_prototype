package com.vincentfazio.ui.model.parser.xml;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.xml.client.impl.DOMParseException;
import com.vincentfazio.ui.model.parser.xml.VendorUserPermissionsXmlParser;

public class VendorUserPermissionsXmlParserTest extends GWTTestCase {

    @Test
    public void testParseUserPermissions() {
        VendorUserPermissionsXmlParser xmlParser = new VendorUserPermissionsXmlParser();
        List<String> users;
        
        try {
            users = xmlParser.parse("");
            fail();
        } catch (DOMParseException e) {
        }

        users = xmlParser.parse("<contacts></contacts>");
        assertEquals(0, users.size());

        users = xmlParser.parse("<contacts><contact><user-id></user-id></contact></contacts>");
        assertEquals(0, users.size());

        users = xmlParser.parse("<contacts><contact><user-id>user1</user-id></contact></contacts>");
        assertEquals(1, users.size());

        users = xmlParser.parse("<contacts><contact><user-id>user1</user-id><user-id>user2</user-id></contact></contacts>");
        assertEquals(2, users.size());
	}
 
    @Test
    public void testCreateUserPermissionsXml() {
        VendorUserPermissionsXmlParser xmlParser = new VendorUserPermissionsXmlParser();
        
        ArrayList<String> userPermissions = new ArrayList<String>();
        
        String userPermissionsXml = "<contacts></contacts>";
        assertEquals(userPermissionsXml, xmlParser.createXml(userPermissions));

        userPermissions.add("user1");
        userPermissionsXml = "<contacts><contact><user-id>user1</user-id></contact></contacts>";
        assertEquals(userPermissionsXml, xmlParser.createXml(userPermissions));

        userPermissions.add("user2");
        userPermissionsXml = "<contacts><contact><user-id>user1</user-id></contact><contact><user-id>user2</user-id></contact></contacts>";
        assertEquals(userPermissionsXml, xmlParser.createXml(userPermissions));

    }

    @Override
    public String getModuleName() {
        return "com.vincentfazio.ui.gwtprototype";
    } 
}
