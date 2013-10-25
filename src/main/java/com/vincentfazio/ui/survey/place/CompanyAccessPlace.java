package com.vincentfazio.ui.survey.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;
import com.vincentfazio.ui.survey.global.GwtSurveyGlobals;
import com.vincentfazio.ui.survey.view.CompanyHomeDisplay.CompanyDeckEnum;
import com.vincentfazio.ui.view.HomeDisplay;

public class CompanyAccessPlace extends Place {

    public CompanyAccessPlace() {
        super();
        HomeDisplay display = (HomeDisplay) GwtSurveyGlobals.getInstance().getDisplay(HomeDisplay.class);
        display.showView(CompanyDeckEnum.CompanyDetails);
    }

    @Prefix(Tokenizer.PLACE_TOKEN)
    public static class Tokenizer implements PlaceTokenizer<CompanyAccessPlace> {
        public static final String PLACE_TOKEN = "home";

        @Override
        public String getToken(CompanyAccessPlace place) {
            return "company";            
        }

        @Override
        public CompanyAccessPlace getPlace(String token) {
            return new CompanyAccessPlace();
        }
        
    }


}
