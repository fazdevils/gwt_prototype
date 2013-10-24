package com.vincentfazio.ui.administration.model.web_service;

import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.vincentfazio.ui.global.GwtGlobals;
import com.vincentfazio.ui.model.ErrorModel;
import com.vincentfazio.ui.model.UserPasswordResetModel;
import com.vincentfazio.ui.model.parser.xml.EmptyStringXmlParser;
import com.vincentfazio.ui.model.web_service.GwtRequestCallback;
import com.vincentfazio.ui.model.web_service.GwtService;

public class UserPasswordResetService extends GwtService implements UserPasswordResetModel {

    private final String baseUrl;
    private final EmptyStringXmlParser xmlParser;
    private final GwtGlobals globals;
    
    public UserPasswordResetService(GwtGlobals globals) {
        super();
        this.xmlParser = new EmptyStringXmlParser();
        this.baseUrl = globals.getServiceBaseUrl() + "/admin/users";
        this.globals = globals;
    }

    @Override
    public void resetUserPassword(String userId, final AsyncCallback<String> asyncCallback) {
        String url = getEncodedUrl(userId);
        ErrorModel errorModel = (ErrorModel) globals.getModel(ErrorModel.class);
        GwtRequestCallback<String> requestCallback = new GwtRequestCallback<String>(url, asyncCallback, xmlParser, errorModel);  

        RequestBuilder builder = createRequestBuilder(RequestBuilder.PUT, url);

        try {
          builder.sendRequest(null, requestCallback);
        } catch (Exception e) {
            asyncCallback.onFailure(e);
        }    
    }

    
    private String getEncodedUrl(String userId) {
        return encodeUrl(baseUrl + "/" + userId + "/password_reset");
    }
    
}
