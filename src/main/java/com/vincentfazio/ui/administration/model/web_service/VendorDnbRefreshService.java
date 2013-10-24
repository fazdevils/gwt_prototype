package com.vincentfazio.ui.administration.model.web_service;

import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.vincentfazio.ui.global.GwtGlobals;
import com.vincentfazio.ui.model.ErrorModel;
import com.vincentfazio.ui.model.VendorDnbRefreshModel;
import com.vincentfazio.ui.model.web_service.GwtService;
import com.vincentfazio.ui.model.web_service.GwtStatusOnlyRequestCallback;

public class VendorDnbRefreshService extends GwtService implements VendorDnbRefreshModel {

    private final String baseUrl;
    private final GwtGlobals globals;
    
    public VendorDnbRefreshService(GwtGlobals globals) {
        super();
        this.baseUrl = globals.getServiceBaseUrl() + "/admin/vendors";
        this.globals = globals;
    }


    @Override
    public void refresh(String vendorId, final AsyncCallback<String> asyncCallback) {
        String url = getEncodedUrl(vendorId);
        ErrorModel errorModel = (ErrorModel) globals.getModel(ErrorModel.class);
        RequestCallback requestCallback = new GwtStatusOnlyRequestCallback(url, asyncCallback, "D&B Data Requested", errorModel);
        RequestBuilder builder = createRequestBuilder(RequestBuilder.PUT, url);

        builder.setHeader("Content-Type", "application/xml");
        builder.setCallback(requestCallback);
        
        try {
          builder.send();
        } catch (Exception e) {
            asyncCallback.onFailure(e);
        }    
        
    }
    
    
    private String getEncodedUrl(String vendorId) {
        return encodeUrl(baseUrl + "/" + vendorId + "/dnb_update");
    }
    
}
