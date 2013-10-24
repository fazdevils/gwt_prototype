package com.vincentfazio.ui.model.parser.xml;

import org.junit.Test;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.xml.client.impl.DOMParseException;
import com.vincentfazio.ui.bean.VendorDetailsBean;
import com.vincentfazio.ui.model.parser.xml.VendorDetailsXmlParser;

public class VendorDetailsXmlParserTest extends GWTTestCase {

    @Test
    public void testParseVendors() {
        VendorDetailsXmlParser xmlParser = new VendorDetailsXmlParser();
        
        VendorDetailsBean vendor;
        
        try {
            vendor = xmlParser.parse("");
            fail();
        } catch (DOMParseException e) {
        }

        vendor = xmlParser.parse("<vendor></vendor>");
        assertNull(vendor.getAddress());
        assertNull(vendor.getCity());
        assertNull(vendor.getPhone());
        assertNull(vendor.getState());
        assertNull(vendor.getCountry());
        assertNull(vendor.getStockExchange());
        assertNull(vendor.getUrl());
        assertNull(vendor.getVendorId());
        assertNull(vendor.getZip());

        vendor = xmlParser.parse("<vendor><name>vendor1</name></vendor>");
        assertNull(vendor.getAddress());
        assertNull(vendor.getCity());
        assertNull(vendor.getPhone());
        assertNull(vendor.getState());
        assertNull(vendor.getCountry());
        assertNull(vendor.getStockExchange());
        assertNull(vendor.getUrl());
        assertEquals("vendor1", vendor.getVendorId());
        assertNull(vendor.getZip());

        vendor = xmlParser.parse(
        		"<vendor>" +
        		   "<name>vendor1</name>" +
        		   "<street-address>address1</street-address>" +
                   "<city>city1</city>" +
                   "<phone>phone1</phone>" +
                   "<state>state1</state>" +
                   "<country>country1</country>" +
                   "<url>url1</url>" +
                   "<zip>zip1</zip>" +
                   "<stock-exchange>stock exchange1</stock-exchange>" +
                   "<stock-symbol>stock sym1</stock-symbol>" +
                   "<duns-number>123456789</duns-number>" +
                   "<experian-bin-number>987654321</experian-bin-number>" +
        		"</vendor>");
        assertEquals("address1", vendor.getAddress());
        assertEquals("city1", vendor.getCity());
        assertEquals("phone1", vendor.getPhone());
        assertEquals("state1", vendor.getState());
        assertEquals("country1", vendor.getCountry());
        assertEquals("url1", vendor.getUrl());
        assertEquals("zip1", vendor.getZip());
        assertEquals("stock exchange1", vendor.getStockExchange());
        assertEquals("stock sym1", vendor.getStockSymbol());
        assertEquals("123456789", vendor.getDunsNumber());
        assertEquals("987654321", vendor.getExperianBin());

        vendor = xmlParser.parse(
                "<vendor>" +
                "<name>Amherst Alarm, Inc.</name>" +
                "<street-address>435 Lawrence Bell Dr.</street-address>" +
                "<city>Amherst</city>" +
                "<state>NY</state>" +
                "<zip></zip>" +
                "<country>US</country>" +
                "<phone>716-632-4600</phone>" +
                "<duns-number>123456789</duns-number>" +
                "<experian-bin-number>987654321</experian-bin-number>" +
                "<stock-exchange>Unknown</stock-exchange>" +
                "<stock-symbol>Unknown</stock-symbol>" +
                "<company-type>" +
                "<name>Fire Alarm Monitoring</name>" +
                "<category>" +
                "<name>Physical Security</name>" +
                "<types/>" +
                "</category>" +
                "</company-type>" +
                "<url>http://www.amherstalarm.com</url>" +
                "</vendor>");
        assertEquals("435 Lawrence Bell Dr.", vendor.getAddress());
        assertEquals("Amherst", vendor.getCity());
        assertEquals("716-632-4600", vendor.getPhone());
        assertEquals("NY", vendor.getState());
        assertEquals("US", vendor.getCountry());
        assertEquals("http://www.amherstalarm.com", vendor.getUrl());
        assertNull(vendor.getZip());
        assertEquals("Unknown", vendor.getStockExchange());
        assertEquals("Unknown", vendor.getStockSymbol());
        assertEquals("123456789", vendor.getDunsNumber());
        assertEquals("987654321", vendor.getExperianBin());
	}

    @Test
    public void testCreateVendorXml() {
        VendorDetailsXmlParser xmlParser = new VendorDetailsXmlParser();
        
        VendorDetailsBean vendor = new VendorDetailsBean();
        
        String vendorXml = "<vendor></vendor>";
        assertEquals(vendorXml, xmlParser.createXml(vendor));

        vendor.setVendorId("vendor1");
        vendorXml= "<vendor><name>vendor1</name></vendor>";
        assertEquals(vendorXml, xmlParser.createXml(vendor));

        vendor.setAddress("address1");
        vendor.setCity("city1");
        vendor.setPhone("phone1");
        vendor.setState("state1");
        vendor.setCountry("country1");
        vendor.setUrl("url1");
        vendor.setZip("zip1");
        vendor.setStockExchange("stock exchange1");
        vendor.setStockSymbol("stock sym1");
        vendor.setDunsNumber("123456789");
        vendor.setExperianBin("987654321");
        vendorXml = 
                "<vendor>" +
                   "<name>vendor1</name>" +
                   "<street-address>address1</street-address>" +
                   "<city>city1</city>" +
                   "<state>state1</state>" +
                   "<zip>zip1</zip>" +
                   "<country>country1</country>" +
                   "<phone>phone1</phone>" +
                   "<stock-exchange>stock exchange1</stock-exchange>" +
                   "<stock-symbol>stock sym1</stock-symbol>" +
                   "<duns-number>123456789</duns-number>" +
                   "<experian-bin-number>987654321</experian-bin-number>" +
                   "<url>url1</url>" +
                "</vendor>";
        assertEquals(vendorXml, xmlParser.createXml(vendor));

    }

    @Override
    public String getModuleName() {
        return "com.vincentfazio.ui.gwtprototype";
    }
 
}
