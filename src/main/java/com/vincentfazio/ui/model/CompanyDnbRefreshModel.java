package com.vincentfazio.ui.model;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface CompanyDnbRefreshModel extends Model {

    void refresh(String companyId, AsyncCallback<String> asyncCallback);
}