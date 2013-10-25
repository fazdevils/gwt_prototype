package com.vincentfazio.ui.survey.view;

import com.vincentfazio.ui.view.HomeDisplay;
import com.vincentfazio.ui.view.ViewDeckEnum;

public interface CompanyHomeDisplay extends HomeDisplay {

    public static enum CompanyDeckEnum implements ViewDeckEnum {
        CompanyDetails,
        ProfileSurvey,
        SecuritySurvey,
        Documentation,
        UserSettings, 
        UserPassword;
    }

}
