package com.vincentfazio.ui.model.parser.xml;

import org.junit.Test;

import com.google.gwt.junit.client.GWTTestCase;
import com.vincentfazio.ui.bean.MyDetailsBean;
import com.vincentfazio.ui.model.parser.xml.MyDetailsXmlParser;

public class MyDetailsXmlParserTest extends GWTTestCase {

    @Test
    public void testParseVendors() {
        MyDetailsXmlParser xmlParser = new MyDetailsXmlParser();
        MyDetailsBean myDetails;
        
        myDetails = xmlParser.parse(
                "<contact>" +
                        "<user-id>Customer1</user-id>" +
                        "<lastname>Newell</lastname>" +
                        "<firstname>Dave</firstname>" +
                        "<roles>" +
                            "<role>" +
                                "<name>Vendor</name>" +
                                "<vendors>" +
                                    "<name>Dell</name>" +
                                    "<name>Service Master Building Maintenance</name>" +
                                "</vendors>" +
                            "</role>" +
                        "</roles>" +
        		"</contact>");
        assertEquals("Dave Newell", myDetails.getName());
        assertEquals("Customer1", myDetails.getUserId());
        assertEquals(2, myDetails.getVendorAccess().size());
        assertEquals("Dell", myDetails.getVendorAccess().get(0));
	}
 
    @Test
    public void testParseAdmin() {
        MyDetailsXmlParser xmlParser = new MyDetailsXmlParser();
        MyDetailsBean myDetails;
        
        myDetails = xmlParser.parse(
            "<contact>" +
                "<user-id>vfazio</user-id>" +
                "<email>vincent.fazio@gmail.com</email>" +
                "<postalcode>14209</postalcode>" +
                "<lastname>Fazio</lastname>" +
                "<firstname>Vinnie</firstname>" +
                "<city>Buffalo</city>" +
                "<telephonenumber>716-888-3670</telephonenumber>" +
                "<postaladdress>800 Delaware Avenue</postaladdress>" +
                "<state>NY</state>" +
                "<fax>716-887-7272</fax>" +
                "<roles>" +
                    "<role>" +
                        "<name>Admin</name>" +
                    "</role>" +
                "</roles>" +
                "<lastLogin>2013-01-15 09:46:53.000</lastLogin>" +
            "</contact>");
        assertEquals("Vinnie Fazio", myDetails.getName());
        assertEquals("vfazio", myDetails.getUserId());
        assertEquals(0, myDetails.getVendorAccess().size());
    }

    @Override
    public String getModuleName() {
        return "com.vincentfazio.ui.gwtprototype";
    } 
}
