package com.vincentfazio.ui.model.parser.xml;

import java.util.ArrayList;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.Text;
import com.google.gwt.xml.client.XMLParser;

public class VendorListXmlParser extends XmlParser<ArrayList<String>> {
    
    public ArrayList<String> parse(String responseXml) {
        // parse the XML document into a DOM
        Document messageDom = XMLParser.parse(responseXml);

        NodeList vendorList = messageDom.getElementsByTagName("vendors");
        NodeList vendorNodes = ((Element)vendorList.item(0)).getElementsByTagName("vendor");

        ArrayList<String> vendors = new ArrayList<String>();
        for (int i=0; i < vendorNodes.getLength(); ++i) {
            Node vendorNameNode = ((Element)vendorNodes.item(i)).getElementsByTagName("name").item(0);
            Text vendorName = (Text) vendorNameNode.getFirstChild();
            vendors.add(vendorName.getData());
        }
        return vendors;
    }


    public String createXml(ArrayList<String> vendors) {
        throw new UnsupportedOperationException();
    }

}
