package com.vincentfazio.ui.vendor.activity;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.vincentfazio.ui.activity.GwtActivity;
import com.vincentfazio.ui.bean.ErrorBean;
import com.vincentfazio.ui.bean.StatusBean;
import com.vincentfazio.ui.bean.VendorDetailsBean;
import com.vincentfazio.ui.global.GwtGlobals;
import com.vincentfazio.ui.model.VendorDetailsModel;
import com.vincentfazio.ui.vendor.view.VendorDetailsDisplay;
import com.vincentfazio.ui.view.ErrorDisplay;
import com.vincentfazio.ui.view.StatusDisplay;

public class VendorDetailsSaveActivity extends GwtActivity {

    private GwtGlobals globals;
    private VendorDetailsBean vendorDetails;
    
    public VendorDetailsSaveActivity(GwtGlobals gwtGlobals, VendorDetailsBean vendorDetails) {
        this.globals = gwtGlobals;
        this.vendorDetails = vendorDetails;
    }

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        VendorDetailsModel model = (VendorDetailsModel) globals.getModel(VendorDetailsModel.class);
        model.saveVendor(vendorDetails,
                new AsyncCallback<String>() {
                    public void onSuccess(String result) {
                        StatusDisplay statusDisplay = globals.getStatusDisplay();
                        statusDisplay.handleStatusUpdate(new StatusBean("Supplier Changes Saved"));
                        
                        VendorDetailsDisplay detailsDisplay = (VendorDetailsDisplay) globals.getDisplay(VendorDetailsDisplay.class);                        
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
