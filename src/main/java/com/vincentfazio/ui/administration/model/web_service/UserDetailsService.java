package com.vincentfazio.ui.administration.model.web_service;

import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.vincentfazio.ui.bean.UserDetailsBean;
import com.vincentfazio.ui.global.GwtGlobals;
import com.vincentfazio.ui.model.ErrorModel;
import com.vincentfazio.ui.model.UserDetailsModel;
import com.vincentfazio.ui.model.parser.xml.UserDetailsXmlParser;
import com.vincentfazio.ui.model.web_service.GwtRequestCallback;
import com.vincentfazio.ui.model.web_service.GwtService;
import com.vincentfazio.ui.model.web_service.GwtStatusOnlyRequestCallback;

public class UserDetailsService extends GwtService implements UserDetailsModel {

    private final String baseUrl;
    private final UserDetailsXmlParser xmlParser;
    private final GwtGlobals globals;
    
    public UserDetailsService(GwtGlobals globals) {
        super();
        this.xmlParser = new UserDetailsXmlParser();
        this.baseUrl = globals.getServiceBaseUrl() + "/admin/users";
        this.globals = globals;
    }


    @Override
    public void getUser(String userId, final AsyncCallback<UserDetailsBean> asyncCallback) {
        String url = getEncodedUrl(userId);
        ErrorModel errorModel = (ErrorModel) globals.getModel(ErrorModel.class);
        GwtRequestCallback<UserDetailsBean> requestCallback = new GwtRequestCallback<UserDetailsBean>(url, asyncCallback, xmlParser, errorModel);  
        RequestBuilder builder = createRequestBuilder(RequestBuilder.GET, url);

        try {
          builder.sendRequest(null, requestCallback);
        } catch (Exception e) {
            asyncCallback.onFailure(e);
        }    
    }


    @Override
    public void saveUser(UserDetailsBean userDetails, final AsyncCallback<String> asyncCallback) {
        String url = getEncodedUrl(userDetails.getUserId());
        ErrorModel errorModel = (ErrorModel) globals.getModel(ErrorModel.class);
        RequestCallback requestCallback = new GwtStatusOnlyRequestCallback(url, asyncCallback, "Saved", errorModel);
        RequestBuilder builder = createRequestBuilder(RequestBuilder.PUT, url);

        builder.setHeader("Content-Type", "application/xml");
        builder.setRequestData(xmlParser.createXml(userDetails)); 
        builder.setCallback(requestCallback);
        
        try {
          builder.send();
        } catch (Exception e) {
            asyncCallback.onFailure(e);
        }    
        
    }


    @Override
    public void createUser(String userId, final AsyncCallback<String> asyncCallback) {
        UserDetailsBean userDetails = new UserDetailsBean();
        userDetails.setUserId(userId);

        String url = getEncodedUrl(userId);
        ErrorModel errorModel = (ErrorModel) globals.getModel(ErrorModel.class);
        RequestCallback requestCallback = new GwtStatusOnlyRequestCallback(url, asyncCallback, "Created", errorModel);
        RequestBuilder builder = createRequestBuilder(RequestBuilder.POST, url);
        builder.setHeader("Content-Type", "application/xml");
        builder.setRequestData(xmlParser.createXml(userDetails)); 
        builder.setCallback(requestCallback);
        
        try {
          builder.send();
        } catch (Exception e) {
            asyncCallback.onFailure(e);
        }    
    }


    @Override
    public void deleteUser(String userId, final AsyncCallback<String> asyncCallback) {
        String url = getEncodedUrl(userId);
        ErrorModel errorModel = (ErrorModel) globals.getModel(ErrorModel.class);
        RequestCallback requestCallback = new GwtStatusOnlyRequestCallback(url, asyncCallback, "Deleted", errorModel);
        RequestBuilder builder = createRequestBuilder(RequestBuilder.DELETE, url);

        try {
          builder.sendRequest(null, requestCallback);
        } catch (Exception e) {
            asyncCallback.onFailure(e);
        }    
    }
    
    private String getEncodedUrl(String userId) {
        return encodeUrl(baseUrl + "/" + userId);
    }
    
}
