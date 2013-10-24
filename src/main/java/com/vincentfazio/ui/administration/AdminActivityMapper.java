package com.vincentfazio.ui.administration;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import com.vincentfazio.ui.activity.MyDetailsActivity;
import com.vincentfazio.ui.activity.MyPasswordActivity;
import com.vincentfazio.ui.administration.activity.UserDetailsActivity;
import com.vincentfazio.ui.administration.activity.UserListActivity;
import com.vincentfazio.ui.administration.activity.UserPermissionsActivity;
import com.vincentfazio.ui.administration.activity.VendorDetailsActivity;
import com.vincentfazio.ui.administration.activity.VendorListActivity;
import com.vincentfazio.ui.administration.activity.VendorUserPermissionsActivity;
import com.vincentfazio.ui.administration.place.MyUserPasswordPlace;
import com.vincentfazio.ui.administration.place.MyUserSettingsPlace;
import com.vincentfazio.ui.administration.place.UserDetailsPlace;
import com.vincentfazio.ui.administration.place.UserListPlace;
import com.vincentfazio.ui.administration.place.UserPermissionsPlace;
import com.vincentfazio.ui.administration.place.VendorDetailsPlace;
import com.vincentfazio.ui.administration.place.VendorListPlace;
import com.vincentfazio.ui.administration.place.VendorUserPermissionsPlace;
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
        } else if (place instanceof VendorDetailsPlace) {
            return new VendorDetailsActivity(globals, ((VendorDetailsPlace) place).getVendorId());
        } else if (place instanceof VendorListPlace) {
            return new VendorListActivity(globals, true);
        } else if (place instanceof VendorUserPermissionsPlace) {
            return new VendorUserPermissionsActivity(globals, ((VendorUserPermissionsPlace) place).getVendorId(), ((VendorUserPermissionsPlace) place).getRole());
        }
        return null;
    }

}
