package com.vincentfazio.ui.model.web_service;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.vincentfazio.ui.model.ErrorModel;
import com.vincentfazio.ui.model.parser.xml.XmlParser;

public class MissingIsOkRequestCallback<T> extends GwtRequestCallback<T> {

    public MissingIsOkRequestCallback(String url, AsyncCallback<T> asyncCallback, XmlParser<T> xmlParser, ErrorModel errorModel) {
        super(url, asyncCallback, xmlParser, errorModel);
    }

    public void onResponseReceived(Request request, Response response) {
        if (200 == response.getStatusCode()) {
            asyncCallback.onSuccess(xmlParser.parse(response.getText()));                
        } else if (404 == response.getStatusCode()) { // 404 is OK - it means the question has not been answered
            asyncCallback.onSuccess(null);                
        } else if (0 == response.getStatusCode()) {
            Window.Location.reload(); // likely that the session timed out and we're being redirected - reload the page to go to the login page with the current page as the redirect
            //asyncCallback.onFailure(new Throwable("Violation of Same Origin Policy (SOP)\n" + url));
        } else {
            asyncCallback.onFailure(new Throwable(/*response.getStatusCode() + " - " +*/ response.getStatusText() /*+ "\n" + url*/));
            errorModel.logError(url, response.getStatusCode() + " - " + response.getStatusText());
        }
    }
}
