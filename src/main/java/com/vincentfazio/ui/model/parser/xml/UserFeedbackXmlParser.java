package com.vincentfazio.ui.model.parser.xml;

import com.vincentfazio.ui.bean.UserFeedbackBean;

public class UserFeedbackXmlParser extends XmlParser<UserFeedbackBean> {
    
    public String createXml(UserFeedbackBean userFeedback) {
        StringBuffer xmlBuffer = new StringBuffer("<user-feedback>");
        xmlBuffer.append(createXmlField("page", userFeedback.getPage()));
        xmlBuffer.append(createXmlField("comment", userFeedback.getComment()));
        xmlBuffer.append(createXmlField("error-feedback", userFeedback.isError()));
        xmlBuffer.append(createXmlField("positive-feedback", userFeedback.isPositive()));

        xmlBuffer.append("</user-feedback>");
        return xmlBuffer.toString();

    }

    @Override
    public UserFeedbackBean parse(String xml) {
        throw new UnsupportedOperationException();
    }

}
