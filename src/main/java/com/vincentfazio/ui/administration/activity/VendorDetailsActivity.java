package com.vincentfazio.ui.administration.activity;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.vincentfazio.ui.activity.GwtActivity;
import com.vincentfazio.ui.administration.global.GwtAdminGlobals;
import com.vincentfazio.ui.administration.view.VendorDetailsDisplay;
import com.vincentfazio.ui.administration.view.VendorListDisplay;
import com.vincentfazio.ui.administration.view.VendorListDisplay.VendorDeckEnum;
import com.vincentfazio.ui.bean.ErrorBean;
import com.vincentfazio.ui.bean.StatusBean;
import com.vincentfazio.ui.bean.VendorDetailsBean;
import com.vincentfazio.ui.global.Globals;
import com.vincentfazio.ui.model.VendorDetailsModel;
import com.vincentfazio.ui.view.ErrorDisplay;
import com.vincentfazio.ui.view.StatusDisplay;

public class VendorDetailsActivity extends GwtActivity {

    private final Globals globals;
    private final String vendorId;

    public VendorDetailsActivity(Globals globals, String vendorId) {
        super();
        this.globals = globals;
        this.vendorId = vendorId;
    }
    
    
    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {        
        VendorDetailsDisplay vendorDetailsDisplay = (VendorDetailsDisplay) globals.getDisplay(VendorDetailsDisplay.class);
        VendorDetailsBean vendorDetails = vendorDetailsDisplay.getVendorDetails(); 
        if ((null == vendorDetails) || !vendorDetails.getVendorId().equals(vendorId)) {
            vendorDetails = new VendorDetailsBean();
            vendorDetails.setVendorId(vendorId);
            vendorDetailsDisplay.setVendorDetails(vendorDetails);
            
            VendorDetailsModel model = (VendorDetailsModel) globals.getModel(VendorDetailsModel.class);
            model.getVendor(vendorId,
                    new AsyncCallback<VendorDetailsBean>() {
                        public void onSuccess(VendorDetailsBean result) {
                            VendorDetailsDisplay vendorDetailsDisplay = (VendorDetailsDisplay) globals.getDisplay(VendorDetailsDisplay.class);
                            vendorDetailsDisplay.setVendorDetails(result);

                            // initialize the vendor list if it isn't
                            VendorListDisplay vendorListDisplay = (VendorListDisplay) globals.getDisplay(VendorListDisplay.class);
                            if (!vendorListDisplay.isVendorListLoaded()) {
                                new VendorListActivity(GwtAdminGlobals.getInstance(), false).start(null, null);
                            } else {
                                vendorListDisplay.selectVendor(vendorId, false);
                            }
                            
                        }
                        public void onFailure(Throwable caught) {
                            ErrorDisplay errorDisplay = (ErrorDisplay) globals.getErrorDisplay();
                            errorDisplay.handleError(new ErrorBean(caught.getMessage(), caught));
                            VendorListDisplay vendorListDisplay = (VendorListDisplay) globals.getDisplay(VendorListDisplay.class);
                            vendorListDisplay.showView(VendorDeckEnum.NoVendor);
                            if (vendorListDisplay.isVendorListLoaded()) {
                                vendorListDisplay.selectVendor(vendorId, false);                                
                            } else {
                                VendorDetailsBean vendorDetails = new VendorDetailsBean();
                                vendorDetails.setVendorId(vendorId);
                                VendorDetailsDisplay vendorDetailsDisplay = (VendorDetailsDisplay) globals.getDisplay(VendorDetailsDisplay.class);
                                vendorDetailsDisplay.setVendorDetails(vendorDetails);
                                new VendorListActivity(GwtAdminGlobals.getInstance(), false).start(null, null);
                            }
                       }
                    }
                 );
        }
    }
    
    
    @Override
    public String mayStop() {
        VendorDetailsDisplay vendorDetailsDisplay = (VendorDetailsDisplay) globals.getDisplay(VendorDetailsDisplay.class);
        if (vendorDetailsDisplay.hasUnsavedChanges() && vendorDetailsDisplay.isValid()) {
            new VendorDetailsSaveActivity(GwtAdminGlobals.getInstance(), vendorDetailsDisplay.getVendorDetails()).start(null, null);
        } else if (!vendorDetailsDisplay.isValid()) {
            StatusDisplay statusDisplay = (StatusDisplay) globals.getStatusDisplay();
            statusDisplay.handleStatusUpdate(new StatusBean("Changes Discarded"));            
        }
        return null;
    }


}
