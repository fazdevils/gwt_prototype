package com.vincentfazio.ui.company.view;

import java.util.List;
import java.util.Set;
import java.util.SortedSet;

import com.vincentfazio.ui.bean.TaskBean;
import com.vincentfazio.ui.bean.CompanyDetailsBean;
import com.vincentfazio.ui.survey.view.CompanyDetailsDisplay;

public class MockCompanyDetailsDisplay implements CompanyDetailsDisplay {

    private boolean valid = true;
    private boolean unsavedChanges = false;
    private CompanyDetailsBean companyDetailsBean;
    private SortedSet<TaskBean> activeTasks;
    private SortedSet<TaskBean> completedTasks;
    
    @Override
    public boolean isValid() {
        return valid;
    }

    @Override
    public CompanyDetailsBean getCompanyDetails() {
        return companyDetailsBean;
    }

    @Override
    public void setCompanyDetails(CompanyDetailsBean company) {
        companyDetailsBean = company;
    }

    @Override
    public boolean hasUnsavedChanges() {
        return unsavedChanges;
    }

    @Override
    public void setHasUnsavedChanges(boolean hasUnsavedChanges) {
        this.unsavedChanges = hasUnsavedChanges;
    }

    @Override
    public void setCompanySearchOptions(List<String> companyList) {
        //this.companySearchOptions = companyList;
    }

    @Override
    public void setActiveTasks(SortedSet<TaskBean> activeTasks) {
        this.activeTasks = activeTasks;
    }

    public SortedSet<TaskBean> getActiveTasks() {
        return activeTasks;
    }

    @Override
    public void highlightCompanyDetailsPane() {
        throw new UnsupportedOperationException("Need to test this!!!");        
    }

    @Override
    public void setCompletedTasks(SortedSet<TaskBean> completedTasks) {
        this.completedTasks = completedTasks;
    }

    public Set<TaskBean> getCompletedTasks() {
        return completedTasks;
    }

}
