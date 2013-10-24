package com.vincentfazio.ui.view;

import com.vincentfazio.ui.bean.PasswordBean;

public interface MyPasswordDisplay extends Display {

    boolean isValid();
    
    PasswordBean getPasswordUpdateBean();

    boolean hasUnsavedChanges();

    public abstract void resetDisplay();

}
