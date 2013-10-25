package com.vincentfazio.ui.model.parser.xml;

import org.junit.Test;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.xml.client.impl.DOMParseException;
import com.vincentfazio.ui.bean.CompanyDetailsBean;
import com.vincentfazio.ui.model.parser.xml.CompanyDetailsXmlParser;

public class CompanyDetailsXmlParserTest extends GWTTestCase {

    @Test
    public void testParseCompanies() {
        CompanyDetailsXmlParser xmlParser = new CompanyDetailsXmlParser();
        
        CompanyDetailsBean company;
        
        try {
            company = xmlParser.parse("");
            fail();
        } catch (DOMParseException e) {
        }

        company = xmlParser.parse("<company></company>");
        assertNull(company.getAddress());
        assertNull(company.getCity());
        assertNull(company.getPhone());
        assertNull(company.getState());
        assertNull(company.getCountry());
        assertNull(company.getStockExchange());
        assertNull(company.getUrl());
        assertNull(company.getCompanyId());
        assertNull(company.getZip());

        company = xmlParser.parse("<company><name>company1</name></company>");
        assertNull(company.getAddress());
        assertNull(company.getCity());
        assertNull(company.getPhone());
        assertNull(company.getState());
        assertNull(company.getCountry());
        assertNull(company.getStockExchange());
        assertNull(company.getUrl());
        assertEquals("company1", company.getCompanyId());
        assertNull(company.getZip());

        company = xmlParser.parse(
        		"<company>" +
        		   "<name>company1</name>" +
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
        		"</company>");
        assertEquals("address1", company.getAddress());
        assertEquals("city1", company.getCity());
        assertEquals("phone1", company.getPhone());
        assertEquals("state1", company.getState());
        assertEquals("country1", company.getCountry());
        assertEquals("url1", company.getUrl());
        assertEquals("zip1", company.getZip());
        assertEquals("stock exchange1", company.getStockExchange());
        assertEquals("stock sym1", company.getStockSymbol());
        assertEquals("123456789", company.getDunsNumber());
        assertEquals("987654321", company.getExperianBin());

        company = xmlParser.parse(
                "<company>" +
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
                "</company>");
        assertEquals("435 Lawrence Bell Dr.", company.getAddress());
        assertEquals("Amherst", company.getCity());
        assertEquals("716-632-4600", company.getPhone());
        assertEquals("NY", company.getState());
        assertEquals("US", company.getCountry());
        assertEquals("http://www.amherstalarm.com", company.getUrl());
        assertNull(company.getZip());
        assertEquals("Unknown", company.getStockExchange());
        assertEquals("Unknown", company.getStockSymbol());
        assertEquals("123456789", company.getDunsNumber());
        assertEquals("987654321", company.getExperianBin());
	}

    @Test
    public void testCreateCompanyXml() {
        CompanyDetailsXmlParser xmlParser = new CompanyDetailsXmlParser();
        
        CompanyDetailsBean company = new CompanyDetailsBean();
        
        String companyXml = "<company></company>";
        assertEquals(companyXml, xmlParser.createXml(company));

        company.setCompanyId("company1");
        companyXml= "<company><name>company1</name></company>";
        assertEquals(companyXml, xmlParser.createXml(company));

        company.setAddress("address1");
        company.setCity("city1");
        company.setPhone("phone1");
        company.setState("state1");
        company.setCountry("country1");
        company.setUrl("url1");
        company.setZip("zip1");
        company.setStockExchange("stock exchange1");
        company.setStockSymbol("stock sym1");
        company.setDunsNumber("123456789");
        company.setExperianBin("987654321");
        companyXml = 
                "<company>" +
                   "<name>company1</name>" +
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
                "</company>";
        assertEquals(companyXml, xmlParser.createXml(company));

    }

    @Override
    public String getModuleName() {
        return "com.vincentfazio.ui.gwtprototype";
    }
 
}
