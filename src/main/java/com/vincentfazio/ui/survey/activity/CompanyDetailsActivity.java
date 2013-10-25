package com.vincentfazio.ui.survey.activity;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.vincentfazio.ui.activity.GwtActivity;
import com.vincentfazio.ui.bean.ErrorBean;
import com.vincentfazio.ui.bean.StatusBean;
import com.vincentfazio.ui.bean.CompanyDetailsBean;
import com.vincentfazio.ui.global.Globals;
import com.vincentfazio.ui.model.CompanyDetailsModel;
import com.vincentfazio.ui.survey.global.GwtSurveyGlobals;
import com.vincentfazio.ui.survey.view.CompanyDetailsDisplay;
import com.vincentfazio.ui.view.ErrorDisplay;
import com.vincentfazio.ui.view.StatusDisplay;

public class CompanyDetailsActivity extends GwtActivity {

    private String companyId;
    private Globals globals;
    
    
    public CompanyDetailsActivity(String companyId, Globals globals) {
        this.companyId = companyId;
        this.globals = globals;
    }

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        final CompanyDetailsDisplay companyDetailsDisplay = (CompanyDetailsDisplay) globals.getDisplay(CompanyDetailsDisplay.class);
        CompanyDetailsBean companyDetails = new CompanyDetailsBean();
        companyDetails.setCompanyId(companyId);
        companyDetailsDisplay.setCompanyDetails(companyDetails);
        
        CompanyDetailsModel model = (CompanyDetailsModel) globals.getModel(CompanyDetailsModel.class);
        model.getCompany(companyId,
                new AsyncCallback<CompanyDetailsBean>() {
                    public void onSuccess(CompanyDetailsBean result) {
                        companyDetailsDisplay.setCompanyDetails(result);
                    }
                    public void onFailure(Throwable caught) {
                        ErrorDisplay errorDisplay = globals.getErrorDisplay();
                        errorDisplay.handleError(new ErrorBean(caught.getMessage(), caught));
                        CompanyDetailsBean companyDetails = new CompanyDetailsBean();
                        companyDetails.setCompanyId(companyId);
                        companyDetailsDisplay.setCompanyDetails(companyDetails);
                   }
                }
             );
    }
    
    
    @Override
    public String mayStop() {
        CompanyDetailsDisplay companyDetailsDisplay = (CompanyDetailsDisplay) globals.getDisplay(CompanyDetailsDisplay.class);
        if (companyDetailsDisplay.hasUnsavedChanges() && companyDetailsDisplay.isValid()) {
            new CompanyDetailsSaveActivity(GwtSurveyGlobals.getInstance(), companyDetailsDisplay.getCompanyDetails()).start(null, null);
        } else if (!companyDetailsDisplay.isValid()) {
            StatusDisplay statusDisplay = (StatusDisplay) globals.getStatusDisplay();
            statusDisplay.handleStatusUpdate(new StatusBean("Changes Discarded"));            
        }
        return null;
    }

}
