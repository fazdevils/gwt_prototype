package com.vincentfazio.ui.bean;

import java.util.List;

public class UserPermissionsBean {
    
    private String userId;
    private String role;
    private List<String> comapnyPermissionList;
    
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
    public List<String> getCompanyPermissionsList() {
        return comapnyPermissionList;
    }
    public void setCompanyPermissionsList(List<String> companyPermissionList) {
        this.comapnyPermissionList = companyPermissionList;
    }
    
}
