package com.vincentfazio.ui.model.parser.xml;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;
import com.vincentfazio.ui.bean.CompanyDetailsBean;

public class CompanyDetailsXmlParser extends XmlParser<CompanyDetailsBean> {
    
    public CompanyDetailsBean parse(String responseXml) {
        CompanyDetailsBean companyDetailsBean = new CompanyDetailsBean();
        
        // parse the XML document into a DOM
        Document messageDom = XMLParser.parse(responseXml);
        
        NodeList company = messageDom.getElementsByTagName("company");
        Element companyElement = (Element)company.item(0);
        companyDetailsBean.setCompanyId(extractField(companyElement, "name"));
        companyDetailsBean.setAddress(extractField(companyElement, "street-address"));
        companyDetailsBean.setCity(extractField(companyElement, "city"));
        companyDetailsBean.setState(extractField(companyElement, "state"));
        companyDetailsBean.setZip(extractField(companyElement, "zip"));
        companyDetailsBean.setCountry(extractField(companyElement, "country"));
        companyDetailsBean.setPhone(extractField(companyElement, "phone"));
        companyDetailsBean.setStockExchange(extractField(companyElement, "stock-exchange"));
        companyDetailsBean.setStockSymbol(extractField(companyElement, "stock-symbol"));
        companyDetailsBean.setDunsNumber(extractField(companyElement, "duns-number"));
        companyDetailsBean.setExperianBin(extractField(companyElement, "experian-bin-number"));
        companyDetailsBean.setUrl(extractField(companyElement, "url"));

        return companyDetailsBean;
    }


    public String createXml(CompanyDetailsBean companyDetailsBean) {
        StringBuffer xmlBuffer = new StringBuffer("<company>");
        xmlBuffer.append(createXmlField("name", companyDetailsBean.getCompanyId()));
        xmlBuffer.append(createXmlField("street-address", companyDetailsBean.getAddress()));
        xmlBuffer.append(createXmlField("city", companyDetailsBean.getCity()));
        xmlBuffer.append(createXmlField("state", companyDetailsBean.getState()));
        xmlBuffer.append(createXmlField("zip", companyDetailsBean.getZip()));
        xmlBuffer.append(createXmlField("country", companyDetailsBean.getCountry()));
        xmlBuffer.append(createXmlField("phone", companyDetailsBean.getPhone()));
        xmlBuffer.append(createXmlField("stock-exchange", companyDetailsBean.getStockExchange()));
        xmlBuffer.append(createXmlField("stock-symbol", companyDetailsBean.getStockSymbol()));
        xmlBuffer.append(createXmlField("duns-number", companyDetailsBean.getDunsNumber()));
        xmlBuffer.append(createXmlField("experian-bin-number", companyDetailsBean.getExperianBin()));
        xmlBuffer.append(createXmlField("url", companyDetailsBean.getUrl()));
        xmlBuffer.append("</company>");
        return xmlBuffer.toString();

    }

}
