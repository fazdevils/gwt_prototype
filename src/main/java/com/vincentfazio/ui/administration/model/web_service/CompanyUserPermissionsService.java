package com.vincentfazio.ui.administration.model.web_service;

import java.util.List;

import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.vincentfazio.ui.global.GwtGlobals;
import com.vincentfazio.ui.model.ErrorModel;
import com.vincentfazio.ui.model.CompanyUserPermissionsModel;
import com.vincentfazio.ui.model.parser.xml.CompanyUserPermissionsXmlParser;
import com.vincentfazio.ui.model.web_service.GwtRequestCallback;
import com.vincentfazio.ui.model.web_service.GwtService;
import com.vincentfazio.ui.model.web_service.GwtStatusOnlyRequestCallback;

public class CompanyUserPermissionsService extends GwtService implements CompanyUserPermissionsModel {

    private final String baseUrl;
    private final CompanyUserPermissionsXmlParser xmlParser;
    private final GwtGlobals globals;
    
    public CompanyUserPermissionsService(GwtGlobals globals) {
        super();
        this.xmlParser = new CompanyUserPermissionsXmlParser();
        this.baseUrl = globals.getServiceBaseUrl() + "/admin/companies";
        this.globals = globals;
    }


    @Override
    public void getCompanyUserPermissions(String companyId, String role, AsyncCallback<List<String>> asyncCallback) {
        String url = getEncodedUrl(companyId, role);
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
    public void saveCompanyUserPermissions(
            String companyId, 
            String role,
            List<String> companyUserPermissionList,
            AsyncCallback<String> asyncCallback) 
    {
        String url = getEncodedUrl(companyId, role);
        ErrorModel errorModel = (ErrorModel) globals.getModel(ErrorModel.class);
        RequestCallback requestCallback = new GwtStatusOnlyRequestCallback(url, asyncCallback, "Saved", errorModel);
        RequestBuilder builder = createRequestBuilder(RequestBuilder.PUT, url);

        builder.setHeader("Content-Type", "application/xml");
        builder.setRequestData(xmlParser.createXml(companyUserPermissionList)); 
        builder.setCallback(requestCallback);
        
        try {
          builder.send();
        } catch (Exception e) {
            asyncCallback.onFailure(e);
        }    
        
    }
    
    
    private String getEncodedUrl(String companyId, String roleId) {
        return encodeUrl(baseUrl + "/" + companyId + "/" + roleId.toLowerCase());
    }
    
}
