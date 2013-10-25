package com.vincentfazio.ui.administration.view;

import java.util.ArrayList;
import java.util.List;

import com.vincentfazio.ui.bean.CompanyUserPermissionBean;
import com.vincentfazio.ui.view.Display;


public interface CompanyUserPermissionsDisplay extends Display {

    public void setUserRole(String userRole);
    public String getUserRole();

    public void setCompanyId(String companyId);
    public String getCompanyId();

    void setCompanyUserPermissionList(List<CompanyUserPermissionBean> companyUserPermissions);
    List<CompanyUserPermissionBean> getCompanyUserPermissionList();

    boolean hasUnsavedChanges();
    void setHasUnsavedChanges(boolean hasUnsavedChanges);

    void setUserList(ArrayList<String> users);
    ArrayList<String> getUserList();
    
}