package com.vincentfazio.ui.model.parser.xml;

import org.junit.Test;

import com.google.gwt.junit.client.GWTTestCase;
import com.vincentfazio.ui.bean.QuestionBean;
import com.vincentfazio.ui.model.parser.xml.ProfileSurveyQuestionResponseXmlParser;

public class ProfileSurveyQuestionResponseXmlParserTest extends GWTTestCase {

    @Test
    public void testParseChooseOneQuestion() {
        ProfileSurveyQuestionResponseXmlParser xmlParser = new ProfileSurveyQuestionResponseXmlParser();
        QuestionBean question = xmlParser.parse(chooseOneProfileSurveyQuestionXml);
        assertEquals("Business Type", question.getQuestionId());
        QuestionBean selectedChoice = question.getSelectedChoice();
        assertEquals("Public", selectedChoice.getQuestionId());
        assertEquals("Publicly traded company", selectedChoice.getQuestionText());
        assertEquals("true", selectedChoice.getAnswerValue());
        assertEquals("GWT", selectedChoice.getAnsweredBy());
        assertEquals(xmlParser.parseDate("2011-01-31 14:16:20.000"), selectedChoice.getAnsweredTime());

        String expected = "<profile-answers><profile-answer><id>Business Type</id><answer-value>Public</answer-value></profile-answer></profile-answers>";
        assertEquals(expected, xmlParser.createXml(question));
    }
 
    @Test
    public void testParseChooseOneQuestionWithSubquestion() {
        ProfileSurveyQuestionResponseXmlParser xmlParser = new ProfileSurveyQuestionResponseXmlParser();
        QuestionBean question = xmlParser.parse(chooseOneProfileSurveyQuestionWithSubquestionXml);
        assertEquals("Legal Structure", question.getQuestionId());
        assertEquals(5, question.getChoices().size());
        QuestionBean selectedChoice = question.getSelectedChoice();
        assertEquals("Limited Liability", selectedChoice.getQuestionId());
        assertEquals("true", selectedChoice.getAnswerValue());

        String expected = "<profile-answers><profile-answer><id>Legal Structure</id><answer-value>Limited Liability</answer-value></profile-answer><profile-answer><id>Entity Type</id><answer-value>Corporation entity</answer-value></profile-answer></profile-answers>";
        assertEquals(expected, xmlParser.createXml(question));
    }
    
    @Test
    public void testParseFibQuestion() {
        ProfileSurveyQuestionResponseXmlParser xmlParser = new ProfileSurveyQuestionResponseXmlParser();
        QuestionBean question = xmlParser.parse(fibProfileSurveyQuestion);
        assertEquals("Year Established", question.getQuestionId());
        assertNull(question.getChoices());
        assertNull(question.getSelectedChoice());
        assertEquals("8921", question.getAnswerValue());

        String expected = "<profile-answers><profile-answer><id>Year Established</id><answer-value>8921</answer-value></profile-answer></profile-answers>";
        assertEquals(expected, xmlParser.createXml(question));
    }
    
    @Test
    public void testParseTfQuestion() {
        ProfileSurveyQuestionResponseXmlParser xmlParser = new ProfileSurveyQuestionResponseXmlParser();
        QuestionBean question = xmlParser.parse(tfProfileSurveyQuestion);
        assertEquals("Presence Of Derogatory Filings", question.getQuestionId());
        assertEquals(2, question.getChoices().size());
        QuestionBean selectedChoice = question.getSelectedChoice();
        assertEquals("no", selectedChoice.getQuestionText());
        assertEquals("false", selectedChoice.getAnswerValue());

        String expected = "<profile-answers><profile-answer><id>Presence Of Derogatory Filings</id><answer-value>false</answer-value></profile-answer></profile-answers>";
        assertEquals(expected, xmlParser.createXml(question));
    }

    
    @Test
    public void testParseChooseManyQuestion() {
        ProfileSurveyQuestionResponseXmlParser xmlParser = new ProfileSurveyQuestionResponseXmlParser();
        QuestionBean question = xmlParser.parse(chooseManyProfileSurveyQuestion);
        assertEquals("Physical Location", question.getQuestionId());
        assertEquals(3, question.getChoices().size());

        String expected = "<profile-answers><profile-answer><id>US Location</id><answer-value>true</answer-value></profile-answer><profile-answer><id>MultiState US Location</id><answer-value>true</answer-value></profile-answer><profile-answer><id>Foreign Location</id><answer-value>false</answer-value></profile-answer></profile-answers>";
        assertEquals(expected, xmlParser.createXml(question));
    }

    
    @Override
    public String getModuleName() {
        return "com.vincentfazio.ui.gwtprototype";
    } 
    
    
    private String chooseOneProfileSurveyQuestionXml = 
        "<?xml version=\"1.0\" ?>" +
        "<choose-one-question>" +
        "  <id>Business Type</id>" +
        "  <label>Is your organization privately owned, public, or government owned?</label>" +
        "  <answer-groupings>" +
        "    <answer-grouping>" +
        "      <answers>" +
        "        <profile-answer>" +
        "          <id>Private</id>" +
        "          <label>Privately owned company</label>" +
        "          <answer-value>false</answer-value>" +
        "          <is-accepted>false</is-accepted>" +
        "        </profile-answer>" +
        "        <profile-answer>" +
        "          <id>Public</id>" +
        "          <response-id>4099</response-id>" +
        "          <label>Publicly traded company</label>" +
        "          <answer-time>2011-01-31 14:16:20.000</answer-time>" +
        "          <answered-by>GWT</answered-by>" +
        "          <answer-value>true</answer-value>" +
        "          <is-accepted>true</is-accepted>" +
        "          <answer-source>Customer</answer-source>" +
        "        </profile-answer>" +
        "        <profile-answer>" +
        "          <id>Government</id>" +
        "          <label>Government, government agency, or government department</label>" +
        "          <answer-value>false</answer-value>" +
        "          <is-accepted>false</is-accepted>" +
        "        </profile-answer>" +
        "      </answers>" +
        "    </answer-grouping>" +
        "  </answer-groupings>" +
        "</choose-one-question>";
    
