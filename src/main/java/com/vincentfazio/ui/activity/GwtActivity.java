package com.vincentfazio.ui.activity;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.RootPanel;

public abstract class GwtActivity extends AbstractActivity {

    public GwtActivity() {
        super();
    }

    protected void showBusyCursor() {
        try {
            DOM.setStyleAttribute(RootPanel.getBodyElement(), "cursor", "wait");
        } catch (Throwable e) {
            // this will happen in unit tests when GWT.create is called
        }
    }
     
    protected void showDefaultCursor() {
        try {
            DOM.setStyleAttribute(RootPanel.getBodyElement(), "cursor", "default");
        } catch (Throwable e) {
            // this will happen in unit tests when GWT.create is called
        }
    }

}