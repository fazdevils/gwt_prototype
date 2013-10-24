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

        NodeList vendorList = messageDom.getElementsByTagName("vendors");
        ArrayList<String> vendors = new ArrayList<String>();
        
        if (vendorList.getLength() > 0) {
            NodeList vendorNodes = ((Element)vendorList.item(0)).getElementsByTagName("name");
    
            for (int i=0; i < vendorNodes.getLength(); ++i) {
                Node vendorNode = vendorNodes.item(i); 
                Node vendorNameNode = vendorNode.getFirstChild();               
                if (null != vendorNameNode) {
                    vendors.add(vendorNameNode.getNodeValue());
                }
            }
        }
        return vendors;
    }

    public String createXml(List<String> vendors) {
        StringBuffer xmlBuffer = new StringBuffer("<role>");
        xmlBuffer.append("<vendors>");
        for (String vendorName: vendors) {
            xmlBuffer.append(createVendorNameXml(vendorName));
        }
        xmlBuffer.append("</vendors>");
        xmlBuffer.append("</role>");
        return xmlBuffer.toString();
    }

    private String createVendorNameXml(String vendorName) {
        return createXmlField("name", vendorName);
    }

}
