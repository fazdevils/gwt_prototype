package com.vincentfazio.ui.administration.activity;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.vincentfazio.ui.activity.GwtActivity;
import com.vincentfazio.ui.administration.global.GwtAdminGlobals;
import com.vincentfazio.ui.administration.view.VendorDetailsDisplay;
import com.vincentfazio.ui.bean.ErrorBean;
import com.vincentfazio.ui.bean.StatusBean;
import com.vincentfazio.ui.bean.VendorDetailsBean;
import com.vincentfazio.ui.global.Globals;
import com.vincentfazio.ui.model.VendorDetailsModel;
import com.vincentfazio.ui.view.ErrorDisplay;
import com.vincentfazio.ui.view.StatusDisplay;

public class DeleteVendorActivity extends GwtActivity {

    private final String vendorId;
    private final Globals globals;

    public DeleteVendorActivity(Globals globals, String vendorId) {
        super();
        this.globals = globals;
        this.vendorId = vendorId;
    }

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        VendorDetailsModel model = (VendorDetailsModel) globals.getModel(VendorDetailsModel.class);
        model.deleteVendor(vendorId,
                new AsyncCallback<String>() {
                    public void onSuccess(String result) {
                        StatusDisplay statusDisplay = (StatusDisplay) globals.getStatusDisplay();
                        statusDisplay.handleStatusUpdate(new StatusBean("Supplier Deleted"));
                        VendorDetailsDisplay detailsDisplay = (VendorDetailsDisplay) globals.getDisplay(VendorDetailsDisplay.class);
                        detailsDisplay.setVendorDetails(new VendorDetailsBean());
                        new VendorListActivity(GwtAdminGlobals.getInstance(), true).start(null, null);
                    }
                    public void onFailure(Throwable caught) {
                        ErrorDisplay errorDisplay = (ErrorDisplay) globals.getErrorDisplay();
                        errorDisplay.handleError(new ErrorBean(caught.getMessage(), caught));
                    }
                }
             );
    }
    
}
