package com.vincentfazio.ui.model.web_service;

import java.util.Date;

import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestBuilder.Method;
import com.google.gwt.http.client.URL;

public abstract class GwtService {

    protected String encodeUrl(String url) {
        return URL.encode(url + "?ts=" + new Date().getTime());
    }

    protected RequestBuilder createRequestBuilder(Method method, String url) {
        RequestBuilder builder = new RequestBuilder(method, url);
        return builder;
    }
        
}
