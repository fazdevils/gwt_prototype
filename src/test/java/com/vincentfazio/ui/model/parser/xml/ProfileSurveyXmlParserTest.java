package com.vincentfazio.ui.model.parser.xml;

import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.google.gwt.junit.client.GWTTestCase;
import com.vincentfazio.ui.bean.QuestionBean;
import com.vincentfazio.ui.bean.QuestionType;
import com.vincentfazio.ui.bean.QuestionBean.QuestionStatus;
import com.vincentfazio.ui.model.parser.xml.ProfileSurveyXmlParser;

public class ProfileSurveyXmlParserTest extends GWTTestCase {

    @Test
    public void testParseSurvey() {
        ProfileSurveyXmlParser xmlParser = new ProfileSurveyXmlParser();
        List<QuestionBean> questions = xmlParser.parse(profileSurveyXml);
        assertEquals(50, questions.size());
        
        QuestionStatus surveyStatus = new QuestionStatus();
        for (QuestionBean question: questions) {
            QuestionStatus questionStatus = question.getQuestionStatus();
            surveyStatus.numberOfSubquestions += questionStatus.numberOfSubquestions;
            surveyStatus.numberOfAnsweredSubquestions += questionStatus.numberOfAnsweredSubquestions;
        }

        assertEquals(103, surveyStatus.numberOfAnsweredSubquestions);
        assertEquals(148, surveyStatus.numberOfSubquestions);
    }
 
    @Test
    public void testParseSurveySubset() {
        ProfileSurveyXmlParser xmlParser = new ProfileSurveyXmlParser();
        List<QuestionBean> questions = xmlParser.parse(profileSurveyXmlSubset);

        assertEquals(1, questions.size());
        QuestionBean questionBean = questions.get(0);
        QuestionStatus questionStatus = questionBean.getQuestionStatus(); 
        assertEquals(1, questionStatus.numberOfSubquestions);
        assertEquals(0, questionStatus.numberOfAnsweredSubquestions);

        QuestionStatus subquestionStatus = questionBean.getSubquestionStatus(); 
        assertEquals(0, subquestionStatus.numberOfSubquestions);
        assertEquals(0, subquestionStatus.numberOfAnsweredSubquestions);
        
        assertFalse(questionBean.hasSubquestions());
        assertFalse(questionBean.isAnswerCompleted());
        assertFalse(questionBean.isAnswered());
        
        QuestionBean answerChoice = questionBean.getChoices().get(0);
        answerChoice.setAnswerValue("true");
        subquestionStatus = questionBean.getSubquestionStatus(); 
        assertEquals(1, subquestionStatus.numberOfSubquestions);
        assertEquals(0, subquestionStatus.numberOfAnsweredSubquestions);
        
        assertTrue(questionBean.hasSubquestions());
        assertFalse(questionBean.isAnswerCompleted());
        assertTrue(questionBean.isAnswered());
    }

    
    @Test
    public void testParseTFSurveySubset() {
        ProfileSurveyXmlParser xmlParser = new ProfileSurveyXmlParser();
        List<QuestionBean> questions = xmlParser.parse(profileTFSurveyXmlSubset);

        assertEquals(1, questions.size());
        QuestionBean questionBean = questions.get(0);
        assertEquals(QuestionType.TrueFalse, questionBean.getQuestionType());
        assertEquals("no", questionBean.getAnswerValue());
        assertTrue(questionBean.isAnswerCompleted());
        assertTrue(questionBean.isAnswered());
        assertFalse(questionBean.hasSubquestions());

        QuestionStatus questionStatus = questionBean.getQuestionStatus(); 
        assertEquals(1, questionStatus.numberOfSubquestions);
        assertEquals(1, questionStatus.numberOfAnsweredSubquestions);

        QuestionStatus subquestionStatus = questionBean.getSubquestionStatus(); 
        assertEquals(0, subquestionStatus.numberOfSubquestions);
        assertEquals(0, subquestionStatus.numberOfAnsweredSubquestions);

        QuestionBean trueChoice = questionBean.getChoices().get(0);
        QuestionBean falseChoice = questionBean.getChoices().get(1);        
        String answeredBy = trueChoice.getAnsweredBy();
        Date answeredTime = trueChoice.getAnsweredTime();
        trueChoice.setAnsweredBy(falseChoice.getAnsweredBy());
        trueChoice.setAnsweredTime(falseChoice.getAnsweredTime());
        falseChoice.setAnsweredBy(answeredBy);
        falseChoice.setAnsweredTime(answeredTime);

        assertEquals("yes", questionBean.getAnswerValue());    
        assertFalse(questionBean.isAnswerCompleted());
        assertTrue(questionBean.isAnswered());
        assertTrue(questionBean.hasSubquestions());

        questionStatus = questionBean.getQuestionStatus(); 
        assertEquals(2, questionStatus.numberOfSubquestions);
        assertEquals(1, questionStatus.numberOfAnsweredSubquestions);

        subquestionStatus = questionBean.getSubquestionStatus(); 
        assertEquals(1, subquestionStatus.numberOfSubquestions);
        assertEquals(0, subquestionStatus.numberOfAnsweredSubquestions);

}

    
    @Override
    public String getModuleName() {
        return "com.vincentfazio.ui.gwtprototype";
    } 
    
    
    private String profileTFSurveyXmlSubset = 
            "<?xml version=\"1.0\" ?><question-categories>" +
                    "<question-category>" +
                      "<label>Company Demographics</label>" +
                      "<questions>" +
                            "<true-false-question>" +
                            "<id>Subsidiary</id>" +
                            "<label>Is your organization a subsidiary of another organization?</label>" +
                            "<answer-groupings>" +
                              "<answer-grouping>" +
                                "<answers>" +
                                  "<profile-answer>" +
                                    "<id>Subsidiary</id>" +
                                    "<label>yes</label>" +
                                    "<answer-value>true</answer-value>" +
                                    "<is-accepted>false</is-accepted>" +
                                    "<subquestions>" +
                                      "<fill-in-the-blank-question>" +
                                        "<id>Parent</id>" +
                                        "<label>What is the parent organization name?</label>" +
                                        "<answer-groupings/>" +
                                      "</fill-in-the-blank-question>" +
                                    "</subquestions>" +
                                  "</profile-answer>" +
                                  "<profile-answer>" +
                                    "<id>Subsidiary</id>" +
                                    "<response-id>2481</response-id>" +
                                    "<label>no</label>" +
                                    "<answer-time>2011-01-31 14:16:20.000</answer-time>" +
                                    "<answered-by>GWT</answered-by>" +
                                    "<answer-value>false</answer-value>" +
                                    "<is-accepted>true</is-accepted>" +
                                    "<answer-source>Customer</answer-source>" +
                                  "</profile-answer>" +
                                "</answers>" +
                              "</answer-grouping>" +
                            "</answer-groupings>" +
                          "</true-false-question>" +
                      "</questions>" +
                  "</question-category>" +
            "</question-categories>";
    
