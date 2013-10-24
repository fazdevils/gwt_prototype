package com.vincentfazio.ui.model.parser.xml;

import java.util.List;

import org.junit.Test;

import com.google.gwt.junit.client.GWTTestCase;
import com.vincentfazio.ui.bean.QuestionBean;
import com.vincentfazio.ui.bean.QuestionBean.QuestionStatus;
import com.vincentfazio.ui.model.parser.xml.SecuritySurveyXmlParser;

public class SecuritySurveyXmlParserTest extends GWTTestCase {

    @Test
    public void testParseSurvey() {
        SecuritySurveyXmlParser xmlParser = new SecuritySurveyXmlParser();
        List<QuestionBean> questions = xmlParser.parse(securitySurveyXml);
        assertEquals(37, questions.size());
        
        QuestionStatus surveyStatus = new QuestionStatus();
        for (QuestionBean question: questions) {
            QuestionStatus questionStatus = question.getQuestionStatus();
            surveyStatus.numberOfSubquestions += questionStatus.numberOfSubquestions;
            surveyStatus.numberOfAnsweredSubquestions += questionStatus.numberOfAnsweredSubquestions;
        }

        assertEquals(6, surveyStatus.numberOfAnsweredSubquestions);
        assertEquals(37, surveyStatus.numberOfSubquestions);
        
        QuestionBean question1 = questions.get(0);
        QuestionStatus question1Status = question1.getSubquestionStatus();
        assertEquals(0, question1Status.numberOfAnsweredSubquestions);
        assertEquals(0, question1Status.numberOfSubquestions);
        assertEquals("In Place", question1.getAnswerValue());
        assertEquals("vfazio", question1.getAnsweredBy());
        assertEquals(xmlParser.parseDate("2012-11-19 15:16:37.000"), question1.getAnsweredTime());
        
        QuestionBean selectedChoice = question1.getSelectedChoice();
        assertEquals(question1.getAnswerValue(), selectedChoice.getQuestionId());
        assertEquals("true", selectedChoice.getAnswerValue());
        for (QuestionBean subquestion: selectedChoice.getSubquestions()) {
        	if (subquestion.getQuestionId().equals(SecuritySurveyXmlParser.SUPPORTING_DOCUMENTATION_QUESTION_ID)) {
        		List<QuestionBean> documentChoices = subquestion.getChoices();
				assertEquals(7, documentChoices.size());
        	    int selectedCount = 0;
        		for (QuestionBean document: documentChoices) {
        			if ("true".equals(document.getAnswerValue())) {
        				++selectedCount;
        			}
        		}
        		assertEquals(1, selectedCount);
        	} else if (subquestion.getQuestionId().equals(SecuritySurveyXmlParser.COMMENTS_QUESTION_ID)) {
        		assertEquals("test comment", subquestion.getAnswerValue());
        	} else if (subquestion.getQuestionId().equals(SecuritySurveyXmlParser.DATE_QUESTION_ID)) {
        		assertEquals("01/29/2013", subquestion.getAnswerValue());
        	}
        }
        
    }
 
    
    @Test
    public void testParseSurvey2() {
        SecuritySurveyXmlParser xmlParser = new SecuritySurveyXmlParser();
        List<QuestionBean> questions = xmlParser.parse(securitySurveyXml2);
        assertEquals(37, questions.size());
        
        QuestionStatus surveyStatus = new QuestionStatus();
        for (QuestionBean question: questions) {
            QuestionStatus questionStatus = question.getQuestionStatus();
            surveyStatus.numberOfSubquestions += questionStatus.numberOfSubquestions;
            surveyStatus.numberOfAnsweredSubquestions += questionStatus.numberOfAnsweredSubquestions;
        }

        assertEquals(3, surveyStatus.numberOfAnsweredSubquestions);
        assertEquals(37, surveyStatus.numberOfSubquestions);
        
        QuestionBean question1 = questions.get(0);
        QuestionStatus question1Status = question1.getSubquestionStatus();
        assertEquals(0, question1Status.numberOfAnsweredSubquestions);
        assertEquals(0, question1Status.numberOfSubquestions);
        
    }
 
    
    @Override
    public String getModuleName() {
        return "com.vincentfazio.ui.gwtprototype";
    } 
    
    
    private String securitySurveyXml = 
            "<?xml version=\"1.0\" ?>" +
                    "<survey>" +
                      "<questions class=\"tree-set\">" +
                        "<survey-question>" +
                          "<abbreviation>5.1.1</abbreviation>" +
                          "<question-text>Does your organization have an information security policy / program, which has been approved by senior management and communicated to all employees and relevant external parties?</question-text>" +
                          "<question-set>" +
                            "<name>Question Set</name>" +
                          "</question-set>" +
                          "<documents class=\"set\">" +
                            "<document>" +
                              "<name>Information security policy (one or more documents)</name>" +
                            "</document>" +
                            "<document>" +
                              "<name>Evidence of management approval for current policies</name>" +
                            "</document>" +
                            "<document>" +
                              "<name>Evidence of communication to staff or training records</name>" +
                            "</document>" +
                            "<document>" +
                              "<name>Security program charter</name>" +
                            "</document>" +
                            "<document>" +
                              "<name>Information security roles and responsibilities document(s)</name>" +
                            "</document>" +
                            "<document>" +
                              "<name>Statement of applicability/security control inventory</name>" +
                            "</document>" +
                            "<document>" +
                              "<name>Organizational chart showing security roles</name>" +
                            "</document>" +
                          "</documents>" +
                          "<answer>" +
                            "<response-id>237</response-id>" +
                            "<selected-answer>" +
                              "<name>In Place</name>" +
                              "<description>This is currently in place</description>" +
                            "</selected-answer>" +
                            "<supporting-documentation>" +
                              "<document>" +
                                "<name>Information security policy (one or more documents)</name>" +
                              "</document>" +
                            "</supporting-documentation>" +
                            "<answer-time>2012-11-19 15:16:37.000</answer-time>" +
                            "<answered-by>vfazio</answered-by>" +
                            "<comment>test comment</comment>" +
                            "<target-date>01/16/2013</target-date>" +
                          "</answer>" +
                        "</survey-question>" +
                        "<survey-question>" +
                          "<abbreviation>6.1.3</abbreviation>" +
                          "<question-text>Are roles and responsibilities clearly defined for the protection of information assets and implementation / monitoring of security processes?</question-text>" +
                          "<question-set>" +
                            "<name>Question Set</name>" +
                          "</question-set>" +
                          "<documents class=\"set\">" +
                            "<document>" +
                              "<name>Code of ethics</name>" +
                            "</document>" +
                            "<document>" +
                              "<name>Roles and responsibilities (including job descriptions)</name>" +
                            "</document>" +
                            "<document>" +
                              "<name>Section of employee handbook on security responsibilities</name>" +
                            "</document>" +
                            "<document>" +
                              "<name>Employment agreement</name>" +
                            "</document>" +
                          "</documents>" +
                        "</survey-question>" +
                        "<survey-question>" +
                          "<abbreviation>6.1.5</abbreviation>" +
                          "<question-text>Does your organization have a process for managing / monitoring confidentiality or non-disclosure agreements?</question-text>" +
                          "<question-set>" +
                            "<name>Question Set</name>" +
                          "</question-set>" +
                          "<documents class=\"set\">" +
                            "<document>" +
                              "<name>Third party security policy</name>" +
                            "</document>" +
                            "<document>" +
                              "<name>Non-disclosure agreement</name>" +
                            "</document>" +
                            "<document>" +
                              "<name>Cardholder data protection policy</name>" +
                            "</document>" +
                            "<document>" +
                              "<name>Confidentiality agreements</name>" +
                            "</document>" +
                          "</documents>" +
                        "</survey-question>" +
                        "<survey-question>" +
                          "<abbreviation>6.1.8</abbreviation>" +
                          "<question-text>Does senior management ensure that independent information security audits are conducted and the findings reported on a regular schedule?</question-text>" +
                          "<question-set>" +
                            "<name>Question Set</name>" +
                          "</question-set>" +
                          "<documents class=\"set\">" +
                            "<document>" +
                              "<name>Internal audit reports</name>" +
                            "</document>" +
                            "<document>" +
                              "<name>Reports to management regarding independent reviews</name>" +
                            "</document>" +
                            "<document>" +
                              "<name>Third party assessment reports</name>" +
                            "</document>" +
                          "</documents>" +
                          "<answer>" +
                            "<response-id>218</response-id>" +
                            "<selected-answer>" +
                              "<name>In Place</name>" +
                              "<description>This is currently in place</description>" +
                            "</selected-answer>" +
                            "<supporting-documentation/>" +
                            "<comment></comment>" +
                            "<answer-time>2012-05-30 10:48:05.000</answer-time>" +
                            "<answered-by>vfazio</answered-by>" +
                          "</answer>" +
                        "</survey-question>" +
                        "<survey-question>" +
                          "<abbreviation>6.2.1</abbreviation>" +
                          "<question-text>Does your organization have a process for evaluating risks and implementing controls prior to granting external organizations access to information assets?</question-text>" +
                          "<question-set>" +
                            "<name>Question Set</name>" +
                          "</question-set>" +
                          "<documents class=\"set\">" +
                            "<document>" +
                              "<name>Third party security policy</name>" +
                            "</document>" +
                            "<document>" +
                              "<name>Third-party security agreements</name>" +
                            "</document>" +
                            "<document>" +
                              "<name>Security or risk assessment reports</name>" +
                            "</document>" +
                            "<document>" +
                              "<name>Third-party risk assessment</name>" +
                            "</document>" +
                            "<document>" +
                              "<name>Service provider list</name>" +
                            "</document>" +
                          "</documents>" +
                        "</survey-question>" +
                        "<survey-question>" +
                          "<abbreviation>7.1.1</abbreviation>" +
                          "<question-text>Does your organization maintain an information asset inventory and a process for managing risks associated with the classification of each asset?  </question-text>" +
                          "<question-set>" +
                            "<name>Question Set</name>" +
                          "</question-set>" +
                          "<documents class=\"set\">" +
                            "<document>" +
                              "<name>Asset inventory / classification log (systems and storage media)</name>" +
                            "</document>" +
                            "<document>" +
                              "<name>Media tracking and storage process</name>" +
                            "</document>" +
                            "<document>" +
                              "<name>Asset risk assessments</name>" +
                            "</document>" +
                            "<document>" +
                              "<name>Asset management policy and procedures</name>" +
                            "</document>" +
                          "</documents>" +
                        "</survey-question>" +
                        "<survey-question>" +
                          "<abbreviation>8.1.2</abbreviation>" +
                          "<question-text>Does your organization have a process for conducting background checks on employees, contractors and third party users? Are there different levels of clearance criteria based on the asset classification?</question-text>" +
                          "<question-set>" +
                            "<name>Question Set</name>" +
                          "</question-set>" +
                          "<documents class=\"set\">" +
                            "<document>" +
                              "<name>Verification / log of background checks</name>" +
                            "</document>" +
                            "<document>" +
                              "<name>Background / screening check procedure</name>" +
                            "</document>" +
                          "</documents>" +
                        "</survey-question>" +
                        "<survey-question>" +
                          "<abbreviation>8.1.3</abbreviation>" +
                          "<question-text>Does your organization require that employees, contractors and third party organizations sign information security agreements prior to granting access to information assets?</question-text>" +
                          "<question-set>" +
                            "<name>Question Set</name>" +
                          "</question-set>" +
                          "<documents class=\"set\">" +
                            "<document>" +
                              "<name>Employment agreements requiring return of assets</name>" +
                            "</document>" +
                            "<document>" +
                              "<name>Section of employee handbook on security responsibilities</name>" +
                            "</document>" +
                          "</documents>" +
                          "<answer>" +
                            "<response-id>227</response-id>" +
                            "<selected-answer>" +
                              "<name>Compensating Control</name>" +
                              "<description>Our organization currently adheres to a compensating control</description>" +
                            "</selected-answer>" +
                            "<supporting-documentation/>" +
                            "<comment></comment>" +
                            "<answer-time>2012-06-20 07:31:06.000</answer-time>" +
                            "<answered-by>vfazio</answered-by>" +
                          "</answer>" +
                        "</survey-question>" +
                        "<survey-question>" +
                          "<abbreviation>8.2.2</abbreviation>" +
                          "<question-text>Does your organization have a process for ensuring that employees, contractors and third parties receive the appropriate information security training for their role?  </question-text>" +
                          "<question-set>" +
                            "<name>Question Set</name>" +
                          "</question-set>" +
                          "<documents class=\"set\">" +
                            "<document>" +
                              "<name>Training records of acceptable use policy (AUP)</name>" +
                            "</document>" +
                            "<document>" +
                              "<name>Training plans</name>" +
                            "</document>" +
                            "<document>" +
                              "<name>Security awareness materials</name>" +
                            "</document>" +
                          "</documents>" +
                        "</survey-question>" +
                        "<survey-question>" +
                          "<abbreviation>8.3.3</abbreviation>" +
                          "<question-text>Does your organization have a process for reducing or removing security access to all systems and assets when an employee, contractor, or third party agreement is terminated?</question-text>" +
                          "<question-set>" +
                            "<name>Question Set</name>" +
                          "</question-set>" +
                          "<documents class=\"set\">" +
                            "<document>" +
                              "<name>Record/log of termination (ticket/work order)</name>" +
                            "</document>" +
                            "<document>" +
                              "<name>Log of periodic access review (audit to verify access rights were removed at termination)</name>" +
                            "</document>" +
                            "<document>" +
                              "<name>Records of access removal due to termination</name>" +
                            "</document>" +
                          "</documents>" +
                        "</survey-question>" +
                        "<survey-question>" +
                          "<abbreviation>9.1.4</abbreviation>" +
                          "<question-text>Does your organization have a process for protecting against external and environmental risks such as fire, flood, tornados, etc.?</question-text>" +
                          "<question-set>" +
                            "<name>Question Set</name>" +
                          "</question-set>" +
                          "<documents class=\"set\">" +
                            "<document>" +
                              "<name>Procedure for protection against environmental risks</name>" +
                            "</document>" +
                          "</documents>" +
                        "</survey-question>" +
                        "<survey-question>" +
                          "<abbreviation>10.1.1</abbreviation>" +
                          "<question-text>Does your organization have formal document management program which ensures that information security and related operating procedures are reviewed / approved by the appropriate authorities?</question-text>" +
                          "<question-set>" +
                            "<name>Question Set</name>" +
                          "</question-set>" +
                          "<documents class=\"set\">" +
                            "<document>" +
                              "<name>Document management procedure</name>" +
                            "</document>" +
                          "</documents>" +
                        "</survey-question>" +
                        "<survey-question>" +
                          "<abbreviation>10.5.1</abbreviation>" +
                          "<question-text>Does your organization have a back-up process which is regularly tested for accurate and complete record restoration and are backups stored in remote locations?</question-text>" +
                          "<question-set>" +
                            "<name>Question Set</name>" +
                          "</question-set>" +
                          "<documents class=\"set\">" +
                            "<document>" +
                              "<name>Backup media inventory</name>" +
                            "</document>" +
                            "<document>" +
                              "<name>Backup system logs</name>" +
                            "</document>" +
                            "<document>" +
                              "<name>System backup policy and procedures</name>" +
                            "</document>" +
                            "<document>" +
                              "<name>Restore testing logs</name>" +
                            "</document>" +
                            "<document>" +
                              "<name>Backup schedule</name>" +
                            "</document>" +
                          "</documents>" +
                        "</survey-question>" +
                        "<survey-question>" +
                          "<abbreviation>10.6.1</abbreviation>" +
                          "<question-text>Does your organization have a separate security group that is responsible for implementing network security controls and monitoring for security risks and performance.</question-text>" +
                          "<question-set>" +
                            "<name>Question Set</name>" +
                          "</question-set>" +
                          "<documents class=\"set\">" +
                            "<document>" +
                              "<name>Procedure, guideline, or standard for firewall configuration</name>" +
                            "</document>" +
                            "<document>" +
                              "<name>Network security policy</name>" +
                            "</document>" +
                            "<document>" +
                              "<name>Network configuration diagrams for internal and external networks in scope</name>" +
                            "</document>" +
                          "</documents>" +
                        "</survey-question>" +
                        "<survey-question>" +
                          "<abbreviation>10.7.3</abbreviation>" +
                          "<question-text>Does your organization have a process for handling information in accordance with its classification level to prevent unauthorized use?</question-text>" +
                          "<question-set>" +
                            "<name>Question Set</name>" +
                          "</question-set>" +
                          "<documents class=\"set\">" +
                            "<document>" +
                              "<name>Physical media policy</name>" +
                            "</document>" +
                          "</documents>" +
                          "<answer>" +
                            "<response-id>229</response-id>" +
                            "<selected-answer>" +
                              "<name>In Place</name>" +
                              "<description>This is currently in place</description>" +
                            "</selected-answer>" +
                            "<supporting-documentation/>" +
                            "<comment></comment>" +
                            "<answer-time>2012-06-20 07:32:30.000</answer-time>" +
                            "<answered-by>vfazio</answered-by>" +
                          "</answer>" +
                        "</survey-question>" +
                        "<survey-question>" +
                          "<abbreviation>10.8.4</abbreviation>" +
                          "<question-text>Does your organization have a process or guidance for the appropriate use of electronic messaging to share organizational information?</question-text>" +
                          "<question-set>" +
                            "<name>Question Set</name>" +
                          "</question-set>" +
                          "<documents class=\"set\">" +
                            "<document>" +
                              "<name>Application security architecture or model</name>" +
                            "</document>" +
                            "<document>" +
                              "<name>Application topology</name>" +
                            "</document>" +
                            "<document>" +
                              "<name>Appropriate electronic messaging awareness training materials / records</name>" +
                            "</document>" +
                            "<document>" +
                              "<name>Electronic messaging policy</name>" +
                            "</document>" +
                          "</documents>" +
                        "</survey-question>" +
                        "<survey-question>" +
                          "<abbreviation>12.2.1</abbreviation>" +
                          "<question-text>Does your organization require formal specifications and testing of data input when purchasing, developing, or upgrading information systems?</question-text>" +
                          "<question-set>" +
                            "<name>Question Set</name>" +
                          "</question-set>" +
                          "<documents class=\"set\">" +
                            "<document>" +
                              "<name>Security procedures or guidelines for developers</name>" +
                            "</document>" +
                            "<document>" +
                              "<name>Input data validation testing records</name>" +
                            "</document>" +
                          "</documents>" +
                        "</survey-question>" +
                        "<survey-question>" +
                          "<abbreviation>12.5.4</abbreviation>" +
                          "<question-text>Does your organization assess the potential risks for information leakage when implementing new systems or upgrading existing systems?</question-text>" +
                          "<question-set>" +
                            "<name>Question Set</name>" +
                          "</question-set>" +
                          "<documents class=\"set\">" +
                            "<document>" +
                              "<name>Risk assessment procedure for information leakage for new systems or upgrades</name>" +
                            "</document>" +
                          "</documents>" +
                        "</survey-question>" +
                        "<survey-question>" +
                          "<abbreviation>12.6.1</abbreviation>" +
                          "<question-text>Does your organization have a process to monitor technical vulnerabilities (based on your organization&apos;s system / information asset list) by regularly reviewing warnings and information published by system companies and credible technical sources?</question-text>" +
                          "<question-set>" +
                            "<name>Question Set</name>" +
                          "</question-set>" +
                          "<documents class=\"set\">" +
                            "<document>" +
                              "<name>Vulnerability assessments reports of systems, applications, and networks</name>" +
                            "</document>" +
                            "<document>" +
                              "<name>Vulnerability and threat management policy and procedures</name>" +
                            "</document>" +
                          "</documents>" +
                        "</survey-question>" +
                        "<survey-question>" +
                          "<abbreviation>13.1.1</abbreviation>" +
                          "<question-text>Does your organization have an awareness program and a process for reporting non-compliance of security policies to appropriate management?</question-text>" +
                          "<question-set>" +
                            "<name>Question Set</name>" +
                          "</question-set>" +
                          "<documents class=\"set\">" +
                            "<document>" +
                              "<name>Security incident reporting awareness training materials</name>" +
                            "</document>" +
                            "<document>" +
                              "<name>Security incident reporting and handling records</name>" +
                            "</document>" +
                            "<document>" +
                              "<name>Incident management procedure</name>" +
                            "</document>" +
                            "<document>" +
                              "<name>Procedure for incident report notification</name>" +
                            "</document>" +
                          "</documents>" +
                        "</survey-question>" +
                        "<survey-question>" +
                          "<abbreviation>13.1.2</abbreviation>" +
                          "<question-text>Does your organization have an awareness program and a process for reporting security weaknesses?</question-text>" +
                          "<question-set>" +
                            "<name>Question Set</name>" +
                          "</question-set>" +
                          "<documents class=\"set\">" +
                            "<document>" +
                              "<name>Awareness training for security weaknesses</name>" +
                            "</document>" +
                          "</documents>" +
                        "</survey-question>" +
                        "<survey-question>" +
                          "<abbreviation>13.2.1</abbreviation>" +
                          "<question-text>Does your organization have a process for responding to security incidents; including incident classification, logging of incident, evaluation, response, and escalation?</question-text>" +
                          "<question-set>" +
                            "<name>Question Set</name>" +
                          "</question-set>" +
                          "<documents class=\"set\">" +
                            "<document>" +
                              "<name>Security incident management procedure</name>" +
                            "</document>" +
                            "<document>" +
                              "<name>Security incident handling training records</name>" +
                            "</document>" +
                            "<document>" +
                              "<name>Security log review policies and procedures</name>" +
                            "</document>" +
                            "<document>" +
                              "<name>Security incident response team charter/list</name>" +
                            "</document>" +
                          "</documents>" +
                          "<answer>" +
                            "<response-id>233</response-id>" +
                            "<selected-answer>" +
                              "<name>In Place</name>" +
                              "<description>This is currently in place</description>" +
                            "</selected-answer>" +
                            "<supporting-documentation>" +
                              "<document>" +
                                "<name>Security incident handling training records</name>" +
                              "</document>" +
                            "</supporting-documentation>" +
                            "<comment></comment>" +
                            "<answer-time>2012-08-20 10:16:09.000</answer-time>" +
                            "<answered-by>rdrzewucki</answered-by>" +
                            "<target-date></target-date>" +
                          "</answer>" +
                        "</survey-question>" +
                        "<survey-question>" +
                          "<abbreviation>13.2.2</abbreviation>" +
                          "<question-text>Does your organization have a process to conduct reviews of incident reports to evaluate risks, costs, and preventive actions that may be required?</question-text>" +
                          "<question-set>" +
                            "<name>Question Set</name>" +
                          "</question-set>" +
                          "<documents class=\"set\">" +
                            "<document>" +
                              "<name>Security incident review procedure records</name>" +
                            "</document>" +
                            "<document>" +
                              "<name>Security monitoring policy / procedure</name>" +
                            "</document>" +
                          "</documents>" +
                        "</survey-question>" +
                        "<survey-question>" +
                          "<abbreviation>13.2.3</abbreviation>" +
                          "<question-text>Does your organization have a process for escalating security incidents which may involve disciplinary or legal action?</question-text>" +
                          "<question-set>" +
                            "<name>Question Set</name>" +
                          "</question-set>" +
                          "<documents class=\"set\">" +
                            "<document>" +
                              "<name>Evidence collection and retention procedures</name>" +
                            "</document>" +
                            "<document>" +
                              "<name>Incident escallation process</name>" +
                            "</document>" +
                            "<document>" +
                              "<name>Legal and regulatory compliance reviews</name>" +
                            "</document>" +
                          "</documents>" +
                        "</survey-question>" +
                        "<survey-question>" +
                          "<abbreviation>14.1.1</abbreviation>" +
                          "<question-text>Does your organization have a process to manage business continuity of information systems and assets?</question-text>" +
                          "<question-set>" +
                            "<name>Question Set</name>" +
                          "</question-set>" +
                          "<documents class=\"set\">" +
                            "<document>" +
                              "<name>BC/DR team charter/list</name>" +
                            "</document>" +
                            "<document>" +
                              "<name>Business continuity - disaster recover plan / procedure(s)</name>" +
                            "</document>" +
                          "</documents>" +
                        "</survey-question>" +
                        "<survey-question>" +
                          "<abbreviation>14.1.2</abbreviation>" +
                          "<question-text>Does your organization regularly conduct risk assessments for the critical systems / assets to assess the likelihood and impact of the risks to business continuity?</question-text>" +
                          "<question-set>" +
                            "<name>Question Set</name>" +
                          "</question-set>" +
                          "<documents class=\"set\">" +
                            "<document>" +
                              "<name>Business continuity risk assessment</name>" +
                            "</document>" +
                            "<document>" +
                              "<name>Business impact analysis document</name>" +
                            "</document>" +
                          "</documents>" +
                        "</survey-question>" +
                        "<survey-question>" +
                          "<abbreviation>14.1.3</abbreviation>" +
                          "<question-text>Does your organization have a business continuity plan for each of the critical information systems / assets on the organization&apos;s inventory list to minimize the impact and ensure the availability of information?</question-text>" +
                          "<question-set>" +
                            "<name>Question Set</name>" +
                          "</question-set>" +
                          "<documents class=\"set\">" +
                            "<document>" +
                              "<name>Business continuity - disaster recover plan / procedure(s)</name>" +
                            "</document>" +
                            "<document>" +
                              "<name>Inventory / list of critical information systems / assets</name>" +
                            "</document>" +
                          "</documents>" +
                        "</survey-question>" +
                        "<survey-question>" +
                          "<abbreviation>15.1.1</abbreviation>" +
                          "<question-text>Does your organization have a process to document and verify that all relevant statutory, regulatory, and contractual requirements are implemented and reviewed periodically or when systems are modified / retired to ensure continued compliance?</question-text>" +
                          "<question-set>" +
                            "<name>Question Set</name>" +
                          "</question-set>" +
                          "<documents class=\"set\">" +
                            "<document>" +
                              "<name>Inventory of compliance requirements</name>" +
                            "</document>" +
                            "<document>" +
                              "<name>Sanctions policy</name>" +
                            "</document>" +
                            "<document>" +
                              "<name>Legal and regulatory compliance reviews</name>" +
                            "</document>" +
                            "<document>" +
                              "<name>Compliance tracking matrix</name>" +
                            "</document>" +
                            "<document>" +
                              "<name>Compliance policy</name>" +
                            "</document>" +
                          "</documents>" +
                        "</survey-question>" +
                        "<survey-question>" +
                          "<abbreviation>15.1.2</abbreviation>" +
                          "<question-text>Does your organization have a process to document and verify that all relevant intellectual property rights requirements are implemented and reviewed periodically or when systems are modified / retired to ensure continued compliance?</question-text>" +
                          "<question-set>" +
                            "<name>Question Set</name>" +
                          "</question-set>" +
                          "<documents class=\"set\">" +
                            "<document>" +
                              "<name>Intellectual rights monitoring policy</name>" +
                            "</document>" +
                            "<document>" +
                              "<name>Intellectual rights compliance reviews</name>" +
                            "</document>" +
                            "<document>" +
                              "<name>Intellectual property protection policy</name>" +
                            "</document>" +
                          "</documents>" +
                        "</survey-question>" +
                        "<survey-question>" +
                          "<abbreviation>15.1.3</abbreviation>" +
                          "<question-text>Does your organization have a process to document and verify that all relevant record retention and reproducibility requirements are implemented and reviewed periodically or when systems are modified / retired to ensure continued compliance?</question-text>" +
                          "<question-set>" +
                            "<name>Question Set</name>" +
                          "</question-set>" +
                          "<documents class=\"set\">" +
                            "<document>" +
                              "<name>Records retention policy</name>" +
                            "</document>" +
                          "</documents>" +
                        "</survey-question>" +
                        "<survey-question>" +
                          "<abbreviation>15.1.4</abbreviation>" +
                          "<question-text>Does your organization have a process to document and verify that all relevant privacy of personal information requirements are implemented and reviewed periodically or when systems are modified / retired to ensure continued compliance?</question-text>" +
                          "<question-set>" +
                            "<name>Question Set</name>" +
                          "</question-set>" +
                          "<documents class=\"set\">" +
                            "<document>" +
                              "<name>Privacy policy</name>" +
                            "</document>" +
                            "<document>" +
                              "<name>Incident response policy, process, and/or plan</name>" +
                            "</document>" +
                            "<document>" +
                              "<name>Privacy notice (on-line)</name>" +
                            "</document>" +
                          "</documents>" +
                        "</survey-question>" +
                        "<survey-question>" +
                          "<abbreviation>15.2.1</abbreviation>" +
                          "<question-text>Does your organization have a process to ensure that managers conduct periodic reviews of their area of responsibility to ensure compliance with organizational and departmental information security policies and procedures?</question-text>" +
                          "<question-set>" +
                            "<name>Question Set</name>" +
                          "</question-set>" +
                          "<documents class=\"set\">" +
                            "<document>" +
                              "<name>Security compliance review records / reports</name>" +
                            "</document>" +
                            "<document>" +
                              "<name>Security compliance review procedures</name>" +
                            "</document>" +
                          "</documents>" +
                        "</survey-question>" +
                        "<survey-question>" +
                          "<abbreviation>10.10.1</abbreviation>" +
                          "<question-text>Does your organization ensure that audit logs are implemented to record access, usage, alarms, and failed login attempts on sensitive or critical information systems? </question-text>" +
                          "<question-set>" +
                            "<name>Question Set</name>" +
                          "</question-set>" +
                          "<documents class=\"set\">" +
                            "<document>" +
                              "<name>Procedure, guideline, or standard for system logging configuration</name>" +
                            "</document>" +
                            "<document>" +
                              "<name>Intrusion detection / prevention event logs</name>" +
                            "</document>" +
                            "<document>" +
                              "<name>System audit logs</name>" +
                            "</document>" +
                          "</documents>" +
                        "</survey-question>" +
                        "<survey-question>" +
                          "<abbreviation>10.10.2</abbreviation>" +
                          "<question-text>Does your organization have a process that defines the frequency and method for conducting reviews of audit logs based on risk levels?</question-text>" +
                          "<question-set>" +
                            "<name>Question Set</name>" +
                          "</question-set>" +
                          "<documents class=\"set\">" +
                            "<document>" +
                              "<name>List of user accounts for critical applications</name>" +
                            "</document>" +
                            "<document>" +
                              "<name>Records of intrusion detection / prevention event log reviews</name>" +
                            "</document>" +
                            "<document>" +
                              "<name>Record of audit log  reviews</name>" +
                            "</document>" +
                            "<document>" +
                              "<name>Procedure for monitoring / reviewing audit logs</name>" +
                            "</document>" +
                          "</documents>" +
                          "<answer>" +
                            "<response-id>223</response-id>" +
                            "<selected-answer>" +
                              "<name>Not In Place</name>" +
                              "<description>This is currently not in place</description>" +
                            "</selected-answer>" +
                            "<supporting-documentation/>" +
                            "<comment></comment>" +
                            "<answer-time>2012-05-30 11:21:47.000</answer-time>" +
                            "<answered-by>vfazio</answered-by>" +
                          "</answer>" +
                        "</survey-question>" +
                        "<survey-question>" +
                          "<abbreviation>10.10.3</abbreviation>" +
                          "<question-text>Does your organization have controls to protect against tampering or unauthorized access to audit logs?</question-text>" +
                          "<question-set>" +
                            "<name>Question Set</name>" +
                          "</question-set>" +
                          "<documents class=\"set\">" +
                            "<document>" +
                              "<name>Documentation showing log audit logs are secure from modification or deletion</name>" +
                            "</document>" +
                            "<document>" +
                              "<name>Centralized log server configuration</name>" +
                            "</document>" +
                            "<document>" +
                              "<name>Default logging settings (e.g., GPO)</name>" +
                            "</document>" +
                          "</documents>" +
                        "</survey-question>" +
                        "<survey-question>" +
                          "<abbreviation>10.10.4</abbreviation>" +
                          "<question-text>Does your organization ensure that audit logs are implemented to record activities of administrators and operators?</question-text>" +
                          "<question-set>" +
                            "<name>Question Set</name>" +
                          "</question-set>" +
                          "<documents class=\"set\">" +
                            "<document>" +
                              "<name>Audit records of third party service providers</name>" +
                            "</document>" +
                          "</documents>" +
                        "</survey-question>" +
                        "<survey-question>" +
                          "<abbreviation>10.10.5</abbreviation>" +
                          "<question-text>Does your organization ensure that both system-generated and user-reported errors are logged and investigated for corrective and preventive action?</question-text>" +
                          "<question-set>" +
                            "<name>Question Set</name>" +
                          "</question-set>" +
                          "<documents class=\"set\">" +
                            "<document>" +
                              "<name>System fault / error logs</name>" +
                            "</document>" +
                          "</documents>" +
                        "</survey-question>" +
                      "</questions>" +
                    "</survey>";
    
