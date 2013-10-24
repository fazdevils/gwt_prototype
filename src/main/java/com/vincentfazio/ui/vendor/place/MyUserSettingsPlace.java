package com.vincentfazio.ui.vendor.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;
import com.vincentfazio.ui.vendor.global.GwtVendorGlobals;
import com.vincentfazio.ui.vendor.view.VendorHomeDisplay.VendorDeckEnum;
import com.vincentfazio.ui.view.HomeDisplay;

public class MyUserSettingsPlace extends Place {

    public MyUserSettingsPlace() {
    }

    @Prefix(Tokenizer.PLACE_TOKEN)
    public static class Tokenizer implements PlaceTokenizer<MyUserSettingsPlace> {
        public static final String PLACE_TOKEN = "settings";

        @Override
        public String getToken(MyUserSettingsPlace place) {
            HomeDisplay homeDisplay = (HomeDisplay) GwtVendorGlobals.getInstance().getDisplay(HomeDisplay.class);
            homeDisplay.showView(VendorDeckEnum.UserSettings);
            
            return "user";            
        }

        @Override
        public MyUserSettingsPlace getPlace(String token) {
            return new MyUserSettingsPlace();
        }
        
    }

}
