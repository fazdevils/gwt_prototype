package com.vincentfazio.ui.survey.view.component;

import com.google.gwt.user.client.ui.RadioButton;
import com.vincentfazio.ui.bean.QuestionBean;

public class ChooseOneRadioButtonComponent extends RadioButton {
    
    private final QuestionBean question;
    private final QuestionBean answerChoice;
    
    
    public ChooseOneRadioButtonComponent(QuestionBean question, QuestionBean answerChoice) {
        super(question.getQuestionId(), answerChoice.getQuestionText());
        this.question = question;
        this.answerChoice = answerChoice;
        
        String answerValue = answerChoice.getAnswerValue();
        if (null != answerValue) {
            setValue(answerValue.equals("true"));
        }
    }


    public QuestionBean getQuestion() {
        return question;
    }


    public QuestionBean getAnswerChoice() {
        return answerChoice;
    }
    
    
}
