package com.vincentfazio.ui.administration.model.web_service;

import java.util.ArrayList;

import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.vincentfazio.ui.global.GwtGlobals;
import com.vincentfazio.ui.model.ErrorModel;
import com.vincentfazio.ui.model.UserListModel;
import com.vincentfazio.ui.model.parser.xml.UserListXmlParser;
import com.vincentfazio.ui.model.web_service.GwtRequestCallback;
import com.vincentfazio.ui.model.web_service.GwtService;

public class UserListService extends GwtService implements UserListModel {

    private final String baseUrl;
    private final UserListXmlParser xmlParser;
    private final GwtGlobals globals;
    
    public UserListService(GwtGlobals globals) {
        super();
        this.xmlParser = new UserListXmlParser();
        this.baseUrl = globals.getServiceBaseUrl() + "/admin/users";
        this.globals = globals;
    }


    @Override
    public void getUserList(final AsyncCallback<ArrayList<String>> asyncCallback) {
        String url = encodeUrl(baseUrl);
        ErrorModel errorModel = (ErrorModel) globals.getModel(ErrorModel.class);
        GwtRequestCallback<ArrayList<String>> requestCallback = new GwtRequestCallback<ArrayList<String>>(url, asyncCallback, xmlParser, errorModel);  
        RequestBuilder builder = createRequestBuilder(RequestBuilder.GET, url);

        try {
            builder.sendRequest(null, requestCallback);
        } catch (Exception e) {
            asyncCallback.onFailure(e);
        }    
    }

    @Override
    public void getUserList(String role, final AsyncCallback<ArrayList<String>> asyncCallback) {
        String url = getEncodedRoleUrl(role);
        ErrorModel errorModel = (ErrorModel) globals.getModel(ErrorModel.class);
        GwtRequestCallback<ArrayList<String>> requestCallback = new GwtRequestCallback<ArrayList<String>>(url, asyncCallback, xmlParser, errorModel);  
        RequestBuilder builder = createRequestBuilder(RequestBuilder.GET, url);

        try {
            builder.sendRequest(null, requestCallback);
        } catch (Exception e) {
            asyncCallback.onFailure(e);
        }    
    }
    
    private String getEncodedRoleUrl(String roleId) {
        return encodeUrl(baseUrl + "/roles/" + roleId.toLowerCase());
    }

}
