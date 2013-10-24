package com.vincentfazio.ui.model.parser.xml;

import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.Text;

public abstract class XmlParser<T> {

    protected XmlParser() {
        super();
    }

    public abstract T parse(String xml);
    public abstract String createXml(T bean);
    
    protected String extractField(Element element, String fieldName) {
        NodeList fieldNodeList = element.getElementsByTagName(fieldName);
        if ((null != fieldNodeList) && (fieldNodeList.getLength() > 0)) {
            Node fieldNode = fieldNodeList.item(0); 
            Text textField = (Text) fieldNode.getFirstChild();
            if ((null != textField) && (null != textField.getData()) && (textField.getData().length() > 0)) {
                return textField.getData();
            }
        }
        return null;
    }

    protected String createXmlField(String fieldName, String data) {
        if (null == data) {
            return "";
        }
        return "<" + fieldName + ">" + xmlEncode(data) + "</" + fieldName + ">";
    }

    public String xmlEncode(String data) {
        data = data.replaceAll("&", "&amp;");
        data = data.replaceAll("<", "&lt;");
        data = data.replaceAll(">", "&gt;");
        data = data.replaceAll("\"", "&quot;");
        data = data.replaceAll("'", "&apos;");
        return data;
    }

    protected Object createXmlField(String fieldName, Boolean data) {
        if (null == data) {
            return "";
        }
        return "<" + fieldName + ">" + xmlEncode(data.toString()) + "</" + fieldName + ">";
    }

}