package com.vincentfazio.ui.vendor.view;

import com.vincentfazio.ui.view.HomeDisplay;
import com.vincentfazio.ui.view.ViewDeckEnum;

public interface VendorHomeDisplay extends HomeDisplay {

    public static enum VendorDeckEnum implements ViewDeckEnum {
        VendorDetails,
        ProfileSurvey,
        SecuritySurvey,
        Documentation,
        UserSettings, 
        UserPassword;
    }

}
