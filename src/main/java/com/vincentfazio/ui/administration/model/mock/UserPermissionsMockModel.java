package com.vincentfazio.ui.administration.model.mock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.vincentfazio.ui.model.UserPermissionsModel;

public class UserPermissionsMockModel implements UserPermissionsModel {
    
    private Map<String, List<String>> mockPermissionsMap = new HashMap<String, List<String>>();
    
    @Override
    public void getUserPermissions(String userId, String role, AsyncCallback<List<String>> asyncCallback) {
        asyncCallback.onSuccess(getVendorPermissionList(userId, role));
    }

    private List<String> getVendorPermissionList(String userId, String role) {
        String key = getKey(userId, role);
        List<String> permissions = mockPermissionsMap.get(key);
        if (null == permissions) {
            permissions = new ArrayList<String>();
            for (int i=0; i < 4000; ++i) {
                if ((i%5) == 3) {
                    permissions.add("Vendor " + (i+1));
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
    public void saveUserPermissions(
            String userId, 
            String role,
            List<String> vendorsPermissionList,
            AsyncCallback<String> asyncCallback) 
    {
        mockPermissionsMap.put(getKey(userId, role), copyList(vendorsPermissionList));        
        asyncCallback.onSuccess("Saved");  
    }

    private List<String> copyList(
            List<String> vendorsPermissionList) {
        
        List<String> copy = new ArrayList<String>();
        for (String bean: vendorsPermissionList) {
            copy.add(bean);
        }
        return copy;
    }

}
