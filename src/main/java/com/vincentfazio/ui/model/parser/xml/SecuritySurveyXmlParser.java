package com.vincentfazio.ui.model.parser.xml;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.TreeSet;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;
import com.vincentfazio.ui.bean.QuestionBean;
import com.vincentfazio.ui.bean.QuestionType;

public class SecuritySurveyXmlParser extends XmlParser<List<QuestionBean>> {
    
    private final SecuritySurveyAnswerConfiguration[] answerChoiceConfiguration;
	private final DateTimeFormat dateFormat; 
    
    public static final String SUPPORTING_DOCUMENTATION_QUESTION_ID = "Supporting Documentation";
    public static final String COMMENTS_QUESTION_ID = "Comments";
    public static final String DATE_QUESTION_ID = "Date";

    
    public SecuritySurveyXmlParser() {
        super();
        answerChoiceConfiguration = createSecuritySurveyAnswerChoices();
        dateFormat = DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss.SSS");        
    }


    public List<QuestionBean> parse(String responseXml) {
        ArrayList<QuestionBean> questions = new ArrayList<QuestionBean>();
        
        // parse the XML document into a DOM
        Document messageDom = XMLParser.parse(responseXml);
        
        NodeList questionList = messageDom.getElementsByTagName("survey-question");
        for (int i=0; i < questionList.getLength(); ++i) {
            try {
                questions.add(parseQuestion(null, (Element) questionList.item(i)));
            } catch (Throwable t) {
            }
        }

        return questions;
    }

    
    private QuestionBean parseQuestion(QuestionBean parentQuestion, Element questionElement) {
        QuestionBean questionBean = new QuestionBean();
        questionBean.setParentQuestion(parentQuestion);
        questionBean.setQuestionId(extractField(questionElement, "abbreviation"));
        questionBean.setQuestionText(extractField(questionElement, "question-text"));
        questionBean.setQuestionType(QuestionType.ChooseOne);
        
        List<QuestionBean> answerChoices = getAnswerChoices(questionBean, questionElement);
        questionBean.setChoices(answerChoices);
         
        setAnswerResponses(questionBean, questionElement);
       
        return questionBean;
    }

    
    private List<QuestionBean> getAnswerChoices(QuestionBean parentQuestion, Element answerGroupElement) {
        List<QuestionBean> answerChoices = new ArrayList<QuestionBean>();
                        
        // get the documentation subquestion which is shared between multiple question choices
        QuestionBean documentationQuestion = getDocumentationQuestion(parentQuestion, answerGroupElement);

        // add the answer choices
        for (SecuritySurveyAnswerConfiguration thisChoiceConfig :answerChoiceConfiguration) {
            QuestionBean answerChoice = new QuestionBean();
            answerChoice.setParentQuestion(parentQuestion);
            answerChoice.setQuestionId(thisChoiceConfig.getName());
            answerChoice.setQuestionText(thisChoiceConfig.getDescription());

            // add the subquestions appropriate for each answer choice
            ArrayList<QuestionBean> subquestions = getSubquestions(parentQuestion, thisChoiceConfig, answerGroupElement);
            if (thisChoiceConfig.canHaveDocumentation() && (null != documentationQuestion)) {
                subquestions.add(documentationQuestion);
            }
            answerChoice.setSubquestions(subquestions);
            
            
            answerChoices.add(answerChoice);
        }

        return answerChoices;
    }


	private QuestionBean getDocumentationQuestion(QuestionBean parentQuestion, Element answerGroupElement) {
        Element documentationList = (Element) answerGroupElement.getElementsByTagName("documents").item(0);
        NodeList documentationNodes = documentationList.getElementsByTagName("document");
        
        if (0 == documentationNodes.getLength()) {
            return null;
        }

        QuestionBean documentationQuestion = new QuestionBean();
        documentationQuestion.setParentQuestion(parentQuestion);
        documentationQuestion.setQuestionId(SUPPORTING_DOCUMENTATION_QUESTION_ID);
        documentationQuestion.setQuestionText("Supporting Documentation");
        documentationQuestion.setQuestionType(QuestionType.ChooseMany);
        documentationQuestion.setResponseRequired(false);
                
        List<QuestionBean> documentationChoices = new ArrayList<QuestionBean>();
        for (int i=0; i < documentationNodes.getLength(); ++i) {
            Element documentationElement = (Element) documentationNodes.item(i);
            QuestionBean documentationChoice = new QuestionBean();
            documentationChoice.setParentQuestion(documentationQuestion);
            documentationChoice.setQuestionId(extractField(documentationElement, "name"));
            documentationChoice.setQuestionText(documentationChoice.getQuestionId());
            documentationChoices.add(documentationChoice);
        }
        documentationQuestion.setChoices(documentationChoices);

        return documentationQuestion;
    }


