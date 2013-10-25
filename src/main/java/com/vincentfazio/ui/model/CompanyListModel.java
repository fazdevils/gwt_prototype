package com.vincentfazio.ui.model;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface CompanyListModel extends Model {

    void getCompanyList(AsyncCallback<ArrayList<String>> asyncCallback);

}