package com.vincentfazio.ui.administration.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;
import com.vincentfazio.ui.administration.global.GwtAdminGlobals;
import com.vincentfazio.ui.administration.view.AdminHomeDisplay.AdminDeckEnum;
import com.vincentfazio.ui.view.HomeDisplay;

public class VendorListPlace extends Place {

    public VendorListPlace() {
        super();
        HomeDisplay homeDisplay = (HomeDisplay) GwtAdminGlobals.getInstance().getDisplay(HomeDisplay.class);
        homeDisplay.showView(AdminDeckEnum.MainNavigation);
    }
    
    @Prefix(Tokenizer.PLACE_TOKEN)
    public static class Tokenizer implements PlaceTokenizer<VendorListPlace> {
        public static final String PLACE_TOKEN = "home";

        @Override
        public String getToken(VendorListPlace place) {
            return "administrator";            
        }

        @Override
        public VendorListPlace getPlace(String token) {
            return new VendorListPlace();
        }
        
    }

}
