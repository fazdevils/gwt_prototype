package com.vincentfazio.ui.administration.view;

import java.util.List;

import com.vincentfazio.ui.view.Display;

public interface VendorListDisplay extends Display {

    public static enum VendorDeckEnum {
        VendorDetails,
        NoVendor,
        VendorUserPermissions;
    }

    boolean isVendorListLoaded();
    
    void selectVendor(Boolean refreshVendorDetail);

    void selectVendor(String selectedVendorId, Boolean refreshVendorDetail);

    void setVendorList(List<String> vendorList);
    
    void setPresenter(Presenter presenter);
    
    void showView(VendorDeckEnum vendorView);

    public interface Presenter {
        void switchVendor(String vendorId, VendorDeckEnum currentPage);
    }
    
}