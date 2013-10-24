package com.vincentfazio.ui.administration.model.web_service;

import java.util.List;

import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.vincentfazio.ui.global.GwtGlobals;
import com.vincentfazio.ui.model.ErrorModel;
import com.vincentfazio.ui.model.VendorUserPermissionsModel;
import com.vincentfazio.ui.model.parser.xml.VendorUserPermissionsXmlParser;
import com.vincentfazio.ui.model.web_service.GwtRequestCallback;
import com.vincentfazio.ui.model.web_service.GwtService;
import com.vincentfazio.ui.model.web_service.GwtStatusOnlyRequestCallback;

public class VendorUserPermissionsService extends GwtService implements VendorUserPermissionsModel {

    private final String baseUrl;
    private final VendorUserPermissionsXmlParser xmlParser;
    private final GwtGlobals globals;
    
    public VendorUserPermissionsService(GwtGlobals globals) {
        super();
        this.xmlParser = new VendorUserPermissionsXmlParser();
        this.baseUrl = globals.getServiceBaseUrl() + "/admin/vendors";
        this.globals = globals;
    }


    @Override
    public void getVendorUserPermissions(String vendorId, String role, AsyncCallback<List<String>> asyncCallback) {
        String url = getEncodedUrl(vendorId, role);
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
    public void saveVendorUserPermissions(
            String vendorId, 
            String role,
            List<String> vendorUserPermissionList,
            AsyncCallback<String> asyncCallback) 
    {
        String url = getEncodedUrl(vendorId, role);
        ErrorModel errorModel = (ErrorModel) globals.getModel(ErrorModel.class);
        RequestCallback requestCallback = new GwtStatusOnlyRequestCallback(url, asyncCallback, "Saved", errorModel);
        RequestBuilder builder = createRequestBuilder(RequestBuilder.PUT, url);

        builder.setHeader("Content-Type", "application/xml");
        builder.setRequestData(xmlParser.createXml(vendorUserPermissionList)); 
        builder.setCallback(requestCallback);
        
        try {
          builder.send();
        } catch (Exception e) {
            asyncCallback.onFailure(e);
        }    
        
    }
    
    
    private String getEncodedUrl(String vendorId, String roleId) {
        return encodeUrl(baseUrl + "/" + vendorId + "/" + roleId.toLowerCase());
    }
    
}
