package com.vincentfazio.ui.survey.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;
import com.vincentfazio.ui.survey.global.GwtSurveyGlobals;
import com.vincentfazio.ui.survey.view.CompanyHomeDisplay.CompanyDeckEnum;
import com.vincentfazio.ui.view.HomeDisplay;

public class CompanyDetailsPlace extends Place {

    private String companyId;

    public CompanyDetailsPlace(String token) {
        this.companyId = token;

        HomeDisplay display = (HomeDisplay) GwtSurveyGlobals.getInstance().getDisplay(HomeDisplay.class);
        display.showView(CompanyDeckEnum.CompanyDetails);
    }

    public String getCompanyId() {
        return companyId;
    }
    
    @Prefix(Tokenizer.PLACE_TOKEN)
    public static class Tokenizer implements PlaceTokenizer<CompanyDetailsPlace> {
        public static final String PLACE_TOKEN = "company";

        @Override
        public String getToken(CompanyDetailsPlace place) {
            if (null == place.getCompanyId()) {
                return "";
            }
            return place.getCompanyId();
        }

        @Override
        public CompanyDetailsPlace getPlace(String token) {
            return new CompanyDetailsPlace(token);
        }
        
    }

}
