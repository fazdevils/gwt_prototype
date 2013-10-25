package com.vincentfazio.ui.model.parser.xml;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;

public class CompanyUserPermissionsXmlParser extends XmlParser<List<String>> {
    
    public List<String> parse(String responseXml) {
        // parse the XML document into a DOM
        Document messageDom = XMLParser.parse(responseXml);

        NodeList userList = messageDom.getElementsByTagName("contacts");
        ArrayList<String> users = new ArrayList<String>();
        
        if (userList.getLength() > 0) {
            NodeList userNodes = ((Element)userList.item(0)).getElementsByTagName("user-id");
    
            for (int i=0; i < userNodes.getLength(); ++i) {
                Node userNode = userNodes.item(i); 
                Node userIdNode = userNode.getFirstChild();               
                if (null != userIdNode) {
                    users.add(userIdNode.getNodeValue());
                }
            }
        }
        return users;
    }

    public String createXml(List<String> companies) {
        StringBuffer xmlBuffer = new StringBuffer("<contacts>");
        for (String companyName: companies) {
            xmlBuffer.append("<contact>");
            xmlBuffer.append(createUserIdXml(companyName));
            xmlBuffer.append("</contact>");
        }
        xmlBuffer.append("</contacts>");
        return xmlBuffer.toString();
    }

    private String createUserIdXml(String companyName) {
        return createXmlField("user-id", companyName);
    }

}
