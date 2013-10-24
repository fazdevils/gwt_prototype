package com.vincentfazio.ui.administration;

import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;
import com.vincentfazio.ui.administration.place.MyUserPasswordPlace;
import com.vincentfazio.ui.administration.place.MyUserSettingsPlace;
import com.vincentfazio.ui.administration.place.UserDetailsPlace;
import com.vincentfazio.ui.administration.place.UserPermissionsPlace;
import com.vincentfazio.ui.administration.place.VendorDetailsPlace;
import com.vincentfazio.ui.administration.place.VendorListPlace;
import com.vincentfazio.ui.administration.place.VendorUserPermissionsPlace;

@WithTokenizers({
    MyUserSettingsPlace.Tokenizer.class,
    MyUserPasswordPlace.Tokenizer.class,
    UserDetailsPlace.Tokenizer.class,
    UserPermissionsPlace.Tokenizer.class,
    VendorDetailsPlace.Tokenizer.class,
    VendorListPlace.Tokenizer.class,
    VendorUserPermissionsPlace.Tokenizer.class
})

public interface AdminPlaceHistoryMapper extends PlaceHistoryMapper {
    
}