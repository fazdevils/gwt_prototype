package com.vincentfazio.ui.vendor.activity;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.vincentfazio.ui.activity.GwtActivity;
import com.vincentfazio.ui.bean.DocumentationBean;
import com.vincentfazio.ui.bean.ErrorBean;
import com.vincentfazio.ui.global.Globals;
import com.vincentfazio.ui.model.DocumentationModel;
import com.vincentfazio.ui.vendor.global.GwtVendorGlobals;
import com.vincentfazio.ui.vendor.view.documentation.DocumentationDetailsDisplay;
import com.vincentfazio.ui.vendor.view.documentation.DocumentationListDisplay;
import com.vincentfazio.ui.view.ErrorDisplay;

public class DocumentationDetailsActivity extends GwtActivity {

    private String vendorId;
    private DocumentationBean documentation;
    private Globals globals;
    
    
    public DocumentationDetailsActivity(String vendorId, DocumentationBean documentation, Globals globals) {
        this.vendorId = vendorId;
        this.documentation = documentation;
        this.globals = globals;
    }

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        DocumentationModel model = (DocumentationModel) globals.getModel(DocumentationModel.class);
        model.getDocumentation(vendorId, documentation, new AsyncCallback<DocumentationBean>() {
            
            @Override
            public void onSuccess(DocumentationBean result) {
                // update the display with the set of documentation
                DocumentationDetailsDisplay display = (DocumentationDetailsDisplay) globals.getDisplay(DocumentationDetailsDisplay.class);
                display.setVendorId(vendorId);
                display.setDocumentationDetails(result);

                // initialize the documentation list if it isn't
                DocumentationListDisplay documentationListDisplay = (DocumentationListDisplay) globals.getDisplay(DocumentationListDisplay.class);
                if (!documentationListDisplay.isDocumentationListLoaded()) {
                    new DocumentationListActivity(vendorId, GwtVendorGlobals.getInstance()).start(null, null);
                } else {
                	documentationListDisplay.selectDocumentation(documentation.getDocumentationName(), false);
                }
                
            }
            
            @Override
            public void onFailure(Throwable caught) {
                ErrorDisplay errorDisplay = globals.getErrorDisplay();
                errorDisplay.handleError(new ErrorBean(caught.getMessage(), caught));
            }
        });
    }


}
