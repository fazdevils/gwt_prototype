package com.vincentfazio.ui.administration.activity;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.vincentfazio.ui.activity.GwtActivity;
import com.vincentfazio.ui.administration.view.CompanyUserPermissionsDisplay;
import com.vincentfazio.ui.bean.ErrorBean;
import com.vincentfazio.ui.bean.StatusBean;
import com.vincentfazio.ui.bean.CompanyUserPermissionBean;
import com.vincentfazio.ui.global.Globals;
import com.vincentfazio.ui.model.CompanyUserPermissionsModel;
import com.vincentfazio.ui.view.ErrorDisplay;
import com.vincentfazio.ui.view.StatusDisplay;

public class CompanyUserPermissionsSaveActivity extends GwtActivity {

    private final Globals globals;
    private final String companyId;
    private final String role;
    private final List<CompanyUserPermissionBean> companyUserPermissions;

    public CompanyUserPermissionsSaveActivity(Globals globals, String companyId, String role, List<CompanyUserPermissionBean> companyUserPermissions) {
        super();
        this.globals = globals;
        this.companyId = companyId;
        this.role = role;
        this.companyUserPermissions = companyUserPermissions;
    }    

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        List<String> users = new ArrayList<String>();
        
        for (CompanyUserPermissionBean companyUserPermissionBean: companyUserPermissions) {
            if (companyUserPermissionBean.hasAccess()) {
                users.add(companyUserPermissionBean.getUserName());
            }
        }
        
        CompanyUserPermissionsModel model = (CompanyUserPermissionsModel) globals.getModel(CompanyUserPermissionsModel.class);
        model.saveCompanyUserPermissions(companyId, role, users,
                new AsyncCallback<String>() {
                    public void onSuccess(String result) {
                        StatusDisplay statusDisplay = (StatusDisplay) globals.getStatusDisplay();
                        statusDisplay.handleStatusUpdate(new StatusBean("Permission Changes Saved"));
                        CompanyUserPermissionsDisplay permissionsDisplay = (CompanyUserPermissionsDisplay) globals.getDisplay(CompanyUserPermissionsDisplay.class);
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
