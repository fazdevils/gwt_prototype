package com.vincentfazio.ui.model.parser.xml;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;
import com.vincentfazio.ui.bean.PasswordBean;

public class PasswordXmlParser extends XmlParser<PasswordBean> {
    
    public PasswordBean parse(String responseXml) {
        PasswordBean passwordBean = new PasswordBean();
        
        // parse the XML document into a DOM
        Document messageDom = XMLParser.parse(responseXml);
        
        NodeList user = messageDom.getElementsByTagName("password");
        Element userElement = (Element)user.item(0);
        passwordBean.setCurrentPassword(extractField(userElement, "current"));
        passwordBean.setNewPassword(extractField(userElement, "new"));
        return passwordBean;
    }


    public String createXml(PasswordBean passwordBean) {
        StringBuffer xmlBuffer = new StringBuffer("<password>");
        xmlBuffer.append(createXmlField("current", passwordBean.getCurrentPassword()));
        xmlBuffer.append(createXmlField("new", passwordBean.getNewPassword()));
        xmlBuffer.append("</password>");
        return xmlBuffer.toString();

    }
}
