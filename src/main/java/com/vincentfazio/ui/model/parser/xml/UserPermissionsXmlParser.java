package com.vincentfazio.ui.model.parser.xml;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;

public class UserPermissionsXmlParser extends XmlParser<List<String>> {
    
    public List<String> parse(String responseXml) {
        // parse the XML document into a DOM
        Document messageDom = XMLParser.parse(responseXml);

        NodeList companyList = messageDom.getElementsByTagName("companies");
        ArrayList<String> companies = new ArrayList<String>();
        
        if (companyList.getLength() > 0) {
            NodeList companyNodes = ((Element)companyList.item(0)).getElementsByTagName("name");
    
            for (int i=0; i < companyNodes.getLength(); ++i) {
                Node companyNode = companyNodes.item(i); 
                Node companyNameNode = companyNode.getFirstChild();               
                if (null != companyNameNode) {
                    companies.add(companyNameNode.getNodeValue());
                }
            }
        }
        return companies;
    }

    public String createXml(List<String> companies) {
        StringBuffer xmlBuffer = new StringBuffer("<role>");
        xmlBuffer.append("<companies>");
        for (String companyName: companies) {
            xmlBuffer.append(createCompanyNameXml(companyName));
        }
        xmlBuffer.append("</companies>");
        xmlBuffer.append("</role>");
        return xmlBuffer.toString();
    }

    private String createCompanyNameXml(String companyName) {
        return createXmlField("name", companyName);
    }

}
