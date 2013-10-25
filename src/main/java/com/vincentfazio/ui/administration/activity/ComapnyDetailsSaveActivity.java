package com.vincentfazio.ui.administration.activity;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.vincentfazio.ui.activity.GwtActivity;
import com.vincentfazio.ui.administration.view.CompanyDetailsDisplay;
import com.vincentfazio.ui.bean.ErrorBean;
import com.vincentfazio.ui.bean.StatusBean;
import com.vincentfazio.ui.bean.CompanyDetailsBean;
import com.vincentfazio.ui.global.Globals;
import com.vincentfazio.ui.model.CompanyDetailsModel;
import com.vincentfazio.ui.view.ErrorDisplay;
import com.vincentfazio.ui.view.StatusDisplay;

public class ComapnyDetailsSaveActivity extends GwtActivity {

    private final Globals globals;
    private final CompanyDetailsBean companyDetails;

    public ComapnyDetailsSaveActivity(Globals globals, CompanyDetailsBean companyDetails) {
        super();
        this.globals = globals;
        this.companyDetails = companyDetails;
    }

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        CompanyDetailsModel model = (CompanyDetailsModel) globals.getModel(CompanyDetailsModel.class);
        model.saveCompany(companyDetails,
                new AsyncCallback<String>() {
                    public void onSuccess(String result) {
                        StatusDisplay statusDisplay = (StatusDisplay) globals.getStatusDisplay();
                        statusDisplay.handleStatusUpdate(new StatusBean("Company Changes Saved"));
                        CompanyDetailsDisplay detailsDisplay = (CompanyDetailsDisplay) globals.getDisplay(CompanyDetailsDisplay.class);
                        detailsDisplay.setHasUnsavedChanges(false);
                    }
                    public void onFailure(Throwable caught) {
                        ErrorDisplay errorDisplay = (ErrorDisplay) globals.getErrorDisplay();
                        errorDisplay.handleError(new ErrorBean(caught.getMessage(), caught));
                    }
                }
             );
    }
    
}