    private String chooseOneProfileSurveyQuestionWithSubquestionXml = 
         "<choose-one-question>" +
			"<id>Legal Structure</id>" +
		    "<label>What is the legal structure of your organization?</label>" +
		    "<answer-groupings>" +
		      "<answer-grouping>" +
		        "<answers>" +
		          "<profile-answer>" +
		            "<id>Corporation</id>" +
		            "<label>Corporation</label>" +
		            "<answer-value>false</answer-value>" +
		            "<is-accepted>false</is-accepted>" +
		            "<subquestions>" +
		              "<fill-in-the-blank-question>" +
		                "<id>Corporation Description</id>" +
		                "<label>Corporation Description</label>" +
		                "<answer-groupings/>" +
		              "</fill-in-the-blank-question>" +
		            "</subquestions>" +
		          "</profile-answer>" +
		          "<profile-answer>" +
		            "<id>Partnership</id>" +
		            "<label>Partnership</label>" +
		            "<answer-value>false</answer-value>" +
		            "<is-accepted>false</is-accepted>" +
		            "<subquestions>" +
		              "<fill-in-the-blank-question>" +
		                "<id>Partnership Description</id>" +
		                "<label>Partnership Description</label>" +
		                "<answer-groupings/>" +
		              "</fill-in-the-blank-question>" +
		            "</subquestions>" +
		          "</profile-answer>" +
		          "<profile-answer>" +
		            "<id>Sole Proprietor</id>" +
		            "<label>Sole Proprietor or Individual</label>" +
		            "<answer-value>false</answer-value>" +
		            "<is-accepted>false</is-accepted>" +
		          "</profile-answer>" +
		          "<profile-answer>" +
		            "<id>Limited Liability</id>" +
		            "<label>Limited Liability Organization</label>" +
		            "<answer-value>true</answer-value>" +
		            "<is-accepted>false</is-accepted>" +
		            "<subquestions>" +
		              "<choose-one-question>" +
		                "<id>Entity Type</id>" +
		                "<label>Limited Liability Type:</label>" +
		                "<answer-groupings>" +
		                  "<answer-grouping>" +
		                    "<answers>" +
		                      "<profile-answer>" +
		                        "<id>Disregard entity</id>" +
		                        "<label>Disregard</label>" +
		                        "<answer-value>false</answer-value>" +
		                        "<is-accepted>false</is-accepted>" +
		                      "</profile-answer>" +
		                      "<profile-answer>" +
		                        "<id>Corporation entity</id>" +
		                        "<label>Corporation</label>" +
		                        "<answer-value>true</answer-value>" +
		                        "<is-accepted>false</is-accepted>" +
		                      "</profile-answer>" +
		                      "<profile-answer>" +
		                        "<id>Partnership entity</id>" +
		                        "<label>Partnership</label>" +
		                        "<answer-value>false</answer-value>" +
		                        "<is-accepted>false</is-accepted>" +
		                      "</profile-answer>" +
		                    "</answers>" +
		                  "</answer-grouping>" +
		                "</answer-groupings>" +
		              "</choose-one-question>" +
		            "</subquestions>" +
		          "</profile-answer>" +
		          "<profile-answer>" +
		            "<id>Other Business Type</id>" +
		            "<label>Other</label>" +
		            "<answer-value>false</answer-value>" +
		            "<is-accepted>false</is-accepted>" +
		            "<subquestions>" +
		              "<fill-in-the-blank-question>" +
		                "<id>Other Description</id>" +
		                "<label>Other Description</label>" +
		                "<answer-groupings/>" +
		              "</fill-in-the-blank-question>" +
		            "</subquestions>" +
		          "</profile-answer>" +
		        "</answers>" +
		      "</answer-grouping>" +
		    "</answer-groupings>" +
	     "</choose-one-question>";

