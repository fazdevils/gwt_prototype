package com.vincentfazio.ui.company.view;

import com.vincentfazio.ui.survey.view.CompanyHomeDisplay;
import com.vincentfazio.ui.view.ViewDeckEnum;


public class MockCompanyHomeDisplay implements CompanyHomeDisplay {

    private CompanyDeckEnum selectedView;
    
    
    @Override
    public void showView(ViewDeckEnum view) {
        CompanyDeckEnum companyView = (CompanyDeckEnum)view;
        switch (companyView) {
            case CompanyDetails:
                selectedView = CompanyDeckEnum.CompanyDetails;
                break;
            case SecuritySurvey:
                selectedView = CompanyDeckEnum.SecuritySurvey;
                break;       
            case ProfileSurvey:
                selectedView = CompanyDeckEnum.ProfileSurvey;
                break;
            default:
                break;       
        }
    }


    public CompanyDeckEnum getSelectedView() {
        return selectedView;
    }

    
    
}
