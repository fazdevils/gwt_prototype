package com.vincentfazio.ui.survey.activity;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.vincentfazio.ui.activity.GwtActivity;
import com.vincentfazio.ui.bean.ErrorBean;
import com.vincentfazio.ui.bean.StatusBean;
import com.vincentfazio.ui.bean.CompanyDetailsBean;
import com.vincentfazio.ui.global.GwtGlobals;
import com.vincentfazio.ui.model.CompanyDetailsModel;
import com.vincentfazio.ui.survey.view.CompanyDetailsDisplay;
import com.vincentfazio.ui.view.ErrorDisplay;
import com.vincentfazio.ui.view.StatusDisplay;

public class CompanyDetailsSaveActivity extends GwtActivity {

    private GwtGlobals globals;
    private CompanyDetailsBean companyDetails;
    
    public CompanyDetailsSaveActivity(GwtGlobals gwtGlobals, CompanyDetailsBean companyDetails) {
        this.globals = gwtGlobals;
        this.companyDetails = companyDetails;
    }

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        CompanyDetailsModel model = (CompanyDetailsModel) globals.getModel(CompanyDetailsModel.class);
        model.saveCompany(companyDetails,
                new AsyncCallback<String>() {
                    public void onSuccess(String result) {
                        StatusDisplay statusDisplay = globals.getStatusDisplay();
                        statusDisplay.handleStatusUpdate(new StatusBean("Company Changes Saved"));
                        
                        CompanyDetailsDisplay detailsDisplay = (CompanyDetailsDisplay) globals.getDisplay(CompanyDetailsDisplay.class);                        
                        detailsDisplay.setHasUnsavedChanges(false);
                    }
                    public void onFailure(Throwable caught) {
                        ErrorDisplay errorDisplay = globals.getErrorDisplay();
                        errorDisplay.handleError(new ErrorBean(caught.getMessage(), caught));
                    }
                }
             );
    }

}
