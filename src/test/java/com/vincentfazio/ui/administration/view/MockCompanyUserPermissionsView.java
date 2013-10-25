package com.vincentfazio.ui.administration.view;

import java.util.ArrayList;
import java.util.List;

import com.vincentfazio.ui.administration.view.CompanyUserPermissionsDisplay;
import com.vincentfazio.ui.bean.CompanyUserPermissionBean;

public class MockCompanyUserPermissionsView implements CompanyUserPermissionsDisplay {

    private ArrayList<String> userList = null;
    private String companyId = null;
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
    public void setCompanyId(String companyId) {
        this.companyId =  companyId;
    }
    @Override
    public String getCompanyId() {
        return companyId;
    }
    @Override
    public void setCompanyUserPermissionList(List<CompanyUserPermissionBean> companyUserPermissions) {
        throw new UnsupportedOperationException();
    }
    @Override
    public List<CompanyUserPermissionBean> getCompanyUserPermissionList() {
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
