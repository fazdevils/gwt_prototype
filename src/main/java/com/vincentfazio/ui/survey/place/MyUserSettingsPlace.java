package com.vincentfazio.ui.survey.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;
import com.vincentfazio.ui.survey.global.GwtSurveyGlobals;
import com.vincentfazio.ui.survey.view.CompanyHomeDisplay.CompanyDeckEnum;
import com.vincentfazio.ui.view.HomeDisplay;

public class MyUserSettingsPlace extends Place {

    public MyUserSettingsPlace() {
    }

    @Prefix(Tokenizer.PLACE_TOKEN)
    public static class Tokenizer implements PlaceTokenizer<MyUserSettingsPlace> {
        public static final String PLACE_TOKEN = "settings";

        @Override
        public String getToken(MyUserSettingsPlace place) {
            HomeDisplay homeDisplay = (HomeDisplay) GwtSurveyGlobals.getInstance().getDisplay(HomeDisplay.class);
            homeDisplay.showView(CompanyDeckEnum.UserSettings);
            
            return "user";            
        }

        @Override
        public MyUserSettingsPlace getPlace(String token) {
            return new MyUserSettingsPlace();
        }
        
    }

}
