package com.vincentfazio.ui.administration.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;
import com.vincentfazio.ui.administration.global.GwtAdminGlobals;
import com.vincentfazio.ui.administration.view.CompanyListDisplay;
import com.vincentfazio.ui.administration.view.AdminHomeDisplay.AdminDeckEnum;
import com.vincentfazio.ui.administration.view.CompanyListDisplay.CompanyDeckEnum;
import com.vincentfazio.ui.view.HomeDisplay;

public class CompanyUserPermissionsPlace extends Place {

    private String companyId;
    private String role;

    public CompanyUserPermissionsPlace(String companyId, String role) {
        this.companyId = companyId;
        this.role = role;

        HomeDisplay homeDisplay = (HomeDisplay) GwtAdminGlobals.getInstance().getDisplay(HomeDisplay.class);
        homeDisplay.showView(AdminDeckEnum.MainNavigation);
        
        CompanyListDisplay companyListDisplay = (CompanyListDisplay) GwtAdminGlobals.getInstance().getDisplay(CompanyListDisplay.class);
        companyListDisplay.showView(CompanyDeckEnum.CompanyUserPermissions);
    }

    public String getCompanyId() {
        return companyId;
    }
    
    public String getRole() {
        return role;
    }

    @Prefix(Tokenizer.PLACE_TOKEN)
    public static class Tokenizer implements PlaceTokenizer<CompanyUserPermissionsPlace> {
        public static final String PLACE_TOKEN = "companyp";

        @Override
        public String getToken(CompanyUserPermissionsPlace place) {
            return place.getCompanyId() + "&" + place.getRole().toLowerCase();
        }

        @Override
        public CompanyUserPermissionsPlace getPlace(String token) {
            String[] tokens = token.split("&");
            return new CompanyUserPermissionsPlace(tokens[0], tokens[1]);
        }
        
    }

}
