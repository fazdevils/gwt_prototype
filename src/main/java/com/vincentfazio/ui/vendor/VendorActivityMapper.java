package com.vincentfazio.ui.vendor;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import com.vincentfazio.ui.activity.MyDetailsActivity;
import com.vincentfazio.ui.activity.MyPasswordActivity;
import com.vincentfazio.ui.bean.DocumentationBean;
import com.vincentfazio.ui.global.Globals;
import com.vincentfazio.ui.vendor.activity.DocumentationDetailsActivity;
import com.vincentfazio.ui.vendor.activity.DocumentationListActivity;
import com.vincentfazio.ui.vendor.activity.ProfileSurveyActivity;
import com.vincentfazio.ui.vendor.activity.SecuritySurveyActivity;
import com.vincentfazio.ui.vendor.activity.VendorAccessActivity;
import com.vincentfazio.ui.vendor.activity.VendorDetailsActivity;
import com.vincentfazio.ui.vendor.activity.VendorAccessActivity.VendorAccessDisplayType;
import com.vincentfazio.ui.vendor.place.DocumentationPlace;
import com.vincentfazio.ui.vendor.place.MyUserPasswordPlace;
import com.vincentfazio.ui.vendor.place.MyUserSettingsPlace;
import com.vincentfazio.ui.vendor.place.ProfileSurveyPlace;
import com.vincentfazio.ui.vendor.place.SecuritySurveyPlace;
import com.vincentfazio.ui.vendor.place.VendorAccessPlace;
import com.vincentfazio.ui.vendor.place.VendorDetailsPlace;

public class VendorActivityMapper implements ActivityMapper {

    private Globals globals;
    
    public VendorActivityMapper(Globals globals) {
        super();
        this.globals = globals;
    }

    @Override
    public Activity getActivity(Place place) {
        if (place instanceof MyUserSettingsPlace) {
            return new MyDetailsActivity(globals);
        } else if (place instanceof MyUserPasswordPlace) {
            return new MyPasswordActivity(globals);
        } else if (place instanceof VendorAccessPlace) {
            return new VendorAccessActivity(globals, VendorAccessDisplayType.VendorDetails);
        } else if (place instanceof VendorDetailsPlace) {
            return new VendorDetailsActivity(((VendorDetailsPlace)place).getVendorId(), globals);
        } else if (place instanceof ProfileSurveyPlace) {
            return new ProfileSurveyActivity(((ProfileSurveyPlace)place).getVendorId(), ((ProfileSurveyPlace)place).getQuestion(), globals);
        } else if (place instanceof SecuritySurveyPlace) {
            return new SecuritySurveyActivity(((SecuritySurveyPlace)place).getVendorId(), ((SecuritySurveyPlace)place).getQuestion(), globals);
        } else if (place instanceof DocumentationPlace) {
            DocumentationPlace documentationPlace = (DocumentationPlace)place;
			DocumentationBean documentation = documentationPlace.getDocumentation();
			String vendorId = documentationPlace.getVendorId();
            if (null == documentation) {
    			return new DocumentationListActivity(vendorId, globals);            	
            } else {
            	return new DocumentationDetailsActivity(vendorId, documentation, globals);
            }
        } 
        return null;
    }

}
