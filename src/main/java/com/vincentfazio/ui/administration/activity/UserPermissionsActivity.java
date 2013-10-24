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
import com.vincentfazio.ui.bean.CompanyPermissionBean;
import com.vincentfazio.ui.global.Globals;
import com.vincentfazio.ui.model.UserPermissionsModel;
import com.vincentfazio.ui.model.CompanyListModel;
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
        if (permissionsDisplay.isCompanyListLoaded()) {  
            loadUserPermissionsData();            
        } else {
            CompanyListModel companyListModel = (CompanyListModel) globals.getModel(CompanyListModel.class);
            companyListModel.getCompanyList(  // normally this would get loaded when the company list display is loaded
                    new AsyncCallback<ArrayList<String>>() {
                        public void onSuccess(ArrayList<String> result) {
                            UserPermissionsDisplay permissionsDisplay = (UserPermissionsDisplay) globals.getDisplay(UserPermissionsDisplay.class);
                            permissionsDisplay.setCompanyList(result);
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
                            permissionsDisplay.setCompaniesPermissionList(getUserPermissionsList(permissionsDisplay.getCompanyList(), result));
                            
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
                        
                        private List<CompanyPermissionBean> getUserPermissionsList(List<String> companyList, List<String> companyAccess) {
                            ArrayList<CompanyPermissionBean> userPermissionsList = new ArrayList<CompanyPermissionBean>();
                            TreeSet<String> companyAccessSet = new TreeSet<String>(companyAccess);
                            
                            for (String companyName: companyList) {
                                CompanyPermissionBean companyPermissionBean = new CompanyPermissionBean();
                                companyPermissionBean.setCompanyName(companyName);
                                companyPermissionBean.setAccess(companyAccessSet.contains(companyName));
                                userPermissionsList.add(companyPermissionBean);
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
            new UserPermissionsSaveActivity(GwtAdminGlobals.getInstance(), userId, role, permissionsDisplay.getCompaniesPermissionList()).start(null, null);
        }
        return null;
    }

}
