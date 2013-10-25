package com.vincentfazio.ui.administration.activity;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.vincentfazio.ui.activity.GwtActivity;
import com.vincentfazio.ui.administration.global.GwtAdminGlobals;
import com.vincentfazio.ui.administration.view.CompanyDetailsDisplay;
import com.vincentfazio.ui.administration.view.CompanyListDisplay;
import com.vincentfazio.ui.administration.view.CompanyListDisplay.CompanyDeckEnum;
import com.vincentfazio.ui.bean.ErrorBean;
import com.vincentfazio.ui.bean.StatusBean;
import com.vincentfazio.ui.bean.CompanyDetailsBean;
import com.vincentfazio.ui.global.Globals;
import com.vincentfazio.ui.model.CompanyDetailsModel;
import com.vincentfazio.ui.view.ErrorDisplay;
import com.vincentfazio.ui.view.StatusDisplay;

public class CompanyDetailsActivity extends GwtActivity {

    private final Globals globals;
    private final String companyId;

    public CompanyDetailsActivity(Globals globals, String companyId) {
        super();
        this.globals = globals;
        this.companyId = companyId;
    }
    
    
    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {        
        CompanyDetailsDisplay companyDetailsDisplay = (CompanyDetailsDisplay) globals.getDisplay(CompanyDetailsDisplay.class);
        CompanyDetailsBean companyDetails = companyDetailsDisplay.getCompanyDetails(); 
        if ((null == companyDetails) || !companyDetails.getCompanyId().equals(companyId)) {
            companyDetails = new CompanyDetailsBean();
            companyDetails.setCompanyId(companyId);
            companyDetailsDisplay.setCompanyDetails(companyDetails);
            
            CompanyDetailsModel model = (CompanyDetailsModel) globals.getModel(CompanyDetailsModel.class);
            model.getCompany(companyId,
                    new AsyncCallback<CompanyDetailsBean>() {
                        public void onSuccess(CompanyDetailsBean result) {
                            CompanyDetailsDisplay companyDetailsDisplay = (CompanyDetailsDisplay) globals.getDisplay(CompanyDetailsDisplay.class);
                            companyDetailsDisplay.setCompanyDetails(result);

                            // initialize the company list if it isn't
                            CompanyListDisplay companyListDisplay = (CompanyListDisplay) globals.getDisplay(CompanyListDisplay.class);
                            if (!companyListDisplay.isCompanyListLoaded()) {
                                new CompanyListActivity(GwtAdminGlobals.getInstance(), false).start(null, null);
                            } else {
                                companyListDisplay.selectCompany(companyId, false);
                            }
                            
                        }
                        public void onFailure(Throwable caught) {
                            ErrorDisplay errorDisplay = (ErrorDisplay) globals.getErrorDisplay();
                            errorDisplay.handleError(new ErrorBean(caught.getMessage(), caught));
                            CompanyListDisplay companyListDisplay = (CompanyListDisplay) globals.getDisplay(CompanyListDisplay.class);
                            companyListDisplay.showView(CompanyDeckEnum.NoCompany);
                            if (companyListDisplay.isCompanyListLoaded()) {
                                companyListDisplay.selectCompany(companyId, false);                                
                            } else {
                                CompanyDetailsBean companyDetails = new CompanyDetailsBean();
                                companyDetails.setCompanyId(companyId);
                                CompanyDetailsDisplay companyDetailsDisplay = (CompanyDetailsDisplay) globals.getDisplay(CompanyDetailsDisplay.class);
                                companyDetailsDisplay.setCompanyDetails(companyDetails);
                                new CompanyListActivity(GwtAdminGlobals.getInstance(), false).start(null, null);
                            }
                       }
                    }
                 );
        }
    }
    
    
    @Override
    public String mayStop() {
        CompanyDetailsDisplay companyDetailsDisplay = (CompanyDetailsDisplay) globals.getDisplay(CompanyDetailsDisplay.class);
        if (companyDetailsDisplay.hasUnsavedChanges() && companyDetailsDisplay.isValid()) {
            new ComapnyDetailsSaveActivity(GwtAdminGlobals.getInstance(), companyDetailsDisplay.getCompanyDetails()).start(null, null);
        } else if (!companyDetailsDisplay.isValid()) {
            StatusDisplay statusDisplay = (StatusDisplay) globals.getStatusDisplay();
            statusDisplay.handleStatusUpdate(new StatusBean("Changes Discarded"));            
        }
        return null;
    }


}
