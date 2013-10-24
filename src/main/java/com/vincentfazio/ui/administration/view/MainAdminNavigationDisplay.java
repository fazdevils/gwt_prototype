package com.vincentfazio.ui.administration.view;

import com.vincentfazio.ui.view.Display;

public interface MainAdminNavigationDisplay extends Display {
    
    public static enum MainAdminNavigationEnum {
        Companies,
        Users;
    }

    void showView(MainAdminNavigationEnum navigationView);
}
