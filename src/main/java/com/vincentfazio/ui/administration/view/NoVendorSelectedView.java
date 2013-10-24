package com.vincentfazio.ui.administration.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class NoVendorSelectedView extends Composite {

    private static NoVendorSelectedUiBinder uiBinder = GWT.create(NoVendorSelectedUiBinder.class);


    interface NoVendorSelectedUiBinder extends UiBinder<Widget, NoVendorSelectedView> {
    }


    public NoVendorSelectedView() {
        initWidget(uiBinder.createAndBindUi(this));
    }
    
}
