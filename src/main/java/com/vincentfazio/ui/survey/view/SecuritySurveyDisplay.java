package com.vincentfazio.ui.survey.view;

import java.util.List;
import java.util.SortedSet;

import com.vincentfazio.ui.bean.QuestionBean;
import com.vincentfazio.ui.bean.TaskBean;
import com.vincentfazio.ui.view.Display;

public interface SecuritySurveyDisplay extends Display {

    void setQuestionList(List<QuestionBean> questionList);

    void setCompanySearchOptions(List<String> companyList);
    
    String getCompanyId();
    void setCompanyId(String companyId);
    
    void setActiveTasks(SortedSet<TaskBean> activeTasks);
    void setQuestion(QuestionBean question);

}
