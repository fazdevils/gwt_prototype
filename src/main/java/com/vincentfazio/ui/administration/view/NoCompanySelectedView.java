package com.vincentfazio.ui.administration.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class NoCompanySelectedView extends Composite {

    private static NoCompanySelectedUiBinder uiBinder = GWT.create(NoCompanySelectedUiBinder.class);


    interface NoCompanySelectedUiBinder extends UiBinder<Widget, NoCompanySelectedView> {
    }


    public NoCompanySelectedView() {
        initWidget(uiBinder.createAndBindUi(this));
    }
    
}
