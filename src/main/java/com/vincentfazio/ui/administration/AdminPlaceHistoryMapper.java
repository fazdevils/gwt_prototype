package com.vincentfazio.ui.administration;

import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;
import com.vincentfazio.ui.administration.place.MyUserPasswordPlace;
import com.vincentfazio.ui.administration.place.MyUserSettingsPlace;
import com.vincentfazio.ui.administration.place.UserDetailsPlace;
import com.vincentfazio.ui.administration.place.UserPermissionsPlace;
import com.vincentfazio.ui.administration.place.CompanyDetailsPlace;
import com.vincentfazio.ui.administration.place.CompanyListPlace;
import com.vincentfazio.ui.administration.place.CompanyUserPermissionsPlace;

@WithTokenizers({
    MyUserSettingsPlace.Tokenizer.class,
    MyUserPasswordPlace.Tokenizer.class,
    UserDetailsPlace.Tokenizer.class,
    UserPermissionsPlace.Tokenizer.class,
    CompanyDetailsPlace.Tokenizer.class,
    CompanyListPlace.Tokenizer.class,
    CompanyUserPermissionsPlace.Tokenizer.class
})

public interface AdminPlaceHistoryMapper extends PlaceHistoryMapper {
    
}