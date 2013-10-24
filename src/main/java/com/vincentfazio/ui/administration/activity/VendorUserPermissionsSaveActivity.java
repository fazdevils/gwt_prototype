package com.vincentfazio.ui.administration.activity;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.vincentfazio.ui.activity.GwtActivity;
import com.vincentfazio.ui.administration.view.VendorUserPermissionsDisplay;
import com.vincentfazio.ui.bean.ErrorBean;
import com.vincentfazio.ui.bean.StatusBean;
import com.vincentfazio.ui.bean.VendorUserPermissionBean;
import com.vincentfazio.ui.global.Globals;
import com.vincentfazio.ui.model.VendorUserPermissionsModel;
import com.vincentfazio.ui.view.ErrorDisplay;
import com.vincentfazio.ui.view.StatusDisplay;

public class VendorUserPermissionsSaveActivity extends GwtActivity {

    private final Globals globals;
    private final String vendorId;
    private final String role;
    private final List<VendorUserPermissionBean> vendorUserPermissions;

    public VendorUserPermissionsSaveActivity(Globals globals, String vendorId, String role, List<VendorUserPermissionBean> vendorUserPermissions) {
        super();
        this.globals = globals;
        this.vendorId = vendorId;
        this.role = role;
        this.vendorUserPermissions = vendorUserPermissions;
    }    

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        List<String> users = new ArrayList<String>();
        
        for (VendorUserPermissionBean vendorUserPermissionBean: vendorUserPermissions) {
            if (vendorUserPermissionBean.hasAccess()) {
                users.add(vendorUserPermissionBean.getUserName());
            }
        }
        
        VendorUserPermissionsModel model = (VendorUserPermissionsModel) globals.getModel(VendorUserPermissionsModel.class);
        model.saveVendorUserPermissions(vendorId, role, users,
                new AsyncCallback<String>() {
                    public void onSuccess(String result) {
                        StatusDisplay statusDisplay = (StatusDisplay) globals.getStatusDisplay();
                        statusDisplay.handleStatusUpdate(new StatusBean("Permission Changes Saved"));
                        VendorUserPermissionsDisplay permissionsDisplay = (VendorUserPermissionsDisplay) globals.getDisplay(VendorUserPermissionsDisplay.class);
                        permissionsDisplay.setHasUnsavedChanges(false);
                    }
                    public void onFailure(Throwable caught) {
                        ErrorDisplay errorDisplay = (ErrorDisplay) globals.getErrorDisplay();
                        errorDisplay.handleError(new ErrorBean(caught.getMessage(), caught));
                    }
                }
             );
    }
    
}
