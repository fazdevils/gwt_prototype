package com.vincentfazio.ui.model.parser.xml;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.vincentfazio.ui.bean.QuestionBean;
import com.vincentfazio.ui.bean.QuestionType;

public abstract class AbstractProfileSurveyXmlParser<T> extends XmlParser<T> {

    private DateTimeFormat dateFormat = DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss.SSS");

    public AbstractProfileSurveyXmlParser() {
        super();
    }

    protected QuestionBean parseQuestion(QuestionBean parentQuestion, Element questionElement) {
        QuestionBean questionBean = new QuestionBean();
        questionBean.setParentQuestion(parentQuestion);
        questionBean.setQuestionId(extractField(questionElement, "id"));
        questionBean.setQuestionText(extractField(questionElement, "label"));
        String questionType = questionElement.getNodeName();
        if (questionType.equalsIgnoreCase("choose-many-question")) {
            parseChooseManyQuestion(questionBean, questionElement);
        } else if  (questionType.equalsIgnoreCase("choose-one-question")) {
            parseChooseOneQuestion(questionBean, questionElement);
        } else if  (questionType.equalsIgnoreCase("fill-in-the-blank-question")) {
            parseFillInTheBlankQuestion(questionBean, questionElement);
        } else if  (questionType.equalsIgnoreCase("question-group")) {
            parseQuestionGroup(questionBean, questionElement);
        } else if  (questionType.equalsIgnoreCase("read-only-question")) {
            parseReadOnlyQuestion(questionBean, questionElement);
        } else if  (questionType.equalsIgnoreCase("true-false-question")) {
            parseTrueFalseQuestion(questionBean, questionElement);
        }
        return questionBean;
    }

    private void parseTrueFalseQuestion(QuestionBean questionBean, Element questionElement) {
        questionBean.setQuestionType(QuestionType.TrueFalse);
        
        List<QuestionBean> tfQuestionChoices = getAnswerChoices(questionBean, questionElement);
        questionBean.setChoices(tfQuestionChoices);
    }

    private void parseReadOnlyQuestion(QuestionBean questionBean, Element questionElement) {
        questionBean.setQuestionType(QuestionType.ReadOnly);
        
        Element answerList = (Element) questionElement.getElementsByTagName("answers").item(0);
        if (null != answerList) {
            Element answerElement = (Element) answerList.getElementsByTagName("profile-answer").item(0);
            questionBean.setAnsweredBy(extractField(answerElement, "answered-by"));
            questionBean.setAnsweredTime(parseDate(extractField(answerElement, "answer-time")));
            questionBean.setAnswerValue(extractField(answerElement, "answer-value"));
            questionBean.setSubquestions(parseSubquestions(questionBean, answerElement));
        }
        questionBean.setValidationType(extractField(questionElement, "validation-type"));
    }

    private void parseQuestionGroup(QuestionBean questionBean, Element questionElement) {
        questionBean.setQuestionType(QuestionType.QuestionGroup);
        
        Element subquestionListElement = (Element) questionElement.getElementsByTagName("questions").item(0);
        if (null != subquestionListElement) {
            NodeList subquestionList = subquestionListElement.getChildNodes();
            List<QuestionBean> subquestions = new ArrayList<QuestionBean>();
            for (int j=0; j < subquestionList.getLength(); ++j) {
                Node subquestion = subquestionList.item(j);
                if (Node.ELEMENT_NODE == subquestion.getNodeType()) {                
                    Element subquestionElement = (Element) subquestion;
                    subquestions.add(parseQuestion(questionBean, subquestionElement));
                }
            }
            questionBean.setSubquestions(subquestions);
        }
    }

    private void parseFillInTheBlankQuestion(QuestionBean questionBean, Element questionElement) {
        questionBean.setQuestionType(QuestionType.FillInTheBlank);
        
        Element answerList = (Element) questionElement.getElementsByTagName("answers").item(0);
        if (null != answerList) {
            Element answerElement = (Element) answerList.getElementsByTagName("profile-answer").item(0);
            questionBean.setAnsweredBy(extractField(answerElement, "answered-by"));
            questionBean.setAnsweredTime(parseDate(extractField(answerElement, "answer-time")));
            questionBean.setAnswerValue(extractField(answerElement, "answer-value"));
            questionBean.setSubquestions(parseSubquestions(questionBean, answerElement));
        }
        questionBean.setValidationType(extractField(questionElement, "validation-type"));
    }

