package com.vincentfazio.ui.model.parser.xml;

import java.util.ArrayList;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.Text;
import com.google.gwt.xml.client.XMLParser;

public class UserListXmlParser extends XmlParser<ArrayList<String>> {
    
    public ArrayList<String> parse(String responseXml) {
        // parse the XML document into a DOM
        Document messageDom = XMLParser.parse(responseXml);

        NodeList userList = messageDom.getElementsByTagName("users");
        NodeList userNodes = ((Element)userList.item(0)).getElementsByTagName("user");

        ArrayList<String> users = new ArrayList<String>();
        for (int i=0; i < userNodes.getLength(); ++i) {
            Node userNameNode = ((Element)userNodes.item(i)).getElementsByTagName("user-id").item(0);
            Text userName = (Text) userNameNode.getFirstChild();
            users.add(userName.getData());
        }
        return users;
    }


    public String createXml(ArrayList<String> users) {
        throw new UnsupportedOperationException();
    }

}
