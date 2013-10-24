package com.vincentfazio.ui.administration.view;

import com.vincentfazio.ui.view.Display;

public interface MainAdminNavigationDisplay extends Display {
    
    public static enum MainAdminNavigationEnum {
        Vendors,
        Users;
    }

    void showView(MainAdminNavigationEnum navigationView);
}
