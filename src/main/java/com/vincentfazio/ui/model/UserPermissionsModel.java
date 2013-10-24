package com.vincentfazio.ui.model;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface UserPermissionsModel extends Model {

    void getUserPermissions(String userId, String role, AsyncCallback<List<String>> asyncCallback);

    void saveUserPermissions(
            String userId, 
            String role,
            List<String> companyPermissionList,
            AsyncCallback<String> asyncCallback);

}