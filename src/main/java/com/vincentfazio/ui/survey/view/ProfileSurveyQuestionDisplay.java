package com.vincentfazio.ui.survey.view;

import com.vincentfazio.ui.bean.QuestionBean;
import com.vincentfazio.ui.view.Display;

public interface ProfileSurveyQuestionDisplay extends Display {

    void setQuestion(String companyId, QuestionBean question);

    boolean isValid();

    boolean hasUnsavedChanges();

    QuestionBean getQuestion();

}
