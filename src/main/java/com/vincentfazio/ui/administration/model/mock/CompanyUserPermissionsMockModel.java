package com.vincentfazio.ui.administration.model.mock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.vincentfazio.ui.model.CompanyUserPermissionsModel;

public class CompanyUserPermissionsMockModel implements CompanyUserPermissionsModel {
    
    private Map<String, List<String>> mockPermissionsMap = new HashMap<String, List<String>>();
    
    @Override
    public void getCompanyUserPermissions(String companyId, String role, AsyncCallback<List<String>> asyncCallback) {
        asyncCallback.onSuccess(getCompanyUserPermissionList(companyId, role));
    }

    private List<String> getCompanyUserPermissionList(String userId, String role) {
        String key = getKey(userId, role);
        List<String> permissions = mockPermissionsMap.get(key);
        if (null == permissions) {
            permissions = new ArrayList<String>();
            for (int i=0; i < 4000; ++i) {
                if ((i%5) == 3) {
                    permissions.add("User " + (i+1));
                }
            }
            mockPermissionsMap.put(key, permissions);
        }
        
        return copyList(permissions);
    }

    private String getKey(String userId, String role) {
        return userId + role;
    }

    @Override
    public void saveCompanyUserPermissions(
            String companyId, 
            String role,
            List<String> companyUserPermissionList,
            AsyncCallback<String> asyncCallback) 
    {
        mockPermissionsMap.put(getKey(companyId, role), copyList(companyUserPermissionList));        
        asyncCallback.onSuccess("Saved");  
    }

    private List<String> copyList(
            List<String> companyUserPermissionList) {
        
        List<String> copy = new ArrayList<String>();
        for (String bean: companyUserPermissionList) {
            copy.add(bean);
        }
        return copy;
    }

}
