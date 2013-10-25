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
import com.vincentfazio.ui.administration.view.CompanyUserPermissionsDisplay;
import com.vincentfazio.ui.bean.ErrorBean;
import com.vincentfazio.ui.bean.CompanyUserPermissionBean;
import com.vincentfazio.ui.global.Globals;
import com.vincentfazio.ui.model.UserListModel;
import com.vincentfazio.ui.model.CompanyUserPermissionsModel;
import com.vincentfazio.ui.view.ErrorDisplay;

public class CompanyUserPermissionsActivity extends GwtActivity {

    private final Globals globals;
    private final String companyId;
    private final String role;

    public CompanyUserPermissionsActivity(Globals globals, String companyId, String role) {
        super();
        this.globals = globals;
        this.companyId = companyId;
        this.role = role;
    }

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        UserListModel userListModel = (UserListModel) globals.getModel(UserListModel.class);
        userListModel.getUserList(role,
                new AsyncCallback<ArrayList<String>>() {
                    public void onSuccess(ArrayList<String> result) {
                        CompanyUserPermissionsDisplay permissionsDisplay = (CompanyUserPermissionsDisplay) globals.getDisplay(CompanyUserPermissionsDisplay.class);
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
        CompanyUserPermissionsDisplay permissionsDisplay = (CompanyUserPermissionsDisplay) globals.getDisplay(CompanyUserPermissionsDisplay.class);
        if (!permissionsDisplay.getCompanyId().equals(companyId) || !permissionsDisplay.getUserRole().equals(role)) {
            permissionsDisplay.setCompanyId(companyId);
            CompanyUserPermissionsModel model = (CompanyUserPermissionsModel) globals.getModel(CompanyUserPermissionsModel.class);
            model.getCompanyUserPermissions(companyId, role,
                    new AsyncCallback<List<String>>() {
                        public void onSuccess(List<String> result) {
                            CompanyUserPermissionsDisplay permissionsDisplay = (CompanyUserPermissionsDisplay) globals.getDisplay(CompanyUserPermissionsDisplay.class);
                            permissionsDisplay.setCompanyId(companyId);
                            permissionsDisplay.setUserRole(role);
                            permissionsDisplay.setCompanyUserPermissionList(getUserPermissionsList(permissionsDisplay.getUserList(), result));
                            
                            // initialize the user list if it isn't
                            UserListDisplay userListDisplay = (UserListDisplay) globals.getDisplay(UserListDisplay.class);
                            if (!userListDisplay.isUserListLoaded()) {
                                new UserListActivity(GwtAdminGlobals.getInstance(), false).start(null, null);
                            } else {
                                userListDisplay.selectUser(companyId, false);
                            }
                        }
                        
                        public void onFailure(Throwable caught) {
                            ErrorDisplay errorDisplay = (ErrorDisplay) globals.getErrorDisplay();
                            errorDisplay.handleError(new ErrorBean(caught.getMessage(), caught));
                        }
                        
                        private List<CompanyUserPermissionBean> getUserPermissionsList(List<String> userList, List<String> userAccess) {
                            ArrayList<CompanyUserPermissionBean> companyUserPermissionsList = new ArrayList<CompanyUserPermissionBean>();
                            TreeSet<String> companyUserAccessSet = new TreeSet<String>(userAccess);
                            
                            for (String userName: userList) {
                                CompanyUserPermissionBean companyUserPermissionBean = new CompanyUserPermissionBean();
                                companyUserPermissionBean.setUserName(userName);
                                companyUserPermissionBean.setAccess(companyUserAccessSet.contains(userName));
                                companyUserPermissionsList.add(companyUserPermissionBean);
                            }
                            
                            return companyUserPermissionsList;
                        }
                    }
             );
        }
    }
    
    @Override
    public String mayStop() {
        CompanyUserPermissionsDisplay permissionsDisplay = (CompanyUserPermissionsDisplay) globals.getDisplay(CompanyUserPermissionsDisplay.class);
        if (permissionsDisplay.hasUnsavedChanges()) {
            new CompanyUserPermissionsSaveActivity(GwtAdminGlobals.getInstance(), companyId, role, permissionsDisplay.getCompanyUserPermissionList()).start(null, null);
        }
        return null;
    }

}
