package com.vincentfazio.ui.model.parser.xml;

import java.util.ArrayList;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.Text;
import com.google.gwt.xml.client.XMLParser;

public class CompanyListXmlParser extends XmlParser<ArrayList<String>> {
    
    public ArrayList<String> parse(String responseXml) {
        // parse the XML document into a DOM
        Document messageDom = XMLParser.parse(responseXml);

        NodeList companyList = messageDom.getElementsByTagName("companies");
        NodeList companyNodes = ((Element)companyList.item(0)).getElementsByTagName("company");

        ArrayList<String> companies = new ArrayList<String>();
        for (int i=0; i < companyNodes.getLength(); ++i) {
            Node companyNameNode = ((Element)companyNodes.item(i)).getElementsByTagName("name").item(0);
            Text companyName = (Text) companyNameNode.getFirstChild();
            companies.add(companyName.getData());
        }
        return companies;
    }


    public String createXml(ArrayList<String> companies) {
        throw new UnsupportedOperationException();
    }

}
