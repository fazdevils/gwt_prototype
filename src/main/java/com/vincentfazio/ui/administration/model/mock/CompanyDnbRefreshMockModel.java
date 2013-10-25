package com.vincentfazio.ui.administration.model.mock;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.vincentfazio.ui.model.CompanyDnbRefreshModel;

public class CompanyDnbRefreshMockModel implements CompanyDnbRefreshModel {
    
    @Override
    public void refresh(String companyId, AsyncCallback<String> asyncCallback) {
        final AsyncCallback<String> callback = asyncCallback;
        Timer t = new Timer() {
            public void run() {
                callback.onSuccess("D&B Data Requested (Mock)");  
            }
        };
        t.schedule(2000);
    }
}
