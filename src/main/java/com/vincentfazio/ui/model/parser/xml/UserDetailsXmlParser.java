package com.vincentfazio.ui.model.parser.xml;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;
import com.vincentfazio.ui.bean.UserDetailsBean;

public class UserDetailsXmlParser extends XmlParser<UserDetailsBean> {
    
    public UserDetailsBean parse(String responseXml) {
        UserDetailsBean userDetailsBean = new UserDetailsBean();
        
        // parse the XML document into a DOM
        Document messageDom = XMLParser.parse(responseXml);
        
        NodeList user = messageDom.getElementsByTagName("contact");
        Element userElement = (Element)user.item(0);
        userDetailsBean.setUserId(extractField(userElement, "user-id"));
        userDetailsBean.setName(extractName(userElement));
        userDetailsBean.setEmail(extractField(userElement, "email"));
        userDetailsBean.setPhone(extractField(userElement, "telephonenumber"));
        userDetailsBean.setTitle(extractField(userElement, "title"));
        
        NodeList roleNodes = userElement.getElementsByTagName("role");
        for (int i=0; i < roleNodes.getLength(); ++i) {
            String role = extractField((Element)roleNodes.item(i), "name");
            if (role.equalsIgnoreCase("customer")) {
                userDetailsBean.setCustomer(true);
            } else if (role.equalsIgnoreCase("vendor")) {
                userDetailsBean.setVendor(true);
            } else if (role.equalsIgnoreCase("admin")) {
                userDetailsBean.setAdministrator(true);
            }
        }
        
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


    public String createXml(UserDetailsBean userDetailsBean) {
        StringBuffer xmlBuffer = new StringBuffer("<contact>");
        xmlBuffer.append(createXmlField("user-id", userDetailsBean.getUserId()));
        xmlBuffer.append(createNameXmlField(userDetailsBean.getName()));
        xmlBuffer.append(createXmlField("email", userDetailsBean.getEmail()));
        xmlBuffer.append(createXmlField("telephonenumber", userDetailsBean.getPhone()));
        xmlBuffer.append(createXmlField("title", userDetailsBean.getTitle()));
        if (userDetailsBean.isAdministrator() || userDetailsBean.isCustomer() || userDetailsBean.isVendor()) {
            xmlBuffer.append("<roles>");
            if (userDetailsBean.isAdministrator()) {
                xmlBuffer.append(createRoleXml("Admin"));
            }
            if (userDetailsBean.isCustomer()) {
                xmlBuffer.append(createRoleXml("Customer"));
            }
            if (userDetailsBean.isVendor()) {
                xmlBuffer.append(createRoleXml("Vendor"));
            }
            xmlBuffer.append("</roles>");
        }
        xmlBuffer.append("</contact>");
        return xmlBuffer.toString();

    }


    private StringBuffer createRoleXml(String role) {
        StringBuffer xmlRoleBuffer = new StringBuffer("<role>");
        xmlRoleBuffer.append(createXmlField("name", role));
        xmlRoleBuffer.append("</role>");
        return xmlRoleBuffer;
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
