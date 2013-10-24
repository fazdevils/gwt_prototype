package com.vincentfazio.ui.model.mock;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.vincentfazio.ui.bean.PasswordBean;
import com.vincentfazio.ui.model.MyPasswordModel;

public class MyPasswordMockModel implements MyPasswordModel {
    
    public MyPasswordMockModel() {
    }

    @Override
    public void changePassword(PasswordBean password, AsyncCallback<String> asyncCallback) {
        asyncCallback.onSuccess("Password Changed");  
    }

}
