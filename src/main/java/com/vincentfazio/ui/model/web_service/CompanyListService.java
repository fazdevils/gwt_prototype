package com.vincentfazio.ui.model.web_service;

import java.util.ArrayList;

import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.vincentfazio.ui.global.GwtGlobals;
import com.vincentfazio.ui.model.ErrorModel;
import com.vincentfazio.ui.model.CompanyListModel;
import com.vincentfazio.ui.model.parser.xml.CompanyListXmlParser;

public class CompanyListService extends GwtService implements CompanyListModel {

    private final String baseUrl;
    private final CompanyListXmlParser xmlParser;
    private final GwtGlobals globals;
    
    public CompanyListService(GwtGlobals globals) {
        super();
        this.xmlParser = new CompanyListXmlParser();
        this.baseUrl = globals.getServiceBaseUrl() + "/" + globals.getRole() + "/companies";
        this.globals = globals;
    }


    @Override
    public void getCompanyList(final AsyncCallback<ArrayList<String>> asyncCallback) {
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
