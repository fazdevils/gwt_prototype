package com.vincentfazio.ui.survey.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;
import com.vincentfazio.ui.survey.global.GwtSurveyGlobals;
import com.vincentfazio.ui.survey.view.CompanyHomeDisplay.CompanyDeckEnum;
import com.vincentfazio.ui.view.HomeDisplay;

public class MyUserPasswordPlace extends Place {

    public MyUserPasswordPlace() {
    }

    @Prefix(Tokenizer.PLACE_TOKEN)
    public static class Tokenizer implements PlaceTokenizer<MyUserPasswordPlace> {
        public static final String PLACE_TOKEN = "password";

        @Override
        public String getToken(MyUserPasswordPlace place) {
            HomeDisplay homeDisplay = (HomeDisplay) GwtSurveyGlobals.getInstance().getDisplay(HomeDisplay.class);
            homeDisplay.showView(CompanyDeckEnum.UserPassword);
            
            return "user";            
        }

        @Override
        public MyUserPasswordPlace getPlace(String token) {
            return new MyUserPasswordPlace();
        }
        
    }

}
