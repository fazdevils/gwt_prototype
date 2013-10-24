package com.vincentfazio.ui.administration.model.web_service;

import java.util.List;

import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.vincentfazio.ui.global.GwtGlobals;
import com.vincentfazio.ui.model.ErrorModel;
import com.vincentfazio.ui.model.UserPermissionsModel;
import com.vincentfazio.ui.model.parser.xml.UserPermissionsXmlParser;
import com.vincentfazio.ui.model.web_service.GwtRequestCallback;
import com.vincentfazio.ui.model.web_service.GwtService;
import com.vincentfazio.ui.model.web_service.GwtStatusOnlyRequestCallback;

public class UserPermissionsService extends GwtService implements UserPermissionsModel {

    private final String baseUrl;
    private final UserPermissionsXmlParser xmlParser;
    private final GwtGlobals globals;
    
    public UserPermissionsService(GwtGlobals globals) {
        super();
        this.xmlParser = new UserPermissionsXmlParser();
        this.baseUrl = globals.getServiceBaseUrl() + "/admin/users";
        this.globals = globals;
    }


    @Override
    public void getUserPermissions(String userId, String role, AsyncCallback<List<String>> asyncCallback) {
        String url = getEncodedUrl(userId, role);
        ErrorModel errorModel = (ErrorModel) globals.getModel(ErrorModel.class);
        GwtRequestCallback<List<String>> requestCallback = new GwtRequestCallback<List<String>>(url, asyncCallback, xmlParser, errorModel);  
        RequestBuilder builder = createRequestBuilder(RequestBuilder.GET, url);

        try {
          builder.sendRequest(null, requestCallback);
        } catch (Exception e) {
            asyncCallback.onFailure(e);
        }    
    }


    @Override
    public void saveUserPermissions(
            String userId, 
            String role,
            List<String> vendorPermissionList,
            AsyncCallback<String> asyncCallback) 
    {
        String url = getEncodedUrl(userId, role);
        ErrorModel errorModel = (ErrorModel) globals.getModel(ErrorModel.class);
        RequestCallback requestCallback = new GwtStatusOnlyRequestCallback(url, asyncCallback, "Saved", errorModel);
        RequestBuilder builder = createRequestBuilder(RequestBuilder.PUT, url);

        builder.setHeader("Content-Type", "application/xml");
        builder.setRequestData(xmlParser.createXml(vendorPermissionList)); 
        builder.setCallback(requestCallback);
        
        try {
          builder.send();
        } catch (Exception e) {
            asyncCallback.onFailure(e);
        }    
        
    }
    
    
    private String getEncodedUrl(String userId, String roleId) {
        return encodeUrl(baseUrl + "/" + userId + "/" + roleId.toLowerCase());
    }
    
}
