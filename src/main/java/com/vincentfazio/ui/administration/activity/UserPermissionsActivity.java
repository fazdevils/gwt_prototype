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
import com.vincentfazio.ui.administration.view.UserPermissionsDisplay;
import com.vincentfazio.ui.bean.ErrorBean;
import com.vincentfazio.ui.bean.VendorPermissionBean;
import com.vincentfazio.ui.global.Globals;
import com.vincentfazio.ui.model.UserPermissionsModel;
import com.vincentfazio.ui.model.VendorListModel;
import com.vincentfazio.ui.view.ErrorDisplay;

public class UserPermissionsActivity extends GwtActivity {

    private final Globals globals;
    private final String userId;
    private final String role;

    public UserPermissionsActivity(Globals globals, String userId, String role) {
        super();
        this.globals = globals;
        this.userId = userId;
        this.role = role;
    }

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        UserPermissionsDisplay permissionsDisplay = (UserPermissionsDisplay) globals.getDisplay(UserPermissionsDisplay.class);
        if (permissionsDisplay.isVendorListLoaded()) {  
            loadUserPermissionsData();            
        } else {
            VendorListModel vendorListModel = (VendorListModel) globals.getModel(VendorListModel.class);
            vendorListModel.getVendorList(  // normally this would get loaded when the vendor list display is loaded
                    new AsyncCallback<ArrayList<String>>() {
                        public void onSuccess(ArrayList<String> result) {
                            UserPermissionsDisplay permissionsDisplay = (UserPermissionsDisplay) globals.getDisplay(UserPermissionsDisplay.class);
                            permissionsDisplay.setVendorList(result);
                            loadUserPermissionsData();
                        }
                        public void onFailure(Throwable caught) {
                            ErrorDisplay errorDisplay = (ErrorDisplay) globals.getErrorDisplay();
                            errorDisplay.handleError(new ErrorBean(caught.getMessage(), caught));
                        }
                    }
                 );
        }
    }

    private void loadUserPermissionsData() {
        UserPermissionsDisplay permissionsDisplay = (UserPermissionsDisplay) globals.getDisplay(UserPermissionsDisplay.class);
        if (!permissionsDisplay.getUserId().equals(userId) || !permissionsDisplay.getUserRole().equals(role)) {
            permissionsDisplay.setUserId(userId);
            UserPermissionsModel model = (UserPermissionsModel) globals.getModel(UserPermissionsModel.class);
            model.getUserPermissions(userId, role,
                    new AsyncCallback<List<String>>() {
                        public void onSuccess(List<String> result) {
                            UserPermissionsDisplay permissionsDisplay = (UserPermissionsDisplay) globals.getDisplay(UserPermissionsDisplay.class);
                            permissionsDisplay.setUserId(userId);
                            permissionsDisplay.setUserRole(role);
                            permissionsDisplay.setVendorsPermissionList(getUserPermissionsList(permissionsDisplay.getVendorList(), result));
                            
                            // initialize the user list if it isn't
                            UserListDisplay userListDisplay = (UserListDisplay) globals.getDisplay(UserListDisplay.class);
                            if (!userListDisplay.isUserListLoaded()) {
                                new UserListActivity(GwtAdminGlobals.getInstance(), false).start(null, null);
                            } else {
                                userListDisplay.selectUser(userId, false);
                            }
                        }
                        
                        public void onFailure(Throwable caught) {
                            ErrorDisplay errorDisplay = (ErrorDisplay) globals.getErrorDisplay();
                            errorDisplay.handleError(new ErrorBean(caught.getMessage(), caught));
                        }
                        
                        private List<VendorPermissionBean> getUserPermissionsList(List<String> vendorList, List<String> vendorAccess) {
                            ArrayList<VendorPermissionBean> userPermissionsList = new ArrayList<VendorPermissionBean>();
                            TreeSet<String> vendorAccessSet = new TreeSet<String>(vendorAccess);
                            
                            for (String vendorName: vendorList) {
                                VendorPermissionBean vendorPermissionBean = new VendorPermissionBean();
                                vendorPermissionBean.setVendorName(vendorName);
                                vendorPermissionBean.setAccess(vendorAccessSet.contains(vendorName));
                                userPermissionsList.add(vendorPermissionBean);
                            }
                            
                            return userPermissionsList;
                        }
                    }
             );
        }
    }
    
    @Override
    public String mayStop() {
        UserPermissionsDisplay permissionsDisplay = (UserPermissionsDisplay) globals.getDisplay(UserPermissionsDisplay.class);
        if (permissionsDisplay.hasUnsavedChanges()) {
            new UserPermissionsSaveActivity(GwtAdminGlobals.getInstance(), userId, role, permissionsDisplay.getVendorsPermissionList()).start(null, null);
        }
        return null;
    }

}
