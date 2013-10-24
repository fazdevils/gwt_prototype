package com.vincentfazio.ui.model.web_service;

import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.vincentfazio.ui.bean.PasswordBean;
import com.vincentfazio.ui.global.GwtGlobals;
import com.vincentfazio.ui.model.ErrorModel;
import com.vincentfazio.ui.model.MyPasswordModel;
import com.vincentfazio.ui.model.parser.xml.PasswordXmlParser;

public class MyPasswordService extends GwtService implements MyPasswordModel {

    private final String baseUrl;
    private final PasswordXmlParser xmlParser;
    private final GwtGlobals globals;
    
    public MyPasswordService(GwtGlobals globals) {
        super();
        this.xmlParser = new PasswordXmlParser();
        baseUrl = globals.getUserServicesBaseUrl() + "/secure/change_password";
        this.globals = globals;
    }


    @Override
    public void changePassword(PasswordBean password, final AsyncCallback<String> asyncCallback) {
        String url = encodeUrl(baseUrl);
        ErrorModel errorModel = (ErrorModel) globals.getModel(ErrorModel.class);
        RequestCallback requestCallback = new GwtStatusOnlyRequestCallback(url, asyncCallback, "Saved", errorModel);
        RequestBuilder builder = createRequestBuilder(RequestBuilder.PUT, url);

        builder.setHeader("Content-Type", "application/xml");
        builder.setRequestData(xmlParser.createXml(password)); 
        builder.setCallback(requestCallback);
        
        try {
          builder.send();
        } catch (Exception e) {
            asyncCallback.onFailure(e);
        }    
        
    }

}