    private String profileSurveyXmlSubset = 
            "<?xml version=\"1.0\" ?><question-categories>" +
                    "<question-category>" +
                      "<label>Company Demographics</label>" +
                      "<questions>" +
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
                                  "<answer-value>false</answer-value>" +
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
                                              "<answer-value>false</answer-value>" +
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
                        "</choose-one-question>" +
                    "</questions>" +
                "</question-category>" +
              "</question-categories>";

    
    private String profileSurveyXml = 
            "<?xml version=\"1.0\" ?><question-categories>" +
                    "<question-category>" +
                      "<label>Company Demographics</label>" +
                      "<questions>" +
                        "<choose-one-question>" +
                          "<id>Business Type</id>" +
                          "<label>Is your organization privately owned, public, or government owned?</label>" +
                          "<answer-groupings>" +
                            "<answer-grouping>" +
                              "<answers>" +
                                "<profile-answer>" +
                                  "<id>Private</id>" +
                                  "<label>Privately owned company</label>" +
                                  "<answer-value>false</answer-value>" +
                                  "<is-accepted>false</is-accepted>" +
                                "</profile-answer>" +
                                "<profile-answer>" +
                                  "<id>Public</id>" +
                                  "<response-id>4099</response-id>" +
                                  "<label>Publicly traded company</label>" +
                                  "<answer-time>2011-01-31 14:16:20.000</answer-time>" +
                                  "<answered-by>GWT</answered-by>" +
                                  "<answer-value>true</answer-value>" +
                                  "<is-accepted>true</is-accepted>" +
                                  "<answer-source>Customer</answer-source>" +
                                "</profile-answer>" +
                                "<profile-answer>" +
                                  "<id>Government</id>" +
                                  "<label>Government, government agency, or government department</label>" +
                                  "<answer-value>false</answer-value>" +
                                  "<is-accepted>false</is-accepted>" +
                                "</profile-answer>" +
                              "</answers>" +
                            "</answer-grouping>" +
                          "</answer-groupings>" +
                        "</choose-one-question>" +
                        "<question-group>" +
                          "<id>Business Demographics</id>" +
                          "<label>Maturity and size:</label>" +
                          "<answer-groupings/>" +
                          "<questions>" +
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
                            "</fill-in-the-blank-question>" +
                            "<fill-in-the-blank-question>" +
                              "<id>Control Year</id>" +
                              "<label>Year present management assumed control:</label>" +
                              "<answer-groupings>" +
                                "<answer-grouping>" +
                                  "<answers>" +
                                    "<profile-answer>" +
                                      "<id>Control Year</id>" +
                                      "<response-id>5147</response-id>" +
                                      "<answer-time>2012-11-15 10:11:06.000</answer-time>" +
                                      "<answered-by>customer1</answered-by>" +
                                      "<answer-value>1111</answer-value>" +
                                      "<is-accepted>true</is-accepted>" +
                                      "<answer-source>Vendor</answer-source>" +
                                    "</profile-answer>" +
                                  "</answers>" +
                                "</answer-grouping>" +
                              "</answer-groupings>" +
                              "<validation-type>year</validation-type>" +
                            "</fill-in-the-blank-question>" +
                            "<fill-in-the-blank-question>" +
                              "<id>Number Of Employees</id>" +
                              "<label>Number of employees:</label>" +
                              "<answer-groupings>" +
                                "<answer-grouping>" +
                                  "<answers>" +
                                    "<profile-answer>" +
                                      "<id>Number Of Employees</id>" +
                                      "<response-id>5191</response-id>" +
                                      "<answer-time>2012-11-16 11:15:02.000</answer-time>" +
                                      "<answered-by>customer1</answered-by>" +
                                      "<answer-value>11</answer-value>" +
                                      "<is-accepted>true</is-accepted>" +
                                      "<answer-source>Vendor</answer-source>" +
                                    "</profile-answer>" +
                                  "</answers>" +
                                "</answer-grouping>" +
                              "</answer-groupings>" +
                              "<validation-type>integer</validation-type>" +
                            "</fill-in-the-blank-question>" +
                            "<fill-in-the-blank-question>" +
                              "<id>Net Income</id>" +
                              "<label>Net income:</label>" +
                              "<answer-groupings/>" +
                              "<validation-type>dollar</validation-type>" +
                            "</fill-in-the-blank-question>" +
                            "<fill-in-the-blank-question>" +
                              "<id>Annual Sales Revenue</id>" +
                              "<label>Annual revenue:</label>" +
                              "<answer-groupings/>" +
                              "<validation-type>dollar</validation-type>" +
                            "</fill-in-the-blank-question>" +
                          "</questions>" +
                        "</question-group>" +
                        "<fill-in-the-blank-question>" +
                          "<id>Annual Sales Allocation</id>" +
                          "<label>What percentage of the annual sales revenue results from Dell services to Dunder Mifflin?</label>" +
                          "<answer-groupings/>" +
                          "<validation-type>percent</validation-type>" +
                        "</fill-in-the-blank-question>" +
                        "<question-group>" +
                          "<id>Three year history</id>" +
                          "<label>How many of the following are currently open or have been filed against Dell in the last three years?</label>" +
                          "<answer-groupings/>" +
                          "<questions>" +
                            "<fill-in-the-blank-question>" +
                              "<id>Suits</id>" +
                              "<label>Suits</label>" +
                              "<answer-groupings/>" +
                              "<validation-type>integer</validation-type>" +
                            "</fill-in-the-blank-question>" +
                            "<fill-in-the-blank-question>" +
                              "<id>Liens</id>" +
                              "<label>Liens</label>" +
                              "<answer-groupings/>" +
                              "<validation-type>integer</validation-type>" +
                            "</fill-in-the-blank-question>" +
                            "<fill-in-the-blank-question>" +
                              "<id>Judgments</id>" +
                              "<label>Judgments</label>" +
                              "<answer-groupings/>" +
                              "<validation-type>integer</validation-type>" +
                            "</fill-in-the-blank-question>" +
                          "</questions>" +
                        "</question-group>" +
                        "<choose-one-question>" +
                          "<id>Location Type</id>" +
                          "<label>Does Dell deliver services to Dunder Mifflin from a branch office, from the headquarters&apos; location, through or as a subsidiary, or Dell&apos;s sole or single location?</label>" +
                          "<answer-groupings>" +
                            "<answer-grouping>" +
                              "<answers>" +
                                "<profile-answer>" +
                                  "<id>Branch</id>" +
                                  "<label>Branch</label>" +
                                  "<answer-value>false</answer-value>" +
                                  "<is-accepted>false</is-accepted>" +
                                "</profile-answer>" +
                                "<profile-answer>" +
                                  "<id>Headquarters</id>" +
                                  "<label>Headquarters</label>" +
                                  "<answer-value>false</answer-value>" +
                                  "<is-accepted>false</is-accepted>" +
                                "</profile-answer>" +
                                "<profile-answer>" +
                                  "<id>Single location Subsidiary</id>" +
                                  "<label>Single location Subsidiary</label>" +
                                  "<answer-value>false</answer-value>" +
                                  "<is-accepted>false</is-accepted>" +
                                "</profile-answer>" +
                                "<profile-answer>" +
                                  "<id>Single Entity</id>" +
                                  "<label>Single Entity</label>" +
                                  "<answer-value>false</answer-value>" +
                                  "<is-accepted>false</is-accepted>" +
                                "</profile-answer>" +
                                "<profile-answer>" +
                                  "<id>Other Business Type</id>" +
                                  "<label>Other</label>" +
                                  "<answer-value>false</answer-value>" +
                                  "<is-accepted>false</is-accepted>" +
                                "</profile-answer>" +
                              "</answers>" +
                            "</answer-grouping>" +
                          "</answer-groupings>" +
                        "</choose-one-question>" +
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
                                  "<answer-value>false</answer-value>" +
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
                                              "<answer-value>false</answer-value>" +
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
                        "</choose-one-question>" +
                        "<true-false-question>" +
                          "<id>Subsidiary</id>" +
                          "<label>Is your organization a subsidiary of another organization?</label>" +
                          "<answer-groupings>" +
                            "<answer-grouping>" +
                              "<answers>" +
                                "<profile-answer>" +
                                  "<id>Subsidiary</id>" +
                                  "<label>yes</label>" +
                                  "<answer-value>true</answer-value>" +
                                  "<is-accepted>false</is-accepted>" +
                                  "<subquestions>" +
                                    "<fill-in-the-blank-question>" +
                                      "<id>Parent</id>" +
                                      "<label>What is the parent organization name?</label>" +
                                      "<answer-groupings/>" +
                                    "</fill-in-the-blank-question>" +
                                  "</subquestions>" +
                                "</profile-answer>" +
                                "<profile-answer>" +
                                  "<id>Subsidiary</id>" +
                                  "<response-id>2481</response-id>" +
                                  "<label>no</label>" +
                                  "<answer-time>2011-01-31 14:16:20.000</answer-time>" +
                                  "<answered-by>GWT</answered-by>" +
                                  "<answer-value>false</answer-value>" +
                                  "<is-accepted>true</is-accepted>" +
                                  "<answer-source>Customer</answer-source>" +
                                "</profile-answer>" +
                              "</answers>" +
                            "</answer-grouping>" +
                          "</answer-groupings>" +
                        "</true-false-question>" +
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
                                "</profile-answer>" +
                              "</answers>" +
                            "</answer-grouping>" +
                          "</answer-groupings>" +
                        "</true-false-question>" +
                        "<fill-in-the-blank-question>" +
                          "<id>Liability Amount</id>" +
                          "<label>What is the total current legal liability or collection amount including accounts in collection, liens, judgments/suits, and bankruptcies?</label>" +
                          "<answer-groupings>" +
                            "<answer-grouping>" +
                              "<answers>" +
                                "<profile-answer>" +
                                  "<id>Liability Amount</id>" +
                                  "<response-id>5192</response-id>" +
                                  "<answer-time>2012-11-16 11:15:41.000</answer-time>" +
                                  "<answered-by>customer1</answered-by>" +
                                  "<answer-value>12</answer-value>" +
                                  "<is-accepted>true</is-accepted>" +
                                  "<answer-source>Vendor</answer-source>" +
                                "</profile-answer>" +
                              "</answers>" +
                            "</answer-grouping>" +
                          "</answer-groupings>" +
                          "<validation-type>dollar</validation-type>" +
                        "</fill-in-the-blank-question>" +
                        "<choose-one-question>" +
                          "<id>UCC Indicator</id>" +
                          "<label>Does Dell have any UCC filings in place?</label>" +
                          "<answer-groupings>" +
                            "<answer-grouping>" +
                              "<answers>" +
                                "<profile-answer>" +
                                  "<id>Cautionary UCC Present</id>" +
                                  "<label>Cautionary UCC Present</label>" +
                                  "<answer-value>false</answer-value>" +
                                  "<is-accepted>false</is-accepted>" +
                                "</profile-answer>" +
                                "<profile-answer>" +
                                  "<id>UCC Present</id>" +
                                  "<label>UCC Present</label>" +
                                  "<answer-value>false</answer-value>" +
                                  "<is-accepted>false</is-accepted>" +
                                "</profile-answer>" +
                                "<profile-answer>" +
                                  "<id>None</id>" +
                                  "<label>No UCC</label>" +
                                  "<answer-value>false</answer-value>" +
                                  "<is-accepted>false</is-accepted>" +
                                "</profile-answer>" +
                              "</answers>" +
                            "</answer-grouping>" +
                          "</answer-groupings>" +
                        "</choose-one-question>" +
                        "<fill-in-the-blank-question>" +
                          "<id>Total Number of Judgments in last 2 years</id>" +
                          "<label>What is the total number of judgments filed against Dell within the last 24 months?</label>" +
                          "<answer-groupings/>" +
                          "<validation-type>integer</validation-type>" +
                        "</fill-in-the-blank-question>" +
                        "<fill-in-the-blank-question>" +
                          "<id>Total Value of Judgments in last 2 years</id>" +
                          "<label>What is the total amount of judgments filed against Dell within the last 24 months?</label>" +
                          "<answer-groupings/>" +
                          "<validation-type>dollar</validation-type>" +
                        "</fill-in-the-blank-question>" +
                        "<fill-in-the-blank-question>" +
                          "<id>Total Value of Liens in last 2 years</id>" +
                          "<label>What is the total amount of tax liens filed within the last 24 months?</label>" +
                          "<answer-groupings/>" +
                          "<validation-type>dollar</validation-type>" +
                        "</fill-in-the-blank-question>" +
                        "<fill-in-the-blank-question>" +
                          "<id>Total Number of UCC Filings</id>" +
                          "<label>What is the total number of current UCC filings for Dell?</label>" +
                          "<answer-groupings/>" +
                          "<validation-type>integer</validation-type>" +
                        "</fill-in-the-blank-question>" +
                      "</questions>" +
                    "</question-category>" +
                    "<question-category>" +
                      "<label>Financial Information</label>" +
                      "<questions>" +
                        "<fill-in-the-blank-question>" +
                          "<id>Number Of Customers</id>" +
                          "<label>How many customers does Dell currently have?</label>" +
                          "<answer-groupings>" +
                            "<answer-grouping>" +
                              "<answers>" +
                                "<profile-answer>" +
                                  "<id>Number Of Customers</id>" +
                                  "<response-id>5153</response-id>" +
                                  "<answer-time>2012-11-15 10:11:07.000</answer-time>" +
                                  "<answered-by>customer1</answered-by>" +
                                  "<answer-value>1</answer-value>" +
                                  "<is-accepted>true</is-accepted>" +
                                  "<answer-source>Vendor</answer-source>" +
                                "</profile-answer>" +
                              "</answers>" +
                            "</answer-grouping>" +
                          "</answer-groupings>" +
                          "<validation-type>integer</validation-type>" +
                        "</fill-in-the-blank-question>" +
                        "<fill-in-the-blank-question>" +
                          "<id>Employee Turnover</id>" +
                          "<label>What is annual the turnover rate for employees working for Dell?</label>" +
                          "<answer-groupings>" +
                            "<answer-grouping>" +
                              "<answers>" +
                                "<profile-answer>" +
                                  "<id>Employee Turnover</id>" +
                                  "<response-id>5154</response-id>" +
                                  "<answer-time>2012-11-15 10:11:08.000</answer-time>" +
                                  "<answered-by>customer1</answered-by>" +
                                  "<answer-value>11</answer-value>" +
                                  "<is-accepted>true</is-accepted>" +
                                  "<answer-source>Vendor</answer-source>" +
                                "</profile-answer>" +
                              "</answers>" +
                            "</answer-grouping>" +
                          "</answer-groupings>" +
                          "<validation-type>percent</validation-type>" +
                        "</fill-in-the-blank-question>" +
                        "<read-only-question>" +
                          "<id>Bankrupt</id>" +
                          "<label>In bankruptcy proceedings?</label>" +
                          "<answer-groupings>" +
                            "<answer-grouping>" +
                              "<answers>" +
                                "<profile-answer>" +
                                  "<id>Bankrupt</id>" +
                                  "<response-id>2399</response-id>" +
                                  "<answer-time>2011-01-31 14:16:17.000</answer-time>" +
                                  "<answered-by>GWT</answered-by>" +
                                  "<answer-value>false</answer-value>" +
                                  "<is-accepted>true</is-accepted>" +
                                  "<answer-source>Customer</answer-source>" +
                                "</profile-answer>" +
                              "</answers>" +
                            "</answer-grouping>" +
                          "</answer-groupings>" +
                          "<validation-type>boolean</validation-type>" +
                        "</read-only-question>" +
                        "<read-only-question>" +
                          "<id>Employee Headcount Change</id>" +
                          "<label>Increased/decreased headcount by more than 10%?</label>" +
                          "<answer-groupings>" +
                            "<answer-grouping>" +
                              "<answers>" +
                                "<profile-answer>" +
                                  "<id>Employee Headcount Change</id>" +
                                  "<response-id>5190</response-id>" +
                                  "<answer-time>2012-11-16 11:14:48.000</answer-time>" +
                                  "<answered-by>customer1</answered-by>" +
                                  "<answer-value>false</answer-value>" +
                                  "<is-accepted>true</is-accepted>" +
                                  "<answer-source>Vendor</answer-source>" +
                                "</profile-answer>" +
                              "</answers>" +
                            "</answer-grouping>" +
                          "</answer-groupings>" +
                          "<validation-type>boolean</validation-type>" +
                        "</read-only-question>" +
                        "<question-group>" +
                          "<id>Dun &amp; Bradstreet</id>" +
                          "<label>Dun &amp; Bradstreet</label>" +
                          "<answer-groupings/>" +
                          "<questions>" +
                            "<read-only-question>" +
                              "<id>SER</id>" +
                              "<label>D&amp;B Supplier Evaluation Risk (SER) score</label>" +
                              "<answer-groupings/>" +
                              "<validation-type>integer</validation-type>" +
                            "</read-only-question>" +
                            "<read-only-question>" +
                              "<id>Severe Risk</id>" +
                              "<label>Severe risk?</label>" +
                              "<answer-groupings>" +
                                "<answer-grouping>" +
                                  "<answers>" +
                                    "<profile-answer>" +
                                      "<id>Severe Risk</id>" +
                                      "<response-id>2473</response-id>" +
                                      "<answer-time>2011-01-31 14:16:20.000</answer-time>" +
                                      "<answered-by>GWT</answered-by>" +
                                      "<answer-value>false</answer-value>" +
                                      "<is-accepted>true</is-accepted>" +
                                      "<answer-source>Customer</answer-source>" +
                                    "</profile-answer>" +
                                  "</answers>" +
                                "</answer-grouping>" +
                              "</answer-groupings>" +
                              "<validation-type>boolean</validation-type>" +
                            "</read-only-question>" +
                            "<read-only-question>" +
                              "<id>Credit Appraisal</id>" +
                              "<label>D&amp;B Composite Credit Appraisal</label>" +
                              "<answer-groupings/>" +
                            "</read-only-question>" +
                            "<read-only-question>" +
                              "<id>Worth/Equity Rating</id>" +
                              "<label>Worth/Equity Rating</label>" +
                              "<answer-groupings/>" +
                            "</read-only-question>" +
                          "</questions>" +
                        "</question-group>" +
                        "<question-group>" +
                          "<id>Experian</id>" +
                          "<label>Experian</label>" +
                          "<answer-groupings/>" +
                          "<questions>" +
                            "<read-only-question>" +
                              "<id>Intelliscore Plus (Risk Score 0-100, 100 is lowest risk)</id>" +
                              "<label>Intelliscore Plus (Risk Score 0-100, 100 is lowest risk)</label>" +
                              "<answer-groupings/>" +
                              "<validation-type>float</validation-type>" +
                            "</read-only-question>" +
                            "<read-only-question>" +
                              "<id>% of businesses with that percentage</id>" +
                              "<label>% of businesses with that percentage</label>" +
                              "<answer-groupings/>" +
                              "<validation-type>percent</validation-type>" +
                            "</read-only-question>" +
                            "<read-only-question>" +
                              "<id>Experian Credit Rating</id>" +
                              "<label>Experian Credit Rating</label>" +
                              "<answer-groupings/>" +
                              "<validation-type>integer</validation-type>" +
                            "</read-only-question>" +
                          "</questions>" +
                        "</question-group>" +
                      "</questions>" +
                    "</question-category>" +
                    "<question-category>" +
                      "<label>Operational Structure and Agreements</label>" +
                      "<questions>" +
                        "<true-false-question>" +
                          "<id>Information Security Policy</id>" +
                          "<label>Does Dell have a formal and documented information security policy or policies?</label>" +
                          "<answer-groupings>" +
                            "<answer-grouping>" +
                              "<answers>" +
                                "<profile-answer>" +
                                  "<id>Information Security Policy</id>" +
                                  "<label>yes</label>" +
                                  "<answer-value>true</answer-value>" +
                                  "<is-accepted>false</is-accepted>" +
                                "</profile-answer>" +
                                "<profile-answer>" +
                                  "<id>Information Security Policy</id>" +
                                  "<label>no</label>" +
                                  "<answer-value>false</answer-value>" +
                                  "<is-accepted>false</is-accepted>" +
                                "</profile-answer>" +
                              "</answers>" +
                            "</answer-grouping>" +
                          "</answer-groupings>" +
                        "</true-false-question>" +
                        "<true-false-question>" +
                          "<id>CSO</id>" +
                          "<label>Does Dell have a formal chief information security officer, CSO, or formally assigned information security role?</label>" +
                          "<answer-groupings>" +
                            "<answer-grouping>" +
                              "<answers>" +
                                "<profile-answer>" +
                                  "<id>CSO</id>" +
                                  "<label>yes</label>" +
                                  "<answer-value>true</answer-value>" +
                                  "<is-accepted>false</is-accepted>" +
                                "</profile-answer>" +
                                "<profile-answer>" +
                                  "<id>CSO</id>" +
                                  "<response-id>2496</response-id>" +
                                  "<label>no</label>" +
                                  "<answer-time>2011-01-31 14:16:21.000</answer-time>" +
                                  "<answered-by>GWT</answered-by>" +
                                  "<answer-value>false</answer-value>" +
                                  "<is-accepted>true</is-accepted>" +
                                  "<answer-source>Customer</answer-source>" +
                                "</profile-answer>" +
                              "</answers>" +
                            "</answer-grouping>" +
                          "</answer-groupings>" +
                        "</true-false-question>" +
                        "<true-false-question>" +
                          "<id>Contract or Legal Agreement</id>" +
                          "<label>Is there a legal agreement in place between Dell and Dunder Mifflin?</label>" +
                          "<answer-groupings>" +
                            "<answer-grouping>" +
                              "<answers>" +
                                "<profile-answer>" +
                                  "<id>Contract or Legal Agreement</id>" +
                                  "<label>yes</label>" +
                                  "<answer-value>true</answer-value>" +
                                  "<is-accepted>false</is-accepted>" +
                                  "<subquestions>" +
                                    "<true-false-question>" +
                                      "<id>Data Protection Clause</id>" +
                                      "<label>Does the current agreement between Dell and Dunder Mifflin include a data protection clause?</label>" +
                                      "<answer-groupings>" +
                                        "<answer-grouping>" +
                                          "<answers>" +
                                            "<profile-answer>" +
                                              "<id>Data Protection Clause</id>" +
                                              "<response-id>2498</response-id>" +
                                              "<label>yes</label>" +
                                              "<answer-time>2011-01-31 14:16:21.000</answer-time>" +
                                              "<answered-by>GWT</answered-by>" +
                                              "<answer-value>true</answer-value>" +
                                              "<is-accepted>true</is-accepted>" +
                                              "<answer-source>Customer</answer-source>" +
                                            "</profile-answer>" +
                                            "<profile-answer>" +
                                              "<id>Data Protection Clause</id>" +
                                              "<label>no</label>" +
                                              "<answer-value>false</answer-value>" +
                                              "<is-accepted>false</is-accepted>" +
                                            "</profile-answer>" +
                                          "</answers>" +
                                        "</answer-grouping>" +
                                      "</answer-groupings>" +
                                    "</true-false-question>" +
                                    "<true-false-question>" +
                                      "<id>SLA</id>" +
                                      "<label>Does the current agreement between Dell and Dunder Mifflin include a service level agreement (SLA)?</label>" +
                                      "<answer-groupings>" +
                                        "<answer-grouping>" +
                                          "<answers>" +
                                            "<profile-answer>" +
                                              "<id>SLA</id>" +
                                              "<response-id>2499</response-id>" +
                                              "<label>yes</label>" +
                                              "<answer-time>2011-01-31 14:16:21.000</answer-time>" +
                                              "<answered-by>GWT</answered-by>" +
                                              "<answer-value>true</answer-value>" +
                                              "<is-accepted>true</is-accepted>" +
                                              "<answer-source>Customer</answer-source>" +
                                              "<subquestions>" +
                                                "<choose-one-question>" +
                                                  "<id>RTO</id>" +
                                                  "<label>What is the recovery time objective (RTO) specified by the SLA?</label>" +
                                                  "<answer-groupings>" +
                                                    "<answer-grouping>" +
                                                      "<answers>" +
                                                        "<profile-answer>" +
                                                          "<id>RTO Under 1 Hour</id>" +
                                                          "<label>1 hour or less</label>" +
                                                          "<answer-value>false</answer-value>" +
                                                          "<is-accepted>false</is-accepted>" +
                                                        "</profile-answer>" +
                                                        "<profile-answer>" +
                                                          "<id>RTO Under 8 Hours</id>" +
                                                          "<label>8  hours or less</label>" +
                                                          "<answer-value>false</answer-value>" +
                                                          "<is-accepted>false</is-accepted>" +
                                                        "</profile-answer>" +
                                                        "<profile-answer>" +
                                                          "<id>RTO Under 1 Day</id>" +
                                                          "<label>24 hours or less</label>" +
                                                          "<answer-value>false</answer-value>" +
                                                          "<is-accepted>false</is-accepted>" +
                                                        "</profile-answer>" +
                                                        "<profile-answer>" +
                                                          "<id>RTO Over 1 Day</id>" +
                                                          "<label>Over 24 hours</label>" +
                                                          "<answer-value>false</answer-value>" +
                                                          "<is-accepted>false</is-accepted>" +
                                                        "</profile-answer>" +
                                                        "<profile-answer>" +
                                                          "<id>No RTO</id>" +
                                                          "<label>No RTO has been established</label>" +
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
                                              "<id>SLA</id>" +
                                              "<label>no</label>" +
                                              "<answer-value>false</answer-value>" +
                                              "<is-accepted>false</is-accepted>" +
                                            "</profile-answer>" +
                                          "</answers>" +
                                        "</answer-grouping>" +
                                      "</answer-groupings>" +
                                    "</true-false-question>" +
                                  "</subquestions>" +
                                "</profile-answer>" +
                                "<profile-answer>" +
                                  "<id>Contract or Legal Agreement</id>" +
                                  "<response-id>2497</response-id>" +
                                  "<label>no</label>" +
                                  "<answer-time>2011-01-31 14:16:21.000</answer-time>" +
                                  "<answered-by>GWT</answered-by>" +
                                  "<answer-value>false</answer-value>" +
                                  "<is-accepted>true</is-accepted>" +
                                  "<answer-source>Customer</answer-source>" +
                                "</profile-answer>" +
                              "</answers>" +
                            "</answer-grouping>" +
                          "</answer-groupings>" +
                        "</true-false-question>" +
                        "<true-false-question>" +
                          "<id>Business Agreement</id>" +
                          "<label>Is there an executed business associate agreement between Dell and Dunder Mifflin?</label>" +
                          "<answer-groupings>" +
                            "<answer-grouping>" +
                              "<answers>" +
                                "<profile-answer>" +
                                  "<id>Business Agreement</id>" +
                                  "<label>yes</label>" +
                                  "<answer-value>true</answer-value>" +
                                  "<is-accepted>false</is-accepted>" +
                                "</profile-answer>" +
                                "<profile-answer>" +
                                  "<id>Business Agreement</id>" +
                                  "<label>no</label>" +
                                  "<answer-value>false</answer-value>" +
                                  "<is-accepted>false</is-accepted>" +
                                "</profile-answer>" +
                              "</answers>" +
                            "</answer-grouping>" +
                          "</answer-groupings>" +
                        "</true-false-question>" +
                        "<choose-one-question>" +
                          "<id>Customer Data Breach</id>" +
                          "<label>Does Dell have any history of incidents involving loss or disclosure of Dunder Mifflin data or an incident that would constitute a data breach?</label>" +
                          "<answer-groupings>" +
                            "<answer-grouping>" +
                              "<answers>" +
                                "<profile-answer>" +
                                  "<id>No Direct History of Incidents</id>" +
                                  "<response-id>4160</response-id>" +
                                  "<label>None</label>" +
                                  "<answer-time>2011-01-31 14:16:19.000</answer-time>" +
                                  "<answered-by>GWT</answered-by>" +
                                  "<answer-value>true</answer-value>" +
                                  "<is-accepted>true</is-accepted>" +
                                  "<answer-source>Customer</answer-source>" +
                                "</profile-answer>" +
                                "<profile-answer>" +
                                  "<id>Incident Involving Organization&apos;s Data Within 1 Year</id>" +
                                  "<label>Within the last 12 months</label>" +
                                  "<answer-value>false</answer-value>" +
                                  "<is-accepted>false</is-accepted>" +
                                "</profile-answer>" +
                                "<profile-answer>" +
                                  "<id>Incident Involving Data More Than 1 Year</id>" +
                                  "<label>More than one year ago</label>" +
                                  "<answer-value>false</answer-value>" +
                                  "<is-accepted>false</is-accepted>" +
                                "</profile-answer>" +
                              "</answers>" +
                            "</answer-grouping>" +
                          "</answer-groupings>" +
                        "</choose-one-question>" +
                        "<choose-one-question>" +
                          "<id>Public Data Breach</id>" +
                          "<label>Does Dell have any public history of reported data breaches for any other organizations?:</label>" +
                          "<answer-groupings>" +
                            "<answer-grouping>" +
                              "<answers>" +
                                "<profile-answer>" +
                                  "<id>No Public History of Data Breaches</id>" +
                                  "<response-id>4223</response-id>" +
                                  "<label>None</label>" +
                                  "<answer-time>2011-01-31 14:16:19.000</answer-time>" +
                                  "<answered-by>GWT</answered-by>" +
                                  "<answer-value>true</answer-value>" +
                                  "<is-accepted>true</is-accepted>" +
                                  "<answer-source>Customer</answer-source>" +
                                "</profile-answer>" +
                                "<profile-answer>" +
                                  "<id>Public Data Breach Within 1 Year</id>" +
                                  "<label>Within the last 12 months</label>" +
                                  "<answer-value>false</answer-value>" +
                                  "<is-accepted>false</is-accepted>" +
                                "</profile-answer>" +
                                "<profile-answer>" +
                                  "<id>Public Data Breach More Than 1 Year</id>" +
                                  "<label>More than one year ago</label>" +
                                  "<answer-value>false</answer-value>" +
                                  "<is-accepted>false</is-accepted>" +
                                "</profile-answer>" +
                              "</answers>" +
                            "</answer-grouping>" +
                          "</answer-groupings>" +
                        "</choose-one-question>" +
                        "<question-group>" +
                          "<id>Service Criticality</id>" +
                          "<label>How critical are Dell services to support the</label>" +
                          "<answer-groupings/>" +
                          "<questions>" +
                            "<choose-one-question>" +
                              "<id>Delivery Of Goods</id>" +
                              "<label>Delivery of goods to Dunder Mifflin customers or end-user population</label>" +
                              "<answer-groupings>" +
                                "<answer-grouping>" +
                                  "<answers>" +
                                    "<profile-answer>" +
                                      "<id>High Delivery Criticality</id>" +
                                      "<label>Critical</label>" +
                                      "<answer-value>false</answer-value>" +
                                      "<is-accepted>false</is-accepted>" +
                                    "</profile-answer>" +
                                    "<profile-answer>" +
                                      "<id>Low Delivery Criticality</id>" +
                                      "<label>Somewhat Critical</label>" +
                                      "<answer-value>false</answer-value>" +
                                      "<is-accepted>false</is-accepted>" +
                                    "</profile-answer>" +
                                    "<profile-answer>" +
                                      "<id>No Delivery Criticality</id>" +
                                      "<label>No Impact</label>" +
                                      "<answer-value>false</answer-value>" +
                                      "<is-accepted>false</is-accepted>" +
                                    "</profile-answer>" +
                                  "</answers>" +
                                "</answer-grouping>" +
                              "</answer-groupings>" +
                            "</choose-one-question>" +
                            "<choose-one-question>" +
                              "<id>Internal Financial Systems</id>" +
                              "<label>Internal financial systems and processes</label>" +
                              "<answer-groupings>" +
                                "<answer-grouping>" +
                                  "<answers>" +
                                    "<profile-answer>" +
                                      "<id>High FS Criticality</id>" +
                                      "<label>Critical</label>" +
                                      "<answer-value>false</answer-value>" +
                                      "<is-accepted>false</is-accepted>" +
                                    "</profile-answer>" +
                                    "<profile-answer>" +
                                      "<id>Low FS Criticality</id>" +
                                      "<label>Somewhat Critical</label>" +
                                      "<answer-value>false</answer-value>" +
                                      "<is-accepted>false</is-accepted>" +
                                    "</profile-answer>" +
                                    "<profile-answer>" +
                                      "<id>No FS Criticality</id>" +
                                      "<label>No Impact</label>" +
                                      "<answer-value>false</answer-value>" +
                                      "<is-accepted>false</is-accepted>" +
                                    "</profile-answer>" +
                                  "</answers>" +
                                "</answer-grouping>" +
                              "</answer-groupings>" +
                            "</choose-one-question>" +
                            "<choose-one-question>" +
                              "<id>IT Operations</id>" +
                              "<label>IT operations and processes</label>" +
                              "<answer-groupings>" +
                                "<answer-grouping>" +
                                  "<answers>" +
                                    "<profile-answer>" +
                                      "<id>High IT_Ops Criticality</id>" +
                                      "<label>Critical</label>" +
                                      "<answer-value>false</answer-value>" +
                                      "<is-accepted>false</is-accepted>" +
                                    "</profile-answer>" +
                                    "<profile-answer>" +
                                      "<id>Low IT_Ops Criticality</id>" +
                                      "<label>Somewhat Critical</label>" +
                                      "<answer-value>false</answer-value>" +
                                      "<is-accepted>false</is-accepted>" +
                                    "</profile-answer>" +
                                    "<profile-answer>" +
                                      "<id>No IT_Ops Criticality</id>" +
                                      "<label>No Impact</label>" +
                                      "<answer-value>false</answer-value>" +
                                      "<is-accepted>false</is-accepted>" +
                                    "</profile-answer>" +
                                  "</answers>" +
                                "</answer-grouping>" +
                              "</answer-groupings>" +
                            "</choose-one-question>" +
                            "<choose-one-question>" +
                              "<id>Facilities Supporting Customer</id>" +
                              "<label>Facilities supporting Dunder Mifflin </label>" +
                              "<answer-groupings>" +
                                "<answer-grouping>" +
                                  "<answers>" +
                                    "<profile-answer>" +
                                      "<id>High Facilities Criticality</id>" +
                                      "<label>Critical</label>" +
                                      "<answer-value>false</answer-value>" +
                                      "<is-accepted>false</is-accepted>" +
                                    "</profile-answer>" +
                                    "<profile-answer>" +
                                      "<id>Low Facilities Criticality</id>" +
                                      "<label>Somewhat Critical</label>" +
                                      "<answer-value>false</answer-value>" +
                                      "<is-accepted>false</is-accepted>" +
                                    "</profile-answer>" +
                                    "<profile-answer>" +
                                      "<id>No Facilities Criticality</id>" +
                                      "<label>No Impact</label>" +
                                      "<answer-value>false</answer-value>" +
                                      "<is-accepted>false</is-accepted>" +
                                    "</profile-answer>" +
                                  "</answers>" +
                                "</answer-grouping>" +
                              "</answer-groupings>" +
                            "</choose-one-question>" +
                            "<choose-one-question>" +
                              "<id>Customer Employee Health</id>" +
                              "<label>Safety, health, or well-being of Dunder Mifflin employees</label>" +
                              "<answer-groupings>" +
                                "<answer-grouping>" +
                                  "<answers>" +
                                    "<profile-answer>" +
                                      "<id>High Health_Safety Criticality</id>" +
                                      "<label>Critical</label>" +
                                      "<answer-value>false</answer-value>" +
                                      "<is-accepted>false</is-accepted>" +
                                    "</profile-answer>" +
                                    "<profile-answer>" +
                                      "<id>Low Health_Safety Criticality</id>" +
                                      "<label>Somewhat Critical</label>" +
                                      "<answer-value>false</answer-value>" +
                                      "<is-accepted>false</is-accepted>" +
                                    "</profile-answer>" +
                                    "<profile-answer>" +
                                      "<id>No Health_Safety Criticality</id>" +
                                      "<label>No Impact</label>" +
                                      "<answer-value>false</answer-value>" +
                                      "<is-accepted>false</is-accepted>" +
                                    "</profile-answer>" +
                                  "</answers>" +
                                "</answer-grouping>" +
                              "</answer-groupings>" +
                            "</choose-one-question>" +
                          "</questions>" +
                        "</question-group>" +
                        "<fill-in-the-blank-question>" +
                          "<id>Customer Revenue Dependency</id>" +
                          "<label>What percentage of annual revenue is Dunder Mifflin dependent on Dell services?</label>" +
                          "<answer-groupings/>" +
                          "<validation-type>percent</validation-type>" +
                        "</fill-in-the-blank-question>" +
                      "</questions>" +
                    "</question-category>" +
                    "<question-category>" +
                      "<label>Facility and Geographic Information</label>" +
                      "<questions>" +
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
                        "</choose-many-question>" +
                        "<choose-many-question>" +
                          "<id>Location Demographics</id>" +
                          "<label>Dell provides services from a location with the following demographics:</label>" +
                          "<answer-groupings>" +
                            "<answer-grouping>" +
                              "<answers>" +
                                "<profile-answer>" +
                                  "<id>High Regulatory</id>" +
                                  "<response-id>5150</response-id>" +
                                  "<label>Strict regulatory requirements</label>" +
                                  "<answer-time>2012-11-15 10:11:06.000</answer-time>" +
                                  "<answered-by>customer1</answered-by>" +
                                  "<answer-value>false</answer-value>" +
                                  "<is-accepted>true</is-accepted>" +
                                  "<answer-source>Vendor</answer-source>" +
                                "</profile-answer>" +
                                "<profile-answer>" +
                                  "<id>Travel Alert</id>" +
                                  "<response-id>5151</response-id>" +
                                  "<label>U.S. travel alert or warning</label>" +
                                  "<answer-time>2012-11-15 10:11:06.000</answer-time>" +
                                  "<answered-by>customer1</answered-by>" +
                                  "<answer-value>false</answer-value>" +
                                  "<is-accepted>true</is-accepted>" +
                                  "<answer-source>Vendor</answer-source>" +
                                "</profile-answer>" +
                                "<profile-answer>" +
                                  "<id>High Crime Index</id>" +
                                  "<response-id>2439</response-id>" +
                                  "<label>Higher crime rate</label>" +
                                  "<answer-time>2011-01-31 14:16:19.000</answer-time>" +
                                  "<answered-by>GWT</answered-by>" +
                                  "<answer-value>false</answer-value>" +
                                  "<is-accepted>true</is-accepted>" +
                                  "<answer-source>Customer</answer-source>" +
                                "</profile-answer>" +
                                "<profile-answer>" +
                                  "<id>High Cyber Crime Index</id>" +
                                  "<response-id>2440</response-id>" +
                                  "<label>Higher electronic/cyber-crime rate</label>" +
                                  "<answer-time>2011-01-31 14:16:19.000</answer-time>" +
                                  "<answered-by>GWT</answered-by>" +
                                  "<answer-value>false</answer-value>" +
                                  "<is-accepted>true</is-accepted>" +
                                  "<answer-source>Customer</answer-source>" +
                                "</profile-answer>" +
                                "<profile-answer>" +
                                  "<id>Government Risk</id>" +
                                  "<response-id>2437</response-id>" +
                                  "<label>Higher likelihood of government espionage</label>" +
                                  "<answer-time>2011-01-31 14:16:18.000</answer-time>" +
                                  "<answered-by>GWT</answered-by>" +
                                  "<answer-value>false</answer-value>" +
                                  "<is-accepted>true</is-accepted>" +
                                  "<answer-source>Customer</answer-source>" +
                                "</profile-answer>" +
                                "<profile-answer>" +
                                  "<id>Geopolitical Risk</id>" +
                                  "<response-id>2435</response-id>" +
                                  "<label>Potentially unstable government</label>" +
                                  "<answer-time>2011-01-31 14:16:18.000</answer-time>" +
                                  "<answered-by>GWT</answered-by>" +
                                  "<answer-value>false</answer-value>" +
                                  "<is-accepted>true</is-accepted>" +
                                  "<answer-source>Customer</answer-source>" +
                                "</profile-answer>" +
                                "<profile-answer>" +
                                  "<id>Telecom Risk</id>" +
                                  "<response-id>2487</response-id>" +
                                  "<label>Higher likelihood of telecommunication failure</label>" +
                                  "<answer-time>2011-01-31 14:16:20.000</answer-time>" +
                                  "<answered-by>GWT</answered-by>" +
                                  "<answer-value>false</answer-value>" +
                                  "<is-accepted>true</is-accepted>" +
                                  "<answer-source>Customer</answer-source>" +
                                "</profile-answer>" +
                                "<profile-answer>" +
                                  "<id>Utility Risk</id>" +
                                  "<response-id>2493</response-id>" +
                                  "<label>Higher likelihood of utility failure</label>" +
                                  "<answer-time>2011-01-31 14:16:20.000</answer-time>" +
                                  "<answered-by>GWT</answered-by>" +
                                  "<answer-value>false</answer-value>" +
                                  "<is-accepted>true</is-accepted>" +
                                  "<answer-source>Customer</answer-source>" +
                                "</profile-answer>" +
                                "<profile-answer>" +
                                  "<id>Transportation Risk</id>" +
                                  "<response-id>2489</response-id>" +
                                  "<label>Higher likelihood of transportation disruption</label>" +
                                  "<answer-time>2011-01-31 14:16:20.000</answer-time>" +
                                  "<answered-by>GWT</answered-by>" +
                                  "<answer-value>false</answer-value>" +
                                  "<is-accepted>true</is-accepted>" +
                                  "<answer-source>Customer</answer-source>" +
                                "</profile-answer>" +
                              "</answers>" +
                            "</answer-grouping>" +
                          "</answer-groupings>" +
                        "</choose-many-question>" +
                        "<choose-many-question>" +
                          "<id>Environmental Characteristics</id>" +
                          "<label>Dell provides services from a location with a higher likelihood of the following environmental characteristics:</label>" +
                          "<answer-groupings>" +
                            "<answer-grouping>" +
                              "<answers>" +
                                "<profile-answer>" +
                                  "<id>Weather Risk</id>" +
                                  "<response-id>5152</response-id>" +
                                  "<label>Severe weather</label>" +
                                  "<answer-time>2012-11-15 10:11:07.000</answer-time>" +
                                  "<answered-by>customer1</answered-by>" +
                                  "<answer-value>false</answer-value>" +
                                  "<is-accepted>true</is-accepted>" +
                                  "<answer-source>Vendor</answer-source>" +
                                "</profile-answer>" +
                                "<profile-answer>" +
                                  "<id>Natural Disaster Risk</id>" +
                                  "<response-id>2454</response-id>" +
                                  "<label>Natural disaster</label>" +
                                  "<answer-time>2011-01-31 14:16:19.000</answer-time>" +
                                  "<answered-by>GWT</answered-by>" +
                                  "<answer-value>false</answer-value>" +
                                  "<is-accepted>true</is-accepted>" +
                                  "<answer-source>Customer</answer-source>" +
                                "</profile-answer>" +
                                "<profile-answer>" +
                                  "<id>Disease Risk</id>" +
                                  "<response-id>2427</response-id>" +
                                  "<label>Communicable diseases</label>" +
                                  "<answer-time>2011-01-31 14:16:18.000</answer-time>" +
                                  "<answered-by>GWT</answered-by>" +
                                  "<answer-value>false</answer-value>" +
                                  "<is-accepted>true</is-accepted>" +
                                  "<answer-source>Customer</answer-source>" +
                                "</profile-answer>" +
                              "</answers>" +
                            "</answer-grouping>" +
                          "</answer-groupings>" +
                        "</choose-many-question>" +
                        "<choose-many-question>" +
                          "<id>Service Facilities</id>" +
                          "<label>Dell facilities used to provide services are:</label>" +
                          "<answer-groupings>" +
                            "<answer-grouping>" +
                              "<answers>" +
                                "<profile-answer>" +
                                  "<id>Leased</id>" +
                                  "<response-id>2445</response-id>" +
                                  "<label>Leased</label>" +
                                  "<answer-time>2011-01-31 14:16:19.000</answer-time>" +
                                  "<answered-by>GWT</answered-by>" +
                                  "<answer-value>false</answer-value>" +
                                  "<is-accepted>true</is-accepted>" +
                                  "<answer-source>Customer</answer-source>" +
                                "</profile-answer>" +
                                "<profile-answer>" +
                                  "<id>MultiFacility</id>" +
                                  "<response-id>2452</response-id>" +
                                  "<label>Multi-tenant</label>" +
                                  "<answer-time>2011-01-31 14:16:19.000</answer-time>" +
                                  "<answered-by>GWT</answered-by>" +
                                  "<answer-value>true</answer-value>" +
                                  "<is-accepted>true</is-accepted>" +
                                  "<answer-source>Customer</answer-source>" +
                                "</profile-answer>" +
                                "<profile-answer>" +
                                  "<id>Owned</id>" +
                                  "<label>Owned</label>" +
                                  "<is-accepted>false</is-accepted>" +
                                "</profile-answer>" +
                              "</answers>" +
                            "</answer-grouping>" +
                          "</answer-groupings>" +
                        "</choose-many-question>" +
                      "</questions>" +
                    "</question-category>" +
                    "<question-category>" +
                      "<label>Services Profile</label>" +
                      "<questions>" +
                        "<choose-one-question>" +
                          "<id>Branding</id>" +
                          "<label>Dell provide services branded as:</label>" +
                          "<answer-groupings>" +
                            "<answer-grouping>" +
                              "<answers>" +
                                "<profile-answer>" +
                                  "<id>Branded</id>" +
                                  "<response-id>4317</response-id>" +
                                  "<label>Its&apos; own (Dell)</label>" +
                                  "<answer-time>2011-01-31 14:16:17.000</answer-time>" +
                                  "<answered-by>GWT</answered-by>" +
                                  "<answer-value>true</answer-value>" +
                                  "<is-accepted>true</is-accepted>" +
                                  "<answer-source>Customer</answer-source>" +
                                "</profile-answer>" +
                                "<profile-answer>" +
                                  "<id>DBA Branding</id>" +
                                  "<label>Dunder Mifflin</label>" +
                                  "<answer-value>false</answer-value>" +
                                  "<is-accepted>false</is-accepted>" +
                                "</profile-answer>" +
                                "<profile-answer>" +
                                  "<id>Cobranded</id>" +
                                  "<label>Cobranded as both Dunder Mifflin and Dell</label>" +
                                  "<answer-value>false</answer-value>" +
                                  "<is-accepted>false</is-accepted>" +
                                "</profile-answer>" +
                              "</answers>" +
                            "</answer-grouping>" +
                          "</answer-groupings>" +
                        "</choose-one-question>" +
                        "<choose-one-question>" +
                          "<id>Service Location</id>" +
                          "<label>Dell provides services from:</label>" +
                          "<answer-groupings>" +
                            "<answer-grouping>" +
                              "<answers>" +
                                "<profile-answer>" +
                                  "<id>Onsite Location</id>" +
                                  "<label>A location owned by Dunder Mifflin</label>" +
                                  "<answer-value>false</answer-value>" +
                                  "<is-accepted>false</is-accepted>" +
                                "</profile-answer>" +
                                "<profile-answer>" +
                                  "<id>Shared Location</id>" +
                                  "<label>A location shared by Dunder Mifflin and Dell</label>" +
                                  "<answer-value>false</answer-value>" +
                                  "<is-accepted>false</is-accepted>" +
                                "</profile-answer>" +
                                "<profile-answer>" +
                                  "<id>Offsite Location</id>" +
                                  "<response-id>4376</response-id>" +
                                  "<label>A location not owned by Dunder Mifflin</label>" +
                                  "<answer-time>2011-01-31 14:16:19.000</answer-time>" +
                                  "<answered-by>GWT</answered-by>" +
                                  "<answer-value>true</answer-value>" +
                                  "<is-accepted>true</is-accepted>" +
                                  "<answer-source>Customer</answer-source>" +
                                "</profile-answer>" +
                              "</answers>" +
                            "</answer-grouping>" +
                          "</answer-groupings>" +
                        "</choose-one-question>" +
                        "<choose-one-question>" +
                          "<id>Service Building Location</id>" +
                          "<label>This location is in:</label>" +
                          "<answer-groupings>" +
                            "<answer-grouping>" +
                              "<answers>" +
                                "<profile-answer>" +
                                  "<id>Building Facility</id>" +
                                  "<response-id>4423</response-id>" +
                                  "<label>A building dedicated to Dell</label>" +
                                  "<answer-time>2011-01-31 14:16:17.000</answer-time>" +
                                  "<answered-by>GWT</answered-by>" +
                                  "<answer-value>true</answer-value>" +
                                  "<is-accepted>true</is-accepted>" +
                                  "<answer-source>Customer</answer-source>" +
                                "</profile-answer>" +
                                "<profile-answer>" +
                                  "<id>Floor Facility</id>" +
                                  "<label>A dedicated floor of a multi-tenant building</label>" +
                                  "<answer-value>false</answer-value>" +
                                  "<is-accepted>false</is-accepted>" +
                                "</profile-answer>" +
                                "<profile-answer>" +
                                  "<id>Office Facility</id>" +
                                  "<label>An office/suite of a multi-tenant building</label>" +
                                  "<answer-value>false</answer-value>" +
                                  "<is-accepted>false</is-accepted>" +
                                "</profile-answer>" +
                                "<profile-answer>" +
                                  "<id>Campus Facility</id>" +
                                  "<label>A multi-building campus operated by entity</label>" +
                                  "<answer-value>false</answer-value>" +
                                  "<is-accepted>false</is-accepted>" +
                                "</profile-answer>" +
                                "<profile-answer>" +
                                  "<id>Offshore Location</id>" +
                                  "<label>Outside of the United States</label>" +
                                  "<answer-value>false</answer-value>" +
                                  "<is-accepted>false</is-accepted>" +
                                "</profile-answer>" +
                              "</answers>" +
                            "</answer-grouping>" +
                          "</answer-groupings>" +
                        "</choose-one-question>" +
                        "<choose-one-question>" +
                          "<id>Building Access</id>" +
                          "<label>Dell personnel have access to the Dunder Mifflin premises:</label>" +
                          "<answer-groupings>" +
                            "<answer-grouping>" +
                              "<answers>" +
                                "<profile-answer>" +
                                  "<id>Unescorted Access</id>" +
                                  "<label>Unescorted</label>" +
                                  "<answer-value>false</answer-value>" +
                                  "<is-accepted>false</is-accepted>" +
                                "</profile-answer>" +
                                "<profile-answer>" +
                                  "<id>Escorted Access</id>" +
                                  "<label>Only if accompanied by an escort</label>" +
                                  "<answer-value>false</answer-value>" +
                                  "<is-accepted>false</is-accepted>" +
                                "</profile-answer>" +
                              "</answers>" +
                            "</answer-grouping>" +
                          "</answer-groupings>" +
                        "</choose-one-question>" +
                        "<choose-one-question>" +
                          "<id>Access Connection</id>" +
                          "<label>Dell connects to Dunder Mifflin systems or networks</label>" +
                          "<answer-groupings>" +
                            "<answer-grouping>" +
                              "<answers>" +
                                "<profile-answer>" +
                                  "<id>Connected With a Firewall</id>" +
                                  "<label>With a firewall</label>" +
                                  "<answer-value>false</answer-value>" +
                                  "<is-accepted>false</is-accepted>" +
                                "</profile-answer>" +
                                "<profile-answer>" +
                                  "<id>Connected Without Firewall</id>" +
                                  "<label>Without a firewall</label>" +
                                  "<answer-value>false</answer-value>" +
                                  "<is-accepted>false</is-accepted>" +
                                "</profile-answer>" +
                              "</answers>" +
                            "</answer-grouping>" +
                          "</answer-groupings>" +
                        "</choose-one-question>" +
                        "<choose-many-question>" +
                          "<id>Network Access</id>" +
                          "<label>Dell personnel has access to Dunder Mifflin systems or networks:</label>" +
                          "<answer-groupings>" +
                            "<answer-grouping>" +
                              "<answers>" +
                                "<profile-answer>" +
                                  "<id>Corporate Network Access</id>" +
                                  "<response-id>2407</response-id>" +
                                  "<label>Corporate network</label>" +
                                  "<answer-time>2011-01-31 14:16:17.000</answer-time>" +
                                  "<answered-by>GWT</answered-by>" +
                                  "<answer-value>false</answer-value>" +
                                  "<is-accepted>true</is-accepted>" +
                                  "<answer-source>Customer</answer-source>" +
                                "</profile-answer>" +
                                "<profile-answer>" +
                                  "<id>Wan Access</id>" +
                                  "<response-id>2495</response-id>" +
                                  "<label>Wide-area network (WAN)</label>" +
                                  "<answer-time>2011-01-31 14:16:21.000</answer-time>" +
                                  "<answered-by>GWT</answered-by>" +
                                  "<answer-value>false</answer-value>" +
                                  "<is-accepted>true</is-accepted>" +
                                  "<answer-source>Customer</answer-source>" +
                                "</profile-answer>" +
                                "<profile-answer>" +
                                  "<id>VPN Access</id>" +
                                  "<response-id>2494</response-id>" +
                                  "<label>VPN</label>" +
                                  "<answer-time>2011-01-31 14:16:20.000</answer-time>" +
                                  "<answered-by>GWT</answered-by>" +
                                  "<answer-value>false</answer-value>" +
                                  "<is-accepted>true</is-accepted>" +
                                  "<answer-source>Customer</answer-source>" +
                                "</profile-answer>" +
                                "<profile-answer>" +
                                  "<id>DMZ Access</id>" +
                                  "<response-id>2428</response-id>" +
                                  "<label>Internet-connected DMZ</label>" +
                                  "<answer-time>2011-01-31 14:16:18.000</answer-time>" +
                                  "<answered-by>GWT</answered-by>" +
                                  "<answer-value>false</answer-value>" +
                                  "<is-accepted>true</is-accepted>" +
                                  "<answer-source>Customer</answer-source>" +
                                "</profile-answer>" +
                                "<profile-answer>" +
                                  "<id>Network Device Access</id>" +
                                  "<response-id>2455</response-id>" +
                                  "<label>Network devices</label>" +
                                  "<answer-time>2011-01-31 14:16:19.000</answer-time>" +
                                  "<answered-by>GWT</answered-by>" +
                                  "<answer-value>false</answer-value>" +
                                  "<is-accepted>true</is-accepted>" +
                                  "<answer-source>Customer</answer-source>" +
                                "</profile-answer>" +
                                "<profile-answer>" +
                                  "<id>Server Access</id>" +
                                  "<response-id>2472</response-id>" +
                                  "<label>Servers (e.g., administrative or developer access)</label>" +
                                  "<answer-time>2011-01-31 14:16:20.000</answer-time>" +
                                  "<answered-by>GWT</answered-by>" +
                                  "<answer-value>false</answer-value>" +
                                  "<is-accepted>true</is-accepted>" +
                                  "<answer-source>Customer</answer-source>" +
                                "</profile-answer>" +
                                "<profile-answer>" +
                                  "<id>Messaging Access</id>" +
                                  "<response-id>2449</response-id>" +
                                  "<label>Electronic messaging systems (e.g., email, IM)</label>" +
                                  "<answer-time>2011-01-31 14:16:19.000</answer-time>" +
                                  "<answered-by>GWT</answered-by>" +
                                  "<answer-value>false</answer-value>" +
                                  "<is-accepted>true</is-accepted>" +
                                  "<answer-source>Customer</answer-source>" +
                                "</profile-answer>" +
                                "<profile-answer>" +
                                  "<id>Desktop Access</id>" +
                                  "<response-id>2425</response-id>" +
                                  "<label>Desktop and/or laptop systems</label>" +
                                  "<answer-time>2011-01-31 14:16:18.000</answer-time>" +
                                  "<answered-by>GWT</answered-by>" +
                                  "<answer-value>false</answer-value>" +
                                  "<is-accepted>true</is-accepted>" +
                                  "<answer-source>Customer</answer-source>" +
                                "</profile-answer>" +
                                "<profile-answer>" +
                                  "<id>Mobile Device Access</id>" +
                                  "<response-id>2450</response-id>" +
                                  "<label>Mobile devices (e.g., smart phones)</label>" +
                                  "<answer-time>2011-01-31 14:16:19.000</answer-time>" +
                                  "<answered-by>GWT</answered-by>" +
                                  "<answer-value>false</answer-value>" +
                                  "<is-accepted>true</is-accepted>" +
                                  "<answer-source>Customer</answer-source>" +
                                "</profile-answer>" +
                              "</answers>" +
                            "</answer-grouping>" +
                          "</answer-groupings>" +
                        "</choose-many-question>" +
                        "<choose-many-question>" +
                          "<id>Vendor Facility Services</id>" +
                          "<label>Dell services rely on the following, for which Dell is responsible:</label>" +
                          "<answer-groupings>" +
                            "<answer-grouping>" +
                              "<answers>" +
                                "<profile-answer>" +
                                  "<id>Dependency Provides Backup Electrical</id>" +
                                  "<response-id>2417</response-id>" +
                                  "<label>UPSs or generators</label>" +
                                  "<answer-time>2011-01-31 14:16:18.000</answer-time>" +
                                  "<answered-by>GWT</answered-by>" +
                                  "<answer-value>false</answer-value>" +
                                  "<is-accepted>true</is-accepted>" +
                                  "<answer-source>Customer</answer-source>" +
                                "</profile-answer>" +
                                "<profile-answer>" +
                                  "<id>Dependency Provides Facility Access</id>" +
                                  "<response-id>2418</response-id>" +
                                  "<label>Facility access control</label>" +
                                  "<answer-time>2011-01-31 14:16:18.000</answer-time>" +
                                  "<answered-by>GWT</answered-by>" +
                                  "<answer-value>false</answer-value>" +
                                  "<is-accepted>true</is-accepted>" +
                                  "<answer-source>Customer</answer-source>" +
                                "</profile-answer>" +
                                "<profile-answer>" +
                                  "<id>Dependency Provides Facility Staff</id>" +
                                  "<response-id>2419</response-id>" +
                                  "<label>Facility security staff</label>" +
                                  "<answer-time>2011-01-31 14:16:18.000</answer-time>" +
                                  "<answered-by>GWT</answered-by>" +
                                  "<answer-value>false</answer-value>" +
                                  "<is-accepted>true</is-accepted>" +
                                  "<answer-source>Customer</answer-source>" +
                                "</profile-answer>" +
                                "<profile-answer>" +
                                  "<id>Dependency Provides Fire Alarm</id>" +
                                  "<response-id>2420</response-id>" +
                                  "<label>Fire alarm monitoring</label>" +
                                  "<answer-time>2011-01-31 14:16:18.000</answer-time>" +
                                  "<answered-by>GWT</answered-by>" +
                                  "<answer-value>false</answer-value>" +
                                  "<is-accepted>true</is-accepted>" +
                                  "<answer-source>Customer</answer-source>" +
                                "</profile-answer>" +
                                "<profile-answer>" +
                                  "<id>Dependency Provides Fire Suppression</id>" +
                                  "<response-id>2421</response-id>" +
                                  "<label>Fire suppression</label>" +
                                  "<answer-time>2011-01-31 14:16:18.000</answer-time>" +
                                  "<answered-by>GWT</answered-by>" +
                                  "<answer-value>false</answer-value>" +
                                  "<is-accepted>true</is-accepted>" +
                                  "<answer-source>Customer</answer-source>" +
                                "</profile-answer>" +
                                "<profile-answer>" +
                                  "<id>Dependency Provides Security Alarm</id>" +
                                  "<response-id>2422</response-id>" +
                                  "<label>Security alarm monitoring</label>" +
                                  "<answer-time>2011-01-31 14:16:18.000</answer-time>" +
                                  "<answered-by>GWT</answered-by>" +
                                  "<answer-value>false</answer-value>" +
                                  "<is-accepted>true</is-accepted>" +
                                  "<answer-source>Customer</answer-source>" +
                                "</profile-answer>" +
                                "<profile-answer>" +
                                  "<id>Dependency Provides Surveillance</id>" +
                                  "<response-id>2423</response-id>" +
                                  "<label>Surveillance systems</label>" +
                                  "<answer-time>2011-01-31 14:16:18.000</answer-time>" +
                                  "<answered-by>GWT</answered-by>" +
                                  "<answer-value>false</answer-value>" +
                                  "<is-accepted>true</is-accepted>" +
                                  "<answer-source>Customer</answer-source>" +
                                "</profile-answer>" +
                                "<profile-answer>" +
                                  "<id>DependencyProvides Utility Electrical</id>" +
                                  "<response-id>2424</response-id>" +
                                  "<label>Electrical utilities</label>" +
                                  "<answer-time>2011-01-31 14:16:18.000</answer-time>" +
                                  "<answered-by>GWT</answered-by>" +
                                  "<answer-value>false</answer-value>" +
                                  "<is-accepted>true</is-accepted>" +
                                  "<answer-source>Customer</answer-source>" +
                                "</profile-answer>" +
                              "</answers>" +
                            "</answer-grouping>" +
                          "</answer-groupings>" +
                        "</choose-many-question>" +
                        "<choose-many-question>" +
                          "<id>Data Mode</id>" +
                          "<label>Dell is involved in:</label>" +
                          "<answer-groupings>" +
                            "<answer-grouping>" +
                              "<answers>" +
                                "<profile-answer>" +
                                  "<id>Storage Mode</id>" +
                                  "<response-id>2480</response-id>" +
                                  "<label>Storage of client Dunder Mifflin data or assets</label>" +
                                  "<answer-time>2011-01-31 14:16:20.000</answer-time>" +
                                  "<answered-by>GWT</answered-by>" +
                                  "<answer-value>false</answer-value>" +
                                  "<is-accepted>true</is-accepted>" +
                                  "<answer-source>Customer</answer-source>" +
                                "</profile-answer>" +
                                "<profile-answer>" +
                                  "<id>Processing Mode</id>" +
                                  "<response-id>2465</response-id>" +
                                  "<label>Processing of Dunder Mifflin data or assets</label>" +
                                  "<answer-time>2011-01-31 14:16:19.000</answer-time>" +
                                  "<answered-by>GWT</answered-by>" +
                                  "<answer-value>false</answer-value>" +
                                  "<is-accepted>true</is-accepted>" +
                                  "<answer-source>Customer</answer-source>" +
                                "</profile-answer>" +
                                "<profile-answer>" +
                                  "<id>Transmission Mode</id>" +
                                  "<response-id>2488</response-id>" +
                                  "<label>Transmission of Dunder Mifflin data or assets</label>" +
                                  "<answer-time>2011-01-31 14:16:20.000</answer-time>" +
                                  "<answered-by>GWT</answered-by>" +
                                  "<answer-value>false</answer-value>" +
                                  "<is-accepted>true</is-accepted>" +
                                  "<answer-source>Customer</answer-source>" +
                                "</profile-answer>" +
                                "<profile-answer>" +
                                  "<id>Destruction Mode</id>" +
                                  "<response-id>2426</response-id>" +
                                  "<label>Destruction of Dunder Mifflin data or assets</label>" +
                                  "<answer-time>2011-01-31 14:16:18.000</answer-time>" +
                                  "<answered-by>GWT</answered-by>" +
                                  "<answer-value>false</answer-value>" +
                                  "<is-accepted>true</is-accepted>" +
                                  "<answer-source>Customer</answer-source>" +
                                "</profile-answer>" +
                                "<profile-answer>" +
                                  "<id>Transportation Mode</id>" +
                                  "<response-id>5212</response-id>" +
                                  "<label>Transportation of Dunder Mifflin data or assets</label>" +
                                  "<answer-time>2012-11-19 11:09:32.000</answer-time>" +
                                  "<answered-by>customer1</answered-by>" +
                                  "<answer-value>true</answer-value>" +
                                  "<is-accepted>true</is-accepted>" +
                                  "<answer-source>Vendor</answer-source>" +
                                "</profile-answer>" +
                              "</answers>" +
                            "</answer-grouping>" +
                          "</answer-groupings>" +
                        "</choose-many-question>" +
                        "<choose-many-question>" +
                          "<id>Data Format</id>" +
                          "<label>Dell handles the following forms of Dunder Mifflin data:</label>" +
                          "<answer-groupings>" +
                            "<answer-grouping>" +
                              "<answers>" +
                                "<profile-answer>" +
                                  "<id>Hardcopy Format</id>" +
                                  "<response-id>2438</response-id>" +
                                  "<label>In hardcopy (i.e., printed or written) form</label>" +
                                  "<answer-time>2011-01-31 14:16:19.000</answer-time>" +
                                  "<answered-by>GWT</answered-by>" +
                                  "<answer-value>false</answer-value>" +
                                  "<is-accepted>true</is-accepted>" +
                                  "<answer-source>Customer</answer-source>" +
                                "</profile-answer>" +
                                "<profile-answer>" +
                                  "<id>Electronic Format</id>" +
                                  "<response-id>2430</response-id>" +
                                  "<label>In electronic form</label>" +
                                  "<answer-time>2011-01-31 14:16:18.000</answer-time>" +
                                  "<answered-by>GWT</answered-by>" +
                                  "<answer-value>false</answer-value>" +
                                  "<is-accepted>true</is-accepted>" +
                                  "<answer-source>Customer</answer-source>" +
                                "</profile-answer>" +
                                "<profile-answer>" +
                                  "<id>Media Format</id>" +
                                  "<response-id>2448</response-id>" +
                                  "<label>Stored on electronic/magnetic media</label>" +
                                  "<answer-time>2011-01-31 14:16:19.000</answer-time>" +
                                  "<answered-by>GWT</answered-by>" +
                                  "<answer-value>false</answer-value>" +
                                  "<is-accepted>true</is-accepted>" +
                                  "<answer-source>Customer</answer-source>" +
                                "</profile-answer>" +
                                "<profile-answer>" +
                                  "<id>Knowledge Format</id>" +
                                  "<response-id>2444</response-id>" +
                                  "<label>Known by individuals</label>" +
                                  "<answer-time>2011-01-31 14:16:19.000</answer-time>" +
                                  "<answered-by>GWT</answered-by>" +
                                  "<answer-value>false</answer-value>" +
                                  "<is-accepted>true</is-accepted>" +
                                  "<answer-source>Customer</answer-source>" +
                                "</profile-answer>" +
                              "</answers>" +
                            "</answer-grouping>" +
                          "</answer-groupings>" +
                        "</choose-many-question>" +
                        "<choose-many-question>" +
                          "<id>Dedicated Services</id>" +
                          "<label>Dell provides the following services that are dedicated to Dunder Mifflin:</label>" +
                          "<answer-groupings>" +
                            "<answer-grouping>" +
                              "<answers>" +
                                "<profile-answer>" +
                                  "<id>Dedicated Network Hardware</id>" +
                                  "<response-id>2414</response-id>" +
                                  "<label>Network(s)</label>" +
                                  "<answer-time>2011-01-31 14:16:18.000</answer-time>" +
                                  "<answered-by>GWT</answered-by>" +
                                  "<answer-value>false</answer-value>" +
                                  "<is-accepted>true</is-accepted>" +
                                  "<answer-source>Customer</answer-source>" +
                                "</profile-answer>" +
                                "<profile-answer>" +
                                  "<id>Dedicated Server Hardware</id>" +
                                  "<response-id>2416</response-id>" +
                                  "<label>Server(s)</label>" +
                                  "<answer-time>2011-01-31 14:16:18.000</answer-time>" +
                                  "<answered-by>GWT</answered-by>" +
                                  "<answer-value>false</answer-value>" +
                                  "<is-accepted>true</is-accepted>" +
                                  "<answer-source>Customer</answer-source>" +
                                "</profile-answer>" +
                                "<profile-answer>" +
                                  "<id>Dedicated Saas Software</id>" +
                                  "<response-id>2415</response-id>" +
                                  "<label>Software</label>" +
                                  "<answer-time>2011-01-31 14:16:18.000</answer-time>" +
                                  "<answered-by>GWT</answered-by>" +
                                  "<answer-value>false</answer-value>" +
                                  "<is-accepted>true</is-accepted>" +
                                  "<answer-source>Customer</answer-source>" +
                                "</profile-answer>" +
                                "<profile-answer>" +
                                  "<id>Dedicated Database Software</id>" +
                                  "<response-id>2413</response-id>" +
                                  "<label>Database(s)</label>" +
                                  "<answer-time>2011-01-31 14:16:18.000</answer-time>" +
                                  "<answered-by>GWT</answered-by>" +
                                  "<answer-value>false</answer-value>" +
                                  "<is-accepted>true</is-accepted>" +
                                  "<answer-source>Customer</answer-source>" +
                                "</profile-answer>" +
                              "</answers>" +
                            "</answer-grouping>" +
                          "</answer-groupings>" +
                        "</choose-many-question>" +
                        "<choose-many-question>" +
                          "<id>Shared Services</id>" +
                          "<label>Dell provides the following services that are shared with multiple customers:</label>" +
                          "<answer-groupings>" +
                            "<answer-grouping>" +
                              "<answers>" +
                                "<profile-answer>" +
                                  "<id>Shared Network Hardware</id>" +
                                  "<response-id>2477</response-id>" +
                                  "<label>Network(s)</label>" +
                                  "<answer-time>2011-01-31 14:16:20.000</answer-time>" +
                                  "<answered-by>GWT</answered-by>" +
                                  "<answer-value>false</answer-value>" +
                                  "<is-accepted>true</is-accepted>" +
                                  "<answer-source>Customer</answer-source>" +
                                "</profile-answer>" +
                                "<profile-answer>" +
                                  "<id>Shared Server Hardware</id>" +
                                  "<response-id>2479</response-id>" +
                                  "<label>Server(s)</label>" +
                                  "<answer-time>2011-01-31 14:16:20.000</answer-time>" +
                                  "<answered-by>GWT</answered-by>" +
                                  "<answer-value>false</answer-value>" +
                                  "<is-accepted>true</is-accepted>" +
                                  "<answer-source>Customer</answer-source>" +
                                "</profile-answer>" +
                                "<profile-answer>" +
                                  "<id>Shared Saas Software</id>" +
                                  "<response-id>2478</response-id>" +
                                  "<label>Software</label>" +
                                  "<answer-time>2011-01-31 14:16:20.000</answer-time>" +
                                  "<answered-by>GWT</answered-by>" +
                                  "<answer-value>false</answer-value>" +
                                  "<is-accepted>true</is-accepted>" +
                                  "<answer-source>Customer</answer-source>" +
                                "</profile-answer>" +
                                "<profile-answer>" +
                                  "<id>Shared Database Software</id>" +
                                  "<response-id>2475</response-id>" +
                                  "<label>Database(s)</label>" +
                                  "<answer-time>2011-01-31 14:16:20.000</answer-time>" +
                                  "<answered-by>GWT</answered-by>" +
                                  "<answer-value>false</answer-value>" +
                                  "<is-accepted>true</is-accepted>" +
                                  "<answer-source>Customer</answer-source>" +
                                "</profile-answer>" +
                              "</answers>" +
                            "</answer-grouping>" +
                          "</answer-groupings>" +
                        "</choose-many-question>" +
                        "<choose-many-question>" +
                          "<id>Data Center Services</id>" +
                          "<label>Dell provides data center services by means of:</label>" +
                          "<answer-groupings>" +
                            "<answer-grouping>" +
                              "<answers>" +
                                "<profile-answer>" +
                                  "<id>Data Center Cage</id>" +
                                  "<response-id>2409</response-id>" +
                                  "<label>A dedicated room or cage</label>" +
                                  "<answer-time>2011-01-31 14:16:18.000</answer-time>" +
                                  "<answered-by>GWT</answered-by>" +
                                  "<answer-value>false</answer-value>" +
                                  "<is-accepted>true</is-accepted>" +
                                  "<answer-source>Customer</answer-source>" +
                                "</profile-answer>" +
                                "<profile-answer>" +
                                  "<id>Data Center Rack</id>" +
                                  "<response-id>2410</response-id>" +
                                  "<label>A dedicated rack</label>" +
                                  "<answer-time>2011-01-31 14:16:18.000</answer-time>" +
                                  "<answered-by>GWT</answered-by>" +
                                  "<answer-value>false</answer-value>" +
                                  "<is-accepted>true</is-accepted>" +
                                  "<answer-source>Customer</answer-source>" +
                                "</profile-answer>" +
                                "<profile-answer>" +
                                  "<id>Data Center Shared</id>" +
                                  "<response-id>2411</response-id>" +
                                  "<label>A rack that is shared with other customers</label>" +
                                  "<answer-time>2011-01-31 14:16:18.000</answer-time>" +
                                  "<answered-by>GWT</answered-by>" +
                                  "<answer-value>false</answer-value>" +
                                  "<is-accepted>true</is-accepted>" +
                                  "<answer-source>Customer</answer-source>" +
                                "</profile-answer>" +
                              "</answers>" +
                            "</answer-grouping>" +
                          "</answer-groupings>" +
                        "</choose-many-question>" +
                        "<choose-many-question>" +
                          "<id>Managed Services</id>" +
                          "<label>Dell provides services for which it is responsible for managing:</label>" +
                          "<answer-groupings>" +
                            "<answer-grouping>" +
                              "<answers>" +
                                "<profile-answer>" +
                                  "<id>Managing OS Software</id>" +
                                  "<response-id>2447</response-id>" +
                                  "<label>Underlying operating systems</label>" +
                                  "<answer-time>2011-01-31 14:16:19.000</answer-time>" +
                                  "<answered-by>GWT</answered-by>" +
                                  "<answer-value>false</answer-value>" +
                                  "<is-accepted>true</is-accepted>" +
                                  "<answer-source>Customer</answer-source>" +
                                "</profile-answer>" +
                                "<profile-answer>" +
                                  "<id>Subsystems Managing Storage</id>" +
                                  "<response-id>2486</response-id>" +
                                  "<label>Storage</label>" +
                                  "<answer-time>2011-01-31 14:16:20.000</answer-time>" +
                                  "<answered-by>GWT</answered-by>" +
                                  "<answer-value>false</answer-value>" +
                                  "<is-accepted>true</is-accepted>" +
                                  "<answer-source>Customer</answer-source>" +
                                "</profile-answer>" +
                                "<profile-answer>" +
                                  "<id>Subsystems Managing Backups</id>" +
                                  "<response-id>2482</response-id>" +
                                  "<label>Backups</label>" +
                                  "<answer-time>2011-01-31 14:16:20.000</answer-time>" +
                                  "<answered-by>GWT</answered-by>" +
                                  "<answer-value>false</answer-value>" +
                                  "<is-accepted>true</is-accepted>" +
                                  "<answer-source>Customer</answer-source>" +
                                "</profile-answer>" +
                                "<profile-answer>" +
                                  "<id>Subsystems Managing Network Access</id>" +
                                  "<response-id>2483</response-id>" +
                                  "<label>Network access</label>" +
                                  "<answer-time>2011-01-31 14:16:20.000</answer-time>" +
                                  "<answered-by>GWT</answered-by>" +
                                  "<answer-value>false</answer-value>" +
                                  "<is-accepted>true</is-accepted>" +
                                  "<answer-source>Customer</answer-source>" +
                                "</profile-answer>" +
                                "<profile-answer>" +
                                  "<id>Subsystems Managing Network Monitoring</id>" +
                                  "<response-id>2484</response-id>" +
                                  "<label>Network monitoring</label>" +
                                  "<answer-time>2011-01-31 14:16:20.000</answer-time>" +
                                  "<answered-by>GWT</answered-by>" +
                                  "<answer-value>false</answer-value>" +
                                  "<is-accepted>true</is-accepted>" +
                                  "<answer-source>Customer</answer-source>" +
                                "</profile-answer>" +
                                "<profile-answer>" +
                                  "<id>Subsystems Managing Security Monitoring</id>" +
                                  "<response-id>2485</response-id>" +
                                  "<label>Security monitoring</label>" +
                                  "<answer-time>2011-01-31 14:16:20.000</answer-time>" +
                                  "<answered-by>GWT</answered-by>" +
                                  "<answer-value>false</answer-value>" +
                                  "<is-accepted>true</is-accepted>" +
                                  "<answer-source>Customer</answer-source>" +
                                "</profile-answer>" +
                              "</answers>" +
                            "</answer-grouping>" +
                          "</answer-groupings>" +
                        "</choose-many-question>" +
                      "</questions>" +
                    "</question-category>" +
                    "<question-category>" +
                      "<label>Customer Information Access</label>" +
                      "<questions>" +
                        "<choose-many-question>" +
                          "<id>Assets</id>" +
                          "<label>Which of the following data and information does Dell directly deal with or potentially have access to within Dunder Mifflin?</label>" +
                          "<answer-groupings>" +
                            "<answer-grouping>" +
                              "<label>Corporate Assets</label>" +
                              "<answers>" +
                                "<profile-answer>" +
                                  "<id>Corp Financial</id>" +
                                  "<response-id>2406</response-id>" +
                                  "<label>Financial data or records</label>" +
                                  "<answer-time>2011-01-31 14:16:17.000</answer-time>" +
                                  "<answered-by>GWT</answered-by>" +
                                  "<answer-value>true</answer-value>" +
                                  "<is-accepted>true</is-accepted>" +
                                  "<answer-source>Customer</answer-source>" +
                                "</profile-answer>" +
                                "<profile-answer>" +
                                  "<id>Legal</id>" +
                                  "<response-id>2446</response-id>" +
                                  "<label>Legal data or records</label>" +
                                  "<answer-time>2011-01-31 14:16:19.000</answer-time>" +
                                  "<answered-by>GWT</answered-by>" +
                                  "<answer-value>true</answer-value>" +
                                  "<is-accepted>true</is-accepted>" +
                                  "<answer-source>Customer</answer-source>" +
                                "</profile-answer>" +
                                "<profile-answer>" +
                                  "<id>Monitoring</id>" +
                                  "<response-id>2451</response-id>" +
                                  "<label>Monitoring and audit data (logs/records/reports)</label>" +
                                  "<answer-time>2011-01-31 14:16:19.000</answer-time>" +
                                  "<answered-by>GWT</answered-by>" +
                                  "<answer-value>true</answer-value>" +
                                  "<is-accepted>true</is-accepted>" +
                                  "<answer-source>Customer</answer-source>" +
                                "</profile-answer>" +
                                "<profile-answer>" +
                                  "<id>Customer</id>" +
                                  "<response-id>2408</response-id>" +
                                  "<label>Customer data</label>" +
                                  "<answer-time>2011-01-31 14:16:17.000</answer-time>" +
                                  "<answered-by>GWT</answered-by>" +
                                  "<answer-value>true</answer-value>" +
                                  "<is-accepted>true</is-accepted>" +
                                  "<answer-source>Customer</answer-source>" +
                                "</profile-answer>" +
                                "<profile-answer>" +
                                  "<id>Employee</id>" +
                                  "<response-id>2431</response-id>" +
                                  "<label>Employee data</label>" +
                                  "<answer-time>2011-01-31 14:16:18.000</answer-time>" +
                                  "<answered-by>GWT</answered-by>" +
                                  "<answer-value>true</answer-value>" +
                                  "<is-accepted>true</is-accepted>" +
                                  "<answer-source>Customer</answer-source>" +
                                "</profile-answer>" +
                                "<profile-answer>" +
                                  "<id>Sensitive</id>" +
                                  "<response-id>2471</response-id>" +
                                  "<label>Sensitive corporate data/Intellectual property</label>" +
                                  "<answer-time>2011-01-31 14:16:20.000</answer-time>" +
                                  "<answered-by>GWT</answered-by>" +
                                  "<answer-value>true</answer-value>" +
                                  "<is-accepted>true</is-accepted>" +
                                  "<answer-source>Customer</answer-source>" +
                                "</profile-answer>" +
                              "</answers>" +
                            "</answer-grouping>" +
                            "<answer-grouping>" +
                              "<label>Individual Assets</label>" +
                              "<answers>" +
                                "<profile-answer>" +
                                  "<id>Education</id>" +
                                  "<response-id>2429</response-id>" +
                                  "<label>Educational records</label>" +
                                  "<answer-time>2011-01-31 14:16:18.000</answer-time>" +
                                  "<answered-by>GWT</answered-by>" +
                                  "<answer-value>true</answer-value>" +
                                  "<is-accepted>true</is-accepted>" +
                                  "<answer-source>Customer</answer-source>" +
                                "</profile-answer>" +
                                "<profile-answer>" +
                                  "<id>Financial</id>" +
                                  "<response-id>2433</response-id>" +
                                  "<label>Financial data</label>" +
                                  "<answer-time>2011-01-31 14:16:18.000</answer-time>" +
                                  "<answered-by>GWT</answered-by>" +
                                  "<answer-value>true</answer-value>" +
                                  "<is-accepted>true</is-accepted>" +
                                  "<answer-source>Customer</answer-source>" +
                                "</profile-answer>" +
                                "<profile-answer>" +
                                  "<id>PHI</id>" +
                                  "<response-id>2463</response-id>" +
                                  "<label>Protected health information (PHI)</label>" +
                                  "<answer-time>2011-01-31 14:16:19.000</answer-time>" +
                                  "<answered-by>GWT</answered-by>" +
                                  "<answer-value>true</answer-value>" +
                                  "<is-accepted>true</is-accepted>" +
                                  "<answer-source>Customer</answer-source>" +
                                "</profile-answer>" +
                                "<profile-answer>" +
                                  "<id>Cardholder</id>" +
                                  "<response-id>2403</response-id>" +
                                  "<label>Cardholder data (credit card data)</label>" +
                                  "<answer-time>2011-01-31 14:16:17.000</answer-time>" +
                                  "<answered-by>GWT</answered-by>" +
                                  "<answer-value>true</answer-value>" +
                                  "<is-accepted>true</is-accepted>" +
                                  "<answer-source>Customer</answer-source>" +
                                "</profile-answer>" +
                                "<profile-answer>" +
                                  "<id>Authentication</id>" +
                                  "<response-id>2398</response-id>" +
                                  "<label>Authentication data</label>" +
                                  "<answer-time>2011-01-31 14:16:17.000</answer-time>" +
                                  "<answered-by>GWT</answered-by>" +
                                  "<answer-value>true</answer-value>" +
                                  "<is-accepted>true</is-accepted>" +
                                  "<answer-source>Customer</answer-source>" +
                                "</profile-answer>" +
                                "<profile-answer>" +
                                  "<id>Identifier</id>" +
                                  "<response-id>2441</response-id>" +
                                  "<label>Personally identifiable information (PII)</label>" +
                                  "<answer-time>2011-01-31 14:16:19.000</answer-time>" +
                                  "<answered-by>GWT</answered-by>" +
                                  "<answer-value>true</answer-value>" +
                                  "<is-accepted>true</is-accepted>" +
                                  "<answer-source>Customer</answer-source>" +
                                "</profile-answer>" +
                                "<profile-answer>" +
                                  "<id>Personal</id>" +
                                  "<response-id>2462</response-id>" +
                                  "<label>Nonpublic personal information (NPPI)</label>" +
                                  "<answer-time>2011-01-31 14:16:19.000</answer-time>" +
                                  "<answered-by>GWT</answered-by>" +
                                  "<answer-value>true</answer-value>" +
                                  "<is-accepted>true</is-accepted>" +
                                  "<answer-source>Customer</answer-source>" +
                                "</profile-answer>" +
                              "</answers>" +
                            "</answer-grouping>" +
                          "</answer-groupings>" +
                        "</choose-many-question>" +
                      "</questions>" +
                    "</question-category>" +
                    "<question-category>" +
                      "<label>Validations and 3rd Party Certifications</label>" +
                      "<questions>" +
                        "<choose-many-question>" +
                          "<id>Cross Industry Audits and Assessments</id>" +
                          "<label>Of the following formal certifications or third-party assessments, which have been performed in the last 12 months?</label>" +
                          "<answer-groupings>" +
                            "<answer-grouping>" +
                              "<answers>" +
                                "<profile-answer>" +
                                  "<id>25999</id>" +
                                  "<response-id>5211</response-id>" +
                                  "<label>BS 25999 certification</label>" +
                                  "<answer-time>2012-11-19 11:04:17.000</answer-time>" +
                                  "<answered-by>customer1</answered-by>" +
                                  "<answer-value>false</answer-value>" +
                                  "<is-accepted>true</is-accepted>" +
                                  "<answer-source>Vendor</answer-source>" +
                                "</profile-answer>" +
                                "<profile-answer>" +
                                  "<id>COBIT Audit</id>" +
                                  "<response-id>5161</response-id>" +
                                  "<label>COBIT-based assessment</label>" +
                                  "<answer-time>2012-11-15 11:45:27.000</answer-time>" +
                                  "<answered-by>customer1</answered-by>" +
                                  "<answer-value>true</answer-value>" +
                                  "<is-accepted>true</is-accepted>" +
                                  "<answer-source>Vendor</answer-source>" +
                                "</profile-answer>" +
                                "<profile-answer>" +
                                  "<id>HITRUSTCSF</id>" +
                                  "<response-id>5210</response-id>" +
                                  "<label>HITRUST CSF</label>" +
                                  "<answer-time>2012-11-19 11:03:56.000</answer-time>" +
                                  "<answered-by>customer1</answered-by>" +
                                  "<answer-value>true</answer-value>" +
                                  "<is-accepted>true</is-accepted>" +
                                  "<answer-source>Vendor</answer-source>" +
                                "</profile-answer>" +
                                "<profile-answer>" +
                                  "<id>Internal Audit Financial</id>" +
                                  "<response-id>5209</response-id>" +
                                  "<label>Internal audit of financial processes</label>" +
                                  "<answer-time>2012-11-19 11:03:34.000</answer-time>" +
                                  "<answered-by>customer1</answered-by>" +
                                  "<answer-value>true</answer-value>" +
                                  "<is-accepted>true</is-accepted>" +
                                  "<answer-source>Vendor</answer-source>" +
                                "</profile-answer>" +
                                "<profile-answer>" +
                                  "<id>Internal Audit Technology</id>" +
                                  "<label>Internal audit of information technology controls (General controls review)</label>" +
                                  "<is-accepted>false</is-accepted>" +
                                "</profile-answer>" +
                                "<profile-answer>" +
                                  "<id>ISO27001 Certification</id>" +
                                  "<label>ISO27001 certification</label>" +
                                  "<is-accepted>false</is-accepted>" +
                                "</profile-answer>" +
                                "<profile-answer>" +
                                  "<id>ISO9001 Certification</id>" +
                                  "<label>ISO9001 certification</label>" +
                                  "<is-accepted>false</is-accepted>" +
                                "</profile-answer>" +
                                "<profile-answer>" +
                                  "<id>ROC Assessment</id>" +
                                  "<response-id>2470</response-id>" +
                                  "<label>PCI DSS Report on Compliance (ROC) assessment</label>" +
                                  "<answer-time>2011-01-31 14:16:20.000</answer-time>" +
                                  "<answered-by>GWT</answered-by>" +
                                  "<answer-value>true</answer-value>" +
                                  "<is-accepted>true</is-accepted>" +
                                  "<answer-source>Customer</answer-source>" +
                                "</profile-answer>" +
                                "<profile-answer>" +
                                  "<id>SAQ Assessment</id>" +
                                  "<label>PCI Self Assessment Questionnaire (SAQ)</label>" +
                                  "<is-accepted>false</is-accepted>" +
                                "</profile-answer>" +
                                "<profile-answer>" +
                                  "<id>SAS70</id>" +
                                  "<label>SAS 70 Type I</label>" +
                                  "<is-accepted>false</is-accepted>" +
                                "</profile-answer>" +
                                "<profile-answer>" +
                                  "<id>SAS70 II</id>" +
                                  "<label>SAS 70 Type II</label>" +
                                  "<is-accepted>false</is-accepted>" +
                                "</profile-answer>" +
                                "<profile-answer>" +
                                  "<id>BITS SIG Full</id>" +
                                  "<label>SIG Full questionnaire</label>" +
                                  "<is-accepted>false</is-accepted>" +
                                "</profile-answer>" +
                                "<profile-answer>" +
                                  "<id>BITS</id>" +
                                  "<label>SIG Lite questionnaire</label>" +
                                  "<is-accepted>false</is-accepted>" +
                                "</profile-answer>" +
                                "<profile-answer>" +
                                  "<id>SSAE16</id>" +
                                  "<label>SSAE 16</label>" +
                                  "<is-accepted>false</is-accepted>" +
                                "</profile-answer>" +
                              "</answers>" +
                            "</answer-grouping>" +
                          "</answer-groupings>" +
                        "</choose-many-question>" +
                        "<choose-many-question>" +
                          "<id>Security Testing</id>" +
                          "<label>Of the following types of security testing, which have been performed in the last 12 months and produced as evidence?</label>" +
                          "<answer-groupings>" +
                            "<answer-grouping>" +
                              "<answers>" +
                                "<profile-answer>" +
                                  "<id>Application Security Test</id>" +
                                  "<response-id>5206</response-id>" +
                                  "<label>Application security testing</label>" +
                                  "<answer-time>2012-11-19 09:33:56.000</answer-time>" +
                                  "<answered-by>customer1</answered-by>" +
                                  "<answer-value>true</answer-value>" +
                                  "<is-accepted>true</is-accepted>" +
                                  "<answer-source>Vendor</answer-source>" +
                                "</profile-answer>" +
                                "<profile-answer>" +
                                  "<id>PCI Quarterly Scanning</id>" +
                                  "<response-id>5207</response-id>" +
                                  "<label>PCI quarterly scanning</label>" +
                                  "<answer-time>2012-11-19 09:34:31.000</answer-time>" +
                                  "<answered-by>customer1</answered-by>" +
                                  "<answer-value>true</answer-value>" +
                                  "<is-accepted>true</is-accepted>" +
                                  "<answer-source>Vendor</answer-source>" +
                                "</profile-answer>" +
                                "<profile-answer>" +
                                  "<id>Penetration Test</id>" +
                                  "<response-id>5208</response-id>" +
                                  "<label>Penetration testing</label>" +
                                  "<answer-time>2012-11-19 09:35:24.000</answer-time>" +
                                  "<answered-by>customer1</answered-by>" +
                                  "<answer-value>true</answer-value>" +
                                  "<is-accepted>true</is-accepted>" +
                                  "<answer-source>Vendor</answer-source>" +
                                "</profile-answer>" +
                                "<profile-answer>" +
                                  "<id>Vulnerability Scan</id>" +
                                  "<response-id>5158</response-id>" +
                                  "<label>Vulnerability scanning</label>" +
                                  "<answer-time>2012-11-15 10:54:50.000</answer-time>" +
                                  "<answered-by>customer1</answered-by>" +
                                  "<answer-value>true</answer-value>" +
                                  "<is-accepted>true</is-accepted>" +
                                  "<answer-source>Vendor</answer-source>" +
                                "</profile-answer>" +
                                "<profile-answer>" +
                                  "<id>Wireless security testing</id>" +
                                  "<label>Wireless security testing</label>" +
                                  "<is-accepted>false</is-accepted>" +
                                "</profile-answer>" +
                              "</answers>" +
                            "</answer-grouping>" +
                          "</answer-groupings>" +
                        "</choose-many-question>" +
                        "<choose-many-question>" +
                          "<id>US Federal Government Assessments and Certifications</id>" +
                          "<label>Of the following US government assessment criteria, which have been performed in the last 12 months?</label>" +
                          "<answer-groupings>" +
                            "<answer-grouping>" +
                              "<answers>" +
                                "<profile-answer>" +
                                  "<id>DIACAP</id>" +
                                  "<label>DIACAP-based assessment</label>" +
                                  "<is-accepted>false</is-accepted>" +
                                "</profile-answer>" +
                                "<profile-answer>" +
                                  "<id>FISMA</id>" +
                                  "<label>FISMA-based assessment</label>" +
                                  "<is-accepted>false</is-accepted>" +
                                "</profile-answer>" +
                                "<profile-answer>" +
                                  "<id>NISP Facility Clearance</id>" +
                                  "<label>National Industrial Security Program (NISP) facility clearance</label>" +
                                  "<is-accepted>false</is-accepted>" +
                                "</profile-answer>" +
                              "</answers>" +
                            "</answer-grouping>" +
                          "</answer-groupings>" +
                        "</choose-many-question>" +
                        "<choose-one-question>" +
                          "<id>PCI Certifications</id>" +
                          "<label>If applicable, of the following Payment Card Industry categories, which best describes Dell?</label>" +
                          "<answer-groupings>" +
                            "<answer-grouping>" +
                              "<answers>" +
                                "<profile-answer>" +
                                  "<id>Type Merchant</id>" +
                                  "<label>Credit card merchant</label>" +
                                  "<answer-value>false</answer-value>" +
                                  "<is-accepted>false</is-accepted>" +
                                "</profile-answer>" +
                                "<profile-answer>" +
                                  "<id>Type Service Provider</id>" +
                                  "<response-id>4274</response-id>" +
                                  "<label>Credit card service provider</label>" +
                                  "<answer-time>2011-01-31 14:16:20.000</answer-time>" +
                                  "<answered-by>GWT</answered-by>" +
                                  "<answer-value>true</answer-value>" +
                                  "<is-accepted>true</is-accepted>" +
                                  "<answer-source>Customer</answer-source>" +
                                "</profile-answer>" +
                                "<profile-answer>" +
                                  "<id>PCI Other</id>" +
                                  "<label>Other</label>" +
                                  "<answer-value>false</answer-value>" +
                                  "<is-accepted>false</is-accepted>" +
                                "</profile-answer>" +
                                "<profile-answer>" +
                                  "<id>PCI None</id>" +
                                  "<label>None</label>" +
                                  "<answer-value>false</answer-value>" +
                                  "<is-accepted>false</is-accepted>" +
                                "</profile-answer>" +
                              "</answers>" +
                            "</answer-grouping>" +
                          "</answer-groupings>" +
                        "</choose-one-question>" +
                      "</questions>" +
                    "</question-category>" +
                  "</question-categories>";
    
}
