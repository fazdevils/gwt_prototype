package com.vincentfazio.ui.model.web_service;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.vincentfazio.ui.model.ErrorModel;
import com.vincentfazio.ui.model.parser.xml.XmlParser;

public class GwtStatusOnlyRequestCallback extends GwtRequestCallback<String> {

    public GwtStatusOnlyRequestCallback(String url, AsyncCallback<String> asyncCallback, String successMessage, ErrorModel errorModel) {
        super(url, asyncCallback, new XmlGwtStatusOnlyRequestParser(successMessage), errorModel);
    }

    private static class XmlGwtStatusOnlyRequestParser extends XmlParser<String> {
        private String successMessage = null;

        
        public XmlGwtStatusOnlyRequestParser(String successMessage) {
            super();
            this.successMessage = successMessage;
        }

        @Override
        public String parse(String xml) {
            return successMessage;
        }

        @Override
        public String createXml(String bean) {
            throw new UnsupportedOperationException();
        }
    }
}
