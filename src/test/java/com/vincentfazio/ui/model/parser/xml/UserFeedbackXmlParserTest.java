package com.vincentfazio.ui.model.parser.xml;

import org.junit.Test;

import com.google.gwt.junit.client.GWTTestCase;
import com.vincentfazio.ui.bean.UserFeedbackBean;
import com.vincentfazio.ui.model.parser.xml.UserFeedbackXmlParser;

public class UserFeedbackXmlParserTest extends GWTTestCase {

    @Test
    public void testCreateUserFeedbackXml() {
        UserFeedbackXmlParser xmlParser = new UserFeedbackXmlParser();
        
        UserFeedbackBean feedback = new UserFeedbackBean();
        
        feedback.setPage("test page");
        feedback.setIsError(true);
        feedback.setComment("feedback text");
        String feedbackXml = 
                "<user-feedback>" +
                        "<page>test page</page>" +
                        "<comment>feedback text</comment>" +
                        "<error-feedback>true</error-feedback>" +
                "</user-feedback>";
        assertEquals(feedbackXml, xmlParser.createXml(feedback));

        
        feedback.setPage("test page");
        feedback.setIsError(false);
        feedback.setIsPositive(true);
        feedback.setComment("feedback text");
        feedbackXml = 
                "<user-feedback>" +
                        "<page>test page</page>" +
                        "<comment>feedback text</comment>" +
                        "<error-feedback>false</error-feedback>" +
                        "<positive-feedback>true</positive-feedback>" +
                "</user-feedback>";
        assertEquals(feedbackXml, xmlParser.createXml(feedback));
    }

    @Override
    public String getModuleName() {
        return "com.vincentfazio.ui.gwtprototype";
    }
 
}