    private String fibProfileSurveyQuestion = 
	    "<fill-in-the-blank-question>" +
		    "<id>Year Established</id>" +
		    "<label>Established in:</label>" +
		    "<answer-groupings>" +
		      "<answer-grouping>" +
		        "<answers>" +
		          "<profile-answer>" +
		            "<id>Year Established</id>" +
		            "<response-id>5205</response-id>" +
		            "<answer-time>2012-11-19 09:32:21.000</answer-time>" +
		            "<answered-by>customer1</answered-by>" +
		            "<answer-value>8921</answer-value>" +
		            "<is-accepted>true</is-accepted>" +
		            "<answer-source>Vendor</answer-source>" +
		          "</profile-answer>" +
		        "</answers>" +
		      "</answer-grouping>" +
		    "</answer-groupings>" +
		    "<validation-type>year</validation-type>" +
	    "</fill-in-the-blank-question>";

    private String tfProfileSurveyQuestion = 
	    "<true-false-question>" +
		    "<id>Presence Of Derogatory Filings</id>" +
		    "<label>Does Dell currently have any associated derogatory public filings?</label>" +
		    "<answer-groupings>" +
		      "<answer-grouping>" +
		        "<answers>" +
		          "<profile-answer>" +
		            "<id>Presence Of Derogatory Filings</id>" +
		            "<label>yes</label>" +
		            "<answer-value>true</answer-value>" +
		            "<is-accepted>false</is-accepted>" +
		          "</profile-answer>" +
		          "<profile-answer>" +
		            "<id>Presence Of Derogatory Filings</id>" +
		            "<label>no</label>" +
		            "<answer-value>false</answer-value>" +
		            "<is-accepted>false</is-accepted>" +
		            "<answer-time>2012-11-19 09:32:21.000</answer-time>" +
		            "<answered-by>customer1</answered-by>" +
		          "</profile-answer>" +
		        "</answers>" +
		      "</answer-grouping>" +
		    "</answer-groupings>" +
	    "</true-false-question>";

    private String chooseManyProfileSurveyQuestion = 
	  "<choose-many-question>" +
	    "<id>Physical Location</id>" +
	    "<label>Dell provides services from:</label>" +
	    "<answer-groupings>" +
	      "<answer-grouping>" +
	        "<answers>" +
	          "<profile-answer>" +
	            "<id>US Location</id>" +
	            "<response-id>5148</response-id>" +
	            "<label>Within the United States</label>" +
	            "<answer-time>2012-11-15 10:11:06.000</answer-time>" +
	            "<answered-by>customer1</answered-by>" +
	            "<answer-value>true</answer-value>" +
	            "<is-accepted>true</is-accepted>" +
	            "<answer-source>Vendor</answer-source>" +
	          "</profile-answer>" +
	          "<profile-answer>" +
	            "<id>MultiState US Location</id>" +
	            "<response-id>2453</response-id>" +
	            "<label>More than one U.S. state</label>" +
	            "<answer-time>2011-01-31 14:16:19.000</answer-time>" +
	            "<answered-by>GWT</answered-by>" +
	            "<answer-value>true</answer-value>" +
	            "<is-accepted>true</is-accepted>" +
	            "<answer-source>Customer</answer-source>" +
	          "</profile-answer>" +
	          "<profile-answer>" +
	            "<id>Foreign Location</id>" +
	            "<response-id>5149</response-id>" +
	            "<label>Outside the United States</label>" +
	            "<answer-time>2012-11-15 10:11:06.000</answer-time>" +
	            "<answered-by>customer1</answered-by>" +
	            "<answer-value>false</answer-value>" +
	            "<is-accepted>true</is-accepted>" +
	            "<answer-source>Vendor</answer-source>" +
	          "</profile-answer>" +
	        "</answers>" +
	      "</answer-grouping>" +
	    "</answer-groupings>" +
	  "</choose-many-question>";

}
