package com.vincentfazio.ui.administration.view;

import com.vincentfazio.ui.bean.UserDetailsBean;
import com.vincentfazio.ui.view.Display;


public interface UserDetailsDisplay extends Display {

    boolean isValid();
    
    UserDetailsBean getUserDetails();
    void setUserDetails(UserDetailsBean user);

    boolean hasUnsavedChanges();
    void setHasUnsavedChanges(boolean hasUnsavedChanges);

}