package com.vincentfazio.ui.administration.view;

import com.vincentfazio.ui.bean.CompanyDetailsBean;
import com.vincentfazio.ui.view.Display;


public interface CompanyDetailsDisplay extends Display {

    boolean isValid();

    CompanyDetailsBean getCompanyDetails();    
    void setCompanyDetails(CompanyDetailsBean company);

    boolean hasUnsavedChanges();
    void setHasUnsavedChanges(boolean hasUnsavedChanges);
}