package com.vincentfazio.ui.model.web_service;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.vincentfazio.ui.bean.UserFeedbackBean;
import com.vincentfazio.ui.global.GwtGlobals;
import com.vincentfazio.ui.model.ErrorModel;
import com.vincentfazio.ui.model.parser.xml.UserFeedbackXmlParser;

public class ErrorService extends GwtService implements ErrorModel {

    private final String baseUrl;
    private final UserFeedbackXmlParser xmlParser;
    
    public ErrorService(GwtGlobals globals) {
        super();
        this.xmlParser = new UserFeedbackXmlParser();
        baseUrl = globals.getServiceBaseUrl() + "/" + globals.getRole() + "/user_feedback";
    }

    @Override
    public void logError(String errorUrl, String errorDescription) {
        String url = encodeUrl(baseUrl);
        RequestCallback requestCallback = new RequestCallback() {
            
            @Override
            public void onResponseReceived(Request request, Response response) {
                // Error reported... do nothing
            }
            
            @Override
            public void onError(Request request, Throwable exception) {
                // Error reporting failed... do nothing (we probably have bigger problems)
            }
        };
        RequestBuilder builder = createRequestBuilder(RequestBuilder.POST, url);

        builder.setHeader("Content-Type", "application/xml");
        UserFeedbackBean userDetails = new UserFeedbackBean();
        userDetails.setPage(errorUrl);
        userDetails.setComment(errorDescription);
        userDetails.setIsError(true);
        builder.setRequestData(xmlParser.createXml(userDetails )); 
        builder.setCallback(requestCallback);
        
        try {
          builder.send();
        } catch (Exception e) {
            requestCallback.onError(null, e);
        }    
        
    }


}
