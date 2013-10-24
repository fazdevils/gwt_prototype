package com.vincentfazio.ui.model.parser.xml;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;
import com.vincentfazio.ui.bean.VendorDetailsBean;

public class VendorDetailsXmlParser extends XmlParser<VendorDetailsBean> {
    
    public VendorDetailsBean parse(String responseXml) {
        VendorDetailsBean vendorDetailsBean = new VendorDetailsBean();
        
        // parse the XML document into a DOM
        Document messageDom = XMLParser.parse(responseXml);
        
        NodeList vendor = messageDom.getElementsByTagName("vendor");
        Element vendorElement = (Element)vendor.item(0);
        vendorDetailsBean.setVendorId(extractField(vendorElement, "name"));
        vendorDetailsBean.setAddress(extractField(vendorElement, "street-address"));
        vendorDetailsBean.setCity(extractField(vendorElement, "city"));
        vendorDetailsBean.setState(extractField(vendorElement, "state"));
        vendorDetailsBean.setZip(extractField(vendorElement, "zip"));
        vendorDetailsBean.setCountry(extractField(vendorElement, "country"));
        vendorDetailsBean.setPhone(extractField(vendorElement, "phone"));
        vendorDetailsBean.setStockExchange(extractField(vendorElement, "stock-exchange"));
        vendorDetailsBean.setStockSymbol(extractField(vendorElement, "stock-symbol"));
        vendorDetailsBean.setDunsNumber(extractField(vendorElement, "duns-number"));
        vendorDetailsBean.setExperianBin(extractField(vendorElement, "experian-bin-number"));
        vendorDetailsBean.setUrl(extractField(vendorElement, "url"));

        return vendorDetailsBean;
    }


    public String createXml(VendorDetailsBean vendorDetailsBean) {
        StringBuffer xmlBuffer = new StringBuffer("<vendor>");
        xmlBuffer.append(createXmlField("name", vendorDetailsBean.getVendorId()));
        xmlBuffer.append(createXmlField("street-address", vendorDetailsBean.getAddress()));
        xmlBuffer.append(createXmlField("city", vendorDetailsBean.getCity()));
        xmlBuffer.append(createXmlField("state", vendorDetailsBean.getState()));
        xmlBuffer.append(createXmlField("zip", vendorDetailsBean.getZip()));
        xmlBuffer.append(createXmlField("country", vendorDetailsBean.getCountry()));
        xmlBuffer.append(createXmlField("phone", vendorDetailsBean.getPhone()));
        xmlBuffer.append(createXmlField("stock-exchange", vendorDetailsBean.getStockExchange()));
        xmlBuffer.append(createXmlField("stock-symbol", vendorDetailsBean.getStockSymbol()));
        xmlBuffer.append(createXmlField("duns-number", vendorDetailsBean.getDunsNumber()));
        xmlBuffer.append(createXmlField("experian-bin-number", vendorDetailsBean.getExperianBin()));
        xmlBuffer.append(createXmlField("url", vendorDetailsBean.getUrl()));
        xmlBuffer.append("</vendor>");
        return xmlBuffer.toString();

    }

}
