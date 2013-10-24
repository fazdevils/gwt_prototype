package com.vincentfazio.ui.model.parser.xml;

import java.util.ArrayList;

import org.junit.Test;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.xml.client.impl.DOMParseException;
import com.vincentfazio.ui.model.parser.xml.VendorListXmlParser;

public class VendorListXmlParserTest extends GWTTestCase {

    @Test
    public void testParseVendors() {
        VendorListXmlParser xmlParser = new VendorListXmlParser();
        ArrayList<String> vendors;
        
        try {
            vendors = xmlParser.parse("");
            fail();
        } catch (DOMParseException e) {
        }

        vendors = xmlParser.parse("<vendors></vendors>");
        assertEquals(0, vendors.size());

        try {
            vendors = xmlParser.parse("<vendors><vendor></vendor></vendors>");
            fail();
        } catch (NullPointerException e) {
        }

        vendors = xmlParser.parse("<vendors><vendor><name>vendor1</name></vendor></vendors>");
        assertEquals(1, vendors.size());

        vendors = xmlParser.parse("<vendors><vendor><name>vendor1</name></vendor><vendor><name>vendor2</name></vendor></vendors>");
        assertEquals(2, vendors.size());
	}
 
    @Test
    public void testXmlEnode() {
        VendorListXmlParser xmlParser = new VendorListXmlParser();
        assertEquals("&amp;&lt;&gt;&quot;&apos;", xmlParser.xmlEncode("&<>\"'"));
    }
    
    @Override
    public String getModuleName() {
        return "com.vincentfazio.ui.gwtprototype";
    } 
}
