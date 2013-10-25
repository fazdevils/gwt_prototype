package com.vincentfazio.ui.administration.activity;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.vincentfazio.ui.activity.GwtActivity;
import com.vincentfazio.ui.administration.global.GwtAdminGlobals;
import com.vincentfazio.ui.administration.place.CompanyDetailsPlace;
import com.vincentfazio.ui.bean.ErrorBean;
import com.vincentfazio.ui.bean.StatusBean;
import com.vincentfazio.ui.global.Globals;
import com.vincentfazio.ui.model.CompanyDetailsModel;
import com.vincentfazio.ui.view.ErrorDisplay;
import com.vincentfazio.ui.view.StatusDisplay;

public class CreateCompanyActivity extends GwtActivity {

    private final Globals globals;
    private final String companyId;
    private final CompanyDetailsModel model;

    public CreateCompanyActivity(Globals globals, String companyId) {
        super();
        this.globals = globals;
        this.companyId = companyId;        
        this.model = (CompanyDetailsModel) globals.getModel(CompanyDetailsModel.class);
    }

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        model.createCompany(companyId,
                new AsyncCallback<String>() {
                    public void onSuccess(String result) {
                        StatusDisplay statusDisplay = (StatusDisplay) globals.getStatusDisplay();
                        statusDisplay.handleStatusUpdate(new StatusBean("Company Created"));
                        new CompanyListActivity(GwtAdminGlobals.getInstance(), false).start(null, null);
                        globals.gotoPlace(new CompanyDetailsPlace(companyId));
                    }
                    public void onFailure(Throwable caught) {
                        ErrorDisplay errorDisplay = (ErrorDisplay) globals.getErrorDisplay();
                        errorDisplay.handleError(new ErrorBean(caught.getMessage(), caught));
                    }
                }
             );
    }
    
}
