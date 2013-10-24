package com.vincentfazio.ui.vendor.view;

import java.util.List;
import java.util.SortedSet;

import com.vincentfazio.ui.bean.QuestionBean;
import com.vincentfazio.ui.bean.TaskBean;
import com.vincentfazio.ui.view.Display;

public interface SecuritySurveyDisplay extends Display {

    void setQuestionList(List<QuestionBean> questionList);

    void setVendorSearchOptions(List<String> vendorList);
    
    String getVendorId();
    void setVendorId(String vendorId);
    
    void setActiveTasks(SortedSet<TaskBean> activeTasks);
    void setQuestion(QuestionBean question);

}