    private String securitySurveyXml2 = 
                "<?xml version=\"1.0\" ?><survey>" +
                "<questions class=\"tree-set\">" +
                  "<survey-question>" +
                    "<abbreviation>Q_4_0_1</abbreviation>" +
                    "<question-text>Are operating procedures documented, maintained, and made available to all users who need them?</question-text>" +
                    "<question-set>" +
                      "<name>Survey Question Set</name>" +
                    "</question-set>" +
                    "<documents class=\"set\">" +
                      "<document>" +
                        "<name>Daily operational security procedures</name>" +
                      "</document>" +
                    "</documents>" +
                    "<answer>" +
                      "<response-id>3</response-id>" +
                      "<selected-answer>" +
                        "<name>In Place</name>" +
                        "<description>This is currently in place</description>" +
                      "</selected-answer>" +
                      "<supporting-documentation>" +
                        "<document>" +
                          "<name>Daily operational security procedures</name>" +
                        "</document>" +
                      "</supporting-documentation>" +
                      "<answer-time>2012-11-19 11:13:47.000</answer-time>" +
                      "<answered-by>customer1</answered-by>" +
                    "</answer>" +
                  "</survey-question>" +
                  "<survey-question>" +
                    "<abbreviation>Q_4_0_3</abbreviation>" +
                    "<question-text>Is there a mechanism to enforce segregation of duties between key management roles and normal operational roles?</question-text>" +
                    "<question-set>" +
                      "<name>Survey Question Set</name>" +
                    "</question-set>" +
                    "<documents class=\"set\">" +
                      "<document>" +
                        "<name>Procedure, guideline, or manual describing access granting process</name>" +
                      "</document>" +
                      "<document>" +
                        "<name>Records/logs of changes made based on access reviews</name>" +
                      "</document>" +
                      "<document>" +
                        "<name>Log of access reviews</name>" +
                      "</document>" +
                    "</documents>" +
                    "<answer>" +
                      "<response-id>4</response-id>" +
                      "<selected-answer>" +
                        "<name>In Place</name>" +
                        "<description>This is currently in place</description>" +
                      "</selected-answer>" +
                      "<supporting-documentation>" +
                        "<document>" +
                          "<name>Procedure, guideline, or manual describing access granting process</name>" +
                        "</document>" +
                      "</supporting-documentation>" +
                      "<answer-time>2012-11-19 11:26:42.000</answer-time>" +
                      "<answered-by>customer1</answered-by>" +
                    "</answer>" +
                  "</survey-question>" +
                  "<survey-question>" +
                    "<abbreviation>Q_4_0_5</abbreviation>" +
                    "<question-text>Are all successful and failed wireless connections logged?</question-text>" +
                    "<question-set>" +
                      "<name>Survey Question Set</name>" +
                    "</question-set>" +
                    "<documents class=\"set\">" +
                      "<document>" +
                        "<name>Record/log of logging system reviews</name>" +
                      "</document>" +
                      "<document>" +
                        "<name>Active outsourced individuals, contractors, third parties list</name>" +
                      "</document>" +
                      "<document>" +
                        "<name>Procedure, guideline, or standard for system logging configuration</name>" +
                      "</document>" +
                      "<document>" +
                        "<name>List of user accounts for critical applications</name>" +
                      "</document>" +
                      "<document>" +
                        "<name>Active users with access to sensitive data (list)</name>" +
                      "</document>" +
                      "<document>" +
                        "<name>IDS/IPS diagram and configuration</name>" +
                      "</document>" +
                      "<document>" +
                        "<name>DS/IPS event logs</name>" +
                      "</document>" +
                      "<document>" +
                        "<name>External code review or penetration test documentation</name>" +
                      "</document>" +
                    "</documents>" +
                  "</survey-question>" +
                  "<survey-question>" +
                    "<abbreviation>Q_4_0_6</abbreviation>" +
                    "<question-text>Is there a host-based or network-based intrusion detection/prevention system in place and actively monitored?</question-text>" +
                    "<question-set>" +
                      "<name>Survey Question Set</name>" +
                    "</question-set>" +
                    "<documents class=\"set\">" +
                      "<document>" +
                        "<name>Centralized log server configuration</name>" +
                      "</document>" +
                      "<document>" +
                        "<name>Default logging settings (e.g., GPO)</name>" +
                      "</document>" +
                    "</documents>" +
                  "</survey-question>" +
                  "<survey-question>" +
                    "<abbreviation>Q_4_0_15</abbreviation>" +
                    "<question-text>Is backup media stored offsite?</question-text>" +
                    "<question-set>" +
                      "<name>Survey Question Set</name>" +
                    "</question-set>" +
                    "<documents class=\"set\">" +
                      "<document>" +
                        "<name>Backup media inventory</name>" +
                      "</document>" +
                      "<document>" +
                        "<name>Backup system logs</name>" +
                      "</document>" +
                      "<document>" +
                        "<name>Off-line archive/export of applicable router and/or firewall configuration(s)</name>" +
                      "</document>" +
                      "<document>" +
                        "<name>Restore testing logs</name>" +
                      "</document>" +
                      "<document>" +
                        "<name>Backup schedule</name>" +
                      "</document>" +
                    "</documents>" +
                  "</survey-question>" +
                  "<survey-question>" +
                    "<abbreviation>Q_4_0_16</abbreviation>" +
                    "<question-text>Are constituents required to use an approved standard operating environment?</question-text>" +
                    "<question-set>" +
                      "<name>Survey Question Set</name>" +
                    "</question-set>" +
                    "<documents class=\"set\">" +
                      "<document>" +
                        "<name>Procedure, guideline, or standard for firewall configuration</name>" +
                      "</document>" +
                      "<document>" +
                        "<name>Network diagram showing IDS/IPS</name>" +
                      "</document>" +
                      "<document>" +
                        "<name>802.11 wireless architecture topology</name>" +
                      "</document>" +
                      "<document>" +
                        "<name>Firewall change log</name>" +
                      "</document>" +
                      "<document>" +
                        "<name>Network topology diagram (wired)</name>" +
                      "</document>" +
                      "<document>" +
                        "<name>Wireless network security policy</name>" +
                      "</document>" +
                    "</documents>" +
                  "</survey-question>" +
                  "<survey-question>" +
                    "<abbreviation>Q_4_0_20</abbreviation>" +
                    "<question-text>Is there a process to address the reuse of media?</question-text>" +
                    "<question-set>" +
                      "<name>Survey Question Set</name>" +
                    "</question-set>" +
                    "<documents class=\"set\">" +
                      "<document>" +
                        "<name>Physical media policy</name>" +
                      "</document>" +
                    "</documents>" +
                  "</survey-question>" +
                  "<survey-question>" +
                    "<abbreviation>Q_4_0_23</abbreviation>" +
                    "<question-text>Is unencrypted e-mail used for the transmission or receipt of customer data outside of normal business correspondance?</question-text>" +
                    "<question-set>" +
                      "<name>Survey Question Set</name>" +
                    "</question-set>" +
                    "<documents class=\"set\">" +
                      "<document>" +
                        "<name>Electronic messaging policy</name>" +
                      "</document>" +
                    "</documents>" +
                  "</survey-question>" +
                  "<survey-question>" +
                    "<abbreviation>Q_4_0_46</abbreviation>" +
                    "<question-text>Are validation checks performed on applications to detect any corruption of data?</question-text>" +
                    "<question-set>" +
                      "<name>Survey Question Set</name>" +
                    "</question-set>" +
                    "<documents class=\"set\">" +
                      "<document>" +
                        "<name>Security procedures or guidelines for developers</name>" +
                      "</document>" +
                    "</documents>" +
                  "</survey-question>" +
                  "<survey-question>" +
                    "<abbreviation>Q_4_0_54</abbreviation>" +
                    "<question-text>Are security patches regularly reviewed and applied to network devices?</question-text>" +
                    "<question-set>" +
                      "<name>Survey Question Set</name>" +
                    "</question-set>" +
                    "<documents class=\"set\">" +
                      "<document>" +
                        "<name>Wireless assessment reports</name>" +
                      "</document>" +
                      "<document>" +
                        "<name>External quarterly scan reports</name>" +
                      "</document>" +
                      "<document>" +
                        "<name>Record/log of vulnerability assessments</name>" +
                      "</document>" +
                      "<document>" +
                        "<name>Record/log of penetration testing</name>" +
                      "</document>" +
                      "<document>" +
                        "<name>Record/log of vulnerability assessment corrective actions</name>" +
                      "</document>" +
                      "<document>" +
                        "<name>Vulnerability management policy</name>" +
                      "</document>" +
                      "<document>" +
                        "<name>Vulnerability assessment reports</name>" +
                      "</document>" +
                      "<document>" +
                        "<name>Patch management process</name>" +
                      "</document>" +
                      "<document>" +
                        "<name>Software development systems architecture</name>" +
                      "</document>" +
                      "<document>" +
                        "<name>Vulnerability assessment policy</name>" +
                      "</document>" +
                      "<document>" +
                        "<name>Technical vulnerability management procedure</name>" +
                      "</document>" +
                      "<document>" +
                        "<name>Internal quarterly scan reports</name>" +
                      "</document>" +
                    "</documents>" +
                  "</survey-question>" +
                  "<survey-question>" +
                    "<abbreviation>Q_4_0_55</abbreviation>" +
                    "<question-text>Is there a security incident response team with defined roles and responsibilities?</question-text>" +
                    "<question-set>" +
                      "<name>Survey Question Set</name>" +
                    "</question-set>" +
                    "<documents class=\"set\">" +
                      "<document>" +
                        "<name>Incident reporting and handling records</name>" +
                      "</document>" +
                      "<document>" +
                        "<name>Incident management procedure</name>" +
                      "</document>" +
                      "<document>" +
                        "<name>Procedure for incident report notification</name>" +
                      "</document>" +
                    "</documents>" +
                  "</survey-question>" +
                  "<survey-question>" +
                    "<abbreviation>Q_4_0_56</abbreviation>" +
                    "<question-text>Are incident management procedures tested at least annually?</question-text>" +
                    "<question-set>" +
                      "<name>Survey Question Set</name>" +
                    "</question-set>" +
                    "<documents class=\"set\">" +
                      "<document>" +
                        "<name>System monitoring procedure</name>" +
                      "</document>" +
                      "<document>" +
                        "<name>Security monitoring policy</name>" +
                      "</document>" +
                      "<document>" +
                        "<name>Record/log of incident response testing</name>" +
                      "</document>" +
                    "</documents>" +
                  "</survey-question>" +
                  "<survey-question>" +
                    "<abbreviation>Q_4_0_57</abbreviation>" +
                    "<question-text>Is documentation maintained on incidents (issues, outcomes, and remediation)?</question-text>" +
                    "<question-set>" +
                      "<name>Survey Question Set</name>" +
                    "</question-set>" +
                    "<documents class=\"set\">" +
                      "<document>" +
                        "<name>Evidence collection and retention procedures</name>" +
                      "</document>" +
                      "<document>" +
                        "<name>Legal and regulatory compliance reviews</name>" +
                      "</document>" +
                    "</documents>" +
                  "</survey-question>" +
                  "<survey-question>" +
                    "<abbreviation>Q_4_0_59</abbreviation>" +
                    "<question-text>Is a Business Impact Analysis conducted at least annually?</question-text>" +
                    "<question-set>" +
                      "<name>Survey Question Set</name>" +
                    "</question-set>" +
                    "<documents class=\"set\">" +
                      "<document>" +
                        "<name>Business continuity risk assessment</name>" +
                      "</document>" +
                      "<document>" +
                        "<name>Business impact analysis document</name>" +
                      "</document>" +
                      "<document>" +
                        "<name>Risk Assessment</name>" +
                      "</document>" +
                    "</documents>" +
                  "</survey-question>" +
                  "<survey-question>" +
                    "<abbreviation>Q_4_0_63</abbreviation>" +
                    "<question-text>Does your organization have any requirements to comply with legal, regulatory, or industry requirements, such as HIPAA/HITECH, PCI, GLBA, SOX, etc.?</question-text>" +
                    "<question-set>" +
                      "<name>Survey Question Set</name>" +
                    "</question-set>" +
                    "<documents class=\"set\">" +
                      "<document>" +
                        "<name>Inventory of compliance requirements</name>" +
                      "</document>" +
                      "<document>" +
                        "<name>Sanctions policy</name>" +
                      "</document>" +
                      "<document>" +
                        "<name>Legal and regulatory compliance reviews</name>" +
                      "</document>" +
                      "<document>" +
                        "<name>Compliance tracking matrix</name>" +
                      "</document>" +
                      "<document>" +
                        "<name>Compliance policy</name>" +
                      "</document>" +
                    "</documents>" +
                  "</survey-question>" +
                  "<survey-question>" +
                    "<abbreviation>Q_4_0_64</abbreviation>" +
                    "<question-text>Are procedures implemented to ensure compliance with legislative, regulatory, and contractual requirements on the use of material where intellectual property rights may be applied and on the use of proprietary software products?</question-text>" +
                    "<question-set>" +
                      "<name>Survey Question Set</name>" +
                    "</question-set>" +
                    "<documents class=\"set\">" +
                      "<document>" +
                        "<name>Compliance monitoring policy</name>" +
                      "</document>" +
                      "<document>" +
                        "<name>Software development procedure</name>" +
                      "</document>" +
                      "<document>" +
                        "<name>Software development policy</name>" +
                      "</document>" +
                      "<document>" +
                        "<name>Intellectual property protection policy</name>" +
                      "</document>" +
                    "</documents>" +
                  "</survey-question>" +
                  "<survey-question>" +
                    "<abbreviation>Q_4_0_65</abbreviation>" +
                    "<question-text>Is there a records retention policy?</question-text>" +
                    "<question-set>" +
                      "<name>Survey Question Set</name>" +
                    "</question-set>" +
                    "<documents class=\"set\"/>" +
                  "</survey-question>" +
                  "<survey-question>" +
                    "<abbreviation>Q_4_0_66</abbreviation>" +
                    "<question-text>Is the privacy policy posted on your web site home page?</question-text>" +
                    "<question-set>" +
                      "<name>Survey Question Set</name>" +
                    "</question-set>" +
                    "<documents class=\"set\">" +
                      "<document>" +
                        "<name>Privacy policy</name>" +
                      "</document>" +
                      "<document>" +
                        "<name>Privacy notice (on-line)</name>" +
                      "</document>" +
                    "</documents>" +
                  "</survey-question>" +
                  "<survey-question>" +
                    "<abbreviation>Q_4_0_69</abbreviation>" +
                    "<question-text>Is there a standard process for incident tracking, monitoring, and resolution?</question-text>" +
                    "<question-set>" +
                      "<name>Survey Question Set</name>" +
                    "</question-set>" +
                    "<documents class=\"set\">" +
                      "<document>" +
                        "<name>Record/log of corrective actions related to internal or external assessments</name>" +
                      "</document>" +
                      "<document>" +
                        "<name>Audit logs</name>" +
                      "</document>" +
                      "<document>" +
                        "<name>External assessment reports</name>" +
                      "</document>" +
                      "<document>" +
                        "<name>Compliance assessment report (internal or external)</name>" +
                      "</document>" +
                      "<document>" +
                        "<name>Active users with administrative privileges (list)</name>" +
                      "</document>" +
                      "<document>" +
                        "<name>Corrective action plan</name>" +
                      "</document>" +
                      "<document>" +
                        "<name>Active users with remote access (list)</name>" +
                      "</document>" +
                    "</documents>" +
                  "</survey-question>" +
                  "<survey-question>" +
                    "<abbreviation>Q_4_0_73</abbreviation>" +
                    "<question-text>Is there a Disaster Recovery plan?</question-text>" +
                    "<question-set>" +
                      "<name>Survey Question Set</name>" +
                    "</question-set>" +
                    "<documents class=\"set\">" +
                      "<document>" +
                        "<name>Third party security policy</name>" +
                      "</document>" +
                      "<document>" +
                        "<name>Information security policy (one or more documents)</name>" +
                      "</document>" +
                      "<document>" +
                        "<name>Evidence of management approval for current policies</name>" +
                      "</document>" +
                      "<document>" +
                        "<name>Sanctions policy</name>" +
                      "</document>" +
                      "<document>" +
                        "<name>Statement of applicability/security control inventory</name>" +
                      "</document>" +
                      "<document>" +
                        "<name>Evidence of communication to staff</name>" +
                      "</document>" +
                    "</documents>" +
                  "</survey-question>" +
                  "<survey-question>" +
                    "<abbreviation>Q_4_0_75</abbreviation>" +
                    "<question-text>Is there an individual or group responsible for security within the organization?</question-text>" +
                    "<question-set>" +
                      "<name>Survey Question Set</name>" +
                    "</question-set>" +
                    "<documents class=\"set\">" +
                      "<document>" +
                        "<name>Security program charter</name>" +
                      "</document>" +
                      "<document>" +
                        "<name>Job descriptions that include security responsibilities</name>" +
                      "</document>" +
                    "</documents>" +
                  "</survey-question>" +
                  "<survey-question>" +
                    "<abbreviation>Q_4_0_76</abbreviation>" +
                    "<question-text>Is there an internal audit, risk management or compliance department with responsibility for identifying and tracking resolution of outstanding regulatory issues?</question-text>" +
                    "<question-set>" +
                      "<name>Survey Question Set</name>" +
                    "</question-set>" +
                    "<documents class=\"set\">" +
                      "<document>" +
                        "<name>Information Security Plan</name>" +
                      "</document>" +
                      "<document>" +
                        "<name>Organizational chart showing security role(s)</name>" +
                      "</document>" +
                      "<document>" +
                        "<name>Roles and Responsibilities</name>" +
                      "</document>" +
                      "<document>" +
                        "<name>Information Security Responsibilities (Incl. Job Descriptions)</name>" +
                      "</document>" +
                    "</documents>" +
                  "</survey-question>" +
                  "<survey-question>" +
                    "<abbreviation>Q_4_0_77</abbreviation>" +
                    "<question-text>Are information security responsibilities allocated to an individual or group?</question-text>" +
                    "<question-set>" +
                      "<name>Survey Question Set</name>" +
                    "</question-set>" +
                    "<documents class=\"set\">" +
                      "<document>" +
                        "<name>Code of ethics</name>" +
                      "</document>" +
                      "<document>" +
                        "<name>Roles and Responsibilities</name>" +
                      "</document>" +
                      "<document>" +
                        "<name>Section of employee handbook on security responsibilities</name>" +
                      "</document>" +
                      "<document>" +
                        "<name>Employment agreement</name>" +
                      "</document>" +
                      "<document>" +
                        "<name>Information Security Responsibilities (Incl. Job Descriptions)</name>" +
                      "</document>" +
                    "</documents>" +
                  "</survey-question>" +
                  "<survey-question>" +
                    "<abbreviation>Q_4_0_78</abbreviation>" +
                    "<question-text>Is there an authorization process for new information processing facilities?</question-text>" +
                    "<question-set>" +
                      "<name>Survey Question Set</name>" +
                    "</question-set>" +
                    "<documents class=\"set\">" +
                      "<document>" +
                        "<name>Security requirements for systems policy</name>" +
                      "</document>" +
                      "<document>" +
                        "<name>Change control / management procedure</name>" +
                      "</document>" +
                    "</documents>" +
                  "</survey-question>" +
                  "<survey-question>" +
                    "<abbreviation>Q_4_0_79</abbreviation>" +
                    "<question-text>Does management require the use of confidentiality or non-disclosure agreements?</question-text>" +
                    "<question-set>" +
                      "<name>Survey Question Set</name>" +
                    "</question-set>" +
                    "<documents class=\"set\">" +
                      "<document>" +
                        "<name>Third party security policy</name>" +
                      "</document>" +
                      "<document>" +
                        "<name>Third-party contract template</name>" +
                      "</document>" +
                      "<document>" +
                        "<name>Non-disclosure agreement</name>" +
                      "</document>" +
                      "<document>" +
                        "<name>Cardholder data protection policy</name>" +
                      "</document>" +
                      "<document>" +
                        "<name>Confidentiality agreements</name>" +
                      "</document>" +
                      "<document>" +
                        "<name>Confidentiality agreement template</name>" +
                      "</document>" +
                    "</documents>" +
                  "</survey-question>" +
                  "<survey-question>" +
                    "<abbreviation>Q_4_0_82</abbreviation>" +
                    "<question-text>Are all software applications and infrastructure devices and systems independently tested and validated to your standards prior to implementation?</question-text>" +
                    "<question-set>" +
                      "<name>Survey Question Set</name>" +
                    "</question-set>" +
                    "<documents class=\"set\">" +
                      "<document>" +
                        "<name>Change control / management procedure</name>" +
                      "</document>" +
                    "</documents>" +
                  "</survey-question>" +
                  "<survey-question>" +
                    "<abbreviation>Q_4_0_83</abbreviation>" +
                    "<question-text>Are risk assessments or reviews conducted on your third parties?</question-text>" +
                    "<question-set>" +
                      "<name>Survey Question Set</name>" +
                    "</question-set>" +
                    "<documents class=\"set\">" +
                      "<document>" +
                        "<name>Third-party security agreements</name>" +
                      "</document>" +
                      "<document>" +
                        "<name>Security or risk assessment reports</name>" +
                      "</document>" +
                      "<document>" +
                        "<name>Risk treatment plan(s)</name>" +
                      "</document>" +
                      "<document>" +
                        "<name>Risk management policy</name>" +
                      "</document>" +
                      "<document>" +
                        "<name>Third-party risk assessment</name>" +
                      "</document>" +
                      "<document>" +
                        "<name>Risk Assessment</name>" +
                      "</document>" +
                      "<document>" +
                        "<name>Service provider list</name>" +
                      "</document>" +
                    "</documents>" +
                  "</survey-question>" +
                  "<survey-question>" +
                    "<abbreviation>Q_4_0_86</abbreviation>" +
                    "<question-text>Is there an inventory of hardware/software assets?</question-text>" +
                    "<question-set>" +
                      "<name>Survey Question Set</name>" +
                    "</question-set>" +
                    "<documents class=\"set\">" +
                      "<document>" +
                        "<name>Media tracking and storage process</name>" +
                      "</document>" +
                      "<document>" +
                        "<name>Media management procedure</name>" +
                      "</document>" +
                      "<document>" +
                        "<name>Media inventory procedure</name>" +
                      "</document>" +
                      "<document>" +
                        "<name>Asset inventory</name>" +
                      "</document>" +
                      "<document>" +
                        "<name>Inventory log (record of periodic review/maintenance)</name>" +
                      "</document>" +
                    "</documents>" +
                  "</survey-question>" +
                  "<survey-question>" +
                    "<abbreviation>Q_4_0_92</abbreviation>" +
                    "<question-text>Are background screenings of applicants performed to include criminal, credit, professional / academic, references and drug screening?</question-text>" +
                    "<question-set>" +
                      "<name>Survey Question Set</name>" +
                    "</question-set>" +
                    "<documents class=\"set\">" +
                      "<document>" +
                        "<name>Record/log of screening</name>" +
                      "</document>" +
                      "<document>" +
                        "<name>Data/asset classification and control policy</name>" +
                      "</document>" +
                      "<document>" +
                        "<name>Screening procedure</name>" +
                      "</document>" +
                      "<document>" +
                        "<name>Background check procedure</name>" +
                      "</document>" +
                      "<document>" +
                        "<name>Data classification procedure</name>" +
                      "</document>" +
                      "<document>" +
                        "<name>Verification of background checks</name>" +
                      "</document>" +
                    "</documents>" +
                  "</survey-question>" +
                  "<survey-question>" +
                    "<abbreviation>Q_4_0_93</abbreviation>" +
                    "<question-text>Are new hires required to sign any agreements that pertain to non/disclosure, confidentiality, acceptable use or code of ethics upon hire?</question-text>" +
                    "<question-set>" +
                      "<name>Survey Question Set</name>" +
                    "</question-set>" +
                    "<documents class=\"set\">" +
                      "<document>" +
                        "<name>Employment agreement</name>" +
                      "</document>" +
                    "</documents>" +
                  "</survey-question>" +
                  "<survey-question>" +
                    "<abbreviation>Q_4_0_94</abbreviation>" +
                    "<question-text>Do constituents responsible for information security undergo additional training?</question-text>" +
                    "<question-set>" +
                      "<name>Survey Question Set</name>" +
                    "</question-set>" +
                    "<documents class=\"set\">" +
                      "<document>" +
                        "<name>Record of training (logs and/or certificates)</name>" +
                      "</document>" +
                      "<document>" +
                        "<name>Security awareness material</name>" +
                      "</document>" +
                      "<document>" +
                        "<name>Training and awareness materials</name>" +
                      "</document>" +
                      "<document>" +
                        "<name>Training and awareness policy</name>" +
                      "</document>" +
                      "<document>" +
                        "<name>Incident handling team training records</name>" +
                      "</document>" +
                      "<document>" +
                        "<name>Malicious code awareness procedure</name>" +
                      "</document>" +
                      "<document>" +
                        "<name>Training plans</name>" +
                      "</document>" +
                    "</documents>" +
                    "<answer>" +
                      "<response-id>2</response-id>" +
                      "<selected-answer>" +
                        "<name>In Place</name>" +
                        "<description>This is currently in place</description>" +
                      "</selected-answer>" +
                      "<supporting-documentation>" +
                        "<document>" +
                          "<name>Security awareness material</name>" +
                        "</document>" +
                        "<document>" +
                          "<name>Training and awareness materials</name>" +
                        "</document>" +
                      "</supporting-documentation>" +
                      "<answer-time>2012-11-14 16:11:09.000</answer-time>" +
                      "<answered-by>customer1</answered-by>" +
                    "</answer>" +
                  "</survey-question>" +
                  "<survey-question>" +
                    "<abbreviation>Q_4_0_96</abbreviation>" +
                    "<question-text>Is there a documented termination or change of status policy or process?</question-text>" +
                    "<question-set>" +
                      "<name>Survey Question Set</name>" +
                    "</question-set>" +
                    "<documents class=\"set\">" +
                      "<document>" +
                        "<name>Employment agreement requiring return of assets</name>" +
                      "</document>" +
                      "<document>" +
                        "<name>Terminated employee list</name>" +
                      "</document>" +
                      "<document>" +
                        "<name>Termination procedure</name>" +
                      "</document>" +
                    "</documents>" +
                  "</survey-question>" +
                  "<survey-question>" +
                    "<abbreviation>Q_4_0_97</abbreviation>" +
                    "<question-text>Does HR notify security / access administration of a constituents change of status for access rights removal?</question-text>" +
                    "<question-set>" +
                      "<name>Survey Question Set</name>" +
                    "</question-set>" +
                    "<documents class=\"set\">" +
                      "<document>" +
                        "<name>Record/log of termination (ticket/work order)</name>" +
                      "</document>" +
                      "<document>" +
                        "<name>Log of periodic access review (audit to verify access rights were removed at termination)</name>" +
                      "</document>" +
                    "</documents>" +
                  "</survey-question>" +
                  "<survey-question>" +
                    "<abbreviation>Q_4_0_98</abbreviation>" +
                    "<question-text>Is the data center shared with other tenants?</question-text>" +
                    "<question-set>" +
                      "<name>Survey Question Set</name>" +
                    "</question-set>" +
                    "<documents class=\"set\">" +
                      "<document>" +
                        "<name>Facility security maintenance records</name>" +
                      "</document>" +
                      "<document>" +
                        "<name>Physical security plan</name>" +
                      "</document>" +
                      "<document>" +
                        "<name>Floorplan showing guard station(s)</name>" +
                      "</document>" +
                      "<document>" +
                        "<name>Facility access control procedure</name>" +
                      "</document>" +
                      "<document>" +
                        "<name>Floorplan/diagram showing security perimeter</name>" +
                      "</document>" +
                      "<document>" +
                        "<name>Physical security policy</name>" +
                      "</document>" +
                      "<document>" +
                        "<name>Site security checklist</name>" +
                      "</document>" +
                    "</documents>" +
                  "</survey-question>" +
                  "<survey-question>" +
                    "<abbreviation>Q_4_0_99</abbreviation>" +
                    "<question-text>Is there a loading dock at the data center of IT facility?</question-text>" +
                    "<question-set>" +
                      "<name>Survey Question Set</name>" +
                    "</question-set>" +
                    "<documents class=\"set\">" +
                      "<document>" +
                        "<name>Floorplan/diagram showing CCTV components</name>" +
                      "</document>" +
                    "</documents>" +
                  "</survey-question>" +
                  "<survey-question>" +
                    "<abbreviation>Q_4_0_100</abbreviation>" +
                    "<question-text>Is there a generator or generator area?</question-text>" +
                    "<question-set>" +
                      "<name>Survey Question Set</name>" +
                    "</question-set>" +
                    "<documents class=\"set\"/>" +
                  "</survey-question>" +
                  "<survey-question>" +
                    "<abbreviation>Q_4_0_102</abbreviation>" +
                    "<question-text>Is physical media that contains target data re-used when no longer required?</question-text>" +
                    "<question-set>" +
                      "<name>Survey Question Set</name>" +
                    "</question-set>" +
                    "<documents class=\"set\">" +
                      "<document>" +
                        "<name>Removal of assets/property policy</name>" +
                      "</document>" +
                      "<document>" +
                        "<name>Media tracking and storage process</name>" +
                      "</document>" +
                      "<document>" +
                        "<name>Media management procedure</name>" +
                      "</document>" +
                      "<document>" +
                        "<name>Media inventory procedure</name>" +
                      "</document>" +
                      "<document>" +
                        "<name>Information backup and retention policy</name>" +
                      "</document>" +
                      "<document>" +
                        "<name>Media tracking logs</name>" +
                      "</document>" +
                      "<document>" +
                        "<name>Media disposal procedure</name>" +
                      "</document>" +
                      "<document>" +
                        "<name>Information/media disposal and/or destruction</name>" +
                      "</document>" +
                      "<document>" +
                        "<name>Data retention and disposal policy</name>" +
                      "</document>" +
                    "</documents>" +
                  "</survey-question>" +
                "</questions>" +
              "</survey>";    
}
