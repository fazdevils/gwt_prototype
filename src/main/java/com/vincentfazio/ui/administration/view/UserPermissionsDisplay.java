package com.vincentfazio.ui.administration.view;

import java.util.ArrayList;
import java.util.List;

import com.vincentfazio.ui.bean.CompanyPermissionBean;
import com.vincentfazio.ui.view.Display;


public interface UserPermissionsDisplay extends Display {

    public void setUserRole(String userId);

    public String getUserRole();

    public void setUserId(String userId);

    public String getUserId();

    void setCompaniesPermissionList(List<CompanyPermissionBean> companyPermissions);
    List<CompanyPermissionBean> getCompaniesPermissionList();

    boolean hasUnsavedChanges();
    void setHasUnsavedChanges(boolean hasUnsavedChanges);

    void setCompanyList(ArrayList<String> companies);
    ArrayList<String> getCompanyList();
    
    boolean isCompanyListLoaded();

}