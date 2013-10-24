package com.vincentfazio.ui.bean;

import java.util.Date;
import java.util.List;

public class QuestionBean {
    
    public static class QuestionStatus{
        public int numberOfSubquestions = 0;
        public int numberOfAnsweredSubquestions = 0;
    }
    
    private String questionId = null;
    private String questionText = null;
    private QuestionType questionType = null;
    private QuestionBean parentQuestion = null;
    private List<QuestionBean> subquestions = null;
    private List<QuestionBean> choices = null;
    private boolean responseRequired = true;

    private String validationType = null;
    private String answerValue = null;
    private String answeredBy = null;
    private Date answeredTime = null;
    
    public String getQuestionId() {
        return questionId;
    }
    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }
    public String getQuestionText() {
        return questionText;
    }
    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }
    public String getAnsweredBy() {
        return answeredBy;
    }
    public void setAnsweredBy(String answeredBy) {
        this.answeredBy = answeredBy;
    }
    public Date getAnsweredTime() {
        return answeredTime;
    }
    public void setAnsweredTime(Date answeredTime) {
        this.answeredTime = answeredTime;
    }
    public QuestionType getQuestionType() {
        return questionType;
    }
    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }
    public List<QuestionBean> getSubquestions() {
        return subquestions;
    }
    public void setSubquestions(List<QuestionBean> subquestions) {
        this.subquestions = subquestions;
    }
    public List<QuestionBean> getChoices() {
        return choices;
    }
    public void setChoices(List<QuestionBean> choices) {
        this.choices = choices;
    }
    public String getValidationType() {
        return validationType;
    }
    public void setValidationType(String validationType) {
        this.validationType = validationType;
    }  
    public boolean isResponseRequired() {
        return responseRequired;
    }
    public void setResponseRequired(boolean responseRequired) {
        this.responseRequired = responseRequired;
    }
    public String getAnswerValue() {
        if (null != questionType) {
            switch (questionType) {
                case ChooseOne:
                case TrueFalse:
                    QuestionBean selectedChoice = getSelectedChoice(); 
                    if (null == selectedChoice) {
                        return null;
                    }
                    return selectedChoice.questionText;
                default:
                    break;
            }
        }
        return answerValue;
    }
    
    public void setAnswerValue(String answerValue) {
        this.answerValue = answerValue;
    }

    public boolean isAnswerCompleted() {
        boolean hasAnswer = false;
        if (null == questionType) {
            hasAnswer = isAnswered();            
        } else {
            switch (questionType) {
                case QuestionGroup:
                    hasAnswer = true;
                    break;
                case TrueFalse:
                case FillInTheBlank:
                case ReadOnly:
                case ChooseOne: 
                case Date:
                case TextBox:
                    hasAnswer = isAnswered();                    
                    break;
                case ChooseMany:
                    hasAnswer = true;
                    for (QuestionBean answerChoice: getChoices()) {
                        hasAnswer = hasAnswer && answerChoice.isAnswerCompleted();
                    }
                    break;
            }
        }
        
        
        boolean subquestionsComplete = true;
        if (hasSubquestions()) {
            List<QuestionBean> subquestionList = subquestions;
            
            if (null != questionType) {
                switch (questionType) {
                    case ChooseOne:
                    case TrueFalse:
                        QuestionBean selectAnswerChoice = getSelectedChoice();
                        subquestionList = selectAnswerChoice.subquestions;
                        break;
                    default:
                        break;
                    }
            }
            
            for (QuestionBean subquestion: subquestionList) {
                subquestionsComplete = subquestionsComplete && subquestion.isAnswerCompleted();
            }
        }
        
        return hasAnswer && subquestionsComplete;
    }
    
    public boolean hasSubquestions() {
        if (null != questionType) {
            switch (questionType) {
                case ChooseOne:
                case TrueFalse:
                    QuestionBean selectAnswerChoice = getSelectedChoice();
                    if (null == selectAnswerChoice) {
                        return false;
                    } else {
                        return selectAnswerChoice.hasSubquestions();
                    }
                default:
                    break;
            }
        }
        return (null != subquestions) && (subquestions.size() > 0);
    }
    
    public boolean isAnswered() {
        if (!responseRequired) {
            return true;
        }
        if (null != questionType) {
            switch (questionType) {
                case ChooseOne:
                case TrueFalse:
                    return null != getSelectedChoice();
                default:
                    break;
            }
        }
        return (null != answerValue) && !answerValue.trim().isEmpty();
    }
    
    public QuestionStatus getQuestionStatus() {
        if (responseRequired) {
            QuestionStatus questionStatus = getSubquestionStatus();
            if ((questionType != QuestionType.ChooseMany) && 
                (questionType != QuestionType.QuestionGroup)) {
                questionStatus.numberOfSubquestions++;
                if (isAnswered()) {
                    questionStatus.numberOfAnsweredSubquestions++;
                }
            }
            return questionStatus;
        } else {
            return new QuestionStatus();
        }
    }

    public QuestionStatus getSubquestionStatus() {
        QuestionStatus status = new QuestionStatus();
        if (null != questionType) {
            switch (questionType) {
                case ChooseMany:
                    for (QuestionBean answerChoice: choices) {
                        if (answerChoice.isAnswered()) {
                            QuestionStatus answerChoiceStatus = answerChoice.getQuestionStatus();
                            status.numberOfAnsweredSubquestions += answerChoiceStatus.numberOfAnsweredSubquestions;
                            status.numberOfSubquestions += answerChoiceStatus.numberOfSubquestions;
                        } else {
                            status.numberOfSubquestions++;                            
                        }
                    }
                    break;
                case ChooseOne:
                case TrueFalse:
                    QuestionBean selectedChoice = getSelectedChoice();
                    if (null != selectedChoice) {
                        QuestionStatus answerChoiceStatus = selectedChoice.getSubquestionStatus();
                        status.numberOfAnsweredSubquestions += answerChoiceStatus.numberOfAnsweredSubquestions;
                        status.numberOfSubquestions += answerChoiceStatus.numberOfSubquestions;
                    }
                    break;
                default:
                    break;
            }
        }

        if (null != subquestions) {
            for (QuestionBean subquestion: subquestions) {
                QuestionStatus subquestionStatus = subquestion.getQuestionStatus();
                status.numberOfAnsweredSubquestions += subquestionStatus.numberOfAnsweredSubquestions;
                status.numberOfSubquestions += subquestionStatus.numberOfSubquestions;
            }
        }
        return status;
    }

    public QuestionBean getSelectedChoice() {
        if (null != questionType) {
            switch (questionType) {
                case ChooseOne:
                    for (QuestionBean answerChoice: choices) {
                        if (answerChoice.isAnswered() && answerChoice.getAnswerValue().equals("true")) {
                            return answerChoice;
                        }
                    }
                    return null;
                case TrueFalse:
                    for (QuestionBean answerChoice: choices) {
                        String answeredBy = answerChoice.getAnsweredBy();
                        if ((null != answeredBy) && (answeredBy.trim().length() > 0)) {
                            return answerChoice;
                        }
                    }
                    return null;
                default:
                    break;
            }
        }
        return null;        
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((questionId == null) ? 0 : questionId.hashCode());
        return result;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        QuestionBean other = (QuestionBean) obj;
        if (questionId == null) {
            if (other.questionId != null)
                return false;
        } else if (!questionId.equals(other.questionId))
            return false;
        return true;
    }
    
    public QuestionBean getParentQuestion() {
        return parentQuestion;
    }
    
    public void setParentQuestion(QuestionBean parentQuestion) {
        this.parentQuestion = parentQuestion;    
    }
    
}
