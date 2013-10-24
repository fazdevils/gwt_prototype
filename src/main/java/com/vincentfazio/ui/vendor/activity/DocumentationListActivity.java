package com.vincentfazio.ui.vendor.activity;

import java.util.Collection;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.vincentfazio.ui.activity.GwtActivity;
import com.vincentfazio.ui.bean.DocumentationBean;
import com.vincentfazio.ui.bean.ErrorBean;
import com.vincentfazio.ui.bean.StatusBean;
import com.vincentfazio.ui.global.Globals;
import com.vincentfazio.ui.model.DocumentationModel;
import com.vincentfazio.ui.vendor.global.GwtVendorGlobals;
import com.vincentfazio.ui.vendor.view.SecuritySurveyQuestionDisplay;
import com.vincentfazio.ui.vendor.view.documentation.DocumentationDetailsDisplay;
import com.vincentfazio.ui.vendor.view.documentation.DocumentationListDisplay;
import com.vincentfazio.ui.view.ErrorDisplay;
import com.vincentfazio.ui.view.StatusDisplay;

public class DocumentationListActivity extends GwtActivity {

    private String vendorId;
    private Globals globals;
    
    
    public DocumentationListActivity(String vendorId, Globals globals) {
        this.vendorId = vendorId;
        this.globals = globals;
    }

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        DocumentationModel model = (DocumentationModel) globals.getModel(DocumentationModel.class);
        final DocumentationListDisplay display = (DocumentationListDisplay) globals.getDisplay(DocumentationListDisplay.class);
        
        String currentVendorId = display.getVendorId();
		if ((null != currentVendorId) && (currentVendorId.equals(vendorId))) { // the documentation list should already be loaded for this vendor so skip the WS call
            DocumentationDetailsDisplay detailsDisplay = (DocumentationDetailsDisplay) globals.getDisplay(DocumentationDetailsDisplay.class);
            DocumentationBean documentation = detailsDisplay.getDocumentationDetails();
            new DocumentationDetailsActivity(vendorId, documentation, globals).start(null, null);            
        } else {
            model.getDocumentationList(vendorId, new AsyncCallback<Collection<DocumentationBean>>() {
                
                @Override
                public void onSuccess(Collection<DocumentationBean> result) {
                    // update the display with the set of questions
                    display.setVendorId(vendorId);
                    display.setDocumentationList(result);

                    DocumentationDetailsDisplay detailsDisplay = (DocumentationDetailsDisplay) globals.getDisplay(DocumentationDetailsDisplay.class);
                    DocumentationBean documentation = detailsDisplay.getDocumentationDetails();
                    if (null == documentation) {
                    	documentation = result.iterator().next();
                    }
                    new DocumentationDetailsActivity(vendorId, documentation, globals).start(null, null);
                }
                
                @Override
                public void onFailure(Throwable caught) {
                    ErrorDisplay errorDisplay = globals.getErrorDisplay();
                    errorDisplay.handleError(new ErrorBean(caught.getMessage(), caught));
                }
            });
        }
    }

    @Override
    public String mayStop() {
        SecuritySurveyQuestionDisplay questionDisplay = (SecuritySurveyQuestionDisplay) globals.getDisplay(SecuritySurveyQuestionDisplay.class);
        if (questionDisplay.hasUnsavedChanges() && questionDisplay.isValid()) {
            new SecuritySurveyQuestionSaveActivity(vendorId, questionDisplay.getQuestion(), GwtVendorGlobals.getInstance()).start(null, null);
        } else if (!questionDisplay.isValid()) {
            StatusDisplay statusDisplay = (StatusDisplay) globals.getStatusDisplay();
            statusDisplay.handleStatusUpdate(new StatusBean("Changes Discarded"));            
        }
        return null;
    }

}
