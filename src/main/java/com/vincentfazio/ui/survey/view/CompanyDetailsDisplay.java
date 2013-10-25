package com.vincentfazio.ui.survey.view;

import java.util.List;
import java.util.SortedSet;

import com.vincentfazio.ui.bean.TaskBean;
import com.vincentfazio.ui.bean.CompanyDetailsBean;
import com.vincentfazio.ui.view.Display;


public interface CompanyDetailsDisplay extends Display {

    boolean isValid();

    CompanyDetailsBean getCompanyDetails();    
    void setCompanyDetails(CompanyDetailsBean company);

    boolean hasUnsavedChanges();
    void setHasUnsavedChanges(boolean hasUnsavedChanges);

    void setCompanySearchOptions(List<String> companyList);
    
    void setActiveTasks(SortedSet<TaskBean> activeTasks);

    void highlightCompanyDetailsPane();

    void setCompletedTasks(SortedSet<TaskBean> completedTasks);

}