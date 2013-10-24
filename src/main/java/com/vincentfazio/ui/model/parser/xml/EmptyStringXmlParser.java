package com.vincentfazio.ui.model.parser.xml;


public class EmptyStringXmlParser extends XmlParser<String> {
    
    public String parse(String responseXml) {
        return "";
    }


    public String createXml(String string) {
        return "";
    }


}