	private ArrayList<QuestionBean> getSubquestions(QuestionBean parentQuestion, SecuritySurveyAnswerConfiguration answerConfiguration, Element answerGroupElement) {
        ArrayList<QuestionBean> subquestions = new ArrayList<QuestionBean>();
        
        QuestionBean commentsQuestion = new QuestionBean();
        commentsQuestion.setParentQuestion(parentQuestion);
        commentsQuestion.setQuestionId(COMMENTS_QUESTION_ID);
        commentsQuestion.setQuestionText(answerConfiguration.getCommentLabel());
        commentsQuestion.setQuestionType(QuestionType.TextBox);
        commentsQuestion.setResponseRequired(false);
        subquestions.add(commentsQuestion);

        String dateLabel = answerConfiguration.getDateLabel();
        if (null != dateLabel) {
            QuestionBean dateQuestion = new QuestionBean();
            dateQuestion.setParentQuestion(parentQuestion);
            dateQuestion.setQuestionId(DATE_QUESTION_ID);
            dateQuestion.setQuestionText(dateLabel);
            dateQuestion.setQuestionType(QuestionType.Date);
            dateQuestion.setResponseRequired(false);
            subquestions.add(dateQuestion);
        }
        
        return subquestions;
    }


    private SecuritySurveyAnswerConfiguration[] createSecuritySurveyAnswerChoices() {
        TreeSet<SecuritySurveyAnswerConfiguration> answerChoices = new TreeSet<SecuritySurveyAnswerConfiguration>(
                new Comparator<SecuritySurveyAnswerConfiguration>() {
                    @Override
                    public int compare(SecuritySurveyAnswerConfiguration o1, SecuritySurveyAnswerConfiguration o2) {
                        return o1.getName().compareTo(o2.getName());
                    }
        });
        
        // hard coded for now. at some point may want to call WS for answer options but all this info is not currently returned by any service 
        answerChoices.add(new SecuritySurveyAnswerConfiguration("Compensating Control", "Describe the compensating control", null, true));
        answerChoices.add(new SecuritySurveyAnswerConfiguration("In Place", "Comments", null, true));
        answerChoices.add(new SecuritySurveyAnswerConfiguration("Not Applicable", "Explain why this control is not applicable", null, false));
        answerChoices.add(new SecuritySurveyAnswerConfiguration("Not In Place", "Comments", null, false));
        answerChoices.add(new SecuritySurveyAnswerConfiguration("Partial", "What part is in place", null, true));
        answerChoices.add(new SecuritySurveyAnswerConfiguration("Remediation Plan", "Comments", "Estimated time of completion on the remediation", true));
        
        return answerChoices.toArray(new SecuritySurveyAnswerConfiguration[0]);
    }
    

    public String createXml(List<QuestionBean> tasList) {
        throw new UnsupportedOperationException();
    }

    
    public String createAnswerXml(QuestionBean questionResponse) {
        StringBuffer xmlBuffer = new StringBuffer("<answer>");
        String answerValue = questionResponse.getAnswerValue();
        if (null != answerValue) {
	        xmlBuffer.append("<selected-answer>");
			xmlBuffer.append(createXmlField("name", answerValue));
	        xmlBuffer.append("</selected-answer>");
        }
        
        QuestionBean selectedChoice = questionResponse.getSelectedChoice();
        for (QuestionBean subquestion: selectedChoice.getSubquestions()) {
	    	if (subquestion.getQuestionId().equals(SecuritySurveyXmlParser.SUPPORTING_DOCUMENTATION_QUESTION_ID)) {
		        xmlBuffer.append("<supporting-documentation>");
	        	for (QuestionBean document: subquestion.getChoices()) {
	    			if ("true".equals(document.getAnswerValue())) {
		    	        xmlBuffer.append("<document>");
		    			xmlBuffer.append(createXmlField("name", document.getQuestionId()));
		    	        xmlBuffer.append("</document>");
	    			}
	        	}
		        xmlBuffer.append("</supporting-documentation>");
	    	} else if (subquestion.getQuestionId().equals(SecuritySurveyXmlParser.COMMENTS_QUESTION_ID)) {
	    		xmlBuffer.append(createXmlField("comment", subquestion.getAnswerValue()));
	    	} else if (subquestion.getQuestionId().equals(SecuritySurveyXmlParser.DATE_QUESTION_ID)) {
	    		xmlBuffer.append(createXmlField("target-date", subquestion.getAnswerValue()));
	    	}
        }        
        xmlBuffer.append("</answer>");
        return xmlBuffer.toString();
	}


