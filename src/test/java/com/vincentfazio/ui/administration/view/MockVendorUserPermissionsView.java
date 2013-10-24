package com.vincentfazio.ui.administration.view;

import java.util.ArrayList;
import java.util.List;

import com.vincentfazio.ui.administration.view.VendorUserPermissionsDisplay;
import com.vincentfazio.ui.bean.VendorUserPermissionBean;

public class MockVendorUserPermissionsView implements VendorUserPermissionsDisplay {

    private ArrayList<String> userList = null;
    private String vendorId = null;
    private String userRole = null;
    private boolean unsavedChanges;
    
    
    @Override
    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
    @Override
    public String getUserRole() {
        return userRole;
    }
    @Override
    public void setVendorId(String vendorId) {
        this.vendorId =  vendorId;
    }
    @Override
    public String getVendorId() {
        return vendorId;
    }
    @Override
    public void setVendorUserPermissionList(List<VendorUserPermissionBean> vendorUserPermissions) {
        throw new UnsupportedOperationException();
    }
    @Override
    public List<VendorUserPermissionBean> getVendorUserPermissionList() {
        throw new UnsupportedOperationException();
    }
    @Override
    public boolean hasUnsavedChanges() {
        return unsavedChanges;
    }
    @Override
    public void setHasUnsavedChanges(boolean hasUnsavedChanges) {
        this.unsavedChanges = hasUnsavedChanges;
    }
    @Override
    public void setUserList(ArrayList<String> users) {
        this.userList = users;
    }
    @Override
    public ArrayList<String> getUserList() {
        return userList;
    }
    
    

}
