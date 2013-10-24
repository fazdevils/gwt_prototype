package com.vincentfazio.ui.vendor;

import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;
import com.vincentfazio.ui.vendor.place.DocumentationPlace;
import com.vincentfazio.ui.vendor.place.MyUserPasswordPlace;
import com.vincentfazio.ui.vendor.place.MyUserSettingsPlace;
import com.vincentfazio.ui.vendor.place.ProfileSurveyPlace;
import com.vincentfazio.ui.vendor.place.SecuritySurveyPlace;
import com.vincentfazio.ui.vendor.place.VendorAccessPlace;
import com.vincentfazio.ui.vendor.place.VendorDetailsPlace;

@WithTokenizers({
    MyUserSettingsPlace.Tokenizer.class,
    MyUserPasswordPlace.Tokenizer.class,
    VendorDetailsPlace.Tokenizer.class,
    SecuritySurveyPlace.Tokenizer.class,
    ProfileSurveyPlace.Tokenizer.class,
    VendorAccessPlace.Tokenizer.class,
    DocumentationPlace.Tokenizer.class
})

public interface VendorPlaceHistoryMapper extends PlaceHistoryMapper {
    
}