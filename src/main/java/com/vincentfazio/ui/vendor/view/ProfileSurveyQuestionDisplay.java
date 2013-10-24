package com.vincentfazio.ui.vendor.view;

import com.vincentfazio.ui.bean.QuestionBean;
import com.vincentfazio.ui.view.Display;

public interface ProfileSurveyQuestionDisplay extends Display {

    void setQuestion(String vendorId, QuestionBean question);

    boolean isValid();

    boolean hasUnsavedChanges();

    QuestionBean getQuestion();

}
