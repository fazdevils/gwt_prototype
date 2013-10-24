package com.vincentfazio.ui.administration;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import com.vincentfazio.ui.activity.MyDetailsActivity;
import com.vincentfazio.ui.activity.MyPasswordActivity;
import com.vincentfazio.ui.administration.activity.UserDetailsActivity;
import com.vincentfazio.ui.administration.activity.UserListActivity;
import com.vincentfazio.ui.administration.activity.UserPermissionsActivity;
import com.vincentfazio.ui.administration.activity.CompanyDetailsActivity;
import com.vincentfazio.ui.administration.activity.CompanyListActivity;
import com.vincentfazio.ui.administration.activity.CompanyUserPermissionsActivity;
import com.vincentfazio.ui.administration.place.MyUserPasswordPlace;
import com.vincentfazio.ui.administration.place.MyUserSettingsPlace;
import com.vincentfazio.ui.administration.place.UserDetailsPlace;
import com.vincentfazio.ui.administration.place.UserListPlace;
import com.vincentfazio.ui.administration.place.UserPermissionsPlace;
import com.vincentfazio.ui.administration.place.CompanyDetailsPlace;
import com.vincentfazio.ui.administration.place.CompanyListPlace;
import com.vincentfazio.ui.administration.place.CompanyUserPermissionsPlace;
import com.vincentfazio.ui.global.Globals;

public class AdminActivityMapper implements ActivityMapper {

    private Globals globals;
    
    public AdminActivityMapper(Globals globals) {
        super();
        this.globals = globals;
    }

    @Override
    public Activity getActivity(Place place) {
        if (place instanceof MyUserSettingsPlace) {
            return new MyDetailsActivity(globals);
        } else if (place instanceof MyUserPasswordPlace) {
            return new MyPasswordActivity(globals);
        } else if (place instanceof UserPermissionsPlace) {
            return new UserPermissionsActivity(globals, ((UserPermissionsPlace) place).getUid(), ((UserPermissionsPlace) place).getRole());
        } else if (place instanceof UserListPlace) {
            return new UserListActivity(globals, true);
        } else if (place instanceof UserDetailsPlace) {
            return new UserDetailsActivity(globals, ((UserDetailsPlace) place).getUid());
        } else if (place instanceof CompanyDetailsPlace) {
            return new CompanyDetailsActivity(globals, ((CompanyDetailsPlace) place).getCompanyId());
        } else if (place instanceof CompanyListPlace) {
            return new CompanyListActivity(globals, true);
        } else if (place instanceof CompanyUserPermissionsPlace) {
            return new CompanyUserPermissionsActivity(globals, ((CompanyUserPermissionsPlace) place).getCompanyId(), ((CompanyUserPermissionsPlace) place).getRole());
        }
        return null;
    }

}
