package com.vincentfazio.ui.administration.model.mock;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.vincentfazio.ui.model.UserPasswordResetModel;

public class UserPasswordResetMockModel implements UserPasswordResetModel {

    public void resetUserPassword(String userId, final AsyncCallback<String> asyncCallback) {
        asyncCallback.onSuccess("");
    }

}
