package com.vincentfazio.ui.administration.view;

import java.util.ArrayList;
import java.util.List;

import com.vincentfazio.ui.bean.VendorPermissionBean;
import com.vincentfazio.ui.view.Display;


public interface UserPermissionsDisplay extends Display {

    public void setUserRole(String userId);

    public String getUserRole();

    public void setUserId(String userId);

    public String getUserId();

    void setVendorsPermissionList(List<VendorPermissionBean> vendorPermissions);
    List<VendorPermissionBean> getVendorsPermissionList();

    boolean hasUnsavedChanges();
    void setHasUnsavedChanges(boolean hasUnsavedChanges);

    void setVendorList(ArrayList<String> vendors);
    ArrayList<String> getVendorList();
    
    boolean isVendorListLoaded();

}