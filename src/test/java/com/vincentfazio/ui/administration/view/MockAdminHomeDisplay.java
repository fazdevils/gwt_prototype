package com.vincentfazio.ui.administration.view;

import com.vincentfazio.ui.administration.view.AdminHomeDisplay;
import com.vincentfazio.ui.view.ViewDeckEnum;


public class MockAdminHomeDisplay implements AdminHomeDisplay {

    private AdminDeckEnum selectedView;
    
    
    @Override
    public void showView(ViewDeckEnum view) {
        AdminDeckEnum adminView = (AdminDeckEnum)view;
        switch (adminView) {
            case MainNavigation:
                selectedView = AdminDeckEnum.MainNavigation;
                break;
            default:
                break;
        }
    }


    public ViewDeckEnum getSelectedView() {
        return selectedView;
    }

    
    
}
