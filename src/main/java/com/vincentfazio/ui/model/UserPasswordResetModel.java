package com.vincentfazio.ui.model;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface UserPasswordResetModel extends Model {

    public abstract void resetUserPassword(String userId, final AsyncCallback<String> asyncCallback);

}
