package com.vincentfazio.ui.administration.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.vincentfazio.ui.activity.GwtActivity;
import com.vincentfazio.ui.administration.global.GwtAdminGlobals;
import com.vincentfazio.ui.administration.view.UserListDisplay;
import com.vincentfazio.ui.administration.view.VendorUserPermissionsDisplay;
import com.vincentfazio.ui.bean.ErrorBean;
import com.vincentfazio.ui.bean.VendorUserPermissionBean;
import com.vincentfazio.ui.global.Globals;
import com.vincentfazio.ui.model.UserListModel;
import com.vincentfazio.ui.model.VendorUserPermissionsModel;
import com.vincentfazio.ui.view.ErrorDisplay;

public class VendorUserPermissionsActivity extends GwtActivity {

    private final Globals globals;
    private final String vendorId;
    private final String role;

    public VendorUserPermissionsActivity(Globals globals, String vendorId, String role) {
        super();
        this.globals = globals;
        this.vendorId = vendorId;
        this.role = role;
    }

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        UserListModel userListModel = (UserListModel) globals.getModel(UserListModel.class);
        userListModel.getUserList(role,
                new AsyncCallback<ArrayList<String>>() {
                    public void onSuccess(ArrayList<String> result) {
                        VendorUserPermissionsDisplay permissionsDisplay = (VendorUserPermissionsDisplay) globals.getDisplay(VendorUserPermissionsDisplay.class);
                        permissionsDisplay.setUserList(result);
                        loadUserPermissionsData();
                    }
                    public void onFailure(Throwable caught) {
                        ErrorDisplay errorDisplay = (ErrorDisplay) globals.getErrorDisplay();
                        errorDisplay.handleError(new ErrorBean(caught.getMessage(), caught));
                    }
                }
             );
    }

    private void loadUserPermissionsData() {
        VendorUserPermissionsDisplay permissionsDisplay = (VendorUserPermissionsDisplay) globals.getDisplay(VendorUserPermissionsDisplay.class);
        if (!permissionsDisplay.getVendorId().equals(vendorId) || !permissionsDisplay.getUserRole().equals(role)) {
            permissionsDisplay.setVendorId(vendorId);
            VendorUserPermissionsModel model = (VendorUserPermissionsModel) globals.getModel(VendorUserPermissionsModel.class);
            model.getVendorUserPermissions(vendorId, role,
                    new AsyncCallback<List<String>>() {
                        public void onSuccess(List<String> result) {
                            VendorUserPermissionsDisplay permissionsDisplay = (VendorUserPermissionsDisplay) globals.getDisplay(VendorUserPermissionsDisplay.class);
                            permissionsDisplay.setVendorId(vendorId);
                            permissionsDisplay.setUserRole(role);
                            permissionsDisplay.setVendorUserPermissionList(getUserPermissionsList(permissionsDisplay.getUserList(), result));
                            
                            // initialize the user list if it isn't
                            UserListDisplay userListDisplay = (UserListDisplay) globals.getDisplay(UserListDisplay.class);
                            if (!userListDisplay.isUserListLoaded()) {
                                new UserListActivity(GwtAdminGlobals.getInstance(), false).start(null, null);
                            } else {
                                userListDisplay.selectUser(vendorId, false);
                            }
                        }
                        
                        public void onFailure(Throwable caught) {
                            ErrorDisplay errorDisplay = (ErrorDisplay) globals.getErrorDisplay();
                            errorDisplay.handleError(new ErrorBean(caught.getMessage(), caught));
                        }
                        
                        private List<VendorUserPermissionBean> getUserPermissionsList(List<String> userList, List<String> userAccess) {
                            ArrayList<VendorUserPermissionBean> vendorUserPermissionsList = new ArrayList<VendorUserPermissionBean>();
                            TreeSet<String> vendorUserAccessSet = new TreeSet<String>(userAccess);
                            
                            for (String userName: userList) {
                                VendorUserPermissionBean vendorUserPermissionBean = new VendorUserPermissionBean();
                                vendorUserPermissionBean.setUserName(userName);
                                vendorUserPermissionBean.setAccess(vendorUserAccessSet.contains(userName));
                                vendorUserPermissionsList.add(vendorUserPermissionBean);
                            }
                            
                            return vendorUserPermissionsList;
                        }
                    }
             );
        }
    }
    
    @Override
    public String mayStop() {
        VendorUserPermissionsDisplay permissionsDisplay = (VendorUserPermissionsDisplay) globals.getDisplay(VendorUserPermissionsDisplay.class);
        if (permissionsDisplay.hasUnsavedChanges()) {
            new VendorUserPermissionsSaveActivity(GwtAdminGlobals.getInstance(), vendorId, role, permissionsDisplay.getVendorUserPermissionList()).start(null, null);
        }
        return null;
    }

}
