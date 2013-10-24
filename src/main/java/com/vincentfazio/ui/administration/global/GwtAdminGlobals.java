package com.vincentfazio.ui.administration.global;

import com.google.gwt.place.shared.Place;
import com.vincentfazio.ui.administration.model.mock.UserMockModel;
import com.vincentfazio.ui.administration.model.mock.UserPasswordResetMockModel;
import com.vincentfazio.ui.administration.model.mock.UserPermissionsMockModel;
import com.vincentfazio.ui.administration.model.mock.CompanyDnbRefreshMockModel;
import com.vincentfazio.ui.administration.model.mock.CompanyMockModel;
import com.vincentfazio.ui.administration.model.mock.CompanyUserPermissionsMockModel;
import com.vincentfazio.ui.administration.model.web_service.UserDetailsService;
import com.vincentfazio.ui.administration.model.web_service.UserListService;
import com.vincentfazio.ui.administration.model.web_service.UserPasswordResetService;
import com.vincentfazio.ui.administration.model.web_service.UserPermissionsService;
import com.vincentfazio.ui.administration.model.web_service.CompanyDnbRefreshService;
import com.vincentfazio.ui.administration.model.web_service.CompanyUserPermissionsService;
import com.vincentfazio.ui.administration.place.MyUserPasswordPlace;
import com.vincentfazio.ui.administration.place.MyUserSettingsPlace;
import com.vincentfazio.ui.administration.place.UserListPlace;
import com.vincentfazio.ui.global.GwtGlobals;
import com.vincentfazio.ui.model.ErrorModel;
import com.vincentfazio.ui.model.LogoutModel;
import com.vincentfazio.ui.model.MyDetailsModel;
import com.vincentfazio.ui.model.MyPasswordModel;
import com.vincentfazio.ui.model.UserDetailsModel;
import com.vincentfazio.ui.model.UserListModel;
import com.vincentfazio.ui.model.UserPasswordResetModel;
import com.vincentfazio.ui.model.UserPermissionsModel;
import com.vincentfazio.ui.model.CompanyDetailsModel;
import com.vincentfazio.ui.model.CompanyDnbRefreshModel;
import com.vincentfazio.ui.model.CompanyListModel;
import com.vincentfazio.ui.model.CompanyUserPermissionsModel;
import com.vincentfazio.ui.model.mock.ErrorMockModel;
import com.vincentfazio.ui.model.mock.LogoutMockModel;
import com.vincentfazio.ui.model.mock.MyDetailsMockModel;
import com.vincentfazio.ui.model.mock.MyPasswordMockModel;
import com.vincentfazio.ui.model.web_service.ErrorService;
import com.vincentfazio.ui.model.web_service.LogoutService;
import com.vincentfazio.ui.model.web_service.MyDetailsService;
import com.vincentfazio.ui.model.web_service.MyPasswordService;
import com.vincentfazio.ui.model.web_service.CompanyDetailsService;
import com.vincentfazio.ui.model.web_service.CompanyListService;

public class GwtAdminGlobals extends GwtGlobals {

    protected static GwtAdminGlobals instance = null;
    public static GwtGlobals getInstance() {
        if (null == instance) {
            synchronized (GwtAdminGlobals.class) {
                if (null == instance) {
                    instance = new GwtAdminGlobals();
                }
            }
        }
        return instance;
    }

    protected GwtAdminGlobals() {
        super();
    }

    @Override
    protected void setMockModels(GwtGlobals globals) {
        modelMap.put(UserListModel.class, new UserMockModel());
        modelMap.put(UserDetailsModel.class, modelMap.get(UserListModel.class));
        modelMap.put(UserPermissionsModel.class, new UserPermissionsMockModel());
        modelMap.put(CompanyListModel.class, new CompanyMockModel());
        modelMap.put(CompanyDetailsModel.class, modelMap.get(CompanyListModel.class));
        modelMap.put(UserPasswordResetModel.class, new UserPasswordResetMockModel());
        modelMap.put(MyDetailsModel.class, new MyDetailsMockModel(false));
        modelMap.put(MyPasswordModel.class, new MyPasswordMockModel());
        modelMap.put(LogoutModel.class, new LogoutMockModel(globals));
        modelMap.put(ErrorModel.class, new ErrorMockModel(globals));
        modelMap.put(CompanyUserPermissionsModel.class, new CompanyUserPermissionsMockModel());
        modelMap.put(CompanyDnbRefreshModel.class, new CompanyDnbRefreshMockModel());
    }

    @Override
    protected void setSerivceModels(GwtGlobals globals) {
        modelMap.put(UserListModel.class, new UserListService(globals));
        modelMap.put(UserDetailsModel.class, new UserDetailsService(globals));
        modelMap.put(UserPermissionsModel.class, new UserPermissionsService(globals));
        modelMap.put(CompanyListModel.class, new CompanyListService(globals));
        modelMap.put(CompanyDetailsModel.class, new CompanyDetailsService(globals));
        modelMap.put(UserPasswordResetModel.class, new UserPasswordResetService(globals));
        modelMap.put(MyDetailsModel.class, new MyDetailsService(globals));
        modelMap.put(MyPasswordModel.class, new MyPasswordService(globals));
        modelMap.put(LogoutModel.class, new LogoutService(globals));
        modelMap.put(ErrorModel.class, new ErrorService(globals));
        modelMap.put(CompanyUserPermissionsModel.class, new CompanyUserPermissionsService(globals));
        modelMap.put(CompanyDnbRefreshModel.class, new CompanyDnbRefreshService(globals));
    }
    
    @Override
    public String getRole() {
        return "admin";
    }

    @Override
    public Place createMyUserSettingsPlace() {
        return new MyUserSettingsPlace();
    }

    @Override
    public Place createMyUserPasswordPlace() {
        return new MyUserPasswordPlace();
    }

    @Override
    public Place getDefaultPlace() {
        return new UserListPlace();
    }

}
