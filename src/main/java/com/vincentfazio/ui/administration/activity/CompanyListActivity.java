package com.vincentfazio.ui.administration.activity;

import java.util.ArrayList;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.vincentfazio.ui.activity.GwtActivity;
import com.vincentfazio.ui.administration.global.GwtAdminGlobals;
import com.vincentfazio.ui.administration.place.CompanyDetailsPlace;
import com.vincentfazio.ui.administration.place.CompanyUserPermissionsPlace;
import com.vincentfazio.ui.administration.view.UserPermissionsDisplay;
import com.vincentfazio.ui.administration.view.CompanyListDisplay;
import com.vincentfazio.ui.administration.view.CompanyUserPermissionsDisplay;
import com.vincentfazio.ui.administration.view.CompanyListDisplay.CompanyDeckEnum;
import com.vincentfazio.ui.bean.ErrorBean;
import com.vincentfazio.ui.global.Globals;
import com.vincentfazio.ui.model.CompanyListModel;
import com.vincentfazio.ui.view.ErrorDisplay;

public class CompanyListActivity extends GwtActivity implements CompanyListDisplay.Presenter {
    
    public CompanyListActivity(Globals globals, Boolean refreshCompanyDetail) {
        super();
        this.globals = globals;
        this.refreshCompanyDetail = refreshCompanyDetail;
        
        CompanyListDisplay companyListDisplay = (CompanyListDisplay) globals.getDisplay(CompanyListDisplay.class);
        companyListDisplay.setPresenter(this);
    }

    private final Boolean refreshCompanyDetail;
    private final Globals globals;
    
    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        CompanyListModel model = (CompanyListModel) globals.getModel(CompanyListModel.class);
        model.getCompanyList(
                new AsyncCallback<ArrayList<String>>() {
                    public void onSuccess(ArrayList<String> result) {
                        CompanyListDisplay companyListDisplay = (CompanyListDisplay) globals.getDisplay(CompanyListDisplay.class);
                        companyListDisplay.setCompanyList(result);
                        companyListDisplay.selectCompany(refreshCompanyDetail);
                        
                        UserPermissionsDisplay userPermissionsDisplay = (UserPermissionsDisplay) globals.getDisplay(UserPermissionsDisplay.class);
                        userPermissionsDisplay.setCompanyList(result);                        
                    }
                    public void onFailure(Throwable caught) {
                        ErrorDisplay errorDisplay = (ErrorDisplay) globals.getErrorDisplay();
                        errorDisplay.handleError(new ErrorBean(caught.getMessage(), caught));
                    }
                }
             );
    }

    @Override
    public void switchCompany(String companyId, CompanyDeckEnum currentPage) {
        switch (currentPage) {
            case CompanyUserPermissions:
                CompanyUserPermissionsDisplay permissionsDisplay =  (CompanyUserPermissionsDisplay) GwtAdminGlobals.getInstance().getDisplay(CompanyUserPermissionsDisplay.class);
                globals.gotoPlace(new CompanyUserPermissionsPlace(companyId, permissionsDisplay.getUserRole()));                
                break;
            case CompanyDetails:
                globals.gotoPlace(new CompanyDetailsPlace(companyId));
                break;
            default:
                break;
        }

    }

}
