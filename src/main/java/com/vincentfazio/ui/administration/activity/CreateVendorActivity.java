package com.vincentfazio.ui.administration.activity;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.vincentfazio.ui.activity.GwtActivity;
import com.vincentfazio.ui.administration.global.GwtAdminGlobals;
import com.vincentfazio.ui.administration.place.VendorDetailsPlace;
import com.vincentfazio.ui.bean.ErrorBean;
import com.vincentfazio.ui.bean.StatusBean;
import com.vincentfazio.ui.global.Globals;
import com.vincentfazio.ui.model.VendorDetailsModel;
import com.vincentfazio.ui.view.ErrorDisplay;
import com.vincentfazio.ui.view.StatusDisplay;

public class CreateVendorActivity extends GwtActivity {

    private final Globals globals;
    private final String vendorId;
    private final VendorDetailsModel model;

    public CreateVendorActivity(Globals globals, String vendorId) {
        super();
        this.globals = globals;
        this.vendorId = vendorId;        
        this.model = (VendorDetailsModel) globals.getModel(VendorDetailsModel.class);
    }

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        model.createVendor(vendorId,
                new AsyncCallback<String>() {
                    public void onSuccess(String result) {
                        StatusDisplay statusDisplay = (StatusDisplay) globals.getStatusDisplay();
                        statusDisplay.handleStatusUpdate(new StatusBean("Vendor Created"));
                        new VendorListActivity(GwtAdminGlobals.getInstance(), false).start(null, null);
                        globals.gotoPlace(new VendorDetailsPlace(vendorId));
                    }
                    public void onFailure(Throwable caught) {
                        ErrorDisplay errorDisplay = (ErrorDisplay) globals.getErrorDisplay();
                        errorDisplay.handleError(new ErrorBean(caught.getMessage(), caught));
                    }
                }
             );
    }
    
}
