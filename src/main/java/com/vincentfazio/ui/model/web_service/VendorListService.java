package com.vincentfazio.ui.model.web_service;

import java.util.ArrayList;

import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.vincentfazio.ui.global.GwtGlobals;
import com.vincentfazio.ui.model.ErrorModel;
import com.vincentfazio.ui.model.VendorListModel;
import com.vincentfazio.ui.model.parser.xml.VendorListXmlParser;

public class VendorListService extends GwtService implements VendorListModel {

    private final String baseUrl;
    private final VendorListXmlParser xmlParser;
    private final GwtGlobals globals;
    
    public VendorListService(GwtGlobals globals) {
        super();
        this.xmlParser = new VendorListXmlParser();
        this.baseUrl = globals.getServiceBaseUrl() + "/" + globals.getRole() + "/vendors";
        this.globals = globals;
    }


    @Override
    public void getVendorList(final AsyncCallback<ArrayList<String>> asyncCallback) {
        String url = encodeUrl(baseUrl);
        ErrorModel errorModel = (ErrorModel) globals.getModel(ErrorModel.class);
        GwtRequestCallback<ArrayList<String>> requestCallback = new GwtRequestCallback<ArrayList<String>>(url, asyncCallback, xmlParser, errorModel);  
        RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, url);

        try {
            builder.sendRequest(null, requestCallback);
        } catch (Exception e) {
            asyncCallback.onFailure(e);
        }    
    }
    
}
