package com.vincentfazio.ui.model;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.vincentfazio.ui.bean.PasswordBean;

public interface MyPasswordModel extends Model {

    void changePassword(PasswordBean password, AsyncCallback<String> asyncCallback);

}