package com.vincentfazio.ui.vendor.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;
import com.vincentfazio.ui.vendor.global.GwtVendorGlobals;
import com.vincentfazio.ui.vendor.view.VendorHomeDisplay.VendorDeckEnum;
import com.vincentfazio.ui.view.HomeDisplay;

public class MyUserPasswordPlace extends Place {

    public MyUserPasswordPlace() {
    }

    @Prefix(Tokenizer.PLACE_TOKEN)
    public static class Tokenizer implements PlaceTokenizer<MyUserPasswordPlace> {
        public static final String PLACE_TOKEN = "password";

        @Override
        public String getToken(MyUserPasswordPlace place) {
            HomeDisplay homeDisplay = (HomeDisplay) GwtVendorGlobals.getInstance().getDisplay(HomeDisplay.class);
            homeDisplay.showView(VendorDeckEnum.UserPassword);
            
            return "user";            
        }

        @Override
        public MyUserPasswordPlace getPlace(String token) {
            return new MyUserPasswordPlace();
        }
        
    }

}
