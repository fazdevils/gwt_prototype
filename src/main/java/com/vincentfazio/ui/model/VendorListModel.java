package com.vincentfazio.ui.model;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface VendorListModel extends Model {

    void getVendorList(AsyncCallback<ArrayList<String>> asyncCallback);

}