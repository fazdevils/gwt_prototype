package com.vincentfazio.ui.model.web_service;

import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.vincentfazio.ui.bean.MyDetailsBean;
import com.vincentfazio.ui.global.GwtGlobals;
import com.vincentfazio.ui.model.ErrorModel;
import com.vincentfazio.ui.model.MyDetailsModel;
import com.vincentfazio.ui.model.parser.xml.MyDetailsXmlParser;

public class MyDetailsService extends GwtService implements MyDetailsModel {

    private final String getUrl;
    private final String putUrl;
    private final MyDetailsXmlParser xmlParser;
    private final GwtGlobals globals;
    
    public MyDetailsService(GwtGlobals globals) {
        super();
        this.xmlParser = new MyDetailsXmlParser();
        getUrl = globals.getServiceBaseUrl() + "/" + globals.getRole() + "/whoami";
        putUrl = globals.getUserServicesBaseUrl() + "/secure/whoami";
        this.globals = globals;
    }


    @Override
    public void getMyDetails(final AsyncCallback<MyDetailsBean> asyncCallback) {
        String url = encodeUrl(getUrl);
        ErrorModel errorModel = (ErrorModel) globals.getModel(ErrorModel.class);
        GwtRequestCallback<MyDetailsBean> requestCallback = new GwtRequestCallback<MyDetailsBean>(url, asyncCallback, xmlParser, errorModel);  
        RequestBuilder builder = createRequestBuilder(RequestBuilder.GET, url);

        try {
          builder.sendRequest(null, requestCallback);
        } catch (Exception e) {
            asyncCallback.onFailure(e);
        }    
    }


    @Override
    public void saveMyDetails(MyDetailsBean userDetails, final AsyncCallback<String> asyncCallback) {
        String url = encodeUrl(putUrl);
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

}
