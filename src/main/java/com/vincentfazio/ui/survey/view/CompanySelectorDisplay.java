package com.vincentfazio.ui.survey.view;

import java.util.List;

public interface CompanySelectorDisplay {

    String getCompanyId();

    void setCompanyId(String companyId);

    void setCompanySearchOptions(List<String> companyList);

}