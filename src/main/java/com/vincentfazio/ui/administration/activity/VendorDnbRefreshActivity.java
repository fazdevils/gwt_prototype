package com.vincentfazio.ui.administration.activity;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.vincentfazio.ui.activity.GwtActivity;
import com.vincentfazio.ui.bean.ErrorBean;
import com.vincentfazio.ui.bean.StatusBean;
import com.vincentfazio.ui.global.Globals;
import com.vincentfazio.ui.model.VendorDnbRefreshModel;
import com.vincentfazio.ui.view.ErrorDisplay;
import com.vincentfazio.ui.view.StatusDisplay;

public class VendorDnbRefreshActivity extends GwtActivity {

    private final Globals globals;
    private final String vendorId;

    public VendorDnbRefreshActivity(Globals globals, String vendorId) {
        super();
        this.globals = globals;
        this.vendorId = vendorId;
    }

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        VendorDnbRefreshModel model = (VendorDnbRefreshModel) globals.getModel(VendorDnbRefreshModel.class);
        showBusyCursor();
        model.refresh(vendorId,
                new AsyncCallback<String>() {
                    public void onSuccess(String result) {
                        StatusDisplay statusDisplay = (StatusDisplay) globals.getStatusDisplay();
                        statusDisplay.handleStatusUpdate(new StatusBean("D&B Data Requested"));
                        showDefaultCursor();
                    }
                    public void onFailure(Throwable caught) {
                        ErrorDisplay errorDisplay = (ErrorDisplay) globals.getErrorDisplay();
                        errorDisplay.handleError(new ErrorBean(caught.getMessage(), caught));
                        showDefaultCursor();
                    }
                }
             );
    }
    
}
