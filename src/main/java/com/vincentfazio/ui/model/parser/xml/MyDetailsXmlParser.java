package com.vincentfazio.ui.model.parser.xml;

import java.util.ArrayList;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.Text;
import com.google.gwt.xml.client.XMLParser;
import com.vincentfazio.ui.bean.MyDetailsBean;

public class MyDetailsXmlParser extends XmlParser<MyDetailsBean> {
    
    public MyDetailsBean parse(String responseXml) {
        MyDetailsBean userDetailsBean = new MyDetailsBean();
        
        // parse the XML document into a DOM
        Document messageDom = XMLParser.parse(responseXml);
        
        NodeList user = messageDom.getElementsByTagName("contact");
        Element userElement = (Element)user.item(0);
        userDetailsBean.setUserId(extractField(userElement, "user-id"));
        userDetailsBean.setName(extractName(userElement));
        userDetailsBean.setEmail(extractField(userElement, "email"));
        userDetailsBean.setPhone(extractField(userElement, "telephonenumber"));
        userDetailsBean.setTitle(extractField(userElement, "title"));
        
        ArrayList<String> vendorAccess = new ArrayList<String>();
        NodeList vendorNodeList = userElement.getElementsByTagName("vendors");
        if (vendorNodeList.getLength() > 0) {
            Element vendorListElement = (Element) vendorNodeList.item(0);
            NodeList vendorNames = vendorListElement.getElementsByTagName("name");
            for (int i=0; i < vendorNames.getLength(); ++i) {
                Element vendorNameElement = (Element) vendorNames.item(i);
                Text vendorName = (Text) vendorNameElement.getFirstChild();
                vendorAccess.add(vendorName.getData());
            }
        }
        userDetailsBean.setVendorAccess(vendorAccess);
        
        return userDetailsBean;
    }


    private String extractName(Element userElement) {
        String firstname = extractField(userElement, "firstname");
        String lastname = extractField(userElement, "lastname");
        
        if ((firstname == null) && (lastname == null)) {
            return null;
        }

        StringBuffer name = new StringBuffer();
        if (firstname != null) {
            name.append(firstname);
        }
        if ((firstname != null) && (lastname != null)) {
            name.append(" ");
        }
        if (lastname != null) {
            name.append(lastname);
        }
        return name.toString();
    }


    public String createXml(MyDetailsBean userDetailsBean) {
        StringBuffer xmlBuffer = new StringBuffer("<contact>");
        xmlBuffer.append(createXmlField("user-id", userDetailsBean.getUserId()));
        xmlBuffer.append(createNameXmlField(userDetailsBean.getName()));
        xmlBuffer.append(createXmlField("email", userDetailsBean.getEmail()));
        xmlBuffer.append(createXmlField("telephonenumber", userDetailsBean.getPhone()));
        xmlBuffer.append(createXmlField("title", userDetailsBean.getTitle()));
        xmlBuffer.append("</contact>");
        return xmlBuffer.toString();

    }


    private String createNameXmlField(String userName) {
        if (null == userName) {
            return "";
        }
        StringBuffer nameBuffer = new StringBuffer();
        String[] nameArray = userName.split("\\s+", 2);
        if (nameArray.length > 0) {
            nameBuffer.append(createXmlField("firstname", nameArray[0]));
            if (nameArray.length > 1) {
                nameBuffer.append(createXmlField("lastname", nameArray[1]));
            }   
        }
        return nameBuffer.toString();
    }

}
