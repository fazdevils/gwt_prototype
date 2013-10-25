package com.vincentfazio.ui.survey;

import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;
import com.vincentfazio.ui.survey.place.DocumentationPlace;
import com.vincentfazio.ui.survey.place.MyUserPasswordPlace;
import com.vincentfazio.ui.survey.place.MyUserSettingsPlace;
import com.vincentfazio.ui.survey.place.ProfileSurveyPlace;
import com.vincentfazio.ui.survey.place.SecuritySurveyPlace;
import com.vincentfazio.ui.survey.place.CompanyAccessPlace;
import com.vincentfazio.ui.survey.place.CompanyDetailsPlace;

@WithTokenizers({
    MyUserSettingsPlace.Tokenizer.class,
    MyUserPasswordPlace.Tokenizer.class,
    CompanyDetailsPlace.Tokenizer.class,
    SecuritySurveyPlace.Tokenizer.class,
    ProfileSurveyPlace.Tokenizer.class,
    CompanyAccessPlace.Tokenizer.class,
    DocumentationPlace.Tokenizer.class
})

public interface SurveyPlaceHistoryMapper extends PlaceHistoryMapper {
    
}