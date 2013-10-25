package com.vincentfazio.ui.survey.view.documentation;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class NoDocumentationSelectedView extends Composite {

    private static NoDocumentationSelectedUiBinder uiBinder = GWT.create(NoDocumentationSelectedUiBinder.class);


    interface NoDocumentationSelectedUiBinder extends UiBinder<Widget, NoDocumentationSelectedView> {
    }


    public NoDocumentationSelectedView() {
        initWidget(uiBinder.createAndBindUi(this));
    }
    
}
