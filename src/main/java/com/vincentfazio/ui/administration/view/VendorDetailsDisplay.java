package com.vincentfazio.ui.administration.view;

import com.vincentfazio.ui.bean.VendorDetailsBean;
import com.vincentfazio.ui.view.Display;


public interface VendorDetailsDisplay extends Display {

    boolean isValid();

    VendorDetailsBean getVendorDetails();    
    void setVendorDetails(VendorDetailsBean vendor);

    boolean hasUnsavedChanges();
    void setHasUnsavedChanges(boolean hasUnsavedChanges);
}