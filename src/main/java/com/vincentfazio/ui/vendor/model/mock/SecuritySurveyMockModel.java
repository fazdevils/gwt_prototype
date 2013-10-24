package com.vincentfazio.ui.vendor.model.mock;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.vincentfazio.ui.bean.DocumentationBean;
import com.vincentfazio.ui.bean.QuestionBean;
import com.vincentfazio.ui.model.SecuritySurveyModel;
import com.vincentfazio.ui.model.TaskModel;
import com.vincentfazio.ui.model.parser.xml.SecuritySurveyXmlParser;

public class SecuritySurveyMockModel implements SecuritySurveyModel {

    private Map<String, List<QuestionBean>> surveyQuestions = new HashMap<String, List<QuestionBean>>();
    private SecuritySurveyXmlParser parser = new SecuritySurveyXmlParser();
    private TaskModel taskModel;  
     
    public SecuritySurveyMockModel(TaskModel taskModel) {
        super();
        this.taskModel = taskModel;
    }

    @Override
    public void getQuestions(String vendorId, AsyncCallback<List<QuestionBean>> asyncCallback) {
        List<QuestionBean> questionList = surveyQuestions.get(vendorId);
        if (null == questionList) {
            questionList = createDefaultQuestionSet(vendorId);
            surveyQuestions.put(vendorId, questionList);
        } 
        
        List<QuestionBean> questionsCopy = copyQuestions(questionList);
        asyncCallback.onSuccess(questionsCopy);
    }

	private void updateRequestedDocumentation(String vendorId, QuestionBean question) {
		List<DocumentationBean> selectedDocumentation = new ArrayList<DocumentationBean>(); 
		if (question.isAnswered()) {
			String answer = question.getAnswerValue();
			if (!answer.equals("Not Applicable") && !answer.equals("Not In Place")) {
				List<QuestionBean> subquestions = question.getSelectedChoice().getSubquestions();
				if (null != subquestions) {
					for (QuestionBean subquestion: subquestions) {
						if (subquestion.getQuestionId().equals(SecuritySurveyXmlParser.SUPPORTING_DOCUMENTATION_QUESTION_ID)) {
							for (QuestionBean documentationChoice: subquestion.getChoices()) {
			        			if ("true".equals(documentationChoice.getAnswerValue())) {
			        				DocumentationBean documentation = new DocumentationBean();
			        				documentation.setDocumentationName(documentationChoice.getQuestionId());
			        				selectedDocumentation.add(documentation);
			        			}
							}
						}
					}
				}
			}
			taskModel.updateDocumentationTask(vendorId, selectedDocumentation);
		}
	}

    @Override
    public void getQuestion(String vendorId, QuestionBean question, AsyncCallback<QuestionBean> asyncCallback) {
        List<QuestionBean> questionList = surveyQuestions.get(vendorId);
        QuestionBean selectedQuestion = questionList.get(getSelectedQuestionIndex(question.getQuestionId(), questionList));
        
        asyncCallback.onSuccess(copyQuestion(null, selectedQuestion));
    }

    private int getSelectedQuestionIndex(String questionId, List<QuestionBean> questionList) {
        for (int i=0; i < questionList.size(); ++i) {
            if (questionList.get(i).getQuestionId().equals(questionId)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void answerQuestion(String vendorId, QuestionBean response, AsyncCallback<String> asyncCallback) {
        List<QuestionBean> questionList = surveyQuestions.get(vendorId);
        response.setAnsweredBy("test user");
        response.setAnsweredTime(new Date());
        int selectedQuestionIndex = getSelectedQuestionIndex(response.getQuestionId(), questionList);
        questionList.remove(selectedQuestionIndex);
        questionList.add(selectedQuestionIndex, copyQuestion(null, response));
        
        updateRequestedDocumentation(vendorId, response);
        
        asyncCallback.onSuccess("Answer Saved");
    }

    private List<QuestionBean> copyQuestions(Collection<QuestionBean> questions) {
        List<QuestionBean> questionListCopy = new ArrayList<QuestionBean>();
        for (QuestionBean question: questions) {
            questionListCopy.add(copyQuestion(null, question));
        }
        return questionListCopy;
    }

    private QuestionBean copyQuestion(QuestionBean parentQuestion, QuestionBean question) {
        QuestionBean questionBean = new QuestionBean();
        questionBean.setParentQuestion(parentQuestion);
        questionBean.setAnswerValue(question.getAnswerValue());
        questionBean.setAnsweredBy(question.getAnsweredBy());
        questionBean.setAnsweredTime(question.getAnsweredTime());
        questionBean.setQuestionId(question.getQuestionId());
        questionBean.setQuestionText(question.getQuestionText());
        questionBean.setQuestionType(question.getQuestionType());
        questionBean.setResponseRequired(question.isResponseRequired());
        questionBean.setValidationType(question.getValidationType());
        if (null != question.getChoices()) {
            ArrayList<QuestionBean> answerChoices = new ArrayList<QuestionBean>();
            for (QuestionBean answerChoice: question.getChoices()) {
                answerChoices.add(copyQuestion(questionBean, answerChoice));
            }
            questionBean.setChoices(answerChoices);
        }
        if (null != question.getSubquestions()) {
            ArrayList<QuestionBean> subquestions = new ArrayList<QuestionBean>();
            for (QuestionBean subquestion: question.getSubquestions()) {
                subquestions.add(copyQuestion(questionBean, subquestion));
            }
            questionBean.setSubquestions(subquestions);
        }
        return questionBean;
    }

    private List<QuestionBean> createDefaultQuestionSet(String vendorId) {
    	List<QuestionBean> questionList = parser.parse(SecuritySurveyXml);
        for (QuestionBean question: questionList) {
            updateRequestedDocumentation(vendorId, question);
        }
        return questionList;

    }

    private String SecuritySurveyXml = 
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
                          "<question-text>Does your organization have a process to monitor technical vulnerabilities (based on your organization&apos;s system / information asset list) by regularly reviewing warnings and information published by system vendors and credible technical sources?</question-text>" +
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
 }
