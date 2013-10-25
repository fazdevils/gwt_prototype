package com.vincentfazio.ui.survey.activity;

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
import com.vincentfazio.ui.survey.global.GwtSurveyGlobals;
import com.vincentfazio.ui.survey.view.SecuritySurveyQuestionDisplay;
import com.vincentfazio.ui.survey.view.documentation.DocumentationDetailsDisplay;
import com.vincentfazio.ui.survey.view.documentation.DocumentationListDisplay;
import com.vincentfazio.ui.view.ErrorDisplay;
import com.vincentfazio.ui.view.StatusDisplay;

public class DocumentationListActivity extends GwtActivity {

    private String companyId;
    private Globals globals;
    
    
    public DocumentationListActivity(String companyId, Globals globals) {
        this.companyId = companyId;
        this.globals = globals;
    }

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        DocumentationModel model = (DocumentationModel) globals.getModel(DocumentationModel.class);
        final DocumentationListDisplay display = (DocumentationListDisplay) globals.getDisplay(DocumentationListDisplay.class);
        
        String currentCompanyId = display.getCompanyId();
		if ((null != currentCompanyId) && (currentCompanyId.equals(companyId))) { // the documentation list should already be loaded for this company so skip the WS call
            DocumentationDetailsDisplay detailsDisplay = (DocumentationDetailsDisplay) globals.getDisplay(DocumentationDetailsDisplay.class);
            DocumentationBean documentation = detailsDisplay.getDocumentationDetails();
            new DocumentationDetailsActivity(companyId, documentation, globals).start(null, null);            
        } else {
            model.getDocumentationList(companyId, new AsyncCallback<Collection<DocumentationBean>>() {
                
                @Override
                public void onSuccess(Collection<DocumentationBean> result) {
                    // update the display with the set of questions
                    display.setCompanyId(companyId);
                    display.setDocumentationList(result);

                    DocumentationDetailsDisplay detailsDisplay = (DocumentationDetailsDisplay) globals.getDisplay(DocumentationDetailsDisplay.class);
                    DocumentationBean documentation = detailsDisplay.getDocumentationDetails();
                    if (null == documentation) {
                    	documentation = result.iterator().next();
                    }
                    new DocumentationDetailsActivity(companyId, documentation, globals).start(null, null);
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
            new SecuritySurveyQuestionSaveActivity(companyId, questionDisplay.getQuestion(), GwtSurveyGlobals.getInstance()).start(null, null);
        } else if (!questionDisplay.isValid()) {
            StatusDisplay statusDisplay = (StatusDisplay) globals.getStatusDisplay();
            statusDisplay.handleStatusUpdate(new StatusBean("Changes Discarded"));            
        }
        return null;
    }

}
