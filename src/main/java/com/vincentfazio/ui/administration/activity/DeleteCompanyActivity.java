package com.vincentfazio.ui.administration.activity;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.vincentfazio.ui.activity.GwtActivity;
import com.vincentfazio.ui.administration.global.GwtAdminGlobals;
import com.vincentfazio.ui.administration.view.CompanyDetailsDisplay;
import com.vincentfazio.ui.bean.ErrorBean;
import com.vincentfazio.ui.bean.StatusBean;
import com.vincentfazio.ui.bean.CompanyDetailsBean;
import com.vincentfazio.ui.global.Globals;
import com.vincentfazio.ui.model.CompanyDetailsModel;
import com.vincentfazio.ui.view.ErrorDisplay;
import com.vincentfazio.ui.view.StatusDisplay;

public class DeleteCompanyActivity extends GwtActivity {

    private final String companyId;
    private final Globals globals;

    public DeleteCompanyActivity(Globals globals, String companyId) {
        super();
        this.globals = globals;
        this.companyId = companyId;
    }

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        CompanyDetailsModel model = (CompanyDetailsModel) globals.getModel(CompanyDetailsModel.class);
        model.deleteCompany(companyId,
                new AsyncCallback<String>() {
                    public void onSuccess(String result) {
                        StatusDisplay statusDisplay = (StatusDisplay) globals.getStatusDisplay();
                        statusDisplay.handleStatusUpdate(new StatusBean("Company Deleted"));
                        CompanyDetailsDisplay detailsDisplay = (CompanyDetailsDisplay) globals.getDisplay(CompanyDetailsDisplay.class);
                        detailsDisplay.setCompanyDetails(new CompanyDetailsBean());
                        new CompanyListActivity(GwtAdminGlobals.getInstance(), true).start(null, null);
                    }
                    public void onFailure(Throwable caught) {
                        ErrorDisplay errorDisplay = (ErrorDisplay) globals.getErrorDisplay();
                        errorDisplay.handleError(new ErrorBean(caught.getMessage(), caught));
                    }
                }
             );
    }
    
}
