package com.vincentfazio.ui.view;

import com.vincentfazio.ui.bean.MyDetailsBean;

public interface MyDetailsDisplay extends Display {

    boolean isValid();
    
    MyDetailsBean getMyDetails();
    void setMyDetails(MyDetailsBean user);

    boolean hasUnsavedChanges();
    void setHasUnsavedChanges(boolean hasUnsavedChanges);

}
