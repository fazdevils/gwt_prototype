package com.vincentfazio.ui.model;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface VendorUserPermissionsModel extends Model {

    void getVendorUserPermissions(String vendorId, String role, AsyncCallback<List<String>> asyncCallback);

    void saveVendorUserPermissions(
            String vendorId, 
            String role,
            List<String> vendorUserPermissionList,
            AsyncCallback<String> asyncCallback);

}