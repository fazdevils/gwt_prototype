package com.vincentfazio.ui.model.web_service;

import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.vincentfazio.ui.bean.CompanyDetailsBean;
import com.vincentfazio.ui.global.GwtGlobals;
import com.vincentfazio.ui.model.ErrorModel;
import com.vincentfazio.ui.model.CompanyDetailsModel;
import com.vincentfazio.ui.model.parser.xml.CompanyDetailsXmlParser;

public class CompanyDetailsService extends GwtService implements CompanyDetailsModel {

    private final String baseUrl;
    private final CompanyDetailsXmlParser xmlParser;
    private final GwtGlobals globals;
   
    public CompanyDetailsService(GwtGlobals globals) {
        super();
        this.xmlParser = new CompanyDetailsXmlParser();
        this.baseUrl = globals.getServiceBaseUrl() + "/" + globals.getRole() + "/companies";
        this.globals = globals;
    }


    @Override
    public void getCompany(String companyId, final AsyncCallback<CompanyDetailsBean> asyncCallback) {
        String url = getEncodedUrl(companyId);
        ErrorModel errorModel = (ErrorModel) globals.getModel(ErrorModel.class);
        GwtRequestCallback<CompanyDetailsBean> requestCallback = new GwtRequestCallback<CompanyDetailsBean>(url, asyncCallback, xmlParser, errorModel);  
        RequestBuilder builder = createRequestBuilder(RequestBuilder.GET, url);

        try {
          builder.sendRequest(null, requestCallback);
        } catch (Exception e) {
            asyncCallback.onFailure(e);
        }    
    }


    @Override
    public void saveCompany(CompanyDetailsBean companyDetails, final AsyncCallback<String> asyncCallback) {
        String url = getEncodedUrl(companyDetails.getCompanyId());
        ErrorModel errorModel = (ErrorModel) globals.getModel(ErrorModel.class);
        RequestCallback requestCallback = new GwtStatusOnlyRequestCallback(url, asyncCallback, "Saved", errorModel);
        RequestBuilder builder = createRequestBuilder(RequestBuilder.PUT, url);
        builder.setHeader("Content-Type", "application/xml");
        builder.setRequestData(xmlParser.createXml(companyDetails)); 
        builder.setCallback(requestCallback);
        
        try {
          builder.send();
        } catch (Exception e) {
            asyncCallback.onFailure(e);
        }    
        
    }


    @Override
    public void createCompany(String companyId, final AsyncCallback<String> asyncCallback) {
        CompanyDetailsBean companyDetails = new CompanyDetailsBean();
        companyDetails.setCompanyId(companyId);

        String url = getEncodedUrl(companyId);
        ErrorModel errorModel = (ErrorModel) globals.getModel(ErrorModel.class);
        RequestCallback requestCallback = new GwtStatusOnlyRequestCallback(url, asyncCallback, "Created", errorModel);
        RequestBuilder builder = createRequestBuilder(RequestBuilder.POST, url);
        builder.setHeader("Content-Type", "application/xml");
        builder.setRequestData(xmlParser.createXml(companyDetails)); 
        builder.setCallback(requestCallback);
        
        try {
          builder.send();
        } catch (Exception e) {
            asyncCallback.onFailure(e);
        }    
    }


    @Override
    public void deleteCompany(String companyId, final AsyncCallback<String> asyncCallback) {
        String url = getEncodedUrl(companyId);
        ErrorModel errorModel = (ErrorModel) globals.getModel(ErrorModel.class);
        RequestCallback requestCallback = new GwtStatusOnlyRequestCallback(url, asyncCallback, "Deleted", errorModel);
        RequestBuilder builder = createRequestBuilder(RequestBuilder.DELETE, url);

        try {
          builder.sendRequest(null, requestCallback);
        } catch (Exception e) {
            asyncCallback.onFailure(e);
        }    
    }
    
    private String getEncodedUrl(String companyId) {
        return encodeUrl(baseUrl + "/" + companyId);
    }
    
}
