package com.vincentfazio.ui.administration.activity;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.vincentfazio.ui.activity.GwtActivity;
import com.vincentfazio.ui.administration.view.VendorDetailsDisplay;
import com.vincentfazio.ui.bean.ErrorBean;
import com.vincentfazio.ui.bean.StatusBean;
import com.vincentfazio.ui.bean.VendorDetailsBean;
import com.vincentfazio.ui.global.Globals;
import com.vincentfazio.ui.model.VendorDetailsModel;
import com.vincentfazio.ui.view.ErrorDisplay;
import com.vincentfazio.ui.view.StatusDisplay;

public class VendorDetailsSaveActivity extends GwtActivity {

    private final Globals globals;
    private final VendorDetailsBean vendorDetails;

    public VendorDetailsSaveActivity(Globals globals, VendorDetailsBean vendorDetails) {
        super();
        this.globals = globals;
        this.vendorDetails = vendorDetails;
    }

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        VendorDetailsModel model = (VendorDetailsModel) globals.getModel(VendorDetailsModel.class);
        model.saveVendor(vendorDetails,
                new AsyncCallback<String>() {
                    public void onSuccess(String result) {
                        StatusDisplay statusDisplay = (StatusDisplay) globals.getStatusDisplay();
                        statusDisplay.handleStatusUpdate(new StatusBean("Supplier Changes Saved"));
                        VendorDetailsDisplay detailsDisplay = (VendorDetailsDisplay) globals.getDisplay(VendorDetailsDisplay.class);
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
