package com.vincentfazio.ui.administration.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;
import com.vincentfazio.ui.administration.global.GwtAdminGlobals;
import com.vincentfazio.ui.administration.view.CompanyListDisplay;
import com.vincentfazio.ui.administration.view.AdminHomeDisplay.AdminDeckEnum;
import com.vincentfazio.ui.administration.view.CompanyListDisplay.CompanyDeckEnum;
import com.vincentfazio.ui.view.HomeDisplay;

public class CompanyDetailsPlace extends Place {

    private String companyId;

    public CompanyDetailsPlace(String token) {
        this.companyId = token;

        HomeDisplay homeDisplay = (HomeDisplay) GwtAdminGlobals.getInstance().getDisplay(HomeDisplay.class);
        homeDisplay.showView(AdminDeckEnum.MainNavigation);
        
        CompanyListDisplay companyListDisplay = (CompanyListDisplay) GwtAdminGlobals.getInstance().getDisplay(CompanyListDisplay.class);
        companyListDisplay.showView(CompanyDeckEnum.CompanyDetails);
    }

    public String getCompanyId() {
        return companyId;
    }
    
    @Prefix(Tokenizer.PLACE_TOKEN)
    public static class Tokenizer implements PlaceTokenizer<CompanyDetailsPlace> {
        public static final String PLACE_TOKEN = "company";

        @Override
        public String getToken(CompanyDetailsPlace place) {
            return place.getCompanyId();
        }

        @Override
        public CompanyDetailsPlace getPlace(String token) {
            return new CompanyDetailsPlace(token);
        }
        
    }

}
