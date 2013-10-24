package com.vincentfazio.ui.model;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.vincentfazio.ui.bean.MyDetailsBean;

public interface MyDetailsModel extends Model {
    
    void getMyDetails(AsyncCallback<MyDetailsBean> asyncCallback);

    void saveMyDetails(MyDetailsBean updatedDetails, AsyncCallback<String> asyncCallback);

}