package com.vincentfazio.ui.model.parser.xml;

import org.junit.Test;

import com.google.gwt.junit.client.GWTTestCase;
import com.vincentfazio.ui.bean.PasswordBean;
import com.vincentfazio.ui.model.parser.xml.PasswordXmlParser;

public class PasswordXmlParserTest extends GWTTestCase {

    @Test
    public void testParsePassword() {
        PasswordXmlParser xmlParser = new PasswordXmlParser();
        
        PasswordBean password;
        
        password = xmlParser.parse(
        		"<password>" +
        		   "<current>old-pw</current>" +
        		   "<new>new-pw</new>" +
        		"</password>");
        assertEquals("old-pw", password.getCurrentPassword());
        assertEquals("new-pw", password.getNewPassword());
	}

    @Test
    public void testCreateUserXml() {
        PasswordXmlParser xmlParser = new PasswordXmlParser();
        
        PasswordBean password = new PasswordBean();
        
        password.setCurrentPassword("old-pw");
        password.setNewPassword("new-pw");
        String passwordXml = 
                "<password>" +
                        "<current>old-pw</current>" +
                        "<new>new-pw</new>" +
                "</password>";
        assertEquals(passwordXml, xmlParser.createXml(password));

    }

    @Override
    public String getModuleName() {
        return "com.vincentfazio.ui.gwtprototype";
    }
 
}
