package com.vincentfazio.ui.administration.activity;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.vincentfazio.ui.activity.GwtActivity;
import com.vincentfazio.ui.administration.view.UserPermissionsDisplay;
import com.vincentfazio.ui.bean.ErrorBean;
import com.vincentfazio.ui.bean.StatusBean;
import com.vincentfazio.ui.bean.VendorPermissionBean;
import com.vincentfazio.ui.global.Globals;
import com.vincentfazio.ui.model.UserPermissionsModel;
import com.vincentfazio.ui.view.ErrorDisplay;
import com.vincentfazio.ui.view.StatusDisplay;

public class UserPermissionsSaveActivity extends GwtActivity {

    private final Globals globals;
    private final String userId;
    private final String role;
    private final List<VendorPermissionBean> vendorPermissions;

    public UserPermissionsSaveActivity(Globals globals, String userId, String role, List<VendorPermissionBean> vendorPermissions) {
        super();
        this.globals = globals;
        this.userId = userId;
        this.role = role;
        this.vendorPermissions = vendorPermissions;
    }

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        List<String> vendors = new ArrayList<String>();
        
        for (VendorPermissionBean vendorPermissionBean: vendorPermissions) {
            if (vendorPermissionBean.hasAccess()) {
                vendors.add(vendorPermissionBean.getVendorName());
            }
        }
        
        UserPermissionsModel model = (UserPermissionsModel) globals.getModel(UserPermissionsModel.class);
        model.saveUserPermissions(userId, role, vendors,
                new AsyncCallback<String>() {
                    public void onSuccess(String result) {
                        StatusDisplay statusDisplay = (StatusDisplay) globals.getStatusDisplay();
                        statusDisplay.handleStatusUpdate(new StatusBean("Permission Changes Saved"));
                        UserPermissionsDisplay permissionsDisplay = (UserPermissionsDisplay) globals.getDisplay(UserPermissionsDisplay.class);
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
