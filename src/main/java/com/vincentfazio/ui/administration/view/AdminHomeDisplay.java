package com.vincentfazio.ui.administration.view;

import com.vincentfazio.ui.view.HomeDisplay;
import com.vincentfazio.ui.view.ViewDeckEnum;


public interface AdminHomeDisplay extends HomeDisplay {

    public static enum AdminDeckEnum implements ViewDeckEnum {
        MainNavigation,
        UserSettings, 
        UserPassword;
    }

}
