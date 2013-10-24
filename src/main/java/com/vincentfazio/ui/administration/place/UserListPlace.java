package com.vincentfazio.ui.administration.place;

import com.google.gwt.place.shared.Place;
import com.vincentfazio.ui.administration.global.GwtAdminGlobals;
import com.vincentfazio.ui.administration.view.AdminHomeDisplay.AdminDeckEnum;
import com.vincentfazio.ui.view.HomeDisplay;

public class UserListPlace extends Place {

    public UserListPlace() {
        super();
        HomeDisplay homeDisplay = (HomeDisplay) GwtAdminGlobals.getInstance().getDisplay(HomeDisplay.class);
        homeDisplay.showView(AdminDeckEnum.MainNavigation);        
    }
    
}
