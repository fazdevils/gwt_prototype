package com.vincentfazio.ui.administration.view;

import java.util.ArrayList;
import java.util.List;

import com.vincentfazio.ui.bean.VendorUserPermissionBean;
import com.vincentfazio.ui.view.Display;


public interface VendorUserPermissionsDisplay extends Display {

    public void setUserRole(String userRole);
    public String getUserRole();

    public void setVendorId(String vendorId);
    public String getVendorId();

    void setVendorUserPermissionList(List<VendorUserPermissionBean> vendorUserPermissions);
    List<VendorUserPermissionBean> getVendorUserPermissionList();

    boolean hasUnsavedChanges();
    void setHasUnsavedChanges(boolean hasUnsavedChanges);

    void setUserList(ArrayList<String> users);
    ArrayList<String> getUserList();
    
}