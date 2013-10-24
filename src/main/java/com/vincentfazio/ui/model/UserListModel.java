package com.vincentfazio.ui.model;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface UserListModel extends Model {

    void getUserList(AsyncCallback<ArrayList<String>> asyncCallback);
    void getUserList(String role, AsyncCallback<ArrayList<String>> asyncCallback);

}