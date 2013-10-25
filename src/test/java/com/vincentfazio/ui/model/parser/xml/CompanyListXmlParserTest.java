package com.vincentfazio.ui.model.parser.xml;

import java.util.ArrayList;

import org.junit.Test;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.xml.client.impl.DOMParseException;
import com.vincentfazio.ui.model.parser.xml.CompanyListXmlParser;

public class CompanyListXmlParserTest extends GWTTestCase {

    @Test
    public void testParseCompanies() {
        CompanyListXmlParser xmlParser = new CompanyListXmlParser();
        ArrayList<String> companies;
        
        try {
            companies = xmlParser.parse("");
            fail();
        } catch (DOMParseException e) {
        }

        companies = xmlParser.parse("<companies></companies>");
        assertEquals(0, companies.size());

        try {
            companies = xmlParser.parse("<companies><company></company></companies>");
            fail();
        } catch (NullPointerException e) {
        }

        companies = xmlParser.parse("<companies><company><name>company1</name></company></companies>");
        assertEquals(1, companies.size());

        companies = xmlParser.parse("<companies><company><name>company1</name></company><company><name>company2</name></company></companies>");
        assertEquals(2, companies.size());
	}
 
    @Test
    public void testXmlEnode() {
        CompanyListXmlParser xmlParser = new CompanyListXmlParser();
        assertEquals("&amp;&lt;&gt;&quot;&apos;", xmlParser.xmlEncode("&<>\"'"));
    }
    
    @Override
    public String getModuleName() {
        return "com.vincentfazio.ui.gwtprototype";
    } 
}
