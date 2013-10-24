package com.vincentfazio.ui.administration.model.mock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.vincentfazio.ui.model.VendorUserPermissionsModel;

public class VendorUserPermissionsMockModel implements VendorUserPermissionsModel {
    
    private Map<String, List<String>> mockPermissionsMap = new HashMap<String, List<String>>();
    
    @Override
    public void getVendorUserPermissions(String vendorId, String role, AsyncCallback<List<String>> asyncCallback) {
        asyncCallback.onSuccess(getVendorUserPermissionList(vendorId, role));
    }

    private List<String> getVendorUserPermissionList(String userId, String role) {
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
    public void saveVendorUserPermissions(
            String vendorId, 
            String role,
            List<String> vendorUserPermissionList,
            AsyncCallback<String> asyncCallback) 
    {
        mockPermissionsMap.put(getKey(vendorId, role), copyList(vendorUserPermissionList));        
        asyncCallback.onSuccess("Saved");  
    }

    private List<String> copyList(
            List<String> vendorUserPermissionList) {
        
        List<String> copy = new ArrayList<String>();
        for (String bean: vendorUserPermissionList) {
            copy.add(bean);
        }
        return copy;
    }

}
