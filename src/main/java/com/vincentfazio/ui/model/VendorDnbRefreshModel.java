package com.vincentfazio.ui.model;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface VendorDnbRefreshModel extends Model {

    void refresh(String vendorId, AsyncCallback<String> asyncCallback);
}