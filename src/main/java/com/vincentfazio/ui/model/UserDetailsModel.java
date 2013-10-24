package com.vincentfazio.ui.model;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.vincentfazio.ui.bean.UserDetailsBean;

public interface UserDetailsModel extends Model {

    void getUser(String userId, AsyncCallback<UserDetailsBean> asyncCallback);

    void saveUser(UserDetailsBean user, AsyncCallback<String> asyncCallback);

    void createUser(String userId, AsyncCallback<String> asyncCallback);

    void deleteUser(String userId, AsyncCallback<String> asyncCallback);

}