package com.vincentfazio.ui.vendor.activity;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.vincentfazio.ui.activity.GwtActivity;
import com.vincentfazio.ui.bean.ErrorBean;
import com.vincentfazio.ui.bean.StatusBean;
import com.vincentfazio.ui.bean.VendorDetailsBean;
import com.vincentfazio.ui.global.Globals;
import com.vincentfazio.ui.model.VendorDetailsModel;
import com.vincentfazio.ui.vendor.global.GwtVendorGlobals;
import com.vincentfazio.ui.vendor.view.VendorDetailsDisplay;
import com.vincentfazio.ui.view.ErrorDisplay;
import com.vincentfazio.ui.view.StatusDisplay;

public class VendorDetailsActivity extends GwtActivity {

    private String vendorId;
    private Globals globals;
    
    
    public VendorDetailsActivity(String vendorId, Globals globals) {
        this.vendorId = vendorId;
        this.globals = globals;
    }

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        final VendorDetailsDisplay vendorDetailsDisplay = (VendorDetailsDisplay) globals.getDisplay(VendorDetailsDisplay.class);
        VendorDetailsBean vendorDetails = new VendorDetailsBean();
        vendorDetails.setVendorId(vendorId);
        vendorDetailsDisplay.setVendorDetails(vendorDetails);
        
        VendorDetailsModel model = (VendorDetailsModel) globals.getModel(VendorDetailsModel.class);
        model.getVendor(vendorId,
                new AsyncCallback<VendorDetailsBean>() {
                    public void onSuccess(VendorDetailsBean result) {
                        vendorDetailsDisplay.setVendorDetails(result);
                    }
                    public void onFailure(Throwable caught) {
                        ErrorDisplay errorDisplay = globals.getErrorDisplay();
                        errorDisplay.handleError(new ErrorBean(caught.getMessage(), caught));
                        VendorDetailsBean vendorDetails = new VendorDetailsBean();
                        vendorDetails.setVendorId(vendorId);
                        vendorDetailsDisplay.setVendorDetails(vendorDetails);
                   }
                }
             );
    }
    
    
    @Override
    public String mayStop() {
        VendorDetailsDisplay vendorDetailsDisplay = (VendorDetailsDisplay) globals.getDisplay(VendorDetailsDisplay.class);
        if (vendorDetailsDisplay.hasUnsavedChanges() && vendorDetailsDisplay.isValid()) {
            new VendorDetailsSaveActivity(GwtVendorGlobals.getInstance(), vendorDetailsDisplay.getVendorDetails()).start(null, null);
        } else if (!vendorDetailsDisplay.isValid()) {
            StatusDisplay statusDisplay = (StatusDisplay) globals.getStatusDisplay();
            statusDisplay.handleStatusUpdate(new StatusBean("Changes Discarded"));            
        }
        return null;
    }

}
