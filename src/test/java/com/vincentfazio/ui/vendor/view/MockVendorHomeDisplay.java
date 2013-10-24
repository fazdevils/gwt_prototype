package com.vincentfazio.ui.vendor.view;

import com.vincentfazio.ui.vendor.view.VendorHomeDisplay;
import com.vincentfazio.ui.view.ViewDeckEnum;


public class MockVendorHomeDisplay implements VendorHomeDisplay {

    private VendorDeckEnum selectedView;
    
    
    @Override
    public void showView(ViewDeckEnum view) {
        VendorDeckEnum vendorView = (VendorDeckEnum)view;
        switch (vendorView) {
            case VendorDetails:
                selectedView = VendorDeckEnum.VendorDetails;
                break;
            case SecuritySurvey:
                selectedView = VendorDeckEnum.SecuritySurvey;
                break;       
            case ProfileSurvey:
                selectedView = VendorDeckEnum.ProfileSurvey;
                break;
            default:
                break;       
        }
    }


    public VendorDeckEnum getSelectedView() {
        return selectedView;
    }

    
    
}
