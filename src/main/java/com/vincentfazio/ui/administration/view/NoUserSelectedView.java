package com.vincentfazio.ui.administration.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class NoUserSelectedView extends Composite {

    private static NoUserSelectedUiBinder uiBinder = GWT.create(NoUserSelectedUiBinder.class);


    interface NoUserSelectedUiBinder extends UiBinder<Widget, NoUserSelectedView> {
    }


    public NoUserSelectedView() {
        initWidget(uiBinder.createAndBindUi(this));
    }
    
}
