package com.vincentfazio.ui.administration.model.mock;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.vincentfazio.ui.model.VendorDnbRefreshModel;

public class VendorDnbRefreshMockModel implements VendorDnbRefreshModel {
    
    @Override
    public void refresh(String vendorId, AsyncCallback<String> asyncCallback) {
        final AsyncCallback<String> callback = asyncCallback;
        Timer t = new Timer() {
            public void run() {
                callback.onSuccess("D&B Data Requested");  
            }
        };
        t.schedule(2000);
    }
}
