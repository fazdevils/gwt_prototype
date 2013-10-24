package com.vincentfazio.ui.model.parser.xml;

import java.util.List;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.XMLParser;
import com.vincentfazio.ui.bean.QuestionBean;
import com.vincentfazio.ui.bean.QuestionType;

public class ProfileSurveyQuestionResponseXmlParser extends AbstractProfileSurveyXmlParser<QuestionBean> {
    
    public ProfileSurveyQuestionResponseXmlParser() {
        super();
    }


    public QuestionBean parse(String responseXml) {
        Document messageDom = XMLParser.parse(responseXml);
        
        Element questionElement = messageDom.getDocumentElement();
        
        QuestionBean questionBean = parseQuestion(null, questionElement);
        return questionBean;
    }


    public String createXml(QuestionBean questionResponse) {
        StringBuffer xmlBuffer = new StringBuffer("<profile-answers>");
        addAnswerXml(xmlBuffer, questionResponse);
        xmlBuffer.append("</profile-answers>");
        return xmlBuffer.toString();
    }

    
    private void addAnswerXml(StringBuffer xmlBuffer, QuestionBean questionResponse) {
        QuestionType questionType = questionResponse.getQuestionType();
		if (null != questionType) {
			switch (questionType) {
                case ChooseMany:
                    for (QuestionBean answerChoice: questionResponse.getChoices()) {
                    	appendAnswerXml(xmlBuffer, answerChoice);
                    	if ("true".equalsIgnoreCase(answerChoice.getAnswerValue())) {
                        	addSubquestionAnswers(xmlBuffer, answerChoice.getSubquestions());                    		
                    	}
                    }
                    break;
                case ChooseOne:
                    QuestionBean selectedChoice = questionResponse.getSelectedChoice();
                    if (null != selectedChoice) {
                    	QuestionBean temp = new QuestionBean();
                    	temp.setQuestionId(questionResponse.getQuestionId());
                    	temp.setAnswerValue(selectedChoice.getQuestionId());
                    	appendAnswerXml(xmlBuffer, temp);
                   		addSubquestionAnswers(xmlBuffer, selectedChoice.getSubquestions());
                    }
                    break;
                case FillInTheBlank:
                case Date:
                case TextBox:
                    appendAnswerXml(xmlBuffer, questionResponse);
                    break;
                case TrueFalse:
                    QuestionBean selectedTfChoice = questionResponse.getSelectedChoice();
                    if (null != selectedTfChoice) {
                    	appendAnswerXml(xmlBuffer, selectedTfChoice);
                   		addSubquestionAnswers(xmlBuffer, selectedTfChoice.getSubquestions());
                    }
                    break;                    
                default:
                    break;
            }
        }

		List<QuestionBean> subquestions = questionResponse.getSubquestions();
        addSubquestionAnswers(xmlBuffer, subquestions);

    }


	protected void addSubquestionAnswers(StringBuffer xmlBuffer,
			List<QuestionBean> subquestions) {
		if (null != subquestions) {
            for (QuestionBean subquestion: subquestions) {
            	addAnswerXml(xmlBuffer, subquestion);
            }
        }
	}


	protected void appendAnswerXml(StringBuffer xmlBuffer, QuestionBean answer) {
		if (null != answer) {
		    String answerValue = answer.getAnswerValue();
			if ((null != answerValue) && !answerValue.trim().isEmpty()) {
			    xmlBuffer.append("<profile-answer>");
			    xmlBuffer.append("<id>" + answer.getQuestionId() + "</id>");
				xmlBuffer.append("<answer-value>" + answerValue + "</answer-value>");
			    xmlBuffer.append("</profile-answer>");
			}
		}
	}
    

}
