package com.vincentfazio.ui.model.parser.xml;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.xml.client.impl.DOMParseException;
import com.vincentfazio.ui.model.parser.xml.UserPermissionsXmlParser;

public class UserPermissionsXmlParserTest extends GWTTestCase {

    @Test
    public void testParseUserPermissions() {
        UserPermissionsXmlParser xmlParser = new UserPermissionsXmlParser();
        List<String> role;
        
        try {
            role = xmlParser.parse("");
            fail();
        } catch (DOMParseException e) {
        }

        role = xmlParser.parse("<role></role>");
        assertEquals(0, role.size());

        role = xmlParser.parse("<role><vendors><name></name></vendors></role>");
        assertEquals(0, role.size());

        role = xmlParser.parse("<role><vendors><name>vendor1</name></vendors></role>");
        assertEquals(1, role.size());

        role = xmlParser.parse("<role><vendors><name>vendor1</name><name>vendor2</name></vendors></role>");
        assertEquals(2, role.size());
	}
 
    @Test
    public void testCreateUserPermissionsXml() {
        UserPermissionsXmlParser xmlParser = new UserPermissionsXmlParser();
        
        ArrayList<String> userPermissions = new ArrayList<String>();
        
        String userPermissionsXml = "<role><vendors></vendors></role>";
        assertEquals(userPermissionsXml, xmlParser.createXml(userPermissions));

        userPermissions.add("vendor1");
        userPermissionsXml = "<role><vendors><name>vendor1</name></vendors></role>";
        assertEquals(userPermissionsXml, xmlParser.createXml(userPermissions));

        userPermissions.add("vendor2");
        userPermissionsXml = "<role><vendors><name>vendor1</name><name>vendor2</name></vendors></role>";
        assertEquals(userPermissionsXml, xmlParser.createXml(userPermissions));

    }

    @Override
    public String getModuleName() {
        return "com.vincentfazio.ui.gwtprototype";
    } 
}