    private void parseChooseOneQuestion(QuestionBean questionBean, Element questionElement) {
        questionBean.setQuestionType(QuestionType.ChooseOne);
        
        List<QuestionBean> chooseOneQuestionChoices = getAnswerChoices(questionBean, questionElement);
        questionBean.setChoices(chooseOneQuestionChoices);
    }

    private void parseChooseManyQuestion(QuestionBean questionBean, Element questionElement) {
        
        Element answerGrouping = (Element) questionElement.getElementsByTagName("answer-groupings").item(0);
        NodeList answerGroupingNodes = answerGrouping.getElementsByTagName("answer-grouping");
        if (answerGroupingNodes.getLength() > 1) {
            questionBean.setQuestionType(QuestionType.QuestionGroup);
            ArrayList<QuestionBean> answerGroups = new ArrayList<QuestionBean>();
            for (int i=0; i < answerGroupingNodes.getLength(); ++i) {
                Element answerGroupElement = (Element) answerGroupingNodes.item(i);
                List<QuestionBean> chooseManyQuestionChoices = getAnswerChoices(questionBean, answerGroupElement);
                QuestionBean answerGroup = new QuestionBean();
                answerGroup.setParentQuestion(questionBean);
                answerGroup.setQuestionType(QuestionType.ChooseMany);
                answerGroup.setQuestionId(extractField(answerGroupElement, "label"));
                answerGroup.setQuestionText(answerGroup.getQuestionId());
                answerGroup.setChoices(chooseManyQuestionChoices);
                
                answerGroups.add(answerGroup);
            }
            questionBean.setSubquestions(answerGroups);
        } else {
            questionBean.setQuestionType(QuestionType.ChooseMany);
            Element answerGroupElement = (Element) answerGroupingNodes.item(0);
            List<QuestionBean> chooseManyQuestionChoices = getAnswerChoices(questionBean, answerGroupElement);
            questionBean.setChoices(chooseManyQuestionChoices);
        }
        
    }

    private List<QuestionBean> getAnswerChoices(QuestionBean parentQuestion, Element answerGroupElement) {
        Element answerList = (Element) answerGroupElement.getElementsByTagName("answers").item(0);
        NodeList answerChoices = answerList.getElementsByTagName("profile-answer");
        List<QuestionBean> chooseManyQuestionChoices = new ArrayList<QuestionBean>();
        for (int i=0; i < answerChoices.getLength(); ++i) {
            Element answerElement = (Element) answerChoices.item(i);
        	if (answerList.equals(answerElement.getParentNode())) {
	            QuestionBean answerChoice = new QuestionBean();
	            answerChoice.setParentQuestion(parentQuestion);
	            answerChoice.setQuestionId(extractField(answerElement, "id"));
	            answerChoice.setQuestionText(extractField(answerElement, "label"));
	            answerChoice.setAnsweredBy(extractField(answerElement, "answered-by"));
	            answerChoice.setAnsweredTime(parseDate(extractField(answerElement, "answer-time")));
	            answerChoice.setAnswerValue(extractField(answerElement, "answer-value"));            
	            answerChoice.setSubquestions(parseSubquestions(answerChoice, answerElement));
	            
	            chooseManyQuestionChoices.add(answerChoice);
        	}
        }
        return chooseManyQuestionChoices;
    }

    private List<QuestionBean> parseSubquestions(QuestionBean parentQuestion, Element questionElement) {
        Element subquestionListElement = (Element) questionElement.getElementsByTagName("subquestions").item(0);
        if (null == subquestionListElement) {
            return null;
        } else {
            NodeList subquestionList = subquestionListElement.getChildNodes();
            List<QuestionBean> subquestions = new ArrayList<QuestionBean>();
            for (int j=0; j < subquestionList.getLength(); ++j) {
                Node subquestion = subquestionList.item(j);
                if (Node.ELEMENT_NODE == subquestion.getNodeType()) {                
                    Element subquestionElement = (Element) subquestion;
                    subquestions.add(parseQuestion(parentQuestion, subquestionElement));
                }
            }
            return subquestions;
        }
    }

    Date parseDate(String answerTime) {
        if (null != answerTime) {
            return dateFormat.parse(answerTime);
        }
        return null;
    }

}