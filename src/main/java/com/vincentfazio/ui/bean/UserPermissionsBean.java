package com.vincentfazio.ui.bean;

import java.util.List;

public class UserPermissionsBean {
    
    private String userId;
    private String role;
    private List<String> vendorPermissionList;
    
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public List<String> getVendorPermissionsList() {
        return vendorPermissionList;
    }
    public void setVendorPermissionsList(List<String> vendorPermissionList) {
        this.vendorPermissionList = vendorPermissionList;
    }
    
}
