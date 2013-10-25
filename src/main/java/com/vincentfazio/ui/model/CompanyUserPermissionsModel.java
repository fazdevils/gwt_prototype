package com.vincentfazio.ui.model;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface CompanyUserPermissionsModel extends Model {

    void getCompanyUserPermissions(String companyId, String role, AsyncCallback<List<String>> asyncCallback);

    void saveCompanyUserPermissions(
            String companyId, 
            String role,
            List<String> companyUserPermissionList,
            AsyncCallback<String> asyncCallback);

}