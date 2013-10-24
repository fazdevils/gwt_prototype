package com.vincentfazio.ui.model;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.vincentfazio.ui.bean.VendorDetailsBean;

public interface VendorDetailsModel extends Model {

    void getVendor(String vendorId, AsyncCallback<VendorDetailsBean> asyncCallback);

    void saveVendor(VendorDetailsBean vendorDetails, AsyncCallback<String> asyncCallback);

    void createVendor(String vendorId, AsyncCallback<String> asyncCallback);

    void deleteVendor(String vendorId, AsyncCallback<String> asyncCallback);

}