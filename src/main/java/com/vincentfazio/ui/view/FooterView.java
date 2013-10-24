package com.vincentfazio.ui.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsDate;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

public class FooterView extends Composite implements FooterDisplay {

    private static FooterViewUiBinder uiBinder = GWT.create(FooterViewUiBinder.class);


    interface FooterViewUiBinder extends UiBinder<Widget, FooterView> {}


    public FooterView() {
        initWidget(uiBinder.createAndBindUi(this));
        
        copyright.setHTML("&copy; 2009-" + JsDate.create().getFullYear() + ".  All Rights Reserved");
    }
    
    @UiField
    HTML copyright;


}