    private String getSelectedAnswer(Element answerGroupElement) {
	    Element answerElement = (Element) answerGroupElement.getElementsByTagName("selected-answer").item(0);
	    if (null == answerElement) {
	        return null;
	    }
	    String answer = extractField(answerElement, "name");
	    return answer;
	}


	private List<String> getSelectedDocumentation(Element answerElement) {
	    List<String> selectedDocumentationList = new ArrayList<String>();
	    Element documentationList = (Element) answerElement.getElementsByTagName("supporting-documentation").item(0);
	    if (null != documentationList) {
	        NodeList documentationNodes = documentationList.getElementsByTagName("document");
	        for (int i=0; i < documentationNodes.getLength(); ++i) {
	            Element documentationElement = (Element) documentationNodes.item(i);
	            selectedDocumentationList.add(extractField(documentationElement, "name"));
	        }            
	    }
	    return selectedDocumentationList;
	}


	Date parseDate(String answerTime) {
	    if (null != answerTime) {
	        return dateFormat.parse(answerTime);
	    }
	    return null;
	}


	private void setDocumentationResponses(List<QuestionBean> documentationChoices, List<String> selectedDocumentation) {
	    for (QuestionBean documentationChoice: documentationChoices) {
	        Boolean isDocumentSelected = selectedDocumentation.contains(documentationChoice.getQuestionId());
	        documentationChoice.setAnswerValue(isDocumentSelected.toString());
	    }
	}


	public void setAnswerResponses(QuestionBean questionBean, Element answerGroupElement) {
		String answer = getSelectedAnswer(answerGroupElement);
	    for (QuestionBean answerChoice: questionBean.getChoices()) {
	        if (null != answer) {
	            Boolean selectedAnswer = answer.equals(answerChoice.getQuestionId());                
	            answerChoice.setAnswerValue(selectedAnswer.toString());
	            for (QuestionBean subquestion: answerChoice.getSubquestions()) {
	            	if (selectedAnswer) {
	            		if (subquestion.getQuestionId().equals(SUPPORTING_DOCUMENTATION_QUESTION_ID)) {
	                    	setDocumentationResponses(subquestion.getChoices(), getSelectedDocumentation(answerGroupElement));
	            		} else if (subquestion.getQuestionId().equals(DATE_QUESTION_ID)) {
	            			subquestion.setAnswerValue(extractField(answerGroupElement, "target-date"));	            			
	            		} else if (subquestion.getQuestionId().equals(COMMENTS_QUESTION_ID)) {
	            			subquestion.setAnswerValue(extractField(answerGroupElement, "comment"));
	            		}	
	            	} else {
	            		// selected document list is shared so no need to reset it here.
	            		if (subquestion.getQuestionId().equals(DATE_QUESTION_ID)) {
	            			subquestion.setAnswerValue(null);	            			
	            		} else if (subquestion.getQuestionId().equals(COMMENTS_QUESTION_ID)) {
	            			subquestion.setAnswerValue(null);
	            		}
	            	}
	            	
	            }
	        }
	    }
	    questionBean.setAnsweredBy(extractField(answerGroupElement, "answered-by"));
	    questionBean.setAnsweredTime(parseDate(extractField(answerGroupElement, "answer-time")));
	}


	private static final class SecuritySurveyAnswerConfiguration {
        private final String name;
        private final String description;
        private final String commentLabel;
        private final String dateLabel;
        private final boolean canHaveDocumentation;
        
        public SecuritySurveyAnswerConfiguration(
                String name,
                String commentLabel, 
                String dateLabel,
                boolean canHaveDocumentation) {
            super();
            this.name = name;
            this.description = this.name;
            this.commentLabel = commentLabel;
            this.dateLabel = dateLabel;
            this.canHaveDocumentation = canHaveDocumentation;
        }
        
        public String getName() {
            return name;
        }
        public String getDescription() {
            return description;
        }
        public String getCommentLabel() {
            return commentLabel;
        }
        public String getDateLabel() {
            return dateLabel;
        }
        public boolean canHaveDocumentation() {
            return canHaveDocumentation;
        }
        
    }

}
