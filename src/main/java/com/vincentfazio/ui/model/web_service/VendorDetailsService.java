package com.vincentfazio.ui.model.web_service;

import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.vincentfazio.ui.bean.VendorDetailsBean;
import com.vincentfazio.ui.global.GwtGlobals;
import com.vincentfazio.ui.model.ErrorModel;
import com.vincentfazio.ui.model.VendorDetailsModel;
import com.vincentfazio.ui.model.parser.xml.VendorDetailsXmlParser;

public class VendorDetailsService extends GwtService implements VendorDetailsModel {

    private final String baseUrl;
    private final VendorDetailsXmlParser xmlParser;
    private final GwtGlobals globals;
   
    public VendorDetailsService(GwtGlobals globals) {
        super();
        this.xmlParser = new VendorDetailsXmlParser();
        this.baseUrl = globals.getServiceBaseUrl() + "/" + globals.getRole() + "/vendors";
        this.globals = globals;
    }


    @Override
    public void getVendor(String vendorId, final AsyncCallback<VendorDetailsBean> asyncCallback) {
        String url = getEncodedUrl(vendorId);
        ErrorModel errorModel = (ErrorModel) globals.getModel(ErrorModel.class);
        GwtRequestCallback<VendorDetailsBean> requestCallback = new GwtRequestCallback<VendorDetailsBean>(url, asyncCallback, xmlParser, errorModel);  
        RequestBuilder builder = createRequestBuilder(RequestBuilder.GET, url);

        try {
          builder.sendRequest(null, requestCallback);
        } catch (Exception e) {
            asyncCallback.onFailure(e);
        }    
    }


    @Override
    public void saveVendor(VendorDetailsBean vendorDetails, final AsyncCallback<String> asyncCallback) {
        String url = getEncodedUrl(vendorDetails.getVendorId());
        ErrorModel errorModel = (ErrorModel) globals.getModel(ErrorModel.class);
        RequestCallback requestCallback = new GwtStatusOnlyRequestCallback(url, asyncCallback, "Saved", errorModel);
        RequestBuilder builder = createRequestBuilder(RequestBuilder.PUT, url);
        builder.setHeader("Content-Type", "application/xml");
        builder.setRequestData(xmlParser.createXml(vendorDetails)); 
        builder.setCallback(requestCallback);
        
        try {
          builder.send();
        } catch (Exception e) {
            asyncCallback.onFailure(e);
        }    
        
    }


    @Override
    public void createVendor(String vendorId, final AsyncCallback<String> asyncCallback) {
        VendorDetailsBean vendorDetails = new VendorDetailsBean();
        vendorDetails.setVendorId(vendorId);

        String url = getEncodedUrl(vendorId);
        ErrorModel errorModel = (ErrorModel) globals.getModel(ErrorModel.class);
        RequestCallback requestCallback = new GwtStatusOnlyRequestCallback(url, asyncCallback, "Created", errorModel);
        RequestBuilder builder = createRequestBuilder(RequestBuilder.POST, url);
        builder.setHeader("Content-Type", "application/xml");
        builder.setRequestData(xmlParser.createXml(vendorDetails)); 
        builder.setCallback(requestCallback);
        
        try {
          builder.send();
        } catch (Exception e) {
            asyncCallback.onFailure(e);
        }    
    }


    @Override
    public void deleteVendor(String vendorId, final AsyncCallback<String> asyncCallback) {
        String url = getEncodedUrl(vendorId);
        ErrorModel errorModel = (ErrorModel) globals.getModel(ErrorModel.class);
        RequestCallback requestCallback = new GwtStatusOnlyRequestCallback(url, asyncCallback, "Deleted", errorModel);
        RequestBuilder builder = createRequestBuilder(RequestBuilder.DELETE, url);

        try {
          builder.sendRequest(null, requestCallback);
        } catch (Exception e) {
            asyncCallback.onFailure(e);
        }    
    }
    
    private String getEncodedUrl(String vendorId) {
        return encodeUrl(baseUrl + "/" + vendorId);
    }
    
}
